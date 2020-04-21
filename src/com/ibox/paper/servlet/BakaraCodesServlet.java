package com.ibox.paper.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBDal;
import org.openbravo.erpCommon.utility.reporting.DocumentType;
import org.openbravo.erpCommon.utility.reporting.printing.PrintController;

import com.ibox.paper.data.ProductionQuality;
import com.ibox.paper.entity.BakaracodesEntity;
import com.ibox.paper.model.BakarcodesModel;

public class BakaraCodesServlet extends PrintController {

  public void init(ServletConfig config) {
    super.init(config);
    boolHist = false;
  }

  @SuppressWarnings("unchecked")
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    VariablesSecureApp vars = new VariablesSecureApp(request);
    DocumentType documentType = DocumentType.SALESORDER;
    String sessionValuePrefix = "Paper_RollCode";
    String strDocumentId = null;

    strDocumentId = vars.getSessionValue(sessionValuePrefix + ".inppaperQualityId_R");

    if (strDocumentId.equals(""))
      strDocumentId = vars.getSessionValue(sessionValuePrefix + ".inppaperQualityId");

    String IdString = strDocumentId.replace("'", "").replace("(", "").replace(")", "");

    ProductionQuality bakaracodet = OBDal.getInstance().get(ProductionQuality.class, IdString);

    BakaracodesEntity[] data = new BakarcodesModel().get_data(bakaracodet);

    String strReportName = "@basedesign@/com/ibox/paper/erpReports/Paper_RollCode.jrxml";

    HashMap<String, Object> parameters = new HashMap<String, Object>();
    // FieldProvider[] data=(FieldProvider[]) new
    // SimpleReportEntity[data2.length];

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    parameters.put("ad_client_id", OBContext.getOBContext().getCurrentClient().getId());
    parameters.put("clientName", OBContext.getOBContext().getCurrentClient().getName());
   // post(request, response, vars, documentType, sessionValuePrefix, strDocumentId);
    renderJR(vars, response, strReportName, "pdf", parameters, data, null);

  }

  public String getServletInfo() {
    return "Servlet that processes the print action";
  } // End of getServletInfo() method

}
