package com.ibox.paper.invevtorydeatil.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBDal;
import org.openbravo.model.common.enterprise.Locator;
import org.openbravo.model.materialmgmt.onhandquantity.StorageDetail;

import com.ibox.paper.invevtorydeatil.entity.InventoryDetail_Entity;

public class InventoryDetail_Model {

  public InventoryDetail_Entity[] get_report_data(String invD) {

    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    List<InventoryDetail_Entity> entityList = new ArrayList<InventoryDetail_Entity>();

    InventoryDetail_Entity[] entityArray = null;
    Locator cuu = null;

    // Date fromDate = null;
    // Date toDate = null;

    SimpleDateFormat sDate = new SimpleDateFormat("MM");
    Date s = null;

    if (invD != null && !invD.equals("")) {
      cuu = OBDal.getInstance().get(Locator.class, invD);
    }
    // try {
    // s = sDate.parse(dateto);
    // } catch (ParseException e1) {
    // // TODO Auto-generated catch block
    // e1.printStackTrace();
    // }
    // if (datefrom != null && !datefrom.equals("")) {
    // try {
    // fromDate = formate.parse(datefrom);
    // } catch (ParseException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    //
    // if (dateto != null && !dateto.equals("")) {
    // try {
    // toDate = formate.parse(dateto);
    // } catch (ParseException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }

    List<StorageDetail> StorageList = new ArrayList<StorageDetail>();

    String hql = "SELECT h from MaterialMgmtStorageDetail h where h.client=:client and  h.quantityOnHand<>0";

    if (cuu != null && !cuu.equals(""))
      hql = hql + " and  h.storageBin.id =:invD ";

    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (cuu != null && !cuu.equals(""))
      query.setParameter("invD", cuu.getId());

    StorageList = query.list();

    // // -----------------getting sum bakara--------------

    String bakrasum = "SELECT count(h.pAPERJumboCode) from MaterialMgmtStorageDetail h where h.client=:client and  h.quantityOnHand<>0";

    if (cuu != null && !cuu.equals(""))
      bakrasum = bakrasum + " and  h.storageBin.id =:invD ";

    Query bakrasumquery = OBDal.getInstance().getSession().createQuery(bakrasum);
    bakrasumquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (cuu != null && !cuu.equals(""))
      bakrasumquery.setParameter("invD", cuu.getId());

    Long currentNum1 = (Long) bakrasumquery.list().get(0);

    ///////////////////////////

    // // // -----------------getting sum bakara--------------

    String bakrawi = "SELECT sum(h.pAPERBakaraWeight) from MaterialMgmtStorageDetail h where h.client=:client and h.quantityOnHand<>0";

    if (cuu != null && !cuu.equals(""))
      bakrawi = bakrawi + " and h.storageBin.id =:invD ";

    Query bakrawiquery = OBDal.getInstance().getSession().createQuery(bakrawi);
    bakrawiquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (cuu != null && !cuu.equals(""))
      bakrawiquery.setParameter("invD", cuu.getId());

    BigDecimal currentNum2 = (BigDecimal) bakrawiquery.list().get(0);
    // String bakrasum = "select count(e.pAPERJumboCode) from MaterialMgmtStorageDetail e where
    // e.creationDate >=:fromdate and e.creationDate <=:todate "
    // + "and e.client=:client";
    //
    // Query bakrasumquery = OBDal.getInstance().getSession().createQuery(bakrasum);
    // bakrasumquery.setParameter("client", OBContext.getOBContext().getCurrentClient());
    //
    // if (invD != null)
    // bakrasumquery.setParameter("invD", invD);
    //
    // // if (fromDate != null)
    // // bakrasumquery.setParameter("fromdate", fromDate);
    // //
    // // if (toDate != null)
    // // bakrasumquery.setParameter("todate", toDate);
    //
    // Long currentNum1 = (Long) bakrasumquery.list().get(0);

    InventoryDetail_Entity entity = null;

    for (StorageDetail currentStorageDetail : StorageList) {
      entity = new InventoryDetail_Entity();
      if (currentStorageDetail.getPAPERJumboCode() != null
          && !currentStorageDetail.getPAPERJumboCode().equals("")) {
        entity.setBakaracode(currentStorageDetail.getPAPERJumboCode());

      } else {
        entity.setBakaracode(null);
      }
      // // if (currentStorageDetail.getPAPERProductType() != null
      // // && !currentStorageDetail.getPAPERProductType().equals("")) {
      // entity.setProducttype(currentStorageDetail.getPAPERProductType());
      //
      // // } else {
      // // entity.setProducttype(null);
      // // }
      //
      if (currentStorageDetail.getPAPERProductType().equals("3")) {
        entity.setProducttype("Core Board");
      }
      if (currentStorageDetail.getPAPERProductType().equals("2")) {
        entity.setProducttype("Meduim Fluting");
      }
      if (currentStorageDetail.getPAPERProductType().equals("1")) {
        entity.setProducttype("Manilla");
      }
      if (currentStorageDetail.getPAPERProductType().equals("4")) {
        entity.setProducttype("Test Liner");
      }
      if (currentStorageDetail.getPAPERProductType().equals("5")) {
        entity.setProducttype("special Core Board");
      }
      if (currentStorageDetail.getPAPERProductType() == null
          && currentStorageDetail.getPAPERProductType().equals("")) {
        entity.setProducttype(null);
      }

      if (currentStorageDetail.getPAPERPaperClient() != null
          && !currentStorageDetail.getPAPERPaperClient().equals("")) {
        entity.setBussinesspartnercode(currentStorageDetail.getPAPERPaperClient().getName());

      } else {
        entity.setBussinesspartnercode(null);
      }
      //
      // if (currentStorageDetail.getPAPERGsm() != null
      // && !currentStorageDetail.getPAPERGsm().equals("")) {
      entity.setGsm(new BigDecimal(currentStorageDetail.getPAPERGsm()));
      //
      // } else {
      // entity.setGsm(null);
      // }
      //
      // if (currentStorageDetail.getPAPERBakaraWidth() != null
      // && !currentStorageDetail.getPAPERBakaraWidth().equals("")) {
      entity.setWidth(currentStorageDetail.getPAPERBakaraWidth());

      // } else {
      // entity.setWidth(null);
      // }

      entity.setBakarweigth(currentStorageDetail.getPAPERBakaraWeight());
      entity.setQualitydegree(currentStorageDetail.getPAPERQualityDegree().getDegree());

      if (currentStorageDetail.getPAPERQualityDegree().getDegree().equals("1- درجة اولي")) {
        entity.setFirstresonfars(null);
        entity.setSecondresonfars(null);
      }

      if ((currentStorageDetail.getPAPERQualityDegree().getDegree().equals("2- درجة ثانيه")
          || currentStorageDetail.getPAPERQualityDegree().getDegree().equals("3- درجة ثالثه")
          || currentStorageDetail.getPAPERQualityDegree().getDegree().equals("4- مخلفات"))) {
        if ((currentStorageDetail.getPaperFirstrefuse() != null
            && currentStorageDetail.getPaperSecondrefuse() != null)) {
          entity.setFirstresonfars(currentStorageDetail.getPaperFirstrefuse().getReason());
          entity.setSecondresonfars(currentStorageDetail.getPaperSecondrefuse().getReason());
        } else if ((currentStorageDetail.getPaperFirstrefuse() != null
            && currentStorageDetail.getPaperSecondrefuse() == null)) {

          entity.setFirstresonfars(currentStorageDetail.getPaperFirstrefuse().getReason());
          entity.setSecondresonfars(null);
        } else {
          entity.setFirstresonfars(null);
          entity.setSecondresonfars(null);
        }
      }
      entity.setQotr(currentStorageDetail.getPAPERBakkaraQotr().toString());
      if (currentStorageDetail.getPAPERPaperMachine() != null
          && !currentStorageDetail.getPAPERPaperMachine().equals("")) {
        entity.setMachine(currentStorageDetail.getPAPERPaperMachine().getMashineName());

      } else {
        entity.setMachine(null);
      }

      // entity.setNotes(currentStorageDetail.getPaperNotes());
      //
      // // entity.setFromdate(fromDate.toString());
      // // entity.setTodate(toDate.toString());
      entity.setBno(new BigDecimal(currentNum1));
      entity.setWno(currentNum2);
      //
      // entity.setProdate(currentStorageDetail.getCreationDate().toString());
      entityList.add(entity);
    }
    // ----------------------

    // to set bakraweigth

    entityArray = new InventoryDetail_Entity[entityList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }
}