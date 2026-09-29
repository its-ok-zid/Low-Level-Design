package DesignPattern.Creational.bonus.MultiChannelDistributedNotificationDispatchHub;

public interface NotificationChannel {
    boolean send(String recipient, String message);
}
