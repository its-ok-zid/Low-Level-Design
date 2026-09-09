package DesignPattern.factory.PaymentGatewayOrchestrationEngine;

public class PayPalGateway implements PaymentGateway {
    @Override
    public boolean connect() {
        System.out.println("[PayPal] Establishing OAuth handshake and session tokens...");
        return true;
    }

    @Override
    public boolean processTransaction(String transactionId, double amount) {
        System.out.println("[PayPal] Debited $" + amount + " for Transaction: " + transactionId);
        return true;
    }
}