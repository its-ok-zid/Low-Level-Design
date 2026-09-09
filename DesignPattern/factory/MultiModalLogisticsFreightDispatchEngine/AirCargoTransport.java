package DesignPattern.factory.MultiModalLogisticsFreightDispatchEngine;

public class AirCargoTransport implements Transport {
    private static final double MAX_WEIGHT_KG = 50000.0;

    @Override
    public void planRoute(String origin, String destination) {
        System.out.println("[Air Cargo] Reserving international flight corridor from " + origin + " to " + destination + ".");
    }

    @Override
    public boolean dispatch(String shipmentId, double weightInKg) {
        if (weightInKg <= 0 || weightInKg > MAX_WEIGHT_KG) {
            System.out.println("[Air Cargo] Error: Shipment " + shipmentId + " weight (" + weightInKg + " kg) exceeds max air limit (" + MAX_WEIGHT_KG + " kg).");
            return false;
        }
        System.out.println("[Air Cargo] Flight manifested. Dispatched shipment " + shipmentId + " (" + weightInKg + " kg).");
        return true;
    }
}