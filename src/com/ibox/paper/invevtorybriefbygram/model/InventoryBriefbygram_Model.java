package com.ibox.paper.invevtorybriefbygram.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.query.Query;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBDal;
import org.openbravo.model.common.enterprise.Locator;
import org.openbravo.model.materialmgmt.onhandquantity.StorageDetail;

import com.ibox.paper.data.PaperMachine;
import com.ibox.paper.data.QualityDegree;
import com.ibox.paper.invevtorybriefbygram.entity.InventoryBriefbygram_Entity;

public class InventoryBriefbygram_Model {

  public InventoryBriefbygram_Entity[] get_report_data(String invD, String inmachine,
      String inpproducttype, String inpdegree) {

    // public InventoryBrief_Entity[] get_report_data(String invD, String inmachine,
    // String inpproducttype) {

    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    List<InventoryBriefbygram_Entity> entityListr = new ArrayList<InventoryBriefbygram_Entity>();

    Set<InventoryBriefbygram_Entity> set1 = new HashSet<>(entityListr);
    List<InventoryBriefbygram_Entity> entityList = new ArrayList<>(set1);
    List<InventoryBriefbygram_Entity> entityyList = new ArrayList<>(set1);
    InventoryBriefbygram_Entity[] entityArray = null;
    InventoryBriefbygram_Entity[] entityArrayy = null;
    Locator cuu = null;
    PaperMachine pMachine = null;
    QualityDegree pQuality = null;
    Long currentNum3 = null;
    Long currentNum4 = null;
    Long currentNum5 = null;
    Long currentNum6 = null;

    List<String> listtest = new ArrayList<>();
    listtest.add("0");
    List<String> totlist = new ArrayList<>();
    totlist.add("0");
    List<String> gsmlist = new ArrayList<>();
    gsmlist.add("0");
    List<String> widthlist = new ArrayList<>();
    widthlist.add("0");

    SimpleDateFormat sDate = new SimpleDateFormat("MM");
    Date s = null;

    if (invD != null && !invD.equals("")) {
      cuu = OBDal.getInstance().get(Locator.class, invD);
    }

    if (inmachine != null && !inmachine.equals("")) {
      pMachine = OBDal.getInstance().get(PaperMachine.class, inmachine);
    }

    if (inpdegree != null && !inpdegree.equals("")) {
      pQuality = OBDal.getInstance().get(QualityDegree.class, inpdegree);
    }

    List<StorageDetail> StorageList = new ArrayList<StorageDetail>();
    //

    //
    String hql = "SELECT  DISTINCT (h.pAPERGsm) from MaterialMgmtStorageDetail h where h.client=:client and  h.quantityOnHand<>0";

    if (cuu != null && !cuu.equals(""))
      hql = hql + " and h.storageBin.id =:invD ";
    if (pMachine != null && !pMachine.equals(""))
      hql = hql + " and h.pAPERPaperMachine =:inmachine ";
    if (inpproducttype != null && !inpproducttype.equals(""))
      hql = hql + " and h.pAPERProductType =:inpproducttype ";

    if (pQuality != null && !pQuality.equals(""))
      hql = hql + " and h.pAPERQualityDegree =:inpdegree ";

    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (cuu != null && !cuu.equals(""))
      query.setParameter("invD", cuu.getId());

    if (pMachine != null && !pMachine.equals(""))
      query.setParameter("inmachine", pMachine);

    if (inpproducttype != null && !inpproducttype.equals(""))
      query.setParameter("inpproducttype", inpproducttype);
    if (pQuality != null && !pQuality.equals(""))
      query.setParameter("inpdegree", pQuality);
    List<Object> gsom = query.list();

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

    //////

    // // // -----------------getting weigth bakara--------------

    String bakrawi = "SELECT sum(h.pAPERBakaraWeight) from MaterialMgmtStorageDetail h where h.client=:client and h.quantityOnHand<>0";

    if (cuu != null && !cuu.equals(""))
      bakrawi = bakrawi + " and h.storageBin.id =:invD ";
    if (pMachine != null && !pMachine.equals(""))
      bakrawi = bakrawi + " and  h.pAPERPaperMachine =:inmachine ";

    if (inpproducttype != null && !inpproducttype.equals(""))
      bakrawi = bakrawi + " and h.pAPERProductType =:inpproducttype ";

    if (pQuality != null && !pQuality.equals(""))
      bakrawi = bakrawi + " and h.pAPERQualityDegree =:inpdegree ";

    Query bakrawiquery = OBDal.getInstance().getSession().createQuery(bakrawi);
    bakrawiquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (cuu != null && !cuu.equals(""))
      bakrawiquery.setParameter("invD", cuu.getId());

    if (pMachine != null && !pMachine.equals(""))
      bakrawiquery.setParameter("inmachine", pMachine);

    if (inpproducttype != null && !inpproducttype.equals(""))
      bakrawiquery.setParameter("inpproducttype", inpproducttype);

    if (pQuality != null && !pQuality.equals(""))
      bakrawiquery.setParameter("inpdegree", pQuality);

    BigDecimal currentNum2 = (BigDecimal) bakrawiquery.list().get(0);

    ///////////////////////////
    List<QualityDegree> degreeList = new ArrayList<QualityDegree>();
    InventoryBriefbygram_Entity entity = new InventoryBriefbygram_Entity();
    ArrayList<BigDecimal> newList = new ArrayList<BigDecimal>();
    Integer gsmobject;
    for (Object currentNumelio : gsom) {
      // for (Object[] currentNumelio : currentNume) {
      gsmobject = new Integer(currentNumelio.toString());
      entity = new InventoryBriefbygram_Entity();
      entity.setGsm(new BigDecimal(gsmobject));
      entity.setwarehouse(cuu.getSearchKey());
      if (inpproducttype != null && !inpproducttype.equals("")) {
        if (inpproducttype.equals("3")) {
          entity.setProducttype("Core Board");
        }
        if (inpproducttype.equals("2")) {
          entity.setProducttype("Meduim Fluting");
        }
        if (inpproducttype.equals("1")) {
          entity.setProducttype("Manilla");
        }
        if (inpproducttype.equals("4")) {
          entity.setProducttype("Test Liner");
        }
        if (inpproducttype.equals("5")) {
          entity.setProducttype("special Core Board");
        }

      } else {
        entity.setProducttype(null);
      }

      if (inpdegree != null && !inpdegree.equals("")) {
        entity.setQualitydegree(pQuality.getDegree());

      } else {
        entity.setQualitydegree(null);
      }

      if (inmachine != null && !inmachine.equals("")) {
        entity.setMachine(pMachine.getMashineName());

      } else {
        entity.setMachine(null);
      }
      if (currentNum1 == 0) {
        entity.setBno(BigDecimal.ZERO);

      } else {
        entity.setBno(new BigDecimal(currentNum1));
      }

      if (currentNum2 == null) {
        entity.setWno(BigDecimal.ZERO);
      } else {
        entity.setWno(currentNum2);
      }

      entityList.add(entity);
      //
      //

    }

    entityArray = new InventoryBriefbygram_Entity[entityList.size()];

    entityList.toArray(entityArray);
    // return the list
    return entityArray;

  }
}