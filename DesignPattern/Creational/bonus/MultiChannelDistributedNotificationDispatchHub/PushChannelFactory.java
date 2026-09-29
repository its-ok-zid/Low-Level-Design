package DesignPattern.Creational.bonus.MultiChannelDistributedNotificationDispatchHub;

public class PushChannelFactory implements NotificationChannelFactory{
    @Override
    public NotificationChannel createChannel() {
        return new PushNotificationChannel();
    }
}
