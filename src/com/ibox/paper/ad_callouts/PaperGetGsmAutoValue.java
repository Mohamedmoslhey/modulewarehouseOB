package com.ibox.paper.ad_callouts;

import java.math.BigDecimal;

import javax.servlet.ServletException;

public class PaperGetGsmAutoValue extends GetAutoValues {

  @Override
  protected void execute(CalloutInfo info) throws ServletException {
    // TODO Auto-generated method stub
    BigDecimal tr1 = info.getBigDecimalParameter("inpsomcone");
    BigDecimal tr2 = info.getBigDecimalParameter("inpsomctwo");
    BigDecimal tr3 = info.getBigDecimalParameter("inpsomcthree");
    BigDecimal tr4 = info.getBigDecimalParameter("inpsomcfour");
    BigDecimal tr5 = info.getBigDecimalParameter("inpsomcfive");
    BigDecimal tr6 = info.getBigDecimalParameter("inpsomcsix");
    BigDecimal tr7 = info.getBigDecimalParameter("inpsomcseven");
    BigDecimal tr8 = info.getBigDecimalParameter("inpsomceigth");
    BigDecimal tr9 = info.getBigDecimalParameter("inpsomcnine");
    BigDecimal tr10 = info.getBigDecimalParameter("inpsomcten");

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
    info.addResult("inpsomcaverage", total.doubleValue());
  }

}
