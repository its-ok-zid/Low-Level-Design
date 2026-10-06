package DesignPattern.Creational.bonus.DistributedGameWorldMultiLayeredPrototypeBuffers;

public class TransformBuilder {
    double xCoordinate;
    double yCoordinate;
    double zCoordinate;
    double pitch;
    double yaw;
    double roll;

    public TransformBuilder() {
    }

    public TransformBuilder xCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
        return this;
    }

    public TransformBuilder yCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
        return this;
    }

    public TransformBuilder zCoordinate(double zCoordinate) {
        this.zCoordinate = zCoordinate;
        return this;
    }

    public TransformBuilder pitch(double pitch) {
        this.pitch = pitch;
        return this;
    }

    public TransformBuilder yaw(double yaw) {
        this.yaw = yaw;
        return this;
    }

    public TransformBuilder roll(double roll) {
        this.roll = roll;
        return this;
    }

    public Transform build() {
        return new Transform(this);
    }
}