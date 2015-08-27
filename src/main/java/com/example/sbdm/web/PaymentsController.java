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
import com.example.sbdm.domain.Payments;
import com.example.sbdm.service.impl.CustomersService;
import com.example.sbdm.service.impl.PaymentsService;
import com.example.sbdm.utils.JsonLoader;

@Controller
@RequestMapping(value = "/payments")
public class PaymentsController extends BaseController {
  private static Logger logger = LoggerFactory.getLogger(PaymentsController.class);

  private static final int PAGE_SIZE = 30;

  @Autowired
  private PaymentsService paymentsService;

  @Autowired
  private CustomersService customersService;

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
    logger.debug("PaymentsController:[index] Passing through...");

    Iterable<Payments> result = paymentsService.findAll(pageNo, PAGE_SIZE, "customerNumber");
    model.addAttribute("result", result);

    int totalCount = (int)paymentsService.count();

    addPageAttr(paymentsService.calcPage(totalCount, pageNo, PAGE_SIZE), model);

    return "Payments/index";
  }

  @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
  public String detail(
      @PathVariable(value="id") String id,
      Model model) {
    logger.debug("PaymentsController:[detail] Passing through...");

    Payments payment = paymentsService.findById(id);

    String json = "{}";
    if (payment != null) {
      json = JsonLoader.toJson(payment);
    }

    Customers customer = customersService.findByPk(payment.getCustomerNumber());

    model.addAttribute("payment", payment);
    model.addAttribute("customer", customer);
    model.addAttribute("json", json);

    return "Payments/detail";
  }

}
