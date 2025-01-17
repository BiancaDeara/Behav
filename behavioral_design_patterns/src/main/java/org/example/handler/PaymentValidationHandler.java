
package org.example.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.models.Item;
import org.example.models.Order;
import org.example.repository.ItemsRepository;
import org.example.repository.OrderRepository;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

@Slf4j
@Component
public class PaymentValidationHandler extends OrderValidationHandler {

    private final Random random = new Random();

    public boolean isPaymentValid() {

        return random.nextBoolean();
    }
    @Override
    public void validate(Order order) {
        // Check if the payment details are valid
//        boolean isValid = isPaymentValid();
        boolean isValid = true;

        if (isValid) {
            log.info("Payment validation passed for order ID: {}", order.getId());
            super.validate(order);
        } else {
            log.error("Payment validation failed for order ID: {}", order.getId());
            throw new RuntimeException("Payment validation failed for order ID: " + order.getId());
        }
    }

}
