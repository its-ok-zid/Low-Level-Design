package DynamicShippingFeeEngine;

public class ExpressAirShipping implements ShippingStrategy {
    @Override
    public double calculateShippingFee(double totalWeight, double totalDeclaredValue) {
        return 15.00 + (totalWeight * 4.00) + 10.00;
    }

    @Override
    public String getStrategyName() {
        return "Express Air Shipping";
    }
}