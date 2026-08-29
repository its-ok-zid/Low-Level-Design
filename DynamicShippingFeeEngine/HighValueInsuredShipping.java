package DynamicShippingFeeEngine;

public class HighValueInsuredShipping implements ShippingStrategy {
    @Override
    public double calculateShippingFee(double totalWeight, double totalDeclaredValue) {
        return 20.00 + (totalWeight * 3.00) + (totalDeclaredValue * 0.01);
    }

    @Override
    public String getStrategyName() {
        return "High-Value Insured Shipping";
    }
}