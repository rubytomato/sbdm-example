package com.example.sbdm.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.sbdm.domain.Customers;
import com.example.sbdm.domain.OrderDetails;
import com.example.sbdm.domain.Orders;
import com.example.sbdm.domain.Payments;
import com.example.sbdm.domain.ProductLines;
import com.example.sbdm.domain.Products;
import com.example.sbdm.repository.CustomersRepository;
import com.example.sbdm.repository.OrderDetailsRepository;
import com.example.sbdm.repository.OrdersRepository;
import com.example.sbdm.repository.PaymentsRepository;
import com.example.sbdm.repository.ProductLinesRepository;
import com.example.sbdm.repository.ProductsRepository;
import com.example.sbdm.utils.JsonLoader;
import com.example.sbdm.utils.MongoService;

@Service
public class InitDataService {
  private static Logger logger = LoggerFactory.getLogger(InitDataService.class);

  private static final String RESOURCE_DIR = "classpath:data/init";

  private static final String CUSTOMERS = RESOURCE_DIR + "/Customers/Customers.json";
  private static final String ORDERDETAILS = RESOURCE_DIR + "/OrderDetails/OrderDetails.json";
  private static final String ORDERS = RESOURCE_DIR + "/Orders/Orders.json";
  private static final String PAYMENTS = RESOURCE_DIR + "/Payments/Payments.json";
  private static final String PRODUCTLINES = RESOURCE_DIR + "/ProductLines/ProductLines.json";
  private static final String PRODUCTS = RESOURCE_DIR + "/Products/Products.json";

  @Autowired
  private MongoService mongoService;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private CustomersRepository customersRepository;

  @Autowired
  private OrderDetailsRepository orderDetailsRepository;

  @Autowired
  private OrdersRepository ordersRepository;

  @Autowired
  private PaymentsRepository paymentsRepository;

  @Autowired
  private ProductLinesRepository productLinesRepository;

  @Autowired
  private ProductsRepository productsRepository;

  public Map<String ,Integer> init() {
    logger.info("init start");

    mongoService.collectionAllDrop();

    List<Customers> customersList =
      JsonLoader.multi(CUSTOMERS, Customers.class);
    if (CollectionUtils.isNotEmpty(customersList)) {
      logger.info("customersList: " + customersList.size());
    } else {
      logger.info("customersList null");
    }

    List<OrderDetails> orderDetailsList =
      JsonLoader.multi(ORDERDETAILS, OrderDetails.class);
    if (CollectionUtils.isNotEmpty(orderDetailsList)) {
      logger.info("orderDetailsList: " + orderDetailsList.size());
    } else {
      logger.info("orderDetailsList null");
    }

    List<Orders> ordersList =
      JsonLoader.multi(ORDERS, Orders.class);
    if (CollectionUtils.isNotEmpty(ordersList)) {
      logger.info("ordersList: " + ordersList.size());
    } else {
      logger.info("ordersList null");
    }

    List<Payments> paymentsList =
      JsonLoader.multi(PAYMENTS, Payments.class);
    if (CollectionUtils.isNotEmpty(paymentsList)) {
      logger.info("paymentsList: " + paymentsList.size());
    } else {
      logger.info("paymentsList null");
    }

    List<ProductLines> productLinesList =
      JsonLoader.multi(PRODUCTLINES, ProductLines.class);
    if (CollectionUtils.isNotEmpty(productLinesList)) {
      logger.info("productLinesList: " + productLinesList.size());
    } else {
      logger.info("productLinesList null");
    }

    List<Products> productsList =
      JsonLoader.multi(PRODUCTS, Products.class);
    if (CollectionUtils.isNotEmpty(productsList)) {
      logger.info("productsList: " + productsList.size());
    } else {
      logger.info("productsList null");
    }

    int cu = customersRepository.save(customersList).size();
    int od = orderDetailsRepository.save(orderDetailsList).size();
    int or = ordersRepository.save(ordersList).size();
    int pa = paymentsRepository.save(paymentsList).size();
    int pl = productLinesRepository.save(productLinesList).size();
    int pr = productsRepository.save(productsList).size();

    Map<String ,Integer> result = new HashMap<>();
    result.put("customers", cu);
    result.put("orderDetails", od);
    result.put("orders", or);
    result.put("payments", pa);
    result.put("productLines", pl);
    result.put("products", pr);

    logger.info("init end");

    return result;
  }

}
