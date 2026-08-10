package MultiChannelNotificationEngine;

import java.util.List;

public class NotificationService {

    void sendNotification(Channel channel) {
        channel.send();
    }

    void broadcast(List<Channel> channels) {
        for (Channel channel : channels) {
            System.out.println("Broadcasting " + channel + " channel");
        }
    }
}
