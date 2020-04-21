package com.ibox.paper.ad_callouts;

import javax.servlet.ServletException;

import org.hibernate.criterion.Restrictions;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;
import org.openbravo.erpCommon.ad_callouts.SimpleCallout;

import com.ibox.paper.data.PaperRefuse;
import com.ibox.paper.data.QualityDegree;

public class ValidateWasla extends SimpleCallout {

  @Override
  protected void execute(CalloutInfo info) throws ServletException {

    try {
      String wasla = info.getStringParameter("inpwasla", null);

      String qualityDegree = info.getStringParameter("inppaperQualityDegreeId", null);
      String firstRefuse = info.getStringParameter("inpfirstrefuse", null);
      QualityDegree degree = OBDal.getInstance().get(QualityDegree.class, qualityDegree);
      PaperRefuse refuse = OBDal.getInstance().get(PaperRefuse.class, firstRefuse);

      if (wasla.contains("3")) {
        final OBCriteria<QualityDegree> QDCriteria = OBDal.getInstance()
            .createCriteria(QualityDegree.class);
        QDCriteria.add(Restrictions.eq("commercialName", "DEG03"));
        final java.util.List<QualityDegree> deg = QDCriteria.list();
        final QualityDegree deg1 = deg.get(0);

        info.addResult("inppaperQualityDegreeId", deg1.getId());
        return;
      }
      if (wasla.equals("3") && (degree.getDegree().trim().equals("درجة اولي")
          || degree.getIdentifier().trim().contains("DEG01"))) {

        info.addResult("ERROR", "عدد الوصلات لا يتناسب مع درجة الجوده");
        return;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
