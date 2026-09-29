package DesignPattern.Creational.bonus.MultiChannelDistributedNotificationDispatchHub;

public class SmsChannelFactory implements NotificationChannelFactory{
    @Override
    public NotificationChannel createChannel() {
        return new SmsNotificationChannel();
    }
}
