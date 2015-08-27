package com.example.sbdm.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class IndexController extends BaseController {
  private static Logger logger = LoggerFactory.getLogger(IndexController.class);

  @RequestMapping(method = RequestMethod.GET)
  public String index() {
    logger.debug("IndexController:[index] Passing through...");
    return "redirect:/customers";
  }

}
