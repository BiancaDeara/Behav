package org.example.service;
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
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private InventoryCheckHandler inventoryCheckHandler;

    @Autowired
    private PaymentValidationHandler paymentValidationHandler;


    public void addOrder(Order order) {

        log.info("Adding order {}", order);
        order.updateStatus("IN_PROGRESS");
        validateOrder(order);

        PaymentStrategy paymentStrategy = getPaymentStrategy(order.getPaymentMethod());
        if (paymentStrategy != null) {
            paymentStrategy.pay(order.getTotalAmount());
        } else {
            throw new RuntimeException("Invalid payment method: " + order.getPaymentMethod());
        }

        PlaceOrderCommand placeOrderCommand = new PlaceOrderCommand(order, orderRepository);
        placeOrderCommand.execute();

        NotificationService notificationService = setupNotificationService(order);
        notificationService.notifyObservers("Order placed successfully: " + order.getId());

        order.updateStatus("COMPLETED");
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

