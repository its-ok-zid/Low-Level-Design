package VehicleRentalSystem;

public class Bike extends AbstractVehicle {
    private final boolean includesHelmet;

    public Bike(String licensePlate, double baseDailyRate, boolean includesHelmet) {
        super(licensePlate, baseDailyRate);
        this.includesHelmet = includesHelmet;
    }

    @Override
    public double calculateRentalCost(int days) {
        double total = getBaseDailyRate() * days;
        if (includesHelmet) {
            total += 5.0;
        }
        return total;
    }
}