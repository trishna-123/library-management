package com.hexaware.librarymanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.mockito.MockitoAnnotations;

import com.hexaware.librarymanagement.exception.InvalidInputException;
import com.hexaware.librarymanagement.exception.ResourceNotFoundException;
import com.hexaware.librarymanagement.model.Author;
import com.hexaware.librarymanagement.repository.AuthorRepository;

class AuthorServiceTest {

	@InjectMocks
	private AuthorService authorService;

	@Mock
	private AuthorRepository authorRepository;

	private Author sampleAuthor;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		sampleAuthor = new Author();
		sampleAuthor.setAuthorId(1L);
		sampleAuthor.setName("John Doe");
	}

	@Test
	void shouldGetAllAuthors() {
		
		List<Author> authors = new ArrayList<>();
		authors.add(sampleAuthor);
		when(authorRepository.findAll()).thenReturn(authors);

		List<Author> result = authorService.getAllAuthors();

		assertThat(result).isNotEmpty();
		assertThat(result.size()).isEqualTo(1);
		verify(authorRepository, times(1)).findAll();
	}

	@Test
	void shouldGetAuthorById() {

		when(authorRepository.findById(1L)).thenReturn(Optional.of(sampleAuthor));

		Author result = authorService.getAuthorById(1L);

		assertThat(result).isNotNull();
		assertThat(result.getAuthorId()).isEqualTo(1L);
		assertThat(result.getName()).isEqualTo("John Doe");
		verify(authorRepository, times(1)).findById(1L);
	}

	@Test
	void shouldThrowExceptionWhenAuthorNotFoundById() {

		when(authorRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> authorService.getAuthorById(1L));
		verify(authorRepository, times(1)).findById(1L);
	}

	@Test
	void shouldCreateAuthor() {

		when(authorRepository.save(any(Author.class))).thenReturn(sampleAuthor);

		Author result = authorService.createAuthor(sampleAuthor);

		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("John Doe");
		verify(authorRepository, times(1)).save(sampleAuthor);
	}

	@Test
	void shouldThrowExceptionWhenCreatingAuthorWithoutName() {

		sampleAuthor.setName(null);

		assertThrows(InvalidInputException.class, () -> authorService.createAuthor(sampleAuthor));
		verify(authorRepository, never()).save(any());
	}

	@Test
	void shouldUpdateAuthor() {

		when(authorRepository.existsById(1L)).thenReturn(true);
		when(authorRepository.save(any(Author.class))).thenReturn(sampleAuthor);

		Author result = authorService.updateAuthor(1L, sampleAuthor);

		assertThat(result).isNotNull();
		assertThat(result.getAuthorId()).isEqualTo(1L);
		verify(authorRepository, times(1)).existsById(1L);
		verify(authorRepository, times(1)).save(sampleAuthor);
	}

	@Test
	void shouldThrowExceptionWhenUpdatingNonExistentAuthor() {

		when(authorRepository.existsById(1L)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> authorService.updateAuthor(1L, sampleAuthor));
		verify(authorRepository, times(1)).existsById(1L);
		verify(authorRepository, never()).save(any());
	}

	@Test
	void shouldDeleteAuthor() {

		when(authorRepository.existsById(1L)).thenReturn(true);
		doNothing().when(authorRepository).deleteById(1L);

		authorService.deleteAuthor(1L);

		verify(authorRepository, times(1)).existsById(1L);
		verify(authorRepository, times(1)).deleteById(1L);
	}

	@Test
	void shouldThrowExceptionWhenDeletingNonExistentAuthor() {

		when(authorRepository.existsById(1L)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> authorService.deleteAuthor(1L));
		verify(authorRepository, times(1)).existsById(1L);
		verify(authorRepository, never()).deleteById(anyLong());
	}

}
