package com.hexaware.librarymanagement.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class InvalidInputExceptionTest {
	 
    @Test
    public void testExceptionMessage() {

        String expectedMessage = "Invalid input provided";

        InvalidInputException exception = new InvalidInputException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }
 
    @Test
    public void testExceptionInheritance() {

        InvalidInputException exception = new InvalidInputException("Test");

        assertTrue(exception instanceof RuntimeException);
    }
}