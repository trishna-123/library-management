package com.hexaware.librarymanagement.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ResourceNotFoundExceptionTest {
	 
    @Test
    public void testExceptionMessage() {

        String expectedMessage = "Resource not found";

        ResourceNotFoundException exception = new ResourceNotFoundException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }
 
    @Test
    public void testExceptionInheritance() {

        ResourceNotFoundException exception = new ResourceNotFoundException("Test");

        assertTrue(exception instanceof RuntimeException);
    }
}