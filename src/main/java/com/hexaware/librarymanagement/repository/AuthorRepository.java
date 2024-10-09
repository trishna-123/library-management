package com.hexaware.librarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.librarymanagement.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{
	
}
