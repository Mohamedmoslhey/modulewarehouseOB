package com.ibox.paper.salesreportbyrollspe.entity;

import java.math.BigDecimal;

import org.openbravo.data.FieldProvider;

public class SalesReportRollspec_Entity implements FieldProvider {

  private String cpartner;

  private String jumboCode;
  private BigDecimal bakarweigth;

  private BigDecimal gsm;
  private String qotr;
  private BigDecimal width;
  private String qualitydegree;
  private String producttype;
  private String iznno;

  @Override
  public String getField(String fieldName) {
    // TODO Auto-generated method stub

    if (fieldName.equalsIgnoreCase("cpartner"))
      return cpartner;

    else if (fieldName.equalsIgnoreCase("jumboCode")) {
      return jumboCode;

    }

    else if (fieldName.equalsIgnoreCase("bakarweigth")) {
      return bakarweigth.toString();

    } else if (fieldName.equalsIgnoreCase("gsm")) {
      return gsm.toString();
    }

    else if (fieldName.equalsIgnoreCase("qotr")) {
      return qotr;

    }

    else if (fieldName.equalsIgnoreCase("width")) {
      return width.toString();

    } else if (fieldName.equalsIgnoreCase("qualitydegree")) {
      return qualitydegree;
    }

    else if (fieldName.equalsIgnoreCase("producttype")) {
      return producttype;

    }

    else if (fieldName.equalsIgnoreCase("iznno")) {
      return iznno;

    } else

      return null;
  }

  //
  public String getCpartner() {
    return cpartner;
  }

  public void setCpartner(String cpartner) {
    this.cpartner = cpartner;
  }

  public String getJumboCode() {
    return jumboCode;
  }

  public void setJumboCode(String jumboCode) {
    this.jumboCode = jumboCode;
  }

  public BigDecimal getBakarweigth() {
    return bakarweigth;
  }

  public void setBakarweigth(BigDecimal bakarweigth) {
    this.bakarweigth = bakarweigth;
  }

  public BigDecimal getGsm() {
    return gsm;
  }

  public void setGsm(BigDecimal gsm) {
    this.gsm = gsm;
  }

  public String getQotr() {
    return qotr;
  }

  public void setQotr(String qotr) {
    this.qotr = qotr;
  }

  public BigDecimal getWidth() {
    return width;
  }

  public void setWidth(BigDecimal width) {
    this.width = width;
  }

  public String getQualitydegree() {
    return qualitydegree;
  }

  public void setQualitydegree(String qualitydegree) {
    this.qualitydegree = qualitydegree;
  }

  public String getProducttype() {
    return producttype;
  }

  public void setProducttype(String producttype) {
    this.producttype = producttype;
  }

  public String getIznno() {
    return iznno;
  }

  public void setIznno(String iznno) {
    this.iznno = iznno;
  }

  // @Override
  // public String toString() {
  // return "SalesReport_Entity [cpartner=" + cpartner + ", movementDate=" + movementDate
  // + ", jumboCode=" + jumboCode + ", bakarweigth=" + bakarweigth + ", gsm=" + gsm + ", qotr="
  // + qotr + ", width=" + width + ", qualitydegree=" + qualitydegree + ", producttype="
  // + producttype + ", iznno=" + iznno + "]";
  // }

}
