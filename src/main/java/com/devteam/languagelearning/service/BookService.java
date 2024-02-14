package com.devteam.languagelearning.service;

import com.devteam.languagelearning.model.Book;
import com.devteam.languagelearning.model.Word;
import com.devteam.languagelearning.persistence.BookRepository;
import com.devteam.languagelearning.persistence.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private WordRepository wordRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    public List<Book> getBooksByUser(long userId) {
        return wordRepository.findBooksByUserId(userId);
    }

    public Book editBook(long id, Book newBook) {
        Book book = this.getBookById(id);
        book.setTitle(newBook.getTitle());
        book.setAuthor(newBook.getAuthor());
        book.setIsbn(newBook.getIsbn());
        book.setLanguage(newBook.getLanguage());
        return bookRepository.save(book);
    }

    // Also sets book_id to null on all words saved for that book
    // Unlikely to delete books in reality, they are shared by all users
    public Book deleteBook(long id) {
        Book book = bookRepository.findById(id).orElseThrow();
        bookRepository.deleteById(id);
        for (Word word : book.getWords()) {
            word.setBook(null);
            wordRepository.save(word);
        }
        return book;
    }

    public Book getOrCreateBook(Book book) {
        Optional<Book> bookOptional;
        if (book.getIsbn() != null) {
            bookOptional = bookRepository.findByIsbn(book.getIsbn());
            if (bookOptional.isEmpty()) {
                bookOptional = bookRepository.findByTitleAndAuthorAndLanguage(book.getTitle(), book.getAuthor(), book.getLanguage());
                if (bookOptional.isEmpty()) {
                    return bookRepository.save(book);
                }
            }
            return bookOptional.get();
        } else {
            bookOptional = bookRepository.findByTitleAndAuthorAndLanguage(book.getTitle(), book.getAuthor(), book.getLanguage());
            return bookOptional.orElseGet(() -> bookRepository.save(book));
        }
    }
}
