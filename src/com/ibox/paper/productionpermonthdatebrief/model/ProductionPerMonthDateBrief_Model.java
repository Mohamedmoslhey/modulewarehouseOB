package com.ibox.paper.productionpermonthdatebrief.model;

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
import com.ibox.paper.productionpermonthdatebrief.entity.ProductionPerMonthDateBrief_Entity;

public class ProductionPerMonthDateBrief_Model {

  public ProductionPerMonthDateBrief_Entity[] get_report_data(String datefrom, String dateto,
      String inmachine) {

    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    List<ProductionPerMonthDateBrief_Entity> entityList = new ArrayList<ProductionPerMonthDateBrief_Entity>();
    ProductionPerMonthDateBrief_Entity[] entityArray = null;

    PaperMachine currentMachine = null;

    QualityDegree currentQDegree = null;
    List<String> datetest = new ArrayList<>();
    datetest.add("0");

    List<String> listtest = new ArrayList<>();
    listtest.add("0");
    List<String> deglist = new ArrayList<>();
    deglist.add("0");

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

    if (inmachine != null && !inmachine.equals("")) {
      currentMachine = OBDal.getInstance().get(PaperMachine.class, inmachine);
    }

    List<ProductionQuality> ProductList = new ArrayList<ProductionQuality>();

    String hql = "select h from paper_quality h where h.client=:client and h.sosataus IN('1' ,'2')";

    if (fromDate != null)
      hql = hql + " and h.productiondate >=:fromdate ";

    if (toDate != null)
      hql = hql + " and h.productiondate <=:todate ";

    if (currentMachine != null && !currentMachine.equals(""))
      hql = hql + " and h.paperMachine =:inmachine ";

    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (fromDate != null)
      query.setParameter("fromdate", fromDate);

    if (toDate != null)
      query.setParameter("todate", toDate);

    if (currentMachine != null && !currentMachine.equals(""))
      query.setParameter("inmachine", currentMachine);

    ProductList = query.list();

    // -----------------getting sum bakara--------------
    String bakrasum = "select count(e.bakaracode) from paper_quality e   where e.client=:client and e.sosataus IN('1' , '2')";

    if (fromDate != null)
      bakrasum = bakrasum + " and  e.productiondate >=:fromdate ";

    if (toDate != null)
      bakrasum = bakrasum + " and  e.productiondate <=:todate ";
    if (currentMachine != null && !currentMachine.equals(""))
      bakrasum = bakrasum + " and  e.paperMachine =:inmachine ";

    Query bakrasumquery = OBDal.getInstance().getSession().createQuery(bakrasum);
    bakrasumquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (fromDate != null)
      bakrasumquery.setParameter("fromdate", fromDate);

    if (toDate != null)
      bakrasumquery.setParameter("todate", toDate);

    if (currentMachine != null && !currentMachine.equals(""))
      bakrasumquery.setParameter("inmachine", currentMachine);

    Long currentNum1 = (Long) bakrasumquery.list().get(0);

    // -----------------getting weigth bakara--------------
    String bakraweigth = "select sum(e.bakaraweight) from paper_quality e where e.client=:client  and e.sosataus IN('1' , '2')";

    if (fromDate != null)
      bakraweigth = bakraweigth + " and e.productiondate >=:fromdate ";

    if (toDate != null)
      bakraweigth = bakraweigth + " and e.productiondate <=:todate ";

    if (currentMachine != null && !currentMachine.equals(""))
      bakraweigth = bakraweigth + " and  e.paperMachine =:inmachine ";

    Query bakrawequery = OBDal.getInstance().getSession().createQuery(bakraweigth);
    bakrawequery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (fromDate != null)
      bakrawequery.setParameter("fromdate", fromDate);

    if (toDate != null)
      bakrawequery.setParameter("todate", toDate);
    if (currentMachine != null && !currentMachine.equals(""))
      bakrawequery.setParameter("inmachine", currentMachine);

    Long currentNum2 = (Long) bakrawequery.list().get(0);

    ProductionPerMonthDateBrief_Entity entity = null;

    // QualityDegree
    // -----------------getting sum bakara---byday-----------
    String bakradaysum = "select distinct (e.productiondate),count(e.bakaracode),sum(e.bakaraweight),e.paperQualityDegree.id from paper_quality e where e.client=:client and e.sosataus IN('1' , '2')";
    if (fromDate != null)
      bakradaysum = bakradaysum + " and e.productiondate >=:fromdate ";

    if (toDate != null)
      bakradaysum = bakradaysum + " and e.productiondate <=:todate ";
    if (currentMachine != null && !currentMachine.equals(""))
      bakradaysum = bakradaysum + " and  e.paperMachine =:inmachine ";

    // bakradaysum = bakradaysum + " and e.paperQualityDegree =:degt";
    // bakradaysum = bakradaysum + " and e.productiondate =:dayo ";
    bakradaysum = bakradaysum + " group by e.productiondate,e.paperQualityDegree";
    bakradaysum = bakradaysum + " order by e.productiondate ASC,e.paperQualityDegree DESC";

    Query bakradaysumquery = OBDal.getInstance().getSession().createQuery(bakradaysum);
    bakradaysumquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (fromDate != null)
      bakradaysumquery.setParameter("fromdate", fromDate);

    if (toDate != null)
      bakradaysumquery.setParameter("todate", toDate);
    if (currentMachine != null && !currentMachine.equals(""))
      bakradaysumquery.setParameter("inmachine", currentMachine);
    // for (ProductionQuality currentproduct : ProductList) {
    // bakradaysumquery.setParameter("degt", currentproduct.getPaperQualityDegree());
    // bakradaysumquery.setParameter("dayo", currentproduct.getProductiondate());
    // }
    List<Object[]> currentNumday = bakradaysumquery.list();

    for (Object[] currentNumelist : currentNumday) {

      //
      entity = new ProductionPerMonthDateBrief_Entity();
      entity.setProdate(currentNumelist[0].toString());
      if ((currentNumelist[3].toString()).equals("BDA01ACE0C064761A002991BEFC4C2DC")) {
        entity.setQualitydegree("درجة اولى");
      }
      if ((currentNumelist[3].toString()).equals("8DE7C67B4DDE4CA396E1CE7EF3A6F177")) {
        entity.setQualitydegree("درجة ثانية");
      }
      if ((currentNumelist[3].toString()).equals("1864872CEC4D41EE9182293F0A8D1BE1")) {
        entity.setQualitydegree("درجة ثالثة");
      }
      if ((currentNumelist[3].toString()).equals("E73B2C568B07494EB2350A0828277CE8")) {
        entity.setQualitydegree("مخلفات");
      }

      entity.setBakarno(new BigDecimal(currentNumelist[1].toString()));
      entity.setBakarwno(new BigDecimal(currentNumelist[2].toString()));
      entity.setFromdate(fromDate.toString());
      entity.setTodate(toDate.toString());
      entity.setBno(new BigDecimal(currentNum1));
      entity.setWno(new BigDecimal(currentNum2));
      if (currentMachine != null && !currentMachine.equals("")) {
        entity.setMachine(currentMachine.getMashineName());
      }
      // if (!entityList.contains((currentproduct.getPaperQualityDegree()))) {

      entityList.add(entity);
      // }
    }

    // to set bakraweigth

    entityArray = new ProductionPerMonthDateBrief_Entity[entityList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }
}