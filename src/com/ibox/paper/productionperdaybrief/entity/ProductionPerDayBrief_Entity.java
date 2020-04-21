package com.ibox.paper.productionperdaybrief.entity;

import java.math.BigDecimal;

import org.openbravo.data.FieldProvider;

public class ProductionPerDayBrief_Entity implements FieldProvider {

  private String todate;
  private String fromdate;
  private String roductType;
  private BigDecimal bno;
  private BigDecimal bnodeg1 = BigDecimal.ZERO;
  private BigDecimal bnodeg2 = BigDecimal.ZERO;
  private BigDecimal bnodeg3 = BigDecimal.ZERO;
  private BigDecimal bnodeg4 = BigDecimal.ZERO;
  private BigDecimal wnodeg1 = BigDecimal.ZERO;
  private BigDecimal wnodeg2 = BigDecimal.ZERO;
  private BigDecimal wnodeg3 = BigDecimal.ZERO;
  private BigDecimal wnodeg4 = BigDecimal.ZERO;

  private BigDecimal wno;

  private String producttype;

  private String machine; //

  private String shifts;

  // this is for make sure that wahst you set in java model will
  // be assign for its position in jrxml

  @Override
  public String getField(String fieldName) {
    // TODO Auto-generated method stub

    if (fieldName.equalsIgnoreCase("todate"))
      return todate;
    if (fieldName.equalsIgnoreCase("fromdate"))
      return fromdate;

    if (fieldName.equalsIgnoreCase("producttype")) {
      return producttype;
    }
    if (fieldName.equalsIgnoreCase("roductType")) {
      return roductType;
    }

    else if (fieldName.equalsIgnoreCase("bno")) {
      return bno.toString();
    } else if (fieldName.equalsIgnoreCase("bnodeg1")) {
      return bnodeg1.toString();
    } else if (fieldName.equalsIgnoreCase("bnodeg2")) {
      return bnodeg2.toString();
    } else if (fieldName.equalsIgnoreCase("bnodeg3")) {
      return bnodeg3.toString();
    } else if (fieldName.equalsIgnoreCase("bnodeg4")) {
      return bnodeg4.toString();
    } else if (fieldName.equalsIgnoreCase("wnodeg1")) {
      return wnodeg1.toString();
    } else if (fieldName.equalsIgnoreCase("wnodeg2")) {
      return wnodeg2.toString();
    } else if (fieldName.equalsIgnoreCase("wnodeg3")) {
      return wnodeg3.toString();
    } else if (fieldName.equalsIgnoreCase("wnodeg4")) {
      return wnodeg4.toString();
    }

    else if (fieldName.equalsIgnoreCase("wno")) {
      return wno.toString();
    }

    else if (fieldName.equalsIgnoreCase("machine")) {
      return machine;

    }

    else if (fieldName.equalsIgnoreCase("shifts")) {
      return shifts;

    } else

      return null;
  }

  public String getTodate() {
    return todate;
  }

  public void setTodate(String todate) {
    this.todate = todate;
  }

  public String getFromdate() {
    return fromdate;
  }

  public void setFromdate(String fromdate) {
    this.fromdate = fromdate;
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

  public BigDecimal getBno() {
    return bno;
  }

  public void setBno(BigDecimal bno) {
    this.bno = bno;
  }

  public BigDecimal getWno() {
    return wno;
  }

  public void setWno(BigDecimal wno) {
    this.wno = wno;
  }

  public void setProducttype(String producttype) {
    this.producttype = producttype;
  }

  public String getShifts() {
    return shifts;
  }

  public void setShifts(String shifts) {
    this.shifts = shifts;
  }

  public BigDecimal getBnodeg1() {
    return bnodeg1;
  }

  public String getRoductType() {
    return roductType;
  }

  public void setRoductType(String roductType) {
    this.roductType = roductType;
  }

  public void setBnodeg1(BigDecimal bnodeg1) {
    this.bnodeg1 = bnodeg1;
  }

  public BigDecimal getBnodeg2() {
    return bnodeg2;
  }

  public void setBnodeg2(BigDecimal bnodeg2) {
    this.bnodeg2 = bnodeg2;
  }

  public BigDecimal getBnodeg3() {
    return bnodeg3;
  }

  public void setBnodeg3(BigDecimal bnodeg3) {
    this.bnodeg3 = bnodeg3;
  }

  public BigDecimal getBnodeg4() {
    return bnodeg4;
  }

  public void setBnodeg4(BigDecimal bnodeg4) {
    this.bnodeg4 = bnodeg4;
  }

  public BigDecimal getWnodeg1() {
    return wnodeg1;
  }

  public void setWnodeg1(BigDecimal wnodeg1) {
    this.wnodeg1 = wnodeg1;
  }

  public BigDecimal getWnodeg2() {
    return wnodeg2;
  }

  public void setWnodeg2(BigDecimal wnodeg2) {
    this.wnodeg2 = wnodeg2;
  }

  public BigDecimal getWnodeg3() {
    return wnodeg3;
  }

  public void setWnodeg3(BigDecimal wnodeg3) {
    this.wnodeg3 = wnodeg3;
  }

  public BigDecimal getWnodeg4() {
    return wnodeg4;
  }

  public void setWnodeg4(BigDecimal wnodeg4) {
    this.wnodeg4 = wnodeg4;
  }

}
