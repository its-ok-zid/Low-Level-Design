package MultiChannelOrderFulfillmentPaymentSystem;

public class SmsNotification implements NotificationStrategy {
    @Override
    public void sendNotification(String recipient, String message) {
        String boundedMessage = (message != null && message.length() > 160)
                ? message.substring(0, 157) + "..."
                : message;
        System.out.println("Sending SMS to " + recipient + ": " + boundedMessage);
    }
}