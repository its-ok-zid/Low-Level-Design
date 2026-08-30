package RideSharingDynamicSurgeFareCalculationEngine;

public interface PricingStrategy {
    double calculateFare(double distance, double time);
    String getStrategyName();
}