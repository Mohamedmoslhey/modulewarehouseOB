package com.ibox.paper.salesbarcodescan.entity;

import java.math.BigDecimal;

import org.openbravo.data.FieldProvider;

public class SalesScan_Entity implements FieldProvider {

  private BigDecimal bakarweigth;

  private String iznno;

  private BigDecimal wno;
  private String producttype;
  private String drivName;
  private String carNo;

  private BigDecimal width;
  private String movementDate;
  private String jumboCode;
  private BigDecimal gsm;
  private String cpartner;
  private String machine; //
  private String qotr;
  private String qualitydegree;

  @Override
  public String getField(String fieldName) {
    // TODO Auto-generated method stub

    if (fieldName.equalsIgnoreCase("cpartner")) {
      return cpartner;
    } else if (fieldName.equalsIgnoreCase("movementDate")) {
      return movementDate;
    }

    else if (fieldName.equalsIgnoreCase("jumboCode")) {
      return jumboCode;

    } else if (fieldName.equalsIgnoreCase("drivName")) {
      return drivName;

    } else if (fieldName.equalsIgnoreCase("carNo")) {
      return carNo;

    }

    else if (fieldName.equalsIgnoreCase("wno")) {
      return wno.toString();

    } else if (fieldName.equalsIgnoreCase("producttype")) {
      return producttype;
    }

    else if (fieldName.equalsIgnoreCase("width"))

    {
      return width.toString();
    } else if (fieldName.equalsIgnoreCase("iznno")) {
      return iznno;

    }
    //
    else if (fieldName.equalsIgnoreCase("gsm")) {
      return gsm.toString();

    } else if (fieldName.equalsIgnoreCase("bakarweigth")) {
      return bakarweigth.toString();

    }

    else if (fieldName.equalsIgnoreCase("machine")) {
      return machine;

    }

    else if (fieldName.equalsIgnoreCase("qotr")) {
      return qotr;

    }

    else if (fieldName.equalsIgnoreCase("qualitydegree")) {
      return qualitydegree;

    } else

      return null;
  }

  public String getDrivName() {
    return drivName;
  }

  public void setDrivName(String drivName) {
    this.drivName = drivName;
  }

  public String getCarNo() {
    return carNo;
  }

  public void setCarNo(String carNo) {
    this.carNo = carNo;
  }

  public BigDecimal getWno() {
    return wno;
  }

  public void setWno(BigDecimal wno) {
    this.wno = wno;
  }

  public String getIznno() {
    return iznno;
  }

  public void setIznno(String iznno) {
    this.iznno = iznno;
  }

  public String getMovementDate() {
    return movementDate;
  }

  public void setMovementDate(String movementDate) {
    this.movementDate = movementDate;
  }

  public String getJumboCode() {
    return jumboCode;
  }

  public void setJumboCode(String jumboCode) {
    this.jumboCode = jumboCode;
  }

  public String getCpartner() {
    return cpartner;
  }

  public void setCpartner(String cpartner) {
    this.cpartner = cpartner;
  }

  public String getMachine() {
    return machine;
  }

  public void setMachine(String machine) {
    this.machine = machine;
  }

  public String getQotr() {
    return qotr;
  }

  public void setQotr(String qotr) {
    this.qotr = qotr;
  }

  public String getProducttype() {
    return producttype;
  }

  public void setProducttype(String producttype) {
    this.producttype = producttype;
  }

  public BigDecimal getWidth() {
    return width;
  }

  public void setWidth(BigDecimal width) {
    this.width = width;
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

  public String getQualitydegree() {
    return qualitydegree;
  }

  public void setQualitydegree(String qualitydegree) {
    this.qualitydegree = qualitydegree;
  }

}
