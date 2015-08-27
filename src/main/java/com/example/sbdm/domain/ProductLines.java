package com.example.sbdm.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = CollectionNames.ProductLines)
public class ProductLines {

  @Id
  private String id;

  @Indexed(name = "pk_product_lines", direction = IndexDirection.DESCENDING, unique = true)
  @Field("productLine")
  private String productLine;

  private String textDescription;

  private String htmlDescription;

  private String image;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }

  public String getTextDescription() {
    return textDescription;
  }

  public void setTextDescription(String textDescription) {
    this.textDescription = textDescription;
  }

  public String getHtmlDescription() {
    return htmlDescription;
  }

  public void setHtmlDescription(String htmlDescription) {
    this.htmlDescription = htmlDescription;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

}
