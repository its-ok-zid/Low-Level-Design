package DesignPattern.Creational.MultiChannelDistributedNotificationDispatchHub;

public interface NotificationChannel {
    boolean send(String recipient, String message);
}
