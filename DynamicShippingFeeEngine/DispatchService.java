package DynamicShippingFeeEngine;

public class DispatchService {
    public void processDispatch(Shipment shipment) {
        if (shipment == null) return;

        System.out.println("========== SHIPMENT DISPATCH MANIFEST ==========");
        System.out.println("Tracking Code:       " + shipment.getTrackingCode());
        System.out.println("Destination Address: " + shipment.getDestinationAddress());
        System.out.println("------------------------------------------------");
        for (Package pkg : shipment.getPackages()) {
            System.out.println("- " + pkg.getDescription() + " | Weight: " + pkg.getWeightInKg() + " kg | Declared Value: $" + pkg.getDeclaredValue());
        }
        System.out.println("------------------------------------------------");
        double totalWeight = shipment.calculateTotalWeight();
        double totalValue = shipment.calculateTotalValue();
        double fee = shipment.calculateShippingFee();

        System.out.println("Total Weight:         " + totalWeight + " kg");
        System.out.println("Total Declared Value: $" + totalValue);
        System.out.println("Selected Strategy:    " + shipment.getShippingStrategy().getStrategyName());
        System.out.println("Final Shipping Fee:   $" + fee);
        System.out.println("================================================\n");
    }
}