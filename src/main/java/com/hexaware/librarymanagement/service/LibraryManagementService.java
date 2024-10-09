package com.hexaware.librarymanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.librarymanagement.model.Author;
import com.hexaware.librarymanagement.model.Book;
import com.hexaware.librarymanagement.model.Publisher;
import com.hexaware.librarymanagement.repository.AuthorRepository;
import com.hexaware.librarymanagement.repository.BookRepository;
import com.hexaware.librarymanagement.repository.PublisherRepository;

@Service
public class LibraryManagementService {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private PublisherRepository publisherRepository;
	
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    // Retrieve all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Retrieve a book by ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Delete a book by ID
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // Search books by title, author name, or publisher name
    public List<Book> searchBooks(String bookName, String authorName, String publisherName) {
        if (bookName != null && !bookName.isEmpty()) {
            return bookRepository.findByName(bookName);
        }
        if (authorName != null && !authorName.isEmpty()) {
            return bookRepository.findByAuthorName(authorName);
        }
        if (publisherName != null && !publisherName.isEmpty()) {
            return bookRepository.findByPublisherName(publisherName);
        }
        return getAllBooks();
    }

    // Create or update an author
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    // Retrieve all authors
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Create or update a publisher
    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    // Retrieve all publishers
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

	
}
