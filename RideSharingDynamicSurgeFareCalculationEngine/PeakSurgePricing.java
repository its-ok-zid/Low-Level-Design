package RideSharingDynamicSurgeFareCalculationEngine;

public class PeakSurgePricing implements PricingStrategy {
    private final double surgeMultiplier;

    public PeakSurgePricing(double surgeMultiplier) {
        this.surgeMultiplier = Math.max(1.0, surgeMultiplier);
    }

    @Override
    public double calculateFare(double distance, double time) {
        double standardFare = 3.00 + (distance * 1.20) + (time * 0.25);
        return standardFare * surgeMultiplier;
    }

    @Override
    public String getStrategyName() {
        return "Peak Surge Pricing (" + surgeMultiplier + "x)";
    }
}