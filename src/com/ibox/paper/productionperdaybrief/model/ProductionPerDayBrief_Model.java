package com.ibox.paper.productionperdaybrief.model;

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

import com.ibox.paper.data.PaperMachine;
import com.ibox.paper.data.PaperShift;
import com.ibox.paper.data.ProductionQuality;
import com.ibox.paper.data.QualityDegree;
import com.ibox.paper.productionperdaybrief.entity.ProductionPerDayBrief_Entity;

public class ProductionPerDayBrief_Model {

  public ProductionPerDayBrief_Entity[] get_report_data(String datefrom, String dateto,
      String inmachine, String inpshift) {

    // production
    List<ProductionPerDayBrief_Entity> entityList = new ArrayList<ProductionPerDayBrief_Entity>();

    ProductionPerDayBrief_Entity[] entityArray = null;

    PaperMachine pMachine = null;
    // QualityDegree pQuality = null;
    PaperShift pShift = null;
    ProductionQuality productioQuality = null;
    Long currentNum3 = null;
    Long currentNum4 = null;
    Long currentNum5 = null;
    Long currentNum6 = null;

    Date fromDate = null;
    Date toDate = null;

    SimpleDateFormat sDate = new SimpleDateFormat("MM");
    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");
    org.openbravo.model.ad.domain.List pList = null;

    List<org.openbravo.model.ad.domain.List> aList = new ArrayList<org.openbravo.model.ad.domain.List>();
    String lhQl = "SELECT h from ADList h where ";
    lhQl = lhQl + " h.reference.id='956081BC8F4C4F7EA593059FA631A754' ";

    Query lquery = OBDal.getInstance().getSession().createQuery(lhQl);

    aList = lquery.list();
    ///
    List<QualityDegree> DegList = new ArrayList<QualityDegree>();
    String qql = "SELECT h from paper_quality_degree h where h.client=:client ";

    Query qquery = OBDal.getInstance().getSession().createQuery(qql);
    qquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    DegList = qquery.list();
    ////
    Date s = null;
    try {
      s = sDate.parse(dateto);
    } catch (ParseException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
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
    if (inmachine != null && !inmachine.equals("")) {
      pMachine = OBDal.getInstance().get(PaperMachine.class, inmachine);
    }

    if (inpshift != null && !inpshift.equals("")) {
      pShift = OBDal.getInstance().get(PaperShift.class, inpshift);
    }

    List<ProductionQuality> ProductList = new ArrayList<ProductionQuality>();

    String hql = "select h from paper_quality h where h.client=:client and h.sosataus IN('1' , '2')";

    if (fromDate != null)
      hql = hql + " and  h.productiondate >=:fromdate ";

    if (toDate != null)
      hql = hql + " and  h.productiondate <=:todate ";

    if (pMachine != null && !pMachine.equals(""))
      hql = hql + " and h.paperMachine =:inmachine ";

    if (pShift != null && !pShift.equals(""))
      hql = hql + " and h.proshift =:inpshift ";

    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());
    if (fromDate != null)
      query.setParameter("fromdate", fromDate);

    if (toDate != null)
      query.setParameter("todate", toDate);

    if (pMachine != null && !pMachine.equals(""))
      query.setParameter("inmachine", pMachine);

    if (pShift != null && !pShift.equals(""))
      query.setParameter("inpshift", pShift);

    ProductList = query.list();

    // // -----------------getting sum bakara--------------

    String bakrasum = "select count(e.bakaracode) from paper_quality e   where e.client=:client and e.sosataus IN('1' , '2')";

    if (fromDate != null)
      bakrasum = bakrasum + " and  e.productiondate >=:fromdate ";

    if (toDate != null)
      bakrasum = bakrasum + " and  e.productiondate <=:todate ";

    if (pMachine != null && !pMachine.equals(""))
      bakrasum = bakrasum + " and  e.paperMachine =:inmachine ";

    if (pShift != null && !pShift.equals(""))
      bakrasum = bakrasum + " and e.proshift =:inpshift ";

    Query bakrasumquery = OBDal.getInstance().getSession().createQuery(bakrasum);
    bakrasumquery.setParameter("client", OBContext.getOBContext().getCurrentClient());
    if (fromDate != null)
      bakrasumquery.setParameter("fromdate", fromDate);

    if (toDate != null)
      bakrasumquery.setParameter("todate", toDate);

    if (pMachine != null && !pMachine.equals(""))
      bakrasumquery.setParameter("inmachine", pMachine);

    if (pShift != null && !pShift.equals(""))
      bakrasumquery.setParameter("inpshift", pShift);

    Long currentNum1 = (Long) bakrasumquery.list().get(0);

    ///////////////////////////
    List<QualityDegree> degreeList = new ArrayList<QualityDegree>();

    // // // -----------------getting weigth bakara--------------

    String bakrawi = "SELECT sum(h.bakaraweight) from paper_quality h where h.client=:client and h.sosataus IN('1' , '2')";

    if (fromDate != null)
      bakrawi = bakrawi + " and  h.productiondate >=:fromdate ";
    if (toDate != null)
      bakrawi = bakrawi + " and  h.productiondate <=:todate ";
    if (pMachine != null && !pMachine.equals(""))
      bakrawi = bakrawi + " and  h.paperMachine =:inmachine ";

    if (pShift != null && !pShift.equals(""))
      bakrawi = bakrawi + " and h.proshift =:inpshift ";

    Query bakrawiquery = OBDal.getInstance().getSession().createQuery(bakrawi);
    bakrawiquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (fromDate != null)
      bakrawiquery.setParameter("fromdate", fromDate);
    if (toDate != null)
      bakrawiquery.setParameter("todate", toDate);

    if (pMachine != null && !pMachine.equals(""))
      bakrawiquery.setParameter("inmachine", pMachine);

    if (pShift != null && !pShift.equals(""))
      bakrawiquery.setParameter("inpshift", pShift);

    Long currentNum2 = (Long) bakrawiquery.list().get(0);

    //////////////////////////////////////////////////////////////

    ProductionPerDayBrief_Entity entity = null;
    for (org.openbravo.model.ad.domain.List rList : aList) {
      entity = new ProductionPerDayBrief_Entity();
      // // -----------------getting sum bakara--------------
      entity.setRoductType(rList.getName());
      String plist = rList.getSearchKey();
      ////
      String noOf = "select count(e.bakaracode),sum(e.bakaraweight) from paper_quality e   where  e.sosataus IN('1' , '2')";

      if (fromDate != null)
        noOf = noOf + " and  e.productiondate >=:fromdate ";

      if (toDate != null)
        noOf = noOf + " and  e.productiondate <=:todate ";

      if (pMachine != null && !pMachine.equals(""))
        noOf = noOf + " and  e.paperMachine =:inmachine ";

      if (pShift != null && !pShift.equals(""))
        noOf = noOf + " and e.proshift =:inpshift ";
      noOf = noOf + " and e.productType =:plist ";
      for (QualityDegree doJectDeg : DegList) {
        noOf = noOf + " and e.paperQualityDegree =:doJectDeg ";
        Query quernoOf = OBDal.getInstance().getSession().createQuery(noOf);

        if (fromDate != null)
          quernoOf.setParameter("fromdate", fromDate);

        if (toDate != null)
          quernoOf.setParameter("todate", toDate);

        if (pMachine != null && !pMachine.equals(""))
          quernoOf.setParameter("inmachine", pMachine);

        if (pShift != null && !pShift.equals(""))
          quernoOf.setParameter("inpshift", pShift);
        quernoOf.setParameter("plist", plist);
        quernoOf.setParameter("doJectDeg", doJectDeg);
        List<Object[]> nowo = quernoOf.list();

        for (Object[] noofW : nowo) {

          if (noofW[1] != null) {
            System.out.println(nowo.get(0).toString());
            if (doJectDeg.getCommercialName().contains("DEG01")) {
              entity.setBnodeg1(new BigDecimal(noofW[0].toString()));
              entity.setWnodeg1(new BigDecimal(noofW[1].toString()));
            } else if (doJectDeg.getCommercialName().equals("DEG02")) {
              entity.setBnodeg2(new BigDecimal(noofW[0].toString()));
              entity.setWnodeg2(new BigDecimal(noofW[1].toString()));
            } else if (doJectDeg.getCommercialName().equals("DEG03")) {
              entity.setBnodeg3(new BigDecimal(noofW[0].toString()));
              entity.setWnodeg3(new BigDecimal(noofW[1].toString()));
            } else if (doJectDeg.getCommercialName().equals("DEG04")) {
              entity.setBnodeg4(new BigDecimal(noofW[0].toString()));
              entity.setWnodeg4(new BigDecimal(noofW[1].toString()));
            }
          } else {

            if (doJectDeg.getCommercialName().contains("DEG01")) {
              entity.setBnodeg1(BigDecimal.ZERO);
              entity.setWnodeg1(BigDecimal.ZERO);
            } else if (doJectDeg.getCommercialName().equals("DEG02")) {
              entity.setBnodeg2(BigDecimal.ZERO);
              entity.setWnodeg2(BigDecimal.ZERO);
            } else if (doJectDeg.getCommercialName().equals("DEG03")) {
              entity.setBnodeg3(BigDecimal.ZERO);
              entity.setWnodeg3(BigDecimal.ZERO);
            } else if (doJectDeg.getCommercialName().equals("DEG04")) {
              entity.setBnodeg4(BigDecimal.ZERO);
              entity.setWnodeg4(BigDecimal.ZERO);
            }

          }

        }
      }

      entity.setTodate(dateto);
      entity.setFromdate(datefrom);
      if (inpshift != null && !inpshift.equals("")) {
        entity.setShifts(pShift.getShiftname());

      } else {
        entity.setShifts(null);
      }

      if (inmachine != null && !inmachine.equals("")) {
        entity.setMachine(pMachine.getMashineName());

      } else {
        entity.setMachine(null);
      }

      entity.setBno(new BigDecimal(currentNum1));
      entity.setWno(new BigDecimal(currentNum2));

      entityList.add(entity);
    }
    entityArray = new ProductionPerDayBrief_Entity[entityList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }
}