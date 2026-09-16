package DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner;

import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.factory.AwsResourceFactory;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.factory.AzureResourceFactory;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.factory.CloudResourceFactory;
import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.factory.GcpResourceFactory;

public class CloudApplication {
    public static void main(String[] args) {
        System.out.println("=== Provisioning AWS Stack ===");
        CloudResourceFactory awsFactory = new AwsResourceFactory();
        CloudProvisioningService awsService = new CloudProvisioningService(awsFactory);
        awsService.deployCompleteStack("production-aws", "us-east-1");
        awsService.uploadDataToStorage("app-config.json", "{}".getBytes());
        awsService.getDatabaseConnectionString();
        awsService.terminateCompleteStack("production-aws", "us-east-1");

        System.out.println("\n=== Provisioning Azure Stack ===");
        CloudResourceFactory azureFactory = new AzureResourceFactory();
        CloudProvisioningService azureService = new CloudProvisioningService(azureFactory);
        azureService.deployCompleteStack("enterprise-azure", "eastus2");
        azureService.uploadDataToStorage("schema.sql", "CREATE TABLE users;".getBytes());
        azureService.getDatabaseConnectionString();
        azureService.terminateCompleteStack("enterprise-azure", "eastus2");

        System.out.println("\n=== Provisioning GCP Stack ===");
        CloudResourceFactory gcpFactory = new GcpResourceFactory();
        CloudProvisioningService gcpService = new CloudProvisioningService(gcpFactory);
        gcpService.deployCompleteStack("analytics-gcp", "us-central1");
        gcpService.uploadDataToStorage("model.bin", new byte[]{0x10, 0x20});
        gcpService.getDatabaseConnectionString();
        gcpService.terminateCompleteStack("analytics-gcp", "us-central1");
    }
}