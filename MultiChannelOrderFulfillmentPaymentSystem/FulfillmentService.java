package MultiChannelOrderFulfillmentPaymentSystem;

public class FulfillmentService {
    public void fulfillOrder(Order order, PaymentStrategy paymentStrategy, NotificationStrategy notificationStrategy) {
        if (order == null || paymentStrategy == null || notificationStrategy == null) {
            throw new IllegalArgumentException("Order, PaymentStrategy and NotificationStrategy cannot be null");
        }

        boolean paymentStatus = paymentStrategy.processPayment(order.getOrderAmount());
        if (!paymentStatus) {
            notificationStrategy.sendNotification(
                    order.getEmail(),
                    "Hello " + order.getCustomerName() + ", payment failed for order " + order.getOrderId() + ". Please try again."
            );
            return;
        }

        notificationStrategy.sendNotification(
                order.getEmail(),
                "Hello " + order.getCustomerName() + ", your order " + order.getOrderId() + " has been fulfilled successfully."
        );
    }
}