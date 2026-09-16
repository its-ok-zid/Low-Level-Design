package DesignPattern.Creational.MultiChannelDistributedNotificationDispatchHub;

public class SmsChannelFactory implements NotificationChannelFactory{
    @Override
    public NotificationChannel createChannel() {
        return new SmsNotificationChannel();
    }
}
