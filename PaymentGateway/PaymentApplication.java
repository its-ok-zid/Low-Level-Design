package PaymentGateway;

import java.util.Scanner;

public class PaymentApplication {

    public static void main(String[] args) {

        ProcessPayment processPayment = new ProcessPayment();
        Scanner sc=new Scanner(System.in);
        String paymentType;
        double amount;

        System.out.println("Enter the type of payment card or Paypal");
        paymentType=sc.next();

        System.out.println("Enter the amount to be pay.");
        amount= Double.parseDouble(sc.next());

        processPayment.pay(amount,paymentType);
        processPayment.display();
    }
}
