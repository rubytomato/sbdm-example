package com.example.sbdm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

@Configuration
@EnableMongoRepositories(
  basePackages = "com.example.sbdm.repository",
  queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND
)
public class MongodbConfig extends AbstractMongoConfiguration {

  @Override
  @Bean
  public Mongo mongo() throws Exception {
    Mongo mongo = new MongoClient("localhost", 27017);
    mongo.setWriteConcern(WriteConcern.SAFE);
    return mongo;
  }

  @Override
  @Bean
  public MongoDbFactory mongoDbFactory() throws Exception {
    return new SimpleMongoDbFactory(mongo(), getDatabaseName(), getUserCredentials());
    //return new SimpleMongoDbFactory(mongo(), getDatabaseName());
  }

  @Override
  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
    MongoTemplate template = new MongoTemplate(mongoDbFactory());
    template.setWriteResultChecking(WriteResultChecking.EXCEPTION);
    return template;
  }

  @Value("${spring.data.mongodb.database}")
  private String databasename;

  @Override
  protected String getDatabaseName() {
    return databasename;
  }

  @Override
  protected String getMappingBasePackage() {
    return "com.example.sbdm.domain";
  }

  @Value("${spring.data.mongodb.username}")
  private String username;
  @Value("${spring.data.mongodb.password}")
  private String password;

  @Override
  protected UserCredentials getUserCredentials() {
    UserCredentials userCredentials = new UserCredentials(username, password);
    return userCredentials;
  }

}
