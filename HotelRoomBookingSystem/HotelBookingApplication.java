package HotelRoomBookingSystem;

public class HotelBookingApplication {
    public static void main(String[] args) {
        StandardRoom standardRoom = new StandardRoom("A1102", 10.0, 2);
        SuiteRoom suiteRoom = new SuiteRoom("Z090", 20.0, true);
        BookingService bookingService = new BookingService();

        bookingService.processBooking(standardRoom, 7);
        bookingService.processBooking(suiteRoom,10);
    }
}
