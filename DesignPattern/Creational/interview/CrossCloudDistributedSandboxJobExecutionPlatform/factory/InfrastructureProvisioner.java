package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory;

public class InfrastructureProvisioner {
    public CloudInfrastructureFactory getFactory(String providerType) {
        if (providerType.equalsIgnoreCase("AWS")) {
            return new AwsInfrastructureFactory();
        } else if (providerType.equalsIgnoreCase("GCP")) {
            return new GcpInfrastructureFactory();
        } else {
            throw new IllegalArgumentException("Unsupported cloud provider: " + providerType);
        }
    }
}
