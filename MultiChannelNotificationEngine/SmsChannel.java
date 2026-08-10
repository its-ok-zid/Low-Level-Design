package MultiChannelNotificationEngine;

public class SmsChannel implements Channel {
    String phoneNumber;
    String body;

    public SmsChannel(String phoneNumber, String body) {
        this.phoneNumber = phoneNumber;
        this.body = body;
    }

    @Override
    public void send() {
        System.out.println("This is a message from the SMS channel having : \nPhoneNumber: "+phoneNumber+"\nBody: "+body);
    }
}
