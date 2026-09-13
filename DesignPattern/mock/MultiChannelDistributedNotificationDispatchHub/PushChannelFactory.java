package DesignPattern.mock.MultiChannelDistributedNotificationDispatchHub;

public class PushChannelFactory implements NotificationChannelFactory{
    @Override
    public NotificationChannel createChannel() {
        return new PushNotificationChannel();
    }
}
