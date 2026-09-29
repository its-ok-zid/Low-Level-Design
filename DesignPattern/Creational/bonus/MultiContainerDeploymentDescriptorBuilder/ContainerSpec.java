package DesignPattern.Creational.bonus.MultiContainerDeploymentDescriptorBuilder;

import java.util.Map;

public class ContainerSpec {
    private final String name;
    private final String image;
    private final int containerPort;
    private final double cpuRequest;
    private final double cpuLimit;
    private final int memoryRequestMb;
    private final int memoryLimitMb;
    private Map<String, String> envVars;

    ContainerSpec(ContainerSpecBuilder containerSpecBuilder) {
        this.name = containerSpecBuilder.name;
        this.image = containerSpecBuilder.image;
        this.containerPort = containerSpecBuilder.containerPort;
        this.cpuLimit = containerSpecBuilder.cpuLimit;
        this.cpuRequest = containerSpecBuilder.cpuRequest;
        this.memoryRequestMb = containerSpecBuilder.memoryRequestMb;
        this.memoryLimitMb = containerSpecBuilder.memoryLimitMb;
        this.envVars = Map.copyOf(containerSpecBuilder.envVars);
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getContainerPort() {
        return containerPort;
    }

    public double getCpuRequest() {
        return cpuRequest;
    }

    public double getCpuLimit() {
        return cpuLimit;
    }

    public int getMemoryRequestMb() {
        return memoryRequestMb;
    }

    public int getMemoryLimitMb() {
        return memoryLimitMb;
    }

    public Map<String, String> getEnvVars() {
        return envVars;
    }

}
