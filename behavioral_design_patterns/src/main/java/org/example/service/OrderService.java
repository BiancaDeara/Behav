package org.example.service;
import org.example.command.CancelOrderCommand;
import org.example.models.Item;
import org.example.notification.EmailNotification;
import org.example.notification.WebNotification;
import org.example.payment.CashPayment;
import org.example.payment.CreditCardPayment;
import lombok.extern.slf4j.Slf4j;
import org.example.command.PlaceOrderCommand;
import org.example.handler.InventoryCheckHandler;
import org.example.handler.PaymentValidationHandler;
import org.example.models.Order;
import org.example.payment.PaymentStrategy;
import org.example.repository.ItemsRepository;
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private InventoryCheckHandler inventoryCheckHandler;

    @Autowired
    private PaymentValidationHandler paymentValidationHandler;


    public void addOrder(Order order) {
        log.info("Adding order {}", order);
        order.updateStatus("IN_PROGRESS", orderRepository);

        notifyObservers(order, "Order is being processed.");

        validateOrder(order);
        order.updateStatus("VALIDATED", orderRepository);

        PaymentStrategy paymentStrategy = getPaymentStrategy(order.getPaymentMethod());
        if (paymentStrategy != null) {
            paymentStrategy.pay(order.getTotalAmount());
            order.updateStatus("PAYED", orderRepository);
            notifyObservers(order, "Payment processed successfully.");
        } else {
            order.updateStatus("PAY_ERROR", orderRepository);
            throw new RuntimeException("Invalid payment method: " + order.getPaymentMethod());
        }

        PlaceOrderCommand placeOrderCommand = new PlaceOrderCommand(order, orderRepository);
        placeOrderCommand.execute();
        notifyObservers(order, "Order has been placed.");

        order.updateStatus("COMPLETED", orderRepository);
        notifyObservers(order, "Order is now completed.");
    }

    public void cancelOrder(Long orderId) {
        log.info("Cancelling order with ID {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        CancelOrderCommand cancelOrderCommand = new CancelOrderCommand(order, orderRepository);
        cancelOrderCommand.execute();
        notifyObservers(order, "Order has been canceled.");
    }

    public String getOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        return order.getStatus();
    }

    public List<Item> getAllItems() {
        return itemsRepository.findAll();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    //Helpers
    //Chain of Responsibility Design Pattern
    public void validateOrder(Order order) {

        inventoryCheckHandler.setNext(paymentValidationHandler);

        // Start the chain
        inventoryCheckHandler.validate(order);
    }


    //Strategy Design Pattern
    private PaymentStrategy getPaymentStrategy(String paymentMethod) {
        if ("cash".equalsIgnoreCase(paymentMethod)) {
            return new CashPayment();
        } else if ("credit".equalsIgnoreCase(paymentMethod)) {
            return new CreditCardPayment();
        }
        return null;
    }

    private void notifyObservers(Order order, String message) {
        NotificationService notificationService = setupNotificationService(order);
        notificationService.notifyObservers(message);
    }

    //Observer Design Pattern
    private NotificationService setupNotificationService(Order order) {
        NotificationService notificationService = new NotificationService();

        switch (order.getNotificationPreference().toLowerCase()) {
            case "email":
                notificationService.addObserver(new EmailNotification());
                break;
            case "web":
                notificationService.addObserver(new WebNotification());
                break;
            default:
                throw new IllegalArgumentException("Invalid notification preference: " + order.getNotificationPreference());
        }

        return notificationService;
    }

}

