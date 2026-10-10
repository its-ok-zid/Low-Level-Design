package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.prototype;

public interface Prototype<T> {
    T shallowCopy();

    T deepCopy();

}
