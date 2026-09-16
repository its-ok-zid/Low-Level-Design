package DesignPattern.Factory.PaymentGatewayOrchestrationEngine;

public class StripeGatewayFactory implements PaymentGatewayFactory {
    @Override
    public PaymentGateway createGateway() {
        return new StripeGateway();
    }
}