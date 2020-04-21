// package name should follow the Java Package of the module
package com.ibox.paper.sbscan;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openbravo.base.filter.IsIDFilter;
import org.openbravo.base.secureApp.HttpSecureAppServlet;
import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.erpCommon.businessUtility.Tree;
import org.openbravo.erpCommon.businessUtility.WindowTabs;
import org.openbravo.erpCommon.utility.ComboTableData;
import org.openbravo.erpCommon.utility.LeftTabsBar;
import org.openbravo.erpCommon.utility.NavigationBar;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.erpCommon.utility.ToolBar;
import org.openbravo.erpCommon.utility.Utility;
import org.openbravo.xmlEngine.XmlDocument;

// extending HttpSecureAppServlet automatically takes care of the authentication
// and other session tasks
public class BatchSetProductCategory extends HttpSecureAppServlet {
  private static final long serialVersionUID = 1L;

  // all HTTP requests to this servlet enter here
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    VariablesSecureApp vars = new VariablesSecureApp(request);

    // The controller needs to distinguish different actions that this servlet supports
    // In our case these are:
    // - DEFAULT - manual window called by clicking its menu item
    // - FIND - clicking the Search button
    // - SET - clicking the Set button
    if (vars.commandIn("DEFAULT", "FIND")) {

      // BEGIN Parse input parameters
      String strC_BPartner_ID = vars.getRequestGlobalVariable("inpcBpartnerId",
          "BatchSetProductCategory|C_BPartner_ID");
      String strAD_Org_ID = vars.getRequestGlobalVariable("inpadOrgId",
          "BatchSetProductCategory|Ad_Org_ID");
      // if no organization has been selected yet, set it to 0 which is the asterisk
      // organization
      if (strAD_Org_ID == null || strAD_Org_ID == "")
        strAD_Org_ID = "0";
      String strCategory_ID = vars.getRequestGlobalVariable("inpmProductCategoryId",
          "BatchSetProductCategory|inpmProductCategoryId");
      String strProductType = vars.getRequestGlobalVariable("inpmProductType",
          "BatchSetProductCategory|inpmProductType");
      String strPurchasedSold = vars.getStringParameter("inpPurchasedSold", "A");
      // END Parse input parameters

      // call a submethod to do the job of printing out filtered products
      printPageDataSheet(response, vars, strC_BPartner_ID, strAD_Org_ID, strCategory_ID,
          strProductType, strPurchasedSold);

    } else if (vars.commandIn("SET")) {

      // BEGIN Parse input parameters
      String strMProductId = vars.getInStringParameter("inpProduct", IsIDFilter.instance);
      String strNewProductCategory = vars.getStringParameter("inpmProductCategoryToSetId");
      // END Parse input parameters

      // call the process SQL statement and save the resulting message for this particular window
      // into the message session variable
      vars.setMessage("BatchSetProductCategory",
          processSetCategory(response, vars, strMProductId, strNewProductCategory));

      // reload the screen to show the message
      response.sendRedirect(strDireccion + request.getServletPath());
    } else
      pageError(response);
  }

  private void printPageDataSheet(HttpServletResponse response, VariablesSecureApp vars,
      String strC_BPartner_ID, String strAD_Org_ID, String strCategory_ID, String strProductType,
      String strPurchasedSold) throws IOException, ServletException {

    log4j.debug("BatchSetProductCategory: Call to printPagedataSheet");

    // BEGIN initialize response
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    // END initialize response

    String discard[] = {};
    XmlDocument xmlDocument = null;
    BatchSetProductCategoryData[] data = null;

    String strTreeOrg = BatchSetProductCategoryData.treeOrg(this, vars.getClient());
    if (strAD_Org_ID.equals("")) {
      xmlDocument = xmlEngine
          .readXmlTemplate("org/openbravo/howtos/ad_forms/BatchSetProductCategory", discard)
          .createXmlDocument();
      // retrieve empty data set
      data = BatchSetProductCategoryData.set();
    } else {
      xmlDocument = xmlEngine
          .readXmlTemplate("org/openbravo/howtos/ad_forms/BatchSetProductCategory")
          .createXmlDocument();
      // retrieve filtered products using the select method defined in the .xsql file
      data = BatchSetProductCategoryData.select(this, vars.getLanguage(),
          Utility.getContext(this, vars, "#User_Client", "BatchSetProductCategory"),
          Utility.getContext(this, vars, "#User_Org", "BatchSetProductCategory"), strC_BPartner_ID,
          strCategory_ID, strProductType, strPurchasedSold.equals("P") ? "Y" : "",
          strPurchasedSold.equals("S") ? "Y" : "", Tree.getMembers(this, strTreeOrg, strAD_Org_ID));
    }

    // BEGIN generate and set UI elements
    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "BatchSetProductCategory", false, "",
        "", "", false, "ad_forms", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());
    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "org.openbravo.howtos.ad_forms.BatchSetProductCategory");
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(),
          "BatchSetProductCategory.html", classInfo.id, classInfo.type, strReplaceWith,
          tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "BatchSetProductCategory.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    // END generate and set UI elements

    // BEGIN check for any pending message to be shown and clear it from the session
    OBError myMessage = vars.getMessage("BatchSetProductCategory");
    vars.removeMessage("BatchSetProductCategory");
    if (myMessage != null) {
      xmlDocument.setParameter("messageType", myMessage.getType());
      xmlDocument.setParameter("messageTitle", myMessage.getTitle());
      xmlDocument.setParameter("messageMessage", myMessage.getMessage());
    }
    // END check for any pending message to be shown and clear it from the session

    // BEGIN Organization dropdown - using tabledir reference
    try {
      ComboTableData comboTableData = new ComboTableData(vars, this, "TABLEDIR", "AD_Org_ID", "",
          "AD_Org Security validation",
          Utility.getContext(this, vars, "#User_Org", "BatchSetProductCategory"),
          Utility.getContext(this, vars, "#User_Client", "BatchSetProductCategory"), 0);
      Utility.fillSQLParameters(this, vars, null, comboTableData, "BatchSetProductCategory", null);
      xmlDocument.setData("NAME_reportOrganizationDropdown", "liststructure",
          comboTableData.select(false));
      comboTableData = null;
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    // END Organization dropdown - using tabledir reference

    // BEGIN Product Category dropdown - using tabledir reference
    try {
      ComboTableData comboTableData = new ComboTableData(vars, this, "TABLEDIR",
          "M_Product_Category_ID", "", "",
          Utility.getContext(this, vars, "#User_Org", "BatchSetProductCategory"),
          Utility.getContext(this, vars, "#User_Client", "BatchSetProductCategory"), 0);
      Utility.fillSQLParameters(this, vars, null, comboTableData, "BatchSetProductCategory", null);
      xmlDocument.setData("NAME_reportProductCategoryDropdown", "liststructure",
          comboTableData.select(false));
      comboTableData = null;
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    // END Category dropdown - using tabledir reference

    // BEGIN Product Category to Set dropdown - using tabledir reference
    try {
      ComboTableData comboTableData = new ComboTableData(vars, this, "TABLEDIR",
          "M_Product_Category_ID", "", "",
          Utility.getContext(this, vars, "#User_Org", "BatchSetProductCategory"),
          Utility.getContext(this, vars, "#User_Client", "BatchSetProductCategory"), 0);
      Utility.fillSQLParameters(this, vars, null, comboTableData, "BatchSetProductCategory", null);
      xmlDocument.setData("NAME_reportProductCategoryToSetDropdown", "liststructure",
          comboTableData.select(false));
      comboTableData = null;
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    // END Product Category to Set dropdown - using tabledir reference

    // BEGIN Product Type dropdown - using list reference
    try {
      ComboTableData comboTableData = new ComboTableData(vars, this, "LIST",
          "M_Product_ProductType", "M_Product_ProductType", "",
          Utility.getContext(this, vars, "#User_Org", "BatchSetProductCategory"),
          Utility.getContext(this, vars, "#User_Client", "BatchSetProductCategory"), 0);
      Utility.fillSQLParameters(this, vars, null, comboTableData, "BatchSetProductCategory", null);
      xmlDocument.setData("NAME_reportProductType", "liststructure", comboTableData.select(false));
      comboTableData = null;
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    // END Product Type dropdown - using list reference

    // BEGIN put parameter values back into the form so that they remain entered on reload
    xmlDocument.setParameter("NAME_paramBPartnerId", strC_BPartner_ID);
    xmlDocument.setParameter("NAME_paramBPartnerDescription",
        BatchSetProductCategoryData.bPartnerDescription(this, strC_BPartner_ID));
    xmlDocument.setParameter("NAME_paramOrganizationId", strAD_Org_ID);
    xmlDocument.setParameter("NAME_paramProductCategoryId", strCategory_ID);
    xmlDocument.setParameter("NAME_paramProductType", strProductType);
    xmlDocument.setParameter("NAME_paramPurchased", strPurchasedSold);
    xmlDocument.setParameter("NAME_paramSold", strPurchasedSold);
    xmlDocument.setParameter("NAME_paramAny", strPurchasedSold);
    // END put parameter values back into the form so that they remain entered on reload

    // submit the list of filtered products to the template
    xmlDocument.setData("structure1", data);

    // BEGIN render the output and close it so that it is returned to the user
    out.println(xmlDocument.print());
    out.close();
    // END render the output and close it so that it is returned to the user
  }

  private OBError processSetCategory(HttpServletResponse response, VariablesSecureApp vars,
      String strMProductId, String strNewProductCategory) {

    OBError myMessage = new OBError();
    int intUpdatedProducts = 0;

    // BEGIN check for missing parameters and return in error in the case
    if (strMProductId == null || strMProductId.equals("")) {
      myMessage.setTitle("Error");
      myMessage.setType("ERROR");
      myMessage.setMessage(Utility.messageBD(this, "HT_NoProductsSelected", vars.getLanguage()));
      return myMessage;
    }
    if (strNewProductCategory == null || strNewProductCategory.equals("")) {
      myMessage.setTitle("Error");
      myMessage.setType("ERROR");
      myMessage.setMessage(
          Utility.messageBD(this, "HT_NoNewProductCategorySelected", vars.getLanguage()));
      return myMessage;
    }
    // END check for missing parameters and return in error in the case

    try {
      // peform the actual processing - setting the new product category for the selected products
      intUpdatedProducts = BatchSetProductCategoryData.process(this, strNewProductCategory,
          strMProductId);
    } catch (Exception e) {
      myMessage.setTitle("Error");
      myMessage.setType("ERROR");
      myMessage
          .setMessage(Utility.messageBD(this, "HT_NewProductCategorySetError", vars.getLanguage())
              + e.toString());
      return myMessage;
    }

    myMessage.setTitle("Success");
    myMessage.setType("SUCCESS");
    myMessage.setMessage(intUpdatedProducts
        + Utility.messageBD(this, "HT_NewProductCategorySuccessfullySet", vars.getLanguage()));
    return myMessage;
  }

  public String getServletInfo() {
    return "BatchSetProductCategory Servlet. This Servlet was made by Rok Lenardic";
  } // end of getServletInfo() method
}
