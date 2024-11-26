package com.hexaware.librarymanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.librarymanagement.exception.InvalidInputException;
import com.hexaware.librarymanagement.exception.ResourceNotFoundException;
import com.hexaware.librarymanagement.model.Book;
import com.hexaware.librarymanagement.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
    private BookRepository bookRepository;

    // Create a new book
    public Book createBook(Book book) {
    	if(book.getName() == null || book.getName().isEmpty()) {
    		throw new InvalidInputException("Book name must not be null or empty");
    	} else
        return bookRepository.save(book);
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get a book by ID
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));
    }

    // Update a book
    public Book updateBook(Long bookId, Book bookDetails) {
        Optional<Book> existingBook = bookRepository.findById(bookId);
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setName(bookDetails.getName());
            book.setDateOfPublication(bookDetails.getDateOfPublication());
            book.setAuthor(bookDetails.getAuthor());
            book.setPublisher(bookDetails.getPublisher());
            return bookRepository.save(book);
        } else
        	throw new ResourceNotFoundException("Book not found with id: " +bookId);
    }

    // Delete a book
    public void deleteBook(Long id) {
    	if(!bookRepository.existsById(id)) {
    		throw new ResourceNotFoundException("Book with ID " + id + " not found");
    	}
        bookRepository.deleteById(id);
    }

    //Search a book by it's name
    public List<Book> findByName(String bookName) {
    	List<Book> books = bookRepository.findByName(bookName);
    	if(books.isEmpty()) {
    		throw new ResourceNotFoundException("No books found with name: " +bookName);
    	} else
    	return books;
    }
    
}
