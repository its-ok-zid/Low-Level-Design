package HotelRoomBookingSystem;

public class SuiteRoom extends AbstractRoom {
    private final boolean hasJacuzzi;

    public SuiteRoom(String roomNumber, double baseNightlyRate, boolean hasJacuzzi) {
        super(roomNumber, baseNightlyRate);
        this.hasJacuzzi = hasJacuzzi;
    }

    @Override
    public double calculateTotalBill(int nights) {
        return getBaseNightlyRate() * nights + (hasJacuzzi ? 50.0 : 0.0);
    }
}
