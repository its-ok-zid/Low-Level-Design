package DynamicOrderAndDiscountEngine;

public class EcommerceApplication {
    public static void main(String[] args) {
        CheckoutService checkoutService = new CheckoutService();

        // 1. Order with Percentage Discount (10%)
        Order order1 = new Order("ORD-101", "Alice", new PercentageDiscount(10.0));
        order1.addItem(new OrderItem("Laptop", 1200.00, 1));
        order1.addItem(new OrderItem("Mouse", 25.00, 2));
        checkoutService.processCheckout(order1);

        // 2. Order with Flat Discount ($50)
        Order order2 = new Order("ORD-102", "Bob", new FlatDiscount(50.0));
        order2.addItem(new OrderItem("Headphones", 80.00, 1));
        checkoutService.processCheckout(order2);

        // 3. Dynamic Strategy Switch
        System.out.println(">>> Switching Bob's discount to NoDiscount:");
        order2.setDiscountStrategy(new NoDiscount());
        checkoutService.processCheckout(order2);
    }
}