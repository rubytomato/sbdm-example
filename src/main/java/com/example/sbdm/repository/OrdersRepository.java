package com.example.sbdm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.sbdm.domain.Orders;

public interface OrdersRepository extends MongoRepository<Orders, String> {

  Orders findByOrderNumber(Long orderNumber);

  Iterable<Orders> findByCustomerNumber(Long customerNumber);

}
