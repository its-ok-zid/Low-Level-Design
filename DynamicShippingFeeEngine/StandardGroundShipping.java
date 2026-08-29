package DynamicShippingFeeEngine;

public class StandardGroundShipping implements ShippingStrategy {
    @Override
    public double calculateShippingFee(double totalWeight, double totalDeclaredValue) {
        return 5.00 + (totalWeight * 2.50);
    }

    @Override
    public String getStrategyName() {
        return "Standard Ground Shipping";
    }
}