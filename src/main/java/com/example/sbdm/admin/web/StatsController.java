package com.example.sbdm.admin.web;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.sbdm.utils.MongoService;
import com.example.sbdm.utils.MongoStats;

@Controller
@RequestMapping(value = "/admin")
public class StatsController {
  private static Logger logger = LoggerFactory.getLogger(StatsController.class);

  @Autowired
  private MongoService mongoService;

  @RequestMapping(value = "/stats", method = RequestMethod.GET)
  public String stats(Model model) {
    logger.debug("StatsController:[stats] Passing through...");
    Set<String> collections = mongoService.getCollections();
    model.addAttribute("collections", collections);
    return "Admin/stats/stats";
  }

  @RequestMapping(value = "/stats/{collection}", method = RequestMethod.GET)
  public String detail(@PathVariable("collection") String collection, Model model) {
    logger.debug("StatsController:[detail] Passing through...");
    MongoStats stats = mongoService.stats(collection);
    model.addAttribute("stats", stats);
    return "Admin/stats/collection";
  }

}
