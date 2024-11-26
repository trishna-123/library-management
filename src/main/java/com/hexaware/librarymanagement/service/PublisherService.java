package com.hexaware.librarymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.librarymanagement.exception.InvalidInputException;
import com.hexaware.librarymanagement.exception.ResourceNotFoundException;
import com.hexaware.librarymanagement.model.Publisher;
import com.hexaware.librarymanagement.repository.PublisherRepository;

@Service
public class PublisherService {
	
	@Autowired
    private PublisherRepository publisherRepository;

	//Get all publishers
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    //Get a publisher by Id
    public Publisher getPublisherById(Long publisherId) {
        return publisherRepository.findById(publisherId)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: " + publisherId));
    }

    //Create a publisher
    public Publisher createPublisher(Publisher publisher) {
        if (publisher.getName() == null) {
            throw new InvalidInputException("Publisher name must be provided.");
        } else
        return publisherRepository.save(publisher);
    }

    //Update a publisher
    public Publisher updatePublisher(Long publisherId, Publisher publisherDetails) {
        if (publisherRepository.existsById(publisherId)) {
            publisherDetails.setPublisherId(publisherId);
            return publisherRepository.save(publisherDetails);
        } else 
            throw new ResourceNotFoundException("Publisher not found with id: " + publisherId);
    }

    //Delete a publisher
    public void deletePublisher(Long publisherId) {
        if (!publisherRepository.existsById(publisherId)) {
            throw new ResourceNotFoundException("Publisher not found with id: " + publisherId);
        }
        publisherRepository.deleteById(publisherId);
    }

}
