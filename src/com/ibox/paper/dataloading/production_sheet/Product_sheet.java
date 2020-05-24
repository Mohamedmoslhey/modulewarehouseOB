// package name should follow the Java Package of the module
package com.ibox.paper.dataloading.production_sheet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.hibernate.criterion.Expression;
import org.hibernate.query.Query;
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
import org.openbravo.model.common.businesspartner.BusinessPartner;
import org.openbravo.model.financialmgmt.calendar.Period;
import org.openbravo.xmlEngine.XmlDocument;

import com.ibox.paper.data.PaperEmployee;
import com.ibox.paper.data.PaperMachine;
import com.ibox.paper.data.PaperRefuse;
import com.ibox.paper.data.PaperShift;
import com.ibox.paper.data.ProductionQuality;
import com.ibox.paper.data.QualityDegree;

import jxl.Sheet;
import jxl.Workbook;

public class Product_sheet extends HttpSecureAppServlet {
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
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/dataloading/production_sheet/Product_sheet")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/dataloading/production_sheet/Product_sheet")
          .createXmlDocument();

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "PaySlip", false, "", "", "", false,
        "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.dataloading.production_sheet.Product_sheet");

      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "Product_sheet.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "Product_sheet.html",
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
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/dataloading/production_sheet/Product_sheet")
          .createXmlDocument();
    else
      xmlDocument = xmlEngine
          .readXmlTemplate("com/ibox/paper/dataloading/production_sheet/Product_sheet")
          .createXmlDocument();

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(), "PaySlip", false, "", "", "", false,
        "ad_reports", strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());

    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.ibox.paper.dataloading.production_sheet.Product_sheet");

      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(), "Product_sheet.html",
          classInfo.id, classInfo.type, strReplaceWith, tabs.breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "Product_sheet.html",
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
        // start of excel
        String bakaracode = sheet.getCell(0, x).getContents();
        String producttype = sheet.getCell(1, x).getContents();
        String bussinesspartnercode = sheet.getCell(2, x).getContents(); //
        String width = sheet.getCell(3, x).getContents(); //
        String qotr = sheet.getCell(4, x).getContents(); //
        String weight = sheet.getCell(5, x).getContents(); //
        String gsm = sheet.getCell(6, x).getContents();
        String machine = sheet.getCell(7, x).getContents();
        String productionres = sheet.getCell(8, x).getContents();
        String maqsmasool = sheet.getCell(9, x).getContents(); //
        String qualitymasool = sheet.getCell(10, x).getContents(); //
        String productionshift = sheet.getCell(11, x).getContents(); //
        String maqsshift = sheet.getCell(12, x).getContents(); //

        String weigthone = sheet.getCell(13, x).getContents(); //
        String weigthtwo = sheet.getCell(14, x).getContents();
        String weigththree = sheet.getCell(15, x).getContents(); // لازم تكون رقم عشان يقراها صح
        String weigthfour = sheet.getCell(16, x).getContents(); //
        String weigthfive = sheet.getCell(17, x).getContents(); //////
        String weigthsix = sheet.getCell(18, x).getContents(); //
        String weigthseven = sheet.getCell(19, x).getContents(); //
        String weigtheight = sheet.getCell(20, x).getContents(); //
        String weigthnine = sheet.getCell(21, x).getContents();
        String weigthten = sheet.getCell(22, x).getContents(); // لازم تكون رقم عشان يقراها صح
        // String average = sheet.getCell(16, x).getContents(); //
        String somcone = sheet.getCell(23, x).getContents(); //
        String somctwo = sheet.getCell(24, x).getContents(); //
        String somcthree = sheet.getCell(25, x).getContents(); //
        String somcfour = sheet.getCell(26, x).getContents(); //
        String somcfive = sheet.getCell(27, x).getContents();//
        String somcsix = sheet.getCell(28, x).getContents(); // لازم تكون رقم عشان يقراها صح
        String somcseven = sheet.getCell(29, x).getContents(); //
        String somceigth = sheet.getCell(30, x).getContents(); //
        String somcnine = sheet.getCell(31, x).getContents(); //
        String somcten = sheet.getCell(32, x).getContents(); //
        // String somcaverage = sheet.getCell(27, x).getContents(); //
        String shadtolly = sheet.getCell(33, x).getContents();
        String shadardy = sheet.getCell(34, x).getContents(); // لازم تكون رقم عشان يقراها صح
        String fasleltbaaat = sheet.getCell(35, x).getContents(); //
        String rtoppa = sheet.getCell(36, x).getContents(); //
        String tashrbammamy = sheet.getCell(37, x).getContents(); //
        String tashrabkalfy = sheet.getCell(38, x).getContents(); //
        String waghcolor = sheet.getCell(39, x).getContents(); //
        String backcolor = sheet.getCell(40, x).getContents();
        String waslano = sheet.getCell(41, x).getContents(); // لازم تكون رقم عشان يقراها صح
        qualitydegree = sheet.getCell(42, x).getContents();
        firstresonfars = sheet.getCell(43, x).getContents(); //
        secondresonfars = sheet.getCell(44, x).getContents(); //

        String notes = sheet.getCell(45, x).getContents(); //
        String datotimo = sheet.getCell(46, x).getContents(); //
        DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");

        Date datePro = formate.parse(datotimo);
        // end of excel
        List<Period> periodList = new ArrayList<Period>();
        String priodSql = "select h from FinancialMgmtPeriod h  where h.client=:client and "
            + "h.openClose ='C'";

        Query periodQuery = OBDal.getInstance().getSession().createQuery(priodSql);
        periodQuery.setParameter("client", OBContext.getOBContext().getCurrentClient());
        periodList = periodQuery.list();
        for (Period mPeriod : periodList) {
          if ((mPeriod.getStartingDate().equals(datePro)
              || mPeriod.getStartingDate().after(datePro))
              && (mPeriod.getEndingDate().equals(datePro)
                  || mPeriod.getEndingDate().before(datePro))) {
            throw new Exception("تم اغلاق هذة الفترة لايمكن ادخال البكرة !!!" + bakaracode);
          }
        }
        // ---check if code inserted before-----
        OBCriteria<ProductionQuality> ProductionQualityCriteria = OBDal.getInstance()
            .createCriteria(ProductionQuality.class);

        ProductionQualityCriteria
            .add(Expression.eq(ProductionQuality.PROPERTY_BAKARACODE, bakaracode));

        List<ProductionQuality> ProductionQualityList = ProductionQualityCriteria.list();
        if (ProductionQualityList.size() > 0) {
          throw new Exception("لقد ادخلت هذا الكود سابق !!!" + bakaracode);
        }

        // --- inset new bakra
        ProductionQuality newproduction = new ProductionQuality();
        newproduction.setClient(OBContext.getOBContext().getCurrentClient());
        newproduction.setOrganization(OBContext.getOBContext().getCurrentOrganization());
        newproduction.setActive(true);
        newproduction.setCreationDate(new Date());
        newproduction.setCreatedBy(OBContext.getOBContext().getUser());
        newproduction.setUpdated(new Date());
        newproduction.setUpdatedBy(OBContext.getOBContext().getUser());
        newproduction.setBakaracode(bakaracode);
        newproduction.setProductType(producttype);
        newproduction.setBakarawidth(new BigDecimal(width));
        newproduction.setBakaraqotr(new BigDecimal(qotr));
        newproduction.setBakaraweight(new Long(weight));
        newproduction.setGsm(new BigDecimal(gsm));
        newproduction.setTryone(new Long(weigthone));
        newproduction.setTrytwo(new Long(weigthtwo));
        newproduction.setTrythree(new Long(weigththree));
        newproduction.setTryfour(new Long(weigthfour));
        newproduction.setTryfive(new Long(weigthfive));
        newproduction.setSomcone(new Long(somcone));
        newproduction.setSomctwo(new Long(somctwo));
        newproduction.setSomcthree(new Long(somcthree));
        newproduction.setSomcfour(new Long(somcfour));
        newproduction.setSomcfive(new Long(somcfive));
        newproduction.setShadToly(new BigDecimal(shadtolly));
        newproduction.setShadardy(new BigDecimal(shadardy));
        newproduction.setFasltabky(new BigDecimal(fasleltbaaat));
        newproduction.setRetopa(new BigDecimal(rtoppa));
        newproduction.setFrontcolor(waghcolor);
        newproduction.setBackcolor(backcolor);
        newproduction.setTrysix(new Long(weigthsix));
        newproduction.setTryseven(new Long(weigthseven));
        newproduction.setTryeight(new Long(weigtheight));
        newproduction.setTrynine(new Long(weigthnine));
        newproduction.setTryten(new Long(weigthten));
        // Long av = ((new Long(weigthone) + new Long(weigthtwo) + new Long(weigththree)
        // + new Long(weigthfour) + new Long(weigthfive) + new Long(weigthsix)
        // + new Long(weigthseven) + new Long(weigtheight) + new Long(weigthnine)
        // + new Long(weigthten))) / 10;
        // newproduction.setAvrage(av);

        newproduction.setSomcsix(new Long(somcsix));
        newproduction.setSomcseven(new Long(somcseven));
        newproduction.setSomceigth(new Long(somceigth));
        newproduction.setSomcnine(new Long(somcnine));
        newproduction.setSomcten(new Long(somcten));
        // Long av2 = ((new Long(somcone) + new Long(somctwo) + new Long(somcthree)
        // + new Long(somcfour) + new Long(somcfive) + new Long(somcsix) + new Long(somcseven)
        // + new Long(somceigth) + new Long(somcnine) + new Long(somcten))) / 10;

        // newproduction.setSomcaverage(av2);
        int count = 0;
        if (!new Long(weigthone).equals(new Long(1)))
          count++;
        if (!new Long(weigthtwo).equals(new Long(1)))
          count++;
        if (!new Long(weigththree).equals(new Long(1)))
          count++;
        if (!new Long(weigthfour).equals(new Long(1)))
          count++;
        if (!new Long(weigthfive).equals(new Long(1)))
          count++;
        if (!new Long(weigthsix).equals(new Long(0)))
          count++;
        if (!new Long(weigthseven).equals(new Long(0)))
          count++;
        if (!new Long(weigtheight).equals(new Long(0)))
          count++;
        if (!new Long(weigthnine).equals(new Long(0)))
          count++;
        if (!new Long(weigthten).equals(new Long(0))) {
          count++;
        }
        Long av1 = (new Long(weigthone) + new Long(weigthtwo) + new Long(weigththree)
            + new Long(weigthfour) + new Long(weigthfive) + new Long(weigthsix)
            + new Long(weigthseven) + new Long(weigtheight) + new Long(weigthnine)
            + new Long(weigthten)) / new Long(count);
        newproduction.setAvrage(av1);

        int c = 0;
        if (!new Long(somcone).equals(new Long(1)))
          c++;
        if (!new Long(somctwo).equals(new Long(1)))
          c++;
        if (!new Long(somcthree).equals(new Long(1)))
          c++;
        if (!new Long(somcfour).equals(new Long(1)))
          c++;
        if (!new Long(somcfive).equals(new Long(1)))
          c++;
        if (!new Long(somcsix).equals(new Long(0)))
          c++;
        if (!new Long(somcseven).equals(new Long(0)))
          c++;
        if (!new Long(somceigth).equals(new Long(0)))
          c++;
        if (!new Long(somcnine).equals(new Long(0)))
          c++;
        if (!new Long(somcten).equals(new Long(0))) {
          c++;
        }

        Long av2 = (new Long(somcone) + new Long(somctwo) + new Long(somcthree) + new Long(somcfour)
            + new Long(somcfive) + new Long(somcsix) + new Long(somcseven) + new Long(somceigth)
            + new Long(somcnine) + new Long(somcten)) / new Long(c);
        newproduction.setSomcaverage(av2);

        newproduction.setDecision("1");

        newproduction.setTashrop(new Long(tashrbammamy));
        newproduction.setTasharback(new Long(tashrabkalfy));
        //
        newproduction.setWasla(waslano);
        newproduction.setNotes(notes);
        // production time

        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date2 = formatter2.parse(datotimo);
        newproduction.setProductiondate(date2);

        OBCriteria<QualityDegree> QualityDegreeCriteria = OBDal.getInstance()
            .createCriteria(QualityDegree.class);
        QualityDegreeCriteria
            .add(Expression.eq(QualityDegree.PROPERTY_COMMERCIALNAME, qualitydegree));

        QualityDegree currentdegree = QualityDegreeCriteria.list().get(0);

        newproduction.setPaperQualityDegree(currentdegree);

        // busipartner
        OBCriteria<BusinessPartner> businnessPartCriteria = OBDal.getInstance()
            .createCriteria(BusinessPartner.class);
        businnessPartCriteria
            .add(Expression.eq(BusinessPartner.PROPERTY_SEARCHKEY, bussinesspartnercode));

        BusinessPartner currentbussinesspartner = businnessPartCriteria.list().get(0);

        newproduction.setBusinessPartner(currentbussinesspartner);

        // get Pname ,cnam,quality
        OBCriteria<PaperEmployee> PaperEmployeeCriteria = OBDal.getInstance()
            .createCriteria(PaperEmployee.class);
        PaperEmployeeCriteria.add(Expression.eq(PaperEmployee.PROPERTY_CODE, productionres));

        PaperEmployee currentproductionres = PaperEmployeeCriteria.list().get(0);
        newproduction.setPname(currentproductionres);

        // maqsmasool
        OBCriteria<PaperEmployee> maqsmassolCriteria = OBDal.getInstance()
            .createCriteria(PaperEmployee.class);
        maqsmassolCriteria.add(Expression.eq(PaperEmployee.PROPERTY_CODE, maqsmasool));
        PaperEmployee currentmaqsmasool = maqsmassolCriteria.list().get(0);

        newproduction.setCname(currentmaqsmasool);

        // qualitymasool
        OBCriteria<PaperEmployee> qualitymasoolCriteria = OBDal.getInstance()
            .createCriteria(PaperEmployee.class);
        qualitymasoolCriteria.add(Expression.eq(PaperEmployee.PROPERTY_CODE, qualitymasool));
        PaperEmployee currentqualitymasool = qualitymasoolCriteria.list().get(0);

        newproduction.setPaperEmployeeIdd(currentqualitymasool);

        // machine no

        OBCriteria<PaperMachine> PaperMachineCriteria = OBDal.getInstance()
            .createCriteria(PaperMachine.class);
        PaperMachineCriteria.add(Expression.eq(PaperMachine.PROPERTY_VALIDATIONCODE, machine));

        PaperMachine currentpaperMachine = PaperMachineCriteria.list().get(0);

        newproduction.setPaperMachine(currentpaperMachine);

        // // production shift
        OBCriteria<PaperShift> proShiftCriteria = OBDal.getInstance()
            .createCriteria(PaperShift.class);
        proShiftCriteria.add(Expression.eq(PaperShift.PROPERTY_VALIDATIONCODE, productionshift));

        PaperShift currentPaperShift = proShiftCriteria.list().get(0);
        newproduction.setProshift(currentPaperShift);

        // // maaqs shift
        OBCriteria<PaperShift> maaqsShiftCriteria = OBDal.getInstance()
            .createCriteria(PaperShift.class);
        maaqsShiftCriteria.add(Expression.eq(PaperShift.PROPERTY_VALIDATIONCODE, maqsshift));

        PaperShift currentmaqsshift = maaqsShiftCriteria.list().get(0);
        newproduction.setCutshift(currentmaqsshift);
        if ("DEG02".equals(qualitydegree) || "DEG03".equals(qualitydegree)
            || "DEG04".equals(qualitydegree)) {
          // // // get reson for first
          PaperfirstCriteria = OBDal.getInstance().createCriteria(PaperRefuse.class);
          PaperfirstCriteria.add(Expression.eq(PaperRefuse.PROPERTY_REFNAME, firstresonfars));
          currentfirstpaperRefuse = PaperfirstCriteria.list().get(0);

          newproduction.setFirstrefuse(currentfirstpaperRefuse);
          // second
          OBCriteria<PaperRefuse> PapersecondCriteria = OBDal.getInstance()
              .createCriteria(PaperRefuse.class);
          PapersecondCriteria.add(Expression.eq(PaperRefuse.PROPERTY_REFNAME, secondresonfars));
          currentsecondresonRefuse = PapersecondCriteria.list().get(0);
          newproduction.setSecondrefuse(currentsecondresonRefuse);

          // } else if (("DEG02".equals(qualitydegree) || "DEG03".equals(qualitydegree)
          // || "DEG04".equals(qualitydegree)) && secondresonfars == null) {
          // newproduction.setFirstrefuse(currentfirstpaperRefuse);
          // newproduction.setSecondrefuse(null);
        } else {
          newproduction.setFirstrefuse(null);
          newproduction.setSecondrefuse(null);

        }

        OBDal.getInstance().getSession().save(newproduction);

        //

        //

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

    catch (

    Exception e) {
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
