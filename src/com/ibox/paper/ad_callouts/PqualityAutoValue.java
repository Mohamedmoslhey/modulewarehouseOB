package com.ibox.paper.ad_callouts;

import javax.servlet.ServletException;

import org.hibernate.criterion.Restrictions;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;
import org.openbravo.erpCommon.ad_callouts.SimpleCallout;
import org.openbravo.model.common.plm.ProductCategory;

import com.ibox.paper.data.QualityDegree;

public class PqualityAutoValue extends SimpleCallout {

		  @Override
		  protected void execute(CalloutInfo info) throws ServletException {

		    try {
		      String wasla = info.getStringParameter("inpwasla", null);

		      String qualityDegree = info.getStringParameter("inppaperQualityDegreeId", null);
		      QualityDegree degree = OBDal.getInstance().get(QualityDegree.class, qualityDegree);

		      if (wasla.contains("3")) {
		    	    final OBCriteria<QualityDegree> QDCriteria = OBDal.getInstance()
		    	            .createCriteria(QualityDegree.class);
		    	    QDCriteria.add(Restrictions.eq("searchKey", "DEG03"));
		    	        final java.util.List<QualityDegree> deg = QDCriteria.list();
		    	        final QualityDegree deg1 = deg.get(0);

		    	  info.addResult("inppaperQualityDegreeId", deg1.getId());
		      }

		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		  }

}
