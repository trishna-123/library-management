package com.hexaware.librarymanagement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PublisherTest {

	@Test
	void testPublisherConstructorAndFields() {
		
		List<Book> books = new ArrayList<>();
		Publisher publisher = new Publisher("Publisher Name", books);

		// Test field values
		assertNull(publisher.getPublisherId());
		assertEquals("Publisher Name", publisher.getName());
		assertNotNull(publisher.getBooks());
		assertEquals(0, publisher.getBooks().size());
	}

	@Test
	void testSettersAndGetters() {
		Publisher publisher = new Publisher();
		publisher.setPublisherId(1L);
		publisher.setName("New Publisher");
		publisher.setBooks(new ArrayList<>());

		assertEquals(1L, publisher.getPublisherId());
		assertEquals("New Publisher", publisher.getName());
		assertNotNull(publisher.getBooks());
	}
}
