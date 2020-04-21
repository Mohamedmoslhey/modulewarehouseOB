package com.ibox.paper.erpReports;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openbravo.base.secureApp.HttpSecureAppServlet;
import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBDal;
import org.openbravo.data.FieldProvider;

import com.ibox.paper.data.ProductionQuality;
import com.ibox.paper.entity.BakaracodesEntity;
import com.ibox.paper.model.BakarcodesModel;

public class Paper_RollCode extends HttpSecureAppServlet {

  private static final long serialVersionUID = 1L;
  private static final ProductionQuality ProductionQuality = null;

  public void init(ServletConfig config) {
    super.init(config);
    boolHist = false;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    // VariablesSecureApp vars = new VariablesSecureApp(request);
    //
    // if (vars.commandIn("DEFAULT")) {
    // String strpaperQualityId = vars.getSessionValue("Paper_RollCode.inppaperQualityId_R");
    // if (strpaperQualityId.equals(""))
    // strpaperQualityId = vars.getSessionValue("Paper_RollCode.inppaperQualityId");
    // if (log4j.isDebugEnabled()) {
    // log4j.debug("+***********************: " + strpaperQualityId);
    // }
    // printPagePartePDF(response, vars, strpaperQualityId);
    // } else
    // pageError(response);
    VariablesSecureApp vars = new VariablesSecureApp(request);

    String sessionValuePrefix = "Paper_RollCode";
    String strDocumentId = null;

    strDocumentId = vars.getSessionValue(sessionValuePrefix + ".inppaperQualityId_R");
    if (strDocumentId.equals(""))
      strDocumentId = vars.getSessionValue(sessionValuePrefix + ".inppaperQualityId");

    // strDocumentId = request.getParameter(".inppaperQualityId");

    String IdString = strDocumentId.replace("'", "").replace("(", "").replace(")", "");

    ProductionQuality productionObject = OBDal.getInstance().get(ProductionQuality.class, IdString);

    ////////////////////////////////////
    BakaracodesEntity[] data = new BakarcodesModel().get_data(productionObject);

    String strReportName = "@basedesign@/com/ibox/paper/erpReports/Paper_RollCode.jrxml";

    HashMap<String, Object> parameters = new HashMap<String, Object>();
    // FieldProvider[] data=(FieldProvider[]) new
    // SimpleReportEntity[data2.length];

    List<FieldProvider> listData = new ArrayList<FieldProvider>();

    for (int x = 0; x < data.length; x++) {
      listData.add(data[x]);
    }
    Barcode_Image mi = new Barcode_Image();
    ProductionQuality prodQ = OBDal.getInstance().get(ProductionQuality.class, IdString);
    mi.createImage(prodQ.getBakaracode() + ".jpg", prodQ.getBakaracode());
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    BufferedImage image;
    String bakar = prodQ.getBakaracode();
    try {

      image = ImageIO.read(new FileInputStream("D:\\imagebarcode\\" + bakar + ".jpg"));

      // parameters.put("PAPER_QUALITY_ID", strpaperQualityId);
      parameters.put("logo", image);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    //////////////////
    parameters.put("ad_client_id", OBContext.getOBContext().getCurrentClient().getId());
    parameters.put("clientName", OBContext.getOBContext().getCurrentClient().getName());

    // -- adding it here and use this parameters

    renderJR(vars, response, strReportName, "pdf", parameters, data, null);
    //

    // post(request, response, vars, documentType, sessionValuePrefix, strDocumentId);

    //
  }

  // void printPagePartePDF(HttpServletResponse response, VariablesSecureApp vars,
  // String strpaperQualityId) throws IOException, ServletException {
  // if (log4j.isDebugEnabled())
  // log4j.debug("Output: Paper_RollCode - pdf");
  // JasperPrint jasperPrint;
  // String strReportName = "@basedesign@/com/ibox/paper/erpReports/Paper_RollCode.jrxml";
  // response.setHeader("Content-disposition", "inline; filename=RollCodeJR.pdf");
  // strpaperQualityId = strpaperQualityId.replace("(", "");
  // strpaperQualityId = strpaperQualityId.replace(")", "");
  // strpaperQualityId = strpaperQualityId.replace("\'", "");
  // HashMap<String, Object> parameters = new HashMap<String, Object>();
  // parameters.put("PAPER_QUALITY_ID", strpaperQualityId);
  // renderJR(vars, response, strReportName, "pdf", parameters, null, null);
  // }

  public String getServletInfo() {
    return "Servlet that presents the RptCOrders seeker";
  } // End of getServletInfo() method}
}