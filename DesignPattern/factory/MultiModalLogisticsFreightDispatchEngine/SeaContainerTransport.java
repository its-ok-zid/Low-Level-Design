package DesignPattern.factory.MultiModalLogisticsFreightDispatchEngine;

public class SeaContainerTransport implements Transport {
    private static final double MAX_WEIGHT_KG = 500000.0;

    @Override
    public void planRoute(String origin, String destination) {
        System.out.println("[Sea Freight] Plotting oceanic shipping corridor from " + origin + " port to " + destination + " port.");
    }

    @Override
    public boolean dispatch(String shipmentId, double weightInKg) {
        if (weightInKg <= 0 || weightInKg > MAX_WEIGHT_KG) {
            System.out.println("[Sea Freight] Error: Shipment " + shipmentId + " weight (" + weightInKg + " kg) exceeds container ship limit (" + MAX_WEIGHT_KG + " kg).");
            return false;
        }
        System.out.println("[Sea Freight] Vessel loaded. Dispatched container " + shipmentId + " (" + weightInKg + " kg).");
        return true;
    }
}