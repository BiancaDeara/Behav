package org.example.controller;

import org.example.models.Item;
import org.example.models.Order;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Create a new order
    @PostMapping
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        try {
            orderService.addOrder(order);
            return ResponseEntity.ok("Order placed successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error placing order: " + e.getMessage());
        }
    }

    // Cancel an order
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        try {
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok("Order canceled successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error canceling order: " + e.getMessage());
        }
    }

    // Get the status of an order
    @GetMapping("/{orderId}/status")
    public ResponseEntity<String> getOrderStatus(@PathVariable Long orderId) {
        try {
            String status = orderService.getOrderStatus(orderId);
            return ResponseEntity.ok("Order status: " + status);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error fetching order status: " + e.getMessage());
        }
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = orderService.getAllItems();
        return ResponseEntity.ok(items);
    }

    // Get all orders
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }


}
