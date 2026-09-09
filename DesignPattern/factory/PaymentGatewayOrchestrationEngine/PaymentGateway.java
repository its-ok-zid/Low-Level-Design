package DesignPattern.factory.PaymentGatewayOrchestrationEngine;

public interface PaymentGateway {
    boolean connect();
    boolean processTransaction(String transactionId, double amount);
}