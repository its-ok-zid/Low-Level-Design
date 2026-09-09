package DesignPattern.factory.MultiModalLogisticsFreightDispatchEngine;

public class LogisticsApplication {
    public static void main(String[] args) {
        LogisticDispatchService dispatchService = new LogisticDispatchService();

        // 1. Road Freight Dispatch
        System.out.println("--- Road Truck Consignment ---");
        LogisticPlanner roadPlanner = new RoadLogisticPlanner();
        dispatchService.executeRoute(roadPlanner, "SHP-ROAD-01", "Berlin", "Munich", 18500.0);

        // 2. Air Cargo Dispatch
        System.out.println("--- Air Cargo Consignment ---");
        LogisticPlanner airPlanner = new AirLogisticPlanner();
        dispatchService.executeRoute(airPlanner, "SHP-AIR-02", "New York", "London", 32000.0);

        // 3. Sea Freight Dispatch (Valid)
        System.out.println("--- Sea Container Consignment ---");
        LogisticPlanner seaPlanner = new SeaLogisticPlanner();
        dispatchService.executeRoute(seaPlanner, "SHP-SEA-03", "Shanghai", "Rotterdam", 450000.0);

        // 4. Sea Freight Dispatch (Overweight Limit Rejection)
        System.out.println("--- Sea Container Overweight Failure Test ---");
        dispatchService.executeRoute(seaPlanner, "SHP-SEA-04", "Singapore", "Hamburg", 750000.0);
    }
}