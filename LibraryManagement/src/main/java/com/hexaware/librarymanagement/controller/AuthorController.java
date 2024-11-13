package com.hexaware.librarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.librarymanagement.model.Author;
import com.hexaware.librarymanagement.service.AuthorService;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
	
	@Autowired
    private AuthorService authorService;

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long authorId) {
        Author author = authorService.getAuthorById(authorId);
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author createdAuthor = authorService.createAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long authorId, @RequestBody Author authorDetails) {
        Author updatedAuthor = authorService.updateAuthor(authorId, authorDetails);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId) {
        authorService.deleteAuthor(authorId);
        return ResponseEntity.noContent().build();
    }

}
