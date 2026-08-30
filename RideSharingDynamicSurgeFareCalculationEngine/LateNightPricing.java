package RideSharingDynamicSurgeFareCalculationEngine;

public class LateNightPricing implements PricingStrategy {
    @Override
    public double calculateFare(double distance, double time) {
        return 3.00 + (distance * 1.20) + (time * 0.25) + 5.00;
    }

    @Override
    public String getStrategyName() {
        return "Late Night Pricing";
    }
}