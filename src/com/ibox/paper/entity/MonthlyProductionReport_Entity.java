package com.ibox.paper.entity;

import java.math.BigDecimal;

import org.openbravo.data.FieldProvider;

public class MonthlyProductionReport_Entity implements FieldProvider {

  private String month;
  private String machine_type;
  private String bakra_num;
  private String quality_degree;
  private String quality_type;
  private String group_weight;
  private String total_weight;
  private String quality_degree_weight;
  private String quality_bakra_num;
  private String total_bakra_num;
  private BigDecimal roll_weight;

  @Override
  public String toString() {
    return "MonthlyProductionReport_Entity [month=" + month + ", machine_type=" + machine_type
        + ", bakra_num=" + bakra_num + ", quality_degree=" + quality_degree + ", quality_type="
        + quality_type + ", group_weight=" + group_weight + ", total_weight=" + total_weight
        + ", quality_degree_weight=" + quality_degree_weight + ", quality_bakra_num="
        + quality_bakra_num + ", total_bakra_num=" + total_bakra_num + ", roll_weight="
        + roll_weight + "]";
  }

  @Override
  public String getField(String fieldName) {
    // TODO Auto-generated method stub

    if (fieldName.equalsIgnoreCase("machine_type"))
      return machine_type.toString();

    if (fieldName.equalsIgnoreCase("bakra_num"))
      return bakra_num;

    if (fieldName.equalsIgnoreCase("quality_degree"))
      return quality_degree.toString();

    if (fieldName.equalsIgnoreCase("quality_type"))
      return quality_type;

    if (fieldName.equalsIgnoreCase("group_weight"))
      return group_weight.toString();

    else if (fieldName.equalsIgnoreCase("total_weight"))
      return total_weight.toString();

    else if (fieldName.equalsIgnoreCase("quality_degree_weight"))
      return quality_degree_weight.toString();

    else if (fieldName.equalsIgnoreCase("quality_bakra_num"))
      return quality_bakra_num.toString();

    else if (fieldName.equalsIgnoreCase("total_bakra_num"))
      return total_bakra_num;

    else
      return null;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public String getMachine_type() {
    return machine_type;
  }

  public void setMachine_type(String machine_type) {
    this.machine_type = machine_type;
  }

  public String getBakra_num() {
    return bakra_num;
  }

  public void setBakra_num(String bakra_num) {
    this.bakra_num = bakra_num;
  }

  public String getQuality_degree() {
    return quality_degree;
  }

  public void setQuality_degree(String quality_degree) {
    this.quality_degree = quality_degree;
  }

  public String getQuality_type() {
    return quality_type;
  }

  public void setQuality_type(String quality_type) {
    this.quality_type = quality_type;
  }

  public String getGroup_weight() {
    return group_weight;
  }

  public void setGroup_weight(String group_weight) {
    this.group_weight = group_weight;
  }

  public String getTotal_weight() {
    return total_weight;
  }

  public void setTotal_weight(String total_weight) {
    this.total_weight = total_weight;
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

}
