package LibraryItemReservationSystem;

public class Book extends AbstractBook {
    private final int pageCount;

    public Book(String itemId, String title, int pageCount) {
        super(itemId, title);
        this.pageCount = pageCount;
    }

    @Override
    public double calculateLateFee(int daysOverdue) {
        double baseLateFee = 0.50 * daysOverdue;
        if (pageCount > 500) {
            return baseLateFee + 2.00;
        } else {
            return baseLateFee + 0.0;
        }
    }
}
