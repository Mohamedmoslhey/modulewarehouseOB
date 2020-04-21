package com.ibox.paper.salesbriefBp.entity;

import java.math.BigDecimal;

import org.openbravo.data.FieldProvider;

public class SalesBriefBp_Entity implements FieldProvider {

  private String lproducttype;

  private String fromdate;
  private String todate;
  private String producttype;
  private BigDecimal nodeg1 = BigDecimal.ZERO;
  private BigDecimal nodeg2 = BigDecimal.ZERO;
  private BigDecimal nodeg3 = BigDecimal.ZERO;
  private BigDecimal nodeg4 = BigDecimal.ZERO;
  private BigDecimal nodeg5 = BigDecimal.ZERO;
  private BigDecimal nodeg6 = BigDecimal.ZERO;
  private BigDecimal nodeg7 = BigDecimal.ZERO;
  private BigDecimal nodeg8 = BigDecimal.ZERO;
  private BigDecimal nodeg9 = BigDecimal.ZERO;
  private BigDecimal nodeg10 = BigDecimal.ZERO;
  private BigDecimal nodeg11 = BigDecimal.ZERO;
  private BigDecimal nodeg12 = BigDecimal.ZERO;

  private String machine; //
  private String BusinessPartner; //

  // this is for make sure that wahst you set in java model will
  // be assign for its position in jrxml

  @Override
  public String getField(String fieldName) {
    // TODO Auto-generated method stub

    if (fieldName.equalsIgnoreCase("todate"))
      return todate;

    else if (fieldName.equalsIgnoreCase("producttype")) {
      return producttype;
    } else if (fieldName.equalsIgnoreCase("machine")) {
      return machine;
    } else if (fieldName.equalsIgnoreCase("BusinessPartner")) {
      return BusinessPartner;
    } else if (fieldName.equalsIgnoreCase("fromdate")) {
      return fromdate;
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
    } else if (fieldName.equalsIgnoreCase("nodeg5")) {
      return nodeg5.toString();
    } else if (fieldName.equalsIgnoreCase("nodeg6")) {
      return nodeg6.toString();
    } else if (fieldName.equalsIgnoreCase("nodeg7")) {
      return nodeg7.toString();
    } else if (fieldName.equalsIgnoreCase("nodeg8")) {
      return nodeg8.toString();
    } else if (fieldName.equalsIgnoreCase("nodeg9")) {
      return nodeg9.toString();
    } else if (fieldName.equalsIgnoreCase("nodeg10")) {
      return nodeg10.toString();
    } else if (fieldName.equalsIgnoreCase("nodeg11")) {
      return nodeg11.toString();
    } else if (fieldName.equalsIgnoreCase("nodeg12")) {
      return nodeg12.toString();
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

  public BigDecimal getNodeg5() {
    return nodeg5;
  }

  public void setNodeg5(BigDecimal nodeg5) {
    this.nodeg5 = nodeg5;
  }

  public BigDecimal getNodeg6() {
    return nodeg6;
  }

  public void setNodeg6(BigDecimal nodeg6) {
    this.nodeg6 = nodeg6;
  }

  public BigDecimal getNodeg7() {
    return nodeg7;
  }

  public void setNodeg7(BigDecimal nodeg7) {
    this.nodeg7 = nodeg7;
  }

  public BigDecimal getNodeg8() {
    return nodeg8;
  }

  public void setNodeg8(BigDecimal nodeg8) {
    this.nodeg8 = nodeg8;
  }

  public BigDecimal getNodeg9() {
    return nodeg9;
  }

  public void setNodeg9(BigDecimal nodeg9) {
    this.nodeg9 = nodeg9;
  }

  public BigDecimal getNodeg10() {
    return nodeg10;
  }

  public void setNodeg10(BigDecimal nodeg10) {
    this.nodeg10 = nodeg10;
  }

  public BigDecimal getNodeg11() {
    return nodeg11;
  }

  public void setNodeg11(BigDecimal nodeg11) {
    this.nodeg11 = nodeg11;
  }

  public BigDecimal getNodeg12() {
    return nodeg12;
  }

  public void setNodeg12(BigDecimal nodeg12) {
    this.nodeg12 = nodeg12;
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

  public String getBusinessPartner() {
    return BusinessPartner;
  }

  public void setBusinessPartner(String businessPartner) {
    BusinessPartner = businessPartner;
  }

  public String getFromdate() {
    return fromdate;
  }

  public void setFromdate(String fromdate) {
    this.fromdate = fromdate;
  }

  public String getTodate() {
    return todate;
  }

  public void setTodate(String todate) {
    this.todate = todate;
  }

  public String getLproducttype() {
    return lproducttype;
  }

  public void setLproducttype(String lproducttype) {
    this.lproducttype = lproducttype;
  }

}
