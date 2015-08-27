package com.example.sbdm.domain;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = CollectionNames.OrderDetails)
@CompoundIndexes({
  @CompoundIndex(
    name = "pk_order_details",
    def = "{'orderNumber' : 1, 'productCode' : 1}",
    unique = true
  )
})
public class OrderDetails {

  @Id
  private String id;

  @Field("orderNumber")
  private Long orderNumber;

  @Field("productCode")
  private String productCode;

  private Long quantityOrdered;

  private BigInteger priceEach;

  @Indexed(name = "idx1_order_details", direction = IndexDirection.ASCENDING)
  private Integer orderLineNumber;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(Long orderNumber) {
    this.orderNumber = orderNumber;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public Long getQuantityOrdered() {
    return quantityOrdered;
  }

  public void setQuantityOrdered(Long quantityOrdered) {
    this.quantityOrdered = quantityOrdered;
  }

  public BigInteger getPriceEach() {
    return priceEach;
  }

  public void setPriceEach(BigInteger priceEach) {
    this.priceEach = priceEach;
  }

  public Integer getOrderLineNumber() {
    return orderLineNumber;
  }

  public void setOrderLineNumber(Integer orderLineNumber) {
    this.orderLineNumber = orderLineNumber;
  }

}
