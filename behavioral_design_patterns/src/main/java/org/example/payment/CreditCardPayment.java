
package org.example.payment;

import org.example.models.Order;

import java.util.Random;

public class CreditCardPayment implements PaymentStrategy {
    private final Random random = new Random();
    @Override
    public void pay(double amount) {

        if (isCardValid()) {
            System.out.println("Paid $" + amount + " with Credit Card.");
        } else {
        throw new RuntimeException("Payment could not be processed. Please try again.");
    }

    }

    public boolean isCardValid() {

        return random.nextBoolean();
    }
}
