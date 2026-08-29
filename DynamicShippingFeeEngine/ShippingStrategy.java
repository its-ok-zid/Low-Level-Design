package DynamicShippingFeeEngine;

public interface ShippingStrategy {
     double calculateShippingFee(double totalWeight, double totalDeclaredValue);
     String getStrategyName();
}