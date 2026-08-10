package MultiChannelNotificationEngine;

import java.util.List;

public class NotificationService {

    public void sendNotification(Channel channel) {
        if (channel != null) {
            channel.send();
        }
    }

    public void broadcast(List<Channel> channels) {
        if (channels == null || channels.isEmpty()) {
            return;
        }
        System.out.println("\n\nBROADCASTING CHANNELS: \n");

        for (Channel channel : channels)
            channel.send();
    }
}
