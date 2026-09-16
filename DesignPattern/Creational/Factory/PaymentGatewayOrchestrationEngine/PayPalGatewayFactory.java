package DesignPattern.Creational.Factory.PaymentGatewayOrchestrationEngine;

public class PayPalGatewayFactory implements PaymentGatewayFactory {
    @Override
    public PaymentGateway createGateway() {
        return new PayPalGateway();
    }
}