package RideSharingDynamicSurgeFareCalculationEngine;

public class RideSharingApplication {
    public static void main(String[] args) {
        BillingService billingService = new BillingService();

        // 1. Standard Pricing Trip
        Trip trip1 = new Trip("TRIP-101", "Alice", 10.0, 15.0, new StandardPricing());
        billingService.processTripFare(trip1);

        // 2. Peak Surge Pricing Trip (2.0x Multiplier)
        Trip trip2 = new Trip("TRIP-102", "Bob", 8.0, 20.0, new PeakSurgePricing(2.0));
        billingService.processTripFare(trip2);

        // 3. Dynamic Strategy Switch to Late-Night Pricing
        Trip trip3 = new Trip("TRIP-103", "Charlie", 12.0, 25.0);
        System.out.println(">>> Switching Charlie's trip to Late-Night Pricing:");
        trip3.setPricingStrategy(new LateNightPricing());
        billingService.processTripFare(trip3);
    }
}