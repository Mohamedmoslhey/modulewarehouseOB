package com.ibox.paper.ad_handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.query.Query;
import org.openbravo.base.provider.OBProvider;
import org.openbravo.client.application.process.BaseProcessActionHandler;
import org.openbravo.common.actionhandler.RMShipmentPickEditLines;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBDal;
import org.openbravo.dal.service.OBDao;
import org.openbravo.erpCommon.utility.OBMessageUtils;
import org.openbravo.model.ad.system.Client;
import org.openbravo.model.common.businesspartner.BusinessPartner;
import org.openbravo.model.common.enterprise.Locator;
import org.openbravo.model.common.enterprise.Organization;
import org.openbravo.model.common.plm.Product;
import org.openbravo.model.common.uom.UOM;
import org.openbravo.model.materialmgmt.onhandquantity.StorageDetail;
import org.openbravo.model.materialmgmt.transaction.InternalMovement;
import org.openbravo.model.materialmgmt.transaction.InternalMovementLine;
import org.openbravo.service.db.DbUtility;

import com.ibox.paper.data.PaperMachine;
import com.ibox.paper.data.PaperRefuse;
import com.ibox.paper.data.QualityDegree;

public class PickBkaraRoll extends BaseProcessActionHandler {

  private static Logger log = Logger.getLogger(RMShipmentPickEditLines.class);

  @Override
  protected JSONObject doExecute(Map<String, Object> parameters, String content) {
    JSONObject jsonRequest = null;
    OBContext.setAdminMode();
    try {
      jsonRequest = new JSONObject(content);
      log.debug(jsonRequest);
      // When the focus is NOT in the tab of the button (i.e. any child tab) and the tab does not
      // contain any record, the inpmInoutId parameter contains "null" string. Use M_InOut_ID
      // instead because it always contains the id of the selected goods.
      // Issue 20585: https://issues.openbravo.com/view.php?id=20585
      final String strInOutId = jsonRequest.getString("M_Locator_ID");
      Locator inOcator = OBDal.getInstance().get(Locator.class, strInOutId);
      if (inOcator != null) {
        List<String> idList = OBDao
            .getIDListFromOBObject(inOcator.getMaterialMgmtStorageDetailList());
        createInOutLines(jsonRequest, idList);
      }
      JSONObject errorMessage = new JSONObject();
      errorMessage.put("severity", "success");
      errorMessage.put("text", "Lines created successfully");
      jsonRequest.put("message", errorMessage);

    } catch (Exception e) {
      log.error(e.getMessage(), e);

      try {
        jsonRequest = new JSONObject();
        Throwable ex = DbUtility.getUnderlyingSQLException(e);
        String message = OBMessageUtils.translateError(ex.getMessage()).getMessage();
        JSONObject errorMessage = new JSONObject();
        errorMessage.put("severity", "error");
        errorMessage.put("text", message);
        jsonRequest.put("message", errorMessage);

      } catch (Exception e2) {
        log.error(e.getMessage(), e2);
        // do nothing, give up
      }
    } finally {
      OBContext.restorePreviousMode();
    }
    return jsonRequest;
  }

  private void createInOutLines(JSONObject jsonRequest, List<String> idList) throws JSONException {
    JSONArray selectedLines = jsonRequest.getJSONObject("_params").getJSONObject("grid")
        .getJSONArray("_selection");
    final String strInOutId = jsonRequest.getString("M_Locator_ID");
    Locator inOcator = OBDal.getInstance().get(Locator.class, strInOutId);
    Locator MainOcator = OBDal.getInstance().get(Locator.class, "CB1902C1F89147B182B1999686316DD1");

    Long lineNo = 0L;
    Long totalItems = 0L;
    double totalWeight = 0;
    List<StorageDetail> linesList = inOcator.getMaterialMgmtStorageDetailList();
    List<StorageDetail> MainList = MainOcator.getMaterialMgmtStorageDetailList();
    //
    InternalMovement inMovement = OBProvider.getInstance().get(InternalMovement.class);
    List<InternalMovement> linesMList = new ArrayList<InternalMovement>();
    List<InternalMovementLine> internalMovementLine = inMovement
        .getMaterialMgmtInternalMovementLineList();
    // set Internal M ovement
    //
    String mId = inMovement.getId();

    String hql = "select h.documentno from M_Movement h ORDER BY movementdate DESC LIMIT 1";

    Query query = OBDal.getInstance().getSession().createSQLQuery(hql);

    Integer documentno = Integer.parseInt((String) query.list().get(0));

    //

    inMovement.setOrganization(
        OBDal.getInstance().get(Organization.class, "5B0384D9A3164F1191BAFAFAB53CC1FF"));
    inMovement.setClient(OBDal.getInstance().get(Client.class, "69204F7FB1FC41D791F8684AA908B2F2"));

    if (inOcator.getId().equals("F39E1BB619F7418F950FE180D0610AA7")) {
      inMovement.setName("تحويل من المخزن التام الى مخزن السليتر");
    } else if (inOcator.getId().equals("44BB82F8A41E4A129F66A56CF402C4F3")) {
      inMovement.setName("تحويل من المخزن التام الى مخزن المخلفات");
    }
    inMovement.setProcessed(false);
    inMovement.setProcessNow(false);
    inMovement.setPosted("N");
    inMovement.setMoveBetweenLocators(false);
    inMovement.setMovementDate(new Date());
    inMovement.setDocumentNo(String.valueOf(documentno + 1));
    OBDal.getInstance().getSession().save(inMovement);
    OBDal.getInstance().commitAndClose();

    ///
    outerloop: for (long i = 0; i < selectedLines.length(); i++) {
      JSONObject selectedLine = selectedLines.getJSONObject((int) i);
      log.debug(selectedLine);
      for (StorageDetail line : linesList) {
        if (line.getPAPERJumboCode().trim()
            .equals(selectedLine.getString("pAPERJumboCode").trim())) {
          continue outerloop;
        }
      }

      final StorageDetail StorageDetailLine = OBProvider.getInstance().get(StorageDetail.class);

      StorageDetailLine
          .setProduct(OBDal.getInstance().get(Product.class, selectedLine.getString("product")));
      StorageDetailLine.setStorageBin(inOcator);
      StorageDetailLine.setQuantityOnHand(new BigDecimal("1"));
      StorageDetailLine.setQuantityInDraftTransactions(new BigDecimal("0"));
      StorageDetailLine.setUOM(OBDal.getInstance().get(UOM.class, selectedLine.getString("uOM")));

      StorageDetailLine.setOrganization(inOcator.getOrganization());
      StorageDetailLine.setPAPERPaperClient(OBDal.getInstance().get(BusinessPartner.class,
          selectedLine.getString("pAPERPaperClient")));
      StorageDetailLine.setPAPERJumboCode(selectedLine.getString("pAPERJumboCode"));
      //

      StorageDetailLine.setPAPERJumboCode(selectedLine.getString("pAPERProductType"));
      StorageDetailLine.setPAPERQualityDegree(OBDal.getInstance().get(QualityDegree.class,
          selectedLine.getString("pAPERQualityDegree")));
      StorageDetailLine.setPAPERGsm(new Long(selectedLine.getString("pAPERGsm")));
      StorageDetailLine
          .setPAPERBakaraWidth(new BigDecimal(selectedLine.getString("pAPERBakaraWidth")));
      StorageDetailLine
          .setPAPERBakkaraQotr(new BigDecimal(selectedLine.getString("pAPERBakkaraQotr")));
      StorageDetailLine.setPAPERPaperMachine(
          OBDal.getInstance().get(PaperMachine.class, selectedLine.getString("pAPERPaperMachine")));

      StorageDetailLine
          .setPAPERBakaraWeight(new BigDecimal(selectedLine.getString("pAPERBakaraWeight")));
      if (selectedLine.getString("paperNotes").equals("null")
          || selectedLine.getString("paperNotes").isEmpty()) {
        StorageDetailLine.setPaperNotes(null);
      } else {
        StorageDetailLine.setPaperNotes(selectedLine.getString("paperNotes"));
      }

      if (selectedLine.getString("paperFirstrefuse").equals("null")
          || selectedLine.getString("paperFirstrefuse").isEmpty()) {
        StorageDetailLine.setPaperFirstrefuse(null);

      } else {
        StorageDetailLine.setPaperFirstrefuse(
            OBDal.getInstance().get(PaperRefuse.class, selectedLine.getString("paperFirstrefuse")));
      }
      if (selectedLine.getString("paperSecondrefuse").equals("null")
          || selectedLine.getString("paperSecondrefuse").isEmpty()) {
        StorageDetailLine.setPaperSecondrefuse(null);
      } else {
        StorageDetailLine.setPaperSecondrefuse(OBDal.getInstance().get(PaperRefuse.class,
            selectedLine.getString("paperSecondrefuse")));
      }
      StorageDetailLine.setPaperRejectreason(selectedLine.getString("paperRejectreason"));
      if (selectedLine.getString("paperIsgard").equals("null")
          || selectedLine.getString("paperIsgard").isEmpty()) {
        StorageDetailLine.setPaperIsgard(false);

      } else {
        StorageDetailLine.setPaperIsgard(true);
      }
      if (selectedLine.getString("paperSheretno").equals("null")
          || selectedLine.getString("paperSheretno").isEmpty()) {
        StorageDetailLine.setPaperSheretno(" ");

      } else {
        StorageDetailLine.setPaperSheretno(selectedLine.getString("paperSheretno"));
      }

      OBDal.getInstance().save(StorageDetailLine);
      OBDal.getInstance().flush();
      linesList.add(StorageDetailLine);
      String paperJ = selectedLine.getString("pAPERJumboCode");
      String strhQl = "update m_storage_detail set qtyonhand = 0 where m_storage_detail.M_Locator_ID = 'CB1902C1F89147B182B1999686316DD1' ";

      strhQl = strhQl + "and em_paper_jumbo_code =:paperJ ";

      Query huery = OBDal.getInstance().getSession().createSQLQuery(strhQl);
      huery.setParameter("paperJ", selectedLine.getString("pAPERJumboCode"));
      // int moi =
      huery.executeUpdate();
      /// for Main Locator change onhand to zero
      for (InternalMovementLine line : internalMovementLine) {
        if (line.getPAPERJumboCode().trim()
            .equals(selectedLine.getString("pAPERJumboCode").trim())) {
          continue outerloop;
        }
      }
      InternalMovementLine shipmentDetailList = new InternalMovementLine();
      shipmentDetailList
          .setProduct(OBDal.getInstance().get(Product.class, selectedLine.getString("product")));
      shipmentDetailList.setMovementQuantity(new BigDecimal("1"));

      // internalMovement.setQuantityInDraftTransactions(new BigDecimal("0"));
      shipmentDetailList.setUOM(OBDal.getInstance().get(UOM.class, selectedLine.getString("uOM")));
      shipmentDetailList.setMovement(inMovement);
      shipmentDetailList.setOrganization(inOcator.getOrganization());
      shipmentDetailList.setPaperClient(OBDal.getInstance().get(BusinessPartner.class,
          selectedLine.getString("pAPERPaperClient")));
      shipmentDetailList.setPAPERJumboCode(selectedLine.getString("pAPERJumboCode"));
      //
      shipmentDetailList.setStorageBin(MainOcator);
      if (inOcator.getId().equals("F39E1BB619F7418F950FE180D0610AA7")) {
        shipmentDetailList.setNewStorageBin(
            OBDal.getInstance().get(Locator.class, "F39E1BB619F7418F950FE180D0610AA7"));
      } else if (inOcator.getId().equals("44BB82F8A41E4A129F66A56CF402C4F3")) {
        shipmentDetailList.setNewStorageBin(
            OBDal.getInstance().get(Locator.class, "44BB82F8A41E4A129F66A56CF402C4F3"));
      }

      shipmentDetailList.setPAPERJumboCode(selectedLine.getString("pAPERProductType"));
      shipmentDetailList.setPAPERQualityDegree(OBDal.getInstance().get(QualityDegree.class,
          selectedLine.getString("pAPERQualityDegree")));
      shipmentDetailList.setPaperGsm(new Long(selectedLine.getString("pAPERGsm")));
      shipmentDetailList
          .setPaperBakarawidth(new BigDecimal(selectedLine.getString("pAPERBakaraWidth")));
      shipmentDetailList
          .setPAPERBakkaraQotr(new BigDecimal(selectedLine.getString("pAPERBakkaraQotr")));
      shipmentDetailList.setPaperMachine(
          OBDal.getInstance().get(PaperMachine.class, selectedLine.getString("pAPERPaperMachine")));

      shipmentDetailList
          .setPaperBakawieght(new BigDecimal(selectedLine.getString("pAPERBakaraWeight")));
      if (selectedLine.getString("paperNotes").equals("null")
          || selectedLine.getString("paperNotes").isEmpty()) {
        shipmentDetailList.setPaperNotes(null);
      } else {
        shipmentDetailList.setPaperNotes(selectedLine.getString("paperNotes"));
      }

      OBDal.getInstance().save(shipmentDetailList);
      OBDal.getInstance().flush();
      internalMovementLine.add(shipmentDetailList);

    } // end of outer loop

    inMovement.setMaterialMgmtInternalMovementLineList(internalMovementLine);
    OBDal.getInstance().save(inMovement);
    OBDal.getInstance().commitAndClose();

    inOcator.setMaterialMgmtStorageDetailList(linesList);
    OBDal.getInstance().save(inOcator);
    OBDal.getInstance().flush();

  }
}
