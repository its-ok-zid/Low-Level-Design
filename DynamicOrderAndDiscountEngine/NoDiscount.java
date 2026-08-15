package DynamicOrderAndDiscountEngine;

public class NoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double subtotal) {
        return 0.0;
    }
}