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

import com.example.sbdm.domain.Payments;
import com.example.sbdm.repository.PaymentsRepository;
import com.example.sbdm.service.AbstractService;

@Service
public class PaymentsService extends AbstractService<Payments> {
  private static Logger logger = LoggerFactory.getLogger(PaymentsService.class);

  @Autowired
  private PaymentsRepository paymentsRepository;

  @Override
  protected MongoRepository<Payments, String> getRepository() {
    return paymentsRepository;
  }

  @Override
  public Payments findByPk(Object... keys) {
    return paymentsRepository.findByCustomerNumberAndCheckNumber((Long)keys[0], (String)keys[1]);
  }

  @Override
  public Iterable<Payments> findByNameLike(String name, String sortColumn) {
    return null;
  }

  @Override
  public long searchCount(String keyword) {
    return 0;
  }

  @Override
  public Iterable<Payments> search(String keyword, int page, int size, String sort) {
    return null;
  }

  @Override
  public long count(Payments searchCondition) {
    Criteria criteria = makeCriteria(searchCondition);
    Query query= makeQuery(criteria);
    return doCount(query, Payments.class);
  }

  @Override
  public List<Payments> search(int page, int size, Sort sort, Payments searchCondition) {
    Criteria criteria = makeCriteria(searchCondition);
    Query query= makeQuery(criteria);
    query.skip(calcSkipNum(page, size)).limit(size);
    if (sort != null) {
      query.with(sort);
    }
    return doFind(query, Payments.class);
  }

  @Override
  protected Criteria makeCriteriaByPk(Payments model) {
    return Criteria.where("customerNumber").is(model.getCustomerNumber()).and("checkNumber").is(model.getCheckNumber());
  }

  @Override
  protected Criteria makeCriteria(Payments model) {
    Criteria criteria = null;
    if (model.getCustomerNumber() != null && model.getCustomerNumber() > 0) {
      criteria = makeCriteria(criteria, "customerNumber", model.getCustomerNumber());
    }
    if (StringUtils.isNotEmpty(model.getCheckNumber())) {
      criteria = makeCriteria(criteria, "checkNumber", model.getCheckNumber());
    }
    return criteria;
  }

  @Override
  protected Update makeAllUpdate(Payments model) {
    Update update = new Update();
    update.set("customerNumber",model.getCustomerNumber());
    update.set("checkNumber", model.getCheckNumber());
    update.set("paymentDate", model.getPaymentDate());
    update.set("amount", model.getAmount());
    return update;
  }

}
