package RideSharingDynamicSurgeFareCalculationEngine;

public class StandardPricing implements PricingStrategy {
    @Override
    public double calculateFare(double distance, double time) {
        double baseFare = 3.0;
        return baseFare + (distance * 1.20) + (time * 0.25);
    }

    @Override
    public String getStrategyName() {
        return "Standard Pricing";
    }
}
