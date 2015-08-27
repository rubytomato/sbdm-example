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

import com.example.sbdm.domain.OrderDetails;
import com.example.sbdm.domain.Products;
import com.example.sbdm.service.impl.OrderDetailsService;
import com.example.sbdm.service.impl.ProductsService;
import com.example.sbdm.utils.JsonLoader;

@Controller
@RequestMapping(value = "/products")
public class ProductsController extends BaseController {
  private static Logger logger = LoggerFactory.getLogger(ProductsController.class);

  private static final int PAGE_SIZE = 10;

  @Autowired
  private ProductsService productsService;

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
    logger.debug("ProductsController:[index] Passing through...");

    Iterable<Products> result = productsService.findAll(pageNo, PAGE_SIZE, "productCode");
    model.addAttribute("result", result);

    int totalCount = (int)productsService.count();

    addPageAttr(productsService.calcPage(totalCount, pageNo, PAGE_SIZE), model);

    return "Products/index";
  }

  @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
  public String detail(
      @PathVariable(value="id") String id,
      Model model) {
    logger.debug("ProductsController:[detail] Passing through...");

    Products product = productsService.findById(id);

    String json = "{}";
    if (product != null) {
      json = JsonLoader.toJson(product);
    }

    Iterable<OrderDetails> orderDetailList = orderDetailsService.findByProductCode(product.getProductCode());

    model.addAttribute("product", product);
    model.addAttribute("orderDetailList", orderDetailList);
    model.addAttribute("json", json);

    return "Products/detail";
  }

  @RequestMapping(value = "/search", method = RequestMethod.GET)
  public String search(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String line,
      @RequestParam(required = false) String vendor,
      Model model) {
    logger.debug("ProductsController:[search] Passing through...");

    int pageNo = 1;

    Products searchCondition = new Products();
    if (StringUtils.isNotEmpty(name)) {
      searchCondition.setProductName(name);
    }
    if (StringUtils.isNotEmpty(line)) {
      searchCondition.setProductLine(line);
    }
    if (StringUtils.isNotEmpty(vendor)) {
      searchCondition.setProductVendor(vendor);
    }

    List<Products> result = productsService.search(pageNo, PAGE_SIZE, null, searchCondition);
    if (result != null) {
      logger.info("result found:{}", result.size());
    } else {
      logger.info("result not found");
    }

    int totalCount = (int)productsService.count(searchCondition);
    logger.info("totalCount:{}", totalCount);

    model.addAttribute("result", result);

    addPageAttr(productsService.calcPage(totalCount, pageNo, PAGE_SIZE), model);

    return "Products/index";
  }

}
