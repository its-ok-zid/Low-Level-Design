package DesignPattern.Creational.prototype.VirtualMachineImageStorageVolumeCloner;

import java.util.ArrayList;
import java.util.List;

public class StorageVolume implements Prototype<StorageVolume> {
    private final String volumeId;
    private final int sizeGb;
    private final String storageType;
    private List<String> fileSystemBlocks;

    public StorageVolume(String volumeId, int sizeGb, String storageType, List<String> fileSystemBlocks) {
        this.volumeId = volumeId;
        this.sizeGb = sizeGb;
        this.storageType = storageType;
        this.fileSystemBlocks = fileSystemBlocks;
    }

    public StorageVolume(StorageVolume source) {
        this(source, false);
    }

    public StorageVolume(StorageVolume source, boolean deepCopy) {
        this.volumeId = source.volumeId;
        this.sizeGb = source.sizeGb;
        this.storageType = source.storageType;
        if (deepCopy) {
            this.fileSystemBlocks = (source.fileSystemBlocks != null)
                    ? new ArrayList<>(source.fileSystemBlocks)
                    : new ArrayList<>();
        } else {
            this.fileSystemBlocks = source.fileSystemBlocks;
        }
    }

    @Override
    public StorageVolume shallowCopy() {
        return new StorageVolume(this, false);
    }

    @Override
    public StorageVolume deepCopy() {
        return new StorageVolume(this, true);
    }

    public String getVolumeId() {
        return volumeId;
    }

    public int getSizeGb() {
        return sizeGb;
    }

    public String getStorageType() {
        return storageType;
    }

    public List<String> getFileSystemBlocks() {
        return fileSystemBlocks;
    }

    public void setFileSystemBlocks(List<String> fileSystemBlocks) {
        this.fileSystemBlocks = fileSystemBlocks;
    }
}