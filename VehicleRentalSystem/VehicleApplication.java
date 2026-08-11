package VehicleRentalSystem;

public class VehicleApplication {
    public static void main(String[] args) {
        Vehicle car = new Car("XPCF2209", 100.0, 7);
        Vehicle bike = new Bike("GFWGEW0", 25.0, true);

        RentalService rentalService = new RentalService();
        rentalService.addVehicle(car);
        rentalService.addVehicle(bike);

        // Process rentals
        rentalService.processRental(car, 10);
        rentalService.processRental(bike, 7);

        // Attempting to rent an already rented car
        rentalService.processRental(car, 2);
    }
}