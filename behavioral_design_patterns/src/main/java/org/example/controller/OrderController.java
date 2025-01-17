package org.example.controller;

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

    // Read all orders
//    @GetMapping
//    public ResponseEntity<List<Order>> getAllOrders() {
//        List<Order> orders = orderService.findAllOrders();
//        return ResponseEntity.ok(orders);
//    }
//
//    // Read a single order by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
//        Optional<Order> order = orderService.findOrderById(id);
//        return order.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // Update an existing order
//    @PutMapping("/{id}")
//    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
//        Optional<Order> updatedOrder = orderService.updateOrder(id, orderDetails);
//        return updatedOrder.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // Delete an order
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
//        boolean isDeleted = orderService.deleteOrder(id);
//        if (isDeleted) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


}
