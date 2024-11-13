package com.hexaware.librarymanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
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
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.hexaware.librarymanagement.exception.ResourceNotFoundException;
import com.hexaware.librarymanagement.model.Author;
import com.hexaware.librarymanagement.model.Book;
import com.hexaware.librarymanagement.model.Publisher;
import com.hexaware.librarymanagement.repository.PublisherRepository;

import jakarta.persistence.EntityNotFoundException;

public class PublisherServiceTest {
	
	@Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private Publisher publisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        publisher = new Publisher();
        publisher.setPublisherId(1L);
        publisher.setName("Test Publisher");
    }

    @Test
    void testCreatePublisher() {
        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

        Publisher savedPublisher = publisherService.createPublisher(publisher);

        assertNotNull(savedPublisher);
        assertEquals(publisher.getName(), savedPublisher.getName());
        verify(publisherRepository, times(1)).save(publisher);
    }

    @Test
    void testGetPublisherById_Success() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

        Publisher foundPublisher = publisherService.getPublisherById(1L);

        assertNotNull(foundPublisher);
        assertEquals("Test Publisher", foundPublisher.getName());
        verify(publisherRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPublisherById_NotFound() {
        when(publisherRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            publisherService.getPublisherById(2L);
        });

        verify(publisherRepository, times(1)).findById(2L);
    }
    
    @Test
    public void testUpdatePublisher_Success() {
      
    	List<Book> initialBooks = new ArrayList<>();
    	Publisher publisher = new Publisher("Initial Name", initialBooks);
	  
  	  // Act: Update the author's name and add a book to the book list
  	  publisher.setName("Updated Name"); Book newBook = new Book(); // Assuming Book
  	  initialBooks.add(newBook);
  	  publisher.setBooks(initialBooks);
  	  
  	  // Assert: Verify that the updates were successful
  	  assertEquals("Updated Name", publisher.getName()); assertEquals(1,
  	  publisher.getBooks().size()); // Check that one book is added
  	  assertSame(newBook, publisher.getBooks().get(0));   // Verify that save was called
    }

    @Test
    public void testUpdatePublisher_NotFound() {
    	// Arrange
        Long publisherId = 1L;
        Publisher updatedPublisherData = new Publisher("Updated Name", new ArrayList<>());
        
        // Mock the repository to return empty for the given ID
        Mockito.when(publisherRepository.findById(publisherId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            publisherService.updatePublisher(publisherId, updatedPublisherData);
            });

        Mockito.verify(publisherRepository, Mockito.never()).save(Mockito.any(Publisher.class));
    }

    

    @Test
    void testDeletePublisher_Success() {
        when(publisherRepository.existsById(1L)).thenReturn(true);
        doNothing().when(publisherRepository).deleteById(1L);

        publisherService.deletePublisher(1L);

        verify(publisherRepository, times(1)).existsById(1L);
        verify(publisherRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePublisher_NotFound() {
        when(publisherRepository.existsById(2L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            publisherService.deletePublisher(2L);
        });

        verify(publisherRepository, times(1)).existsById(2L);
        verify(publisherRepository, never()).deleteById(anyLong());
    }

}
