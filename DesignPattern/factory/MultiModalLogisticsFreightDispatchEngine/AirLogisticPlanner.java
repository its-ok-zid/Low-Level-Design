package DesignPattern.factory.MultiModalLogisticsFreightDispatchEngine;

public class AirLogisticPlanner implements LogisticPlanner {
    @Override
    public Transport createTransport() {
        return new AirCargoTransport();
    }
}
