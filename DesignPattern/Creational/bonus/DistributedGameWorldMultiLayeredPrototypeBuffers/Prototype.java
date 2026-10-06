package DesignPattern.Creational.bonus.DistributedGameWorldMultiLayeredPrototypeBuffers;

public interface Prototype<T> {
    T shallowCopy();

    T deepCopy();
}
