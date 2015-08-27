package com.example.sbdm.utils;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MongoStats implements Serializable {

  private static final long serialVersionUID = 1456198201068440166L;

  private String ns;

  private Integer count;

  private Integer size;

  private Double avgObjSize;

  private Integer numExtents;

  private Integer storageSize;

  private Double lastExtentSize;

  private Double paddingFactor;

  private Integer userFlags;

  private Boolean capped;

  private Integer nindexes;

  private Integer totalIndexSize;

  private Map<String, Integer> indexSizes;

  private Double ok;

  public String getNs() {
    return ns;
  }

  public void setNs(String ns) {
    this.ns = ns;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public Double getAvgObjSize() {
    return avgObjSize;
  }

  public void setAvgObjSize(Double avgObjSize) {
    this.avgObjSize = avgObjSize;
  }

  public Integer getStorageSize() {
    return storageSize;
  }

  public void setStorageSize(Integer storageSize) {
    this.storageSize = storageSize;
  }

  public Integer getNumExtents() {
    return numExtents;
  }

  public void setNumExtents(Integer numExtents) {
    this.numExtents = numExtents;
  }

  public Integer getNindexes() {
    return nindexes;
  }

  public void setNindexes(Integer nindexes) {
    this.nindexes = nindexes;
  }

  public Double getLastExtentSize() {
    return lastExtentSize;
  }

  public void setLastExtentSize(Double lastExtentSize) {
    this.lastExtentSize = lastExtentSize;
  }

  public Double getPaddingFactor() {
    return paddingFactor;
  }

  public void setPaddingFactor(Double paddingFactor) {
    this.paddingFactor = paddingFactor;
  }

  public Integer getUserFlags() {
    return userFlags;
  }

  public Boolean getCapped() {
    return capped;
  }

  public void setCapped(Boolean capped) {
    this.capped = capped;
  }

  public void setUserFlags(Integer userFlags) {
    this.userFlags = userFlags;
  }

  public Integer getTotalIndexSize() {
    return totalIndexSize;
  }

  public void setTotalIndexSize(Integer totalIndexSize) {
    this.totalIndexSize = totalIndexSize;
  }

  public Map<String, Integer> getIndexSizes() {
    return indexSizes;
  }

  public void setIndexSizes(Map<String, Integer> indexSizes) {
    this.indexSizes = indexSizes;
  }

  public Double getOk() {
    return ok;
  }

  public void setOk(Double ok) {
    this.ok = ok;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
