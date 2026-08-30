package MultiChannelOrderFulfillmentPaymentSystem;

public interface NotificationStrategy {
    void sendNotification(String recipient, String message);
}