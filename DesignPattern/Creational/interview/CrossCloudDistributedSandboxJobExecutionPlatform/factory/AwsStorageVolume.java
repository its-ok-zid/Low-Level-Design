package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory;

import java.util.ArrayList;
import java.util.List;

public class AwsStorageVolume implements StorageVolume {
    private final String volumeId;
    private final int capacityGb;
    private final String volumeType;
    private List<String> diskBlocks;

    public AwsStorageVolume(String volumeId, int capacityGb, String volumeType, List<String> diskBlocks) {
        this.volumeId = volumeId;
        this.capacityGb = capacityGb;
        this.volumeType = volumeType;
        this.diskBlocks = diskBlocks;
    }

    public AwsStorageVolume(AwsStorageVolume source, boolean deepCopy) {
        this.volumeId = source.volumeId;
        this.capacityGb = source.capacityGb;
        this.volumeType = source.volumeType;
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
        return volumeType;
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
        return new AwsStorageVolume(this, false);
    }

    @Override
    public StorageVolume deepCopy() {
        return new AwsStorageVolume(this, true);
    }
}
