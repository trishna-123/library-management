package com.hexaware.librarymanagement.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public class GlobalExceptionHandlerTest {
	 
    private GlobalExceptionHandler exceptionHandler;
 
    @BeforeEach
    public void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }
 
    @Test
    public void testHandleResourceNotFoundException() {
        
        String errorMessage = "Resource not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        WebRequest webRequest = new ServletWebRequest(mockRequest);
 
        ResponseEntity<Object> response = exceptionHandler.handleResourceNotFoundException(exception, webRequest);
 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(404, body.get("status"));
        assertEquals("Resource Not Found", body.get("error"));
        assertEquals(errorMessage, body.get("message"));
    }
 
    @Test
    public void testHandleInvalidInputException() {
        // Arrange
        String errorMessage = "Invalid input provided";
        InvalidInputException exception = new InvalidInputException(errorMessage);
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        WebRequest webRequest = new ServletWebRequest(mockRequest);
 
        // Act
        ResponseEntity<Object> response = exceptionHandler.handleInvalidInputException(exception, webRequest);
 
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(400, body.get("status"));
        assertEquals("Invalid Input", body.get("error"));
        assertEquals(errorMessage, body.get("message"));
    }
 
    @Test
    public void testHandleGeneralException() {
        
        String generalErrorMessage = "An unexpected error occurred.";
        Exception exception = new Exception("Some internal error");
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        WebRequest webRequest = new ServletWebRequest(mockRequest);
 
        ResponseEntity<Object> response = exceptionHandler.handleGeneralException(exception, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("status"));
        assertEquals("Internal Server Error", body.get("error"));
        assertEquals(generalErrorMessage, body.get("message"));
    }
}