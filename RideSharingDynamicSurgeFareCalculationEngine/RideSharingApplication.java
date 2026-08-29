package RideSharingDynamicSurgeFareCalculationEngine;

public class RideSharingApplication {
    public static void main(String[] args) {
        // Create a trip with standard pricing
        Trip trip1 = new Trip("T001", "Alice", 10.0, 15.0, new StandardPricing());
        System.out.println("Trip ID: " + trip1.getTripId());
        System.out.println("Rider: " + trip1.getRiderName());
        System.out.println("Distance: " + trip1.getDistanceInKm() + " km");
        System.out.println("Duration: " + trip1.getDurationInMinutes() + " minutes");
        System.out.println("Fare (Standard Pricing): $" + trip1.calculateFare());

        // Change to late night pricing
        trip1.setPricingStrategy(new LateNightPricing());
        System.out.println("Fare (Late Night Pricing): $" + trip1.calculateFare());

        // Complete the trip
        trip1.completeTrip();
        System.out.println("Is Trip Completed? " + trip1.isCompleted());

        // Create a trip with standard pricing
        Trip trip2 = new Trip("T001", "Alice", 10.0, 15.0, new PeakSurgePricing(3.0));
        System.out.println("Trip ID: " + trip2.getTripId());
        System.out.println("Rider: " + trip2.getRiderName());
        System.out.println("Distance: " + trip2.getDistanceInKm() + " km");
        System.out.println("Duration: " + trip2.getDurationInMinutes() + " minutes");
        System.out.println("Fare (Standard Pricing): $" + trip2.calculateFare());

        // Change to late night pricing
        trip2.setPricingStrategy(new LateNightPricing());
        System.out.println("Fare (Late Night Pricing): $" + trip2.calculateFare());

        // Complete the trip
        trip2.completeTrip();
        System.out.println("Is Trip Completed? " + trip2.isCompleted());
    }
}
