package com.example.sbdm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.sbdm.domain.Payments;

public interface PaymentsRepository extends MongoRepository<Payments, String> {

  Payments findByCustomerNumberAndCheckNumber(Long customerNumber, String checkNumber);

}
