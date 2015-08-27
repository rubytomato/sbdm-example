package com.example.sbdm.domain;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = CollectionNames.Customers)
@CompoundIndexes({
  @CompoundIndex(
    name = "idx1_customers",
    def = "{'contactLastName' : 1, 'contactFirstName' : 1}"
  )
})
public class Customers {

  public Customers() {
  }

  @PersistenceConstructor
  public Customers(Long customerNumber, String customerName) {
    this.customerNumber = customerNumber;
    this.customerName = customerName;
  }

  @Id
  private String id;

  @Indexed(name = "pk_customers", direction = IndexDirection.DESCENDING, unique = true)
  @Field("customerNumber")
  private Long customerNumber;

  private String customerName;

  @Field("contactLastName")
  private String contactLastName;

  @Field("contactFirstName")
  private String contactFirstName;

  private String phone;

  private String addressLine1;

  private String addressLine2;

  private String city;

  private String state;

  private String postalCode;

  private String country;

  private Long salesRepEmployeeNumber;

  private BigDecimal creditLimit;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getCustomerNumber() {
    return customerNumber;
  }

  public void setCustomerNumber(Long customerNumber) {
    this.customerNumber = customerNumber;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getContactLastName() {
    return contactLastName;
  }

  public void setContactLastName(String contactLastName) {
    this.contactLastName = contactLastName;
  }

  public String getContactFirstName() {
    return contactFirstName;
  }

  public void setContactFirstName(String contactFirstName) {
    this.contactFirstName = contactFirstName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddressLine1() {
    return addressLine1;
  }

  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Long getSalesRepEmployeeNumber() {
    return salesRepEmployeeNumber;
  }

  public void setSalesRepEmployeeNumber(Long salesRepEmployeeNumber) {
    this.salesRepEmployeeNumber = salesRepEmployeeNumber;
  }

  public BigDecimal getCreditLimit() {
    return creditLimit;
  }

  public void setCreditLimit(BigDecimal creditLimit) {
    this.creditLimit = creditLimit;
  }

}
