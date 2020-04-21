package com.ibox.paper.invevtorybrief.entity;

import java.math.BigDecimal;

import org.openbravo.data.FieldProvider;

public class InventoryBrief_Entity implements FieldProvider {

  private String lproducttype;
  private String warehouse;
  private String producttype;
  private BigDecimal nodeg1 = BigDecimal.ZERO;
  private BigDecimal nodeg2 = BigDecimal.ZERO;
  private BigDecimal nodeg3 = BigDecimal.ZERO;
  private BigDecimal nodeg4 = BigDecimal.ZERO;
  private BigDecimal wodeg1 = BigDecimal.ZERO;
  private BigDecimal wodeg2 = BigDecimal.ZERO;
  private BigDecimal wodeg3 = BigDecimal.ZERO;
  private BigDecimal wodeg4 = BigDecimal.ZERO;
  private String machine; //

  private String qualitydegree;

  // this is for make sure that wahst you set in java model will
  // be assign for its position in jrxml

  @Override
  public String getField(String fieldName) {
    // TODO Auto-generated method stub

    if (fieldName.equalsIgnoreCase("warehouse"))
      return warehouse;

    else if (fieldName.equalsIgnoreCase("producttype")) {
      return producttype;
    } else if (fieldName.equalsIgnoreCase("machine")) {
      return machine;
    } else if (fieldName.equalsIgnoreCase("qualitydegree")) {
      return qualitydegree;
    } else if (fieldName.equalsIgnoreCase("lproducttype")) {
      return lproducttype;
    } else if (fieldName.equalsIgnoreCase("nodeg1")) {
      return nodeg1.toString();
    } else if (fieldName.equalsIgnoreCase("nodeg2")) {
      return nodeg2.toString();
    } else if (fieldName.equalsIgnoreCase("nodeg3")) {
      return nodeg3.toString();
    } else if (fieldName.equalsIgnoreCase("nodeg4")) {
      return nodeg4.toString();
    } else if (fieldName.equalsIgnoreCase("wodeg1")) {
      return wodeg1.toString();
    } else if (fieldName.equalsIgnoreCase("wodeg2")) {
      return wodeg2.toString();
    } else if (fieldName.equalsIgnoreCase("wodeg3")) {
      return wodeg3.toString();
    } else if (fieldName.equalsIgnoreCase("wodeg4")) {
      return wodeg4.toString();
    }

    else

      return null;
  }

  public String getMachine() {
    return machine;
  }

  public void setMachine(String machine) {
    this.machine = machine;
  }

  public String getProducttype() {
    return producttype;
  }

  public BigDecimal getWodeg1() {
    return wodeg1;
  }

  public void setWodeg1(BigDecimal wodeg1) {
    this.wodeg1 = wodeg1;
  }

  public BigDecimal getWodeg2() {
    return wodeg2;
  }

  public void setWodeg2(BigDecimal wodeg2) {
    this.wodeg2 = wodeg2;
  }

  public BigDecimal getWodeg3() {
    return wodeg3;
  }

  public void setWodeg3(BigDecimal wodeg3) {
    this.wodeg3 = wodeg3;
  }

  public BigDecimal getWodeg4() {
    return wodeg4;
  }

  public void setWodeg4(BigDecimal wodeg4) {
    this.wodeg4 = wodeg4;
  }

  public BigDecimal getNodeg1() {
    return nodeg1;
  }

  public void setNodeg1(BigDecimal nodeg1) {
    this.nodeg1 = nodeg1;
  }

  public void setProducttype(String producttype) {
    this.producttype = producttype;
  }

  public String getQualitydegree() {
    return qualitydegree;
  }

  public void setQualitydegree(String qualitydegree) {
    this.qualitydegree = qualitydegree;
  }

  public BigDecimal getNodeg2() {
    return nodeg2;
  }

  public void setNodeg2(BigDecimal nodeg2) {
    this.nodeg2 = nodeg2;
  }

  public BigDecimal getNodeg3() {
    return nodeg3;
  }

  public void setNodeg3(BigDecimal nodeg3) {
    this.nodeg3 = nodeg3;
  }

  public BigDecimal getNodeg4() {
    return nodeg4;
  }

  public void setNodeg4(BigDecimal nodeg4) {
    this.nodeg4 = nodeg4;
  }

  public String getWarehouse() {
    return warehouse;
  }

  public void setWarehouse(String warehouse) {
    this.warehouse = warehouse;
  }

  public String getLproducttype() {
    return lproducttype;
  }

  public void setLproducttype(String lproducttype) {
    this.lproducttype = lproducttype;
  }

}
