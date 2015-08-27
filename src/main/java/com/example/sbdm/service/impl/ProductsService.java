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

import com.example.sbdm.domain.Products;
import com.example.sbdm.repository.ProductsRepository;
import com.example.sbdm.service.AbstractService;

@Service
public class ProductsService extends AbstractService<Products> {
  private static Logger logger = LoggerFactory.getLogger(ProductsService.class);

  @Autowired
  private ProductsRepository productsRepository;

  @Override
  protected MongoRepository<Products, String> getRepository() {
    return productsRepository;
  }

  @Override
  public Products findByPk(Object... keys) {
    return productsRepository.findByProductCode((String)keys[0]);
  }

  @Override
  public Iterable<Products> findByNameLike(String name, String sortColumn) {
    return productsRepository.findByProductNameLike(name);
  }

  @Override
  public long searchCount(String keyword) {
    return 0;
  }

  @Override
  public Iterable<Products> search(String keyword, int page, int size, String sort) {
    return null;
  }

  @Override
  public long count(Products searchCondition) {
    Criteria criteria = makeCriteria(searchCondition);
    Query query= makeQuery(criteria);
    return doCount(query, Products.class);
  }

  @Override
  public List<Products> search(int page, int size, Sort sort, Products searchCondition) {
    Criteria criteria = makeCriteria(searchCondition);
    Query query= makeQuery(criteria);
    query.skip(calcSkipNum(page, size)).limit(size);
    if (sort != null) {
      query.with(sort);
    }
    return doFind(query, Products.class);
  }

  @Override
  protected Criteria makeCriteriaByPk(Products model) {
    return Criteria.where("productCode").is(model.getProductCode());
  }

  @Override
  protected Criteria makeCriteria(Products model) {
    Criteria criteria = null;
    if (StringUtils.isNotEmpty(model.getProductCode())) {
      criteria = makeCriteria(criteria, "productCode", model.getProductCode());
    }
    if (StringUtils.isNotEmpty(model.getProductLine())) {
      criteria = makeCriteria(criteria, "productLine", model.getProductLine());
    }
    if (StringUtils.isNotEmpty(model.getProductName())) {
      criteria = makeCriteriaRegex(criteria, "productName", model.getProductName());
    }
    if (StringUtils.isNotEmpty(model.getProductScale())) {
      criteria = makeCriteria(criteria, "productScale", model.getProductScale());
    }
    if (StringUtils.isNotEmpty(model.getProductVendor())) {
      criteria = makeCriteria(criteria, "productVendor", model.getProductVendor());
    }
    return criteria;
  }

  @Override
  protected Update makeAllUpdate(Products model) {
    Update update = new Update();
    update.set("productName", model.getProductName());
    update.set("productLine", model.getProductLine());
    update.set("productScale", model.getProductScale());
    update.set("productVendor", model.getProductVendor());
    update.set("productDescription",model.getProductDescription());
    update.set("quantityInStock", model.getQuantityInStock());
    update.set("buyPrice", model.getBuyPrice());
    update.set("MSRP", model.getMSRP());
    return update;
  }

}
