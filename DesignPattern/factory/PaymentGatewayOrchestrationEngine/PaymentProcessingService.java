package DesignPattern.factory.PaymentGatewayOrchestrationEngine;

public class PaymentProcessingService {
    public boolean processPayment(PaymentGatewayFactory factory, String transactionId, double amount) {
        if (factory == null || transactionId == null || amount <= 0) {
            System.out.println("Invalid payment parameters.");
            return false;
        }

        PaymentGateway gateway = factory.createGateway();
        if (!gateway.connect()) {
            System.out.println("Failed to connect to the external gateway.");
            return false;
        }

        return gateway.processTransaction(transactionId, amount);
    }
}