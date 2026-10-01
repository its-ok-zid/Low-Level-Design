package DesignPattern.Creational.prototype.VirtualMachineImageStorageVolumeCloner;

public interface Prototype<T> {
    T shallowCopy();

    T deepCopy();
}
