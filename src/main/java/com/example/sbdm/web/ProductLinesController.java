package com.example.sbdm.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.sbdm.domain.ProductLines;
import com.example.sbdm.service.impl.ProductLinesService;
import com.example.sbdm.service.impl.ProductsService;
import com.example.sbdm.utils.JsonLoader;

@Controller
@RequestMapping(value = "/productlines")
public class ProductLinesController extends BaseController {
  private static Logger logger = LoggerFactory.getLogger(ProductLinesController.class);

  private static final int PAGE_SIZE = 10;

  @Autowired
  private ProductLinesService productLinesService;

  @Autowired
  private ProductsService productsService;

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
    logger.debug("ProductLinesController:[index] Passing through...");

    Iterable<ProductLines> result = productLinesService.findAll(pageNo, PAGE_SIZE, "productLine");
    model.addAttribute("result", result);

    int totalCount = (int)productLinesService.count();

    addPageAttr(productLinesService.calcPage(totalCount, pageNo, PAGE_SIZE), model);

    return "ProductLines/index";
  }

  @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
  public String detail(
      @PathVariable(value="id") String id,
      Model model) {
    logger.debug("ProductLinesController:[detail] Passing through...");

    ProductLines productLine = productLinesService.findById(id);

    String json = "{}";
    if (productLine != null) {
      json = JsonLoader.toJson(productLine);
    }

    model.addAttribute("productLine", productLine);
    model.addAttribute("json", json);

    return "ProductLines/detail";
  }

}
