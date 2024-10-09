package com.hexaware.librarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.hexaware.librarymanagement.model.Publisher;
import com.hexaware.librarymanagement.service.LibraryManagementService;

@RestController
@RequestMapping("/api/library")
public class LibraryManagementController {
	
	@Autowired
	private LibraryManagementService libraryManagementService;
	
	@PostMapping("/book")
	public Book createBook(@RequestBody Book book) {
		return libraryManagementService.saveBook(book);
	}
	
	@GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return libraryManagementService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        book.setBookId(id);  // Set the ID for the update
        Book updatedBook = libraryManagementService.saveBook(book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        libraryManagementService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/book/search")
    public List<Book> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String publisherName) {
        return libraryManagementService.searchBooks(title, authorName, publisherName);
    }

    // Author Endpoints

    @PostMapping("/author")
    public Author createAuthor(@RequestBody Author author) {
        return libraryManagementService.saveAuthor(author);
    }

    @GetMapping("/author")
    public List<Author> getAllAuthors() {
        return libraryManagementService.getAllAuthors();
    }

    @PutMapping("/author/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        author.setAuthorId(id);  // Set the ID for the update
        Author updatedAuthor = libraryManagementService.saveAuthor(author);
        return ResponseEntity.ok(updatedAuthor);
    }

    // Publisher Endpoints

    @PostMapping("/publisher")
    public Publisher createPublisher(@RequestBody Publisher publisher) {
        return libraryManagementService.savePublisher(publisher);
    }

    @GetMapping("/publisher")
    public List<Publisher> getAllPublishers() {
        return libraryManagementService.getAllPublishers();
    }

    @PutMapping("/publisher/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long id, @RequestBody Publisher publisher) {
        publisher.setPublisherId(id);  // Set the ID for the update
        Publisher updatedPublisher = libraryManagementService.savePublisher(publisher);
        return ResponseEntity.ok(updatedPublisher);
    }

}
