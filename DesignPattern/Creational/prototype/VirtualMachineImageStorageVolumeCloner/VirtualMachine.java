package DesignPattern.Creational.prototype.VirtualMachineImageStorageVolumeCloner;

import java.util.HashMap;
import java.util.Map;

public class VirtualMachine implements Prototype<VirtualMachine> {
    private String instanceId;
    private String osName;
    private int cpuCores;
    private int ramGb;
    private StorageVolume primaryVolume;
    private NetworkInterface networkInterface;
    private Map<String, String> tags;

    public VirtualMachine(String instanceId, String osName, int cpuCores, int ramGb,
                          StorageVolume primaryVolume, NetworkInterface networkInterface,
                          Map<String, String> tags) {
        this.instanceId = instanceId;
        this.osName = osName;
        this.cpuCores = cpuCores;
        this.ramGb = ramGb;
        this.primaryVolume = primaryVolume;
        this.networkInterface = networkInterface;
        this.tags = (tags != null) ? new HashMap<>(tags) : new HashMap<>();
    }

    public VirtualMachine(VirtualMachine source) {
        this(source, false);
    }

    public VirtualMachine(VirtualMachine source, boolean deepCopy) {
        this.instanceId = source.instanceId;
        this.osName = source.osName;
        this.cpuCores = source.cpuCores;
        this.ramGb = source.ramGb;
        if (deepCopy) {
            this.primaryVolume = (source.primaryVolume != null) ? source.primaryVolume.deepCopy() : null;
            this.networkInterface = (source.networkInterface != null) ? source.networkInterface.deepCopy() : null;
            this.tags = (source.tags != null) ? new HashMap<>(source.tags) : new HashMap<>();
        } else {
            this.primaryVolume = source.primaryVolume;
            this.networkInterface = source.networkInterface;
            this.tags = source.tags;
        }
    }

    @Override
    public VirtualMachine shallowCopy() {
        return new VirtualMachine(this, false);
    }

    @Override
    public VirtualMachine deepCopy() {
        return new VirtualMachine(this, true);
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public int getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(int cpuCores) {
        this.cpuCores = cpuCores;
    }

    public int getRamGb() {
        return ramGb;
    }

    public void setRamGb(int ramGb) {
        this.ramGb = ramGb;
    }

    public StorageVolume getPrimaryVolume() {
        return primaryVolume;
    }

    public void setPrimaryVolume(StorageVolume primaryVolume) {
        this.primaryVolume = primaryVolume;
    }

    public NetworkInterface getNetworkInterface() {
        return networkInterface;
    }

    public void setNetworkInterface(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }
}