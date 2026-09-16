package DesignPattern.Creational.Factory.PaymentGatewayOrchestrationEngine;

public interface PaymentGateway {
    boolean connect();

    boolean processTransaction(String transactionId, double amount);
}