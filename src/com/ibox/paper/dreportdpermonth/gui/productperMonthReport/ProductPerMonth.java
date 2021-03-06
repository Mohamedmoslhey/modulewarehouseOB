// package name should follow the Java Package of the module
package com.ibox.paper.dreportdpermonth.gui.productperMonthReport;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openbravo.base.secureApp.HttpSecureAppServlet;
import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.dal.core.OBContext;
import org.openbravo.erpCommon.businessUtility.WindowTabs;
import org.openbravo.erpCommon.utility.LeftTabsBar;
import org.openbravo.erpCommon.utility.NavigationBar;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.erpCommon.utility.ToolBar;
import org.openbravo.xmlEngine.XmlDocument;

import com.ibox.paper.dreportdpermonth.entity.ProductPerMonth_Entity;
import com.ibox.paper.dreportdpermonth.model.ProductPerMonth_Model;

public class ProductPerMonth extends HttpSecureAppServlet {
  private static final long serialVersionUID = 1L;

  final BigDecimal blockSize = new BigDecimal(2500);// 2500
  public String fileFormate = "pdf";

  // all HTTP requests to this servlet enter here
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    VariablesSecureApp vars = new VariablesSecureApp(request);
    // ------------------- getting general data to all functions------------------------------
    String adOrgID = vars.getOrg();
    String sessionID = vars.getStringParameter("insession");
    String adRoleID = vars.getRole();

    // ----------------------------- in real case we will read block size from configuration table
    if (vars.commandIn("DEFAULT") || vars.commandIn("NEW")) {
      clearSession(request);
      printPageDataSheet(response, vars, null);
    }

    else if (vars.commandIn("PRINT")) {
      fileFormate = "pdf";
      printReport(response, request, vars, adRoleID);
    } //

    else if (vars.commandIn("XLS")) {
      fileFormate = "xls";
      printReport(response, request, vars, adRoleID);
    } //

    else if (vars.commandIn("CANCEL")) {

    } else
      pageError(response);
  }

  private void printPageDataSheet(HttpServletResponse response, VariablesSecureApp vars,
      OBError myMessage) throws IOException, ServletException {

    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    XmlDocument xmlDocument;
    if (vars.getLanguage().equals("en_US"))
      xmlDocument = xmlEngine
          .readXmlTemplate(
              "com/ibox/paper/dreportdpermonth/gui/productperMonthReport/ProductPerMonth_ar")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine
          .readXmlTemplate(
              "com/ibox/paper/dreportdpermonth/gui/productperMonthReport/ProductPerMonth_ar")
          .createXmlDocument();

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "ProductPerMonth", false, "", "", "",
        false, "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.dreportdeatilpermonth.gui.productperMonthReport.ProductDeatilPerMonth");

      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(),
          "CustomerStatisticsReport.html", classInfo.id, classInfo.type, strReplaceWith,
          tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "ProductPerMonth.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());

    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    if (myMessage != null) {
      xmlDocument.setParameter("messageType", myMessage.getType());
      xmlDocument.setParameter("messageTitle", myMessage.getTitle());
      xmlDocument.setParameter("messageMessage", myMessage.getMessage());
    }
    xmlDocument.setParameter("dateFromdisplayFormat", vars.getSessionValue("#AD_SqlDateFormat"));
    xmlDocument.setParameter("dateFromsaveFormat", vars.getSessionValue("#AD_SqlDateFormat"));

    xmlDocument.setParameter("dateTodisplayFormat", vars.getSessionValue("#AD_SqlDateFormat"));
    xmlDocument.setParameter("dateTosaveFormat", vars.getSessionValue("#AD_SqlDateFormat"));
    xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    out.println(xmlDocument.print());
    out.close();
  }// print page data sheet function

  // ------------------------------------ define function that finialize the report
  // printing-----------------

  private void printFinishPage(HttpServletResponse response, HttpServletRequest request,
      VariablesSecureApp vars) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();

    XmlDocument xmlDocument;
    if (vars.getLanguage().equals("en_US"))
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/dreportdpermonth/gui/productperMonthReport/finish_ar")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/dreportdpermonth/gui/productperMonthReport/finish_ar")
          .createXmlDocument();

    // ------------ manage blocks

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "sessionSummation", false, "", "", "",
        false, "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.dreportdpermonth.gui.productperMonthReport.ProductPerMonth");
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "ProductPerMonth.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "ProductPerMonth.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());

    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    if (vars.getLanguage().equals("en_US")) {
      xmlDocument.setParameter("messageType", "Success");
      xmlDocument.setParameter("messageTitle", "Printing Completed");
      xmlDocument.setParameter("messageMessage",
          "Printing process was finished ther are no blocks to print");
    } else {
      xmlDocument.setParameter("messageType", "Success");
      xmlDocument.setParameter("messageTitle", "أنتهاء الطباعة");
      xmlDocument.setParameter("messageMessage", "لقد انتهت طباعة التقرير بالكامل");
    }
    xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    out.println(xmlDocument.print());
    out.close();
  }// print page data sheet function

  public void printReport(HttpServletResponse response, HttpServletRequest request,
      VariablesSecureApp vars, String adRoleID) throws ServletException {
    HttpSession session = request.getSession();

    String datefrom = request.getParameter("inpDateFrom");
    String dateto = request.getParameter("inpDateTo");
    String srreason = request.getParameter("inpsecondreason");
    String frreason = request.getParameter("inpfirstreason");

    String lang = vars.getLanguage();
    String strReportName = null;

    if (vars.getLanguage().equals("en_US"))
      strReportName = "@basedesign@/com/ibox/paper/dreportdpermonth/files/ProductPerMonth_en.jrxml";
    else
      // /two/employeeperformance/employee_performance_en.jrxml
      strReportName = "@basedesign@/com/ibox/paper/dreportdpermonth/files/ProductPerMonth_en.jrxml";

    ProductPerMonth_Entity[] entityArray = new ProductPerMonth_Model().get_report_data(frreason,
        srreason, datefrom, dateto);

    HashMap<String, Object> parameters = new HashMap<String, Object>();
    // FieldProvider[] data=(FieldProvider[]) new
    // SimpleReportEntity[data2.length];

    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    parameters.put("ad_client_id", OBContext.getOBContext().getCurrentClient().getId());
    parameters.put("clientName", OBContext.getOBContext().getCurrentClient().getName());

    renderJR(vars, response, strReportName, fileFormate, parameters, entityArray, null);

    // ------------------------- call renderJR function--------------------------
  }// printReport
   // ------------------------------- define function that update session
   // ------------------------------------

  public void IntalizeSessionVaraibles(HttpServletRequest request, BigDecimal totalBlockNumber,
      BigDecimal currentBlock, BigDecimal percentagePrinted, BigDecimal startRecord) {
    HttpSession session = request.getSession();
    session.setAttribute("sessionintalized", true);
    BigDecimal sessiontatal = (BigDecimal) session.getAttribute("sessiontatal");
    if (sessiontatal == null)
      session.setAttribute("sessiontatal", totalBlockNumber);

    BigDecimal sessioncurrent = (BigDecimal) session.getAttribute("sessioncurrent");
    if (sessioncurrent == null)
      session.setAttribute("sessioncurrent", currentBlock);

    BigDecimal sessionpercentage = (BigDecimal) session.getAttribute("sessionpercentage");
    if (sessionpercentage == null)
      session.setAttribute("sessionpercentage", percentagePrinted);

    BigDecimal sessionstartrecord = (BigDecimal) session.getAttribute("sessionstartrecord");
    if (sessionstartrecord == null)
      session.setAttribute("sessionstartrecord", startRecord);

    session.setAttribute("sessionfinish", false);
  }// updatePaginInSession

  public void updateSessionVaraibles(HttpServletRequest request, BigDecimal currentBlock,
      BigDecimal percentage, BigDecimal startRecord) {
    HttpSession session = request.getSession();
    if (percentage != null)
      session.setAttribute("sessionpercentage", percentage);
    if (currentBlock != null)
      session.setAttribute("sessioncurrent", currentBlock);
    if (startRecord != null)
      session.setAttribute("sessionstartrecord", startRecord);
  }// updateSessionVaraibles

  public void clearSession(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.removeAttribute("sessionintalized");
    session.removeAttribute("sessiontatal");
    session.removeAttribute("sessioncurrent");
    session.removeAttribute("sessionpercentage");
    session.removeAttribute("sessionstartrecord");
    session.removeAttribute("sessionfinish");
  }// clearSession

  public String getServletInfo() {
    return "BatchSetProductCategory Servlet. This Servlet was made by Rok Lenardic";
  } // end of getServletInfo() method

}
