package com.devteam.languagelearning.api;

import com.devteam.languagelearning.model.Book;
import com.devteam.languagelearning.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PutExchange;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> findAllBooks() {return bookService.getAllBooks();}

    @GetMapping("{id}")
    public Book findBookById(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PostMapping("multiple")
    public List<Book> addBooks(@RequestBody List<Book> books) {
        List<Book> savedBooks = new ArrayList<>();
        for (Book book : books) {
            savedBooks.add(bookService.addBook(book));
        }
        return savedBooks;
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editBook(@PathVariable long id, @RequestBody Book book) {
        try {
            return ResponseEntity.status(200).body(bookService.editBook(id, book));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Exception: " + e);
        }

    }

    @DeleteMapping("{id}")
    public Book deleteBook(@PathVariable long id) {
        return bookService.deleteBook(id);
    }
}
