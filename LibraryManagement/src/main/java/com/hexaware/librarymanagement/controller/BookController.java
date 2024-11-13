package com.hexaware.librarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.librarymanagement.model.Author;
import com.hexaware.librarymanagement.model.Book;
import com.hexaware.librarymanagement.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(bookId, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }
    

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search/by-name")
    public ResponseEntity<List<Book>> findByName(@RequestParam String bookName) {
        List<Book> books = bookService.findByName(bookName);
        return ResponseEntity.ok(books);
    }

}
