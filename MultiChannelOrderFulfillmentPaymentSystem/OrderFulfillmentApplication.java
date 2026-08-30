package MultiChannelOrderFulfillmentPaymentSystem;

public class OrderFulfillmentApplication {
    public static void main(String[] args) {
        FulfillmentService fulfillmentService = new FulfillmentService();

        // 1. Order fulfilled via Credit Card & Email
        Order order1 = new Order("ORD-101", "Gojo Satoru", "gojo@jujutsu.edu", 100.0);
        fulfillmentService.fulfillOrder(order1, new CreditCardPayment(), new EmailNotification());

        // 2. Order fulfilled via Crypto & WhatsApp
        Order order2 = new Order("ORD-102", "Toji Zenin", "+1-555-0199", 1000.0);
        fulfillmentService.fulfillOrder(order2, new CryptoWalletPayment(), new WhatsAppNotification());

        // 3. Failed payment transaction (Invalid Amount)
        Order order3 = new Order("ORD-103", "Megumi Fushiguro", "megumi@jujutsu.edu", -50.0);
        fulfillmentService.fulfillOrder(order3, new UpiPayment(), new SmsNotification());
    }
}