package DesignPattern.factory.MultiModalLogisticsFreightDispatchEngine;

public class RoadTruckTransport implements Transport {
    private static final double MAX_WEIGHT_KG = 20000.0;

    @Override
    public void planRoute(String origin, String destination) {
        System.out.println("[Road Freight] Calculating highway toll route from " + origin + " to " + destination + ".");
    }

    @Override
    public boolean dispatch(String shipmentId, double weightInKg) {
        if (weightInKg <= 0 || weightInKg > MAX_WEIGHT_KG) {
            System.out.println("[Road Freight] Error: Shipment " + shipmentId + " weight (" + weightInKg + " kg) exceeds max road limit (" + MAX_WEIGHT_KG + " kg).");
            return false;
        }
        System.out.println("[Road Freight] Dispatched shipment " + shipmentId + " successfully. Total payload: " + weightInKg + " kg.");
        return true;
    }
}