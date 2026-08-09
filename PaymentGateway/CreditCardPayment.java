package PaymentGateway;

public class CreditCardPayment implements  Payment{
    private String cardHolderName;
    private int cvv;
    private String cardNumber;

    public CreditCardPayment(String cardHolderName, int cvv, String cardNumber) {
        this.cardHolderName = cardHolderName;
        this.cvv = cvv;
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(Double amount) {
        String lastFour = cardNumber.length() >= 4 ? cardNumber.substring(cardNumber.length() - 4) : cardNumber;
        System.out.println("Paid $" + amount + " using Credit Card ending in " + lastFour + " (Holder: " + cardHolderName + ")");
    }
}