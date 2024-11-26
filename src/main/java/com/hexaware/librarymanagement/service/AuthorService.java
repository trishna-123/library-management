package com.hexaware.librarymanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.librarymanagement.exception.InvalidInputException;
import com.hexaware.librarymanagement.exception.ResourceNotFoundException;
import com.hexaware.librarymanagement.model.Author;
import com.hexaware.librarymanagement.repository.AuthorRepository;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	// Get all authors
	public List<Author> getAllAuthors() {
		return authorRepository.findAll();
	}

	// Get an author by Id
	public Author getAuthorById(Long authorId) {
		return authorRepository.findById(authorId)
				.orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + authorId));
	}

	// Create an author
	public Author createAuthor(Author author) {
		if (author.getName() == null) {
			throw new InvalidInputException("Author name must be provided.");
		} else
		return authorRepository.save(author);
	}

	// Update an author
	public Author updateAuthor(Long authorId, Author authorDetails) {
		if (authorRepository.existsById(authorId)) {
			authorDetails.setAuthorId(authorId);
			return authorRepository.save(authorDetails);
		} else 
			throw new ResourceNotFoundException("Author not found with id: " + authorId);
	}

	// Delete an author
	public void deleteAuthor(Long authorId) {
		if (!authorRepository.existsById(authorId)) {
			throw new ResourceNotFoundException("Author not found with id: " + authorId);
		}
		authorRepository.deleteById(authorId);
	}

}
