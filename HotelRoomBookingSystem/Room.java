package HotelRoomBookingSystem;

public interface Room {
   boolean bookRoom();
   boolean cancelBooking();
   double calculateTotalBill(int nights);
}
