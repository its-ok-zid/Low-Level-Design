package MultiChannelNotificationEngine;

import java.util.ArrayList;
import java.util.List;

public class NotificationApplication {
    public static void main(String[] args) {
        EmailChannel emailChannel = new EmailChannel("zid@gmail.com", "Google Hiring", "This is my Resume for the SDE-II role in Google");
        SmsChannel smsChannel = new SmsChannel("9876543210", "This is a reminder for your application in Google");

        NotificationService notificationService = new NotificationService();

        List<Channel> channels = new ArrayList<>();
        channels.add(emailChannel);
        channels.add(smsChannel);

        notificationService.sendNotification(emailChannel);
        notificationService.broadcast(channels);

    }
}
