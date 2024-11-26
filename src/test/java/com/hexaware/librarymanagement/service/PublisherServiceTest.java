package com.hexaware.librarymanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hexaware.librarymanagement.exception.InvalidInputException;
import com.hexaware.librarymanagement.exception.ResourceNotFoundException;
import com.hexaware.librarymanagement.model.Publisher;
import com.hexaware.librarymanagement.repository.PublisherRepository;

public class PublisherServiceTest {

	@InjectMocks
	private PublisherService publisherService;

	@Mock
	private PublisherRepository publisherRepository;

	private Publisher samplePublisher;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		samplePublisher = new Publisher();
		samplePublisher.setPublisherId(1L);
		samplePublisher.setName("Penguin Books");
	}

	@Test
	void shouldGetAllPublishers() {

		List<Publisher> publishers = new ArrayList<>();
		publishers.add(samplePublisher);
		when(publisherRepository.findAll()).thenReturn(publishers);

		List<Publisher> result = publisherService.getAllPublishers();

		assertThat(result).isNotEmpty();
		assertThat(result.size()).isEqualTo(1);
		verify(publisherRepository, times(1)).findAll();
	}

	@Test
	void shouldGetPublisherById() {
	
		when(publisherRepository.findById(1L)).thenReturn(Optional.of(samplePublisher));

		Publisher result = publisherService.getPublisherById(1L);

		assertThat(result).isNotNull();
		assertThat(result.getPublisherId()).isEqualTo(1L);
		assertThat(result.getName()).isEqualTo("Penguin Books");
		verify(publisherRepository, times(1)).findById(1L);
	}

	@Test
	void shouldThrowExceptionWhenPublisherNotFoundById() {

		when(publisherRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> publisherService.getPublisherById(1L));
		verify(publisherRepository, times(1)).findById(1L);
	}

	@Test
	void shouldCreatePublisher() {

		when(publisherRepository.save(any(Publisher.class))).thenReturn(samplePublisher);

		Publisher result = publisherService.createPublisher(samplePublisher);

		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("Penguin Books");
		verify(publisherRepository, times(1)).save(samplePublisher);
	}

	@Test
	void shouldThrowExceptionWhenCreatingPublisherWithoutName() {

		samplePublisher.setName(null);

		assertThrows(InvalidInputException.class, () -> publisherService.createPublisher(samplePublisher));
		verify(publisherRepository, never()).save(any());
	}

	@Test
	void shouldUpdatePublisher() {

		when(publisherRepository.existsById(1L)).thenReturn(true);
		when(publisherRepository.save(any(Publisher.class))).thenReturn(samplePublisher);

		Publisher result = publisherService.updatePublisher(1L, samplePublisher);

		assertThat(result).isNotNull();
		assertThat(result.getPublisherId()).isEqualTo(1L);
		verify(publisherRepository, times(1)).existsById(1L);
		verify(publisherRepository, times(1)).save(samplePublisher);
	}

	@Test
	void shouldThrowExceptionWhenUpdatingNonExistentPublisher() {

		when(publisherRepository.existsById(1L)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> publisherService.updatePublisher(1L, samplePublisher));
		verify(publisherRepository, times(1)).existsById(1L);
		verify(publisherRepository, never()).save(any());
	}

	@Test
	void shouldDeletePublisher() {

		when(publisherRepository.existsById(1L)).thenReturn(true);
		doNothing().when(publisherRepository).deleteById(1L);

		publisherService.deletePublisher(1L);

		verify(publisherRepository, times(1)).existsById(1L);
		verify(publisherRepository, times(1)).deleteById(1L);
	}

	@Test
	void shouldThrowExceptionWhenDeletingNonExistentPublisher() {

		when(publisherRepository.existsById(1L)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> publisherService.deletePublisher(1L));
		verify(publisherRepository, times(1)).existsById(1L);
		verify(publisherRepository, never()).deleteById(anyLong());
	}
}
