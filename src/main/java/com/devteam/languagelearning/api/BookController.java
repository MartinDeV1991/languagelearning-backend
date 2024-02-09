package com.devteam.languagelearning.api;

import com.devteam.languagelearning.model.Book;
import com.devteam.languagelearning.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks()) ;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findBookById(@PathVariable long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookById(id)) ;
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The book with id '" + id +  "' was not found.");
        }

    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(book));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception: " + e);
        }

    }

    @PostMapping("multiple")
    public ResponseEntity<List<Book>> addBooks(@RequestBody List<Book> books) {
        List<Book> savedBooks = new ArrayList<>();
        List<Book> failedBooks = new ArrayList<>();

        for (Book book : books) {
            try {
                savedBooks.add(bookService.addBook(book));
            } catch (Exception e) {
                failedBooks.add(book);
            }
        }
        if (savedBooks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(savedBooks);
        } else if (failedBooks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBooks);
        } else {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(savedBooks);
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<?> editBook(@PathVariable long id, @RequestBody Book book) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(bookService.editBook(id, book));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception: " + e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBook(@PathVariable long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(bookService.deleteBook(id)) ;
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The book with id '" + id +  "' was not found.");
        }
    }
}
