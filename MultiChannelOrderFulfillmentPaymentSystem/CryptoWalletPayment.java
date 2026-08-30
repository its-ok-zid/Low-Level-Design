package MultiChannelOrderFulfillmentPaymentSystem;

public class CryptoWalletPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid payment amount.");
            return false;
        }
        double finalAmount = amount + 5.00; // $5 flat gas fee
        System.out.println("Processing crypto wallet payment of $" + finalAmount + " (including $5.00 network gas fee).");
        return true;
    }
}