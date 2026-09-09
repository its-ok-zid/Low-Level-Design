package DesignPattern.factory.PaymentGatewayOrchestrationEngine;

public class StripeGateway implements PaymentGateway {
    @Override
    public boolean connect() {
        System.out.println("[Stripe] Validating publishable/secret API keys...");
        return true;
    }

    @Override
    public boolean processTransaction(String transactionId, double amount) {
        System.out.println("[Stripe] Debited $" + amount + " for Transaction: " + transactionId);
        return true;
    }
}