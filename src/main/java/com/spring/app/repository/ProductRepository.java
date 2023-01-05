package com.spring.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.app.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findById(long id);
	
}
