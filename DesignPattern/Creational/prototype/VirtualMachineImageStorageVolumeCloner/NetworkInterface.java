package DesignPattern.Creational.prototype.VirtualMachineImageStorageVolumeCloner;

public class NetworkInterface implements Prototype<NetworkInterface> {
    private final String macAddress;
    private final String ipAddress;
    private final String subnetId;

    public NetworkInterface(String macAddress, String ipAddress, String subnetId) {
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.subnetId = subnetId;
    }

    public NetworkInterface(NetworkInterface source) {
        this.macAddress = source.macAddress;
        this.ipAddress = source.ipAddress;
        this.subnetId = source.subnetId;
    }

    @Override
    public NetworkInterface shallowCopy() {
        return new NetworkInterface(this);
    }

    @Override
    public NetworkInterface deepCopy() {
        return new NetworkInterface(this);
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getSubnetId() {
        return subnetId;
    }
}