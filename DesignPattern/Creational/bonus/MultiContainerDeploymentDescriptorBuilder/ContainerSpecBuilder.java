package DesignPattern.Creational.bonus.MultiContainerDeploymentDescriptorBuilder;

import java.util.HashMap;
import java.util.Map;

public class ContainerSpecBuilder {
    private final PodDeploymentSpecBuilder podDeploymentSpecBuilder;
    String name;
    String image;
    int containerPort;
    double cpuRequest;
    double cpuLimit;
    int memoryRequestMb;
    int memoryLimitMb;
    final Map<String, String> envVars = new HashMap<>();

    public ContainerSpecBuilder(PodDeploymentSpecBuilder parentBuilder) {
        this.podDeploymentSpecBuilder = parentBuilder;
    }

    public ContainerSpecBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ContainerSpecBuilder image(String image) {
        this.image = image;
        return this;
    }

    public ContainerSpecBuilder port(int port) {
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Container port must be between 1 and 65535.");
        }
        this.containerPort = port;
        return this;
    }

    public ContainerSpecBuilder resources(double cpuRequest, double cpuLimit, int memoryRequestMb, int memoryLimitMb) {
        this.cpuRequest = cpuRequest;
        this.cpuLimit = cpuLimit;
        this.memoryRequestMb = memoryRequestMb;
        this.memoryLimitMb = memoryLimitMb;
        return this;
    }

    public ContainerSpecBuilder env(String k, String v) {
        if (k != null && v != null) {
            this.envVars.put(k.trim(), v.trim());
        }
        return this;
    }

    public PodDeploymentSpecBuilder endContainer() {
        if (name == null || name.isBlank()) {
            throw new IllegalStateException("Container name is required.");
        }
        if (image == null || image.isBlank()) {
            throw new IllegalStateException("Container image is required.");
        }
        if (cpuLimit > 0 && cpuLimit < cpuRequest) {
            throw new IllegalArgumentException("cpuLimit cannot be less than cpuRequest.");
        }
        if (memoryLimitMb > 0 && memoryLimitMb < memoryRequestMb) {
            throw new IllegalArgumentException("memoryLimitMb cannot be less than memoryRequestMb.");
        }

        ContainerSpec containerSpec = new ContainerSpec(this);
        this.podDeploymentSpecBuilder.containers.add(containerSpec);
        return this.podDeploymentSpecBuilder;
    }
}