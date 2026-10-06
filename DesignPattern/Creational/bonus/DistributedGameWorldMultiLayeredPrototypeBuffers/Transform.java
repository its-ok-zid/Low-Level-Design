package DesignPattern.Creational.bonus.DistributedGameWorldMultiLayeredPrototypeBuffers;

public class Transform implements Prototype<Transform> {
    private double xCoordinate;
    private double yCoordinate;
    private double zCoordinate;
    private double pitch;
    private double yaw;
    private double roll;

    public Transform(TransformBuilder builder) {
        this.xCoordinate = builder.xCoordinate;
        this.yCoordinate = builder.yCoordinate;
        this.zCoordinate = builder.zCoordinate;
        this.pitch = builder.pitch;
        this.yaw = builder.yaw;
        this.roll = builder.roll;
    }

    public Transform(Transform source) {
        this.xCoordinate = source.xCoordinate;
        this.yCoordinate = source.yCoordinate;
        this.zCoordinate = source.zCoordinate;
        this.pitch = source.pitch;
        this.yaw = source.yaw;
        this.roll = source.roll;
    }

    public static TransformBuilder builder() {
        return new TransformBuilder();
    }

    public double getXCoordinate() { return xCoordinate; }
    public void setXCoordinate(double xCoordinate) { this.xCoordinate = xCoordinate; }
    public double getYCoordinate() { return yCoordinate; }
    public void setYCoordinate(double yCoordinate) { this.yCoordinate = yCoordinate; }
    public double getZCoordinate() { return zCoordinate; }
    public void setZCoordinate(double zCoordinate) { this.zCoordinate = zCoordinate; }
    public double getPitch() { return pitch; }
    public void setPitch(double pitch) { this.pitch = pitch; }
    public double getYaw() { return yaw; }
    public void setYaw(double yaw) { this.yaw = yaw; }
    public double getRoll() { return roll; }
    public void setRoll(double roll) { this.roll = roll; }

    @Override
    public Transform shallowCopy() {
        return new Transform(this);
    }

    @Override
    public Transform deepCopy() {
        return new Transform(this);
    }
}