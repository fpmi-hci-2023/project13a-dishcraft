package com.dishcraft.repositories;

import org.springframework.data.repository.CrudRepository;

import com.dishcraft.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
