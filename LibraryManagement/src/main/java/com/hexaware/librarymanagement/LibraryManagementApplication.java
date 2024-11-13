package com.hexaware.librarymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.hexaware.librarymanagement.model")
public class LibraryManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

}
