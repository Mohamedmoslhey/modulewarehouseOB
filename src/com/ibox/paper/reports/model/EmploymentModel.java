package com.ibox.paper.reports.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.openbravo.dal.service.OBDal;
import org.openbravo.model.common.businesspartner.BusinessPartner;
import org.openbravo.model.common.enterprise.Organization;

import com.ibox.paper.reports.entity.EmploymentEntity;

public class EmploymentModel {

  public EmploymentEntity[] PrintStudentModel(String fromdate, String emptype, String fromemp,
      String toemp) {
    BusinessPartner fromEmp = null;
    BusinessPartner toEmp = null;
    PYLPeriod period = null;
    final ReportService reports = new ReportService();

    final int orgValueLen = (new Organization()).getEntity().getProperty("searchKey")
        .getFieldLength();
    String femp = "select emp.c_bpartner_id from pyl_empprofile emp,c_bpartner cb where emp.c_bpartner_id=cb.c_bpartner_id order by cb.value  limit 1";
    String temp = "select emp.c_bpartner_id from pyl_empprofile emp,c_bpartner cb where emp.c_bpartner_id=cb.c_bpartner_id order by cb.value DESC limit 1";
    Query getfromemp = OBDal.getInstance().getSession().createSQLQuery(femp);
    Query gettoemp = OBDal.getInstance().getSession().createSQLQuery(temp);

    if (fromemp != "") {
      fromEmp = OBDal.getInstance().get(BusinessPartner.class, fromemp);
    } else
      fromEmp = OBDal.getInstance().get(BusinessPartner.class, getfromemp.list().get(0));

    if (toemp != "") {
      toEmp = OBDal.getInstance().get(BusinessPartner.class, toemp);
    } else
      toEmp = OBDal.getInstance().get(BusinessPartner.class, gettoemp.list().get(0));

    if (fromdate != null) {
      period = OBDal.getInstance().get(PYLPeriod.class, fromdate);
    }

    // fulfill the report //
    String hql = "";
    if (fromemp != "" && toemp != "") {

      if (period.getName().contains("_JANUARY")) {

        hql = "  select emp.c_bpartner_id,idnumber,hiringdate,birthdate,basic1.b1+basic2.b2,unclaim.un,pro.prod  from "
            + "        (select c_bpartner_id,sum(pf.payvalue) as b1 from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name = '"
            + period.getName().toString() + "'"
            + "                 and pf.pyl_payelement_id='3EED0D27030F409F874A161ADD205B3B' group by pf.pyl_payelement_id,pf.c_bpartner_id ) basic1 "
            + "        right join  "
            + "        (select c_bpartner_id,sum(pf.payvalue) as b2 from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name = '"
            + period.getName().toString() + "'"
            + "              and pf.pyl_payelement_id='37E45935C0174FB8B202182CB29298A6' group by pf.pyl_payelement_id,pf.c_bpartner_id ) basic2 "
            + "        on basic1.c_bpartner_id = basic2.c_bpartner_id " + "        right join "
            + "        (select c_bpartner_id,sum(pf.payvalue) as un from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name = '"
            + period.getName().toString() + "'"
            + "                 and pf.pyl_payelement_id='FFFF2B5314344DF49D3DE90A3D0E1D56' group by pf.pyl_payelement_id,pf.c_bpartner_id ) unclaim "
            + "          on basic1.c_bpartner_id = unclaim.c_bpartner_id      "
            + "         right join "
            + "        (select c_bpartner_id,sum(pf.payvalue) as prod from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name = '"
            + period.getName().toString() + "'"
            + "             and pf.pyl_payelement_id='0B6EC83EF4A04BF2BB66965E340549E4' group by pf.pyl_payelement_id,pf.c_bpartner_id  ) pro "
            + "            on basic1.c_bpartner_id = pro.c_bpartner_id" + "          right join "
            + "                pyl_empprofile emp "
            + "            on basic1.c_bpartner_id = emp.c_bpartner_id "
            + "              right join "
            + "              c_bpartner cb  on basic1.c_bpartner_id = cb.c_bpartner_id  "
            + "        where emp.employeetype='" + emptype + "'" + "    and cb.value >= '"
            + fromEmp.getSearchKey() + "' and cb.value <= '" + toEmp.getSearchKey()
            + "'          order by cb.value";

      } else if (period.getName().contains("_JULY")) {

        String[] spliter = period.getName().split("_");
        String year = spliter[0];

        hql = "  select emp.c_bpartner_id,idnumber,hiringdate,birthdate,basic1.b1+basic2.b2,unclaim.un,pro.prod  from "
            + "        (select c_bpartner_id,sum(pf.payvalue) as b1 from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name = '"
            + period.getName().toString() + "' "

            + "  and pf.pyl_payelement_id='3EED0D27030F409F874A161ADD205B3B' group by pf.pyl_payelement_id,pf.c_bpartner_id ) basic1 "
            + "        right join  "
            + "        (select c_bpartner_id,sum(pf.payvalue) as b2 from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name = '"
            + period.getName().toString() + "' "

            + "              and pf.pyl_payelement_id='37E45935C0174FB8B202182CB29298A6' group by pf.pyl_payelement_id,pf.c_bpartner_id ) basic2 "
            + "        on basic1.c_bpartner_id = basic2.c_bpartner_id " + "        right join "
            + "        (select c_bpartner_id,sum(pf.payvalue) as un from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name ='"
            + period.getName().toString() + "' "

            + "                 and pf.pyl_payelement_id='FFFF2B5314344DF49D3DE90A3D0E1D56' group by pf.pyl_payelement_id,pf.c_bpartner_id ) unclaim "
            + "          on basic1.c_bpartner_id = unclaim.c_bpartner_id      "
            + "         right join "
            + "        (select c_bpartner_id,sum(pf.payvalue) as prod from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name ='"
            + period.getName().toString() + "' "

            + "             and pf.pyl_payelement_id='0B6EC83EF4A04BF2BB66965E340549E4' group by pf.pyl_payelement_id,pf.c_bpartner_id  ) pro "
            + "            on basic1.c_bpartner_id = pro.c_bpartner_id" + "          right join "
            + "                pyl_empprofile emp "
            + "            on basic1.c_bpartner_id = emp.c_bpartner_id "
            + "              right join "
            + "              c_bpartner cb  on basic1.c_bpartner_id = cb.c_bpartner_id  "
            + "        where emp.employeetype='" + emptype + "'" + "    and cb.value >= '"
            + fromEmp.getSearchKey() + "' and cb.value <= '" + toEmp.getSearchKey()
            + " '             order by cb.value";

      }
    } else {
      if (period.getName().contains("_JANUARY")) {

        hql = "  select emp.c_bpartner_id,idnumber,hiringdate,birthdate,basic1.b1+basic2.b2,unclaim.un,pro.prod  from "
            + "        (select c_bpartner_id,sum(pf.payvalue) as b1 from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name = '"
            + period.getName().toString() + "'"
            + "                 and pf.pyl_payelement_id='3EED0D27030F409F874A161ADD205B3B' group by pf.pyl_payelement_id,pf.c_bpartner_id ) basic1 "
            + "        right join  "
            + "        (select c_bpartner_id,sum(pf.payvalue) as b2 from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name = '"
            + period.getName().toString() + "'"
            + "              and pf.pyl_payelement_id='37E45935C0174FB8B202182CB29298A6' group by pf.pyl_payelement_id,pf.c_bpartner_id ) basic2 "
            + "        on basic1.c_bpartner_id = basic2.c_bpartner_id " + "        right join "
            + "        (select c_bpartner_id,sum(pf.payvalue) as un from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name = '"
            + period.getName().toString() + "'"
            + "                 and pf.pyl_payelement_id='FFFF2B5314344DF49D3DE90A3D0E1D56' group by pf.pyl_payelement_id,pf.c_bpartner_id ) unclaim "
            + "          on basic1.c_bpartner_id = unclaim.c_bpartner_id      "
            + "         right join "
            + "        (select c_bpartner_id,sum(pf.payvalue) as prod from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name = '"
            + period.getName().toString() + "'"
            + "             and pf.pyl_payelement_id='0B6EC83EF4A04BF2BB66965E340549E4' group by pf.pyl_payelement_id,pf.c_bpartner_id  ) pro "
            + "            on basic1.c_bpartner_id = pro.c_bpartner_id" + "          right join "
            + "                pyl_empprofile emp "
            + "            on basic1.c_bpartner_id = emp.c_bpartner_id "
            + "              right join "
            + "              c_bpartner cb  on basic1.c_bpartner_id = cb.c_bpartner_id  "
            + "        where emp.employeetype='" + emptype + "'"

            + "          order by cb.value";

      } else if (period.getName().contains("_JULY")) {

        String[] spliter = period.getName().split("_");
        String year = spliter[0];

        hql = "  select emp.c_bpartner_id,idnumber,hiringdate,birthdate,basic1.b1+basic2.b2,unclaim.un,pro.prod  from "
            + "        (select c_bpartner_id,sum(pf.payvalue) as b1 from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name = '"
            + period.getName().toString() + "' "

            + "                 and pf.pyl_payelement_id='3EED0D27030F409F874A161ADD205B3B' group by pf.pyl_payelement_id,pf.c_bpartner_id ) basic1 "
            + "        right join  "
            + "        (select c_bpartner_id,sum(pf.payvalue) as b2 from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name = '"
            + period.getName().toString() + "' "

            + "              and pf.pyl_payelement_id='37E45935C0174FB8B202182CB29298A6' group by pf.pyl_payelement_id,pf.c_bpartner_id ) basic2 "
            + "        on basic1.c_bpartner_id = basic2.c_bpartner_id " + "        right join "
            + "        (select c_bpartner_id,sum(pf.payvalue) as un from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name = '"
            + period.getName().toString() + "' "

            + "                 and pf.pyl_payelement_id='FFFF2B5314344DF49D3DE90A3D0E1D56' group by pf.pyl_payelement_id,pf.c_bpartner_id ) unclaim "
            + "          on basic1.c_bpartner_id = unclaim.c_bpartner_id      "
            + "         right join "
            + "        (select c_bpartner_id,sum(pf.payvalue) as prod from pyl_fact_pay_view pf,pyl_period pp where  pf.pyl_period_id = pp.pyl_period_id and pp.name = '"
            + period.getName().toString() + "' "

            + "             and pf.pyl_payelement_id='0B6EC83EF4A04BF2BB66965E340549E4' group by pf.pyl_payelement_id,pf.c_bpartner_id  ) pro "
            + "            on basic1.c_bpartner_id = pro.c_bpartner_id" + "          right join "
            + "                pyl_empprofile emp "
            + "            on basic1.c_bpartner_id = emp.c_bpartner_id "
            + "              right join "
            + "              c_bpartner cb  on basic1.c_bpartner_id = cb.c_bpartner_id  "
            + "        where emp.employeetype='" + emptype + "'"
            + "              order by cb.value";

      }
    }

    Query getresult = OBDal.getInstance().getSession().createSQLQuery(hql);

    List<Object[]> pay_list = getresult.list();

    List<EmploymentEntity> EmploymentEntityList = new ArrayList<EmploymentEntity>();

    String[] todatesplit, hiredatesplit;

    for (Object[] report : pay_list) {

      EmploymentEntity report_entity = new EmploymentEntity();

      report_entity.setFromemp(fromEmp.getName());
      report_entity.setToemp(toEmp.getName());
      report_entity.setFromdate(period.getName().toString());

      BusinessPartner Emp = OBDal.getInstance().get(BusinessPartner.class, report[0]);
      report_entity.setNumber(Emp.getSearchKey());
      report_entity.setName(Emp.getName());
      report_entity.setOrgcode(Emp.getOrganization().getSearchKey());
      if (report[1] == null || report[1] == "") {
        report_entity.setNationalid(" ");
      } else
        report_entity.setNationalid(report[1] + "");
      report_entity.setHiredate(report[2] + "");

      Date current = new Date();
      Date hire = (Date) report[2];
      report_entity.setPeriod((current.getYear() - hire.getYear()) + " years");
      report_entity.setBirthdate(report[3] + "");
      report_entity.setBasicsalary(report[4] + "");
      if (report[5] == null & report[6] == null || report[5] == "" & report[6] == "") {
        report_entity.setAllreward("0.0");
        report_entity.setSpecialreward("0.0");
        report_entity.setTotal((Double.parseDouble(report[4] + "") + 0.0 + 0.0 + ""));

      } else {
        report_entity.setAllreward(report[6] + "");
        report_entity.setSpecialreward(report[5] + "");
        report_entity.setTotal((Double.parseDouble(report[4] + "")
            + Double.parseDouble(report[5] + "") + Double.parseDouble(report[6] + "")) + "");

      }

      EmploymentEntityList.add(report_entity);
    }

    EmploymentEntity[] EmploymentEntityArray = new EmploymentEntity[EmploymentEntityList.size()];

    EmploymentEntityList.toArray(EmploymentEntityArray);

    return EmploymentEntityArray;

  }

}
