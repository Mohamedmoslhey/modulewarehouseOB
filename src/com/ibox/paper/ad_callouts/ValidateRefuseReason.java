package com.ibox.paper.ad_callouts;

import javax.servlet.ServletException;

import org.openbravo.dal.service.OBDal;
import org.openbravo.erpCommon.ad_callouts.SimpleCallout;

import com.ibox.paper.data.PaperRefuse;

public class ValidateRefuseReason extends SimpleCallout {

  @Override
  protected void execute(CalloutInfo info) throws ServletException {
    try {

      String firstRef = info.getStringParameter("inpfirstrefuse", null);
      String SecondRef = info.getStringParameter("inpsecondrefuse", null);
      final PaperRefuse paperRefuse = OBDal.getInstance().get(PaperRefuse.class, firstRef);
      final PaperRefuse paperRefuse2 = OBDal.getInstance().get(PaperRefuse.class, SecondRef);
      if (!(paperRefuse2 == null || paperRefuse == null)) {
        if (paperRefuse.getReason().equals(paperRefuse2.getReason())) {

          info.addResult("ERROR", "سبب الفرز الاول يجب ان لا يساوي سبب الفرز التاني");
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
