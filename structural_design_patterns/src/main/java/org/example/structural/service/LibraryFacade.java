package org.example.structural.service;


import io.swagger.annotations.ApiParam;
import org.example.structural.dto.BookDto;
import org.example.structural.entity.Book;
import org.example.structural.entity.Category;
import org.example.structural.utils.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LibraryFacade {

    private final BookService bookService;
    private final CategoryService categoryService;

    @Autowired
    public LibraryFacade(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks()
                .stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        Book book = bookService.getBookById(id);
        return BookMapper.toDTO(book);
    }

    public BookDto addBook(BookDto BookDto) {
        Book book = BookMapper.toEntity(BookDto);
        Book savedBook = bookService.addBook(book);
        return BookMapper.toDTO(savedBook);
    }

    public BookDto updateBook(Long id,  BookDto updatedBookDto) {
        Book updatedBook = BookMapper.toEntity(updatedBookDto);
        Book savedBook = bookService.updateBook(id, updatedBook);
        return BookMapper.toDTO(savedBook);

    }

    public void deleteBook(Long id) {
        bookService.deleteBook(id);
    }

    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    public List<Book> getBooksByCategory(String categoryName) {
        return bookService.getAllBooks()
                .stream()
                .filter(book -> categoryName.equalsIgnoreCase(book.getCategory()))
                .collect(Collectors.toList());
    }

    public Book addBookWithCategory(Book book, String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        if (category == null) {
            category = categoryService.addCategory(new Category(null, categoryName));
        }
        book.setCategory(category.getName());
        return bookService.addBook(book);
    }

    public BookDecorator markBookAsFeatured(Long bookId) {
        return bookService.markBookAsFeatured(bookId);
    }

    public BookDecorator markBookAsBestseller(Long bookId) {
        return bookService.markBookAsBestseller(bookId);
    }
}

