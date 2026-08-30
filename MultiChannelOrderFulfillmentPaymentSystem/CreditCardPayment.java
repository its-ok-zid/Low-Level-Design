package MultiChannelOrderFulfillmentPaymentSystem;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid payment amount.");
            return false;
        }
        double totalAmount = amount * 1.02; // 2% processing fee
        System.out.println("Processing credit card payment of $" + totalAmount + " (including 2% processing fee).");
        return true;
    }
}