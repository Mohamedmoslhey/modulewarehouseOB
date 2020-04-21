// package name should follow the Java Package of the module
package com.ibox.paper.dataloading.roll_sheet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.hibernate.criterion.Expression;
import org.openbravo.base.secureApp.HttpSecureAppServlet;
import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;
import org.openbravo.erpCommon.businessUtility.WindowTabs;
import org.openbravo.erpCommon.utility.LeftTabsBar;
import org.openbravo.erpCommon.utility.NavigationBar;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.erpCommon.utility.ToolBar;
import org.openbravo.xmlEngine.XmlDocument;

import com.ibox.paper.data.OpeningBalance;
import com.ibox.paper.data.QualityDegree;

import jxl.Sheet;
import jxl.Workbook;

public class Roll_sheet extends HttpSecureAppServlet {
  private static final long serialVersionUID = 1L;

  final BigDecimal blockSize = new BigDecimal(2500);// 2500
  public String fileFormate = "pdf";
  private File tmpDir;
  private File destinationDir;
  String rollCode;
  String rollType;
  String weight;
  String width;
  String qotr;
  String gsm;
  String degreeType;
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

      printPageDataSheet(response, vars, null);
    } else if (vars.commandIn("UPLOAD")) {

      print_aftert_upload(response, request, vars);
    } // else if

    else
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
      xmlDocument = xmlEngine.readXmlTemplate("com/ibox/paper/dataloading/roll_sheet/Roll_sheet")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine.readXmlTemplate("com/ibox/paper/dataloading/roll_sheet/Roll_sheet")
          .createXmlDocument();

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "PaySlip", false, "", "", "", false,
        "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.dataloading.Roll_sheet.Roll_sheet");

      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "Roll_sheet.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "Roll_sheet.html",
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

  private void print_aftert_upload(HttpServletResponse response, HttpServletRequest request,
      VariablesSecureApp vars) throws IOException, ServletException {

    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    XmlDocument xmlDocument;
    if (vars.getLanguage().equals("en_US"))
      xmlDocument = xmlEngine.readXmlTemplate("com/ibox/paper/dataloading/roll_sheet/Roll_sheet")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine.readXmlTemplate("com/ibox/paper/dataloading/roll_sheet/Roll_sheet")
          .createXmlDocument();

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "PaySlip", false, "", "", "", false,
        "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.dataloading.roll_sheet.Roll_sheet");

      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "Roll_sheet.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "Roll_sheet.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());

    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    try {

      FileItem excelFile = vars.getMultiFile("fileToUpload");

      InputStream excelStream = excelFile.getInputStream();

      Workbook excelWorkBook = Workbook.getWorkbook(excelStream);

      Sheet sheet = excelWorkBook.getSheet(0);

      int rows = sheet.getRows();

      for (int x = 1; x < rows; x++) {

        rollCode = sheet.getCell(0, x).getContents();
        rollType = sheet.getCell(1, x).getContents(); // لازم تكون رقم عشان يقراها صح
        weight = sheet.getCell(2, x).getContents(); //
        width = sheet.getCell(3, x).getContents(); //
        qotr = sheet.getCell(4, x).getContents(); //
        gsm = sheet.getCell(5, x).getContents(); //
        degreeType = sheet.getCell(6, x).getContents(); //

        // ---check if code inserted before-----
        OBCriteria<OpeningBalance> OpeningBalanceCriteria = OBDal.getInstance()
            .createCriteria(OpeningBalance.class);
        OpeningBalanceCriteria.add(Expression.eq(OpeningBalance.PROPERTY_ROLLCODE, rollCode));
        List<OpeningBalance> OpeningBalanceList = OpeningBalanceCriteria.list();
        if (OpeningBalanceList.size() > 0) {
          throw new Exception("@لقد ادخلت هذا الكود سابقا !!!@" + rollCode);
        }

        // --- inset new bakra
        OpeningBalance newbakara = new OpeningBalance();
        newbakara.setClient(OBContext.getOBContext().getCurrentClient());
        newbakara.setOrganization(OBContext.getOBContext().getCurrentOrganization());
        newbakara.setActive(true);
        newbakara.setCreationDate(new Date());
        newbakara.setCreatedBy(OBContext.getOBContext().getUser());
        newbakara.setUpdated(new Date());
        newbakara.setUpdatedBy(OBContext.getOBContext().getUser());
        newbakara.setGsm(new Long(gsm));
        newbakara.setWeight(new Long(weight));
        newbakara.setWidth(new Long(width));
        newbakara.setQotr(new Long(qotr));
        newbakara.setRollCode(rollCode);
        newbakara.setRollType(rollType);
        // --get degree type
        OBCriteria<QualityDegree> QualityDegreeCriteria = OBDal.getInstance()
            .createCriteria(QualityDegree.class);
        QualityDegreeCriteria.add(Expression.eq(QualityDegree.PROPERTY_COMMERCIALNAME, degreeType));

        QualityDegree currentdegree = QualityDegreeCriteria.list().get(0);

        newbakara.setDegreeType(currentdegree);

        OBDal.getInstance().getSession().save(newbakara);
        // -----------------------------------------------------------
        // String sqlQuery2 = "INSERT INTO paper_opening_balance( paper_opening_balance_id ,
        // ad_client_id, ad_org_id, created,"
        // + " createdby, updated, updatedby, isactive, weight,"
        // + " width , qotr ,roll_type,roll_code, gsm ,degree_type) "
        //
        // + "VALUES ( :id , :client,:org, now(), :user ,now(), :user,'Y', :weight,"
        // + " :width ,:qotr ,:roll_type ,:roll_code ,:gsm ,:degree_type)";
        //
        // Query q2 = OBDal.getInstance().getSession().createSQLQuery(sqlQuery2);
        // String paper_opening_balance_id = SequenceIdData.getUUID();
        // q2.setParameter("id", paper_opening_balance_id);
        // q2.setParameter("client", OBContext.getOBContext().getCurrentClient().getId());
        // q2.setParameter("org", OBContext.getOBContext().getCurrentOrganization().getId());
        // q2.setParameter("user", OBContext.getOBContext().getUser().getId());
        // q2.setParameter("weight", new Long(weight));
        // q2.setParameter("width", new Long(width));
        // q2.setParameter("qotr", new Long(qotr));
        // q2.setParameter("roll_type", new String(rollType));
        //
        // q2.setParameter("roll_code", rollCode);
        // q2.setParameter("gsm", new Long(gsm));
        //
        // q2.setParameter("degree_type", currentdegree);
        //
        // System.out.println(q2.toString());
        // q2.executeUpdate();
        //
        // System.out.println("done");

      }

      myMessage.setType("Success");
      myMessage.setTitle("تم رفع شيت مواصفات البكر");
      myMessage.setMessage("تم ادخال المواصفات بنجاح على السيستم");

    } // try

    catch (Exception e) {
      myMessage.setType("Error");
      myMessage.setTitle("خطأ فى الرفع");
      myMessage.setMessage(e.getMessage());
      e.printStackTrace();

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

  public String getServletInfo() {
    return "BatchSetProductCategory Servlet. This Servlet was made by Rok Lenardic";
  } // end of getServletInfo() method

}
