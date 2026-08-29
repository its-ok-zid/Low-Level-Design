package RideSharingDynamicSurgeFareCalculationEngine;

public class PeakSurgePricing implements PricingStrategy {

    private final double surgeMultiplier;

    public PeakSurgePricing(double surgeMultiplier) {
        this.surgeMultiplier = Math.max(1.0, surgeMultiplier);
    }

    @Override
    public double calculateFare(double distance, double time) {
        double baseFare = 3.0;
        return (baseFare + (distance * 1.20) + (time * 0.25)) * surgeMultiplier;
    }

    @Override
    public String getStrategyName() {
        return "Peak Surge Pricing (Multiplier: " + surgeMultiplier + ")";
    }
}
