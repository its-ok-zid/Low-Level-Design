package DesignPattern.Creational.bonus.MultiContainerDeploymentDescriptorBuilder;

public class VolumeSpec {
    private final String volumeName;
    private final String mountPath;
    private boolean readOnly;

    public String getVolumeName() {
        return volumeName;
    }

    public String getMountPath() {
        return mountPath;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    VolumeSpec(VolumeSpecBuilder volumeSpecBuilder){
        this.volumeName=volumeSpecBuilder.volumeName;
        this.mountPath=volumeSpecBuilder.mountPath;
        this.readOnly=volumeSpecBuilder.readOnly;
    }
}
