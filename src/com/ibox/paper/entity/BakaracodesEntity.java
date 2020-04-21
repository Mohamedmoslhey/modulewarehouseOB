package com.ibox.paper.entity;

import java.io.FileOutputStream;
import java.math.BigDecimal;

import org.openbravo.data.FieldProvider;

public class BakaracodesEntity implements FieldProvider {

  private String bakaracode;
  private String productType;
  private String notes;
  private String arabicpro;
  // private String docbarcode;
  private FileOutputStream imgt;
  private String Degree;
  private BigDecimal diameter;
  private BigDecimal bakarawidth;
  private BigDecimal gsm;
  private BigDecimal somc;
  private BigDecimal bakaraweight;

  @Override
  public String getField(String fieldName) {
    // TODO Auto-generated method stub

    if (fieldName.equalsIgnoreCase("bakaracode"))
      return bakaracode;

    if (fieldName.equalsIgnoreCase("productType"))
      return productType;

    if (fieldName.equalsIgnoreCase("notes"))
      return notes;
    if (fieldName.equalsIgnoreCase("arabicpro"))
      return arabicpro;
    if (fieldName.equalsIgnoreCase("Degree"))
      return Degree;

    if (fieldName.equalsIgnoreCase("imgt"))
      return imgt.toString();
    //
    // if (fieldName.equalsIgnoreCase("docbarcode"))
    // return docbarcode;

    if (fieldName.equalsIgnoreCase("diameter"))
      return diameter.toString();
    //
    else if (fieldName.equalsIgnoreCase("bakarawidth"))
      return bakarawidth.toString();
    else if (fieldName.equalsIgnoreCase("bakaraweight"))
      return bakaraweight.toString();
    else if (fieldName.equalsIgnoreCase("gsm"))
      return gsm.toString();

    else if (fieldName.equalsIgnoreCase("somc"))
      return somc.toString();

    else
      return null;
  }

  public String getBakaracode() {
    return bakaracode;
  }

  public void setBakaracode(String bakaracode) {
    this.bakaracode = bakaracode;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public FileOutputStream getImgt() {
    return imgt;
  }

  public void setImgt(FileOutputStream imgt) {
    this.imgt = imgt;
  }

  public String getArabicpro() {
    return arabicpro;
  }

  public void setArabicpro(String arabicpro) {
    this.arabicpro = arabicpro;
  }

  //
  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getDegree() {
    return Degree;
  }

  public void setDegree(String degree) {
    Degree = degree;
  }
  //
  // public String getDocbarcode() {
  // return docbarcode;
  // }
  //
  // public void setDocbarcode(String docbarcode) {
  // this.docbarcode = docbarcode;
  // }
  //

  public BigDecimal getBakarawidth() {
    return bakarawidth;
  }

  public BigDecimal getDiameter() {
    return diameter;
  }

  public void setDiameter(BigDecimal diameter) {
    this.diameter = diameter;
  }

  public BigDecimal getSomc() {
    return somc;
  }

  public void setSomc(BigDecimal somc) {
    this.somc = somc;
  }

  public void setBakarawidth(BigDecimal bakarawidth) {
    this.bakarawidth = bakarawidth;
  }

  public BigDecimal getBakaraweight() {
    return bakaraweight;
  }

  public void setBakaraweight(BigDecimal bakaraweight) {
    this.bakaraweight = bakaraweight;
  }

  public BigDecimal getGsm() {
    return gsm;
  }

  public void setGsm(BigDecimal gsm) {
    this.gsm = gsm;
  }

}
