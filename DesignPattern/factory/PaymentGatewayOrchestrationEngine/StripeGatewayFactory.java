package DesignPattern.factory.PaymentGatewayOrchestrationEngine;

public class StripeGatewayFactory implements PaymentGatewayFactory {
    @Override
    public PaymentGateway createGateway() {
        return new StripeGateway();
    }
}