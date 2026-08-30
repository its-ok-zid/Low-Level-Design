package MultiChannelOrderFulfillmentPaymentSystem;

public interface PaymentStrategy {
    boolean processPayment(double amount);
}