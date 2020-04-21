package com.ibox.paper.ad_handler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.openbravo.base.provider.OBProvider;
import org.openbravo.client.application.process.BaseProcessActionHandler;
import org.openbravo.common.actionhandler.RMShipmentPickEditLines;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBDal;
import org.openbravo.dal.service.OBDao;
import org.openbravo.erpCommon.utility.OBMessageUtils;
import org.openbravo.model.common.businesspartner.BusinessPartner;
import org.openbravo.model.common.enterprise.Locator;
import org.openbravo.model.common.plm.Product;
import org.openbravo.model.common.uom.UOM;
import org.openbravo.model.materialmgmt.transaction.ShipmentInOut;
import org.openbravo.model.materialmgmt.transaction.ShipmentInOutLine;
import org.openbravo.service.db.DbUtility;

import com.ibox.paper.data.PaperMachine;
import com.ibox.paper.data.QualityDegree;

public class PickAndProcessGoodsShipment extends BaseProcessActionHandler {
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
      final String strInOutId = jsonRequest.getString("M_InOut_ID");
      ShipmentInOut inOut = OBDal.getInstance().get(ShipmentInOut.class, strInOutId);
      if (inOut != null) {
        List<String> idList = OBDao
            .getIDListFromOBObject(inOut.getMaterialMgmtShipmentInOutLineList());
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
    final String strInOutId = jsonRequest.getString("M_InOut_ID");
    ShipmentInOut inOut = OBDal.getInstance().get(ShipmentInOut.class, strInOutId);

    Long lineNo = 0L;
    Long totalItems = 0L;
    double totalWeight = 0;
    List<ShipmentInOutLine> linesList = inOut.getMaterialMgmtShipmentInOutLineList();
    outerloop: for (long i = 0; i < selectedLines.length(); i++) {
      JSONObject selectedLine = selectedLines.getJSONObject((int) i);
      log.debug(selectedLine);
      for (ShipmentInOutLine line : linesList) {
        if (line.getPAPERJumboCode().trim()
            .equals(selectedLine.getString("pAPERJumboCode").trim())) {
          continue outerloop;
        }
      }
      final ShipmentInOutLine shipmentInOutLine = OBProvider.getInstance()
          .get(ShipmentInOutLine.class);

      shipmentInOutLine
          .setProduct(OBDal.getInstance().get(Product.class, selectedLine.getString("product")));
      shipmentInOutLine.setShipmentReceipt(inOut);
      lineNo = lineNo + 10L;
      shipmentInOutLine.setLineNo(lineNo);
      shipmentInOutLine.setMovementQuantity(new BigDecimal("1"));
      shipmentInOutLine.setUOM(OBDal.getInstance().get(UOM.class, selectedLine.getString("uOM")));
      shipmentInOutLine.setReinvoice(false);
      shipmentInOutLine.setDescriptionOnly(false);
      shipmentInOutLine.setOrganization(inOut.getOrganization());
      shipmentInOutLine.setBusinessPartner(inOut.getBusinessPartner());
      shipmentInOutLine.setPAPERJumboCode(selectedLine.getString("pAPERJumboCode"));
      shipmentInOutLine.setPAPERProductType(selectedLine.getString("pAPERProductType"));
      shipmentInOutLine.setPaperQualityDegree(OBDal.getInstance().get(QualityDegree.class,
          selectedLine.getString("pAPERQualityDegree")));
      shipmentInOutLine.setPAPERGSM(new BigDecimal(selectedLine.getString("pAPERGsm")));
      shipmentInOutLine
          .setPaperBakarawidth(new BigDecimal(selectedLine.getString("pAPERBakaraWidth")));
      shipmentInOutLine
          .setPAPERBakkaraQotr(new BigDecimal(selectedLine.getString("pAPERBakkaraQotr")));
      shipmentInOutLine
          .setPaperBakawieght(new BigDecimal(selectedLine.getString("pAPERBakaraWeight")));
      shipmentInOutLine.setPaperMachine(
          OBDal.getInstance().get(PaperMachine.class, selectedLine.getString("pAPERPaperMachine")));
      shipmentInOutLine.setPaperClient(OBDal.getInstance().get(BusinessPartner.class,
          selectedLine.getString("pAPERPaperClient")));
      shipmentInOutLine.setStorageBin(
          OBDal.getInstance().get(Locator.class, selectedLine.getString("storageBin")));
      if (selectedLine.getString("paperNotes").equals("null")
          || selectedLine.getString("paperNotes").isEmpty()) {
        shipmentInOutLine.setPaperNotes("");
      } else {
        shipmentInOutLine.setPaperNotes(selectedLine.getString("paperNotes"));
      }
      // totalItems++;
      // if (shipmentInOutLine.getPaperBakawieght() != null
      // || shipmentInOutLine.getPaperBakawieght().toString().isEmpty()) {
      // totalWeight += shipmentInOutLine.getPaperBakawieght().doubleValue();
      // }

      OBDal.getInstance().save(shipmentInOutLine);
      OBDal.getInstance().flush();
      linesList.add(shipmentInOutLine);
    }
    inOut.setMaterialMgmtShipmentInOutLineList(linesList);
    inOut.setPAPERNumberOfRoller(0L);
    inOut.setPAPERTotalShipmentWeight(new BigDecimal("0"));
    for (ShipmentInOutLine line : inOut.getMaterialMgmtShipmentInOutLineList()) {
      totalItems++;
      inOut.setPAPERTotalShipmentWeight(
          inOut.getPAPERTotalShipmentWeight().add(line.getPaperBakawieght()));
      inOut.setPAPERNumberOfRoller(totalItems);
    }
    OBDal.getInstance().save(inOut);
    OBDal.getInstance().flush();

  }
}
