package com.ibox.paper.reports.entity;

import org.openbravo.data.FieldProvider;

import com.ibox.paper.reports.gui.employment_report.Daily_Report;

public class EmploymentEntity implements FieldProvider {

  String fromdate;
  String todate;
  String number;
  String name;
  String nationalid;
  String hiredate;
  String period;
  String birthdate;
  String basicsalary;
  String allreward;
  String specialreward;
  String total;
  String orgcode;

  public String getOrgcode() {
    return orgcode;
  }

  public void setOrgcode(String orgcode) {
    this.orgcode = orgcode;
  }

  String fromemp;
  String toemp;

  @Override
  public String getField(String fieldName) {

    if (fieldName.equalsIgnoreCase("fromdate"))
      return fromdate;
    else if (fieldName.equalsIgnoreCase("todate"))
      return todate;
    else if (fieldName.equalsIgnoreCase("number"))
      return number;
    else if (fieldName.equalsIgnoreCase("name"))
      return name;
    else if (fieldName.equalsIgnoreCase("nationalid"))
      return nationalid;
    else if (fieldName.equalsIgnoreCase("hiredate"))
      return hiredate;
    else if (fieldName.equalsIgnoreCase("period"))
      return period;
    else if (fieldName.equalsIgnoreCase("birthdate"))
      return birthdate;
    else if (fieldName.equalsIgnoreCase("basicsalary"))
      return basicsalary;
    else if (fieldName.equalsIgnoreCase("allreward"))
      return allreward;
    else if (fieldName.equalsIgnoreCase("specialreward"))
      return specialreward;
    else if (fieldName.equalsIgnoreCase("total"))
      return total;
    else if (fieldName.equalsIgnoreCase("fromemp"))
      return fromemp;
    else if (fieldName.equalsIgnoreCase("toemp"))
      return toemp;
    else if (fieldName.equalsIgnoreCase("orgcode"))
      return orgcode;

    else
      return null;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNationalid() {
    return nationalid;
  }

  public void setNationalid(String nationalid) {
    this.nationalid = nationalid;
  }

  public String getHiredate() {
    return hiredate;
  }

  public void setHiredate(String hiredate) {
    this.hiredate = hiredate;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  public String getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(String birthdate) {
    this.birthdate = birthdate;
  }

  public String getBasicsalary() {
    return basicsalary;
  }

  public void setBasicsalary(String basicsalary) {
    this.basicsalary = basicsalary;
  }

  public String getAllreward() {
    return allreward;
  }

  public void setAllreward(String allreward) {
    this.allreward = allreward;
  }

  public String getSpecialreward() {
    return specialreward;
  }

  public void setSpecialreward(String specialreward) {
    this.specialreward = specialreward;
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

  public String getTotal() {
    return total;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  public String getFromemp() {
    return fromemp;
  }

  public void setFromemp(String fromemp) {
    this.fromemp = fromemp;
  }

  public String getToemp() {
    return toemp;
  }

  public void setToemp(String toemp) {
    this.toemp = toemp;
  }

  public static String treeOrg(Daily_Report daily_Report, String client) {
    // TODO Auto-generated method stub
    return null;
  }

  public static EmploymentEntity[] set() {
    // TODO Auto-generated method stub
    return null;
  }

  public static EmploymentEntity[] select(Daily_Report daily_Report, String language,
      String context, String context2, String strC_BPartner_ID, String strCategory_ID,
      String strProductType, Object object, Object object2, String members) {
    // TODO Auto-generated method stub
    return null;
  }

  public static String bPartnerDescription(Daily_Report daily_Report, String strC_BPartner_ID) {
    // TODO Auto-generated method stub
    return null;
  }

  public static int process(Daily_Report daily_Report, String strNewProductCategory,
      String strMProductId) {
    // TODO Auto-generated method stub
    return 0;
  }

}
