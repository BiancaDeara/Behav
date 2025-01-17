package org.example.command;

import org.example.models.Order;
import org.example.repository.OrderRepository;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class CancelOrderCommand implements OrderCommand  {

    private final Order order;
    private final OrderRepository orderRepository;


    public CancelOrderCommand(Order order, OrderRepository orderRepository) {
        this.order = order;
        this.orderRepository = orderRepository;
    }

    @Override
    public void execute() {
        orderRepository.delete(order);
        System.out.println("Order placed successfully: " + order.getId());
    }
}
