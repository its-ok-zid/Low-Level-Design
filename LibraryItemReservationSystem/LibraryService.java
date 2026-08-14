package LibraryItemReservationSystem;

import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private List<MediaItem> itemList = new ArrayList<>();

    void processReservation(MediaItem item) {
        if (item == null) return;

        if (item.reserve()) {
            System.out.println("The item has been reserved successfully.\n");
        }
    }

    void processReturn(MediaItem item, int daysOverdue) {
        if (item == null) return;
        if (item.returnItem()) {
            double lateFee = item.calculateLateFee(daysOverdue);
            System.out.println("The item has been returned successfully. Late fee: $" + lateFee + "\n");
        }
    }
}
