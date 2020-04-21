package com.ibox.paper.rep.entity;

import org.openbravo.data.FieldProvider;

public class DailyReports_Entity implements FieldProvider {

  private String shift_type;

  private String quality_degree;

  @Override
  public String getField(String fieldName) {
    if (fieldName.equalsIgnoreCase("shift_type"))
      return shift_type;

    else if (fieldName.equalsIgnoreCase("quality_degree")) {
      return quality_degree;

    } else

      return null;
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

}
