package MultiChannelNotificationEngine;

public class EmailChannel implements Channel {
    private final String email;
    private final String subject;
    private final String body;

    public EmailChannel(String email, String subject, String body) {
        this.email = email;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public void send() {
        System.out.println("This is a message from the email channel having : \nEmail: " + email + "\nSubject: " + subject + "" +
                "\nBody: " + body);

    }
}
