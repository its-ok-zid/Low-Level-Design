package PaymentGateway;

import java.util.Scanner;

public class PaymentApplication {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProcessPayment processor = new ProcessPayment();

        System.out.println("Select Payment Type (1: Credit Card, 2: PayPal): ");
        int choice = sc.nextInt();

        System.out.println("Enter Amount: ");
        double amount = sc.nextDouble();

        Payment paymentMethod = null;

        if (choice == 1) {
            paymentMethod = new CreditCardPayment("Zidan Ali", 12345, "123454");
        } else if (choice == 2) {
            paymentMethod = new PayPalPayment("zidan@example.com", "securePassword");
        }

        // Orchestrator executes transaction via abstraction (DIP)
        processor.processTransaction(paymentMethod, amount);
    }
}
