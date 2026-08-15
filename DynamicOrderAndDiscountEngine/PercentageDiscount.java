package DynamicOrderAndDiscountEngine;

public class PercentageDiscount implements DiscountStrategy {
    private final double percentage;

    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double applyDiscount(double subtotal) {
        return subtotal * (percentage / 100.0);
    }
}