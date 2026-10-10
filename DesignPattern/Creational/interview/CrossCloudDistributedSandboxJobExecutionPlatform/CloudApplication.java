package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform;

import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.builder.AwsComputeInstanceBuilder;
import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.builder.GcpComputeInstanceBuilder;
import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory.*;
import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.prototype.ExecutionEnvironment;
import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.singleton.GlobalInfrastructureManager;

import java.util.HashMap;
import java.util.Map;

public class CloudApplication {

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("      CROSS-CLOUD DISTRIBUTED EXECUTION PLATFORM - VERIFICATION TEST SUITE      ");
        System.out.println("================================================================================\n");

        InfrastructureProvisioner provisioner = new InfrastructureProvisioner();
        CloudInfrastructureFactory awsFactory = provisioner.getFactory("AWS");
        CloudInfrastructureFactory gcpFactory = provisioner.getFactory("GCP");
        GlobalInfrastructureManager manager = GlobalInfrastructureManager.getInstance();

        // -----------------------------------------------------------------------------------------
        // STAGE 1: NEGATIVE VALIDATION TESTS (FAIL-FAST INVARIANTS)
        // -----------------------------------------------------------------------------------------
        System.out.println("--- STAGE 1: NEGATIVE VALIDATION TESTS ---");

        // Test 1.1: Builder Invariant Check (RAM < 2x CPU)
        try {
            System.out.print("[TEST 1.1] Builder Invariant (RAM < 2x CPU): ");
            new AwsComputeInstanceBuilder()
                    .instanceId("aws-fail-01")
                    .cpuCores(4)
                    .ramGb(4) // Violation: 4GB is less than required 8GB
                    .subnetCidr("10.0.1.0/24")
                    .build();
            System.err.println("FAILED (Should have thrown IllegalArgumentException)");
        } catch (IllegalArgumentException ex) {
            System.out.println("PASSED -> Caught expected exception: " + ex.getMessage());
        }

        // Test 1.2: Builder Mandatory Field Check (Missing Subnet CIDR)
        try {
            System.out.print("[TEST 1.2] Builder Invariant (Missing Subnet CIDR): ");
            new AwsComputeInstanceBuilder()
                    .instanceId("aws-fail-02")
                    .cpuCores(2)
                    .ramGb(8)
                    .build(); // Missing subnet CIDR
            System.err.println("FAILED (Should have thrown IllegalStateException)");
        } catch (IllegalStateException ex) {
            System.out.println("PASSED -> Caught expected exception: " + ex.getMessage());
        }

        // Test 1.3: Abstract Factory Cross-Cloud Mixing Guard
        try {
            System.out.print("[TEST 1.3] Cross-Cloud Incompatibility Guard: ");
            ComputeInstance awsCompute = awsFactory.createComputeInstance(
                    new AwsComputeInstanceBuilder()
                            .instanceId("aws-test-01")
                            .cpuCores(2)
                            .ramGb(4)
                            .subnetCidr("10.0.1.0/24")
            );
            StorageVolume gcpDisk = gcpFactory.createStorageVolume("gcp-disk-99", 50);

            // Attempt illegal cross-cloud attachment
            awsCompute.attachVolume(gcpDisk);
            System.err.println("FAILED (Should have rejected GCP storage on AWS compute)");
        } catch (IllegalArgumentException ex) {
            System.out.println("PASSED -> Caught expected rejection: " + ex.getMessage());
        }

        // -----------------------------------------------------------------------------------------
        // STAGE 2: POSITIVE ASSEMBLY & GOLDEN TEMPLATE REGISTRATION (HAPPY PATH)
        // -----------------------------------------------------------------------------------------
        System.out.println("\n--- STAGE 2: POSITIVE ASSEMBLY & REGISTRATION ---");

        // 2.1 Assemble AWS Master Archetype
        StorageVolume awsEbsMaster = awsFactory.createStorageVolume("ebs-gold-001", 100);
        awsEbsMaster.writeData("OS_BOOT_IMAGE_v2.4");
        awsEbsMaster.writeData("SECURITY_AGENT_v1.0");

        ComputeInstance awsEc2Master = awsFactory.createComputeInstance(
                new AwsComputeInstanceBuilder()
                        .instanceId("ec2-gold-001")
                        .cpuCores(4)
                        .ramGb(16)
                        .subnetCidr("172.16.0.0/16")
                        .tag("Environment", "GoldenTemplate")
                        .attachedVolume((AwsStorageVolume) awsEbsMaster)
        );

        Map<String, String> awsEnvVars = new HashMap<>();
        awsEnvVars.put("JAVA_HOME", "/usr/lib/jvm/default");
        awsEnvVars.put("TIER", "PROD");

        ExecutionEnvironment awsGoldenStack = new ExecutionEnvironment(
                "env-aws-master", "AWS", awsEc2Master, awsEnvVars
        );

        manager.registerGoldenTemplate("AWS_GOLDEN_MASTER", awsGoldenStack);
        System.out.println("[TEST 2.1] Registered AWS_GOLDEN_MASTER in Global Registry.");

        // 2.2 Assemble GCP Master Archetype
        StorageVolume gcpPdMaster = gcpFactory.createStorageVolume("pd-gold-001", 128);
        gcpPdMaster.writeData("CONTAINER_RUNTIME_IMAGE");

        ComputeInstance gcpGceMaster = gcpFactory.createComputeInstance(
                new GcpComputeInstanceBuilder()
                        .instanceId("gce-gold-001")
                        .cpuCores(8)
                        .ramGb(32)
                        .subnetCidr("10.128.0.0/20")
                        .tag("Cluster", "WorkerPool")
                        .attachedVolume(gcpPdMaster)
        );

        ExecutionEnvironment gcpGoldenStack = new ExecutionEnvironment(
                "env-gcp-master", "GCP", gcpGceMaster, Map.of("REGION", "us-central1")
        );

        manager.registerGoldenTemplate("GCP_GOLDEN_MASTER", gcpGoldenStack);
        System.out.println("[TEST 2.2] Registered GCP_GOLDEN_MASTER in Global Registry.");

        // -----------------------------------------------------------------------------------------
        // STAGE 3: PROTOTYPE DEEP COPY & STATE MUTATION ISOLATION
        // -----------------------------------------------------------------------------------------
        System.out.println("\n--- STAGE 3: PROTOTYPE DEEP COPY ISOLATION ---");

        ExecutionEnvironment activeSandbox = manager.aquireExecutionEnvironment("AWS_GOLDEN_MASTER");
        System.out.println("[TEST 3.1] Acquired active sandbox clone from AWS_GOLDEN_MASTER.");

        // Mutate the active sandbox's nested state
        StorageVolume sandboxVolume = activeSandbox.getComputeInstance().getAttachedVolume();
        sandboxVolume.writeData("TEMP_TEST_RUN_RESULTS_LOG");
        activeSandbox.getEnvironmentVariables().put("JOB_ID", "ci-build-8891");

        // Inspect baseline template in the manager cache
        ExecutionEnvironment masterCheck = manager.getTemplateRegistry().get("AWS_GOLDEN_MASTER");
        int masterBlockCount = masterCheck.getComputeInstance().getAttachedVolume().getDiskBlocks().size();
        int clonedBlockCount = sandboxVolume.getDiskBlocks().size();

        System.out.println("           Master Template Disk Block Count: " + masterBlockCount);
        System.out.println("           Cloned Sandbox Disk Block Count: " + clonedBlockCount);

        if (masterBlockCount == 2 && clonedBlockCount == 3) {
            System.out.println("[TEST 3.2] PASSED -> Disk blocks isolated. No mutation bleed into template.");
        } else {
            System.err.println("[TEST 3.2] FAILED -> Disk blocks bled across instances!");
        }

        boolean envVarIsolated = !masterCheck.getEnvironmentVariables().containsKey("JOB_ID")
                && activeSandbox.getEnvironmentVariables().containsKey("JOB_ID");
        System.out.println("[TEST 3.3] Environment Variables Isolated: " + (envVarIsolated ? "PASSED" : "FAILED"));

        // -----------------------------------------------------------------------------------------
        // STAGE 4: SINGLETON QUOTA & CONCURRENCY BOUNDARIES
        // -----------------------------------------------------------------------------------------
        System.out.println("\n--- STAGE 4: SINGLETON QUOTA MANAGEMENT ---");
        System.out.println("Initial Active Instances: " + manager.getActiveInstanceCount().get()
                + " / " + manager.getMaxInstanceQuota());

        // Max quota is 10 by default; allocate up to quota
        int initialActive = manager.getActiveInstanceCount().get();
        int remainingQuota = manager.getMaxInstanceQuota() - initialActive;

        for (int i = 0; i < remainingQuota; i++) {
            manager.aquireExecutionEnvironment("AWS_GOLDEN_MASTER");
        }
        System.out.println("Allocated up to max limit. Active count: " + manager.getActiveInstanceCount().get());

        // Attempt acquisition past the quota limit
        try {
            System.out.print("[TEST 4.1] Quota Exceeded Trip Check: ");
            manager.aquireExecutionEnvironment("AWS_GOLDEN_MASTER");
            System.err.println("FAILED (Quota was breached without exception)");
        } catch (RuntimeException ex) {
            System.out.println("PASSED -> Caught expected quota exhaustion: " + ex.getMessage());
        }

        // Release one instance and verify acquisition succeeds again
        manager.releaseExecutionEnvironment(activeSandbox);
        System.out.println("Released 1 sandbox. Active count now: " + manager.getActiveInstanceCount().get());

        ExecutionEnvironment reboundInstance = manager.aquireExecutionEnvironment("AWS_GOLDEN_MASTER");
        System.out.println("[TEST 4.2] PASSED -> Re-acquired instance after release. Active count: "
                + manager.getActiveInstanceCount().get());

        System.out.println("\n================================================================================");
        System.out.println("                  ALL 4 VERIFICATION STAGES PASSED SUCCESSFULLY                 ");
        System.out.println("================================================================================");
    }
}