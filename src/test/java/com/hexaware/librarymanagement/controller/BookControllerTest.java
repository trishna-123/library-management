package com.hexaware.librarymanagement.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexaware.librarymanagement.model.Author;
import com.hexaware.librarymanagement.model.Book;
import com.hexaware.librarymanagement.model.Publisher;
import com.hexaware.librarymanagement.service.BookService;

@WebMvcTest(BookController.class)
public class BookControllerTest {

	@Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private BookService bookService;
 
    @Autowired
    private ObjectMapper objectMapper;
 
    private Book sampleBook;
    private Author sampleAuthor;
    private Publisher samplePublisher;
    private Date sampleDateOfPublication;
 
    @BeforeEach
    public void setUp() throws Exception {
        // Initialize the Author
        sampleAuthor = new Author();
        sampleAuthor.setAuthorId(1L);
        sampleAuthor.setName("Craig Walls");
 
        // Initialize the Publisher
        samplePublisher = new Publisher();
        samplePublisher.setPublisherId(1L);
        samplePublisher.setName("Manning");
 
        // Initialize the Book Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        sampleDateOfPublication = dateFormat.parse("2024-01-01");
 
        // Initialize the Book
        sampleBook = new Book();
        sampleBook.setBookId(1L);
        sampleBook.setName("Spring in Action");
        sampleBook.setAuthor(sampleAuthor); // Set Author
        sampleBook.setPublisher(samplePublisher); // Set Publisher
        sampleBook.setDateOfPublication(sampleDateOfPublication); // Set Date of Publication
    }
 
    @Test
    public void testGetAllBooks() throws Exception {
        Mockito.when(bookService.getAllBooks()).thenReturn(Collections.singletonList(sampleBook));
 
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId", is(1)))
                .andExpect(jsonPath("$[0].name", is("Spring in Action")));
    }
 
    @Test
    public void testGetBookById() throws Exception {
        Mockito.when(bookService.getBookById(1L)).thenReturn(sampleBook);
 
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId", is(1)))
                .andExpect(jsonPath("$.name", is("Spring in Action")));
    }
 
    @Test
    public void testCreateBook() throws Exception {
        Mockito.when(bookService.createBook(Mockito.any(Book.class))).thenReturn(sampleBook);
 
        String bookJson = objectMapper.writeValueAsString(sampleBook);
 
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookId", is(1)))
                .andExpect(jsonPath("$.name", is("Spring in Action")));
    }
 
    @Test
    public void testUpdateBook() throws Exception {
        Book updatedBook = new Book();
        updatedBook.setBookId(1L);
        updatedBook.setName("Updated Spring in Action");
        updatedBook.setAuthor(sampleAuthor);
        updatedBook.setPublisher(samplePublisher);
        updatedBook.setDateOfPublication(sampleDateOfPublication);
 
        Mockito.when(bookService.updateBook(Mockito.eq(1L), Mockito.any(Book.class))).thenReturn(updatedBook);
 
        String updatedBookJson = objectMapper.writeValueAsString(updatedBook);
 
        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Spring in Action")));
    }
 
    @Test
    public void testDeleteBook() throws Exception {
        Mockito.doNothing().when(bookService).deleteBook(1L);
 
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }
 
    @Test
    public void testFindBookByName() throws Exception {
        Mockito.when(bookService.findByName("Spring")).thenReturn(Collections.singletonList(sampleBook));
 
        mockMvc.perform(get("/api/books/search/by-name")
                        .param("bookName", "Spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId", is(1)))
                .andExpect(jsonPath("$[0].name", is("Spring in Action")));
    }
}
