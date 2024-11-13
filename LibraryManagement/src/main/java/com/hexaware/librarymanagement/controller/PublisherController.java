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

import com.hexaware.librarymanagement.model.Publisher;
import com.hexaware.librarymanagement.service.PublisherService;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
	
    @Autowired
    private PublisherService publisherService;

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("/{publisherId}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long publisherId) {
        Publisher publisher = publisherService.getPublisherById(publisherId);
        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        Publisher createdPublisher = publisherService.createPublisher(publisher);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPublisher);
    }

    @PutMapping("/{publisherId}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long publisherId, @RequestBody Publisher publisherDetails) {
        Publisher updatedPublisher = publisherService.updatePublisher(publisherId, publisherDetails);
        return ResponseEntity.ok(updatedPublisher);
    }

    @DeleteMapping("/{publisherId}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long publisherId) {
        publisherService.deletePublisher(publisherId);
        return ResponseEntity.noContent().build();
    }

}
