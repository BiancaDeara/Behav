package org.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String status;
    private double totalAmount;
    private Long itemId;
    private String paymentMethod;
    private String notificationPreference;


    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Order " + id + " status updated to: " + newStatus);
    }

}

