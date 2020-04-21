package com.ibox.paper.salesbarcodescan.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;
import org.openbravo.model.common.enterprise.Locator;
import org.openbravo.model.materialmgmt.transaction.ShipmentInOut;
import org.openbravo.model.materialmgmt.transaction.ShipmentInOutLine;

import com.ibox.paper.salesbarcodescan.entity.SalesScan_Entity;

public class SalesScanner_Model {

  public SalesScan_Entity[] get_report_data(String invD) {

    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    List<SalesScan_Entity> entityList = new ArrayList<SalesScan_Entity>();

    SalesScan_Entity[] entityArray = null;
    Locator cuu = null;
    ShipmentInOut inOut = OBDal.getInstance().get(ShipmentInOut.class, invD);
    SimpleDateFormat sDate = new SimpleDateFormat("MM");
    Date s = null;

    List<ShipmentInOut> ShipmentList = new ArrayList<ShipmentInOut>();

    String hql = "select h from MaterialMgmtShipmentInOut h where h.client=:client"
        + " and h.movementType IN ('C-', 'C+') and h.logistic = 'N' and h.documentType.return='N'";

    if (inOut != null && !inOut.equals(""))
      hql = hql + " and  h.id =:invD ";

    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (inOut != null && !inOut.equals(""))
      query.setParameter("invD", inOut.getId());

    ShipmentList = query.list();

    ///////////////////////////

    SalesScan_Entity entity = null;

    for (ShipmentInOut currenttransaction : ShipmentList) {

      List<ShipmentInOutLine> shipmentDetailList = new ArrayList<ShipmentInOutLine>();
      OBCriteria<ShipmentInOutLine> shipmentCrieria = OBDal.getInstance()
          .createCriteria(ShipmentInOutLine.class);
      shipmentCrieria
          .add(Restrictions.eq(ShipmentInOutLine.PROPERTY_SHIPMENTRECEIPT, currenttransaction));

      shipmentDetailList = shipmentCrieria.list();

      for (ShipmentInOutLine cuLine : currenttransaction.getMaterialMgmtShipmentInOutLineList()) {

        entity = new SalesScan_Entity();
        if ((cuLine.getShipmentReceipt().getDocumentNo() != null
            || cuLine.getShipmentReceipt().getBusinessPartner() != null
            || cuLine.getShipmentReceipt().getMovementDate() != null
            || cuLine.getPAPERJumboCode() != null || cuLine.getPaperBakawieght() != null
            || cuLine.getPAPERGSM() != null || cuLine.getPAPERBakkaraQotr() != null
            || cuLine.getPaperBakarawidth() != null
            || cuLine.getPaperQualityDegree().getDegree() != null
            || cuLine.getPAPERProductType() != null))
          if (cuLine.getShipmentReceipt().getDocumentNo() == "1000626") {
            entity.setIznno(null);
            entity.setCpartner(null);
            entity.setMovementDate(null);
            entity.setJumboCode(null);

            entity.setBakarweigth(null);
            entity.setGsm(null);
            entity.setQotr(null);
            entity.setWidth(null);
            entity.setQualitydegree(null);

            entity.setWno(null);
          } else {
            entity.setCarNo(cuLine.getShipmentReceipt().getPAPERCarNumber());
            entity.setDrivName(cuLine.getShipmentReceipt().getPaperDriver());

            entity.setWno(cuLine.getShipmentReceipt().getPAPERTotalShipmentWeight());
            entity.setIznno(cuLine.getShipmentReceipt().getDocumentNo());
            entity.setCpartner(cuLine.getShipmentReceipt().getBusinessPartner().getName());
            entity.setMovementDate(cuLine.getShipmentReceipt().getMovementDate().toString());
            entity.setJumboCode(cuLine.getPAPERJumboCode());

            entity.setBakarweigth(cuLine.getPaperBakawieght());
            entity.setGsm(cuLine.getPAPERGSM());
            entity.setQotr(cuLine.getPAPERBakkaraQotr().toString());
            entity.setWidth(cuLine.getPaperBakarawidth());

            entity.setQualitydegree(cuLine.getPaperQualityDegree().getDegree());

            if (cuLine.getPAPERProductType().equals("3")) {
              entity.setProducttype("Core Board");
            }
            if (cuLine.getPAPERProductType().equals("2")) {
              entity.setProducttype("Meduim Fluting");
            }
            if (cuLine.getPAPERProductType().equals("1")) {
              entity.setProducttype("Manilla");
            }
            if (cuLine.getPAPERProductType().equals("4")) {
              entity.setProducttype("Test Liner");
            }
            if (cuLine.getPAPERProductType().equals("5")) {
              entity.setProducttype("special Core Board");
            }

          }
        entityList.add(entity);

      }

      // -----------------------------------------------------

      // entity.setBakarweigth(currentNum2);
      // // to set bakranumber
      //
      // entity.setTotbakranum(new BigDecimal(currentNum1));

    }
    // ----------------------

    entityArray = new SalesScan_Entity[entityList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }

  public SalesScan_Entity[] get_copy_data(String invD) {

    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    List<SalesScan_Entity> entityList = new ArrayList<SalesScan_Entity>();

    SalesScan_Entity[] entityArray = null;
    Locator cuu = null;
    ShipmentInOut inOut = OBDal.getInstance().get(ShipmentInOut.class, invD);
    SimpleDateFormat sDate = new SimpleDateFormat("MM");
    Date s = null;

    List<ShipmentInOut> ShipmentList = new ArrayList<ShipmentInOut>();

    String hql = "select h from MaterialMgmtShipmentInOut h where h.client=:client"
        + " and h.movementType IN ('C-', 'C+') and h.logistic = 'N' and h.documentType.return='N'";

    if (inOut != null && !inOut.equals(""))
      hql = hql + " and  h.id =:invD ";

    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (inOut != null && !inOut.equals(""))
      query.setParameter("invD", inOut.getId());

    ShipmentList = query.list();

    ///////////////////////////

    SalesScan_Entity entity = null;

    for (ShipmentInOut currenttransaction : ShipmentList) {

      List<ShipmentInOutLine> shipmentDetailList = new ArrayList<ShipmentInOutLine>();
      OBCriteria<ShipmentInOutLine> shipmentCrieria = OBDal.getInstance()
          .createCriteria(ShipmentInOutLine.class);
      shipmentCrieria
          .add(Restrictions.eq(ShipmentInOutLine.PROPERTY_SHIPMENTRECEIPT, currenttransaction));

      shipmentDetailList = shipmentCrieria.list();

      for (ShipmentInOutLine cuLine : currenttransaction.getMaterialMgmtShipmentInOutLineList()) {

        entity = new SalesScan_Entity();
        if ((cuLine.getShipmentReceipt().getDocumentNo() != null
            || cuLine.getShipmentReceipt().getBusinessPartner() != null
            || cuLine.getShipmentReceipt().getMovementDate() != null
            || cuLine.getPAPERJumboCode() != null || cuLine.getPaperBakawieght() != null
            || cuLine.getPAPERGSM() != null || cuLine.getPAPERBakkaraQotr() != null
            || cuLine.getPaperBakarawidth() != null
            || cuLine.getPaperQualityDegree().getDegree() != null
            || cuLine.getPAPERProductType() != null))
          if (cuLine.getShipmentReceipt().getDocumentNo() == "1000626") {
            entity.setIznno(null);
            entity.setCpartner(null);
            entity.setMovementDate(null);
            entity.setJumboCode(null);

            entity.setBakarweigth(null);
            entity.setGsm(null);
            entity.setQotr(null);
            entity.setWidth(null);
            entity.setQualitydegree(null);

            entity.setWno(null);
          } else {
            entity.setCarNo(cuLine.getShipmentReceipt().getPAPERCarNumber());
            entity.setDrivName(cuLine.getShipmentReceipt().getPaperDriver());

            entity.setWno(cuLine.getShipmentReceipt().getPAPERTotalShipmentWeight());
            entity.setIznno(cuLine.getShipmentReceipt().getDocumentNo());
            entity.setCpartner(cuLine.getShipmentReceipt().getBusinessPartner().getName());
            entity.setMovementDate(cuLine.getShipmentReceipt().getMovementDate().toString());
            entity.setJumboCode(cuLine.getPAPERJumboCode());

            entity.setBakarweigth(cuLine.getPaperBakawieght());
            entity.setGsm(cuLine.getPAPERGSM());
            entity.setQotr(cuLine.getPAPERBakkaraQotr().toString());
            entity.setWidth(cuLine.getPaperBakarawidth());

            entity.setQualitydegree(cuLine.getPaperQualityDegree().getDegree());

            if (cuLine.getPAPERProductType().equals("3")) {
              entity.setProducttype("Core Board");
            }
            if (cuLine.getPAPERProductType().equals("2")) {
              entity.setProducttype("Meduim Fluting");
            }
            if (cuLine.getPAPERProductType().equals("1")) {
              entity.setProducttype("Manilla");
            }
            if (cuLine.getPAPERProductType().equals("4")) {
              entity.setProducttype("Test Liner");
            }
            if (cuLine.getPAPERProductType().equals("5")) {
              entity.setProducttype("special Core Board");
            }

          }
        entityList.add(entity);

      }

      // -----------------------------------------------------

      // entity.setBakarweigth(currentNum2);
      // // to set bakranumber
      //
      // entity.setTotbakranum(new BigDecimal(currentNum1));

    }
    // ----------------------

    entityArray = new SalesScan_Entity[entityList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }
}
