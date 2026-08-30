package MultiChannelOrderFulfillmentPaymentSystem;

public class UpiPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount for UPI payment.");
            return false;
        }
        System.out.println("Processing UPI payment of amount: $" + amount);
        return true;
    }
}