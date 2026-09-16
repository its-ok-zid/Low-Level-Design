package DesignPattern.Creational.Factory.PaymentGatewayOrchestrationEngine;

public class RazorpayGatewayFactory implements PaymentGatewayFactory {
    @Override
    public PaymentGateway createGateway() {
        return new RazorpayGateway();
    }
}