package HotelRoomBookingSystem;

public abstract class AbstractRoom implements Room {
    private final String roomNumber;
    private final double baseNightlyRate;
    private boolean isBooked;

    public AbstractRoom(String roomNumber, double baseNightlyRate) {
        this.roomNumber = roomNumber;
        this.baseNightlyRate = baseNightlyRate;
        this.isBooked = false;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public double getBaseNightlyRate() {
        return baseNightlyRate;
    }

    @Override
    public boolean bookRoom() {
        if (isBooked) {
            System.out.println("Room " + roomNumber + " is already booked.");
            return false;
        }
        isBooked = true;
        System.out.println("The room " + roomNumber + " booked Successfully.");
        return true;
    }

    @Override
    public boolean cancelBooking() {
        if (!isBooked) {
            System.out.println("Room " + roomNumber + " is not currently booked.");
            return false;
        }
        isBooked = false;
        System.out.println("Booking for room " + roomNumber + " cancelled successfully.");
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[Room: " + roomNumber + "]";
    }

    @Override
    public abstract double calculateTotalBill(int nights);
}
