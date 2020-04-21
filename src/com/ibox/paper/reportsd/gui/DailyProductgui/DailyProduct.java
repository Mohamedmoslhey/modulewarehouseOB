// package name should follow the Java Package of the module
package com.ibox.paper.reportsd.gui.DailyProductgui;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openbravo.base.secureApp.HttpSecureAppServlet;
import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.dal.core.OBContext;
import org.openbravo.data.FieldProvider;
import org.openbravo.erpCommon.businessUtility.WindowTabs;
import org.openbravo.erpCommon.utility.ComboTableData;
import org.openbravo.erpCommon.utility.LeftTabsBar;
import org.openbravo.erpCommon.utility.NavigationBar;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.erpCommon.utility.ToolBar;
import org.openbravo.erpCommon.utility.Utility;
import org.openbravo.xmlEngine.XmlDocument;

import com.ibox.paper.reportsd.entity.DailyProductEntity;
import com.ibox.paper.reportsd.model.DailyProductModel;

// extending HttpSecureAppServlet automatically takes care of the authentication
// and other session tasks
public class DailyProduct extends HttpSecureAppServlet {
  private static final long serialVersionUID = 1L;

  final BigDecimal blockSize = new BigDecimal(2500);
  String fileFormat = "xls";

  // all HTTP requests to this servlet enter here
  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    try {
      VariablesSecureApp vars = new VariablesSecureApp(request);

      // ------------------- getting general data to all
      // functions------------------------------
      String adOrgID = vars.getOrg();
      String sessionID = vars.getStringParameter("insession");
      String adRoleID = vars.getRole();

      // ----------------------------- in real case we will read block size
      // from configuration table
      if (vars.commandIn("DEFAULT") || vars.commandIn("NEW")) {

        printPageDataSheet(response, vars, null);
      } else if (vars.commandIn("PRINT")) {

        printReport(response, request, vars, adRoleID);

      } else if (vars.commandIn("PREVIEW")) {

        getNextBlock(response, request, vars);

      } else
        pageError(response);
    } catch (Exception e) {
      e.printStackTrace();

    }
  }

  private void printPageDataSheet(HttpServletResponse response, VariablesSecureApp vars,
      OBError errorMessage) throws IOException, ServletException {

    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    XmlDocument xmlDocument;
    if (vars.getLanguage().equals("en_US"))
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/reportsd/gui/DailyProductgui/DailyProduct")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/reportsd/gui/DailyProductgui/DailyProduct")
          .createXmlDocument();

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "EmploymentReport", false, "", "", "",
        false, "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.reportsd.gui.DailyProductgui.DailyProduct");
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "DailyProduct.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "DailyProduct.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());

    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    // ----------------------From ORG-------------------------------------

    String emptype = "";

    try {
      ComboTableData comboTableData = new ComboTableData(vars, this, "TABLE", "",
          "B348771D9F37425A9E58B01C104A3203", "",
          Utility.getContext(this, vars, "#User_Org", "EmploymentReport"),
          Utility.getContext(this, vars, "#User_Client", "EmploymentReport"), 0);
      Utility.fillSQLParameters(this, vars, null, comboTableData, "EmploymentReport", null);
      xmlDocument.setData("NAME_EmpTypeDownList", "liststructure", comboTableData.select(false));

      FieldProvider data[] = comboTableData.select(false);
      if (data.length > 0)
        emptype = data[0].getField("ID");

      comboTableData = null;
    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    String periodID = "";
    try {
      ComboTableData comboTableData = new ComboTableData(vars, this, "TABLE", "",
          "56D8347A6A71411DBBA24FA35E74E432", "",
          Utility.getContext(this, vars, "#User_Org", "EmploymentReport"),
          Utility.getContext(this, vars, "#User_Client", "EmploymentReport"), 0);
      Utility.fillSQLParameters(this, vars, null, comboTableData, "EmploymentReport", null);
      xmlDocument.setData("NAME_ReportPeriodDownList", "liststructure",
          comboTableData.select(false));

      FieldProvider data[] = comboTableData.select(false);
      if (data.length > 0)
        periodID = data[0].getField("ID");

      comboTableData = null;
    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    if (errorMessage != null) {
      xmlDocument.setParameter("messageType", errorMessage.getType());
      xmlDocument.setParameter("messageTitle", errorMessage.getTitle());
      xmlDocument.setParameter("messageMessage", errorMessage.getMessage());
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
  }

  private void getNextBlock(HttpServletResponse response, HttpServletRequest request,
      VariablesSecureApp vars) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();

    HttpSession session = request.getSession();

    String fromdate = vars.getStringParameter("inpperiod");
    String EmpType = vars.getStringParameter("emptype");
    String fromemp = vars.getStringParameter("inpfromemp");
    String toemp = vars.getStringParameter("inptoemp");
    session.setAttribute("fromdate", fromdate);
    session.setAttribute("EmpType", EmpType);
    session.setAttribute("fromemp", fromemp);
    session.setAttribute("toemp", toemp);

    XmlDocument xmlDocument;
    if (vars.getLanguage().equals("en_US"))
      xmlDocument = xmlEngine.readXmlTemplate("com/ibox/paper/reportsd/gui/DailyProductgui/preview")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine.readXmlTemplate("com/ibox/paper/reportsd/gui/DailyProductgui/preview")
          .createXmlDocument();
    // ------------ manage blocks

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "EmploymentReport", false, "", "", "",
        false, "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.reportsd.gui.DailyProductgui.DailyProduct");
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "Employment.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "Employment.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());

    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    out.println(xmlDocument.print());
    out.close();
  }

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
  }

  public void updateSessionVaraibles(HttpServletRequest request, BigDecimal currentBlock,
      BigDecimal percentage, BigDecimal startRecord) {
    HttpSession session = request.getSession();
    if (percentage != null)
      session.setAttribute("sessionpercentage", percentage);
    if (currentBlock != null)
      session.setAttribute("sessioncurrent", currentBlock);
    if (startRecord != null)
      session.setAttribute("sessionstartrecord", startRecord);
  }

  private void printFinishPage(HttpServletResponse response, HttpServletRequest request,
      VariablesSecureApp vars) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();

    XmlDocument xmlDocument;
    if (vars.getLanguage().equals("en_US"))
      xmlDocument = xmlEngine.readXmlTemplate("com/ibox/paper/reportsd/gui/DailyProductgui/finish")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine.readXmlTemplate("com/ibox/paper/reportsd/gui/DailyProductgui/finish")
          .createXmlDocument();
    // ------------ manage blocks
    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "sessionSummation", false, "", "", "",
        false, "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.reportsd.gui.DailyProductgui.DailyProduct");
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "DailyProduct.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "DailyProduct.html",
          strReplaceWith);
      String test = xmlDocument.print();
      System.out.print(test);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());

    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    xmlDocument.setParameter("messageType", "Success");
    if (vars.getLanguage().equals("en_US")) {
      xmlDocument.setParameter("messageTitle", "Printing Completed");
      xmlDocument.setParameter("messageMessage",
          "Printing process was finished ther are no blocks to print");
    } else {
      xmlDocument.setParameter("messageTitle", "أنتهاء الطباعة");
      xmlDocument.setParameter("messageMessage", "لقد انتهت طباعة التقرير بالكامل");
    }

    xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    out.println(xmlDocument.print());
    out.close();
  }

  public void printReport(HttpServletResponse response, HttpServletRequest request,
      VariablesSecureApp vars, String adRoleID) {
    try {

      String lang = vars.getLanguage();
      String strReportName = null;
      HttpSession session = request.getSession();

      String fromdate = (String) session.getAttribute("fromdate");
      String emptype = (String) session.getAttribute("EmpType");
      String fromemp = (String) session.getAttribute("fromemp");
      String toemp = (String) session.getAttribute("toemp");

      if (vars.getLanguage().equals("en_US"))
        strReportName = "@basedesign@/com/ibox/paper/reportsd/files/DailyProductfiles/Daily_Production_Report.jrxml";
      else
        strReportName = "@basedesign@/com/ibox/paper/reportsd/files/DailyProductfiles/Daily_Production_Report.jrxml";

      DailyProductEntity[] entityArray = new DailyProductModel().PrintStudentModel(fromdate,
          emptype, fromemp, toemp);

      if (!entityArray.equals(null)) {
        HashMap<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("ad_client_id", OBContext.getOBContext().getCurrentClient().getId());
        parameters.put("ad_org_id", OBContext.getOBContext().getCurrentOrganization().getId());
        parameters.put("clientName", OBContext.getOBContext().getCurrentClient().getName());

        renderJR(vars, response, strReportName, "pdf", parameters, entityArray, null);
      }

    } catch (Exception e) {
      e.printStackTrace();

    }
  }

  // ------------------------------------ define function that finialize the
  // report printing-----------------

  public String getServletInfo() {
    return "BatchSetProductCategory Servlet. This Servlet was made by Rok Lenardic";
  } // end of getServletInfo() method
}
