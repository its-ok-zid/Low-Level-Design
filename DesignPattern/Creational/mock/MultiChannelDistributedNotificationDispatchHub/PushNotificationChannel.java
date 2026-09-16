package DesignPattern.Creational.mock.MultiChannelDistributedNotificationDispatchHub;

public class PushNotificationChannel implements NotificationChannel {
    @Override
    public boolean send(String recipient, String message) {
        if (recipient == null || recipient.length() < 10 || message == null || message.isEmpty()) {
            System.out.println("PushNotificationChannel: Invalid recipient or message.");
            return false;
        }
        System.out.println("PushNotificationChannel: Sending push notification to " + recipient + " with message: " + message);
        return true;
    }
}
