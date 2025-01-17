package org.example.structural.service;


import org.example.structural.entity.Book;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class LibraryFacade {

    // TODO: Use BookService and other services to simplify the interaction with multiple subsystems

    public void addBook(Book book) {
        // TODO: Add book to the library through service layer
    }

    public List<Book> getFeaturedBooks() {
        // TODO: Return a list of featured books using the decorator pattern
        return null;
    }
}
