package DesignPattern.Creational.bonus.MultiContainerDeploymentDescriptorBuilder;

public class VolumeSpecBuilder {
    private final PodDeploymentSpecBuilder podDeploymentSpecBuilder;
    String volumeName;
    String mountPath;
    boolean readOnly = false;

    public VolumeSpecBuilder(PodDeploymentSpecBuilder parentBuilder) {
        this.podDeploymentSpecBuilder = parentBuilder;
    }

    public VolumeSpecBuilder name(String volumeName) {
        this.volumeName = volumeName;
        return this;
    }

    public VolumeSpecBuilder mountPath(String mountPath) {
        this.mountPath = mountPath;
        return this;
    }

    public VolumeSpecBuilder readOnly(boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    public PodDeploymentSpecBuilder endVolume() {
        if (volumeName == null || volumeName.isBlank()) {
            throw new IllegalStateException("Volume name is required.");
        }
        if (mountPath == null || mountPath.isBlank() || !mountPath.startsWith("/")) {
            throw new IllegalStateException("mountPath is required and must start with '/'");
        }

        VolumeSpec volumeSpec = new VolumeSpec(this);
        this.podDeploymentSpecBuilder.volumes.add(volumeSpec);
        return this.podDeploymentSpecBuilder;
    }
}