package com.hexaware.librarymanagement.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long bookId;
	private String name;
	private Date dateOfPublication;
	
	@ManyToOne
	@JoinColumn(name="author_id", nullable=false)
	@JsonIgnoreProperties("books")
	private Author author;
	
	@ManyToOne
	@JoinColumn(name="publisher_id", nullable=false)
	@JsonIgnoreProperties("books")
	private Publisher publisher;
	
	public Book() {}

	public Book(String name, Date dateOfPublication, Author author, Publisher publisher) {
		super();
		this.name = name;
		this.dateOfPublication = dateOfPublication;
		this.author = author;
		this.publisher = publisher;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfPublication() {
		return dateOfPublication;
	}

	public void setDateOfPublication(Date dateOfPublication) {
		this.dateOfPublication = dateOfPublication;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	
}
