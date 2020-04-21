package com.ibox.paper.ad_actionButton;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.openbravo.base.provider.OBProvider;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.erpCommon.utility.Utility;
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
import org.openbravo.scheduling.ProcessBundle;
import org.openbravo.service.db.DalBaseProcess;
import org.openbravo.service.db.DalConnectionProvider;

import com.ibox.paper.data.ProductionQuality;

public class CompleteProductionQualityReturn extends DalBaseProcess {

  private static final Logger logger = Logger.getLogger(CompleteProductionQualityReturn.class);

  @Override
  protected void doExecute(ProcessBundle bundle) throws Exception {
    try {
      final String ProdQulaityId = (String) bundle.getParams().get("Paper_Quality_ID");

      bundle.setResult(completeProductionquality(ProdQulaityId));
    } catch (Exception e) {

      logger.error("Unexpected error : " + e.getMessage(), e);
      final OBError msg = new OBError();
      msg.setType("Error"); // this will make the resulting message red
      msg.setMessage(e.getMessage()); // just display the stacktrace of this error
      msg.setTitle(Utility.messageBD(new DalConnectionProvider(), "Error",
          OBContext.getOBContext().getLanguage().getLanguage())); // this message already exists in
                                                                  // the core
      bundle.setResult(msg);
    }

  }

  OBError completeProductionquality(String QulaityId) {

    // set current record to be completed
    final ProductionQuality ProdQualit = OBDal.getInstance().get(ProductionQuality.class,
        QulaityId);

    ProdQualit.setCompleted(true);
    OBDal.getInstance().save(ProdQualit);
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
    product.setSearchKey("Bakara_" + ProdQualit.getBakaracode());
    product.setName(ProdQualit.getBakaracode());
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

      /*
       * final OBCriteria<Organization> MainOrgCriteria = OBDal.getInstance()
       * .createCriteria(Organization.class); MainOrgCriteria.add(Restrictions.eq("searchKey",
       * "Main")); MainOrgCriteria.add(Restrictions.eq("name", "Main")); final
       * java.util.List<Organization> Mainorganizations = MainOrgCriteria.list(); final Organization
       * MainOrganization = Mainorganizations.get(0);
       */

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
      /*
       * final OBCriteria<BusinessPartner> BpCriteria = OBDal.getInstance()
       * .createCriteria(BusinessPartner.class); BpCriteria.add(Restrictions.eq("organization",
       * PaperOrganization)); BpCriteria.add(Restrictions.eq("searchKey", "PQEmp")); final
       * java.util.List<BusinessPartner> businessPartneres = BpCriteria.list(); final
       * BusinessPartner businessPartner = businessPartneres.get(0);
       * 
       * final Location loc = businessPartner.getBusinessPartnerLocationList().get(0);
       */
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
      inOut.setBusinessPartner(ProdQualit.getBusinessPartner());
      inOut.setPartnerAddress(
          ProdQualit.getBusinessPartner().getBusinessPartnerLocationList().get(0));
      inOut.setSalesTransaction(false);
      OBDal.getInstance().save(inOut);
      OBDal.getInstance().flush();
      // OBDal.getInstance().commitAndClose();

      // final ProductionQuality productionQuality =
      // OBDal.getInstance().get(ProductionQuality.class,
      // QulaityId);
      final ShipmentInOutLine shipmentInOutLine = OBProvider.getInstance()
          .get(ShipmentInOutLine.class);

      final OBCriteria<Product> ProdCriteria = OBDal.getInstance().createCriteria(Product.class);
      ProdCriteria.add(Restrictions.eq("name", ProdQualit.getBakaracode()));
      final java.util.List<Product> products = ProdCriteria.list();
      final Product product_shipmentInOutLine = products.get(0);

      final OBCriteria<Reference> RefCriteria = OBDal.getInstance().createCriteria(Reference.class);
      RefCriteria.add(Restrictions.eq("name", "paper_ProductType"));
      final java.util.List<Reference> references = RefCriteria.list();
      final Reference reference = references.get(0);

      final OBCriteria<List> LstCriteria = OBDal.getInstance().createCriteria(List.class);
      LstCriteria.add(Restrictions.eq("reference", reference));
      LstCriteria.add(Restrictions.eq("searchKey", ProdQualit.getProductType()));
      final java.util.List<List> lists = LstCriteria.list();
      final List list = lists.get(0);

      final OBCriteria<Locator> LocatorCriteria = OBDal.getInstance().createCriteria(Locator.class);
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
      shipmentInOutLine.setBusinessPartner(ProdQualit.getBusinessPartner());
      shipmentInOutLine.setPAPERJumboCode(ProdQualit.getBakaracode());
      shipmentInOutLine.setPAPERProductType(ProdQualit.getProductType());
      shipmentInOutLine.setPaperQualityDegree(ProdQualit.getPaperQualityDegree());
      shipmentInOutLine.setPAPERGSM(ProdQualit.getGsm());
      shipmentInOutLine.setPaperBakarawidth(ProdQualit.getBakarawidth());
      shipmentInOutLine.setPAPERBakkaraQotr(ProdQualit.getBakaraqotr());
      shipmentInOutLine.setPaperBakawieght(new BigDecimal(ProdQualit.getBakaraweight()));
      shipmentInOutLine.setPaperMachine(ProdQualit.getPaperMachine());
      if (ProdQualit.getBusinessPartner() != null) {
        shipmentInOutLine.setPaperClient(ProdQualit.getBusinessPartner());
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
       * pInstance.getADParameterList().add(parameter); parameter.setProcessInstance(pInstance);
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
        final PreparedStatement ps = connection.prepareStatement("SELECT * FROM m_inout_post0(?)");
        ps.setString(1, pInstance.getId());
        ps.execute();
      } catch (Exception e) {
        throw new IllegalStateException(e);
      }

      // refresh the pInstance as the SP has changed it
      OBDal.getInstance().getSession().refresh(pInstance);

      System.err.println(pInstance.getResult());
      System.err.println(pInstance.getErrorMsg());

      /////////////////////////////////////////////////////
      // add movement header
      /*
       * final InternalMovement movementHead = OBProvider.getInstance().get(InternalMovement.class);
       * movementHead.setOrganization(PaperOrganization); movementHead.setProcessed(false);
       * movementHead.setActive(true);
       * movementHead.setName("تحويل من صالة الانتاج الي مخزن الانتاج التام");
       * movementHead.setMovementDate(new Date()); movementHead.setPosted("N"); //
       * movementHead.setDocumentNo(seq + ""); OBDal.getInstance().save(movementHead);
       * OBDal.getInstance().flush();
       */

      OBDal.getInstance().commitAndClose();
      /////////////////////////////////////////////////////

    } finally {
      OBContext.restorePreviousMode();
    }
    ////////////////////////////////////////////////////
    OBError myMessage = new OBError();

    myMessage.setMessage(String.format(
        Utility.messageBD(new DalConnectionProvider(), "paper_CompleteProductionQualityResult",
            OBContext.getOBContext().getLanguage().getLanguage())));
    myMessage.setType("Success");
    myMessage.setTitle(Utility.messageBD(new DalConnectionProvider(), "Success",
        OBContext.getOBContext().getLanguage().getLanguage()));
    return myMessage;

  }

}
