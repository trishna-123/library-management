package com.hexaware.librarymanagement.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.hamcrest.Matchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexaware.librarymanagement.model.Author;
import com.hexaware.librarymanagement.service.AuthorService;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

	@Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private AuthorService authorService;
 
    @Autowired
    private ObjectMapper objectMapper;
 
    private Author sampleAuthor;
 
    @BeforeEach
    public void setUp() {
        sampleAuthor = new Author();
        sampleAuthor.setAuthorId(1L);
        sampleAuthor.setName("John Doe");
        sampleAuthor.setBooks(Collections.emptyList());
    }
 
    @Test
    public void testGetAllAuthors() throws Exception {
        List<Author> authors = Arrays.asList(sampleAuthor);
        when(authorService.getAllAuthors()).thenReturn(authors);
 
        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[0].authorId", is(sampleAuthor.getAuthorId().intValue())))
                .andExpect(jsonPath("$[0].name", is(sampleAuthor.getName())));
 
        verify(authorService, times(1)).getAllAuthors();
    }
 
    @Test
    public void testGetAuthorById() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(sampleAuthor);
 
        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorId", is(sampleAuthor.getAuthorId().intValue())))
                .andExpect(jsonPath("$.name", is(sampleAuthor.getName())));
 
        verify(authorService, times(1)).getAuthorById(1L);
    }
 
    @Test
    public void testCreateAuthor() throws Exception {
        when(authorService.createAuthor(Mockito.any(Author.class))).thenReturn(sampleAuthor);
 
        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAuthor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.authorId", is(sampleAuthor.getAuthorId().intValue())))
                .andExpect(jsonPath("$.name", is(sampleAuthor.getName())));
 
        verify(authorService, times(1)).createAuthor(Mockito.any(Author.class));
    }
 
    @Test
    public void testUpdateAuthor() throws Exception {
        sampleAuthor.setName("Updated Name");
        when(authorService.updateAuthor(eq(1L), Mockito.any(Author.class))).thenReturn(sampleAuthor);
 
        mockMvc.perform(put("/api/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorId", is(sampleAuthor.getAuthorId().intValue())))
                .andExpect(jsonPath("$.name", is("Updated Name")));
 
        verify(authorService, times(1)).updateAuthor(eq(1L), Mockito.any(Author.class));
    }
 
    @Test
    public void testDeleteAuthor() throws Exception {
        doNothing().when(authorService).deleteAuthor(1L);
 
        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isNoContent());
 
        verify(authorService, times(1)).deleteAuthor(1L);
    }
}
