package com.hexaware.librarymanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.hexaware.librarymanagement.repository.AuthorRepository;

import jakarta.persistence.EntityNotFoundException;

class AuthorServiceTest {
	
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;


    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author(null, null);
        author.setAuthorId(1L);
        author.setName("Test Author");
    }

    @Test
    void testCreateAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author savedAuthor = authorService.createAuthor(author);

        assertNotNull(savedAuthor);
        assertEquals("Test Author", savedAuthor.getName());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testFindAuthorById_Success() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Author foundAuthor = authorService.getAuthorById(1L);

        assertNotNull(foundAuthor);
        assertEquals("Test Author", foundAuthor.getName());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAuthorById_NotFound() {
        when(authorRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            authorService.getAuthorById(2L);
        });

        verify(authorRepository, times(1)).findById(2L);
    }
    
	
	  @Test 
	  public void testUpdateAuthor_Success() { // Arrange: Create an initial Author
	  List<Book> initialBooks = new ArrayList<>();
	  Author author = new Author("Initial Name", initialBooks);
	  
	  // Act: Update the author's name and add a book to the book list
	  author.setName("Updated Name"); Book newBook = new Book(); // Assuming Book
	  initialBooks.add(newBook);
	  author.setBooks(initialBooks);
	  
	  // Assert: Verify that the updates were successful
	  assertEquals("Updated Name", author.getName()); assertEquals(1,
	  author.getBooks().size()); // Check that one book is added
	  assertSame(newBook, author.getBooks().get(0)); 
	  }
	 
    
    @Test
    public void testUpdateAuthor_NotFound() {
        // Arrange
        Long authorId = 1L;
        Author updatedAuthorData = new Author("Updated Name", new ArrayList<>());
        
        // Mock the repository to return empty for the given ID
        Mockito.when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            authorService.updateAuthor(authorId, updatedAuthorData);
            });

        Mockito.verify(authorRepository, Mockito.never()).save(Mockito.any(Author.class));
    }



    @Test
    void testDeleteAuthor_Success() {
        when(authorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(authorRepository).deleteById(1L);

        authorService.deleteAuthor(1L);

        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAuthor_NotFound() {
        when(authorRepository.existsById(2L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            authorService.deleteAuthor(2L);
        });

        verify(authorRepository, times(1)).existsById(2L);
        verify(authorRepository, never()).deleteById(anyLong());
    }

}
