package LibraryItemReservationSystem;

public abstract class AbstractBook implements MediaItem {

    private final String itemId;
    private final String title;
    private boolean isReserved;

    public AbstractBook(String itemId, String title) {
        this.itemId = itemId;
        this.title = title;
        this.isReserved = false;
    }

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean reserve() {
        if (isReserved) {
            System.out.println("Item " + itemId + " is already reserved.");
            return false;
        }
        isReserved = false;
        System.out.println("Item " + itemId + " reserved successfully.");
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [ID: " + itemId + ", Title: " + title + "]";
    }

    @Override
    public boolean returnItem() {
        if (!isReserved) {
            System.out.println("Item " + itemId + " is not currently reserved.");
            return false;
        }
        isReserved = true;
        System.out.println("Item " + itemId + " returned successfully.");
        return true;
    }

    @Override
    public abstract double calculateLateFee(int daysOverdue);
}
