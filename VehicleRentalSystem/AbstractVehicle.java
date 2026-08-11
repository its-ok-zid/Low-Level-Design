package VehicleRentalSystem;

public abstract class AbstractVehicle implements Vehicle {
    private final String licensePlate;
    private final double baseDailyRate;
    private boolean isRented;

    public AbstractVehicle(String licensePlate, double baseDailyRate) {
        this.licensePlate = licensePlate;
        this.baseDailyRate = baseDailyRate;
        this.isRented = false;
    }

    @Override
    public boolean rent() {
        if (isRented) {
            System.out.println("Vehicle " + licensePlate + " is already rented!");
            return false;
        }
        this.isRented = true;
        System.out.println("Vehicle " + licensePlate + " successfully rented.");
        return true;
    }

    @Override
    public boolean returnVehicle() {
        if (!isRented) {
            System.out.println("Vehicle " + licensePlate + " was not rented!");
            return false;
        }
        this.isRented = false;
        System.out.println("Vehicle " + licensePlate + " successfully returned.");
        return true;
    }

    @Override
    public boolean isRented() {
        return isRented;
    }

    @Override
    public String getLicensePlate() {
        return licensePlate;
    }

    public double getBaseDailyRate() {
        return baseDailyRate;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [Plate: " + licensePlate + "]";
    }
}