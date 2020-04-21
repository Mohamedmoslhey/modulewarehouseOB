package com.ibox.paper.reportsd.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Expression;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;

import com.ibox.paper.data.PaperShift;
import com.ibox.paper.data.ProductionQuality;
import com.ibox.paper.data.QualityDegree;
import com.ibox.paper.reportsd.entity.DailyProductEntity;

public class DailyProductModel {

  public DailyProductEntity[] get_report_data(String shiftId, String qualityDegree, String date) {

    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    List<DailyProductEntity> entityList = new ArrayList<DailyProductEntity>();
    DailyProductEntity[] entityArray = null;

    Date dDate = null;

    if (date != null && !date.equals("")) {
      try {
        dDate = formate.parse(date);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    List<ProductionQuality> fullShiftList = new ArrayList<ProductionQuality>();
    // List<StorageDetail> first_deg_RollList = new ArrayList<StorageDetail>();
    // List<StorageDetail> second_deg_RollList = new ArrayList<StorageDetail>();
    // List<StorageDetail> third_deg_RollList = new ArrayList<StorageDetail>();
    if (shiftId != null && !shiftId.equals("")) {

      PaperShift currentShift = OBDal.getInstance().get(PaperShift.class, shiftId);
      QualityDegree qDegree = OBDal.getInstance().get(QualityDegree.class, qualityDegree);

      final OBCriteria<ProductionQuality> obc = OBDal.getInstance()
          .createCriteria(ProductionQuality.class);
      obc.add(Expression.eq(ProductionQuality.PROPERTY_PROSHIFT, currentShift));
      obc.add(Expression.ge(ProductionQuality.PROPERTY_CREATIONDATE, dDate));
      obc.add(Expression.ge(ProductionQuality.PROPERTY_PAPERQUALITYDEGREE, qDegree));
      // obc.add(Expression.le(StorageDetail.PROPERTY_CREATIONDATE, toDate));
      fullShiftList = obc.list();

      // first_deg_RollList = obc.setProjection(
      // Projections.projectionList().add(Projections.groupProperty("em_paper_quality_degree")))
      // .list();

      // obc.add(Expression.eq(StorageDetail.PROPERTY_PAPERPAPERMACHINE, currentMachine));

    } else {
      OBCriteria<ProductionQuality> shiftCriteria = OBDal.getInstance()
          .createCriteria(ProductionQuality.class);

      fullShiftList = shiftCriteria.list();

    }

    for (ProductionQuality currentshift : fullShiftList) {

      // if (currentRoll.getPAPERQualityDegree().getDegree().equals("درجة اولي")) {
      // first_deg_RollList.add(currentRoll);
      // } else if (currentRoll.getPAPERQualityDegree().getDegree().equals("درجة ثانيه")) {
      //
      // second_deg_RollList.add(currentRoll);
      // } else if (currentRoll.getPAPERQualityDegree().getDegree().equals("درجة ثالثه")) {
      // third_deg_RollList.add(currentRoll);
      // }

      // private String group_weight;//
      //
      //
      // private String quality_degree_weight;//
      // private String quality_bakra_num;//
      // private String total_weight;//
      // private String total_bakra_num;//

      DailyProductEntity entity = new DailyProductEntity();
      entity.setRoll_weight(new BigDecimal(currentshift.getBakaraweight()));
      entity.setShift_type(currentshift.getProshift().toString());
      entity.setQuality_degree(currentshift.getPaperQualityDegree().getDegree());
      entity.setQuality_degree(currentshift.getRejectreason());

      entityList.add(entity);
    }

    entityArray = new DailyProductEntity[fullShiftList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }

  public DailyProductEntity[] PrintStudentModel(String fromdate, String emptype, String fromemp,
      String toemp) {
    // TODO Auto-generated method stub
    return null;
  }
}
