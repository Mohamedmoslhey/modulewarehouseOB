package com.ibox.paper.dreportperday.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBDal;

import com.ibox.paper.data.PaperRefuse;
import com.ibox.paper.data.PaperShift;
import com.ibox.paper.data.ProductionQuality;
import com.ibox.paper.data.QualityDegree;
import com.ibox.paper.dreportperday.entity.ProductPerDay_Entity;

public class ProductPerDay_Model {

  public ProductPerDay_Entity[] get_report_data(String proshiftid, String qdegreeid) {
    // , String cutshiftid,
    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    List<ProductPerDay_Entity> entityList = new ArrayList<ProductPerDay_Entity>();
    ProductPerDay_Entity[] entityArray = null;

    PaperShift currentProShift = null;
    PaperShift currentCutShift = null;
    QualityDegree currentQDegree = null;

    List<ProductionQuality> ProductList = new ArrayList<ProductionQuality>();
    List<PaperRefuse> refuseList = new ArrayList<PaperRefuse>();

    if (proshiftid != null && !proshiftid.equals("")) {
      currentProShift = OBDal.getInstance().get(PaperShift.class, proshiftid);
    }

    // if (cutshiftid != null && !cutshiftid.equals("")) {
    // currentCutShift = OBDal.getInstance().get(PaperShift.class, cutshiftid);
    // }
    if (qdegreeid != null && !qdegreeid.equals("")) {
      currentQDegree = OBDal.getInstance().get(QualityDegree.class, qdegreeid);
    }

    String hql = "select h from paper_quality h where h.client=:client";

    if (currentProShift != null && !currentProShift.equals(""))
      hql = hql + " and  h.proshift =:proshiftid  ";

    // if (currentCutShift != null && !currentCutShift.equals(""))
    // hql = hql + " and h.cutshift =:cutshiftid ";

    if (currentQDegree != null && !currentQDegree.equals(""))
      hql = hql + " and  h.paperQualityDegree =:qdegtid  ";

    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (currentProShift != null && !currentProShift.equals(""))
      query.setParameter("proshiftid", currentProShift);

    // if (currentCutShift != null && !currentCutShift.equals(""))
    // query.setParameter("cutshiftid", currentCutShift);

    if (currentQDegree != null && !currentQDegree.equals(""))
      query.setParameter("qdegtid", currentQDegree);

    ProductList = query.list();

    // -----------------getting sum bakara--------------
    String bakrasum = "select count(e.bakaracode) from paper_quality e   where e.paperQualityDegree=:deg and e.client=:client";

    Query bakrasumquery = OBDal.getInstance().getSession().createQuery(bakrasum);
    bakrasumquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (currentQDegree != null && !currentQDegree.equals(""))
      bakrasumquery.setParameter("deg", currentQDegree);

    Long currentNum1 = (Long) bakrasumquery.list().get(0);
    // List<Object[]> returnedList1 = bakrasumquery.list();
    // ------------------------------------------

    // -----------------getting sum weight--------------
    String weigthsum = "select sum(e.bakaraweight) from paper_quality e   where e.paperQualityDegree=:deg and e.client=:client";

    Query weigthsumquery = OBDal.getInstance().getSession().createQuery(weigthsum);
    weigthsumquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (currentQDegree != null && !currentQDegree.equals(""))
      weigthsumquery.setParameter("deg", currentQDegree);

    Long currentNum2 = (Long) weigthsumquery.list().get(0);
    // List<Object[]> returnedList2 = weigthsumquery.list();
    // ------------------------------------------
    // firstrefuse character varying(32),
    // secondrefuse character varying(32),

    ProductPerDay_Entity entity = null;
    for (ProductionQuality currentproduct : ProductList) {

      // ----------------------
      entity = new ProductPerDay_Entity();

      entity.setProshift(currentproduct.getProshift().getShiftname());
      // entity.setCutshift(currentproduct.getCutshift().getShiftname().toString());
      entity.setQdeg(currentproduct.getPaperQualityDegree().getDegree().toString());
      entity.setFaresres(currentproduct.getFirstrefuse() + " " + currentproduct.getSecondrefuse());
      entity.setBakarweigth(new BigDecimal(currentNum2));
      // to set bakranumber

      entity.setBakarnum(new BigDecimal(currentNum1));
      //
      entityList.add(entity);
    }

    // to set bakraweigth

    entityArray = new ProductPerDay_Entity[entityList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }
}
