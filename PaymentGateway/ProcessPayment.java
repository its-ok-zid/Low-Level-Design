package PaymentGateway;

public class ProcessPayment implements Payment{

    private CreditCardPayment creditCardPayment;
    private PayPalPayment payPalPayment;

    private Double amountTobePay;

    private String paymentType;

    @Override
    public void pay(Double amount, String cardType) {

        if(cardType.equals("CREDIT CARD")){
           amountTobePay=amount;
            paymentType="CREDIT CARD";
        }
        else if(cardType.equals("PAYPAL")){
            amountTobePay=amount;
            paymentType="PAYPAL";
        }
    }

    void display(){
        System.out.println("The Amount paid is: "+amountTobePay+ " by: "+ paymentType);
    }
}
