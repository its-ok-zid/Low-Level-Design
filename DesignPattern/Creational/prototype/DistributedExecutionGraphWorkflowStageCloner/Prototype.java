package DesignPattern.Creational.prototype.DistributedExecutionGraphWorkflowStageCloner;

public interface Prototype<T> {
    T shallowCopy();

    T deepCopy();
}
