package DesignPattern.Creational.mock.MultiChannelDistributedNotificationDispatchHub;

public interface NotificationChannel {
    boolean send(String recipient, String message);
}
