package org.example.structural.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.example.structural.service.BookDecorator;


@Entity
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Book implements BookDecorator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;
    String author;
    double price;
    private String category;
    private boolean featured;
    private boolean bestseller;

    @Override
    public String getDescription() {
        return title + " by " + author;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
