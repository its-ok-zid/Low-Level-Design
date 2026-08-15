package DynamicOrderAndDiscountEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final String orderId;
    private final String customerName;
    private final List<OrderItem> items = new ArrayList<>();
    private DiscountStrategy discountStrategy;

    public Order(String orderId, String customerName) {
        this(orderId, customerName, new NoDiscount());
    }

    public Order(String orderId, String customerName, DiscountStrategy discountStrategy) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.discountStrategy = (discountStrategy != null) ? discountStrategy : new NoDiscount();
    }

    public void addItem(OrderItem item) {
        if (item != null) {
            items.add(item);
        }
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = (discountStrategy != null) ? discountStrategy : new NoDiscount();
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public double calculateSubtotal() {
        return items.stream().mapToDouble(OrderItem::getSubtotal).sum();
    }

    public double calculateDiscountAmount() {
        return discountStrategy.applyDiscount(calculateSubtotal());
    }

    public double calculateFinalTotal() {
        double subtotal = calculateSubtotal();
        double discount = calculateDiscountAmount();
        return Math.max(0.0, subtotal - discount);
    }
}