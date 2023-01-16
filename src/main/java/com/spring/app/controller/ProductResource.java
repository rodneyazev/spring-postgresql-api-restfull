package com.spring.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.app.model.Product;
import com.spring.app.repository.ProductRepository;

@RestController
public class ProductResource {
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/product")
	public CollectionModel<Product> productListMany() {
		List<Product> list = productRepository.findAll();
		for(Product prod : list) {
			Link link = linkTo(methodOn(ProductResource.class).productListOne(prod.getId())).withSelfRel();
			prod.add(link);
		}
		Link link = linkTo(methodOn(ProductResource.class).productListMany()).withSelfRel();
		return CollectionModel.of(list, link);
	}
	
	@GetMapping("/product/{id}")
	public EntityModel<Product> productListOne(@PathVariable(value="id") long id) {
		Link link = linkTo(methodOn(ProductResource.class).productListOne(id)).withSelfRel();
		Product prod = productRepository.findById(id);
		prod.add(link);
		return EntityModel.of(prod);
	}
	
	@PostMapping("/product")	
	public Product productSave(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	@PutMapping("/product/{id}")	
	public Product productUpdate(@RequestBody Product product, @PathVariable(value="id") long id) {
		product.setId(id);
		return productRepository.save(product);
	}
	
	@DeleteMapping("/product/{id}")
	public void productDelete(@PathVariable(value="id") long id) {
		productRepository.deleteById(id);
	}
	
}
