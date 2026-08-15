package DynamicOrderAndDiscountEngine;

public class FlatDiscount implements DiscountStrategy {
    private final double amount;

    public FlatDiscount(double amount) {
        this.amount = amount;
    }

    @Override
    public double applyDiscount(double subtotal) {
        return Math.min(amount, Math.max(0.0, subtotal));
    }
}