package org.example.structural.controller;

import io.swagger.annotations.ApiParam;
import org.example.structural.dto.BookDto;
import org.example.structural.entity.Book;
import org.example.structural.entity.Category;
import org.example.structural.service.BookDecorator;
import org.example.structural.service.LibraryFacade;
import org.example.structural.utils.BookMapper;
import org.example.structural.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class LibraryController {


    @Autowired
    private  LibraryFacade libraryFacade;


    @Operation(summary = "Retrieve all books", description = "Returns a list of all books in the library as BookDto objects")
    @GetMapping
    public List<BookDto> getAllBooks() {
        return libraryFacade.getAllBooks();
    }

    @Operation(summary = "Get a book by ID", description = "Provide an ID to lookup a specific book in the library")
    @GetMapping("/{id}")
    public BookDto getBookById(@ApiParam("ID of the book to retrieve") @PathVariable Long id) {
        return libraryFacade.getBookById(id);
    }

    @Operation(summary = "Add a new book", description = "Adds a new book to the library and returns the saved BookDto object")
    @PostMapping
    public BookDto addBook(@ApiParam("BookDto object to be added") @RequestBody BookDto BookDto) {
        return libraryFacade.addBook(BookDto);
    }

    @Operation(summary = "Update an existing book", description = "Updates an existing book by ID with new information from the BookDto object")
    @PutMapping("/{id}")
    public BookDto updateBook(
            @ApiParam("ID of the book to update") @PathVariable Long id,
            @ApiParam( "Updated BookDto object") @RequestBody BookDto updatedBookDto) {
        return libraryFacade.updateBook(id, updatedBookDto);
    }

    @Operation(summary = "Delete a book by ID", description = "Deletes the book with the specified ID from the library")
    @DeleteMapping("/{id}")
    public void deleteBook(@ApiParam("ID of the book to delete") @PathVariable Long id) {
        libraryFacade.deleteBook(id);
    }

    @Operation(summary = "Retrieve all categories", description = "Returns a list of all categories")
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return libraryFacade.getAllCategories();
    }

    @Operation(summary = "Get books by category", description = "Retrieve all books belonging to a specific category")
    @GetMapping("/category")
    public List<BookDto> getBooksByCategory(@RequestParam String category) {
        return libraryFacade.getBooksByCategory(category)
                .stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Add a book with category", description = "Add a new book and associate it with a category")
    @PostMapping("/addWithCategory")
    public BookDto addBookWithCategory(@RequestBody BookDto bookDto, @RequestParam String category) {
        Book book = BookMapper.toEntity(bookDto);
        Book savedBook = libraryFacade.addBookWithCategory(book, category);
        return BookMapper.toDTO(savedBook);
    }

    @Operation(summary = "Mark a book as featured", description = "Marks the book with the specified ID as featured")
    @PutMapping("/{id}/feature")
    public String markBookAsFeatured(@PathVariable Long id) {
        BookDecorator featuredBook = libraryFacade.markBookAsFeatured(id);
        return featuredBook.getDescription();
    }

    @Operation(summary = "Mark a book as bestseller", description = "Marks the book with the specified ID as a bestseller")
    @PutMapping("/{id}/bestseller")
    public String markBookAsBestseller(@PathVariable Long id) {
        BookDecorator bestsellerBook = libraryFacade.markBookAsBestseller(id);
        return bestsellerBook.getDescription();
    }


}