package DynamicShippingFeeEngine;

public class LogisticApplication {
    public static void main(String[] args) {
        DispatchService dispatchService = new DispatchService();

        // 1. High-Value Insured Shipment
        Shipment shipment1 = new Shipment("TRK-90210", "742 Evergreen Terrace, Springfield", new HighValueInsuredShipping());
        shipment1.addPackage(new Package("Smartphone", 0.5, 999.00));
        shipment1.addPackage(new Package("Noise Cancelling Headphones", 0.8, 350.00));
        dispatchService.processDispatch(shipment1);

        // 2. Standard Ground Shipment
        Shipment shipment2 = new Shipment("TRK-44102", "221B Baker Street, London");
        shipment2.addPackage(new Package("Textbooks", 8.0, 120.00));
        dispatchService.processDispatch(shipment2);

        // 3. Dynamic Strategy Switch to Express
        System.out.println(">>> Upgrading Shipment 2 to Express Air Shipping:");
        shipment2.setShippingStrategy(new ExpressAirShipping());
        dispatchService.processDispatch(shipment2);
    }
}