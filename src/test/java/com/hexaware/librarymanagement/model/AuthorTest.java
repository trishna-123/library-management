package com.hexaware.librarymanagement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AuthorTest {

	@Test
	void testAuthorConstructorAndFields() {

		List<Book> books = new ArrayList<>();
		Author author = new Author("Author Name", books);

		// Test field values
		assertNull(author.getAuthorId());
		assertEquals("Author Name", author.getName());
		assertNotNull(author.getBooks());
		assertEquals(0, author.getBooks().size());
	}

	@Test
	void testSettersAndGetters() {
		Author author = new Author();
		author.setAuthorId(1L);
		author.setName("New Author");
		author.setBooks(new ArrayList<>());

		assertEquals(1L, author.getAuthorId());
		assertEquals("New Author", author.getName());
		assertNotNull(author.getBooks());
	}

}
