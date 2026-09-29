package DesignPattern.Creational.bonus.MultiContainerDeploymentDescriptorBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PodDeploymentSpecBuilder {
    String podName;
    String namespace = "default";
    RestartPolicy restartPolicy = RestartPolicy.ALWAYS;
    final List<ContainerSpec> containers = new ArrayList<>();
    final List<VolumeSpec> volumes = new ArrayList<>();
    final Map<String, String> labels = new HashMap<>();

    public ContainerSpecBuilder container() {
        return new ContainerSpecBuilder(this);
    }

    public VolumeSpecBuilder volume() {
        return new VolumeSpecBuilder(this);
    }

    public PodDeploymentSpecBuilder name(String podName) {
        this.podName = podName;
        return this;
    }

    public PodDeploymentSpecBuilder namespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public PodDeploymentSpecBuilder restartPolicy(RestartPolicy restartPolicy) {
        this.restartPolicy = restartPolicy;
        return this;
    }

    public PodDeploymentSpecBuilder label(String key, String value) {
        if (key != null && value != null) {
            this.labels.put(key.trim(), value.trim());
        }
        return this;
    }

    public PodDeploymentSpec build() {
        if (podName == null || podName.isBlank()) {
            throw new IllegalStateException("Pod name is required.");
        }
        if (containers.isEmpty()) {
            throw new IllegalStateException("Pod must have at least one container.");
        }

        // Cross-container invariant validations
        Set<String> containerNames = new HashSet<>();
        Set<Integer> boundPorts = new HashSet<>();

        for (ContainerSpec c : containers) {
            if (!containerNames.add(c.getName())) {
                throw new IllegalStateException("Duplicate container name detected: " + c.getName());
            }
            if (c.getContainerPort() > 0 && !boundPorts.add(c.getContainerPort())) {
                throw new IllegalStateException("Port collision detected on container port: " + c.getContainerPort());
            }
        }

        return new PodDeploymentSpec(this);
    }
}