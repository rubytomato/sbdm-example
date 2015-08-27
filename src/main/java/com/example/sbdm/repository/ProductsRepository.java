package com.example.sbdm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.sbdm.domain.Products;

public interface ProductsRepository extends MongoRepository<Products, String> {

  Products findByProductCode(String productCode);

  Iterable<Products> findByProductNameLike(String productName);

}
