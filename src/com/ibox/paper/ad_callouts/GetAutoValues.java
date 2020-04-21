package com.ibox.paper.ad_callouts;

import java.math.BigDecimal;

import javax.servlet.ServletException;

import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.erpCommon.ad_callouts.SimpleCallout;

public class GetAutoValues extends SimpleCallout {

  @Override
  protected void execute(CalloutInfo info) throws ServletException {
    VariablesSecureApp vars = info.vars;

    BigDecimal tr1 = info.getBigDecimalParameter("inptryone");
    BigDecimal tr2 = info.getBigDecimalParameter("inptrytwo");
    BigDecimal tr3 = info.getBigDecimalParameter("inptrythree");
    BigDecimal tr4 = info.getBigDecimalParameter("inptryfour");
    BigDecimal tr5 = info.getBigDecimalParameter("inptryfive");
    BigDecimal tr6 = info.getBigDecimalParameter("inptrysix");
    BigDecimal tr7 = info.getBigDecimalParameter("inptryseven");
    BigDecimal tr8 = info.getBigDecimalParameter("inptryeight");
    BigDecimal tr9 = info.getBigDecimalParameter("inptrynine");
    BigDecimal tr10 = info.getBigDecimalParameter("inptryten");

    // -----------------------------------------------------------------------------------
    int count = 0;

    if (!tr1.equals(new BigDecimal(1)))
      count++;
    if (!tr2.equals(new BigDecimal(1)))
      count++;
    if (!tr3.equals(new BigDecimal(1)))
      count++;
    if (!tr4.equals(new BigDecimal(1)))
      count++;
    if (!tr5.equals(new BigDecimal(1)))
      count++;
    if (!tr6.equals(new BigDecimal(0)))
      count++;
    if (!tr7.equals(new BigDecimal(0)))
      count++;
    if (!tr8.equals(new BigDecimal(0)))
      count++;
    if (!tr9.equals(new BigDecimal(0)))
      count++;
    if (!tr10.equals(new BigDecimal(0)))
      count++;

    // -----------------------------------------------------------------------------------------
    BigDecimal total = (tr1.add(tr2).add(tr3).add(tr4).add(tr5).add(tr6).add(tr7).add(tr8).add(tr9)
        .add(tr10)).divide(new BigDecimal(count));

    // -----------------------------------------------------------------------------------------
    info.addResult("inpavrage", total.doubleValue());
  }

}
