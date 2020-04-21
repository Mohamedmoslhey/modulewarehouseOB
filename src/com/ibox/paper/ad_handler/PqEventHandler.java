package com.ibox.paper.ad_handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.event.Observes;

import org.apache.commons.lang.time.DateUtils;
import org.openbravo.base.exception.OBException;
import org.openbravo.base.model.Entity;
import org.openbravo.base.model.ModelProvider;
import org.openbravo.base.model.Property;
import org.openbravo.client.kernel.event.EntityDeleteEvent;
import org.openbravo.client.kernel.event.EntityNewEvent;
import org.openbravo.client.kernel.event.EntityPersistenceEventObserver;
import org.openbravo.dal.core.OBContext;
import org.openbravo.database.ConnectionProvider;
import org.openbravo.erpCommon.utility.Utility;
import org.openbravo.service.db.DalConnectionProvider;

import com.ibox.paper.data.ProductionQuality;

public class PqEventHandler extends EntityPersistenceEventObserver {

  private static Entity[] entities = {
      ModelProvider.getInstance().getEntity(ProductionQuality.ENTITY_NAME) };

  @Override
  protected Entity[] getObservedEntities() {
    // TODO Auto-generated method stub
    return entities;
  }

  public void onDelete(@Observes EntityDeleteEvent event) {
    if (!isValidEvent(event)) {
      return;
    }
    final ProductionQuality quality = (ProductionQuality) event.getTargetInstance();
    if (quality.isCompleted()) {
      String language = OBContext.getOBContext().getLanguage().getLanguage();
      ConnectionProvider conn = new DalConnectionProvider(false);
      throw new OBException(Utility.messageBD(conn, "paper_refusedelete", language));
    }
  }

  public void onSave(@Observes EntityNewEvent event) {
    if (!isValidEvent(event)) {
      return;
    }

    boolean dateMinusOne = false;
    final ProductionQuality quality = (ProductionQuality) event.getTargetInstance();

    String language = OBContext.getOBContext().getLanguage().getLanguage();
    ConnectionProvider conn = new DalConnectionProvider(false);
    if (quality.getTryone() == 0 || quality.getTrytwo() == 0 || quality.getTrythree() == 0
        || quality.getTryfour() == 0 || quality.getTryfive() == 0) {

      throw new OBException(Utility.messageBD(conn, "paper_weightTriesValidation", language));
    }
    if (quality.getSomcone() == 0 || quality.getSomctwo() == 0 || quality.getSomcthree() == 0
        || quality.getSomcfour() == 0 || quality.getSomcfive() == 0) {

      throw new OBException(Utility.messageBD(conn, "paper_gsmTriesValidation", language));
    }
    if ((quality.getPaperQualityDegree().getIdentifier().trim().contains("DEG01")
        || quality.getPaperQualityDegree().getDegree().trim().contains("اولي"))
        && (quality.getFirstrefuse() != null || quality.getSecondrefuse() != null)) {

      throw new OBException(Utility.messageBD(conn, "paper_DegreeRefuseValidation_1", language));
    }
    // if (!(quality.getPaperQualityDegree().getIdentifier().trim().contains("DEG01")
    // || quality.getPaperQualityDegree().getDegree().trim().contains("اولي"))) {
    // if (quality.getFirstrefuse() == null)
    // throw new OBException(Utility.messageBD(conn, "paper_DegreeRefuseValidation_2", language));
    // }
    // && quality.getSecondrefuse() == null
    final Entity productionQualityEntity = ModelProvider.getInstance()
        .getEntity(ProductionQuality.ENTITY_NAME);

    /*
     * final OBQuery<ProductionQualitySequence> PQQuery = OBDal.getInstance()
     * .createQuery(ProductionQualitySequence.class, ""); PQQuery.setMaxResult(1);
     * 
     * final ProductionQualitySequence PQS = PQQuery.list().get(0);
     * 
     * Calendar calendar = Calendar.getInstance(); int dayOfYear =
     * calendar.get(Calendar.DAY_OF_YEAR); int hour = calendar.get(Calendar.HOUR_OF_DAY); int mins =
     * calendar.get(Calendar.MINUTE); if (hour >= 0 && hour < 8 && mins > 0) { dayOfYear = dayOfYear
     * - 1; dateMinusOne = true; }
     * 
     * PQS.setCurrentyear(quality.getBakaracode().substring(0, 2)); if
     * (quality.getBakaracode().substring(2).length() == 2) {
     * 
     * PQS.setDayofyear(quality.getBakaracode().substring(2, 3));
     * PQS.setDailyserial(quality.getBakaracode().substring(3)); } else if
     * (quality.getBakaracode().substring(2).length() == 3) { if ((dayOfYear + "").length() == 1) {
     * PQS.setDayofyear(quality.getBakaracode().substring(2, 3));
     * PQS.setDailyserial(quality.getBakaracode().substring(3)); } else if ((dayOfYear +
     * "").length() == 2) { PQS.setDayofyear(quality.getBakaracode().substring(2, 4));
     * PQS.setDailyserial(quality.getBakaracode().substring(4)); } } else if
     * (quality.getBakaracode().substring(2).length() == 4) { if ((dayOfYear + "").length() == 1) {
     * PQS.setDayofyear(quality.getBakaracode().substring(2, 3));
     * PQS.setDailyserial(quality.getBakaracode().substring(3)); } else if ((dayOfYear +
     * "").length() == 2) { PQS.setDayofyear(quality.getBakaracode().substring(2, 4));
     * PQS.setDailyserial(quality.getBakaracode().substring(4)); } else if ((dayOfYear +
     * "").length() == 3) { PQS.setDayofyear(quality.getBakaracode().substring(2, 5));
     * PQS.setDailyserial(quality.getBakaracode().substring(5)); } } else if
     * (quality.getBakaracode().substring(2).length() == 5) { if ((dayOfYear + "").length() == 2) {
     * PQS.setDayofyear(quality.getBakaracode().substring(2, 4));
     * PQS.setDailyserial(quality.getBakaracode().substring(4)); } else if ((dayOfYear +
     * "").length() == 3) { PQS.setDayofyear(quality.getBakaracode().substring(2, 5));
     * PQS.setDailyserial(quality.getBakaracode().substring(5)); } } else if
     * (quality.getBakaracode().substring(2).length() == 6) {
     * PQS.setDayofyear(quality.getBakaracode().substring(2, 5));
     * PQS.setDailyserial(quality.getBakaracode().substring(5)); }
     */
    // OBDal.getInstance().save(PQS);

    final Property docStatusProperty = productionQualityEntity
        .getProperty(ProductionQuality.PROPERTY_DOCUMENTSTATUS);
    // note use setCurrentState and not setters on the Greeting object directly
    event.setCurrentState(docStatusProperty, "S2");
    if (dateMinusOne) {
      final Property productionDate = productionQualityEntity
          .getProperty(ProductionQuality.PROPERTY_PRODUCTIONDATE);
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date productionDateToUse = DateUtils.addDays(new Date(), -1);
      event.setCurrentState(productionDate, simpleDateFormat.format(productionDateToUse));
    }

  }

}
