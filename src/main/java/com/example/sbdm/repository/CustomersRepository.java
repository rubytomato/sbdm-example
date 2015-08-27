package com.example.sbdm.repository;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.sbdm.domain.Customers;

public interface CustomersRepository extends MongoRepository<Customers, String> {

  public static final String FIND =
   "{$or:[" +
   "{'customerName': {$regex: '?0', $options: 'i'}}," +
   "{'contactFirstName': {$regex: '?0', $options: 'i'}}," +
   "{'contactLastName': {$regex: '?0', $options: 'i'}}" +
   "]}";

  Customers findByCustomerNumber(Long customerNumber);

  Iterable<Customers> findByCustomerNameLike(String customerName, Sort sort);

  Long countByCustomerNameLike(String keyword);

  @Query(value = FIND, count = true)
  Long searchCount(String keyword);

  @Query(value = FIND)
  PageImpl<Customers> search(String keyword, Pageable page);

}
