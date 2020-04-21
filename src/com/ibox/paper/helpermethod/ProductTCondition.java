package com.ibox.paper.helpermethod;

import org.openbravo.model.materialmgmt.transaction.ShipmentInOutLine;

import com.ibox.paper.salesreportbyrollnumber.entity.SalesReportRollnumber_Entity;

public class ProductTCondition {
  public void producttypecon(ShipmentInOutLine currenttransaction,
      SalesReportRollnumber_Entity entity) {
    if (currenttransaction.getPAPERProductType().equals("3")) {
      entity.setProducttype("Core Board");
    }
    if (currenttransaction.getPAPERProductType().equals("2")) {
      entity.setProducttype("Meduim Fluting");
    }
    if (currenttransaction.getPAPERProductType().equals("1")) {
      entity.setProducttype("Manilla");
    }
    if (currenttransaction.getPAPERProductType().equals("4")) {
      entity.setProducttype("Test Liner");
    }
    if (currenttransaction.getPAPERProductType().equals("5")) {
      entity.setProducttype("special Core Board");
    }
  }

}
