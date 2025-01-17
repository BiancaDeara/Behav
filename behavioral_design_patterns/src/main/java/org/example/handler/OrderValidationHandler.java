
package org.example.handler;

import org.example.models.Order;

public abstract class OrderValidationHandler {

    protected OrderValidationHandler next;

    public void setNext(OrderValidationHandler next) {
        this.next = next;
    }

    public void validate(Order order) {
        if (next != null) {
            next.validate(order);
        }
    }
}
