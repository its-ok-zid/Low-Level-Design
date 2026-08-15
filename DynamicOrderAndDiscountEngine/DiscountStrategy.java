package DynamicOrderAndDiscountEngine;

public interface DiscountStrategy {
    double applyDiscount(double subtotal);
}