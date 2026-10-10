package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory;

import java.util.ArrayList;
import java.util.List;

public class GcpStorageVolume implements StorageVolume {
    private final String volumeId;
    private final int capacityGb;
    private final String diskType;
    private List<String> diskBlocks;

    public GcpStorageVolume(String volumeId, int capacityGb, String diskType, List<String> diskBlocks) {
        this.volumeId = volumeId;
        this.capacityGb = capacityGb;
        this.diskType = diskType;
        this.diskBlocks = diskBlocks;
    }

    public GcpStorageVolume(GcpStorageVolume source, boolean deepCopy) {
        this.volumeId = source.volumeId;
        this.capacityGb = source.capacityGb;
        this.diskType = source.diskType;
        if (deepCopy) {
            this.diskBlocks = (source.diskBlocks != null) ? new ArrayList<>(source.diskBlocks.stream().toList()) : new ArrayList<>();
        } else {
            this.diskBlocks = source.diskBlocks;
        }
    }

    @Override
    public String getVolumeId() {
        return volumeId;
    }

    @Override
    public int getCapacityGb() {
        return capacityGb;
    }

    @Override
    public String getStorageType() {
        return diskType;
    }

    @Override
    public List<String> getDiskBlocks() {
        return diskBlocks;
    }

    @Override
    public void writeData(String block) {
        if (diskBlocks.size() < capacityGb) {
            diskBlocks.add(block);
        } else {
            throw new IllegalStateException("Storage volume is full.");
        }
    }

    @Override
    public StorageVolume shallowCopy() {
        return new GcpStorageVolume(this, false);
    }

    @Override
    public StorageVolume deepCopy() {
        return new GcpStorageVolume(this, true);
    }
}