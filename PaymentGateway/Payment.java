package PaymentGateway;

public interface Payment {
    void pay(Double amount, String cardType);
}
