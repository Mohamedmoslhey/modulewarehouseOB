package com.ibox.paper.ad_handler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.criterion.Restrictions;
import org.openbravo.base.exception.OBException;
import org.openbravo.base.provider.OBProvider;
import org.openbravo.client.kernel.BaseActionHandler;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;
import org.openbravo.model.ad.domain.List;
import org.openbravo.model.ad.domain.Reference;
import org.openbravo.model.ad.process.ProcessInstance;
import org.openbravo.model.ad.ui.Process;
import org.openbravo.model.ad.utility.Sequence;
import org.openbravo.model.common.enterprise.DocumentType;
import org.openbravo.model.common.enterprise.Locator;
import org.openbravo.model.common.enterprise.Organization;
import org.openbravo.model.common.enterprise.Warehouse;
import org.openbravo.model.common.plm.Product;
import org.openbravo.model.common.plm.ProductCategory;
import org.openbravo.model.common.uom.UOM;
import org.openbravo.model.financialmgmt.tax.TaxCategory;
import org.openbravo.model.materialmgmt.transaction.ShipmentInOut;
import org.openbravo.model.materialmgmt.transaction.ShipmentInOutLine;

import com.ibox.paper.data.ProductionQuality;

public class ManualProcessActionHandler extends BaseActionHandler {

  @Override
  protected JSONObject execute(Map<String, Object> parameters, String data) {
    try {
      final JSONObject jsonData = new JSONObject(data);
      final JSONArray orderIds = jsonData.getJSONArray("orders");
      final String action = jsonData.getString("action");

      for (int i = 0; i < orderIds.length(); i++) {
        final String orderId = orderIds.getString(i);

        // get the order
        final ProductionQuality productionQuality = OBDal.getInstance().get(ProductionQuality.class,
            orderId);

        // and add or subtract 1
        /*
         * BigDecimal originalValue = productionQuality.getGsm(); if (originalValue == null) {
         * originalValue = new BigDecimal("0"); }
         * 
         * BigDecimal finalValue = null; if ("sum".equals(action)) { finalValue =
         * originalValue.add(new BigDecimal("1")); }
         * 
         * productionQuality.setGsm(finalValue);
         */
        if ("quality".equals(action)) {
          productionQuality.setCompleted(true);
          // productionQuality.setStatus("1");
          OBDal.getInstance().save(productionQuality);
          OBDal.getInstance().flush();
          // OBDal.getInstance().commitAndClose();
          /////////////////////////////////////////////////////////////////////////////////

          // add the new Bakkara as product
          final OBCriteria<UOM> UOMCriteria = OBDal.getInstance().createCriteria(UOM.class);
          UOMCriteria.add(Restrictions.eq("name", "Each"));
          UOMCriteria.add(Restrictions.eq("eDICode", "PQ"));
          final java.util.List<UOM> uoms = UOMCriteria.list();
          final UOM uom = uoms.get(0);

          final OBCriteria<ProductCategory> PCCriteria = OBDal.getInstance()
              .createCriteria(ProductCategory.class);
          PCCriteria.add(Restrictions.eq("searchKey", "PQCAT"));
          final java.util.List<ProductCategory> productCategories = PCCriteria.list();
          final ProductCategory productCategory = productCategories.get(0);

          final OBCriteria<TaxCategory> TCCriteria = OBDal.getInstance()
              .createCriteria(TaxCategory.class);
          TCCriteria.add(Restrictions.eq("name", "PQTAXC"));
          final java.util.List<TaxCategory> taxCategories = TCCriteria.list();
          final TaxCategory taxCategory = taxCategories.get(0);

          ///////////////////////////////////////////////////////////////////////////////
          final Product product = OBProvider.getInstance().get(Product.class);

          product.setOrganization(OBDal.getInstance().get(Organization.class, "0"));
          product.setSearchKey("Bakara_" + productionQuality.getBakaracode());
          product.setName(productionQuality.getBakaracode());
          product.setBillOfMaterials(false);
          product.setGeneric(false);
          product.setSummaryLevel(false);
          product.setUOM(uom);
          product.setPurchase(true);
          product.setSale(true);
          product.setProductCategory(productCategory);
          product.setTaxCategory(taxCategory);
          product.setBookUsingPurchaseOrderPrice(false);
          product.setBOMVerified(false);
          product.setPrintDetailsOnInvoice(false);
          product.setPrintDetailsOnPickList(false);
          product.setProcessNow(false);
          product.setProductType("I");
          product.setCalculated(false);
          product.setProduction(false);
          product.setQuantityvariable(false);
          product.setDeferredRevenue(false);
          product.setDeferredexpense(false);
          product.setStocked(true);
          OBDal.getInstance().save(product);
          OBDal.getInstance().flush();
          // OBDal.getInstance().commitAndClose();

          /////////////////////////////////////////////////////
          // add new record in goods receipt (Header and line).
          try {
            OBContext.getOBContext();
            OBContext.setAdminMode(true);
            final ShipmentInOut inOut = OBProvider.getInstance().get(ShipmentInOut.class);

            final OBCriteria<Organization> PaperOrgCriteria = OBDal.getInstance()
                .createCriteria(Organization.class);
            PaperOrgCriteria.add(Restrictions.eq("searchKey", "Paper"));
            PaperOrgCriteria.add(Restrictions.eq("name", "Paper"));
            final java.util.List<Organization> Paperorganizations = PaperOrgCriteria.list();
            final Organization PaperOrganization = Paperorganizations.get(0);

            final OBCriteria<DocumentType> DocCriteria = OBDal.getInstance()
                .createCriteria(DocumentType.class);
            DocCriteria.add(Restrictions.eq("organization", PaperOrganization));
            DocCriteria.add(Restrictions.eq("name", "PQ Receipt"));
            final java.util.List<DocumentType> documentTypes = DocCriteria.list();
            final DocumentType documentType = documentTypes.get(0);

            final OBCriteria<Warehouse> WhloCriteria = OBDal.getInstance()
                .createCriteria(Warehouse.class);
            WhloCriteria.add(Restrictions.eq("organization", PaperOrganization));
            WhloCriteria.add(Restrictions.eq("searchKey", "P10"));
            final java.util.List<Warehouse> Warehouses = WhloCriteria.list();
            final Warehouse warehouse = Warehouses.get(0);

            Long seq = documentType.getDocumentSequence().getNextAssignedNumber()
                + documentType.getDocumentSequence().getIncrementBy();

            final Sequence sequence = documentType.getDocumentSequence();
            sequence.setNextAssignedNumber(seq);
            OBDal.getInstance().save(sequence);
            OBDal.getInstance().flush();

            // start adding the line
            inOut.setOrganization(PaperOrganization);
            inOut.setDocumentType(documentType);
            inOut.setWarehouse(warehouse);
            inOut.setMovementDate(new Date());
            inOut.setAccountingDate(new Date());
            inOut.setDocumentNo(seq + "");
            inOut.setBusinessPartner(productionQuality.getBusinessPartner());
            inOut.setPartnerAddress(
                productionQuality.getBusinessPartner().getBusinessPartnerLocationList().get(0));
            inOut.setSalesTransaction(false);
            OBDal.getInstance().save(inOut);
            OBDal.getInstance().flush();

            final ShipmentInOutLine shipmentInOutLine = OBProvider.getInstance()
                .get(ShipmentInOutLine.class);

            final OBCriteria<Product> ProdCriteria = OBDal.getInstance()
                .createCriteria(Product.class);
            ProdCriteria.add(Restrictions.eq("name", productionQuality.getBakaracode()));
            final java.util.List<Product> products = ProdCriteria.list();
            final Product product_shipmentInOutLine = products.get(0);

            final OBCriteria<Reference> RefCriteria = OBDal.getInstance()
                .createCriteria(Reference.class);
            RefCriteria.add(Restrictions.eq("name", "paper_ProductType"));
            final java.util.List<Reference> references = RefCriteria.list();
            final Reference reference = references.get(0);

            final OBCriteria<List> LstCriteria = OBDal.getInstance().createCriteria(List.class);
            LstCriteria.add(Restrictions.eq("reference", reference));
            LstCriteria.add(Restrictions.eq("searchKey", productionQuality.getProductType()));
            final java.util.List<List> lists = LstCriteria.list();
            final List list = lists.get(0);

            final OBCriteria<Locator> LocatorCriteria = OBDal.getInstance()
                .createCriteria(Locator.class);
            LocatorCriteria.add(Restrictions.eq("warehouse", warehouse));
            LocatorCriteria.add(Restrictions.eq("rowX", "0"));
            LocatorCriteria.add(Restrictions.eq("stackY", "0"));
            LocatorCriteria.add(Restrictions.eq("levelZ", "0"));
            final java.util.List<Locator> Locators = LocatorCriteria.list();
            final Locator locator = Locators.get(0);

            shipmentInOutLine.setProduct(product_shipmentInOutLine);
            shipmentInOutLine.setShipmentReceipt(inOut);
            shipmentInOutLine.setLineNo(10L);
            shipmentInOutLine.setMovementQuantity(new BigDecimal("1"));
            shipmentInOutLine.setUOM(uom);
            shipmentInOutLine.setReinvoice(false);
            shipmentInOutLine.setDescriptionOnly(false);
            shipmentInOutLine.setOrganization(PaperOrganization);
            shipmentInOutLine.setBusinessPartner(productionQuality.getBusinessPartner());
            shipmentInOutLine.setPAPERJumboCode(productionQuality.getBakaracode());
            shipmentInOutLine.setPAPERProductType(productionQuality.getProductType());
            shipmentInOutLine.setPaperQualityDegree(productionQuality.getPaperQualityDegree());
            shipmentInOutLine.setPAPERGSM(productionQuality.getGsm());
            shipmentInOutLine.setPaperBakarawidth(productionQuality.getBakarawidth());
            shipmentInOutLine.setPAPERBakkaraQotr(productionQuality.getBakaraqotr());
            shipmentInOutLine
                .setPaperBakawieght(new BigDecimal(productionQuality.getBakaraweight()));
            shipmentInOutLine.setPaperMachine(productionQuality.getPaperMachine());
            if (productionQuality.getBusinessPartner() != null) {
              shipmentInOutLine.setPaperClient(productionQuality.getBusinessPartner());
            }
            shipmentInOutLine.setStorageBin(locator);

            OBDal.getInstance().save(shipmentInOutLine);
            OBDal.getInstance().flush();
            // OBDal.getInstance().commitAndClose();

            /////////////////////////////////////////////////////
            // Call stored procedure to complete the line

            final Process process = OBDal.getInstance().get(Process.class, "109");

            // Create the pInstance
            final ProcessInstance pInstance = OBProvider.getInstance().get(ProcessInstance.class);
            // sets its process
            pInstance.setProcess(process);
            // must be set to true
            pInstance.setActive(true);
            pInstance.setRecordID(inOut.getId());
            // get the user from the context
            pInstance.setUserContact(OBContext.getOBContext().getUser());

            /*
             * // now create a parameter and set its values final Parameter parameter =
             * OBProvider.getInstance().get(Parameter.class); parameter.setSequenceNumber("1");
             * parameter.setParameterName("Selection"); parameter.setString("Y");
             * 
             * // set both sides of the bidirectional association
             * pInstance.getADParameterList().add(parameter);
             * parameter.setProcessInstance(pInstance);
             */

            // persist to the db
            OBDal.getInstance().save(pInstance);

            // flush, this gives pInstance an ID
            OBDal.getInstance().flush();

            System.err.println(pInstance.getId());

            // call the SP
            try {
              // first get a connection
              final Connection connection = OBDal.getInstance().getConnection();
              final PreparedStatement ps = connection
                  .prepareStatement("SELECT * FROM m_inout_post0(?)");
              ps.setString(1, pInstance.getId());
              ps.execute();
            } catch (Exception e) {
              throw new IllegalStateException(e);
            }

            // refresh the pInstance as the SP has changed it
            OBDal.getInstance().getSession().refresh(pInstance);

            System.err.println(pInstance.getResult());
            System.err.println(pInstance.getErrorMsg());

            OBDal.getInstance().commitAndClose();
            /////////////////////////////////////////////////////

          } finally {
            OBContext.restorePreviousMode();
          }

        }
        if ("qualityReturn".equals(action)) {
          productionQuality.setCompleted(true);
          OBDal.getInstance().save(productionQuality);
          OBDal.getInstance().flush();
          // OBDal.getInstance().commitAndClose();
          /////////////////////////////////////////////////////////////////////////////////

          // add the new Bakkara as product
          final OBCriteria<UOM> UOMCriteria = OBDal.getInstance().createCriteria(UOM.class);
          UOMCriteria.add(Restrictions.eq("name", "Each"));
          final java.util.List<UOM> uoms = UOMCriteria.list();
          final UOM uom = uoms.get(0);

          final OBCriteria<ProductCategory> PCCriteria = OBDal.getInstance()
              .createCriteria(ProductCategory.class);
          PCCriteria.add(Restrictions.eq("searchKey", "PQCAT"));
          final java.util.List<ProductCategory> productCategories = PCCriteria.list();
          final ProductCategory productCategory = productCategories.get(0);

          final OBCriteria<TaxCategory> TCCriteria = OBDal.getInstance()
              .createCriteria(TaxCategory.class);
          TCCriteria.add(Restrictions.eq("name", "PQTAXC"));
          final java.util.List<TaxCategory> taxCategories = TCCriteria.list();
          final TaxCategory taxCategory = taxCategories.get(0);

          ///////////////////////////////////////////////////////////////////////////////
          final Product product = OBProvider.getInstance().get(Product.class);

          product.setOrganization(OBDal.getInstance().get(Organization.class, "0"));
          product.setSearchKey("Bakara_" + productionQuality.getBakaracode());
          product.setName(productionQuality.getBakaracode());
          product.setBillOfMaterials(false);
          product.setGeneric(false);
          product.setSummaryLevel(false);
          product.setUOM(uom);
          product.setPurchase(true);
          product.setSale(true);
          product.setProductCategory(productCategory);
          product.setTaxCategory(taxCategory);
          product.setBookUsingPurchaseOrderPrice(false);
          product.setBOMVerified(false);
          product.setPrintDetailsOnInvoice(false);
          product.setPrintDetailsOnPickList(false);
          product.setProcessNow(false);
          product.setProductType("I");
          product.setCalculated(false);
          product.setProduction(false);
          product.setQuantityvariable(false);
          product.setDeferredRevenue(false);
          product.setDeferredexpense(false);
          product.setStocked(true);
          OBDal.getInstance().save(product);
          OBDal.getInstance().flush();
          // OBDal.getInstance().commitAndClose();

          /////////////////////////////////////////////////////
          // add new record in goods receipt (Header and line).
          try {
            OBContext.getOBContext();
            OBContext.setAdminMode(true);
            final ShipmentInOut inOut = OBProvider.getInstance().get(ShipmentInOut.class);

            final OBCriteria<Organization> PaperOrgCriteria = OBDal.getInstance()
                .createCriteria(Organization.class);
            PaperOrgCriteria.add(Restrictions.eq("searchKey", "Paper"));
            PaperOrgCriteria.add(Restrictions.eq("name", "Paper"));
            final java.util.List<Organization> Paperorganizations = PaperOrgCriteria.list();
            final Organization PaperOrganization = Paperorganizations.get(0);

            final OBCriteria<DocumentType> DocCriteria = OBDal.getInstance()
                .createCriteria(DocumentType.class);
            DocCriteria.add(Restrictions.eq("organization", PaperOrganization));
            DocCriteria.add(Restrictions.eq("name", "PQ Receipt"));
            final java.util.List<DocumentType> documentTypes = DocCriteria.list();
            final DocumentType documentType = documentTypes.get(0);

            final OBCriteria<Warehouse> WhloCriteria = OBDal.getInstance()
                .createCriteria(Warehouse.class);
            WhloCriteria.add(Restrictions.eq("organization", PaperOrganization));
            WhloCriteria.add(Restrictions.eq("searchKey", "P20"));
            final java.util.List<Warehouse> Warehouses = WhloCriteria.list();
            final Warehouse warehouse = Warehouses.get(0);

            Long seq = documentType.getDocumentSequence().getNextAssignedNumber()
                + documentType.getDocumentSequence().getIncrementBy();

            final Sequence sequence = documentType.getDocumentSequence();
            sequence.setNextAssignedNumber(seq);
            OBDal.getInstance().save(sequence);
            OBDal.getInstance().flush();

            // start adding the line
            inOut.setOrganization(PaperOrganization);
            inOut.setDocumentType(documentType);
            inOut.setWarehouse(warehouse);
            inOut.setMovementDate(new Date());
            inOut.setAccountingDate(new Date());
            inOut.setDocumentNo(seq + "");
            inOut.setBusinessPartner(productionQuality.getBusinessPartner());
            inOut.setPartnerAddress(
                productionQuality.getBusinessPartner().getBusinessPartnerLocationList().get(0));
            inOut.setSalesTransaction(false);
            OBDal.getInstance().save(inOut);
            OBDal.getInstance().flush();

            final ShipmentInOutLine shipmentInOutLine = OBProvider.getInstance()
                .get(ShipmentInOutLine.class);

            final OBCriteria<Product> ProdCriteria = OBDal.getInstance()
                .createCriteria(Product.class);
            ProdCriteria.add(Restrictions.eq("name", productionQuality.getBakaracode()));
            final java.util.List<Product> products = ProdCriteria.list();
            final Product product_shipmentInOutLine = products.get(0);

            final OBCriteria<Reference> RefCriteria = OBDal.getInstance()
                .createCriteria(Reference.class);
            RefCriteria.add(Restrictions.eq("name", "paper_ProductType"));
            final java.util.List<Reference> references = RefCriteria.list();
            final Reference reference = references.get(0);

            final OBCriteria<List> LstCriteria = OBDal.getInstance().createCriteria(List.class);
            LstCriteria.add(Restrictions.eq("reference", reference));
            LstCriteria.add(Restrictions.eq("searchKey", productionQuality.getProductType()));
            final java.util.List<List> lists = LstCriteria.list();
            final List list = lists.get(0);

            final OBCriteria<Locator> LocatorCriteria = OBDal.getInstance()
                .createCriteria(Locator.class);
            LocatorCriteria.add(Restrictions.eq("warehouse", warehouse));
            LocatorCriteria.add(Restrictions.eq("rowX", "0"));
            LocatorCriteria.add(Restrictions.eq("stackY", "0"));
            LocatorCriteria.add(Restrictions.eq("levelZ", "0"));
            final java.util.List<Locator> Locators = LocatorCriteria.list();
            final Locator locator = Locators.get(0);

            shipmentInOutLine.setProduct(product_shipmentInOutLine);
            shipmentInOutLine.setShipmentReceipt(inOut);
            shipmentInOutLine.setLineNo(10L);
            shipmentInOutLine.setMovementQuantity(new BigDecimal("1"));
            shipmentInOutLine.setUOM(uom);
            shipmentInOutLine.setReinvoice(false);
            shipmentInOutLine.setDescriptionOnly(false);
            shipmentInOutLine.setOrganization(PaperOrganization);
            shipmentInOutLine.setBusinessPartner(productionQuality.getBusinessPartner());
            shipmentInOutLine.setPAPERJumboCode(productionQuality.getBakaracode());
            shipmentInOutLine.setPAPERProductType(productionQuality.getProductType());
            shipmentInOutLine.setPaperQualityDegree(productionQuality.getPaperQualityDegree());
            shipmentInOutLine.setPAPERGSM(productionQuality.getGsm());
            shipmentInOutLine.setPaperBakarawidth(productionQuality.getBakarawidth());
            shipmentInOutLine.setPAPERBakkaraQotr(productionQuality.getBakaraqotr());
            shipmentInOutLine
                .setPaperBakawieght(new BigDecimal(productionQuality.getBakaraweight()));
            shipmentInOutLine.setPaperMachine(productionQuality.getPaperMachine());
            if (productionQuality.getBusinessPartner() != null) {
              shipmentInOutLine.setPaperClient(productionQuality.getBusinessPartner());
            }
            shipmentInOutLine.setStorageBin(locator);

            OBDal.getInstance().save(shipmentInOutLine);
            OBDal.getInstance().flush();
            // OBDal.getInstance().commitAndClose();

            /////////////////////////////////////////////////////
            // Call stored procedure to complete the line

            final Process process = OBDal.getInstance().get(Process.class, "109");

            // Create the pInstance
            final ProcessInstance pInstance = OBProvider.getInstance().get(ProcessInstance.class);
            // sets its process
            pInstance.setProcess(process);
            // must be set to true
            pInstance.setActive(true);
            pInstance.setRecordID(inOut.getId());
            // get the user from the context
            pInstance.setUserContact(OBContext.getOBContext().getUser());

            // persist to the db
            OBDal.getInstance().save(pInstance);

            // flush, this gives pInstance an ID
            OBDal.getInstance().flush();

            System.err.println(pInstance.getId());

            // call the SP
            try {
              // first get a connection
              final Connection connection = OBDal.getInstance().getConnection();
              final PreparedStatement ps = connection
                  .prepareStatement("SELECT * FROM m_inout_post0(?)");
              ps.setString(1, pInstance.getId());
              ps.execute();
            } catch (Exception e) {
              throw new IllegalStateException(e);
            }

            // refresh the pInstance as the SP has changed it
            OBDal.getInstance().getSession().refresh(pInstance);

            System.err.println(pInstance.getResult());
            System.err.println(pInstance.getErrorMsg());

            OBDal.getInstance().commitAndClose();
            /////////////////////////////////////////////////////

          } finally {
            OBContext.restorePreviousMode();
          }
        }
      }

      JSONObject result = new JSONObject();
      result.put("updated", orderIds.length());

      return result;
    } catch (Exception e) {
      throw new OBException(e);
    }
  }
}