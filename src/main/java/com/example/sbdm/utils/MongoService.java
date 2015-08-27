package com.example.sbdm.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.sbdm.domain.CollectionNames;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;

@Service
public class MongoService {
  private static Logger logger = LoggerFactory.getLogger(MongoService.class);

  @Autowired
  private MongoTemplate template;

  static final String B = "{ \"count\": \"{0}\", \"query\": \"{1}\" }";

  public long countDocuments(final String collection, final Query query) {

    String command = B.replaceAll("{0}", collection).replaceAll("{1}", query.getQueryObject().toString());

    logger.debug("command : " + command);

    CommandResult cr = template.executeCommand(command);

    logger.debug("command result : " + cr.toString());

    return cr.getLong("n");

  }

  static final String C = "{ \"count\": \"{0}\" }";

  public long countDocuments(final String collection) {

    String command = C.replaceFirst("{0}", collection);

    logger.debug("command : " + command);

    CommandResult cr = template.executeCommand(command);

    logger.debug("command result : " + cr.toString());

    return cr.getLong("n");

  }

  public Set<String> getCollections() {
    return template.getCollectionNames();
  }

  public void collectionAllDrop() {
    template.dropCollection(CollectionNames.Customers);
    template.dropCollection(CollectionNames.OrderDetails);
    template.dropCollection(CollectionNames.Orders);
    template.dropCollection(CollectionNames.Payments);
    template.dropCollection(CollectionNames.ProductLines);
    template.dropCollection(CollectionNames.Products);
  }

  public MongoStats stats(final String collection) {

    DBCollection dbc = template.getCollection(collection);

    CommandResult cr = dbc.getStats();

    MongoStats stats = new MongoStats();

    stats.setNs(cr.get("ns").toString());
    stats.setCount((Integer) cr.get("count"));
    stats.setSize((Integer) cr.get("size"));
    stats.setAvgObjSize(Double.valueOf(cr.get("avgObjSize").toString()));
    stats.setNumExtents((Integer) cr.get("numExtents"));
    stats.setStorageSize((Integer) cr.get("storageSize"));
    stats.setLastExtentSize(Double.valueOf(cr.get("lastExtentSize").toString()));
    stats.setPaddingFactor(Double.valueOf(cr.get("paddingFactor").toString()));
    stats.setUserFlags((Integer) cr.get("userFlags"));
    stats.setCapped((Boolean) cr.get("capped"));
    stats.setNindexes((Integer) cr.get("nindexes"));
    stats.setTotalIndexSize((Integer) cr.get("totalIndexSize"));

    @SuppressWarnings("unchecked")
    Map<String, Object> idx = (Map<String,Object>)cr.get("indexSizes");
    if (idx != null && !idx.isEmpty()) {
      Map<String, Integer> iMap = new HashMap<>();
      idx.forEach((key,value)->{
        iMap.put(key, (Integer)value);
      });
      stats.setIndexSizes(iMap);
    }

    stats.setOk(Double.valueOf(cr.get("ok").toString()));

    return stats;

  }

}
