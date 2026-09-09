package DesignPattern.factory.PaymentGatewayOrchestrationEngine;

public class RazorpayGateway implements PaymentGateway {
    @Override
    public boolean connect() {
        System.out.println("[Razorpay] Initializing UPI and banking rails connection...");
        return true;
    }

    @Override
    public boolean processTransaction(String transactionId, double amount) {
        System.out.println("[Razorpay] Debited $" + amount + " for Transaction: " + transactionId);
        return true;
    }
}