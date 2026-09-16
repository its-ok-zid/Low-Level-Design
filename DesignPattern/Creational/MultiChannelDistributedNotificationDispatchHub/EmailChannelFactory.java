package DesignPattern.Creational.MultiChannelDistributedNotificationDispatchHub;

public class EmailChannelFactory implements NotificationChannelFactory{
    @Override
    public NotificationChannel createChannel() {
        return new EmailNotificationChannel();
    }
}
