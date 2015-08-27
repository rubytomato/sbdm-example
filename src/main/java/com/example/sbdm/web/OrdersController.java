package com.example.sbdm.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.sbdm.domain.Customers;
import com.example.sbdm.domain.OrderDetails;
import com.example.sbdm.domain.Orders;
import com.example.sbdm.service.impl.CustomersService;
import com.example.sbdm.service.impl.OrderDetailsService;
import com.example.sbdm.service.impl.OrdersService;
import com.example.sbdm.utils.JsonLoader;

@Controller
@RequestMapping(value = "/orders")
public class OrdersController extends BaseController {
  private static Logger logger = LoggerFactory.getLogger(OrdersController.class);

  private static final int PAGE_SIZE = 30;

  @Autowired
  private OrdersService ordersService;

  @Autowired
  private CustomersService customersService;

  @Autowired
  private OrderDetailsService orderDetailsService;

  @RequestMapping(method = RequestMethod.GET)
  public String _index(Model model) {
    return index(1, model);
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(Model model) {
    return index(1, model);
  }

  @RequestMapping(value = "/{pageNo}", method = RequestMethod.GET)
  public String index(
      @PathVariable() Integer pageNo,
      Model model) {
    logger.debug("OrdersController:[index] Passing through...");

    Iterable<Orders> result = ordersService.findAll(pageNo, PAGE_SIZE, "orderNumber");
    model.addAttribute("result", result);

    int totalCount = (int)ordersService.count();

    addPageAttr(ordersService.calcPage(totalCount, pageNo, PAGE_SIZE), model);

    return "Orders/index";
  }


  @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
  public String detail(
      @PathVariable(value="id") String id,
      Model model) {
    logger.debug("OrdersController:[detail] Passing through...");

    Orders order = ordersService.findById(id);

    String json = "{}";
    if (order != null) {
      json = JsonLoader.toJson(order);
    }

    Customers customer = customersService.findByPk(order.getCustomerNumber());

    Iterable<OrderDetails> orderDetailList = orderDetailsService.findByOrderNumber(order.getOrderNumber());

    model.addAttribute("order", order);
    model.addAttribute("customer", customer);
    model.addAttribute("orderDetailList", orderDetailList);
    model.addAttribute("json", json);

    return "Orders/detail";
  }

}
