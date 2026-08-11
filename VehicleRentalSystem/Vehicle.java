package VehicleRentalSystem;
public interface Vehicle {
    double calculateRentalCost(int days);
    boolean rent();
    boolean returnVehicle();
    boolean isRented();
    String getLicensePlate();
}