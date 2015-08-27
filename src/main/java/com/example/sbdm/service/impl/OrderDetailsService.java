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

import com.example.sbdm.domain.OrderDetails;
import com.example.sbdm.repository.OrderDetailsRepository;
import com.example.sbdm.service.AbstractService;

@Service
public class OrderDetailsService extends AbstractService<OrderDetails> {
  private static Logger logger = LoggerFactory.getLogger(OrderDetailsService.class);

  @Autowired
  private OrderDetailsRepository orderDetailsRepository;

  @Override
  protected MongoRepository<OrderDetails, String> getRepository() {
    return orderDetailsRepository;
  }

  @Override
  public OrderDetails findByPk(Object...keys) {
    return orderDetailsRepository.findByOrderNumberAndProductCode((Long)keys[0], (String)keys[1]);
  }

  @Override
  public Iterable<OrderDetails> findByNameLike(String name, String sortColumn) {
    return null;
  }

  @Override
  public long searchCount(String keyword) {
    return 0;
  }

  @Override
  public Iterable<OrderDetails> search(String keyword, int page, int size, String sort) {
    return null;
  }

  public static final Sort SORT_OLN_ASC = new Sort(Sort.Direction.ASC, "orderLineNumber");
  public static final Sort SORT_ON_ASC_OLN_DESC = new Sort(Sort.Direction.ASC, "orderNumber").and(new Sort(Sort.Direction.DESC, "orderLineNumber"));

  public Iterable<OrderDetails> findByOrderNumber(Long orderNumber) {
    return orderDetailsRepository.findByOrderNumber(orderNumber, SORT_OLN_ASC);
  }

  public Iterable<OrderDetails> findByProductCode(String productCode) {
    return orderDetailsRepository.findByProductCode(productCode, SORT_OLN_ASC);
  }

  @Override
  public long count(OrderDetails searchCondition) {
    Criteria criteria = makeCriteria(searchCondition);
    Query query= makeQuery(criteria);
    return doCount(query, OrderDetails.class);
  }

  @Override
  public List<OrderDetails> search(int page, int size, Sort sort, OrderDetails searchCondition) {
    Criteria criteria = makeCriteria(searchCondition);
    Query query= makeQuery(criteria);
    query.skip(calcSkipNum(page, size)).limit(size);
    if (sort != null) {
      query.with(sort);
    }
    return doFind(query, OrderDetails.class);
  }

  @Override
  protected Criteria makeCriteriaByPk(OrderDetails model) {
    return Criteria.where("orderNumber").is(model.getOrderNumber()).and("orderLineNumber").is(model.getOrderLineNumber());
  }

  @Override
  protected Criteria makeCriteria(OrderDetails model) {
    Criteria criteria = null;
    if (model.getOrderNumber() != null && model.getOrderNumber() > 0L) {
      criteria = makeCriteria(criteria, "orderNumber", model.getOrderNumber());
    }
    if (model.getOrderLineNumber() != null && model.getOrderLineNumber() > 0) {
      criteria = makeCriteria(criteria, "orderLineNumber", model.getOrderLineNumber());
    }
    if (StringUtils.isNotEmpty(model.getProductCode())) {
      criteria = makeCriteria(criteria, "productCode", model.getProductCode());
    }
    return criteria;
  }

  @Override
  protected Update makeAllUpdate(OrderDetails model) {
    Update update = new Update();
    update.set("orderNumber", model.getOrderNumber());
    update.set("productCode", model.getProductCode());
    update.set("quantityOrdered", model.getQuantityOrdered());
    update.set("priceEach", model.getPriceEach());
    update.set("customerNumber", model.getOrderLineNumber());
    return update;
  }

}
