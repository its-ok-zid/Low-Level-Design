package DesignPattern.Creational.bonus.MultiChannelDistributedNotificationDispatchHub;

public class EmailChannelFactory implements NotificationChannelFactory{
    @Override
    public NotificationChannel createChannel() {
        return new EmailNotificationChannel();
    }
}
