package com.ibox.paper.invevtorybrief.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBDal;
import org.openbravo.model.common.enterprise.Locator;
import org.openbravo.model.materialmgmt.onhandquantity.StorageDetail;

import com.ibox.paper.data.PaperMachine;
import com.ibox.paper.data.QualityDegree;
import com.ibox.paper.invevtorybrief.entity.InventoryBrief_Entity;

public class InventoryBrief_Model {

  public InventoryBrief_Entity[] get_report_data(String invD, String inmachine,
      String inpproducttype, String inpdegree) {

    // public InventoryBrief_Entity[] get_report_data(String invD, String inmachine,
    // String inpproducttype) {

    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    List<InventoryBrief_Entity> entityList = new ArrayList<InventoryBrief_Entity>();

    InventoryBrief_Entity[] entityArray = null;
    Locator cuu = null;
    PaperMachine pMachine = null;
    QualityDegree pQuality = null;
    org.openbravo.model.ad.domain.List pList = null;

    if (inpproducttype != null && !inpproducttype.equals("")) {
      pList = OBDal.getInstance().get(org.openbravo.model.ad.domain.List.class, inpproducttype);
    }
    if (invD != null && !invD.equals("")) {
      cuu = OBDal.getInstance().get(Locator.class, invD);
    }

    if (inmachine != null && !inmachine.equals("")) {
      pMachine = OBDal.getInstance().get(PaperMachine.class, inmachine);
    }

    if (inpdegree != null && !inpdegree.equals("")) {
      pQuality = OBDal.getInstance().get(QualityDegree.class, inpdegree);
    }
    List<org.openbravo.model.ad.domain.List> aList = new ArrayList<org.openbravo.model.ad.domain.List>();
    String lhQl = "SELECT h from ADList h where ";
    lhQl = lhQl + " h.reference.id='956081BC8F4C4F7EA593059FA631A754' ";
    if (inpproducttype != null && !inpproducttype.equals("")) {
      lhQl = lhQl + "and  h.id =:inpproducttype ";
    }
    Query lquery = OBDal.getInstance().getSession().createQuery(lhQl);
    if (inpproducttype != null && !inpproducttype.equals("")) {
      lquery.setParameter("inpproducttype", pList.getId());
    }
    aList = lquery.list();
    for (org.openbravo.model.ad.domain.List rList : aList) {
      System.out.println(rList.getName());
    }
    List<StorageDetail> StorageList = new ArrayList<StorageDetail>();

    String hql = "SELECT h from paper_quality_degree h where h.client=:client ";

    if (pQuality != null && !pQuality.equals(""))
      hql = hql + " and h.id =:inpdegree ";

    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (pQuality != null && !pQuality.equals(""))
      query.setParameter("inpdegree", pQuality.getId());

    List<QualityDegree> sobjectList = query.list();

    // // -----------------getting sum bakara--------------

    String bakrasum = "SELECT count(h.pAPERJumboCode) from MaterialMgmtStorageDetail h where h.client=:client and  h.quantityOnHand<>0";

    if (cuu != null && !cuu.equals(""))
      bakrasum = bakrasum + " and  h.storageBin.id =:invD ";

    if (pMachine != null && !pMachine.equals(""))
      bakrasum = bakrasum + " and  h.pAPERPaperMachine =:inmachine ";

    if (inpproducttype != null && !inpproducttype.equals(""))
      bakrasum = bakrasum + " and h.pAPERProductType =:inpproducttype ";

    if (pQuality != null && !pQuality.equals(""))
      bakrasum = bakrasum + " and h.pAPERQualityDegree =:inpdegree ";

    Query bakrasumquery = OBDal.getInstance().getSession().createQuery(bakrasum);
    bakrasumquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (cuu != null && !cuu.equals(""))
      bakrasumquery.setParameter("invD", cuu.getId());

    if (pMachine != null && !pMachine.equals(""))
      bakrasumquery.setParameter("inmachine", pMachine);

    if (inpproducttype != null && !inpproducttype.equals(""))
      bakrasumquery.setParameter("inpproducttype", inpproducttype);

    if (pQuality != null && !pQuality.equals(""))
      bakrasumquery.setParameter("inpdegree", pQuality);

    Long currentNum1 = (Long) bakrasumquery.list().get(0);

    ///////////////////////////
    List<QualityDegree> degreeList = new ArrayList<QualityDegree>();
    /// sum by degree

    InventoryBrief_Entity entity = null;
    for (org.openbravo.model.ad.domain.List rList : aList) {
      System.out.println(rList.getName());

      entity = new InventoryBrief_Entity();
      entity.setLproducttype(rList.getName());

      if (invD != null && !invD.equals("")) {
        entity.setWarehouse(cuu.getSearchKey());

      } else {
        entity.setWarehouse(null);
      }
      if (inpproducttype != null && !inpproducttype.equals("")) {
        entity.setProducttype(rList.getName());

      } else {
        entity.setProducttype(null);
      }

      if (inpdegree != null && !inpdegree.equals("")) {
        entity.setQualitydegree(pQuality.getCommercialName());

      } else {
        entity.setQualitydegree(null);
      }

      if (inmachine != null && !inmachine.equals("")) {
        entity.setMachine(pMachine.getMashineName());

      } else {
        entity.setMachine(null);
      }
      String pType = rList.getSearchKey();

      for (QualityDegree sl : sobjectList) {

        String bdegreeasum = "SELECT count(h.pAPERJumboCode) from MaterialMgmtStorageDetail h where  h.quantityOnHand<>0";
        if (cuu != null && !cuu.equals(""))
          bdegreeasum = bdegreeasum + " and  h.storageBin.id =:invD ";
        if (pMachine != null && !pMachine.equals(""))
          bdegreeasum = bdegreeasum + " and  h.pAPERPaperMachine =:inmachine ";
        /* if (inpdegree != null && !inpdegree.equals("")) */
        bdegreeasum = bdegreeasum + " and h.pAPERQualityDegree =:sl";
        /* if (inpproducttype != null && !inpproducttype.equals("")) */
        bdegreeasum = bdegreeasum + " and h.pAPERProductType =:pType ";

        Query bakraDsumquery = OBDal.getInstance().getSession().createQuery(bdegreeasum);

        if (cuu != null && !cuu.equals(""))
          bakraDsumquery.setParameter("invD", cuu.getId());
        if (pMachine != null && !pMachine.equals(""))
          bakraDsumquery.setParameter("inmachine", pMachine);

        bakraDsumquery.setParameter("sl", sl);

        bakraDsumquery.setParameter("pType", pType);
        List<Object> noOfBaka = bakraDsumquery.list();
        // weigth
        String bdegrewei = "SELECT sum(h.pAPERBakaraWeight) from MaterialMgmtStorageDetail h where  h.quantityOnHand<>0";
        if (cuu != null && !cuu.equals(""))
          bdegrewei = bdegrewei + " and  h.storageBin.id =:invD ";
        if (pMachine != null && !pMachine.equals(""))
          bdegrewei = bdegrewei + " and  h.pAPERPaperMachine =:inmachine ";
        /* if (inpdegree != null && !inpdegree.equals("")) */
        bdegrewei = bdegrewei + " and h.pAPERQualityDegree =:sl";
        /* if (inpproducttype != null && !inpproducttype.equals("")) */
        bdegrewei = bdegrewei + " and h.pAPERProductType =:pType ";

        Query bakrabdegreweiquery = OBDal.getInstance().getSession().createQuery(bdegrewei);

        if (cuu != null && !cuu.equals(""))
          bakrabdegreweiquery.setParameter("invD", cuu.getId());
        if (pMachine != null && !pMachine.equals(""))
          bakrabdegreweiquery.setParameter("inmachine", pMachine);

        bakrabdegreweiquery.setParameter("sl", sl);

        bakrabdegreweiquery.setParameter("pType", pType);
        List<Object> woOfBaka = bakrabdegreweiquery.list();

        for (Object s2 : noOfBaka) {

          if (s2.toString() != null) {
            if (sl.getCommercialName().contains("DEG01"))
              entity.setNodeg1(new BigDecimal(s2.toString()));
            if (sl.getCommercialName().equals("DEG02"))
              entity.setNodeg2(new BigDecimal(s2.toString()));
            if (sl.getCommercialName().equals("DEG03"))
              entity.setNodeg3(new BigDecimal(s2.toString()));
            if (sl.getCommercialName().equals("DEG04"))
              entity.setNodeg4(new BigDecimal(s2.toString()));
          } else {
            if (sl.getCommercialName().equals("DEG01"))
              entity.setNodeg1(BigDecimal.ZERO);
            if (sl.getCommercialName().equals("DEG02"))
              entity.setNodeg2(BigDecimal.ZERO);
            if (sl.getCommercialName().equals("DEG03"))
              entity.setNodeg3(BigDecimal.ZERO);
            if (sl.getCommercialName().equals("DEG04"))
              entity.setNodeg4(BigDecimal.ZERO);
          }
        }
        for (Object sw : woOfBaka) {

          if (sw != null) {
            if (sl.getCommercialName().contains("DEG01"))
              entity.setWodeg1(new BigDecimal(sw.toString()));
            if (sl.getCommercialName().equals("DEG02"))
              entity.setWodeg2(new BigDecimal(sw.toString()));
            if (sl.getCommercialName().equals("DEG03"))
              entity.setWodeg3(new BigDecimal(sw.toString()));
            if (sl.getCommercialName().equals("DEG04"))
              entity.setWodeg4(new BigDecimal(sw.toString()));
          } else {
            if (sl.getCommercialName().equals("DEG01"))
              entity.setWodeg1(BigDecimal.ZERO);
            if (sl.getCommercialName().equals("DEG02"))
              entity.setWodeg2(BigDecimal.ZERO);
            if (sl.getCommercialName().equals("DEG03"))
              entity.setWodeg3(BigDecimal.ZERO);
            if (sl.getCommercialName().equals("DEG04"))
              entity.setWodeg4(BigDecimal.ZERO);
          }
        }

      }
      entityList.add(entity);
    }
    entityArray = new InventoryBrief_Entity[entityList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }
}