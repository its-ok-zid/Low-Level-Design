package RideSharingDynamicSurgeFareCalculationEngine;

public class BillingService {

    public void processTripFare(Trip trip) {
        if (trip == null) return;
        trip.completeTrip();


        double fare = trip.calculateFare();
        String strategyName = trip.getPricingStrategy().getStrategyName();

        System.out.println("========== TRIP RECEIPT ==========");
        System.out.println("Trip ID: " + trip.getTripId());
        System.out.println("Rider Name: " + trip.getRiderName());
        System.out.println("Distance (km): " + trip.getDistanceInKm());
        System.out.println("Duration (minutes): " + trip.getDurationInMinutes());
        System.out.println("Fare: $" + fare);
        System.out.println("Pricing Strategy: " + strategyName);
        System.out.println("Trip Status: " + (trip.isCompleted() ? "COMPLETED" : "IN_PROGRESS"));
        System.out.println("==================================\n");
    }
}
