package HotelRoomBookingSystem;

public class StandardRoom extends AbstractRoom {
    private final int numberOfBeds;


    public StandardRoom(String roomNumber, double baseNightlyRate, int numberOfBeds) {
        super(roomNumber, baseNightlyRate);
        this.numberOfBeds = numberOfBeds;
    }

    @Override
    public double calculateTotalBill(int nights) {
        double baseCost = getBaseNightlyRate() * nights;
        double surcharge = (numberOfBeds > 2) ? (15.0 * nights) : 0.0;
        return baseCost + surcharge;
    }
}
