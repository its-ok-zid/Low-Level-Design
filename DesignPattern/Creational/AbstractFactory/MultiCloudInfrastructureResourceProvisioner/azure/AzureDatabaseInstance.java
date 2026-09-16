package DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.azure;

import DesignPattern.Creational.AbstractFactory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;

public class AzureDatabaseInstance implements DatabaseInstance {

    private String dbName;

    @Override
    public void deploy(String dbName, double storageGb) {
        this.dbName = dbName;
        System.out.println("Deploying Azure Database Instance: " + dbName + " with storage: " + storageGb + " GB");
    }

    @Override
    public String getConnectionString() {
        String connectionString = "jdbc:mysql://azure-database-instance/" + dbName;
        return connectionString;
    }
}
