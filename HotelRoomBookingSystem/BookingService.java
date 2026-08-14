package HotelRoomBookingSystem;

import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private final List<Room> roomList = new ArrayList<>();

    public void addRoom(Room room) {
        if (room != null) roomList.add(room);
    }

    public void processBooking(Room room, int nights) {
        if (room == null) return;

        if (room.bookRoom()) {
            double bills = room.calculateTotalBill(nights);
            System.out.println("The total bills for " + room + " for " + nights + " nights is: $" + bills + "\n");
        }
    }
}

