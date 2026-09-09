package DesignPattern.factory.PaymentGatewayOrchestrationEngine;

public class RazorpayGatewayFactory implements PaymentGatewayFactory {
    @Override
    public PaymentGateway createGateway() {
        return new RazorpayGateway();
    }
}