package com.ibox.paper.salesbrief.model;

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
import org.openbravo.model.materialmgmt.transaction.ShipmentInOutLine;

import com.ibox.paper.data.PaperMachine;
import com.ibox.paper.data.QualityDegree;
import com.ibox.paper.salesbrief.entity.SalesBrief_Entity;

public class SalesBrief_Model {

  public SalesBrief_Entity[] get_report_data(String inpDateFrom, String inmachine,
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
    if (inpDateFrom != null && !inpDateFrom.equals("")) {
      try {
        fromDate = formate.parse(inpDateFrom);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    if (inpDateTo != null && !inpDateTo.equals("")) {

      try {
        toDate = formate.parse(inpDateTo);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
    List<SalesBrief_Entity> entityList = new ArrayList<SalesBrief_Entity>();

    SalesBrief_Entity[] entityArray = null;
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
    List<ShipmentInOutLine> StorageList = new ArrayList<ShipmentInOutLine>();

    String hql = "SELECT h from paper_quality_degree h where h.client=:client ";

    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());

    List<QualityDegree> sobjectList = query.list();

    ///////////////////////////

    /// sum by degree

    SalesBrief_Entity entity = null;
    for (org.openbravo.model.ad.domain.List rList : aList) {
      System.out.println(rList.getName());

      entity = new SalesBrief_Entity();
      entity.setLproducttype(rList.getName());
      entity.setFromdate(inpDateFrom);
      entity.setTodate(inpDateTo);
      entity.setProducttype(rList.getName());

      if (inmachine != null && !inmachine.equals("")) {
        entity.setMachine(pMachine.getMashineName());

      } else {
        entity.setMachine(null);
      }
      String pType = rList.getSearchKey();

      for (QualityDegree sl : sobjectList) {

        String bdegreeasum = "SELECT count(h.pAPERJumboCode),sum(h.paperBakawieght) from MaterialMgmtShipmentInOutLine h where  ";
        bdegreeasum = bdegreeasum + "  h.paperQualityDegree =:sl";
        /* if (inpproducttype != null && !inpproducttype.equals("")) */
        bdegreeasum = bdegreeasum + " and h.pAPERProductType =:pType ";
        if (pMachine != null && !pMachine.equals(""))
          bdegreeasum = bdegreeasum + " and h.paperMachine =:inmachine ";
        if (inpDateFrom != null && !inpDateFrom.equals(""))
          bdegreeasum = bdegreeasum + " and h.shipmentReceipt.movementDate >=:fromDate ";
        if (inpDateTo != null && !inpDateTo.equals(""))
          bdegreeasum = bdegreeasum + " and h.shipmentReceipt.movementDate <:toDate ";
        /* if (inpdegree != null && !inpdegree.equals("")) */
        bdegreeasum = bdegreeasum
            + "and h.shipmentReceipt.id IN (select e.id  from MaterialMgmtShipmentInOut e where  e.movementType IN ('C-', 'C+') and e.logistic = 'N' and e.documentType.return=false)";
        Query bakraDsumquery = OBDal.getInstance().getSession().createQuery(bdegreeasum);

        if (pMachine != null && !pMachine.equals(""))
          bakraDsumquery.setParameter("inmachine", pMachine);
        if (inpDateFrom != null && !inpDateFrom.equals(""))
          bakraDsumquery.setParameter("fromDate", fromDate);
        if (inpDateTo != null && !inpDateTo.equals(""))
          bakraDsumquery.setParameter("toDate", toDate);

        bakraDsumquery.setParameter("sl", sl);

        bakraDsumquery.setParameter("pType", pType);
        List<Object[]> noOfBaka = bakraDsumquery.list();

        for (Object[] s2 : noOfBaka) {

          if (s2[1] != null) {
            if (sl.getCommercialName().contains("DEG01")) {
              entity.setNodeg1(new BigDecimal(s2[0].toString()));
              entity.setWodeg1(new BigDecimal(s2[1].toString()));
            }
            if (sl.getCommercialName().equals("DEG02")) {
              entity.setWodeg2(new BigDecimal(s2[1].toString()));
              entity.setNodeg2(new BigDecimal(s2[0].toString()));
            }
            if (sl.getCommercialName().equals("DEG03")) {
              entity.setNodeg3(new BigDecimal(s2[0].toString()));
              entity.setWodeg3(new BigDecimal(s2[1].toString()));
            }
            if (sl.getCommercialName().equals("DEG04")) {
              entity.setNodeg4(new BigDecimal(s2[0].toString()));
              entity.setWodeg4(new BigDecimal(s2[1].toString()));
            }
          } else {
            if (sl.getCommercialName().equals("DEG01")) {
              entity.setNodeg1(BigDecimal.ZERO);
              entity.setWodeg1(BigDecimal.ZERO);
            }
            if (sl.getCommercialName().equals("DEG02")) {
              entity.setNodeg2(BigDecimal.ZERO);
              entity.setWodeg2(BigDecimal.ZERO);
            }
            if (sl.getCommercialName().equals("DEG03")) {
              entity.setNodeg3(BigDecimal.ZERO);
              entity.setWodeg3(BigDecimal.ZERO);
            }
            if (sl.getCommercialName().equals("DEG04")) {
              entity.setNodeg4(BigDecimal.ZERO);
              entity.setWodeg4(BigDecimal.ZERO);
            }
          }
        }

      }
      entityList.add(entity);
    }
    entityArray = new SalesBrief_Entity[entityList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }
}