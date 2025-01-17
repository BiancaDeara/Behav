package org.example.payment;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class CashPayment implements PaymentStrategy {

    private double balance;
    private final Random random = new Random();

    @Override
    public void pay(double amount) {
        if (hasEnoughFunds(amount)) {
            log.info("Payment successful");
        }else{
            log.info("Payment failed");
            throw new RuntimeException("Payment failed");
        }

    }

    public boolean hasEnoughFunds(double amount) {

        if(this.balance >= amount){
            return true;
        }
        return false;
    }
}
