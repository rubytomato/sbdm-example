package com.example.sbdm.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sbdm.domain.Customers;
import com.example.sbdm.domain.Orders;
import com.example.sbdm.service.impl.CustomersService;
import com.example.sbdm.service.impl.OrdersService;
import com.example.sbdm.utils.JsonLoader;

@Controller
@RequestMapping(value = "/customers")
public class CustomersController extends BaseController {
  private static Logger logger = LoggerFactory.getLogger(CustomersController.class);

  private static final int PAGE_SIZE = 10;

  @Autowired
  private CustomersService customersService;

  @Autowired
  private OrdersService ordersService;

  @RequestMapping(method = RequestMethod.GET)
  public String _index(Model model) {
    return index(1, null, model);
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(Model model) {
    return index(1, null, model);
  }

  @RequestMapping(value = "/{pageNo}", method = RequestMethod.GET)
  public String index(
      @PathVariable Integer pageNo,
      @RequestParam String keyword,
      Model model) {
    logger.debug("CustomersController:[index] Passing through...");

    int totalCount = 0;
    Iterable<Customers> result;

    if (StringUtils.isNotEmpty(keyword)) {
      result = customersService.search(keyword, pageNo, PAGE_SIZE, "customerName");
      totalCount = (int)customersService.searchCount(keyword);
    } else {
      result = customersService.findAll(pageNo, PAGE_SIZE, "customerNumber");
      totalCount = (int)customersService.count();
    }

    model.addAttribute("keyword", keyword);
    model.addAttribute("result", result);

    addPageAttr(customersService.calcPage(totalCount, pageNo, PAGE_SIZE), model);

    return "Customers/index";
  }

  @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
  public String detail(
      @PathVariable String id,
      Model model) {
    logger.debug("CustomersController:[detail] Passing through...");

    Customers customer =  customersService.findById(id);

    String json = "{}";
    if (customer != null) {
      json = JsonLoader.toJson(customer);
    }

    Iterable<Orders> orderList = ordersService.findByCustomerNumber(customer.getCustomerNumber());

    model.addAttribute("customer", customer);
    model.addAttribute("orderList", orderList);
    model.addAttribute("json", json);

    return "Customers/detail";
  }

  @RequestMapping(value = "/search", method = RequestMethod.GET)
  public String search(
      @RequestParam(required = false) String country,
      @RequestParam(required = false) String city,
      @RequestParam(required = false) String state,
      @RequestParam(required = false) String postalcode,
      Model model) {
    logger.debug("CustomersController:[search] Passing through...");

    int pageNo = 1;

    Customers searchCondition = new Customers();
    if (StringUtils.isNotEmpty(country)) {
      searchCondition.setCountry(country);
    }
    if (StringUtils.isNotEmpty(city)) {
      searchCondition.setCity(city);
    }
    if (StringUtils.isNotEmpty(state)) {
      searchCondition.setState(state);
    }
    if (StringUtils.isNotEmpty(postalcode)) {
      searchCondition.setPostalCode(postalcode);
    }

    List<Customers> result = customersService.search(pageNo, PAGE_SIZE, null, searchCondition);

    int totalCount = (int)customersService.count(searchCondition);

    model.addAttribute("result", result);

    addPageAttr(customersService.calcPage(totalCount, pageNo, PAGE_SIZE), model);

    return "Customers/index";
  }

}
