package com.ibox.paper.dreportdpermonth.entity;

import java.math.BigDecimal;

import org.openbravo.data.FieldProvider;

public class ProductPerMonth_Entity implements FieldProvider {

  private ProductPerMonth_EntityData data = new ProductPerMonth_EntityData();

  // this is for make sure that wahst you set in java model will
  // be assign for its position in jrxml

  @Override
  public String getField(String fieldName) {
    // TODO Auto-generated method stub

    if (fieldName.equalsIgnoreCase("bno")) {
      return data.bno.toString();
    } else if (fieldName.equalsIgnoreCase("wo")) {
      return data.wo.toString();
    } else if (fieldName.equalsIgnoreCase("bnor")) {
      return data.bnor.toString();

    } else if (fieldName.equalsIgnoreCase("bnor1v2")) {
      return data.bnor1v2.toString();
    } else if (fieldName.equalsIgnoreCase("wasn")) {
      return data.wasn.toString();
    } else if (fieldName.equalsIgnoreCase("firstresonfars")) {
      return data.firstresonfars;
    }

    if (fieldName.equalsIgnoreCase("fromdate")) {
      return data.fromdate.toString();

    }
    if (fieldName.equalsIgnoreCase("todate")) {
      return data.todate.toString();

    }

    if (fieldName.equalsIgnoreCase("monthdate")) {
      return data.monthdate;

    }

    if (fieldName.equalsIgnoreCase("secondresonfars")) {
      return data.secondresonfars;

    } else

      return null;
  }

  public BigDecimal getBnor() {
    return data.bnor;
  }

  public void setBnor(BigDecimal bnor) {
    this.data.bnor = bnor;
  }

  public BigDecimal getWasn() {
    return data.wasn;
  }

  public void setWasn(BigDecimal wasn) {
    this.data.wasn = wasn;
  }

  public BigDecimal getWo() {
    return data.wo;
  }

  public void setWo(BigDecimal wo) {
    this.data.wo = wo;
  }

  public String getMonthdate() {
    return data.monthdate;
  }

  public void setMonthdate(String monthdate) {
    this.data.monthdate = monthdate;
  }

  public String getFirstresonfars() {
    return data.firstresonfars;
  }

  public void setFirstresonfars(String firstresonfars) {
    this.data.firstresonfars = firstresonfars;
  }

  public String getFromdate() {
    return data.fromdate;
  }

  public void setFromdate(String fromdate) {
    this.data.fromdate = fromdate;
  }

  public String getTodate() {
    return data.todate;
  }

  public void setTodate(String todate) {
    this.data.todate = todate;
  }

  // get and set for all entity in jrxml sheet افهم بقى
  public BigDecimal getBno() {
    return data.bno;
  }

  public void setBno(BigDecimal bno) {
    this.data.bno = bno;
  }

  //

  //

  public String getSecondresonfars() {
    return data.secondresonfars;
  }

  //
  public void setSecondresonfars(String secondresonfars) {
    this.data.secondresonfars = secondresonfars;
  }

  public BigDecimal getBnor1v2() {
    return data.bnor1v2;
  }

  public void setBnor1v2(BigDecimal bnor1v2) {
    this.data.bnor1v2 = bnor1v2;
  }

}
