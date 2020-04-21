package com.ibox.paper.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Expression;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;
import org.openbravo.model.materialmgmt.onhandquantity.StorageDetail;

import com.ibox.paper.data.PaperMachine;
import com.ibox.paper.entity.MonthlyProductionReport_Entity;

public class MonthlyProductionReport_Model {

  public MonthlyProductionReport_Entity[] get_report_data(String machineId, String datefrom,
      String dateto) {

    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    List<MonthlyProductionReport_Entity> entityList = new ArrayList<MonthlyProductionReport_Entity>();
    MonthlyProductionReport_Entity[] entityArray = null;

    Date fromDate = null;
    Date toDate = null;

    if (datefrom != null && !datefrom.equals("")) {
      try {
        fromDate = formate.parse(datefrom);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if (dateto != null && !dateto.equals("")) {
      try {
        toDate = formate.parse(dateto);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    List<StorageDetail> fullRollList = new ArrayList<StorageDetail>();
    // List<StorageDetail> first_deg_RollList = new ArrayList<StorageDetail>();
    // List<StorageDetail> second_deg_RollList = new ArrayList<StorageDetail>();
    // List<StorageDetail> third_deg_RollList = new ArrayList<StorageDetail>();
    if (machineId != null && !machineId.equals("")) {

      PaperMachine currentMachine = OBDal.getInstance().get(PaperMachine.class, machineId);

      final OBCriteria<StorageDetail> obc = OBDal.getInstance().createCriteria(StorageDetail.class);
      obc.add(Expression.eq(StorageDetail.PROPERTY_PAPERPAPERMACHINE, currentMachine));
      obc.add(Expression.ge(StorageDetail.PROPERTY_CREATIONDATE, fromDate));
      // obc.add(Expression.le(StorageDetail.PROPERTY_CREATIONDATE, toDate));
      fullRollList = obc.list();

      // first_deg_RollList = obc.setProjection(
      // Projections.projectionList().add(Projections.groupProperty("em_paper_quality_degree")))
      // .list();

      // obc.add(Expression.eq(StorageDetail.PROPERTY_PAPERPAPERMACHINE, currentMachine));

    } else {
      OBCriteria<StorageDetail> rollCriteria = OBDal.getInstance()
          .createCriteria(StorageDetail.class);

      fullRollList = rollCriteria.list();

    }

    for (StorageDetail currentRoll : fullRollList) {

      // if (currentRoll.getPAPERQualityDegree().getDegree().equals("درجة اولي")) {
      // first_deg_RollList.add(currentRoll);
      // } else if (currentRoll.getPAPERQualityDegree().getDegree().equals("درجة ثانيه")) {
      //
      // second_deg_RollList.add(currentRoll);
      // } else if (currentRoll.getPAPERQualityDegree().getDegree().equals("درجة ثالثه")) {
      // third_deg_RollList.add(currentRoll);
      // }

      MonthlyProductionReport_Entity entity = new MonthlyProductionReport_Entity();
      entity.setRoll_weight(currentRoll.getPAPERBakaraWeight());
      entity.setMachine_type(currentRoll.getPAPERPaperMachine().getMashineName());
      entity.setQuality_degree(currentRoll.getPAPERQualityDegree().getDegree());
      entity.setQuality_degree(currentRoll.getPaperRejectreason());

      entityList.add(entity);
    }

    entityArray = new MonthlyProductionReport_Entity[fullRollList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }

}
//
// public void countOfEachReason( ArrayList<StorageDetail> storageDetailList ) {
//
// for
//
// }

// Long currentNum = (Long) query.list().get(0);

// // ----------------------
// MonthlyProductionReport_Entity entity = new MonthlyProductionReport_Entity();
// // entity.setClinic(currentClinic.getName());
// if (currentNum != null) {
// entity.setUnit(new BigDecimal(currentNum));
// } else {
// entity.setUnit(BigDecimal.ZERO);
// }
//
// entityList.add(entity);

//
// String hql = "select e from inclc_appointment e where e.appointmentstatus='OPENED' and e.clinic
// =:clinic and e.client=:client";
//
// if (fromDate != null)
// hql = hql + " and e.date >=:fromdate ";
//
// if (toDate != null)
// hql = hql + " and e.date <=:todate ";
//
//// ----------------------
// Query query = OBDal.getInstance().getSession().createQuery(hql);
//
// if (fromDate != null)
// query.setParameter("fromdate", fromDate);
// if (toDate != null)
// query.setParameter("todate", toDate);
//
// query.setParameter("client", OBContext.getOBContext().getCurrentClient());
//// query.setParameter("clinic", currentClinic);
