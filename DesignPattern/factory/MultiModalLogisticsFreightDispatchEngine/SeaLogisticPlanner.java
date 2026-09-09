package DesignPattern.factory.MultiModalLogisticsFreightDispatchEngine;

public class SeaLogisticPlanner implements LogisticPlanner {
    @Override
    public Transport createTransport() {
        return new SeaContainerTransport();
    }
}
