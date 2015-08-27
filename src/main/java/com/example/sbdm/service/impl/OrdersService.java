package com.example.sbdm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.example.sbdm.domain.Orders;
import com.example.sbdm.repository.OrdersRepository;
import com.example.sbdm.service.AbstractService;

@Service
public class OrdersService extends AbstractService<Orders> {
  private static Logger logger = LoggerFactory.getLogger(OrdersService.class);

  @Autowired
  private OrdersRepository ordersRepository;

  @Override
  protected MongoRepository<Orders, String> getRepository() {
    return ordersRepository;
  }

  @Override
  public Orders findByPk(Object...keys) {
    return ordersRepository.findByOrderNumber((Long)keys[0]);
  }

  @Override
  public Iterable<Orders> findByNameLike(String name, String sortColumn) {
    return null;
  }

  @Override
  public long searchCount(String keyword) {
    return 0;
  }

  @Override
  public Iterable<Orders> search(String keyword, int page, int size, String sort) {
    return null;
  }

  public Iterable<Orders> findByCustomerNumber(Long customerNumber) {
    return ordersRepository.findByCustomerNumber(customerNumber);
  }

  @Override
  public long count(Orders searchCondition) {
    Criteria criteria = makeCriteria(searchCondition);
    Query query= makeQuery(criteria);
    return doCount(query, Orders.class);
  }

  @Override
  public List<Orders> search(int page, int size, Sort sort, Orders searchCondition) {
    Criteria criteria = makeCriteria(searchCondition);
    Query query= makeQuery(criteria);
    query.skip(calcSkipNum(page, size)).limit(size);
    if (sort != null) {
      query.with(sort);
    }
    return doFind(query, Orders.class);
  }

  @Override
  protected Criteria makeCriteriaByPk(Orders model) {
    return Criteria.where("orderNumber").is(model.getOrderNumber());
  }

  @Override
  protected Criteria makeCriteria(Orders model) {
    Criteria criteria = null;
    if (model.getOrderNumber() != null && model.getOrderNumber() > 0L) {
      criteria = makeCriteria(criteria, "orderNumber", model.getOrderNumber());
    }
    if (model.getCustomerNumber() != null && model.getCustomerNumber() > 0L) {
      criteria = makeCriteria(criteria, "customerNumber", model.getCustomerNumber());
    }
    if (StringUtils.isNotEmpty(model.getStatus())) {
      criteria = makeCriteria(criteria, "status", model.getStatus());
    }
    return criteria;
  }

  @Override
  protected Update makeAllUpdate(Orders model) {
    Update update = new Update();
    update.set("orderNumber", model.getOrderNumber());
    if (model.getOrderDate() == null) {
      update.unset("orderDate");
    } else {
      update.set("orderDate", model.getOrderDate());
    }
    if (model.getRequiredDate() == null) {
      update.unset("requiredDate");
    } else {
      update.set("requiredDate", model.getRequiredDate());
    }
    if (model.getShippedDate() == null) {
      update.unset("shippedDate");
    } else {
      update.set("shippedDate", model.getShippedDate());
    }
    if (model.getStatus() == null) {
      update.unset("status");
    } else {
      update.set("status", model.getStatus());
    }
    if (model.getComments() == null) {
      update.unset("comments");
    } else {
      update.set("comments", model.getComments());
    }
    update.set("customerNumber",model.getCustomerNumber());
    return update;
  }

}
