package RideSharingDynamicSurgeFareCalculationEngine;

public class StandardPricing implements PricingStrategy {
    @Override
    public double calculateFare(double distance, double time) {
        return 3.00 + (distance * 1.20) + (time * 0.25);
    }

    @Override
    public String getStrategyName() {
        return "Standard Pricing";
    }
}