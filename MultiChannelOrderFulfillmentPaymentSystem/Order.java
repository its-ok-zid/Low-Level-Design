package MultiChannelOrderFulfillmentPaymentSystem;

public class Order {
    private final String orderId;
    private final String customerName;
    private final String email;
    private final double orderAmount;

    public Order(String orderId, String customerName, String email, double orderAmount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.email = email;
        this.orderAmount = orderAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getEmail() {
        return email;
    }

    public double getOrderAmount() {
        return orderAmount;
    }
}