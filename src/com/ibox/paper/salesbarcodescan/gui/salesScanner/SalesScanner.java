// package name should follow the Java Package of the module
package com.ibox.paper.salesbarcodescan.gui.salesScanner;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.openbravo.base.provider.OBProvider;
import org.openbravo.base.secureApp.HttpSecureAppServlet;
import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;
import org.openbravo.erpCommon.businessUtility.WindowTabs;
import org.openbravo.erpCommon.utility.ComboTableData;
import org.openbravo.erpCommon.utility.LeftTabsBar;
import org.openbravo.erpCommon.utility.NavigationBar;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.erpCommon.utility.ToolBar;
import org.openbravo.erpCommon.utility.Utility;
import org.openbravo.model.common.businesspartner.BusinessPartner;
import org.openbravo.model.common.businesspartner.Location;
import org.openbravo.model.common.enterprise.DocumentType;
import org.openbravo.model.common.enterprise.Locator;
import org.openbravo.model.common.enterprise.Organization;
import org.openbravo.model.common.enterprise.Warehouse;
import org.openbravo.model.common.plm.Product;
import org.openbravo.model.common.uom.UOM;
import org.openbravo.model.materialmgmt.onhandquantity.StorageDetail;
import org.openbravo.model.materialmgmt.transaction.MaterialTransaction;
import org.openbravo.model.materialmgmt.transaction.ShipmentInOut;
import org.openbravo.model.materialmgmt.transaction.ShipmentInOutLine;
import org.openbravo.xmlEngine.XmlDocument;

import com.ibox.paper.data.PaperMachine;
import com.ibox.paper.data.QualityDegree;
import com.ibox.paper.salesbarcodescan.entity.SalesScan_Entity;
import com.ibox.paper.salesbarcodescan.model.SalesScanner_Model;

public class SalesScanner extends HttpSecureAppServlet {
  private static final long serialVersionUID = 1L;

  OBError myMessage = new OBError();
  final BigDecimal blockSize = new BigDecimal(2500);// 2500
  List<ShipmentInOutLine> ShipBalanceList;
  public String fileFormate = "pdf";
  String bakraBarcode = null;
  // all HTTP requests to this servlet enter here

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    VariablesSecureApp vars = new VariablesSecureApp(request);
    // ------------------- getting general data to all functions------------------------------
    String adOrgID = vars.getOrg();
    String sessionID = vars.getStringParameter("insession");

    String adRoleID = vars.getRole();
    Long lineNo = 0L;

    try {
      if (vars.getStringParameter("inpcx") != null
          && !vars.getStringParameter("inpcx").equals("")) {
        bakraBarcode = vars.getStringParameter("inpcx");

      } else if (vars.getStringParameter("inpcHentry") != null
          && !vars.getStringParameter("inpcHentry").equals("")) {
        bakraBarcode = vars.getStringParameter("inpcHentry");

      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // ----------------------------- in real case we will read block size from configuration table
    if (vars.commandIn("DEFAULT") || vars.commandIn("NEW")) {
      clearSession(request);
      printPageDataSheet(response, vars, null);
    }

    else if (vars.commandIn("FIND")) {
      clearSession(request);
      printPDataSheet(response, vars, null);

    } else if (vars.commandIn("GENERATE")) {
      clearSession(request);
      printFinishPage(response, vars, null, null);

    } else if (vars.commandIn("ADDBARCODE")) {

      if (bakraBarcode != null && !bakraBarcode.equals("")) {
        String documentnoRef = vars.getStringParameter("inpcDocnoId");

        addBines(documentnoRef);

        printMistPage(response, vars, null);

      }
      // }

    } else if (vars.commandIn("REMOVEBARCODE")) {

      deleteListPage(response, vars, myMessage);
    } else if (vars.commandIn("UPDATER")) {

      String documentNoP = vars.getStringParameter("inpcDocnoId");
      updateLinesInout(documentNoP);
      clearSession(request);
      printFinishPage(response, vars, null, null);
    }

    else if (vars.commandIn("PDFR")) {
      fileFormate = "pdf";
      printHReport(response, request, vars, adRoleID);

    } else if (vars.commandIn("PSCANO")) {
      printSPageDataSheet(response, vars, null);
    }

    else if (vars.commandIn("GENERATEO")) {
      clearSession(request);
      printFinishPage(response, vars, null, null);

    } else if (vars.commandIn("ADDBARCODEO")) {

      if (bakraBarcode != null && !bakraBarcode.equals("")) {

        String documentnoRef = vars.getStringParameter("inpfDoc");
        addBines(documentnoRef);
        printMistPageO(response, vars, null);

      }
    } else if (vars.commandIn("REMOVEBARCODEO")) {

      deleteListPage(response, vars, myMessage);
    } else if (vars.commandIn("UPDATERO")) {

      String documentNoP = null;
      if (vars.getStringParameter("inpcDocnoIdO") != null
          && !(vars.getStringParameter("inpcDocnoIdO")).equals("")) {
        documentNoP = vars.getStringParameter("inpcDocnoIdO");
      } else if (vars.getStringParameter("inpfDoc") != null
          && !(vars.getStringParameter("inpfDoc")).equals("")) {

        documentNoP = vars.getStringParameter("inpfDoc");
      }
      updateLinesInout(documentNoP);
      clearSession(request);
      printPageDataSheetShowO(response, vars, null);
    }

    else if (vars.commandIn("PDFRO")) {
      fileFormate = "pdf";
      printHkReport(response, request, vars, adRoleID);

    }

    else if (vars.commandIn("PSCANOO")) {

      printSPageDataSheet(response, vars, null);

    }

    else if (vars.commandIn("SHOWO")) {

      printPageDataSheetShow(response, vars, null);

    }

    else if (vars.commandIn("SHOWOO")) {

      printPageDataSheetShowO(response, vars, null);

    }

    else if (vars.commandIn("CANCEL")) {

    } else
      pageError(response);

  }

  public void printHkReport(HttpServletResponse response, HttpServletRequest request,
      VariablesSecureApp vars, String adRoleID) throws ServletException {
    HttpSession session = request.getSession();
    String ingJumbo = null;

    if (vars.getStringParameter("inpcDocnoIdO") != null
        && !(vars.getStringParameter("inpcDocnoIdO")).equals("")) {
      ingJumbo = vars.getStringParameter("inpcDocnoIdO");
    } else if (vars.getStringParameter("inpfDoc") != null
        && !(vars.getStringParameter("inpfDoc")).equals("")) {

      ingJumbo = vars.getStringParameter("inpfDoc");
    }

    GnvoicesmanualData[] iZndata = GnvoicesmanualData.selectId(this, ingJumbo);
    int laste = iZndata.length - 1;
    String idNoc = vars.getStringParameter("inpopDocument");

    String lang = vars.getLanguage();
    String strReportName = null;

    if (vars.getLanguage().equals("en_US"))

      strReportName = "@basedesign@/com/ibox/paper/salesbarcodescan/files/izno_enz.jrxml";
    else
      // /two/employeeperformance/employee_performance_en.jrxml
      strReportName = "@basedesign@/com/ibox/paper/salesbarcodescan/files/izno_enz.jrxml";

    SalesScan_Entity[] entityArray = new SalesScanner_Model().get_copy_data(idNoc);
    // cutshiftid,
    HashMap<String, Object> parameters = new HashMap<String, Object>();

    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    parameters.put("ad_client_id", OBContext.getOBContext().getCurrentClient().getId());
    parameters.put("clientName", OBContext.getOBContext().getCurrentClient().getName());

    renderJR(vars, response, strReportName, fileFormate, parameters, entityArray, null);

    // ------------------------- call renderJR function--------------------------
  }// printReport

  public void printHReport(HttpServletResponse response, HttpServletRequest request,
      VariablesSecureApp vars, String adRoleID) throws ServletException {
    HttpSession session = request.getSession();
    String DocId = null;
    if (vars.getStringParameter("inpcDocnoId") != null
        && !(vars.getStringParameter("inpcDocnoId")).equals("")) {
      DocId = vars.getStringParameter("inpcDocnoId");
    } else if (vars.getStringParameter("inpfDoc") != null
        && !(vars.getStringParameter("inpfDoc")).equals("")) {
      DocId = vars.getStringParameter("inpfDoc");
    }

    GnvoicesmanualData[] iZndata = GnvoicesmanualData.selectId(this, DocId);
    int laste = iZndata.length - 1;
    String idNoc = iZndata[laste].m_inout_id;

    String lang = vars.getLanguage();
    String strReportName = null;

    if (vars.getLanguage().equals("en_US"))

      strReportName = "@basedesign@/com/ibox/paper/salesbarcodescan/files/izno_en.jrxml";
    else
      // /two/employeeperformance/employee_performance_en.jrxml
      strReportName = "@basedesign@/com/ibox/paper/salesbarcodescan/files/izno_en.jrxml";

    SalesScan_Entity[] entityArray = new SalesScanner_Model().get_report_data(idNoc);
    // cutshiftid,
    HashMap<String, Object> parameters = new HashMap<String, Object>();

    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    parameters.put("ad_client_id", OBContext.getOBContext().getCurrentClient().getId());
    parameters.put("clientName", OBContext.getOBContext().getCurrentClient().getName());

    renderJR(vars, response, strReportName, fileFormate, parameters, entityArray, null);

    // ------------------------- call renderJR function--------------------------
  }// printReport

  private void printSPageDataSheet(HttpServletResponse response, VariablesSecureApp vars,
      OBError myMessage) throws ServletException, IOException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");

    response.setContentType("text/html; charset=UTF-8");
    PrintWriter os = response.getWriter();

    XmlDocument xme;
    if (vars.getLanguage().equals("en_US"))
      xme = xmlEngine.readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/preview_br")
          .createXmlDocument();
    else
      xme = xmlEngine.readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/preview_br")
          .createXmlDocument();
    xme.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    xme.setParameter("parag", "baby shark doodo");
    xme.setParameter("panstyle", "underline");

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "SalesScanner", false, "", "", "",
        false, "ad_forms", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xme.setParameter("toolbar", toolbar.toString());
    try {
      WindowTabs tabs = new WindowTabs(this, vars,

          "com.ibox.paper.salesbarcodescan.gui.salesScanner.SalesScanner");
      xme.setParameter("parentTabContainer", tabs.parentTabs());
      xme.setParameter("mainTabContainer", tabs.mainTabs());
      xme.setParameter("childTabContainer", tabs.childTabs());
      xme.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "preview_del.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xme.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "preview_del.html",
          strReplaceWith);
      xme.setParameter("leftTabs", lBar.manualTemplate());
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    {
      // myMessage = vars.getMessage("SalesScanner");
      vars.removeMessage("SalesScanner");
      if (myMessage != null) {
        xme.setParameter("messageType", myMessage.getType());
        xme.setParameter("messageTitle", myMessage.getTitle());
        xme.setParameter("messageMessage", myMessage.getMessage());
      }
    }
    xme.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xme.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    xme.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");

    xme.setParameter("dateFromdisplayFormat", vars.getSessionValue("#AD_SqlDateFormat"));
    xme.setParameter("dateFromsaveFormat", vars.getSessionValue("#AD_SqlDateFormat"));

    xme.setParameter("dateTodisplayFormat", vars.getSessionValue("#AD_SqlDateFormat"));
    xme.setParameter("dateTosaveFormat", vars.getSessionValue("#AD_SqlDateFormat"));

    xme.setParameter("invDatedisplayFormat", vars.getSessionValue("#AD_SqlDateFormat"));
    xme.setParameter("invDatesaveFormat", vars.getSessionValue("#AD_SqlDateFormat"));

    os.println(xme.print());
    os.close();

  }

  ////////////
  static OBCriteria<Organization> orG;
  static OBCriteria<Warehouse> wrA;
  static OBCriteria<BusinessPartner> buS;
  static Location loC;
  static DocumentType docTy;
  Organization currentOrg;
  BusinessPartner currentPartner;
  Warehouse currentWarhouse;
  Location currentLPartner;

  private void printPDataSheet(HttpServletResponse response, VariablesSecureApp vars,
      OBError myMessage) throws ServletException, IOException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");

    response.setContentType("text/html; charset=UTF-8");
    PrintWriter os = response.getWriter();
    String organiztionP = vars.getStringParameter("inpcOrgaz");
    String documentNoP = vars.getStringParameter("inpcDocnoId");
    String wareHouseP = vars.getStringParameter("inpcWarehouseId");
    String businessPartner = vars.getStringParameter("inpcBpartnerId");
    buS = OBDal.getInstance().createCriteria(BusinessPartner.class);
    buS.add(Expression.eq(BusinessPartner.PROPERTY_ID, businessPartner));
    currentPartner = buS.list().get(0);
    GnvoicesmanualData datr = new GnvoicesmanualData();
    String businessPartnerAddress = datr.bPartnerDescription(this, currentPartner.getId());
    String driver = vars.getStringParameter("inpcDriverId");
    String carNumber = vars.getStringParameter("inpCarnumberId");
    String movementDate = vars.getStringParameter("inpcMovementDateId");
    DateFormat formate = new SimpleDateFormat("yyy-MM-dd");
    Date toDate = null;
    try {
      toDate = formate.parse(movementDate);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    orG = OBDal.getInstance().createCriteria(Organization.class);
    orG.add(Expression.eq(Organization.PROPERTY_NAME, organiztionP));
    currentOrg = orG.list().get(0);

    wrA = OBDal.getInstance().createCriteria(Warehouse.class);
    wrA.add(Expression.eq(Warehouse.PROPERTY_NAME, wareHouseP));
    currentWarhouse = wrA.list().get(0);
    OBCriteria<Location> lan = OBDal.getInstance().createCriteria(Location.class);
    lan.add(Expression.eq(Location.PROPERTY_ID, businessPartnerAddress));
    currentLPartner = lan.list().get(0);

    docTy = OBDal.getInstance().get(DocumentType.class, "0CD50184705A42CCBECA4EC967646440");
    GnvoicesmanualData[] entityArray = null;
    ShipmentInOut shipInOut = new ShipmentInOut();
    shipInOut.setClient(OBContext.getOBContext().getCurrentClient());
    shipInOut.setOrganization(currentOrg);
    shipInOut.setActive(true);
    shipInOut.setCreationDate(new Date());
    shipInOut.setCreatedBy(OBContext.getOBContext().getUser());
    shipInOut.setUpdated(new Date());
    shipInOut.setUpdatedBy(OBContext.getOBContext().getUser());
    shipInOut.setDocumentType(docTy);
    shipInOut.setDocumentNo(documentNoP);
    shipInOut.setLogistic(false);
    shipInOut.setMovementType("C-");
    shipInOut.setWarehouse(currentWarhouse);
    shipInOut.setBusinessPartner(currentPartner);
    shipInOut.setPartnerAddress(currentLPartner);
    shipInOut.setPAPERTotalShipmentWeight(BigDecimal.ZERO);
    shipInOut.setPAPERNumberOfRoller(null);
    shipInOut.setPaperDriver(driver);
    shipInOut.setPAPERCarNumber(carNumber);
    Date dateop = new Date(); // this object contains the current date value

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    System.out.println(formatter.format(dateop));
    shipInOut.setMovementDate(dateop);
    shipInOut.setAccountingDate(toDate);
    OBDal.getInstance().getSession().save(shipInOut);
    OBDal.getInstance().getSession().getTransaction().commit();

    XmlDocument xme;
    if (vars.getLanguage().equals("en_US"))
      xme = xmlEngine
          .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/preview_del")
          .createXmlDocument();
    else
      xme = xmlEngine
          .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/preview_del")
          .createXmlDocument();
    xme.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    xme.setParameter("parag", "baby shark doodo");
    xme.setParameter("panstyle", "underline");

    // XmlEngineSampleData[] datr = XmlEngineSampleData.select(this, jumbocode);
    GnvoicesmanualData[] date = GnvoicesmanualData.select(this);
    List<GnvoicesmanualData> da = new ArrayList<>();
    GnvoicesmanualData daop = date[0];

    int a = date.length - 1;
    String documentNop = date[a].documentno;
    String warhNop = date[a].m_warehouse_id;
    String orgNop = date[a].ad_org_id;

    // String documentNopeR = Integer.toString(documentNope);
    System.out.println(date[a].documentno);

    // ------------ manage blocks

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "sessionSummation", false, "", "", "",
        false, "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xme.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.salesbarcodescan.gui.salesScanner.SalesScanner");
      xme.setParameter("parentTabContainer", tabs.parentTabs());
      xme.setParameter("mainTabContainer", tabs.mainTabs());
      xme.setParameter("childTabContainer", tabs.childTabs());
      xme.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "preview_br.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xme.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "preview_br.html",
          strReplaceWith);
      xme.setParameter("leftTabs", lBar.manualTemplate());

    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    if (vars.getLanguage().equals("en_US")) {
      xme.setParameter("messageType", "Success");
      xme.setParameter("messageTitle", "تم الحفظ");
      xme.setParameter("messageMessage", documentNop + " رقم");
    } else {
      xme.setParameter("messageType", "Success");
      xme.setParameter("messageTitle", "تم الحفظ");
      xme.setParameter("messageMessage", documentNop + " رقم");
    }
    xme.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xme.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    xme.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    xme.setParameter("paramDocumentno", documentNop);
    xme.setParameter("paramOrgz", orgNop);
    xme.setParameter("paramWareZ", warhNop);
    xme.setParameter("paramBpartnerz", currentPartner.getName());
    xme.setParameter("paramBpLocationz", currentLPartner.getName());

    xme.setParameter("paramDriverz", driver);
    xme.setParameter("paramCarnoz", carNumber);
    xme.setParameter("paramMdataz", movementDate);

    os.println(xme.print());
    os.close();
  }// print page data sheet function

  private void printPageDataSheet(HttpServletResponse response, VariablesSecureApp vars,
      OBError myMessage) throws IOException, ServletException {
    //
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");

    response.setContentType("text/html; charset=UTF-8");
    PrintWriter os = response.getWriter();

    XmlDocument xme = xmlEngine
        .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/SalesScanner_ar")
        .createXmlDocument();
    xme.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    xme.setParameter("parag", "baby shark doodo");
    xme.setParameter("panstyle", "underline");

    // XmlEngineSampleData[] datr = XmlEngineSampleData.select(this, jumbocode);
    // GnvoicesmanualData[] datu = GnvoicesmanualData.get_report_data();
    GnvoicesmanualData[] datr = GnvoicesmanualData.select(this);
    GnvoicesmanualData[] datrshow = GnvoicesmanualData.insertselect(this);
    List<GnvoicesmanualData> da = new ArrayList<>();
    GnvoicesmanualData daop = datr[0];

    int a = datr.length - 1;
    String documentNop = datr[a].documentno;
    String warhNop = datr[a].m_warehouse_id;
    String orgNop = datr[a].ad_org_id;
    int documentNope = Integer.parseInt(documentNop) + 1;
    String documentNopeR = Integer.toString(documentNope);
    System.out.println(datr[a].documentno);

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "SalesScanner", false, "", "", "",
        false, "ad_forms", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xme.setParameter("toolbar", toolbar.toString());
    try {
      WindowTabs tabs = new WindowTabs(this, vars,

          "com.ibox.paper.salesbarcodescan.gui.salesScanner.SalesScanner");
      xme.setParameter("parentTabContainer", tabs.parentTabs());
      xme.setParameter("mainTabContainer", tabs.mainTabs());
      xme.setParameter("childTabContainer", tabs.childTabs());
      xme.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "SalesScanner_ar.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xme.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "SalesScanner_ar.html",
          strReplaceWith);
      xme.setParameter("leftTabs", lBar.manualTemplate());
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    {
      myMessage = vars.getMessage("SalesScanner");
      vars.removeMessage("SalesScanner");
      if (myMessage != null) {
        xme.setParameter("messageType", myMessage.getType());
        xme.setParameter("messageTitle", myMessage.getTitle());
        xme.setParameter("messageMessage", myMessage.getMessage());
      }
    }
    xme.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xme.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    xme.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");

    xme.setParameter("dateFromdisplayFormat", vars.getSessionValue("#AD_SqlDateFormat"));
    xme.setParameter("dateFromsaveFormat", vars.getSessionValue("#AD_SqlDateFormat"));

    xme.setParameter("dateTodisplayFormat", vars.getSessionValue("#AD_SqlDateFormat"));
    xme.setParameter("dateTosaveFormat", vars.getSessionValue("#AD_SqlDateFormat"));

    xme.setParameter("invDatedisplayFormat", vars.getSessionValue("#AD_SqlDateFormat"));
    xme.setParameter("invDatesaveFormat", vars.getSessionValue("#AD_SqlDateFormat"));
    xme.setParameter("paramDocumentno", documentNopeR);
    xme.setParameter("paramOrgaz", orgNop);
    xme.setParameter("paramWarehouse", warhNop);

    try {
      ComboTableData comboTableData = new ComboTableData(vars, this, "TABLEDIR", "AD_Org_ID", "",
          "AD_Org Security validation", Utility.getContext(this, vars, "#User_Org", "SalesScanner"),
          Utility.getContext(this, vars, "#User_Client", "SalesScanner"), 0);

      xme.setData("reportAD_Org_ID", "liststructure", comboTableData.select(false));
      comboTableData = null;
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    xme.setData("structure1", datrshow);
    // xme.setData("structure2", datrshow);
    os.println(xme.print());
    os.close();

  }// print page data sheet function

  private void printPageDataSheetShowO(HttpServletResponse response, VariablesSecureApp vars,
      OBError myMessage) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    String ingJumbo = null;
    if (vars.getStringParameter("inpcDocnoIdO") != null
        && !(vars.getStringParameter("inpcDocnoIdO")).equals("")) {
      ingJumbo = vars.getStringParameter("inpcDocnoIdO");
    } else if (vars.getStringParameter("inpfDoc") != null
        && !(vars.getStringParameter("inpfDoc")).equals("")) {

      ingJumbo = vars.getStringParameter("inpfDoc");
    }

    XmlDocument xmlDocument;
    if (vars.getLanguage().equals("en_US"))
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/SalesScanner_show")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/SalesScanner_show")
          .createXmlDocument();

    GnvoicesmanualData[] datId = GnvoicesmanualData.insertselectIo(this, ingJumbo);
    int b = datId.length - 1;
    String documentNoc = datId[b].m_inout_id;

    // ------------ manage blocks

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "sessionSummation", false, "", "", "",
        false, "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.salesbarcodescan.gui.salesScanner.SalesScanner");
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "SalesScanner_show.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "SalesScanner_show.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());

    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    if (vars.getLanguage().equals("en_US")) {
      xmlDocument.setParameter("messageType", "Success");
      xmlDocument.setParameter("messageTitle", "قم بالضغظ على فتح الكاميرا");
      xmlDocument.setParameter("messageMessage",
          "لا تنسى تثبيت الكاميرا على الجزء العلوى من ورقة المنتج لتفادى الخطأ");
    } else {
      xmlDocument.setParameter("messageType", "Success");
      xmlDocument.setParameter("messageTitle", "أنتهاء الطباعة");
      xmlDocument.setParameter("messageMessage", "لقد انتهت طباعة التقرير بالكامل");
    }
    xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    xmlDocument.setParameter("paramDocumentno", ingJumbo);
    XmlEngineSampleData[] ioLineDoc = XmlEngineSampleData.selectIoLine(this, documentNoc);
    xmlDocument.setData("structure1", ioLineDoc);
    out.println(xmlDocument.print());
    out.close();

  }

  private void printPageDataSheetShow(HttpServletResponse response, VariablesSecureApp vars,
      OBError myMessage) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    String ingJumbo = vars.getStringParameter("inpopDocument");

    XmlDocument xmlDocument;
    if (vars.getLanguage().equals("en_US"))
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/SalesScanner_show")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/SalesScanner_show")
          .createXmlDocument();
    // XmlEngineSampleData[] datr = XmlEngineSampleData.select(this);
    // GnvoicesmanualData[] datin = GnvoicesmanualData.select(this);

    // int a = datin.length - 1;
    // String documentNop = datin[a].documentno;
    GnvoicesmanualData[] datId = GnvoicesmanualData.selectIznoWO(this, ingJumbo);
    int b = datId.length - 1;
    String documentNoc = datId[b].documentno;
    // GnvoicesmanualData[] datIdr = GnvoicesmanualData.selectId(this, ingJumbo);
    // int bb = datId.length - 1;
    // String documentNocc = datId[bb].m_inout_id;
    // ------------ manage blocks

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "sessionSummation", false, "", "", "",
        false, "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.salesbarcodescan.gui.salesScanner.SalesScanner");
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "SalesScanner_show.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "SalesScanner_show.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());

    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    if (vars.getLanguage().equals("en_US")) {
      xmlDocument.setParameter("messageType", "Success");
      xmlDocument.setParameter("messageTitle", "قم بالضغظ على فتح الكاميرا");
      xmlDocument.setParameter("messageMessage",
          "لا تنسى تثبيت الكاميرا على الجزء العلوى من ورقة المنتج لتفادى الخطأ");
    } else {
      xmlDocument.setParameter("messageType", "Success");
      xmlDocument.setParameter("messageTitle", "أنتهاء الطباعة");
      xmlDocument.setParameter("messageMessage", "لقد انتهت طباعة التقرير بالكامل");
    }
    xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    xmlDocument.setParameter("paramDocumentno", documentNoc);
    XmlEngineSampleData[] ioLineDoc = XmlEngineSampleData.selectIoLine(this, ingJumbo);
    xmlDocument.setData("structure1", ioLineDoc);
    out.println(xmlDocument.print());
    out.close();

  }// print page data sheet function

  // ------------------------------------ define function that finialize the report
  // printing-----------------
  //

  //
  private void printFinishPage(HttpServletResponse response, VariablesSecureApp vars,
      OBError myMessage, String t) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    XmlDocument xmlDocument;
    if (vars.getLanguage().equals("en_US"))
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/preview_ar")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/preview_ar")
          .createXmlDocument();
    XmlEngineSampleData[] datr = XmlEngineSampleData.select(this);
    GnvoicesmanualData[] datin = GnvoicesmanualData.select(this);

    int a = datin.length - 1;
    String documentNop = datin[a].documentno;
    GnvoicesmanualData[] datId = GnvoicesmanualData.selectId(this, documentNop);
    int b = datId.length - 1;
    String documentNoc = datId[b].m_inout_id;
    // ------------ manage blocks

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "sessionSummation", false, "", "", "",
        false, "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.salesbarcodescan.gui.salesScanner.SalesScanner");
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "preview_ar.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "preview_ar.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());

    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    if (vars.getLanguage().equals("en_US")) {
      xmlDocument.setParameter("messageType", "Success");
      xmlDocument.setParameter("messageTitle", "قم بالضغظ على فتح الكاميرا");
      xmlDocument.setParameter("messageMessage",
          "لا تنسى تثبيت الكاميرا على الجزء العلوى من ورقة المنتج لتفادى الخطأ");
    } else {
      xmlDocument.setParameter("messageType", "Success");
      xmlDocument.setParameter("messageTitle", "أنتهاء الطباعة");
      xmlDocument.setParameter("messageMessage", "لقد انتهت طباعة التقرير بالكامل");
    }
    xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    xmlDocument.setParameter("paramDocumentno", documentNop);
    XmlEngineSampleData[] ioLineDoc = XmlEngineSampleData.selectIoLine(this, documentNoc);
    xmlDocument.setData("structure1", ioLineDoc);
    out.println(xmlDocument.print());
    out.close();
  }

  // print page data sheet function

  private void deleteListPage(HttpServletResponse response, VariablesSecureApp vars,
      OBError myMessage) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();

    String documentnoRef = vars.getStringParameter("inpcDocnoId");
    String documentnoRefO = vars.getStringParameter("inpfDoc");
    GnvoicesmanualData[] datId = null;
    XmlDocument xmlDocument = null;
    if (documentnoRefO != null && !documentnoRefO.equals("")) {
      datId = GnvoicesmanualData.selectId(this, documentnoRefO);
      if (vars.getLanguage().equals("en_US"))
        xmlDocument = xmlEngine
            .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/preview_brO")
            .createXmlDocument();
      else
        xmlDocument = xmlEngine
            .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/preview_brO")
            .createXmlDocument();

    }
    if (documentnoRef != null && !documentnoRef.equals("")) {
      datId = GnvoicesmanualData.selectId(this, documentnoRef);

      if (vars.getLanguage().equals("en_US"))
        xmlDocument = xmlEngine
            .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/preview_br")
            .createXmlDocument();
      else
        xmlDocument = xmlEngine
            .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/preview_br")
            .createXmlDocument();
    }
    // ------------ manage blocks

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "sessionSummation", false, "", "", "",
        false, "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.salesbarcodescan.gui.salesScanner.SalesScanner");
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "preview_del.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "preview_del.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());

    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    int b = datId.length - 1;
    String documentNoc = datId[b].m_inout_id;
    ShipmentInOut inOut = OBDal.getInstance().get(ShipmentInOut.class, documentNoc);

    List<ShipmentInOutLine> linesList = inOut.getMaterialMgmtShipmentInOutLineList();
    BigDecimal sumW = BigDecimal.ZERO;

    sumW = linesList.stream().map(ShipmentInOutLine::getPaperBakawieght).reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);

    int er = linesList.size();
    Query query = null;

    ShipmentInOutLine sLu = null;
    String bakraBarcode = null;
    if (vars.getStringParameter("inpcx") != null && !vars.getStringParameter("inpcx").equals("")) {
      bakraBarcode = vars.getStringParameter("inpcx");
    } else if (vars.getStringParameter("inpcHentry") != null
        && !(vars.getStringParameter("inpcHentry").equals(""))) {
      bakraBarcode = vars.getStringParameter("inpcHentry");
    }

    if (bakraBarcode != null && !bakraBarcode.equals("")) {
      String[] ar;

      // barocdJumbo = barcodeReaderscanner(ImageFile);

      List<ShipmentInOutLine> ShipmentList = new ArrayList<ShipmentInOutLine>();

      String hql = "SELECT h from MaterialMgmtShipmentInOutLine h where h.client=:client ";

      if (inOut != null && !inOut.equals(""))
        hql = hql + " and  h.shipmentReceipt =:inOut";
      if (bakraBarcode != null && !bakraBarcode.equals(""))
        hql = hql + " and  h.pAPERJumboCode =:bakraBarcode ";

      Query inQuery = OBDal.getInstance().getSession().createQuery(hql);
      inQuery.setParameter("client", OBContext.getOBContext().getCurrentClient());

      if (inOut != null && !inOut.equals(""))
        inQuery.setParameter("inOut", inOut);
      if (bakraBarcode != null && !bakraBarcode.equals(""))
        inQuery.setParameter("bakraBarcode", bakraBarcode);

      ShipmentList = inQuery.list();
      for (ShipmentInOutLine lineShip : ShipmentList) {

        if (lineShip != null && !lineShip.equals("")) {
          sLu = OBDal.getInstance().get(ShipmentInOutLine.class, lineShip.getId());
          OBDal.getInstance().getSession().getTransaction().commit();
          OBDal.getInstance().getSession().beginTransaction();
          sLu.getShipmentReceipt().setProcessed(false);
          OBDal.getInstance().save(sLu);
          OBDal.getInstance().getSession().getTransaction().commit();
        }
        OBDal.getInstance().getSession().beginTransaction();
        // --------------------------------
        OBCriteria<MaterialTransaction> MaterialTransactionCriteria = OBDal.getInstance()
            .createCriteria(MaterialTransaction.class);
        MaterialTransactionCriteria
            .add(Expression.eq(MaterialTransaction.PROPERTY_GOODSSHIPMENTLINE, sLu));
        List<MaterialTransaction> transactionList = MaterialTransactionCriteria.list();
        if (transactionList.size() > 0) {
          String strSql = "delete from m_transaction   ";
          if (lineShip != null && !lineShip.equals(""))
            strSql = strSql + "where m_inoutline_id =:lineShip.getId() ";

          query = OBDal.getInstance().getSession().createSQLQuery(strSql);
          query.setParameter("lineShip.getId", sLu.getId());

          query.executeUpdate();

        }

        // ----------
        String strSql = "delete from m_inoutline   ";
        if (lineShip != null && !lineShip.equals(""))
          strSql = strSql + "where m_inoutline_id =:lineShip ";

        query = OBDal.getInstance().getSession().createSQLQuery(strSql);
        query.setParameter("lineShip", sLu.getId());
        // int moi =
        query.executeUpdate();

        OBDal.getInstance().save(sLu);
        OBDal.getInstance().getSession().getTransaction().commit();

      }

      OBDal.getInstance().getSession().beginTransaction();

      inOut.setPAPERTotalShipmentWeight(sumW.subtract(sLu.getPaperBakawieght()));
      inOut.setPAPERNumberOfRoller(new Long(er - 1));

      OBDal.getInstance().save(inOut);
      OBDal.getInstance().getSession().getTransaction().commit();

    }

    // retrieve inoutline detail for inout id
    XmlEngineSampleData[] ioLineDoc = XmlEngineSampleData.selectIoLine(this, documentNoc);

    //
    GnvoicesmanualData[] izNoDocument = GnvoicesmanualData.selectIzno(this, documentNoc);
    int izNoDocumentNo = 0;

    BigDecimal sumsluW = BigDecimal.ZERO;
    Integer sum = 0;
    for (Integer i = 0; i < izNoDocument.length; i++) {
      Integer fow = izNoDocument[i].em_paper_totalwieght.intValue();

      sum = sum + fow;
    }
    String documentnoRT = izNoDocument[izNoDocumentNo].documentno;
    String orgRT = izNoDocument[izNoDocumentNo].ad_org_id;
    String wareHouseRT = izNoDocument[izNoDocumentNo].m_warehouse_id;
    String bPartnerRT = izNoDocument[izNoDocumentNo].c_bpartner_id;
    String bPartnerLocRT = izNoDocument[izNoDocumentNo].c_bpartner_location_id;
    String totweightRT = izNoDocument[izNoDocumentNo].em_paper_totalwieght.toString();
    GnvoicesmanualData[] linoN = GnvoicesmanualData.selectLine(this, documentNoc);
    int c = linoN.length + 1;
    Integer intInstance = new Integer(c);
    String noItemRT = intInstance.toString();
    String driverRT = izNoDocument[izNoDocumentNo].em_paper_driver;
    String carNoRT = izNoDocument[izNoDocumentNo].em_paper_carno;
    String mDateRT = izNoDocument[izNoDocumentNo].movementdate;
    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

    Date toDate = null;
    String strDate = null;
    try {
      toDate = formate.parse(mDateRT);
      strDate = formate.format(toDate);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    if (vars.getLanguage().equals("en_US")) {
      xmlDocument.setParameter("messageType", "Success");
      xmlDocument.setParameter("messageTitle", "تم مسح البكرة");
      xmlDocument.setParameter("messageMessage", bakraBarcode + " رقم");
    } else {
      xmlDocument.setParameter("messageType", "Success");
      xmlDocument.setParameter("messageTitle", "تم مسح البكرة");
      xmlDocument.setParameter("messageMessage", bakraBarcode + " رقم");
    }
    xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    xmlDocument.setParameter("paramDocumentno", documentnoRT);
    xmlDocument.setParameter("paramOrgz", orgRT);
    xmlDocument.setParameter("paramWareZ", wareHouseRT);
    xmlDocument.setParameter("paramBpartnerz", bPartnerRT);
    xmlDocument.setParameter("paramBpLocationz", bPartnerLocRT);
    if (sumW != null) {
      xmlDocument.setParameter("paramTotWz", (sumW.subtract(sLu.getPaperBakawieght())).toString());
    }
    if (izNoDocument.length != 0) {

      xmlDocument.setParameter("paramNoitemz", Integer.toString(er - 1));

    }

    xmlDocument.setParameter("paramDriverz", driverRT);
    xmlDocument.setParameter("paramCarnoz", carNoRT);
    xmlDocument.setParameter("paramMdataz", strDate);
    xmlDocument.setData("structure1", ioLineDoc);
    out.println(xmlDocument.print());
    out.close();
  }// print page data sheet function

  private void printMistPageO(HttpServletResponse response, VariablesSecureApp vars,
      OBError myMessage) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();

    ////

    XmlDocument xmlDocument;
    if (vars.getLanguage().equals("en_US"))
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/preview_brO")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/preview_brO")
          .createXmlDocument();
    // ------------ manage blocks

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "sessionSummation", false, "", "", "",
        false, "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.salesbarcodescan.gui.salesScanner.SalesScanner");
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "preview_brO.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "preview_brO.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());

    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    if (vars.getStringParameter("inpcx") != null && !vars.getStringParameter("inpcx").equals("")) {
      bakraBarcode = vars.getStringParameter("inpcx");
    } else if (vars.getStringParameter("inpcHentry") != null
        && !(vars.getStringParameter("inpcHentry").equals(""))) {
      bakraBarcode = vars.getStringParameter("inpcHentry");
    }
    String documentnoRef = vars.getStringParameter("inpfDoc");
    if (ShipBalanceList.size() > 1) {
      if (vars.getLanguage().equals("en_US") && bakraBarcode != null) {
        xmlDocument.setParameter("messageType", "Error");
        xmlDocument.setParameter("messageTitle", "تم اضافة البكرة");
        xmlDocument.setParameter("messageMessage", bakraBarcode + " رقم");
      }
      if (vars.getLanguage().equals("en_US") && bakraBarcode == null) {
        xmlDocument.setParameter("messageType", "Warning");
        xmlDocument.setParameter("messageTitle", "تم اضافة البكرة سابقا");
        xmlDocument.setParameter("messageMessage", bakraBarcode + " رقم");
      }

      else {
        xmlDocument.setParameter("messageType", "Warning");
        xmlDocument.setParameter("messageTitle", "تم اضافة البكرة سابقا");
        xmlDocument.setParameter("messageMessage", bakraBarcode + " رقم");
      }
      xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
      xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
      xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
      xmlDocument.setParameter("paramDocumentno", documentnoRef);

      out.println(xmlDocument.print());
      out.close();
    } else {
      //
      XmlEngineSampleData[] datBarcode = XmlEngineSampleData.selectBakaraCode(this, bakraBarcode);
      int bBarcode = datBarcode.length - 1;

      GnvoicesmanualData[] datId = GnvoicesmanualData.selectId(this, documentnoRef);
      int b = datId.length - 1;
      String documentNoc = datId[b].m_inout_id;
      ShipmentInOut inOut = OBDal.getInstance().get(ShipmentInOut.class, documentNoc);
      List<ShipmentInOutLine> linesList = inOut.getMaterialMgmtShipmentInOutLineList();
      ShipmentInOutLine inOutL = OBDal.getInstance().get(ShipmentInOutLine.class, inOut.getId());
      int er = inOut.getMaterialMgmtShipmentInOutLineList().size();
      //

      final ShipmentInOutLine shipmentInOutLine = addLineShipment(datBarcode, bBarcode, documentNoc,
          inOut);
      if ((!linesList.get(0).getPAPERProductType().equals(shipmentInOutLine.getPAPERProductType()))
          || (linesList.get(0).getPaperMachine().getMashineName() != shipmentInOutLine
              .getPaperMachine().getMashineName())
          || (linesList.get(0).getPaperQualityDegree().getDegree() != shipmentInOutLine
              .getPaperQualityDegree().getDegree())) {

        errMessage(out, xmlDocument, linesList, documentnoRef);

      } else {

        OBDal.getInstance().save(shipmentInOutLine);
        OBDal.getInstance().getSession().getTransaction().commit();
        linesList.add(shipmentInOutLine);

        int t = inOut.getMaterialMgmtShipmentInOutLineList().size();
        Long totalItems = 0L;
        double totalWeight = 0;

        BigDecimal sumW = linesList.stream().map(ShipmentInOutLine::getPaperBakawieght)
            .reduce(BigDecimal::add).get();
        ////
        OBDal.getInstance().getSession().beginTransaction();
        inOut.setMaterialMgmtShipmentInOutLineList(linesList);

        inOut.setPAPERTotalShipmentWeight(sumW);
        inOut.setPAPERNumberOfRoller(new Long(t));

        OBDal.getInstance().save(inOut);
        // OBDal.getInstance().flush();
        OBDal.getInstance().commitAndClose();
        // retrieve inoutline detail for inout id
        XmlEngineSampleData[] ioLineDoc = XmlEngineSampleData.selectIoLine(this, documentNoc);
        //
        GnvoicesmanualData[] izNoDocument = GnvoicesmanualData.selectIznoO(this, documentNoc);
        GnvoicesmanualData gop = new GnvoicesmanualData();
        String bp = gop.bUs.getId();
        int izNoDocumentNo = 0;
        if (izNoDocument.length != 0) {
          izNoDocumentNo = izNoDocument.length - 1;
        }

        String documentnoRT = izNoDocument[izNoDocumentNo].documentno;
        String orgRT = izNoDocument[izNoDocumentNo].ad_org_id;
        String wareHouseRT = izNoDocument[izNoDocumentNo].m_warehouse_id;
        String bPartnerRT = izNoDocument[izNoDocumentNo].c_bpartner_id;
        String bPartnerLocRT = izNoDocument[izNoDocumentNo].c_bpartner_location_id;
        String driverRT = izNoDocument[izNoDocumentNo].em_paper_driver;
        String carNoRT = izNoDocument[izNoDocumentNo].em_paper_carno;
        String mDateRT = izNoDocument[izNoDocumentNo].movementdate;
        DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

        Date toDate = null;
        String strDate = null;
        try {
          toDate = formate.parse(mDateRT);
          strDate = formate.format(toDate);
        } catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();

        }

        if (vars.getLanguage().equals("en_US") && bakraBarcode != null) {
          xmlDocument.setParameter("messageType", "Success");
          xmlDocument.setParameter("messageTitle", "تم اضافة البكرة");
          xmlDocument.setParameter("messageMessage", bakraBarcode + " رقم");
        }
        if (vars.getLanguage().equals("en_US") && bakraBarcode == null) {
          xmlDocument.setParameter("messageType", "Success");
          xmlDocument.setParameter("messageTitle", "تم اضافة البكرة");
          xmlDocument.setParameter("messageMessage", bakraBarcode + " رقم");
        }

        else {
          xmlDocument.setParameter("messageType", "Success");
          xmlDocument.setParameter("messageTitle", "تم اضافة البكرة");
          xmlDocument.setParameter("messageMessage", bakraBarcode + " رقم");
          if (!linesList.get(0).getPAPERGSM().equals(shipmentInOutLine.getPAPERGSM())) {
            xmlDocument.setParameter("messageType", "Warning");
            xmlDocument.setParameter("messageTitle",
                "GSM  لهذة البكرة لا يتوافق مع اول بكرة تم ادخالها وهوا ");
            xmlDocument.setParameter("messageMessage", linesList.get(0).getPAPERGSM().toString());

          }
        }
        xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
        xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
        xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
        xmlDocument.setParameter("paramDocumentno", documentnoRT);
        xmlDocument.setParameter("paramOrgz", orgRT);
        xmlDocument.setParameter("paramWareZ", wareHouseRT);
        xmlDocument.setParameter("paramBpartnerz", bPartnerRT);
        xmlDocument.setParameter("paramBpLocationz", bPartnerLocRT);
        if (sumW != null) {
          xmlDocument.setParameter("paramTotWz", sumW.toString());
        }

        xmlDocument.setParameter("paramNoitemz", Integer.toString(t));
        xmlDocument.setParameter("paramDriverz", driverRT);
        xmlDocument.setParameter("paramCarnoz", carNoRT);
        xmlDocument.setParameter("paramMdataz", strDate);
        xmlDocument.setData("structure1", ioLineDoc);
        out.println(xmlDocument.print());
        out.close();
      }
    }
  }// print page data sheet function //////////

  private void printMistPage(HttpServletResponse response, VariablesSecureApp vars,
      OBError myMessage) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    XmlDocument xmlDocument;
    if (vars.getLanguage().equals("en_US"))
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/preview_br")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/salesbarcodescan/gui/salesScanner/preview_br")
          .createXmlDocument();
    // ------------ manage blocks

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "sessionSummation", false, "", "", "",
        false, "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.salesbarcodescan.gui.salesScanner.SalesScanner");
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "preview_br.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "preview_br.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());

    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    if (vars.getStringParameter("inpcx") != null && !vars.getStringParameter("inpcx").equals("")) {
      bakraBarcode = vars.getStringParameter("inpcx");
    } else if (vars.getStringParameter("inpcHentry") != null
        && !(vars.getStringParameter("inpcHentry").equals(""))) {
      bakraBarcode = vars.getStringParameter("inpcHentry");
    }
    String docCode = vars.getStringParameter("inpcDocId");
    if (ShipBalanceList.size() > 1) {
      if (vars.getLanguage().equals("en_US") && bakraBarcode != null) {
        xmlDocument.setParameter("messageType", "Error");
        xmlDocument.setParameter("messageTitle", "تم اضافة البكرة");
        xmlDocument.setParameter("messageMessage", bakraBarcode + " رقم");
      }
      if (vars.getLanguage().equals("en_US") && bakraBarcode == null) {
        xmlDocument.setParameter("messageType", "Warning");
        xmlDocument.setParameter("messageTitle", "تم اضافة البكرة سابقا");
        xmlDocument.setParameter("messageMessage", bakraBarcode + " رقم");
      }

      else {
        xmlDocument.setParameter("messageType", "Warning");
        xmlDocument.setParameter("messageTitle", "تم اضافة البكرة سابقا");
        xmlDocument.setParameter("messageMessage", bakraBarcode + " رقم");
      }
      xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
      xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
      xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
      xmlDocument.setParameter("paramDocumentno", docCode);

      out.println(xmlDocument.print());
      out.close();
    } else {
      ////
      XmlEngineSampleData[] datBarcode = XmlEngineSampleData.selectBakaraCode(this, bakraBarcode);
      int bBarcode = datBarcode.length - 1;
      String documentnoRef = vars.getStringParameter("inpcDocnoId");
      GnvoicesmanualData[] datId = GnvoicesmanualData.selectId(this, documentnoRef);
      int b = datId.length - 1;
      String documentNoc = datId[b].m_inout_id;
      ShipmentInOut inOut = OBDal.getInstance().get(ShipmentInOut.class, documentNoc);
      List<ShipmentInOutLine> linesList = inOut.getMaterialMgmtShipmentInOutLineList();
      ShipmentInOutLine inOutL = OBDal.getInstance().get(ShipmentInOutLine.class, inOut.getId());
      int er = inOut.getMaterialMgmtShipmentInOutLineList().size();
      //
      final ShipmentInOutLine shipmentInOutLine = addLineShipment(datBarcode, bBarcode, documentNoc,
          inOut);

      if (!linesList.isEmpty() && (!linesList.get(0).getPAPERProductType()
          .equals(shipmentInOutLine.getPAPERProductType())
          || linesList.get(0).getPaperMachine().getMashineName() != shipmentInOutLine
              .getPaperMachine().getMashineName()
          || linesList.get(0).getPaperQualityDegree().getDegree() != shipmentInOutLine
              .getPaperQualityDegree().getDegree())) {

        errMessage(out, xmlDocument, linesList, documentnoRef);

      } else {

        OBDal.getInstance().save(shipmentInOutLine);
        OBDal.getInstance().getSession().getTransaction().commit();
        linesList.add(shipmentInOutLine);

        int t = inOut.getMaterialMgmtShipmentInOutLineList().size();

        BigDecimal sumW = linesList.stream().map(ShipmentInOutLine::getPaperBakawieght)
            .reduce(BigDecimal::add).get();
        ////
        OBDal.getInstance().getSession().beginTransaction();
        inOut.setMaterialMgmtShipmentInOutLineList(linesList);

        inOut.setPAPERTotalShipmentWeight(sumW);
        inOut.setPAPERNumberOfRoller(new Long(t));

        OBDal.getInstance().save(inOut);
        //

        // retrieve inoutline detail for inout id
        XmlEngineSampleData[] ioLineDoc = XmlEngineSampleData.selectIoLine(this, documentNoc);
        //
        GnvoicesmanualData[] izNoDocument = GnvoicesmanualData.selectIzno(this, documentNoc);
        GnvoicesmanualData gop = new GnvoicesmanualData();
        String bp = gop.bUs.getId();
        int izNoDocumentNo = 0;
        if (izNoDocument.length != 0) {
          izNoDocumentNo = izNoDocument.length - 1;
        }

        String documentnoRT = izNoDocument[izNoDocumentNo].documentno;
        String orgRT = izNoDocument[izNoDocumentNo].ad_org_id;
        String wareHouseRT = izNoDocument[izNoDocumentNo].m_warehouse_id;
        String bPartnerRT = izNoDocument[izNoDocumentNo].c_bpartner_id;
        String bPartnerLocRT = izNoDocument[izNoDocumentNo].c_bpartner_location_id;
        String driverRT = izNoDocument[izNoDocumentNo].em_paper_driver;
        String carNoRT = izNoDocument[izNoDocumentNo].em_paper_carno;
        String mDateRT = izNoDocument[izNoDocumentNo].movementdate;
        DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

        Date toDate = null;
        String strDate = null;
        try {
          toDate = formate.parse(mDateRT);
          strDate = formate.format(toDate);
        } catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        if (vars.getLanguage().equals("en_US") && bakraBarcode != null) {
          xmlDocument.setParameter("messageType", "Success");
          xmlDocument.setParameter("messageTitle", "تم اضافة البكرة");
          xmlDocument.setParameter("messageMessage", bakraBarcode + " رقم");
        }
        if (vars.getLanguage().equals("en_US") && bakraBarcode == null) {
          xmlDocument.setParameter("messageType", "Success");
          xmlDocument.setParameter("messageTitle", "تم اضافة البكرة");
          xmlDocument.setParameter("messageMessage", bakraBarcode + " رقم");
        }

        else {
          xmlDocument.setParameter("messageType", "Success");
          xmlDocument.setParameter("messageTitle", "تم اضافة البكرة");
          xmlDocument.setParameter("messageMessage", bakraBarcode + " رقم");
          if (!linesList.get(0).getPAPERGSM().equals(shipmentInOutLine.getPAPERGSM())) {
            xmlDocument.setParameter("messageType", "Warning");
            xmlDocument.setParameter("messageTitle",
                "GSM  لهذة البكرة لا يتوافق مع اول بكرة تم ادخالها وهوا ");
            xmlDocument.setParameter("messageMessage", linesList.get(0).getPAPERGSM().toString());

          }
        }
        xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
        xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
        xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
        xmlDocument.setParameter("paramDocumentno", documentnoRT);
        xmlDocument.setParameter("paramOrgz", orgRT);
        xmlDocument.setParameter("paramWareZ", wareHouseRT);
        xmlDocument.setParameter("paramBpartnerz", bPartnerRT);
        xmlDocument.setParameter("paramBpLocationz", bPartnerLocRT);
        if (sumW != null) {
          xmlDocument.setParameter("paramTotWz", sumW.toString());
        }

        xmlDocument.setParameter("paramNoitemz", Integer.toString(t));
        xmlDocument.setParameter("paramDriverz", driverRT);
        xmlDocument.setParameter("paramCarnoz", carNoRT);
        xmlDocument.setParameter("paramMdataz", strDate);
        xmlDocument.setData("structure1", ioLineDoc);
        out.println(xmlDocument.print());
        out.close();
      }
    }
  }// print page data sheet function

  /**
   * @param out
   * @param xmlDocument
   * @param linesList
   */
  public void errMessage(PrintWriter out, XmlDocument xmlDocument,
      List<ShipmentInOutLine> linesList, String documentNoc) {
    xmlDocument.setParameter("messageType", "Error");
    xmlDocument.setParameter("messageTitle",
        "نوع المنتج لهذة البكرة لا يتوافق مع اول بكرة تم ادخالها وهوا ");
    xmlDocument.setParameter("messageMessage",
        linesList.get(0).getPAPERProductType() + "-"
            + linesList.get(0).getPaperQualityDegree().getDegree() + "-"
            + linesList.get(0).getPaperMachine().getMashineName());
    xmlDocument.setParameter("paramDocumentno", documentNoc);
    out.println(xmlDocument.print());
    out.close();
  }

  /**
   * @param datBarcode
   * @param bBarcode
   * @param documentNoc
   * @param inOut
   * @return
   * @throws ServletException
   */
  public ShipmentInOutLine addLineShipment(XmlEngineSampleData[] datBarcode, int bBarcode,
      String documentNoc, ShipmentInOut inOut) throws ServletException {
    final ShipmentInOutLine shipmentInOutLine = OBProvider.getInstance()
        .get(ShipmentInOutLine.class);
    shipmentInOutLine
        .setProduct(OBDal.getInstance().get(Product.class, datBarcode[bBarcode].mProductId));
    shipmentInOutLine.setShipmentReceipt(inOut);
    GnvoicesmanualData[] linoN = GnvoicesmanualData.selectLine(this, documentNoc);

    int c = linoN.length;
    if (linoN.length != 0) {
      c = linoN.length - 1;
      Long linNo = linoN[c].line;
      Long lineNo = 0L;
      lineNo = linNo + 10L;
      shipmentInOutLine.setLineNo(lineNo);

    } else {
      shipmentInOutLine.setLineNo(10L);
    }

    shipmentInOutLine.setMovementQuantity(new BigDecimal("1"));
    shipmentInOutLine.setUOM(OBDal.getInstance().get(UOM.class, datBarcode[bBarcode].cUomId));
    shipmentInOutLine.setReinvoice(false);
    shipmentInOutLine.setDescriptionOnly(false);
    shipmentInOutLine.setOrganization(inOut.getOrganization());

    shipmentInOutLine.setBusinessPartner(inOut.getBusinessPartner());
    shipmentInOutLine.setPAPERJumboCode(datBarcode[bBarcode].emPaperJumboCode);
    if ((datBarcode[bBarcode].emPaperProductType).equals("Manilla")) {
      shipmentInOutLine.setPAPERProductType("1");
    }
    if ((datBarcode[bBarcode].emPaperProductType).equals("Meduim Fluting")) {
      shipmentInOutLine.setPAPERProductType("2");

    }
    if ((datBarcode[bBarcode].emPaperProductType).equals("Core Board")) {
      shipmentInOutLine.setPAPERProductType("3");

    }
    if ((datBarcode[bBarcode].emPaperProductType).equals("Test Liner")) {
      shipmentInOutLine.setPAPERProductType("4");

    }
    if ((datBarcode[bBarcode].emPaperProductType).equals("Special core board")) {
      shipmentInOutLine.setPAPERProductType("5");

    }

    shipmentInOutLine.setPaperQualityDegree(
        OBDal.getInstance().get(QualityDegree.class, datBarcode[bBarcode].emPaperQualityDegree));
    shipmentInOutLine.setPAPERGSM(new BigDecimal(datBarcode[bBarcode].emPaperGsm));
    shipmentInOutLine.setPaperBakarawidth(new BigDecimal(datBarcode[bBarcode].emPaperBakarawidth));
    shipmentInOutLine.setPAPERBakkaraQotr(new BigDecimal(datBarcode[bBarcode].emPaperBakaraqotr));
    shipmentInOutLine.setPaperBakawieght(new BigDecimal(datBarcode[bBarcode].emPaperBakawieght));
    shipmentInOutLine.setPaperMachine(
        OBDal.getInstance().get(PaperMachine.class, datBarcode[bBarcode].emPaperMachine));
    shipmentInOutLine.setPaperClient(
        OBDal.getInstance().get(BusinessPartner.class, datBarcode[bBarcode].emPaperClient));

    shipmentInOutLine
        .setStorageBin(OBDal.getInstance().get(Locator.class, "CB1902C1F89147B182B1999686316DD1"));
    if (datBarcode[bBarcode].emPaperNotes.equals("null")
        || datBarcode[bBarcode].emPaperNotes.isEmpty()) {
      shipmentInOutLine.setPaperNotes("");
    } else {
      shipmentInOutLine.setPaperNotes(datBarcode[bBarcode].emPaperNotes);
    }
    return shipmentInOutLine;
  }

  // ------------------------------- define function that update session
  // ------------------------------------
  /**
   * @param documentnoRef
   * @throws ServletException
   */
  public void addBines(String documentnoRef) throws ServletException {
    GnvoicesmanualData[] datId = GnvoicesmanualData.selectId(this, documentnoRef);
    int b = datId.length - 1;
    String documentNoc = datId[b].m_inout_id;
    ShipmentInOut inOut = OBDal.getInstance().get(ShipmentInOut.class, documentNoc);

    ShipmentInOutLine inOutL = OBDal.getInstance().get(ShipmentInOutLine.class, inOut.getId());

    //
    OBCriteria<ShipmentInOutLine> OpeningShipmentCriteria = OBDal.getInstance()
        .createCriteria(ShipmentInOutLine.class);
    OpeningShipmentCriteria.add(Expression.eq(inOutL.PROPERTY_PAPERJUMBOCODE, bakraBarcode));
    ShipBalanceList = OpeningShipmentCriteria.list();
  }

  /**
   * @param documentNoP
   * @throws ServletException
   */
  public void updateLinesInout(String documentNoP) throws ServletException {
    GnvoicesmanualData[] iZndata = GnvoicesmanualData.selectId(this, documentNoP);
    int laste = iZndata.length - 1;
    String idNoc = iZndata[laste].m_inout_id;
    ShipmentInOut inOut = OBDal.getInstance().get(ShipmentInOut.class, idNoc);

    String strSql = "update m_inout set processed ='Y',docaction ='--',docstatus='CO',process_goods_java='--' where m_inout.MovementType IN ('C-', 'C+') and m_inout.isLogistic = 'N'";
    if (idNoc != null && !idNoc.equals(""))
      strSql = strSql + "and m_inout_id =:idNoc ";

    Query query = OBDal.getInstance().getSession().createSQLQuery(strSql);
    query.setParameter("idNoc", inOut.getId());
    // int moi =
    query.executeUpdate();
    List<ShipmentInOutLine> ioLine = inOut.getMaterialMgmtShipmentInOutLineList();
    for (ShipmentInOutLine ioz : ioLine) {
      String proId = ioz.getProduct().getId();
      String strhQl = "update m_storage_detail set qtyonhand = 0 where m_storage_detail.M_Locator_ID = 'CB1902C1F89147B182B1999686316DD1' ";
      if (proId != null && !proId.equals(""))
        strhQl = strhQl + "and m_product_id =:proId ";

      Query huery = OBDal.getInstance().getSession().createSQLQuery(strhQl);
      huery.setParameter("proId", ioz.getProduct().getId());
      // int moi =
      huery.executeUpdate();
    }
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

  private OBError handleException(String msg, VariablesSecureApp vars) {
    String title = Utility.messageBD(this, "Error", vars.getLanguage());
    String message = Utility.messageBD(this, msg, vars.getLanguage());
    if (message == null || message.isEmpty()) {
      message = Utility.messageBD(this, "ErrorInExtensionPoint", vars.getLanguage());
    }
    OBError err = new OBError();

    err.setTitle(title);
    err.setMessage(message);
    err.setType("ERROR");
    return err;
  }

  private List<StorageDetail> getOrders(String strOrderIds) {
    OBCriteria<StorageDetail> orderCrit = OBDal.getInstance().createCriteria(StorageDetail.class);

    ArrayList<String> orderIds = Utility.stringToArrayList(strOrderIds.replaceAll("\\(|\\)|'", ""));
    orderCrit.add(Restrictions.in(StorageDetail.PROPERTY_ID, orderIds));

    List<StorageDetail> shorders = orderCrit.list();
    return shorders;
  }

  private OBError handleException(Exception e, VariablesSecureApp vars) {
    return handleException(e.getMessage(), vars);
  }

  public String getServletInfo() {
    return "BatchSetProductCategory Servlet. This Servlet was made by Rok Lenardic";
  } // end of getServletInfo() method

}