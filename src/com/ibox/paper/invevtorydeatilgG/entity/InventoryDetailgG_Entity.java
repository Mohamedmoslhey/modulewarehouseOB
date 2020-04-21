package com.ibox.paper.invevtorydeatilgG.entity;

import java.math.BigDecimal;

import org.openbravo.data.FieldProvider;

public class InventoryDetailgG_Entity implements FieldProvider {

  private BigDecimal bno;
  private BigDecimal bno150 = BigDecimal.ZERO;
  private BigDecimal bno200 = BigDecimal.ZERO;
  private BigDecimal bno230 = BigDecimal.ZERO;
  private BigDecimal bno250 = BigDecimal.ZERO;
  private BigDecimal bno270 = BigDecimal.ZERO;
  private BigDecimal bno280 = BigDecimal.ZERO;
  private BigDecimal bno300 = BigDecimal.ZERO;
  private BigDecimal bno320 = BigDecimal.ZERO;
  private BigDecimal bno330 = BigDecimal.ZERO;
  private BigDecimal bno340 = BigDecimal.ZERO;
  private BigDecimal bno350 = BigDecimal.ZERO;
  private BigDecimal bno370 = BigDecimal.ZERO;
  private BigDecimal bno380 = BigDecimal.ZERO;
  private BigDecimal bno400 = BigDecimal.ZERO;
  private BigDecimal bno420 = BigDecimal.ZERO;
  private BigDecimal bno450 = BigDecimal.ZERO;
  private BigDecimal bno470 = BigDecimal.ZERO;
  private BigDecimal bno480 = BigDecimal.ZERO;
  private BigDecimal bno500 = BigDecimal.ZERO;
  private BigDecimal bno550 = BigDecimal.ZERO;
  private BigDecimal wno150 = BigDecimal.ZERO;
  private BigDecimal wno200 = BigDecimal.ZERO;
  private BigDecimal wno230 = BigDecimal.ZERO;
  private BigDecimal wno250 = BigDecimal.ZERO;
  private BigDecimal wno270 = BigDecimal.ZERO;
  private BigDecimal wno280 = BigDecimal.ZERO;
  private BigDecimal wno300 = BigDecimal.ZERO;
  private BigDecimal wno320 = BigDecimal.ZERO;
  private BigDecimal wno330 = BigDecimal.ZERO;
  private BigDecimal wno340 = BigDecimal.ZERO;
  private BigDecimal wno350 = BigDecimal.ZERO;
  private BigDecimal wno370 = BigDecimal.ZERO;
  private BigDecimal wno380 = BigDecimal.ZERO;
  private BigDecimal wno400 = BigDecimal.ZERO;
  private BigDecimal wno420 = BigDecimal.ZERO;
  private BigDecimal wno450 = BigDecimal.ZERO;
  private BigDecimal wno470 = BigDecimal.ZERO;
  private BigDecimal wno480 = BigDecimal.ZERO;
  private BigDecimal wno500 = BigDecimal.ZERO;
  private BigDecimal wno550 = BigDecimal.ZERO;
  private BigDecimal wno;
  private BigDecimal bakarawidth;
  ////

  /////////

  // private String notes; //
  // private String prodate; //
  private String producttype;

  // private String average;
  private String machine; //

  private String qualitydegree;

  // //

  // this is for make sure that wahst you set in java model will
  // be assign for its position in jrxml

  @Override
  public String getField(String fieldName) {
    // TODO Auto-generated method stub

    if (fieldName.equalsIgnoreCase("producttype")) {
      return producttype;
    }

    // if (fieldName.equalsIgnoreCase("productionres"))
    // return productionres;
    //
    else if (fieldName.equalsIgnoreCase("bno")) {
      return bno.toString();
    } else if (fieldName.equalsIgnoreCase("bno150")) {
      return bno150.toString();
    } else if (fieldName.equalsIgnoreCase("bno200")) {
      return bno200.toString();
    } else if (fieldName.equalsIgnoreCase("bno230")) {
      return bno230.toString();
    } else if (fieldName.equalsIgnoreCase("bno250")) {
      return bno250.toString();
    } else if (fieldName.equalsIgnoreCase("bno270")) {
      return bno270.toString();
    } else if (fieldName.equalsIgnoreCase("bno280")) {
      return bno280.toString();
    } else if (fieldName.equalsIgnoreCase("bno300")) {
      return bno300.toString();
    } else if (fieldName.equalsIgnoreCase("bno320")) {
      return bno320.toString();
    } else if (fieldName.equalsIgnoreCase("bno330")) {
      return bno330.toString();
    } else if (fieldName.equalsIgnoreCase("bno340")) {
      return bno340.toString();
    } else if (fieldName.equalsIgnoreCase("bno350")) {
      return bno350.toString();
    } else if (fieldName.equalsIgnoreCase("bno370")) {
      return bno370.toString();
    } else if (fieldName.equalsIgnoreCase("bno380")) {
      return bno380.toString();
    } else if (fieldName.equalsIgnoreCase("bno400")) {
      return bno400.toString();
    } else if (fieldName.equalsIgnoreCase("bno420")) {
      return bno420.toString();
    } else if (fieldName.equalsIgnoreCase("bno450")) {
      return bno450.toString();
    } else if (fieldName.equalsIgnoreCase("bno470")) {
      return bno470.toString();
    } else if (fieldName.equalsIgnoreCase("bno480")) {
      return bno480.toString();
    } else if (fieldName.equalsIgnoreCase("bno500")) {
      return bno500.toString();
    } else if (fieldName.equalsIgnoreCase("bno550")) {
      return bno550.toString();
    } else if (fieldName.equalsIgnoreCase("wno150")) {
      return wno150.toString();
    } else if (fieldName.equalsIgnoreCase("wno200")) {
      return wno200.toString();
    } else if (fieldName.equalsIgnoreCase("wno230")) {
      return wno230.toString();
    } else if (fieldName.equalsIgnoreCase("wno250")) {
      return wno250.toString();
    } else if (fieldName.equalsIgnoreCase("wno270")) {
      return wno270.toString();
    } else if (fieldName.equalsIgnoreCase("wno280")) {
      return wno280.toString();
    } else if (fieldName.equalsIgnoreCase("wno300")) {
      return wno300.toString();
    } else if (fieldName.equalsIgnoreCase("wno320")) {
      return wno320.toString();
    } else if (fieldName.equalsIgnoreCase("wno330")) {
      return wno330.toString();
    } else if (fieldName.equalsIgnoreCase("wno340")) {
      return wno340.toString();
    } else if (fieldName.equalsIgnoreCase("wno350")) {
      return wno350.toString();
    } else if (fieldName.equalsIgnoreCase("wno370")) {
      return wno370.toString();
    } else if (fieldName.equalsIgnoreCase("wno380")) {
      return wno380.toString();
    } else if (fieldName.equalsIgnoreCase("wno400")) {
      return wno400.toString();
    } else if (fieldName.equalsIgnoreCase("wno420")) {
      return wno420.toString();
    } else if (fieldName.equalsIgnoreCase("wno450")) {
      return wno450.toString();
    } else if (fieldName.equalsIgnoreCase("wno470")) {
      return wno470.toString();
    } else if (fieldName.equalsIgnoreCase("wno480")) {
      return wno480.toString();
    } else if (fieldName.equalsIgnoreCase("wno500")) {
      return wno500.toString();
    } else if (fieldName.equalsIgnoreCase("wno550")) {
      return wno550.toString();
    } else if (fieldName.equalsIgnoreCase("wno")) {
      return wno.toString();
    } else if (fieldName.equalsIgnoreCase("bakarawidth")) {
      return bakarawidth.toString();
    }

    ////

    else if (fieldName.equalsIgnoreCase("machine")) {
      return machine;

    }

    else if (fieldName.equalsIgnoreCase("qualitydegree")) {
      return qualitydegree;

    } else

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

  public BigDecimal getBakarawidth() {
    return bakarawidth;
  }

  public void setBakarawidth(BigDecimal bakarawidth) {
    this.bakarawidth = bakarawidth;
  }

  public BigDecimal getBno200() {
    return bno200;
  }

  public void setBno200(BigDecimal bno200) {
    this.bno200 = bno200;
  }

  public String getQualitydegree() {
    return qualitydegree;
  }

  public void setQualitydegree(String qualitydegree) {
    this.qualitydegree = qualitydegree;
  }

  public void setProducttype(String producttype) {
    this.producttype = producttype;
  }

  public BigDecimal getBno150() {
    return bno150;
  }

  public void setBno150(BigDecimal bno150) {
    this.bno150 = bno150;
  }

  public BigDecimal getBno230() {
    return bno230;
  }

  public void setBno230(BigDecimal bno230) {
    this.bno230 = bno230;
  }

  public BigDecimal getBno250() {
    return bno250;
  }

  public void setBno250(BigDecimal bno250) {
    this.bno250 = bno250;
  }

  public BigDecimal getBno270() {
    return bno270;
  }

  public BigDecimal getWno470() {
    return wno470;
  }

  public void setWno470(BigDecimal wno470) {
    this.wno470 = wno470;
  }

  public void setBno270(BigDecimal bno270) {
    this.bno270 = bno270;
  }

  public BigDecimal getBno280() {
    return bno280;
  }

  public void setBno280(BigDecimal bno280) {
    this.bno280 = bno280;
  }

  public BigDecimal getBno300() {
    return bno300;
  }

  public BigDecimal getBno470() {
    return bno470;
  }

  public void setBno470(BigDecimal bno470) {
    this.bno470 = bno470;
  }

  public void setBno300(BigDecimal bno300) {
    this.bno300 = bno300;
  }

  public BigDecimal getBno320() {
    return bno320;
  }

  public void setBno320(BigDecimal bno320) {
    this.bno320 = bno320;
  }

  public BigDecimal getWno320() {
    return wno320;
  }

  public void setWno320(BigDecimal wno320) {
    this.wno320 = wno320;
  }

  public BigDecimal getBno330() {
    return bno330;
  }

  public void setBno330(BigDecimal bno330) {
    this.bno330 = bno330;
  }

  public BigDecimal getBno340() {
    return bno340;
  }

  public void setBno340(BigDecimal bno340) {
    this.bno340 = bno340;
  }

  public BigDecimal getBno350() {
    return bno350;
  }

  public void setBno350(BigDecimal bno350) {
    this.bno350 = bno350;
  }

  public BigDecimal getBno370() {
    return bno370;
  }

  public void setBno370(BigDecimal bno370) {
    this.bno370 = bno370;
  }

  public BigDecimal getBno380() {
    return bno380;
  }

  public void setBno380(BigDecimal bno380) {
    this.bno380 = bno380;
  }

  public BigDecimal getBno400() {
    return bno400;
  }

  public void setBno400(BigDecimal bno400) {
    this.bno400 = bno400;
  }

  public BigDecimal getBno420() {
    return bno420;
  }

  public void setBno420(BigDecimal bno420) {
    this.bno420 = bno420;
  }

  public BigDecimal getBno450() {
    return bno450;
  }

  public void setBno450(BigDecimal bno450) {
    this.bno450 = bno450;
  }

  public BigDecimal getBno500() {
    return bno500;
  }

  public void setBno500(BigDecimal bno500) {
    this.bno500 = bno500;
  }

  public BigDecimal getBno550() {
    return bno550;
  }

  public void setBno550(BigDecimal bno550) {
    this.bno550 = bno550;
  }

  public BigDecimal getWno150() {
    return wno150;
  }

  public void setWno150(BigDecimal wno150) {
    this.wno150 = wno150;
  }

  public BigDecimal getWno200() {
    return wno200;
  }

  public void setWno200(BigDecimal wno200) {
    this.wno200 = wno200;
  }

  public BigDecimal getWno230() {
    return wno230;
  }

  public void setWno230(BigDecimal wno230) {
    this.wno230 = wno230;
  }

  public BigDecimal getWno250() {
    return wno250;
  }

  public void setWno250(BigDecimal wno250) {
    this.wno250 = wno250;
  }

  public BigDecimal getWno270() {
    return wno270;
  }

  public void setWno270(BigDecimal wno270) {
    this.wno270 = wno270;
  }

  public BigDecimal getWno280() {
    return wno280;
  }

  public void setWno280(BigDecimal wno280) {
    this.wno280 = wno280;
  }

  public BigDecimal getWno300() {
    return wno300;
  }

  public void setWno300(BigDecimal wno300) {
    this.wno300 = wno300;
  }

  public BigDecimal getWno330() {
    return wno330;
  }

  public void setWno330(BigDecimal wno330) {
    this.wno330 = wno330;
  }

  public BigDecimal getWno340() {
    return wno340;
  }

  public void setWno340(BigDecimal wno340) {
    this.wno340 = wno340;
  }

  public BigDecimal getWno350() {
    return wno350;
  }

  public void setWno350(BigDecimal wno350) {
    this.wno350 = wno350;
  }

  public BigDecimal getWno370() {
    return wno370;
  }

  public void setWno370(BigDecimal wno370) {
    this.wno370 = wno370;
  }

  public BigDecimal getWno380() {
    return wno380;
  }

  public void setWno380(BigDecimal wno380) {
    this.wno380 = wno380;
  }

  public BigDecimal getWno400() {
    return wno400;
  }

  public void setWno400(BigDecimal wno400) {
    this.wno400 = wno400;
  }

  public BigDecimal getWno420() {
    return wno420;
  }

  public void setWno420(BigDecimal wno420) {
    this.wno420 = wno420;
  }

  public BigDecimal getWno450() {
    return wno450;
  }

  public void setWno450(BigDecimal wno450) {
    this.wno450 = wno450;
  }

  public BigDecimal getWno500() {
    return wno500;
  }

  public void setWno500(BigDecimal wno500) {
    this.wno500 = wno500;
  }

  public BigDecimal getWno550() {
    return wno550;
  }

  public void setWno550(BigDecimal wno550) {
    this.wno550 = wno550;
  }

  public BigDecimal getBno480() {
    return bno480;
  }

  public void setBno480(BigDecimal bno480) {
    this.bno480 = bno480;
  }

  public BigDecimal getWno480() {
    return wno480;
  }

  public void setWno480(BigDecimal wno480) {
    this.wno480 = wno480;
  }

}
