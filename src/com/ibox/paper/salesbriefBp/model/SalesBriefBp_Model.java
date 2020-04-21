package com.ibox.paper.salesbriefBp.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBDal;
import org.openbravo.erpCommon.info.Locator;
import org.openbravo.model.common.businesspartner.BusinessPartner;
import org.openbravo.model.common.enterprise.ADMonth;

import com.ibox.paper.data.PaperMachine;
import com.ibox.paper.data.QualityDegree;
import com.ibox.paper.salesbriefBp.entity.SalesBriefBp_Entity;

public class SalesBriefBp_Model {

  public SalesBriefBp_Entity[] get_report_data(String inpYears, String inmachine,
      String inpDateTo) {

    // public InventoryBrief_Entity[] get_report_data(String invD, String inmachine,
    // String inpproducttype) {

    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    Date fromDate = null;
    Date toDate = null;

    /*
     * SimpleDateFormat sDate = new SimpleDateFormat("MM"); Date s = null; try { s =
     * sDate.parse(inpDateTo); } catch (ParseException e1) { // TODO Auto-generated catch block
     * e1.printStackTrace(); }
     */
    /*
     * if (inpDateFrom != null && !inpDateFrom.equals("")) { try { fromDate =
     * formate.parse(inpDateFrom); } catch (ParseException e) { // TODO Auto-generated catch block
     * e.printStackTrace(); } }
     */
    if (inpDateTo != null && !inpDateTo.equals("")) {

      try {
        toDate = formate.parse(inpDateTo);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
    List<SalesBriefBp_Entity> entityList = new ArrayList<SalesBriefBp_Entity>();

    SalesBriefBp_Entity[] entityArray = null;
    Locator cuu = null;
    PaperMachine pMachine = null;

    if (inmachine != null && !inmachine.equals("")) {
      pMachine = OBDal.getInstance().get(PaperMachine.class, inmachine);
    }

    List<org.openbravo.model.ad.domain.List> aList = new ArrayList<org.openbravo.model.ad.domain.List>();
    String lhQl = "SELECT h from ADList h where ";
    lhQl = lhQl + " h.reference.id='956081BC8F4C4F7EA593059FA631A754' ";

    Query lquery = OBDal.getInstance().getSession().createQuery(lhQl);

    aList = lquery.list();
    for (org.openbravo.model.ad.domain.List rList : aList) {
      System.out.println(rList.getName());
    }

    // List<ShipmentInOutLine> StorageList = new ArrayList<ShipmentInOutLine>();

    String hql = "SELECT h from paper_quality_degree h where h.client=:client ";

    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());

    List<QualityDegree> sobjectList = query.list();

    ///////////////////////////
    String bpartner = "SELECT DISTINCT(h.shipmentReceipt.businessPartner) from MaterialMgmtShipmentInOutLine h where  ";

    bpartner = bpartner
        + "  h.shipmentReceipt.id IN (select e.id  from MaterialMgmtShipmentInOut e where  e.movementType IN ('C-', 'C+') and e.logistic = 'N' and e.documentType.return=false)";
    Query partnerquery = OBDal.getInstance().getSession().createQuery(bpartner);

    /// sum by degree

    List<BusinessPartner> StorageList = partnerquery.list();
    List<ADMonth> MonthList = new ArrayList<ADMonth>();
    String MhQl = "select h  from ADMonth h  ";
    // MhQl = MhQl + " where h.searchKey =:aZ";
    Query Mquery = OBDal.getInstance().getSession().createQuery(MhQl);
    // Mquery.setParameter("aZ", aZ);
    MonthList = Mquery.list();

    SalesBriefBp_Entity entity = null;

    for (BusinessPartner rList : StorageList) {
      entity = new SalesBriefBp_Entity();
      for (ADMonth MList : MonthList) {

        String mSearch = MList.getSearchKey();
        if (mSearch.equals("10") || mSearch.equals("11") || mSearch.equals("12")) {
          mSearch = MList.getSearchKey();
        } else {
          mSearch = "0" + MList.getSearchKey();
        }
        String bdegreeasum = "SELECT sum(h.paperBakawieght) from MaterialMgmtShipmentInOutLine h where  ";

        bdegreeasum = bdegreeasum + " h.shipmentReceipt.businessPartner =:rList ";
        bdegreeasum = bdegreeasum + " and TO_CHAR(h.shipmentReceipt.movementDate, 'MM') =:mSearch ";
        if (inpYears != null && !inpYears.equals(" ")) {
          bdegreeasum = bdegreeasum
              + " and TO_CHAR(h.shipmentReceipt.movementDate, 'YYYY') >=:inpYears";
        }
        bdegreeasum = bdegreeasum + " and TO_CHAR(h.shipmentReceipt.movementDate, 'YYYY') >='2019'";
        bdegreeasum = bdegreeasum + " and TO_CHAR(h.shipmentReceipt.movementDate, 'YYYY') !='2201'";
        bdegreeasum = bdegreeasum
            + "and h.shipmentReceipt.id IN (select e.id  from MaterialMgmtShipmentInOut e where  e.movementType IN ('C-', 'C+') and e.logistic = 'N' and e.documentType.return=false)";
        Query bakraDsumquery = OBDal.getInstance().getSession().createQuery(bdegreeasum);
        bakraDsumquery.setParameter("mSearch", mSearch);
        if (inpYears != null && !inpYears.equals(" ")) {
          bakraDsumquery.setParameter("inpYears", inpYears);
        }
        bakraDsumquery.setParameter("rList", rList);
        List<Object> noOfBaka = bakraDsumquery.list();

        for (Object s2 : noOfBaka) {
          if (s2 != null) {
            entity.setBusinessPartner(rList.getName());
            if (mSearch.equals("01")) {
              entity.setNodeg1(new BigDecimal(s2.toString()));
            } else if (mSearch.equals("02")) {
              entity.setNodeg2(new BigDecimal(s2.toString()));
            } else if (mSearch.equals("03")) {
              entity.setNodeg3(new BigDecimal(s2.toString()));
            } else if (mSearch.equals("04")) {
              entity.setNodeg4(new BigDecimal(s2.toString()));
            } else if (mSearch.equals("05")) {
              entity.setNodeg5(new BigDecimal(s2.toString()));
            } else if (mSearch.equals("06")) {
              entity.setNodeg6(new BigDecimal(s2.toString()));
            } else if (mSearch.equals("07")) {
              entity.setNodeg7(new BigDecimal(s2.toString()));
            } else if (mSearch.equals("08")) {
              entity.setNodeg8(new BigDecimal(s2.toString()));
            } else if (mSearch.equals("09")) {
              entity.setNodeg9(new BigDecimal(s2.toString()));
            } else if (mSearch.equals("10")) {
              entity.setNodeg10(new BigDecimal(s2.toString()));
            } else if (mSearch.equals("11")) {
              entity.setNodeg11(new BigDecimal(s2.toString()));
            } else if (mSearch.equals("12")) {
              entity.setNodeg12(new BigDecimal(s2.toString()));
            }

          } else {
            break;
          }
        }

      }
      if (entity.getBusinessPartner() != null) {
        entityList.add(entity);

      }
    }

    entityArray = new SalesBriefBp_Entity[entityList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }
}