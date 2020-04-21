// package name should follow the Java Package of the module
package com.ibox.paper.reports.gui.employment_report;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openbravo.base.secureApp.HttpSecureAppServlet;
import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.erpCommon.businessUtility.WindowTabs;
import org.openbravo.erpCommon.utility.ComboTableData;
import org.openbravo.erpCommon.utility.LeftTabsBar;
import org.openbravo.erpCommon.utility.NavigationBar;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.erpCommon.utility.ToolBar;
import org.openbravo.erpCommon.utility.Utility;
import org.openbravo.xmlEngine.XmlDocument;

import com.ibox.paper.data.PaperRefuse;

// extending HttpSecureAppServlet automatically takes care of the authentication
// and other session tasks
public class Daily_Report extends HttpSecureAppServlet {
  private static final long serialVersionUID = 1L;

  final BigDecimal blockSize = new BigDecimal(2500);// 2500
  public String fileFormate = "pdf";
  private File tmpDir;
  private File destinationDir;
  String firstresonfars = null; //
  String secondresonfars = null; //
  String qualitydegree;
  PaperRefuse currentfirstpaperRefuse;
  OBCriteria<PaperRefuse> PaperfirstCriteria;
  PaperRefuse currentsecondresonRefuse;
  OBError myMessage = new OBError();

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
          .readXmlTemplate("com/ibox/paper/reports/gui/employment_report/Daily_Report")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/reports/gui/employment_report/Daily_Report")
          .createXmlDocument();

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "DailyReports", false, "", "", "",
        false, "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.reports.gui.employment_report.Daily_Report");

      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "Daily_Report.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "Daily_Report.html",
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

    try {
      ComboTableData comboTableData = new ComboTableData(vars, this, "TABLE",
          "Paper_Shift_ShiftName", "Paper_Shift_ShiftName", "",
          Utility.getContext(this, vars, "#User_Org", "RepDaily"),
          Utility.getContext(this, vars, "#User_Client", "RepDaily"), 0);
      Utility.fillSQLParameters(this, vars, null, comboTableData, "RepDaily", null);
      xmlDocument.setData("NAME_reportProductType", "liststructure", comboTableData.select(false));
      comboTableData = null;
    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    try {
      ComboTableData comboTableData = new ComboTableData(vars, this, "TABLE",
          "Paper_Quality_Degree_Name", "Paper_Quality_Degree_Name", "",
          Utility.getContext(this, vars, "#User_Org", "RepDaily"),
          Utility.getContext(this, vars, "#User_Client", "RepDaily"), 0);
      Utility.fillSQLParameters(this, vars, null, comboTableData, "RepDaily", null);
      xmlDocument.setData("NAME_reportProductType", "liststructure", comboTableData.select(false));
      comboTableData = null;
    } catch (Exception ex) {
      throw new ServletException(ex);
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

  private void printFinishPage(HttpServletResponse response, HttpServletRequest request,
      VariablesSecureApp vars) throws IOException, ServletException {

    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    XmlDocument xmlDocument;
    if (vars.getLanguage().equals("en_US"))
      xmlDocument = xmlEngine.readXmlTemplate("com/ibox/paper/reports/gui/employment_report/finish")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine.readXmlTemplate("com/ibox/paper/reports/gui/employment_report/finish")
          .createXmlDocument();

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "PaySlip", false, "", "", "", false,
        "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.reports.gui.employment_report.Daily_Report");

      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "Daily_Report.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "Daily_Report.html",
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

  private void printReport(HttpServletResponse response, HttpServletRequest request,
      VariablesSecureApp vars, String adRoleID) {
    // TODO Auto-generated method stub

  }

  private void clearSession(HttpServletRequest request) {
    // TODO Auto-generated method stub

  }

  public String getServletInfo() {
    return "BatchSetProductCategory Servlet. This Servlet was made by Rok Lenardic";
  } // end of getServletInfo() method

}
