package DesignPattern.Creational.MultiChannelDistributedNotificationDispatchHub;

public class SmsNotificationChannel implements NotificationChannel {
    @Override
    public boolean send(String recipient, String message) {
        if (recipient == null || !recipient.matches("\\+?[1-9]\\d{1,14}") || message == null || message.isEmpty()) {
            System.out.println("Invalid recipient or message for SMS.");
            return false;
        }

        if (message.length() > 160) {
            System.out.println("SMS message exceeds 160 characters. Cannot send.");
            return false;
        }
        System.out.println("[SMS Send] to " + recipient + " | Body: " + message);
        return true;
    }
}
