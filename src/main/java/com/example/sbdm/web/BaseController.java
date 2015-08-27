package com.example.sbdm.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.example.sbdm.service.PageBean;

@Controller
public class BaseController {
  private static Logger logger = LoggerFactory.getLogger(BaseController.class);

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    sdf.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    //binder.registerCustomEditor(Date.class, new DateTypeEditor());
  }

  void addPageAttr(final PageBean page, Model model) {
    model.addAttribute("totalCount", page.getTotalCount());
    model.addAttribute("currentPage", page.getCurrentPage());
    model.addAttribute("maxPage", page.getMaxPage());
  }

  protected void debug(HttpServletRequest request) {
    logger.info("queryString : " + request.getQueryString());
    logger.info("contextPath : " + request.getContextPath());
    logger.info("pathInfo : "    + request.getPathInfo());
    logger.info("servletPath : " + request.getServletPath());
    logger.info("method : "      + request.getMethod());
  }

}
