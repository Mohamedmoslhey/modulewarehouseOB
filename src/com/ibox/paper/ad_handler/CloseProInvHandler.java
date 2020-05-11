package com.ibox.paper.ad_handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBDal;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.model.common.enterprise.Organization;
import org.openbravo.model.financialmgmt.calendar.Period;
import org.openbravo.model.materialmgmt.transaction.ShipmentInOut;
import org.openbravo.scheduling.ProcessBundle;
import org.openbravo.service.db.DalBaseProcess;

public class CloseProInvHandler extends DalBaseProcess {
  // will start soon
  @Override
  protected void doExecute(ProcessBundle bundle) throws Exception {
    try {

      String org_id = "5B0384D9A3164F1191BAFAFAB53CC1FF";
      Organization organization = (Organization) OBDal.getInstance().get(Organization.class,
          org_id);

      // Retrieve Opening balance Object
      final String period_id = (String) bundle.getParams().get("C_Period_ID");

      Period savedPeriod = (Period) OBDal.getInstance().get(Period.class, period_id);

      savedPeriod.getStatus();
      savedPeriod.getYear();
      savedPeriod.getPeriodNo();
      savedPeriod.getName();
      savedPeriod.getPeriodType();

      List<ShipmentInOut> materialtrxList = new ArrayList<ShipmentInOut>();

      Date fromDate = savedPeriod.getStartingDate();
      Date toDate = savedPeriod.getEndingDate();

      String hql = "select h from MaterialMgmtShipmentInOut h where h.client=:client"
          + " and h.movementType IN ('C-', 'C+') and h.logistic = 'N' and h.documentType.return='N'";

      if (fromDate != null && !fromDate.equals(""))
        hql = hql + " and h.movementDate >=:fromdate ";

      if (toDate != null && !toDate.equals(""))
        hql = hql + " and h.movementDate <=:todate ";

      Query query = OBDal.getInstance().getSession().createQuery(hql);
      query.setParameter("client", OBContext.getOBContext().getCurrentClient());

      if (fromDate != null && !fromDate.equals(""))
        query.setParameter("fromdate", fromDate);

      if (toDate != null && !toDate.equals(""))
        query.setParameter("todate", toDate);

      materialtrxList = query.list();
      /*
       * String strSql =
       * "update m_inout set processed ='Y',docaction ='--',docstatus='CO',process_goods_java='--' where m_inout.MovementType IN ('C-', 'C+') and m_inout.isLogistic = 'N'"
       * ; if (idNoc != null && !idNoc.equals("")) strSql = strSql + "and m_inout_id =:idNoc ";
       * 
       * Query query = OBDal.getInstance().getSession().createSQLQuery(strSql);
       * query.setParameter("idNoc", inOut.getId()); // int moi = query.executeUpdate(); // create
       * m_product Product product = new Product();
       * product.setClient(OBContext.getOBContext().getCurrentClient());
       * product.setOrganization(organization); product.setActive(true); product.setCreationDate(new
       * Date()); product.setCreatedBy(OBContext.getOBContext().getUser()); product.setUpdated(new
       * Date()); product.setUpdatedBy(OBContext.getOBContext().getUser());
       * product.setSearchKey(savedRoll.getRollCode()); product.setName(savedRoll.getRollCode());
       * final String UOM_id = "2320C84F79484999A7688BC4CC4121C7"; UOM uom = (UOM)
       * OBDal.getInstance().get(UOM.class, UOM_id); product.setUOM(uom); final String
       * Product_cateogry_id = "57B8E08BB8B74686A7F0F1931E5F7BC6"; ProductCategory category =
       * (ProductCategory) OBDal.getInstance().get(ProductCategory.class, Product_cateogry_id);
       * product.setProductCategory(category); final String cateogrytax_id =
       * "DECE910DAEB84B189E86E89C85BF4F4A"; TaxCategory taxcategory = (TaxCategory)
       * OBDal.getInstance().get(TaxCategory.class, cateogrytax_id);
       * product.setTaxCategory(taxcategory);
       * 
       * OBDal.getInstance().save(product);
       * 
       * String sqlQuery2 =
       * "INSERT INTO paper_quality( paper_quality_id , ad_client_id, ad_org_id, created," +
       * " createdby, updated, updatedby, isactive, bakaracode," +
       * " gsm , producttype ,bakaraweight, bakarawidth, paper_quality_degree_id ,bakaraqotr , isgard , "
       * + " tryone , trytwo , trythree , tryfour, tryfive, trysix , tryseven , tryeight ," +
       * " trynine , tryten , avrage , shad_toly , shadardy , somc , fasltabky , retopa , wasla )" +
       * "VALUES ( :id , :client,:org, now(), :user ,now(), :user,'Y', :bakraCode," +
       * " :gsm ,:product_type , :bakra_weight ,:bakra_width , :paper_degree , :bakra_qotr ,  :isgard,"
       * + " 0 , 0 ,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,'0')";
       * 
       * Query q2 = OBDal.getInstance().getSession().createSQLQuery(sqlQuery2); String
       * paper_quality_id = SequenceIdData.getUUID(); q2.setParameter("id", paper_quality_id);
       * q2.setParameter("client", OBContext.getOBContext().getCurrentClient().getId());
       * q2.setParameter("org", organization.getId()); q2.setParameter("user",
       * OBContext.getOBContext().getUser().getId()); q2.setParameter("bakraCode",
       * savedRoll.getRollCode()); q2.setParameter("gsm", savedRoll.getGsm());
       * q2.setParameter("product_type", savedRoll.getRollType()); q2.setParameter("bakra_weight",
       * savedRoll.getWeight()); q2.setParameter("bakra_width", savedRoll.getWidth());
       * q2.setParameter("paper_degree", savedRoll.getDegreeType().getId());
       * q2.setParameter("bakra_qotr", savedRoll.getQotr()); q2.setParameter("isgard", "Y");
       * 
       * System.out.println(q2.toString()); q2.executeUpdate();
       * 
       * // insert ( bakara Details ) M_storage details
       * 
       * StorageDetail roll_detail = new StorageDetail(); // roll_detail.setId("10064151515151");
       * roll_detail.setClient(OBContext.getOBContext().getCurrentClient());
       * roll_detail.setOrganization(organization); roll_detail.setActive(true);
       * roll_detail.setCreationDate(new Date());
       * roll_detail.setCreatedBy(OBContext.getOBContext().getUser()); roll_detail.setUpdated(new
       * Date()); roll_detail.setUpdatedBy(OBContext.getOBContext().getUser());
       * roll_detail.setStorageBin(storageBin); roll_detail.setProduct(product);
       * roll_detail.setPAPERBakaraWeight(new BigDecimal(savedRoll.getWeight()));
       * roll_detail.setPAPERBakaraWidth(new BigDecimal(savedRoll.getWidth()));
       * roll_detail.setPAPERBakkaraQotr(new BigDecimal(savedRoll.getQotr()));
       * roll_detail.setPAPERJumboCode(savedRoll.getRollCode());
       * roll_detail.setPAPERQualityDegree(savedRoll.getDegreeType());
       * roll_detail.setPaperNotes(savedRoll.getComments());
       * roll_detail.setPAPERProductType(savedRoll.getRollType());
       * roll_detail.setPAPERGsm(savedRoll.getGsm()); roll_detail.setUOM(uom);
       * roll_detail.setQuantityOnHand(BigDecimal.ONE); roll_detail.setPaperIsgard(true);
       * 
       * System.out.println(roll_detail.toString());
       * 
       * OBDal.getInstance().save(roll_detail);
       * 
       * // delete opening balance object String Paper_Opening_Balance_ID =
       * bundle.getParams().get("Paper_Opening_Balance_ID") .toString();
       * 
       * OpeningBalance saved_opening_balance = (OpeningBalance) OBDal.getInstance()
       * .get(OpeningBalance.class, Paper_Opening_Balance_ID);
       * 
       * OBDal.getInstance().remove(saved_opening_balance);
       */
      OBError ob = new OBError();
      ob.setType(SUCCESS);
      ob.setTitle("نجاح");
      ob.setMessage("تم التحويل الى المخزن التام");
      bundle.setResult(ob);

    } catch (Exception e) {
      OBError ob = new OBError();
      ob.setType(ERROR);
      ob.setTitle(ERROR);
      ob.setMessage(e.getMessage());
      System.out.print(e.getMessage());
      bundle.setResult(ob);
    }

  }

}

// OBDal.getInstance().flush();

// OBDal.getInstance().getSession().getTransaction().commit();
// System.out.println(OBDal.getInstance().getSession().getTransaction().getStatus());

// OBDal.getInstance().getSession().beginTransaction();

// System.out.println(OBDal.getInstance().getSession().getTransaction().getStatus());

// OBDal.getInstance().getSession().getTransaction().commit();

// OBDal.getInstance().flush();

// System.out.println(OBDal.getInstance().getSession().getTransaction().getStatus());

// OBDal.getInstance().getSession().beginTransaction();
