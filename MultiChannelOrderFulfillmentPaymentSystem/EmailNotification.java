package MultiChannelOrderFulfillmentPaymentSystem;

public class EmailNotification implements NotificationStrategy {
    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("Sending email to " + recipient + ": " + message);
    }
}