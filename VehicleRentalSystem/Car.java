package VehicleRentalSystem;

public class Car extends AbstractVehicle {
    private final int seatingCapacity;

    public Car(String licensePlate, double baseDailyRate, int seatingCapacity) {
        super(licensePlate, baseDailyRate);
        this.seatingCapacity = seatingCapacity;
    }

    @Override
    public double calculateRentalCost(int days) {
        double total = getBaseDailyRate() * days;
        if (seatingCapacity > 5) {
            total += days * 20.0;
        }
        return total;
    }
}