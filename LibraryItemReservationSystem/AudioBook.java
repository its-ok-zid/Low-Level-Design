package LibraryItemReservationSystem;

public class AudioBook extends AbstractBook {

    private final double durationInMinutes;

    public AudioBook(String itemId, String title, double durationInMinutes) {
        super(itemId, title);
        this.durationInMinutes = durationInMinutes;
    }


    @Override
    public double calculateLateFee(int daysOverdue) {
        if (daysOverdue <= 0) return 0.0;
        double baseLateFee = 1.00 * daysOverdue;

        if (durationInMinutes > 300) {
            return baseLateFee + 5.00;
        } else {
            return baseLateFee + 0.0;
        }
    }
}
