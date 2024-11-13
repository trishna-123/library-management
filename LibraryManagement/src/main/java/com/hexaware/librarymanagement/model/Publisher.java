package com.hexaware.librarymanagement.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Publisher {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long publisherId;
	private String name;
	
	@OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
	@JsonIgnore
	@JsonManagedReference
	private List<Book> books;
	
	public Publisher () {}

	public Publisher(String name, List<Book> books) {
		super();
		this.name = name;
		this.books = books;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	
}
