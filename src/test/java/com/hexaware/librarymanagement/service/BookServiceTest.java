package com.hexaware.librarymanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hexaware.librarymanagement.exception.InvalidInputException;
import com.hexaware.librarymanagement.exception.ResourceNotFoundException;
import com.hexaware.librarymanagement.model.Author;
import com.hexaware.librarymanagement.model.Book;
import com.hexaware.librarymanagement.model.Publisher;
import com.hexaware.librarymanagement.repository.BookRepository;

public class BookServiceTest {

	@InjectMocks
	private BookService bookService;

	@Mock
	private BookRepository bookRepository;

	private Book sampleBook;
	private Author author;
	private Publisher publisher;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		author = new Author();
		author.setAuthorId(1L);
		author.setName("J.K. Rowling");

		publisher = new Publisher();
		publisher.setPublisherId(1L);
		publisher.setName("Bloomsbury");

		sampleBook = new Book();
		sampleBook.setBookId(1L);
		sampleBook.setName("Harry Potter");
		sampleBook.setDateOfPublication(new java.util.Date());
		sampleBook.setAuthor(author);
		sampleBook.setPublisher(publisher);
	}

	@Test
	void shouldCreateBook() {

		when(bookRepository.save(any(Book.class))).thenReturn(sampleBook);

		Book result = bookService.createBook(sampleBook);

		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("Harry Potter");
		verify(bookRepository, times(1)).save(sampleBook);
	}

	@Test
	void shouldThrowExceptionWhenCreatingBookWithEmptyName() {

		sampleBook.setName("");

		assertThrows(InvalidInputException.class, () -> bookService.createBook(sampleBook));
		verify(bookRepository, never()).save(any());
	}

	@Test
	void shouldGetAllBooks() {

		List<Book> books = new ArrayList<>();
		books.add(sampleBook);
		when(bookRepository.findAll()).thenReturn(books);

		List<Book> result = bookService.getAllBooks();

		assertThat(result).isNotEmpty();
		assertThat(result.size()).isEqualTo(1);
		verify(bookRepository, times(1)).findAll();
	}

	@Test
	void shouldGetBookById() {

		when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));

		Book result = bookService.getBookById(1L);

		assertThat(result).isNotNull();
		assertThat(result.getBookId()).isEqualTo(1L);
		verify(bookRepository, times(1)).findById(1L);
	}

	@Test
	void shouldThrowExceptionWhenBookNotFoundById() {

		when(bookRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1L));
		verify(bookRepository, times(1)).findById(1L);
	}

	@Test
	void shouldUpdateBook() {
		
		Book sampleBook = new Book();
		sampleBook.setBookId(1L); // Set the ID explicitly for testing
		sampleBook.setName("Harry Potter");
		sampleBook.setDateOfPublication(new java.util.Date());
		sampleBook.setAuthor(author);
		sampleBook.setPublisher(publisher);

		Book updatedBook = new Book();
		updatedBook.setBookId(1L); // Ensure the ID stays the same as the existing one
		updatedBook.setName("Harry Potter and the Chamber of Secrets");
		updatedBook.setDateOfPublication(new java.util.Date());
		updatedBook.setAuthor(author);
		updatedBook.setPublisher(publisher);

		when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook)); 
		when(bookRepository.save(any(Book.class))).thenReturn(updatedBook); 

		Book result = bookService.updateBook(1L, updatedBook);

		assertThat(result).isNotNull();
		assertThat(result.getBookId()).isEqualTo(1L); 
		assertThat(result.getName()).isEqualTo("Harry Potter and the Chamber of Secrets"); 
		assertThat(result.getAuthor()).isEqualTo(author); 
		assertThat(result.getPublisher()).isEqualTo(publisher); 

		ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
		verify(bookRepository, times(1)).save(bookCaptor.capture()); 

		Book savedBook = bookCaptor.getValue();
		assertThat(savedBook.getName()).isEqualTo("Harry Potter and the Chamber of Secrets");
		assertThat(savedBook.getBookId()).isEqualTo(1L); 
		assertThat(savedBook.getAuthor()).isEqualTo(author);
		assertThat(savedBook.getPublisher()).isEqualTo(publisher);
	}

	@Test
	void shouldThrowExceptionWhenBookNotFound() {
		
		when(bookRepository.findById(1L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> bookService.updateBook(1L, new Book())).isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Book not found with id: 1");
	}

	@Test
	void shouldDeleteBook() {

		when(bookRepository.existsById(1L)).thenReturn(true);
		doNothing().when(bookRepository).deleteById(1L);

		bookService.deleteBook(1L);

		verify(bookRepository, times(1)).existsById(1L);
		verify(bookRepository, times(1)).deleteById(1L);
	}

	@Test
	void shouldThrowExceptionWhenDeletingNonExistentBook() {

		when(bookRepository.existsById(1L)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(1L));
		verify(bookRepository, times(1)).existsById(1L);
		verify(bookRepository, never()).deleteById(anyLong());
	}

	@Test
	void shouldFindBookByName() {

		List<Book> books = new ArrayList<>();
		books.add(sampleBook);
		when(bookRepository.findByName("Harry Potter")).thenReturn(books);

		List<Book> result = bookService.findByName("Harry Potter");

		assertThat(result).isNotEmpty();
		assertThat(result.size()).isEqualTo(1);
		verify(bookRepository, times(1)).findByName("Harry Potter");
	}

	@Test
	void shouldThrowExceptionWhenNoBooksFoundByName() {
		
		when(bookRepository.findByName("Nonexistent Book")).thenReturn(new ArrayList<>());

		assertThrows(ResourceNotFoundException.class, () -> bookService.findByName("Nonexistent Book"));
		verify(bookRepository, times(1)).findByName("Nonexistent Book");
	}

}
