package DesignPattern.Creational.prototype.VirtualMachineImageStorageVolumeCloner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VmProvisioningApplication {
    public static void main(String[] args) {

        // 1. Create Golden Master VM (Original Prototype)
        List<String> masterBlocks = new ArrayList<>(Arrays.asList("BOOT_SECTOR", "OS_KERNEL", "APP_DATA"));
        StorageVolume rootVol = new StorageVolume("vol-001", 100, "SSD_PROVISIONED", masterBlocks);
        NetworkInterface primaryNic = new NetworkInterface("00:1A:2B:3C:4D:5E", "10.0.0.15", "subnet-prod-01");

        Map<String, String> vmTags = new HashMap<>();
        vmTags.put("Environment", "Production");
        vmTags.put("Tier", "Database");

        VirtualMachine goldenMasterVm = new VirtualMachine(
                "vm-i-001",
                "Amazon Linux 2023",
                8,
                32,
                rootVol,
                primaryNic,
                vmTags
        );

        System.out.println("=================================================================");
        System.out.println("1. ORIGINAL GOLDEN MASTER VM CREATED");
        System.out.println("=================================================================");
        printVmDetails("Golden Master", goldenMasterVm);

        // 2. Spawn a VM via SHALLOW COPY
        System.out.println("\n=================================================================");
        System.out.println("2. SHALLOW COPY CLONE & MUTATION TEST");
        System.out.println("=================================================================");
        VirtualMachine shallowVm = goldenMasterVm.shallowCopy();
        shallowVm.setInstanceId("vm-i-002");
        shallowVm.setOsName("Ubuntu 24.04 (Modified)");

        // Mutating shallow copy's fileSystemBlocks directly impacts the original
        System.out.println("--> Action: Adding 'MALICIOUS_LOG_BLOCK' to shallowVm's volume blocks...");
        shallowVm.getPrimaryVolume().getFileSystemBlocks().add("MALICIOUS_LOG_BLOCK");

        printVmDetails("Golden Master (Impacted by Shallow Mutation)", goldenMasterVm);
        printVmDetails("Shallow Clone", shallowVm);

        // 3. Spawn a VM via DEEP COPY
        System.out.println("\n=================================================================");
        System.out.println("3. DEEP COPY CLONE & MUTATION TEST");
        System.out.println("=================================================================");
        VirtualMachine deepVm = goldenMasterVm.deepCopy();
        deepVm.setInstanceId("vm-i-003");
        deepVm.setOsName("RHEL 9");

        // Mutating deep copy's volume blocks, tags, and assigning a new NIC
        System.out.println("--> Action: Modifying deepVm tags, blocks, and network interface...");
        deepVm.getPrimaryVolume().getFileSystemBlocks().add("INDEPENDENT_STAGING_BLOCK");
        deepVm.getTags().put("Environment", "Staging");
        deepVm.setNetworkInterface(new NetworkInterface("00:1A:2B:3C:99:99", "192.168.1.100", "subnet-stage-02"));

        printVmDetails("Golden Master (Remains Untouched by Deep Clone)", goldenMasterVm);
        printVmDetails("Deep Clone", deepVm);

        // 4. Test via VmTemplateRegistry
        System.out.println("\n=================================================================");
        System.out.println("4. TESTING TEMPLATE REGISTRY");
        System.out.println("=================================================================");
        VmTemplateRegistry registry = new VmTemplateRegistry();
        registry.registerTemplate("DB_GOLD_MASTER", goldenMasterVm);

        VirtualMachine spawnedFromRegistry = registry.getTemplate("DB_GOLD_MASTER");
        spawnedFromRegistry.setInstanceId("vm-i-registry-004");
        printVmDetails("Spawned from Registry (Deep Copy)", spawnedFromRegistry);
    }

    private static void printVmDetails(String label, VirtualMachine vm) {
        System.out.printf("[%s]%n", label);
        System.out.printf("  VM Instance: ID=%s, OS=%s, Cores=%d, RAM=%dGB%n",
                vm.getInstanceId(), vm.getOsName(), vm.getCpuCores(), vm.getRamGb());
        System.out.printf("  Storage Volume (Memory Hash: @%h): ID=%s, Type=%s, Blocks=%s%n",
                vm.getPrimaryVolume(), vm.getPrimaryVolume().getVolumeId(),
                vm.getPrimaryVolume().getStorageType(), vm.getPrimaryVolume().getFileSystemBlocks());
        System.out.printf("  Tags (Memory Hash: @%h): %s%n%n",
                vm.getTags(), vm.getTags());
    }
}