package DesignPattern.factory.MultiModalLogisticsFreightDispatchEngine;

public interface Transport {
    void planRoute(String origin, String destination);

    boolean dispatch(String shipmentId, double weightInKg);
}