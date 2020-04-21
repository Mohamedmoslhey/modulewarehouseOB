package com.ibox.paper.salesreportbyrollspe.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBDal;
import org.openbravo.model.common.businesspartner.BusinessPartner;
import org.openbravo.model.common.plm.Product;
import org.openbravo.model.materialmgmt.transaction.ShipmentInOutLine;

import com.ibox.paper.salesreportbyrollspe.entity.SalesReportRollspec_Entity;

public class SalesReportRollspec_Model {

  public SalesReportRollspec_Entity[] get_report_data(String jumboCodeid) {
    // , String cutshiftid,
    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    List<SalesReportRollspec_Entity> entityList = new ArrayList<SalesReportRollspec_Entity>();
    SalesReportRollspec_Entity[] entityArray = null;
    Product currentBakraJumbo = null;

    BusinessPartner currentBusinessPartner = null;
    Date fromDate = null;
    Date toDate = null;

    List<ShipmentInOutLine> materialtrxList = new ArrayList();

    if ((jumboCodeid != null) && (!jumboCodeid.equals(""))) {
      currentBakraJumbo = (Product) OBDal.getInstance().get(Product.class, jumboCodeid);
    }

    String hql = "select h from MaterialMgmtShipmentInOutLine h where h.client=:client";

    if ((jumboCodeid != null) && (!jumboCodeid.equals(""))) {
      hql = hql + " and  h.product =:jumboCodeid  ";
    }
    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if ((jumboCodeid != null) && (!jumboCodeid.equals(""))) {
      query.setParameter("jumboCodeid", currentBakraJumbo);
    }
    materialtrxList = query.list();

    for (ShipmentInOutLine currenttransaction : materialtrxList) {

      SalesReportRollspec_Entity entity = new SalesReportRollspec_Entity();

      entity.setJumboCode(currenttransaction.getPAPERJumboCode());

      entity.setCpartner(currenttransaction.getShipmentReceipt().getBusinessPartner().getName());
      entity.setBakarweigth(currenttransaction.getPaperBakawieght());
      entity.setGsm(currenttransaction.getPAPERGSM());
      entity.setQotr(currenttransaction.getPAPERBakkaraQotr().toString());
      entity.setWidth(currenttransaction.getPaperBakarawidth());
      entity.setQualitydegree(currenttransaction.getPaperQualityDegree().getDegree());
      producttypecon(currenttransaction, entity);

      entity.setIznno(currenttransaction.getShipmentReceipt().getDocumentNo());
      entityList.add(entity);
    }

    entityArray = new SalesReportRollspec_Entity[entityList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }

  private void producttypecon(ShipmentInOutLine currenttransaction,
      SalesReportRollspec_Entity entity) {
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
