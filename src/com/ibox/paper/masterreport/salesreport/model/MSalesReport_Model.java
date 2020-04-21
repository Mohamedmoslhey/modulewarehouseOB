package com.ibox.paper.masterreport.salesreport.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;
import org.openbravo.model.common.businesspartner.BusinessPartner;
import org.openbravo.model.common.plm.Product;
import org.openbravo.model.materialmgmt.transaction.ShipmentInOut;
import org.openbravo.model.materialmgmt.transaction.ShipmentInOutLine;

import com.ibox.paper.masterreport.salesreport.entity.MSalesReport_Entity;

public class MSalesReport_Model {

  public MSalesReport_Entity[] get_report_data(String partnerid, String datefrom, String dateto) {
    // , String cutshiftid,
    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    List<MSalesReport_Entity> entityList = new ArrayList<MSalesReport_Entity>();
    MSalesReport_Entity[] entityArray = null;
    Product currentBakraJumbo = null;

    BusinessPartner currentBusinessPartner = null;
    Date fromDate = null;
    Date toDate = null;

    if (partnerid != null && !partnerid.equals("")) {
      currentBusinessPartner = OBDal.getInstance().get(BusinessPartner.class, partnerid);
    }

    if (datefrom != null && !datefrom.equals("")) {
      try {
        fromDate = formate.parse(datefrom);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    if (dateto != null && !dateto.equals("")) {
      try {
        toDate = formate.parse(dateto);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    List<ShipmentInOut> materialtrxList = new ArrayList<ShipmentInOut>();

    // if (fromDate != null)
    // hql = hql + " and h.movementDate >=:fromdate ";
    //
    // if (toDate != null)
    // hql = hql + " and h.movementDate <=:todate ";
    //
    // Query query = OBDal.getInstance().getSession().createQuery(hql);
    // query.setParameter("client", OBContext.getOBContext().getCurrentClient());
    //
    // if (fromDate != null)
    // query.setParameter("fromdate", fromDate);
    //
    // if (toDate != null)
    // query.setParameter("todate", toDate);

    // e.movementType IN ('C-', 'C+') and e.logistic = 'N' and e.documentType.return=false
    String hql = "select h from MaterialMgmtShipmentInOut h where h.client=:client"
        + " and h.movementType IN ('C-', 'C+') and h.logistic = 'N' and h.documentType.return='N'";

    if (currentBusinessPartner != null && !currentBusinessPartner.equals("")) {
      hql = hql + " and  h.businessPartner =:partnerid  ";
    }

    if (fromDate != null && !fromDate.equals(""))
      hql = hql + " and h.movementDate >=:fromdate ";

    if (toDate != null && !toDate.equals(""))
      hql = hql + " and h.movementDate <=:todate ";

    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (currentBusinessPartner != null && !currentBusinessPartner.equals(""))
      query.setParameter("partnerid", currentBusinessPartner);

    if (fromDate != null && !fromDate.equals(""))
      query.setParameter("fromdate", fromDate);

    if (toDate != null && !toDate.equals(""))
      query.setParameter("todate", toDate);

    materialtrxList = query.list();

    ///////////////////////
    // // -----------------getting sum bakara--------------

    String bakrasum = "select sum(e.pAPERNumberOfRoller) from MaterialMgmtShipmentInOut e where e.client=:client"
        + " and e.movementType IN ('C-', 'C+') and e.logistic = 'N' and e.documentType.return='N'";

    if (currentBusinessPartner != null && !currentBusinessPartner.equals("")) {
      bakrasum = bakrasum + " and  e.businessPartner =:partnerid  ";
    }

    if (fromDate != null && !fromDate.equals(""))
      bakrasum = bakrasum + " and e.movementDate >=:fromdate ";

    if (toDate != null && !toDate.equals(""))
      bakrasum = bakrasum + " and e.movementDate <=:todate ";

    Query bakrasumquery = OBDal.getInstance().getSession().createQuery(bakrasum);
    bakrasumquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (currentBusinessPartner != null && !currentBusinessPartner.equals(""))
      bakrasumquery.setParameter("partnerid", currentBusinessPartner);

    if (fromDate != null && !fromDate.equals(""))
      bakrasumquery.setParameter("fromdate", fromDate);

    if (toDate != null && !toDate.equals(""))
      bakrasumquery.setParameter("todate", toDate);

    Long currentNum1 = (Long) bakrasumquery.list().get(0);
    List<Object[]> returnedList1 = bakrasumquery.list();
    // ------------------------------------------

    // // // -----------------getting sum weight--------------
    String weigthsum = "select sum(e.pAPERTotalShipmentWeight) from MaterialMgmtShipmentInOut e where e.client=:client"
        + " and e.movementType IN ('C-', 'C+') and e.logistic = 'N' and e.documentType.return='N'";

    if (currentBusinessPartner != null && !currentBusinessPartner.equals("")) {
      weigthsum = weigthsum + " and  e.businessPartner =:partnerid  ";
    }

    if (fromDate != null && !fromDate.equals(""))
      weigthsum = weigthsum + " and e.movementDate >=:fromdate ";

    if (toDate != null && !toDate.equals(""))
      weigthsum = weigthsum + " and e.movementDate <=:todate ";

    Query weigthsumquery = OBDal.getInstance().getSession().createQuery(weigthsum);
    weigthsumquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (currentBusinessPartner != null && !currentBusinessPartner.equals(""))
      weigthsumquery.setParameter("partnerid", currentBusinessPartner);

    if (fromDate != null && !fromDate.equals(""))
      weigthsumquery.setParameter("fromdate", fromDate);

    if (toDate != null && !toDate.equals(""))
      weigthsumquery.setParameter("todate", toDate);

    BigDecimal currentNum2 = (BigDecimal) weigthsumquery.list().get(0);
    List<Object[]> returnedList2 = weigthsumquery.list();
    ///////////

    // // // ------------------------------------------

    for (ShipmentInOut currenttransaction : materialtrxList) {
      // String a = null;
      List<ShipmentInOutLine> shipmentDetailList = new ArrayList<ShipmentInOutLine>();
      OBCriteria<ShipmentInOutLine> shipmentCrieria = OBDal.getInstance()
          .createCriteria(ShipmentInOutLine.class);
      shipmentCrieria
          .add(Restrictions.eq(ShipmentInOutLine.PROPERTY_SHIPMENTRECEIPT, currenttransaction));
      // shipmentCrieria.add(Restrictions.eq(ShipmentInOutLine.PROPERTY_SHIPMENTRECEIPT,
      // currenttransaction));

      shipmentDetailList = shipmentCrieria.list();

      for (ShipmentInOutLine cuLine : currenttransaction.getMaterialMgmtShipmentInOutLineList()) {
        // if(currenttransaction.getDocumentNo())

        MSalesReport_Entity entity = new MSalesReport_Entity();

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
        entity.setBno(new BigDecimal(currentNum1));
        entity.setWno(currentNum2);
        entityList.add(entity);
      }

      // -----------------------------------------------------

      // entity.setBakarweigth(currentNum2);
      // // to set bakranumber
      //
      // entity.setTotbakranum(new BigDecimal(currentNum1));

    }

    // to set bakraweigth

    //

    entityArray = new MSalesReport_Entity[entityList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }
}
