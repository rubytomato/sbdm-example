package com.example.sbdm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.example.sbdm.domain.Customers;
import com.example.sbdm.repository.CustomersRepository;
import com.example.sbdm.service.AbstractService;

@Service
public class CustomersService extends AbstractService<Customers> {
  private static Logger logger = LoggerFactory.getLogger(CustomersService.class);

  @Autowired
  private CustomersRepository customersRepository;

  @Override
  protected MongoRepository<Customers, String> getRepository() {
    return customersRepository;
  }

  @Override
  public Customers findByPk(Object...keys) {
    return customersRepository.findByCustomerNumber((Long)keys[0]);
  }

  @Override
  public Iterable<Customers> findByNameLike(String customerName, String sortColumn) {
    Sort sort = new Sort(sortColumn);
    return customersRepository.findByCustomerNameLike(customerName, sort);
  }

  @Override
  public long searchCount(String keyword) {
    return customersRepository.searchCount(keyword);
  }

  @Override
  public Iterable<Customers> search(String keyword, int page, int size, String sortColumn) {
    Pageable pager = new PageRequest(currentPage(page), size, Direction.ASC, sortColumn);
    return customersRepository.search(keyword, pager);
  }

  @Override
  public long count(Customers searchCondition) {
    Criteria criteria = makeCriteria(searchCondition);
    Query query = makeQuery(criteria);
    return doCount(query, Customers.class);
  }

  @Override
  public List<Customers> search(int page, int size, Sort sort, Customers searchCondition) {
    Criteria criteria = makeCriteria(searchCondition);
    Query query= makeQuery(criteria);
    query.skip(calcSkipNum(page, size)).limit(size);
    if (sort != null) {
      query.with(sort);
    }
    return doFind(query, Customers.class);
  }

  @Override
  protected Criteria makeCriteriaByPk(Customers model) {
    return Criteria.where("customerNumber").is(model.getCustomerNumber());
  }

  @Override
  protected Criteria makeCriteria(Customers model) {
    Criteria criteria = null;
    if (model.getCustomerNumber() != null && model.getCustomerNumber() > 0L) {
      criteria = makeCriteria(criteria, "customerNumber", model.getCustomerNumber());
    }
    if (StringUtils.isNotEmpty(model.getCustomerName())) {
      criteria = makeCriteria(criteria, "customerName", model.getCustomerName());
    }
    if (StringUtils.isNotEmpty(model.getPhone())) {
      criteria = makeCriteria(criteria, "phone", model.getPhone());
    }
    if (StringUtils.isNotEmpty(model.getCity())) {
      criteria = makeCriteria(criteria, "city", model.getCity());
    }
    if (StringUtils.isNotEmpty(model.getCountry())) {
      criteria = makeCriteria(criteria, "country", model.getCountry());
    }
    if (StringUtils.isNotEmpty(model.getState())) {
      criteria = makeCriteria(criteria, "state", model.getState());
    }
    if (StringUtils.isNotEmpty(model.getPostalCode())) {
      criteria = makeCriteria(criteria, "postalCode", model.getPostalCode());
    }
    return criteria;
  }

  @Override
  protected Update makeAllUpdate(Customers model) {
    Update update = new Update();
    update.set("customerName", model.getCustomerName());
    update.set("contactLastName", model.getContactLastName());
    update.set("contactFirstName", model.getContactFirstName());
    update.set("phone", model.getPhone());
    update.set("addressLine1", model.getAddressLine1());
    update.set("addressLine2",model.getAddressLine2());
    update.set("city", model.getCity());
    update.set("state", model.getState());
    update.set("postalCode", model.getPostalCode());
    update.set("country", model.getCountry());
    update.set("salesRepEmployeeNumber", model.getSalesRepEmployeeNumber());
    update.set("creditLimit", model.getCreditLimit());
    return update;
  }

}
