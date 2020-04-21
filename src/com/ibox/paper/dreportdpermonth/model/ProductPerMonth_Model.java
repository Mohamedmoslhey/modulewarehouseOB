package com.ibox.paper.dreportdpermonth.model;

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

import com.ibox.paper.data.PaperRefuse;
import com.ibox.paper.data.ProductionQuality;
import com.ibox.paper.data.QualityDegree;
import com.ibox.paper.dreportdpermonth.entity.ProductPerMonth_Entity;

public class ProductPerMonth_Model {

  public ProductPerMonth_Entity[] get_report_data(String freason, String srreason, String datefrom,
      String dateto) {

    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    List<ProductPerMonth_Entity> entityList = new ArrayList<ProductPerMonth_Entity>();
    ProductPerMonth_Entity[] entityArray = null;

    QualityDegree currentQDegree = null;
    PaperRefuse cFRreason = null;
    PaperRefuse cSRreason = null;

    Date fromDate = null;
    Date toDate = null;
    if (freason != null && !freason.equals("")) {
      cFRreason = OBDal.getInstance().get(PaperRefuse.class, freason);
    }

    if (srreason != null && !srreason.equals("")) {
      cSRreason = OBDal.getInstance().get(PaperRefuse.class, srreason);
    }
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

    if (cFRreason != null && !cFRreason.equals("")) {
      hql = hql + " and  h.firstrefuse =:freason  ";
    }
    if (cSRreason != null && !cSRreason.equals("")) {
      hql = hql + " and  h.secondrefuse =:srreason  ";
    }
    if (fromDate != null)
      hql = hql + " and  h.productiondate >=:fromdate ";

    if (toDate != null)
      hql = hql + " and  h.productiondate <=:todate ";

    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (cFRreason != null && !cFRreason.equals(""))
      query.setParameter("freason", cFRreason);

    if (cSRreason != null && !cSRreason.equals(""))
      query.setParameter("srreason", cSRreason);

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

    // -----------------getting sum bakara fares one--------------

    //
    String hqlrf = "select e from paper_refuse e   where e.client=:client ";

    if (freason != null && !freason.equals("")) {
      hqlrf = hqlrf + " and  e.id =:freason  ";
    }

    Query hqlrqueryf = OBDal.getInstance().getSession().createQuery(hqlrf);
    hqlrqueryf.setParameter("client", OBContext.getOBContext().getCurrentClient());
    if (freason != null && !freason.equals(""))
      hqlrqueryf.setParameter("freason", cFRreason.getId());

    List<PaperRefuse> PaperRefuse2List = hqlrqueryf.list();

    // begin
    String bakrafone = "select count(e.bakaracode) from paper_quality e   where e.client=:client and e.sosataus IN('1' , '2')";

    bakrafone = bakrafone + " and  e.firstrefuse =:freason  ";

    if (fromDate != null)
      bakrafone = bakrafone + " and  e.productiondate >=:fromdate ";

    if (toDate != null)
      bakrafone = bakrafone + " and  e.productiondate <=:todate ";

    Query bakrafonequery = OBDal.getInstance().getSession().createQuery(bakrafone);
    bakrafonequery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    bakrafonequery.setParameter("freason", cFRreason);
    if (fromDate != null)
      bakrafonequery.setParameter("fromdate", fromDate);

    if (toDate != null)
      bakrafonequery.setParameter("todate", toDate);

    List<Object> pf = bakrafonequery.list();

    // Long currentNum3 = (Long) bakrafonequery.list().get(0);

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

    ProductPerMonth_Entity entity = null;
    for (ProductionQuality currentproduct : ProductList) {

      // ----------------------
      entity = new ProductPerMonth_Entity();

      entity.setWasn(new BigDecimal(currentproduct.getBakaraweight()));

      entity.setFromdate(fromDate.toString());
      entity.setTodate(toDate.toString());
      entity.setBno(new BigDecimal(currentNum1));
      entity.setWo(new BigDecimal(currentNum2));

      if (currentproduct.getPaperQualityDegree().getDegree().equals("1- درجة اولي")) {
        entity.setFirstresonfars(null);
        entity.setSecondresonfars(null);
        entity.setBnor(BigDecimal.ZERO);
      }
      if (currentproduct.getPaperQualityDegree().getDegree().equals("2- درجة ثانيه")
          || currentproduct.getPaperQualityDegree().getDegree().equals("3- درجة ثالثه")
          || currentproduct.getPaperQualityDegree().getDegree().equals("4- مخلفات")) {

        if ((currentproduct.getFirstrefuse() != null && currentproduct.getSecondrefuse() != null)) {
          entity.setFirstresonfars(currentproduct.getFirstrefuse().getReason());
          for (PaperRefuse cu1 : PaperRefuse2List) {
            System.out.println(cu1);
          }
          // entity.setBnor(new BigDecimal(currentNum3));
          entity.setSecondresonfars(currentproduct.getSecondrefuse().getReason());
        } else if ((currentproduct.getFirstrefuse() != null
            && currentproduct.getSecondrefuse() == null)) {

          entity.setFirstresonfars(currentproduct.getFirstrefuse().getReason());
          // entity.setBnor(new BigDecimal(currentNum3));
          entity.setSecondresonfars(null);
        } else {
          entity.setFirstresonfars(null);
          entity.setBnor(BigDecimal.ZERO);
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

      entity.setMonthdate(s.toString());

      entityList.add(entity);

    }

    // to set bakraweigth

    entityArray = new ProductPerMonth_Entity[entityList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }
}
