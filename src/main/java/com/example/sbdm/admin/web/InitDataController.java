package com.example.sbdm.admin.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sbdm.admin.InitDataService;

@Controller
@RequestMapping(value = "/admin")
public class InitDataController {
  private static Logger logger = LoggerFactory.getLogger(InitDataController.class);

  @Autowired
  private InitDataService initDataService;

  @RequestMapping(value = "/init", method = RequestMethod.GET)
  public String init(Model model) {
    logger.debug("InitDataController:[init] Passing through...");
    Map<String ,Integer> result = initDataService.init();
    model.addAttribute("result", result);
    return "Admin/init/init";
  }

  @ExceptionHandler
  @ResponseBody
  public String handleException(IllegalStateException ex) {
    return "Handled exception: " + ex.getMessage();
  }

}
