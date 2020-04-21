package com.ibox.paper.salesOrderWithProductionMachine.entity;

import java.math.BigDecimal;

import org.openbravo.data.FieldProvider;

public class SalesProduction_Entity implements FieldProvider {

  private String lproducttype;

  private String fromdate;
  private String todate;
  private String yeart;
  private String producttype;
  private BigDecimal noinmach1 = BigDecimal.ZERO;
  private BigDecimal noinmach2 = BigDecimal.ZERO;
  private BigDecimal noinmachinepro1 = BigDecimal.ZERO;
  private BigDecimal noinmachinepro2 = BigDecimal.ZERO;
  private BigDecimal wodeg1 = BigDecimal.ZERO;
  private BigDecimal wodeg2 = BigDecimal.ZERO;
  private BigDecimal wodeg3 = BigDecimal.ZERO;
  private BigDecimal wodeg4 = BigDecimal.ZERO;
  private String machine; //
  private String pMachine;
  private String pMachine2;

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
    } else if (fieldName.equalsIgnoreCase("pMachine")) {
      return pMachine;
    } else if (fieldName.equalsIgnoreCase("pMachine2")) {
      return pMachine2;
    } else if (fieldName.equalsIgnoreCase("fromdate")) {
      return fromdate;
    } else if (fieldName.equalsIgnoreCase("lproducttype")) {
      return lproducttype;
    } else if (fieldName.equalsIgnoreCase("yeart")) {
      return yeart;
    } else if (fieldName.equalsIgnoreCase("noinmach1")) {
      return noinmach1.toString();
    } else if (fieldName.equalsIgnoreCase("noinmach2")) {
      return noinmach2.toString();
    } else if (fieldName.equalsIgnoreCase("noinmachinepro1")) {
      return noinmachinepro1.toString();
    } else if (fieldName.equalsIgnoreCase("noinmachinepro2")) {
      return noinmachinepro2.toString();
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

  public String getYeart() {
    return yeart;
  }

  public void setYeart(String yeart) {
    this.yeart = yeart;
  }

  public String getpMachine() {
    return pMachine;
  }

  public void setpMachine(String pMachine) {
    this.pMachine = pMachine;
  }

  public String getpMachine2() {
    return pMachine2;
  }

  public void setpMachine2(String pMachine2) {
    this.pMachine2 = pMachine2;
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

  public void setProducttype(String producttype) {
    this.producttype = producttype;
  }

  public BigDecimal getNoinmach1() {
    return noinmach1;
  }

  public void setNoinmach1(BigDecimal noinmach1) {
    this.noinmach1 = noinmach1;
  }

  public BigDecimal getNoinmach2() {
    return noinmach2;
  }

  public void setNoinmach2(BigDecimal noinmach2) {
    this.noinmach2 = noinmach2;
  }

  public BigDecimal getNoinmachinepro1() {
    return noinmachinepro1;
  }

  public void setNoinmachinepro1(BigDecimal noinmachinepro1) {
    this.noinmachinepro1 = noinmachinepro1;
  }

  public BigDecimal getNoinmachinepro2() {
    return noinmachinepro2;
  }

  public void setNoinmachinepro2(BigDecimal noinmachinepro2) {
    this.noinmachinepro2 = noinmachinepro2;
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
