package MultiChannelOrderFulfillmentPaymentSystem;

public class WhatsAppNotification implements NotificationStrategy {
    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("Sending WhatsApp message to " + recipient + ": " + message);
    }
}