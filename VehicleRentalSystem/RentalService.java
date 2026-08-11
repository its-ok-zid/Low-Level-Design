package VehicleRentalSystem;

import java.util.ArrayList;
import java.util.List;

public class RentalService {
    private final List<Vehicle> vehicles = new ArrayList<>();

    public void addVehicle(Vehicle vehicle) {
        if (vehicle != null) {
            vehicles.add(vehicle);
        }
    }

    public void processRental(Vehicle vehicle, int days) {
        if (vehicle == null) {
            System.out.println("Invalid vehicle request.");
            return;
        }

        if (vehicle.rent()) {
            double cost = vehicle.calculateRentalCost(days);
            System.out.println("Total rental cost for " + vehicle + " for " + days + " days is: $" + cost + "\n");
        }
    }
}