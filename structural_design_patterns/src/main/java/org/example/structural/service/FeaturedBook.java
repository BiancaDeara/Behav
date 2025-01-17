package org.example.structural.service;

public class FeaturedBook implements BookDecorator {
    private final BookDecorator book;

    public FeaturedBook(BookDecorator book) {
        this.book = book;
    }

    @Override
    public String getDescription() {
        return book.getDescription() + " (Featured)";
    }

    @Override
    public double getPrice() {
        return book.getPrice() + 5.0; // Premium for featured books
    }
}

