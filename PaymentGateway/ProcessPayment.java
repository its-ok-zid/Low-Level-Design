package PaymentGateway;

public class ProcessPayment{

    void  processTransaction(Payment payment, Double amount){
        if(payment==null){
            System.out.println("Invalid payment method!");
            return;
        }
        payment.pay(amount);
    }
}
