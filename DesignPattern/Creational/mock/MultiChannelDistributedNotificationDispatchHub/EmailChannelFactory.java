package DesignPattern.Creational.mock.MultiChannelDistributedNotificationDispatchHub;

public class EmailChannelFactory implements NotificationChannelFactory{
    @Override
    public NotificationChannel createChannel() {
        return new EmailNotificationChannel();
    }
}
