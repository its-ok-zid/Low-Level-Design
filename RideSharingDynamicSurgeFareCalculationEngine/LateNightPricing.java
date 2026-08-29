package RideSharingDynamicSurgeFareCalculationEngine;

public class LateNightPricing implements PricingStrategy {
    @Override
    public double calculateFare(double distance, double time) {
        double baseFare = 3.0;
        return baseFare + (distance * 1.20) + (time * 0.25) + 5.0;
    }

    @Override
    public String getStrategyName() {
        return "Late Night Pricing";
    }
}
