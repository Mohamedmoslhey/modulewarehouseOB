package com.ibox.paper.salesOrderWithProductionMachine.model;

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
import org.openbravo.model.common.enterprise.ADMonth;
import org.openbravo.model.common.enterprise.Locator;
import org.openbravo.model.materialmgmt.transaction.ShipmentInOut;

import com.ibox.paper.data.PaperMachine;
import com.ibox.paper.data.QualityDegree;
import com.ibox.paper.salesOrderWithProductionMachine.entity.SalesProduction_Entity;

public class SalesProduction_Model {

  public SalesProduction_Entity[] get_report_data(String inpDateFrom, String inmachine,
      String inpDateTo) {

    // public InventoryBrief_Entity[] get_report_data(String invD, String inmachine,
    // String inpproducttype) {

    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    Date fromDate = null;
    Date toDate = null;

    if (inpDateFrom != null && !inpDateFrom.equals("")) {
      try {
        fromDate = formate.parse(inpDateFrom);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    if (inpDateTo != null && !inpDateTo.equals("")) {

      try {
        toDate = formate.parse(inpDateTo);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
    List<SalesProduction_Entity> entityList = new ArrayList<SalesProduction_Entity>();

    SalesProduction_Entity[] entityArray = null;
    Locator cuu = null;
    PaperMachine pMachine = null;

    if (inmachine != null && !inmachine.equals("")) {
      pMachine = OBDal.getInstance().get(PaperMachine.class, inmachine);
    }

    List<Date> DateList = new ArrayList<Date>();
    List<ADMonth> MonthList = new ArrayList<ADMonth>();
    String MhQl = "select h  from ADMonth h  ";
    // MhQl = MhQl + " where h.searchKey =:aZ";
    Query Mquery = OBDal.getInstance().getSession().createQuery(MhQl);
    // Mquery.setParameter("aZ", aZ);
    MonthList = Mquery.list();
    for (ADMonth MList : MonthList) {
      String mSearch = MList.getSearchKey();
      String MahQl = "select e.movementDate  from MaterialMgmtShipmentInOut e where e.movementType IN ('C-', 'C+') and e.logistic = 'N'"
          + " and e.documentType.return=false ";

      MahQl = MahQl + " and TO_CHAR(e.movementDate, 'MM') =:mSearch";

      MahQl = MahQl + " group by e.movementDate";
      MahQl = MahQl + " order by e.movementDate asc";
      Query MHJquery = OBDal.getInstance().getSession().createQuery(MahQl);
      MHJquery.setParameter("mSearch", mSearch);
      DateList = MHJquery.list();

      for (Date sdList : DateList) {
        int a = sdList.getMonth();
        String aZ = Integer.toString(a);

        // String MhQl = "select e.movementDate from MaterialMgmtShipmentInOut e where
        // e.movementType
        // IN
        // ('C-', 'C+') and e.logistic = 'N' and e.documentType.return=false ";

        if (aZ.equals(MList.getSearchKey())) {
          System.out.println(MList.getName());
        }

      }
    }

    String hql = "SELECT h from paper_quality_degree h where h.client=:client ";

    Query query = OBDal.getInstance().getSession().createQuery(hql);
    query.setParameter("client", OBContext.getOBContext().getCurrentClient());

    List<QualityDegree> sobjectList = query.list();

    ///////////////////////////
    // TO_CHAR(h.shipmentReceipt.movementDate, 'MM')
    List<Object> YearList = new ArrayList<Object>();
    String YearhQl = "select DISTINCT(TO_CHAR(e.movementDate, 'YYYY'))  from MaterialMgmtShipmentInOut e where e.movementType IN ('C-', 'C+') and e.logistic = 'N'"
        + " and e.documentType.return=false ";
    YearhQl = YearhQl + " and TO_CHAR(e.movementDate, 'YYYY') >='2019'";
    YearhQl = YearhQl + " and TO_CHAR(e.movementDate, 'YYYY') !='2201'";
    Query Yearquery = OBDal.getInstance().getSession().createQuery(YearhQl);

    YearList = Yearquery.list();
    //////////////////////////
    SalesProduction_Entity entity = null;
    SalesProduction_Entity yEntity = null;
    ShipmentInOut i;

    List<PaperMachine> MachineList = new ArrayList<PaperMachine>();
    String machinehQl = "SELECT h from paper_machine h  ";
    if (pMachine != null && !pMachine.equals(""))
      machinehQl = machinehQl + "where  h.id =:inmachine";

    Query machinQuery = OBDal.getInstance().getSession().createQuery(machinehQl);
    if (pMachine != null && !pMachine.equals(""))
      machinQuery.setParameter("inmachine", pMachine.getId());
    MachineList = machinQuery.list();

    for (Object yList : YearList) {

      for (ADMonth MList : MonthList) {
        entity = new SalesProduction_Entity();
        entity.setYeart(yList.toString());
        String mSearch = MList.getSearchKey();
        if (mSearch.equals("10") || mSearch.equals("11") || mSearch.equals("12")) {
          mSearch = MList.getSearchKey();
        } else {
          mSearch = "0" + MList.getSearchKey();
        }

        for (PaperMachine machList : MachineList) {
          if (machList.getMashineName().equals("ماكينة 1")) {
            entity.setpMachine(machList.getMashineName());
          } else {
            entity.setpMachine2(machList.getMashineName());
          }

          String bdegreeasum = "SELECT count(h.pAPERJumboCode),sum(h.paperBakawieght) from MaterialMgmtShipmentInOutLine h ";

          bdegreeasum = bdegreeasum + " where h.paperMachine =:machList ";
          bdegreeasum = bdegreeasum
              + " and TO_CHAR(h.shipmentReceipt.movementDate, 'YYYY') =:yList ";
          bdegreeasum = bdegreeasum
              + " and TO_CHAR(h.shipmentReceipt.movementDate, 'MM') =:mSearch ";
          if (inpDateTo != null && !inpDateTo.equals(""))
            bdegreeasum = bdegreeasum + " and h.shipmentReceipt.movementDate <=:toDate ";
          /* if (inpdegree != null && !inpdegree.equals("")) */
          bdegreeasum = bdegreeasum

              + "and h.shipmentReceipt.id IN (select e.id  from MaterialMgmtShipmentInOut e where  e.movementType IN ('C-', 'C+') and e.logistic = 'N' and e.documentType.return=false)";

          Query bakraDsumquery = OBDal.getInstance().getSession().createQuery(bdegreeasum);

          bakraDsumquery.setParameter("machList", machList);
          bakraDsumquery.setParameter("yList", yList);
          bakraDsumquery.setParameter("mSearch", mSearch);
          if (inpDateTo != null && !inpDateTo.equals(""))
            bakraDsumquery.setParameter("toDate", toDate);

          List<Object[]> noOfBaka = bakraDsumquery.list();
          for (Object[] noInMachine : noOfBaka) {
            BigDecimal DS = new BigDecimal(noInMachine[0].toString());
            if (DS.equals(BigDecimal.ZERO)) {
              break;

            } else {
              if (machList.getMashineName().equals("ماكينة 1")) {
                entity.setNoinmach1(new BigDecimal(noInMachine[1].toString()));
              } else {
                entity.setNoinmach2(new BigDecimal(noInMachine[1].toString()));
              }
            }
          }
          String bakrasum = "select count(e.bakaracode),sum(e.bakaraweight) from paper_quality e   where e.sosataus IN('1' , '2')";
          bakrasum = bakrasum + " and  TO_CHAR(e.productiondate, 'YYYY')  =:yList";
          bakrasum = bakrasum + " and  TO_CHAR(e.productiondate, 'MM')  =:mSearch";

          if (inpDateTo != null && !inpDateTo.equals(""))
            bakrasum = bakrasum + " and  e.productiondate <=:todate ";

          bakrasum = bakrasum + " and  e.paperMachine =:machList ";

          Query bakrasumquery = OBDal.getInstance().getSession().createQuery(bakrasum);
          bakrasumquery.setParameter("yList", yList);
          bakrasumquery.setParameter("mSearch", mSearch);

          if (inpDateTo != null && !inpDateTo.equals(""))
            bakrasumquery.setParameter("todate", toDate);

          bakrasumquery.setParameter("machList", machList);

          List<Object[]> noOfBakaPro = bakrasumquery.list();
          for (Object[] noInMachinePro : noOfBakaPro) {
            BigDecimal DD = new BigDecimal(noInMachinePro[0].toString());
            if (DD.equals(BigDecimal.ZERO)) {
              break;

            } else {
              if (machList.getMashineName().equals("ماكينة 1")) {
                entity.setNoinmachinepro1(new BigDecimal(noInMachinePro[1].toString()));
              } else {
                entity.setNoinmachinepro2(new BigDecimal(noInMachinePro[1].toString()));
              }
            }
          }
          entity.setFromdate(inpDateFrom);
          entity.setTodate(inpDateTo);
          // String pType = MList.getSearchKey();

          entity.setLproducttype(MList.getName());
          entity.setProducttype(MList.getName());
          System.out.println(machList.getMashineName());

        }

        if (inmachine != null && !inmachine.equals("")) {
          entity.setMachine(pMachine.getMashineName());

        } else {
          entity.setMachine(null);
        }
        if (entity.getNoinmach1() != BigDecimal.ZERO || entity.getNoinmach2() != BigDecimal.ZERO)

        {
          entityList.add(entity);

        }

      }

    }

    entityArray = new SalesProduction_Entity[entityList.size()];
    entityList.toArray(entityArray);
    return entityArray;
  }
}