package RideSharingDynamicSurgeFareCalculationEngine;

public class Trip {
    private final String tripId;
    private final String riderName;
    private final double distanceInKm;
    private final double durationInMinutes;
    private boolean isCompleted;
    private PricingStrategy pricingStrategy;

    public Trip(String tripId, String riderName, double distanceInKm, double durationInMinutes) {
        this(tripId, riderName, distanceInKm, durationInMinutes, new StandardPricing());
    }

    public Trip(String tripId, String riderName, double distanceInKm, double durationInMinutes, PricingStrategy pricingStrategy) {
        this.tripId = tripId;
        this.riderName = riderName;
        this.distanceInKm = distanceInKm;
        this.durationInMinutes = durationInMinutes;
        this.isCompleted = false;
        this.pricingStrategy = (pricingStrategy != null) ? pricingStrategy : new StandardPricing();
    }

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = (pricingStrategy != null) ? pricingStrategy : new StandardPricing();
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public void completeTrip() {
        this.isCompleted = true;
    }

    public double calculateFare() {
        return pricingStrategy.calculateFare(distanceInKm, durationInMinutes);
    }

    public String getTripId() {
        return tripId;
    }

    public String getRiderName() {
        return riderName;
    }

    public double getDistanceInKm() {
        return distanceInKm;
    }

    public double getDurationInMinutes() {
        return durationInMinutes;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}