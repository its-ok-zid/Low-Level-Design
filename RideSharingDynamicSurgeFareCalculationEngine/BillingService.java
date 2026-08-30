package RideSharingDynamicSurgeFareCalculationEngine;

public class BillingService {
    public void processTripFare(Trip trip) {
        if (trip == null) return;
        trip.completeTrip();

        double fare = trip.calculateFare();
        String strategyName = trip.getPricingStrategy().getStrategyName();

        System.out.println("========== TRIP RECEIPT ==========");
        System.out.println("Trip ID:            " + trip.getTripId());
        System.out.println("Rider Name:         " + trip.getRiderName());
        System.out.println("Distance:           " + trip.getDistanceInKm() + " km");
        System.out.println("Duration:           " + trip.getDurationInMinutes() + " mins");
        System.out.println("Pricing Strategy:   " + strategyName);
        System.out.println("Calculated Fare:    $" + fare);
        System.out.println("Status:             " + (trip.isCompleted() ? "COMPLETED" : "IN_PROGRESS"));
        System.out.println("==================================\n");
    }
}