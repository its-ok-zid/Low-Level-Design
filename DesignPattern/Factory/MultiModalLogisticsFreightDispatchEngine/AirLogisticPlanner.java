package DesignPattern.Factory.MultiModalLogisticsFreightDispatchEngine;

public class AirLogisticPlanner implements LogisticPlanner {
    @Override
    public Transport createTransport() {
        return new AirCargoTransport();
    }
}
