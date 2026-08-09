package PaymentGateway;

public class PayPalPayment implements  Payment{
    private String email;
    private  String password;

    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }


    @Override
    public void pay(Double amount) {
        System.out.println("Paid $" + amount + " using PayPal account: " + email);
    }
}
