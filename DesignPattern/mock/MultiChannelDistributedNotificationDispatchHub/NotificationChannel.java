package DesignPattern.mock.MultiChannelDistributedNotificationDispatchHub;

public interface NotificationChannel {
    boolean send(String recipient, String message);
}
