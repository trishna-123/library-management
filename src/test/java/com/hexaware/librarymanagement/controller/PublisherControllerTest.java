package com.hexaware.librarymanagement.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexaware.librarymanagement.model.Publisher;
import com.hexaware.librarymanagement.service.PublisherService;

@WebMvcTest(PublisherController.class)
public class PublisherControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private PublisherService publisherService;
 
    @Autowired
    private ObjectMapper objectMapper;
 
    private Publisher samplePublisher;
 
    @BeforeEach
    public void setUp() {
        samplePublisher = new Publisher();
        samplePublisher.setPublisherId(1L);
        samplePublisher.setName("O'Reilly Media");
        samplePublisher.setBooks(Collections.emptyList());
    }
 
    @Test
    public void testGetAllPublishers() throws Exception {
        Mockito.when(publisherService.getAllPublishers()).thenReturn(Collections.singletonList(samplePublisher));
 
        mockMvc.perform(get("/api/publishers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].publisherId", is(1)))
                .andExpect(jsonPath("$[0].name", is("O'Reilly Media")));
    }
 
    @Test
    public void testGetPublisherById() throws Exception {
        Mockito.when(publisherService.getPublisherById(1L)).thenReturn(samplePublisher);
 
        mockMvc.perform(get("/api/publishers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publisherId", is(1)))
                .andExpect(jsonPath("$.name", is("O'Reilly Media")));
    }
 
    @Test
    public void testCreatePublisher() throws Exception {
        Mockito.when(publisherService.createPublisher(Mockito.any(Publisher.class))).thenReturn(samplePublisher);
 
        String publisherJson = objectMapper.writeValueAsString(samplePublisher);
 
        mockMvc.perform(post("/api/publishers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(publisherJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.publisherId", is(1)))
                .andExpect(jsonPath("$.name", is("O'Reilly Media")));
    }
 
    @Test
    public void testUpdatePublisher() throws Exception {
        Publisher updatedPublisher = new Publisher();
        updatedPublisher.setPublisherId(1L);
        updatedPublisher.setName("Updated Publisher");
 
        Mockito.when(publisherService.updatePublisher(Mockito.eq(1L), Mockito.any(Publisher.class))).thenReturn(updatedPublisher);
 
        String updatedPublisherJson = objectMapper.writeValueAsString(updatedPublisher);
 
        mockMvc.perform(put("/api/publishers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedPublisherJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publisherId", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Publisher")));
    }
 
    @Test
    public void testDeletePublisher() throws Exception {
        Mockito.doNothing().when(publisherService).deletePublisher(1L);
 
        mockMvc.perform(delete("/api/publishers/1"))
                .andExpect(status().isNoContent());
    }
}
