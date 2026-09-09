package DesignPattern.factory.MultiModalLogisticsFreightDispatchEngine;

public class RoadLogisticPlanner implements LogisticPlanner {
    @Override
    public Transport createTransport() {
        return new RoadTruckTransport();
    }
}