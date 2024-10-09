package com.hexaware.librarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.librarymanagement.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

	List<Book> findByName(String bookName);
	List<Book> findByAuthorName(String authorName);
	List<Book> findByPublisherName(String publisherName);

}
