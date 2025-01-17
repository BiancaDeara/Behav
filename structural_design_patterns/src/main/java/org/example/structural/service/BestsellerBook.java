package org.example.structural.service;

public class BestsellerBook implements BookDecorator {
    private final BookDecorator book;

    public BestsellerBook(BookDecorator book) {
        this.book = book;
    }

    @Override
    public String getDescription() {
        return book.getDescription() + " (Bestseller)";
    }

    @Override
    public double getPrice() {
        return book.getPrice() + 10.0; // Premium for bestseller books
    }
}

