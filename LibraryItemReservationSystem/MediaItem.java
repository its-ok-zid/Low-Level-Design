package LibraryItemReservationSystem;

public interface MediaItem {

    boolean reserve();
    boolean returnItem();
    double calculateLateFee(int daysOverdue);
}
