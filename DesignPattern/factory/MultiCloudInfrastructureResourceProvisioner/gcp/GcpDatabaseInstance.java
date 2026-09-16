package DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.gcp;

import DesignPattern.factory.MultiCloudInfrastructureResourceProvisioner.product.DatabaseInstance;

public class GcpDatabaseInstance implements DatabaseInstance {
    private String dbName;

    @Override
    public void deploy(String dbName, double storageGb) {
        this.dbName = dbName;
        System.out.println("Deploying GCP Database Instance: " + dbName + " with storage: " + storageGb + " GB");
    }

    @Override
    public String getConnectionString() {
        String connectionString = "jdbc:mysql://gcp-database-instance/" + dbName;
        return connectionString;
    }
}
