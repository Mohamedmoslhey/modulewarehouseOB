package com.ibox.paper.reportsd.entity;

import java.math.BigDecimal;

import org.openbravo.data.FieldProvider;

public class DailyProductEntity implements FieldProvider {

  private String day;//
  private String shift_type;//
  private String quality_degree;//
  private String group_weight;//
  private String bakra_num;//
  private String quality_type;//
  private String quality_degree_weight;//
  private String quality_bakra_num;//
  private String total_weight;//
  private String total_bakra_num;//
  private BigDecimal roll_weight;

  @Override
  public String getField(String fieldName) {
    if (fieldName.equalsIgnoreCase("day"))
      return day;
    else if (fieldName.equalsIgnoreCase("shift_type"))
      return shift_type;

    else if (fieldName.equalsIgnoreCase("quality_degree"))
      return quality_degree;

    else if (fieldName.equalsIgnoreCase("group_weight"))
      return group_weight;

    else if (fieldName.equalsIgnoreCase("bakra_num"))
      return bakra_num;

    else if (fieldName.equalsIgnoreCase("quality_type"))
      return quality_type;

    else if (fieldName.equalsIgnoreCase("quality_degree_weight"))
      return quality_degree_weight;
    else if (fieldName.equalsIgnoreCase("quality_bakra_num"))
      return quality_bakra_num;

    else if (fieldName.equalsIgnoreCase("total_weight"))
      return total_weight;

    else if (fieldName.equalsIgnoreCase("total_bakra_num"))
      return total_bakra_num;

    else
      return null;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public String getShift_type() {
    return shift_type;
  }

  public void setShift_type(String shift_type) {
    this.shift_type = shift_type;
  }

  public String getQuality_degree() {
    return quality_degree;
  }

  public void setQuality_degree(String quality_degree) {
    this.quality_degree = quality_degree;
  }

  public String getGroup_weight() {
    return group_weight;
  }

  public void setGroup_weight(String group_weight) {
    this.group_weight = group_weight;
  }

  public String getBakra_num() {
    return bakra_num;
  }

  public void setBakra_num(String bakra_num) {
    this.bakra_num = bakra_num;
  }

  public String getQuality_type() {
    return quality_type;
  }

  public void setQuality_type(String quality_type) {
    this.quality_type = quality_type;
  }

  public String getQuality_degree_weight() {
    return quality_degree_weight;
  }

  public void setQuality_degree_weight(String quality_degree_weight) {
    this.quality_degree_weight = quality_degree_weight;
  }

  public String getQuality_bakra_num() {
    return quality_bakra_num;
  }

  public void setQuality_bakra_num(String quality_bakra_num) {
    this.quality_bakra_num = quality_bakra_num;
  }

  public String getTotal_weight() {
    return total_weight;
  }

  public void setTotal_weight(String total_weight) {
    this.total_weight = total_weight;
  }

  public String getTotal_bakra_num() {
    return total_bakra_num;
  }

  public void setTotal_bakra_num(String total_bakra_num) {
    this.total_bakra_num = total_bakra_num;
  }

  public BigDecimal getRoll_weight() {
    return roll_weight;
  }

  public void setRoll_weight(BigDecimal roll_weight) {
    this.roll_weight = roll_weight;
  }

  @Override
  public String toString() {
    return "DailyProductEntity [day=" + day + ", shift_type=" + shift_type + ", quality_degree="
        + quality_degree + ", group_weight=" + group_weight + ", bakra_num=" + bakra_num
        + ", quality_type=" + quality_type + ", quality_degree_weight=" + quality_degree_weight
        + ", quality_bakra_num=" + quality_bakra_num + ", total_weight=" + total_weight
        + ", total_bakra_num=" + total_bakra_num + ", roll_weight=" + roll_weight + "]";
  }

}
