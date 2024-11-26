package com.hexaware.librarymanagement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class BookTest {
	
	@Test
    void testBookConstructorAndFields() {
        
        Author author = new Author("Author Name", null);
        Publisher publisher = new Publisher("Publisher Name", null);
        Date dateOfPublication = new Date();

        Book book = new Book("Book Title", dateOfPublication, author, publisher);

        // Test field values
        assertNull(book.getBookId());
        assertEquals("Book Title", book.getName());
        assertEquals(dateOfPublication, book.getDateOfPublication());
        assertEquals(author, book.getAuthor());
        assertEquals(publisher, book.getPublisher());
    }

    @Test
    void testSettersAndGetters() {
        Book book = new Book();
        book.setBookId(1L);
        book.setName("Updated Title");
        Date newDate = new Date();
        book.setDateOfPublication(newDate);

        Author newAuthor = new Author("New Author", null);
        Publisher newPublisher = new Publisher("New Publisher", null);
        book.setAuthor(newAuthor);
        book.setPublisher(newPublisher);

        assertEquals(1L, book.getBookId());
        assertEquals("Updated Title", book.getName());
        assertEquals(newDate, book.getDateOfPublication());
        assertEquals(newAuthor, book.getAuthor());
        assertEquals(newPublisher, book.getPublisher());
    }

}
