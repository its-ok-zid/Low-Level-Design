package DesignPattern.Creational.MultiChannelDistributedNotificationDispatchHub;

public class EmailNotificationChannel implements NotificationChannel {
    @Override
    public boolean send(String recipient, String message) {
        if (recipient == null || !recipient.contains("@") || message == null || message.isEmpty()) {
            System.out.println("EmailNotificationChannel: Invalid recipient or message.");
            return false;
        }
        System.out.println("EmailNotificationChannel: Sending email to " + recipient + " with message: " + message);
        return true;
    }
}
