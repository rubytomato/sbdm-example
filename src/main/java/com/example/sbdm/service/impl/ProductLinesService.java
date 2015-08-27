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

import com.example.sbdm.domain.ProductLines;
import com.example.sbdm.repository.ProductLinesRepository;
import com.example.sbdm.service.AbstractService;

@Service
public class ProductLinesService extends AbstractService<ProductLines> {
  private static Logger logger = LoggerFactory.getLogger(ProductLinesService.class);

  @Autowired
  private ProductLinesRepository productLinesRepository;

  @Override
  protected MongoRepository<ProductLines, String> getRepository() {
    return productLinesRepository;
  }

  @Override
  public ProductLines findByPk(Object... keys) {
    return productLinesRepository.findByProductLine((String)keys[0]);
  }

  @Override
  public Iterable<ProductLines> findByNameLike(String name, String sortColumn) {
    return productLinesRepository.findByTextDescriptionLike(name);
  }

  @Override
  public long searchCount(String keyword) {
    return 0;
  }

  @Override
  public Iterable<ProductLines> search(String keyword, int page, int size, String sort) {
    return null;
  }

  @Override
  public long count(ProductLines searchCondition) {
    Criteria criteria = makeCriteria(searchCondition);
    Query query= makeQuery(criteria);
    return doCount(query, ProductLines.class);
  }

  @Override
  public List<ProductLines> search(int page, int size, Sort sort, ProductLines searchCondition) {
    Criteria criteria = makeCriteria(searchCondition);
    Query query= makeQuery(criteria);
    query.skip(calcSkipNum(page, size)).limit(size);
    return doFind(query, ProductLines.class);
  }

  @Override
  protected Criteria makeCriteriaByPk(ProductLines model) {
    return Criteria.where("productLine").is(model.getProductLine());
  }

  @Override
  protected Criteria makeCriteria(ProductLines model) {
    Criteria criteria = null;
    if (StringUtils.isNotEmpty(model.getProductLine())) {
      criteria = makeCriteria(criteria, "productLine", model.getProductLine());
    }
    return criteria;
  }

  @Override
  protected Update makeAllUpdate(ProductLines model) {
    Update update = new Update();
    update.set("productLine", model.getProductLine());
    update.set("textDescription", model.getTextDescription());
    update.set("htmlDescription", model.getHtmlDescription());
    update.set("image", model.getImage());
    return update;
  }

}
