package com.example.sbdm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.sbdm.domain.ProductLines;

public interface ProductLinesRepository extends MongoRepository<ProductLines, String> {

  ProductLines findByProductLine(String productLine);

  Iterable<ProductLines> findByTextDescriptionLike(String name);

}
