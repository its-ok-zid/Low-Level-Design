package LibraryItemReservationSystem;

public class LibraryItemApplication {
    public static void main(String[] args) {
        Book book = new Book("B001", "The Great Gatsby", 180);
        AudioBook audioBook = new AudioBook("A001", "1984", 360);

        System.out.println("Reserving Book: " + book.reserve());
        System.out.println("Reserving AudioBook: " + audioBook.reserve());

        System.out.println("Returning Book: " + book.returnItem());
        System.out.println("Returning AudioBook: " + audioBook.returnItem());

        int daysOverdue = 5;
        System.out.println("Late fee for Book (5 days overdue): $" + book.calculateLateFee(daysOverdue));
        System.out.println("Late fee for AudioBook (5 days overdue): $" + audioBook.calculateLateFee(daysOverdue));
    }
}
