package DynamicOrderAndDiscountEngine;

public class CheckoutService {
    public void processCheckout(Order order) {
        if (order == null) return;

        System.out.println("========== CHECKOUT RECEIPT ==========");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer: " + order.getCustomerName());
        System.out.println("--------------------------------------");
        for (OrderItem item : order.getItems()) {
            System.out.println(item.getItemName() + " x" + item.getQuantity() + " @ $" + item.getUnitPrice() + " = $" + item.getSubtotal());
        }
        System.out.println("--------------------------------------");
        double subtotal = order.calculateSubtotal();
        double discount = order.calculateDiscountAmount();
        double total = order.calculateFinalTotal();

        System.out.println("Subtotal:        $" + subtotal);
        System.out.println("Discount Applied: -$" + discount);
        System.out.println("Final Payable:   $" + total);
        System.out.println("======================================\n");
    }
}