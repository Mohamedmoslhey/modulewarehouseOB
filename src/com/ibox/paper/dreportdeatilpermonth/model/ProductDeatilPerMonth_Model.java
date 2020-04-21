package com.ibox.paper.dreportdeatilpermonth.model;

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
import com.ibox.paper.data.ProductionQuality;
import com.ibox.paper.data.QualityDegree;
import com.ibox.paper.dreportdeatilpermonth.entity.ProductDeatilPerMonth_Entity;

public class ProductDeatilPerMonth_Model {

  public ProductDeatilPerMonth_Entity[] get_report_data(String datefrom, String dateto) {

    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    List<ProductDeatilPerMonth_Entity> entityList = new ArrayList<ProductDeatilPerMonth_Entity>();
    ProductDeatilPerMonth_Entity[] entityArray = null;

    PaperMachine currentMachine = null;

    QualityDegree currentQDegree = null;

    Date fromDate = null;
    Date toDate = null;

    SimpleDateFormat sDate = new SimpleDateFormat("MM");
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

    List<ProductionQuality> ProductList = new ArrayList<ProductionQuality>();

    String hql = "select h from paper_quality h where h.client=:client and h.sosataus IN('1' , '2')";

    if (fromDate != null)
      hql = hql + " and  h.productiondate >=:fromdate ";

    if (toDate != null)
      hql = hql + " and  h.productiondate <=:todate ";

    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (fromDate != null)
      query.setParameter("fromdate", fromDate);

    if (toDate != null)
      query.setParameter("todate", toDate);

    ProductList = query.list();

    // -----------------getting sum bakara--------------
    String bakrasum = "select count(e.bakaracode) from paper_quality e   where e.client=:client and e.sosataus IN('1' , '2')";

    if (fromDate != null)
      bakrasum = bakrasum + " and  e.productiondate >=:fromdate ";

    if (toDate != null)
      bakrasum = bakrasum + " and  e.productiondate <=:todate ";

    Query bakrasumquery = OBDal.getInstance().getSession().createQuery(bakrasum);
    bakrasumquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (fromDate != null)
      bakrasumquery.setParameter("fromdate", fromDate);

    if (toDate != null)
      bakrasumquery.setParameter("todate", toDate);

    Long currentNum1 = (Long) bakrasumquery.list().get(0);

    // -----------------getting weigth bakara--------------
    String bakraweigth = "select sum(e.bakaraweight) from paper_quality e where e.client=:client  and e.sosataus IN('1' , '2')";

    if (fromDate != null)
      bakraweigth = bakraweigth + " and e.productiondate >=:fromdate ";

    if (toDate != null)
      bakraweigth = bakraweigth + " and e.productiondate <=:todate ";

    Query bakrawequery = OBDal.getInstance().getSession().createQuery(bakraweigth);
    bakrawequery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (fromDate != null)
      bakrawequery.setParameter("fromdate", fromDate);

    if (toDate != null)
      bakrawequery.setParameter("todate", toDate);

    Long currentNum2 = (Long) bakrawequery.list().get(0);

    ProductDeatilPerMonth_Entity entity = null;
    for (ProductionQuality currentproduct : ProductList) {

      // ----------------------
      entity = new ProductDeatilPerMonth_Entity();

      entity.setBakaracode(currentproduct.getBakaracode());
      if (currentproduct.getProductType().equals("3")) {
        entity.setProducttype("Core Board");
      }
      if (currentproduct.getProductType().equals("2")) {
        entity.setProducttype("Meduim Fluting");
      }
      if (currentproduct.getProductType().equals("1")) {
        entity.setProducttype("Manilla");
      }
      if (currentproduct.getProductType().equals("4")) {
        entity.setProducttype("Test Liner");
      }
      if (currentproduct.getProductType().equals("5")) {
        entity.setProducttype("special Core Board");
      }
      entity.setBussinesspartnercode(currentproduct.getBusinessPartner().getName());
      entity.setWidth(currentproduct.getBakarawidth());
      entity.setGsm(currentproduct.getGsm());
      entity.setProductionres(currentproduct.getPname().getEmployeeName());
      entity.setProductionshift(currentproduct.getProshift().getShiftname());
      entity.setMaqsmasool(currentproduct.getCname().getEmployeeName());
      entity.setQualitymasool(currentproduct.getPaperEmployeeIdd().getEmployeeName());
      entity.setMaqsshift(currentproduct.getCutshift().getShiftname());
      entity.setWeigthone(currentproduct.getTryone().toString());
      entity.setWeigthtwo(currentproduct.getTrytwo().toString());
      entity.setWeigththree(currentproduct.getTrythree().toString());
      entity.setWeigthfour(currentproduct.getTryfour().toString());
      entity.setWeigthfive(currentproduct.getTryfive().toString());
      entity.setWeigthsix(currentproduct.getTrysix().toString());
      entity.setWeigthseven(currentproduct.getTryseven().toString());
      entity.setWeigtheight(currentproduct.getTryeight().toString());
      entity.setWeigthnine(currentproduct.getTrynine().toString());
      entity.setWeigthten(currentproduct.getTryten().toString());
      entity.setAverage(currentproduct.getAvrage().toString());
      entity.setWasn(new BigDecimal(currentproduct.getBakaraweight()));

      entity.setSomcone(currentproduct.getSomcone().toString());
      entity.setSomctwo(currentproduct.getSomctwo().toString());
      entity.setSomcthree(currentproduct.getSomcthree().toString());
      entity.setSomcfour(currentproduct.getSomcfour().toString());
      entity.setSomcfive(currentproduct.getSomcfive().toString());
      entity.setSomcsix(currentproduct.getSomcsix().toString());
      entity.setSomcseven(currentproduct.getSomcseven().toString());
      entity.setSomceigth(currentproduct.getSomceigth().toString());
      entity.setSomcnine(currentproduct.getSomcnine().toString());
      entity.setSomcten(currentproduct.getSomcten().toString());
      entity.setSomcaverage(currentproduct.getSomcaverage().toString());
      entity.setFromdate(fromDate.toString());
      entity.setTodate(toDate.toString());
      entity.setBno(new BigDecimal(currentNum1));
      entity.setWo(new BigDecimal(currentNum2));
      entity.setMachine(currentproduct.getPaperMachine().getMashineName());
      entity.setShadtolly(currentproduct.getShadToly());
      entity.setShadardy(currentproduct.getShadToly());

      entity.setWaghcolor(currentproduct.getFrontcolor());
      entity.setBackcolor(currentproduct.getBackcolor());
      entity.setWaslano(currentproduct.getWasla());
      entity.setQualitydegree(currentproduct.getPaperQualityDegree().getDegree());
      if (currentproduct.getPaperQualityDegree().getDegree().equals("1- درجة اولي")) {
        entity.setFirstresonfars(null);
        entity.setSecondresonfars(null);
      }
      if (currentproduct.getPaperQualityDegree().getDegree().equals("2- درجة ثانيه")
          || currentproduct.getPaperQualityDegree().getDegree().equals("3- درجة ثالثه")
          || currentproduct.getPaperQualityDegree().getDegree().equals("4- مخلفات")) {

        if ((currentproduct.getFirstrefuse() != null && currentproduct.getSecondrefuse() != null)) {
          entity.setFirstresonfars(currentproduct.getFirstrefuse().getReason());
          entity.setSecondresonfars(currentproduct.getSecondrefuse().getReason());
        } else if ((currentproduct.getFirstrefuse() != null
            && currentproduct.getSecondrefuse() == null)) {

          entity.setFirstresonfars(currentproduct.getFirstrefuse().getReason());
          entity.setSecondresonfars(null);
        } else {
          entity.setFirstresonfars(null);
          entity.setSecondresonfars(null);
        } //
        // if (currentproduct.getSecondrefuse() == null) {
        // entity.setFirstresonfars(currentproduct.getFirstrefuse().getReason());
        // entity.setSecondresonfars(null);
        // } else if (currentproduct.getFirstrefuse().getReason().equals(null)) {
        // entity.setFirstresonfars(null);
        // entity.setSecondresonfars(currentproduct.getSecondrefuse().getReason());
        // } else {
        //
        // entity.setFirstresonfars(currentproduct.getFirstrefuse().getReason());
        // entity.setSecondresonfars(currentproduct.getSecondrefuse().getReason());
        // }
      }

      entity.setNotes(currentproduct.getNotes());
      entity.setProdate(currentproduct.getProductiondate().toString());
      entity.setMonthdate(s.toString());

      entityList.add(entity);

    }

    // to set bakraweigth

    entityArray = new ProductDeatilPerMonth_Entity[entityList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }
}
