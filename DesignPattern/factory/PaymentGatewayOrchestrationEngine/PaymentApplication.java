package DesignPattern.factory.PaymentGatewayOrchestrationEngine;

public class PaymentApplication {
    public static void main(String[] args) {
        PaymentProcessingService paymentService = new PaymentProcessingService();

        // 1. Process via PayPal
        System.out.println("--- Executing PayPal Transaction ---");
        PaymentGatewayFactory payPalFactory = new PayPalGatewayFactory();
        paymentService.processPayment(payPalFactory, "TXN-PAYPAL-101", 125.50);

        // 2. Process via Stripe
        System.out.println("\n--- Executing Stripe Transaction ---");
        PaymentGatewayFactory stripeFactory = new StripeGatewayFactory();
        paymentService.processPayment(stripeFactory, "TXN-STRIPE-202", 450.00);

        // 3. Process via Razorpay
        System.out.println("\n--- Executing Razorpay Transaction ---");
        PaymentGatewayFactory razorpayFactory = new RazorpayGatewayFactory();
        paymentService.processPayment(razorpayFactory, "TXN-RAZOR-303", 2500.00);
    }
}