package com.ibox.paper.ad_callouts;

import javax.servlet.ServletException;

import org.hibernate.criterion.Expression;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;
import org.openbravo.erpCommon.ad_callouts.SimpleCallout;
import org.openbravo.model.common.businesspartner.BusinessPartner;

public class DefaultEntryproduct extends SimpleCallout {

  @Override
  protected void execute(CalloutInfo info) throws ServletException {
    // TODO Auto-generated method stub
    String bussinesspartnercode = "محلى";
    OBCriteria<BusinessPartner> businnessPartCriteria = OBDal.getInstance()
        .createCriteria(BusinessPartner.class);
    businnessPartCriteria
        .add(Expression.eq(BusinessPartner.PROPERTY_SEARCHKEY, bussinesspartnercode));

    BusinessPartner currentbussinesspartner = businnessPartCriteria.list().get(0);

    info.addResult("inpcBpartnerId", currentbussinesspartner);

  }

}
