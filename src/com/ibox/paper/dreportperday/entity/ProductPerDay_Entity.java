package com.ibox.paper.dreportperday.entity;

import java.math.BigDecimal;

import org.openbravo.data.FieldProvider;

public class ProductPerDay_Entity implements FieldProvider {

  private String proshift;
  // private String cutshift;
  private String qdeg;
  private String faresres;
  private BigDecimal bakarnum;
  private BigDecimal bakarweigth;

  @Override
  public String getField(String fieldName) {
    // TODO Auto-generated method stub

    if (fieldName.equalsIgnoreCase("proshift"))
      return proshift;

    // else if (fieldName.equalsIgnoreCase("cutshift")) {
    // return cutshift;
    //
    // }
    else if (fieldName.equalsIgnoreCase("qdeg")) {
      return qdeg;
    }

    else if (fieldName.equalsIgnoreCase("faresres")) {
      return faresres;

    } else if (fieldName.equalsIgnoreCase("bakarnum")) {
      return bakarnum.toString();
    }

    else if (fieldName.equalsIgnoreCase("bakarweigth")) {
      return bakarweigth.toString();

    } else

      return null;
  }

  public String getProshift() {
    return proshift;
  }

  public void setProshift(String proshift) {
    this.proshift = proshift;
  }

  // public String getCutshift() {
  // return cutshift;
  // }
  //
  // public void setCutshift(String cutshift) {
  // this.cutshift = cutshift;
  // }

  public String getQdeg() {
    return qdeg;
  }

  public void setQdeg(String qdeg) {
    this.qdeg = qdeg;
  }

  public String getFaresres() {
    return faresres;
  }

  public void setFaresres(String faresres) {
    this.faresres = faresres;
  }

  public BigDecimal getBakarnum() {
    return bakarnum;
  }

  public void setBakarnum(BigDecimal bakarnum) {
    this.bakarnum = bakarnum;
  }

  public BigDecimal getBakarweigth() {
    return bakarweigth;
  }

  public void setBakarweigth(BigDecimal bakarweigth) {
    this.bakarweigth = bakarweigth;
  }

  // ", cutshift=" + cutshift +
  @Override
  public String toString() {
    return "ProductPerDay_Entity [proshift=" + proshift + ", qdeg=" + qdeg + ", faresres="
        + faresres + ", bakarnum=" + bakarnum + ", bakarweigth=" + bakarweigth + "]";
  }

}
