package com.hexaware.librarymanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hexaware.librarymanagement.exception.ResourceNotFoundException;
import com.hexaware.librarymanagement.model.Author;
import com.hexaware.librarymanagement.model.Book;
import com.hexaware.librarymanagement.model.Publisher;
import com.hexaware.librarymanagement.repository.AuthorRepository;
import com.hexaware.librarymanagement.repository.BookRepository;

import jakarta.persistence.EntityNotFoundException;

public class BookServiceTest {
	
	@Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setBookId(1L);
        book.setName("Test Book");
        book.setDateOfPublication(new Date());
    }

    @Test
    void testFindByName_Success() {
        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookRepository.findByName("Test Book")).thenReturn(books);

        List<Book> foundBooks = bookService.findByName("Test Book");

        assertFalse(foundBooks.isEmpty());
        assertEquals(1, foundBooks.size());
        assertEquals("Test Book", foundBooks.get(0).getName());
        verify(bookRepository, times(1)).findByName("Test Book");
    }

    @Test
    void testFindByName_NotFound() {
        when(bookRepository.findByName("Non-Existent Book")).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.findByName("Non-Existent Book");
        });

        verify(bookRepository, times(1)).findByName("Non-Existent Book");
    }

    @Test
    void testCreateBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.createBook(book);

        assertNotNull(savedBook);
        assertEquals(book.getName(), savedBook.getName());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testGetBookById_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book foundBook = bookService.getBookById(1L);

        assertNotNull(foundBook);
        assertEquals("Test Book", foundBook.getName());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getBookById(2L);
        });

        verify(bookRepository, times(1)).findById(2L);
    }
    
    @Test
    void testUpdateBook_Success() {
        Long bookId = 1L;
        Book existingBook = new Book("Original Book", null, null, null);
        Book updatedBookDetails = new Book("Updated Book", null, null, null);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        Book result = bookService.updateBook(bookId, updatedBookDetails);

        assertEquals("Updated Book", result.getName());
        verify(bookRepository).save(existingBook);
    }

    @Test
    void testUpdateBook_NotFound() {
        Long bookId = 1L;
        Book updatedBookDetails = new Book("Updated Book", null, null, null);

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.updateBook(bookId, updatedBookDetails);
        });

        verify(bookRepository, never()).save(any(Book.class));
    }

    

    @Test
    void testDeleteBook_Success() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookRepository.existsById(2L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.deleteBook(2L);
        });

        verify(bookRepository, times(1)).existsById(2L);
        verify(bookRepository, never()).deleteById(anyLong());
    }

}
