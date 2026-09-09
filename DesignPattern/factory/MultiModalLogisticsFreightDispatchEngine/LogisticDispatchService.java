package DesignPattern.factory.MultiModalLogisticsFreightDispatchEngine;

public class LogisticDispatchService {
    public void executeRoute(LogisticPlanner planner, String shipmentId, String origin, String destination, double weightInKg) {
        if (planner == null || shipmentId == null || origin == null || destination == null) {
            System.out.println("Invalid dispatch parameters provided.");
            return;
        }

        Transport transport = planner.createTransport();
        transport.planRoute(origin, destination);
        boolean isDispatched = transport.dispatch(shipmentId, weightInKg);

        if (!isDispatched) {
            System.out.println("Dispatch failed for shipment: " + shipmentId + "\n");
        } else {
            System.out.println("Consignment in transit: " + shipmentId + "\n");
        }
    }
}