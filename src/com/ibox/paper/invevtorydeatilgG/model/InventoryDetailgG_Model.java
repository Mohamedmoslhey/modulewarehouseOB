package com.ibox.paper.invevtorydeatilgG.model;

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

import com.ibox.paper.data.PaperMachine;
import com.ibox.paper.data.QualityDegree;
import com.ibox.paper.invevtorydeatilgG.entity.InventoryDetailgG_Entity;

public class InventoryDetailgG_Model {

  public InventoryDetailgG_Entity[] get_report_data(String invD, String invMachine,
      String inpDegree, String inpproducttype) {

    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    List<InventoryDetailgG_Entity> entityList = new ArrayList<InventoryDetailgG_Entity>();

    InventoryDetailgG_Entity[] entityArray = null;
    Locator cuu = null;
    PaperMachine pMachine = null;
    QualityDegree qDegree = null;
    long gsom = 0;
    BigDecimal widoth = BigDecimal.ZERO;

    // Date fromDate = null;
    // Date toDate = null;

    SimpleDateFormat sDate = new SimpleDateFormat("MM");
    Date s = null;

    if (invD != null && !invD.equals("")) {
      cuu = OBDal.getInstance().get(Locator.class, invD);
    }
    if (invMachine != null && !invMachine.equals("")) {
      pMachine = OBDal.getInstance().get(PaperMachine.class, invMachine);
    }

    if (inpDegree != null && !inpDegree.equals("")) {
      qDegree = OBDal.getInstance().get(QualityDegree.class, inpDegree);
    }

    List<StorageDetail> StorageList = new ArrayList<StorageDetail>();

    String hql = "SELECT h from MaterialMgmtStorageDetail h where h.client=:client and  h.quantityOnHand<>0";

    if (cuu != null && !cuu.equals(""))
      hql = hql + " and  h.storageBin.id =:invD ";
    if (pMachine != null && !pMachine.equals("")) {
      hql = hql + " and  h.pAPERPaperMachine =:invMachine ";
    }
    if (qDegree != null && !qDegree.equals("")) {
      hql = hql + " and  h.pAPERQualityDegree =:inpDegree ";
    }

    if (inpproducttype != null && !inpproducttype.equals("")) {
      hql = hql + " and  h.pAPERProductType =:inpproducttype ";
    }

    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (cuu != null && !cuu.equals(""))
      query.setParameter("invD", cuu.getId());

    if (pMachine != null && !pMachine.equals(""))
      query.setParameter("invMachine", pMachine);

    if (qDegree != null && !qDegree.equals(""))
      query.setParameter("inpDegree", qDegree);

    if (inpproducttype != null && !inpproducttype.equals(""))
      query.setParameter("inpproducttype", inpproducttype);

    StorageList = query.list();

    /// --------------------,.,.,/object GSM

    String hqlo = "SELECT DISTINCT (h.pAPERGsm) from MaterialMgmtStorageDetail h where h.client=:client and  h.quantityOnHand<>0";

    if (cuu != null && !cuu.equals(""))
      hqlo = hqlo + " and  h.storageBin.id =:invD ";
    if (pMachine != null && !pMachine.equals("")) {
      hqlo = hqlo + " and  h.pAPERPaperMachine =:invMachine ";
    }
    if (qDegree != null && !qDegree.equals("")) {
      hqlo = hqlo + " and  h.pAPERQualityDegree =:inpDegree ";
    }

    if (inpproducttype != null && !inpproducttype.equals("")) {
      hqlo = hqlo + " and  h.pAPERProductType =:inpproducttype ";
    }

    Query queryo = OBDal.getInstance().getSession().createQuery(hqlo);
    queryo.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (cuu != null && !cuu.equals(""))
      queryo.setParameter("invD", cuu.getId());

    if (pMachine != null && !pMachine.equals(""))
      queryo.setParameter("invMachine", pMachine);

    if (qDegree != null && !qDegree.equals(""))
      queryo.setParameter("inpDegree", qDegree);

    if (inpproducttype != null && !inpproducttype.equals(""))
      queryo.setParameter("inpproducttype", inpproducttype);

    List<Object> object = queryo.list();

    // // -----------------getting sum bakara--------------

    String bakrasum = "SELECT count(h.pAPERJumboCode) from MaterialMgmtStorageDetail h where h.client=:client and  h.quantityOnHand<>0";

    if (cuu != null && !cuu.equals(""))
      bakrasum = bakrasum + " and  h.storageBin.id =:invD ";
    if (pMachine != null && !pMachine.equals("")) {
      bakrasum = bakrasum + " and  h.pAPERPaperMachine =:invMachine ";
    }

    if (qDegree != null && !qDegree.equals("")) {
      bakrasum = bakrasum + " and  h.pAPERQualityDegree =:inpDegree ";
    }

    if (inpproducttype != null && !inpproducttype.equals("")) {
      bakrasum = bakrasum + " and  h.pAPERProductType =:inpproducttype ";
    }

    Query bakrasumquery = OBDal.getInstance().getSession().createQuery(bakrasum);
    bakrasumquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (cuu != null && !cuu.equals(""))
      bakrasumquery.setParameter("invD", cuu.getId());

    if (pMachine != null && !pMachine.equals(""))
      bakrasumquery.setParameter("invMachine", pMachine);

    if (qDegree != null && !qDegree.equals(""))
      bakrasumquery.setParameter("inpDegree", qDegree);

    if (inpproducttype != null && !inpproducttype.equals(""))
      bakrasumquery.setParameter("inpproducttype", inpproducttype);

    Long currentNum1 = (Long) bakrasumquery.list().get(0);

    ///////////////////////////

    // // // -----------------gettingbakara wigth total--------------

    String bakrawi = "SELECT sum(h.pAPERBakaraWeight) from MaterialMgmtStorageDetail h where h.client=:client and h.quantityOnHand<>0";

    if (cuu != null && !cuu.equals(""))
      bakrawi = bakrawi + " and h.storageBin.id =:invD ";

    if (pMachine != null && !pMachine.equals("")) {
      bakrawi = bakrawi + " and  h.pAPERPaperMachine =:invMachine ";
    }

    if (qDegree != null && !qDegree.equals("")) {
      bakrawi = bakrawi + " and  h.pAPERQualityDegree =:inpDegree ";
    }

    if (inpproducttype != null && !inpproducttype.equals("")) {
      bakrawi = bakrawi + " and  h.pAPERProductType =:inpproducttype ";
    }

    Query bakrawiquery = OBDal.getInstance().getSession().createQuery(bakrawi);
    bakrawiquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (cuu != null && !cuu.equals(""))
      bakrawiquery.setParameter("invD", cuu.getId());

    if (pMachine != null && !pMachine.equals(""))
      bakrawiquery.setParameter("invMachine", pMachine);

    if (qDegree != null && !qDegree.equals(""))
      bakrawiquery.setParameter("inpDegree", qDegree);

    if (inpproducttype != null && !inpproducttype.equals(""))
      bakrawiquery.setParameter("inpproducttype", inpproducttype);

    BigDecimal currentNum2 = (BigDecimal) bakrawiquery.list().get(0);

    //////////////////////////// for GSM and Width

    // // -----------------getting sum bakara-------------- public static final String
    // PROPERTY_PAPERGSM = "pAPERGsm";
    // public static final String PROPERTY_PAPERBAKARAWIDTH = "pAPERBakaraWidth";

    ////

    long currentNum200cd = 0;

    /////

    //
    InventoryDetailgG_Entity entity = null;

    /// --------------------,.,.,/object width

    String hqlwidth = "SELECT DISTINCT (h.pAPERBakaraWidth) from MaterialMgmtStorageDetail h where h.client=:client and  h.quantityOnHand<>0";

    if (cuu != null && !cuu.equals(""))
      hqlwidth = hqlwidth + " and  h.storageBin.id =:invD ";
    if (pMachine != null && !pMachine.equals("")) {
      hqlwidth = hqlwidth + " and  h.pAPERPaperMachine =:invMachine ";
    }
    if (qDegree != null && !qDegree.equals("")) {
      hqlwidth = hqlwidth + " and  h.pAPERQualityDegree =:inpDegree ";
    }

    if (inpproducttype != null && !inpproducttype.equals("")) {
      hqlwidth = hqlwidth + " and  h.pAPERProductType =:inpproducttype ";
    }
    hqlwidth = hqlwidth + " ORDER BY  h.pAPERBakaraWidth DESC";

    Query querywidth = OBDal.getInstance().getSession().createQuery(hqlwidth);
    querywidth.setParameter("client", OBContext.getOBContext().getCurrentClient());

    if (cuu != null && !cuu.equals(""))
      querywidth.setParameter("invD", cuu.getId());

    if (pMachine != null && !pMachine.equals(""))
      querywidth.setParameter("invMachine", pMachine);

    if (qDegree != null && !qDegree.equals(""))
      querywidth.setParameter("inpDegree", qDegree);

    if (inpproducttype != null && !inpproducttype.equals(""))
      querywidth.setParameter("inpproducttype", inpproducttype);
    /* querywidth.setParameter("gsmobject", gsmlist); */

    List<Object> widthbject = querywidth.list();

    //

    for (Object widthlisty : widthbject) {
      entity = new InventoryDetailgG_Entity();
      BigDecimal widoobject = new BigDecimal(widthlisty.toString());
      Query bakrasumgwquery = null;
      Query bakrawigwquery = null;
      Integer gsmobject = 0;

      for (Object gsmlist : object) {
        gsmobject = new Integer(gsmlist.toString());

        Double widoobInt = widoobject.doubleValue();
        String bakrasumgw = "SELECT count(h.pAPERJumboCode) from MaterialMgmtStorageDetail h where h.client=:client and  h.quantityOnHand<>0";

        if (cuu != null && !cuu.equals(""))
          bakrasumgw = bakrasumgw + " and  h.storageBin.id =:invD ";
        if (pMachine != null && !pMachine.equals("")) {
          bakrasumgw = bakrasumgw + " and  h.pAPERPaperMachine =:invMachine ";
        }

        if (qDegree != null && !qDegree.equals("")) {
          bakrasumgw = bakrasumgw + " and  h.pAPERQualityDegree =:inpDegree ";
        }

        if (inpproducttype != null && !inpproducttype.equals("")) {
          bakrasumgw = bakrasumgw + " and  h.pAPERProductType =:inpproducttype ";
        }

        bakrasumgw = bakrasumgw + " and  h.pAPERBakaraWidth =:widoobInt ";
        bakrasumgw = bakrasumgw + " and  h.pAPERGsm =:gsmobject ";

        /* bakrasumgw = bakrasumgw + " GROUP BY  h.pAPERGsm "; */
        /* bakrasumgw = bakrasumgw + " and  h.pAPERBakaraWidth =:widoth "; */

        bakrasumgwquery = OBDal.getInstance().getSession().createQuery(bakrasumgw);
        bakrasumgwquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

        if (cuu != null && !cuu.equals(""))
          bakrasumgwquery.setParameter("invD", cuu.getId());

        if (pMachine != null && !pMachine.equals(""))
          bakrasumgwquery.setParameter("invMachine", pMachine);

        if (qDegree != null && !qDegree.equals(""))
          bakrasumgwquery.setParameter("inpDegree", qDegree);

        if (inpproducttype != null && !inpproducttype.equals(""))
          bakrasumgwquery.setParameter("inpproducttype", inpproducttype);

        bakrasumgwquery.setParameter("widoobInt", widthlisty);
        bakrasumgwquery.setParameter("gsmobject", gsmlist);

        //

        String bakrawigw = "SELECT sum(h.pAPERBakaraWeight) from MaterialMgmtStorageDetail h where h.client=:client and h.quantityOnHand<>0";

        if (cuu != null && !cuu.equals(""))
          bakrawigw = bakrawigw + " and h.storageBin.id =:invD ";

        if (pMachine != null && !pMachine.equals("")) {
          bakrawigw = bakrawigw + " and  h.pAPERPaperMachine =:invMachine ";
        }

        if (qDegree != null && !qDegree.equals("")) {
          bakrawigw = bakrawigw + " and  h.pAPERQualityDegree =:inpDegree ";
        }

        if (inpproducttype != null && !inpproducttype.equals("")) {
          bakrawigw = bakrawigw + " and  h.pAPERProductType =:inpproducttype ";
        }

        bakrawigw = bakrawigw + " and  h.pAPERBakaraWidth =:widoobInt ";
        bakrawigw = bakrawigw + " and  h.pAPERGsm =:gsmobject ";

        bakrawigwquery = OBDal.getInstance().getSession().createQuery(bakrawigw);
        bakrawigwquery.setParameter("client", OBContext.getOBContext().getCurrentClient());

        if (cuu != null && !cuu.equals(""))
          bakrawigwquery.setParameter("invD", cuu.getId());

        if (pMachine != null && !pMachine.equals(""))
          bakrawigwquery.setParameter("invMachine", pMachine);

        if (qDegree != null && !qDegree.equals(""))
          bakrawigwquery.setParameter("inpDegree", qDegree);

        if (inpproducttype != null && !inpproducttype.equals(""))
          bakrawigwquery.setParameter("inpproducttype", inpproducttype);

        bakrawigwquery.setParameter("widoobInt", widthlisty);
        bakrawigwquery.setParameter("gsmobject", gsmlist);

        if (inpproducttype.equals("3")) {
          entity.setProducttype("Core Board");
        } else if (inpproducttype.equals("2")) {
          entity.setProducttype("Meduim Fluting");
        } else if (inpproducttype.equals("1")) {
          entity.setProducttype("Manilla");
        } else if (inpproducttype.equals("4")) {
          entity.setProducttype("Test Liner");
        } else if (inpproducttype.equals("5")) {
          entity.setProducttype("special Core Board");
        } else {
          entity.setProducttype("الكل");
        }

        if (inpDegree != null && !inpDegree.equals("")) {
          entity.setQualitydegree(qDegree.getDegree());
        } else {
          entity.setQualitydegree("الكل");
        }

        if (invMachine != null && !invMachine.equals("")) {
          entity.setMachine(pMachine.getMashineName());

        } else {
          entity.setMachine("الكل");
        }

        ////

        entity.setBno(new BigDecimal(currentNum1));
        entity.setWno(currentNum2);
        Integer objInt = 0;
        Double objDo = 0d;
        List<Object> numcd = bakrasumgwquery.list();
        for (Object ob : numcd) {
          objInt = new Integer(ob.toString());

          if (gsmobject == 150) {
            entity.setBno150(new BigDecimal(objInt));
          }
          if (gsmobject == 200) {
            entity.setBno200(new BigDecimal(objInt));
          }
          if (gsmobject == 230) {
            entity.setBno230(new BigDecimal(objInt));
          }
          if (gsmobject == 250) {
            entity.setBno250(new BigDecimal(objInt));
          }
          if (gsmobject == 270) {
            entity.setBno270(new BigDecimal(objInt));
          }
          if (gsmobject == 280) {
            entity.setBno280(new BigDecimal(objInt));
          }
          if (gsmobject == 300) {
            entity.setBno300(new BigDecimal(objInt));
          }
          if (gsmobject == 320) {
            entity.setBno320(new BigDecimal(objInt));
          }
          if (gsmobject == 330) {
            entity.setBno330(new BigDecimal(objInt));
          }
          if (gsmobject == 340) {
            entity.setBno340(new BigDecimal(objInt));
          }
          if (gsmobject == 350) {
            entity.setBno350(new BigDecimal(objInt));
          }
          if (gsmobject == 370) {
            entity.setBno370(new BigDecimal(objInt));
          }

          if (gsmobject == 380) {
            entity.setBno380(new BigDecimal(objInt));
          }
          if (gsmobject == 400) {
            entity.setBno400(new BigDecimal(objInt));
          }
          if (gsmobject == 420) {
            entity.setBno420(new BigDecimal(objInt));
          }
          if (gsmobject == 450) {
            entity.setBno450(new BigDecimal(objInt));
          }
          if (gsmobject == 470) {
            entity.setBno470(new BigDecimal(objInt));
          }
          if (gsmobject == 480) {
            entity.setBno480(new BigDecimal(objInt));
          }
          if (gsmobject == 500) {
            entity.setBno500(new BigDecimal(objInt));
          }
          if (gsmobject == 550) {
            entity.setBno550(new BigDecimal(objInt));
          }

        }
        List<Object> wasncd = bakrawigwquery.list();
        for (Object obwasn : wasncd) {

          if (obwasn != null) {
            objDo = new Double(obwasn.toString());
            if (gsmobject == 150) {
              entity.setWno150(new BigDecimal(objDo));
            }
            if (gsmobject == 200) {
              entity.setWno200(new BigDecimal(objDo));
            }
            if (gsmobject == 280) {
              entity.setWno280(new BigDecimal(objDo));
            }
            if (gsmobject == 230) {
              entity.setWno230(new BigDecimal(objDo));
            }
            if (gsmobject == 250) {
              entity.setWno250(new BigDecimal(objDo));
            }
            if (gsmobject == 270) {
              entity.setWno270(new BigDecimal(objDo));
            }
            if (gsmobject == 280) {
              entity.setWno280(new BigDecimal(objDo));
            }
            if (gsmobject == 300) {
              entity.setWno300(new BigDecimal(objDo));
            }
            if (gsmobject == 320) {
              entity.setWno320(new BigDecimal(objDo));
            }
            if (gsmobject == 330) {
              entity.setWno330(new BigDecimal(objDo));
            }
            if (gsmobject == 340) {
              entity.setWno340(new BigDecimal(objDo));
            }
            if (gsmobject == 350) {
              entity.setWno350(new BigDecimal(objDo));
            }
            if (gsmobject == 370) {
              entity.setWno370(new BigDecimal(objDo));
            }
            if (gsmobject == 380) {
              entity.setWno380(new BigDecimal(objDo));
            }
            if (gsmobject == 400) {
              entity.setWno400(new BigDecimal(objDo));
            }
            if (gsmobject == 420) {
              entity.setWno420(new BigDecimal(objDo));
            }
            if (gsmobject == 450) {
              entity.setWno450(new BigDecimal(objDo));
            }
            if (gsmobject == 470) {
              entity.setWno470(new BigDecimal(objDo));
            }
            if (gsmobject == 480) {
              entity.setWno480(new BigDecimal(objDo));
            }
            if (gsmobject == 500) {
              entity.setWno500(new BigDecimal(objDo));
            }
            if (gsmobject == 550) {
              entity.setWno550(new BigDecimal(objDo));
            }
          } else {
            if (gsmobject == 150) {
              entity.setWno150(BigDecimal.ZERO);
            } else if (gsmobject == 200) {
              entity.setWno200(BigDecimal.ZERO);
            } else if (gsmobject == 230) {
              entity.setWno230(BigDecimal.ZERO);
            } else if (gsmobject == 250) {
              entity.setWno250(BigDecimal.ZERO);
            } else if (gsmobject == 270) {
              entity.setWno270(BigDecimal.ZERO);
            } else if (gsmobject == 280) {
              entity.setWno280(BigDecimal.ZERO);
            } else if (gsmobject == 300) {
              entity.setWno300(BigDecimal.ZERO);
            } else if (gsmobject == 320) {
              entity.setWno320(BigDecimal.ZERO);
            } else if (gsmobject == 330) {
              entity.setWno330(BigDecimal.ZERO);
            } else if (gsmobject == 340) {
              entity.setWno340(BigDecimal.ZERO);
            } else if (gsmobject == 350) {
              entity.setWno350(BigDecimal.ZERO);
            } else if (gsmobject == 370) {
              entity.setWno370(BigDecimal.ZERO);
            } else if (gsmobject == 380) {
              entity.setWno380(BigDecimal.ZERO);
            } else if (gsmobject == 400) {
              entity.setWno400(BigDecimal.ZERO);
            } else if (gsmobject == 420) {
              entity.setWno420(BigDecimal.ZERO);
            } else if (gsmobject == 450) {
              entity.setWno450(BigDecimal.ZERO);
            } else if (gsmobject == 470) {
              entity.setWno470(BigDecimal.ZERO);
            } else if (gsmobject == 480) {
              entity.setWno480(BigDecimal.ZERO);
            } else if (gsmobject == 500) {
              entity.setWno500(BigDecimal.ZERO);
            } else if (gsmobject == 550) {
              entity.setWno550(BigDecimal.ZERO);
            }
          }
        }

      }

      //

      entity.setBakarawidth(widoobject);

      entityList.add(entity);
    }
    // ----------------------

    // to set bakraweigth
    /* if(StorageList) */
    entityArray = new InventoryDetailgG_Entity[entityList.size()];
    entityList.toArray(entityArray);
    if (StorageList.isEmpty()) {
      return null;
    } else {
      return entityArray;
    }
  }
}