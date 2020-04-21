package com.ibox.paper.ad_handler;

import java.math.BigDecimal;

import javax.enterprise.event.Observes;

import org.openbravo.base.model.Entity;
import org.openbravo.base.model.ModelProvider;
import org.openbravo.client.kernel.event.EntityDeleteEvent;
import org.openbravo.client.kernel.event.EntityPersistenceEventObserver;
import org.openbravo.dal.service.OBDal;
import org.openbravo.model.materialmgmt.transaction.ShipmentInOut;
import org.openbravo.model.materialmgmt.transaction.ShipmentInOutLine;

public class GoodsShipmentHandler extends EntityPersistenceEventObserver {

  private static Entity[] entities = {
      ModelProvider.getInstance().getEntity(ShipmentInOutLine.ENTITY_NAME) };

  @Override
  protected Entity[] getObservedEntities() {

    return entities;
  }

  public void onDelete(@Observes EntityDeleteEvent event) {
    if (!isValidEvent(event)) {
      return;
    }
    final ShipmentInOutLine inOutLine = (ShipmentInOutLine) event.getTargetInstance();
    BigDecimal weight = inOutLine.getPaperBakawieght();

    final ShipmentInOut inOut = OBDal.getInstance().get(ShipmentInOut.class,
        inOutLine.getShipmentReceipt().getId());
    if (inOut != null) {
      if (weight != null && inOut.getPAPERTotalShipmentWeight().compareTo(BigDecimal.ZERO) > 0
          && weight.compareTo(inOut.getPAPERTotalShipmentWeight()) <= 0) {
        inOut.setPAPERTotalShipmentWeight(inOut.getPAPERTotalShipmentWeight().subtract(weight));
      }
      if (inOut.getPAPERNumberOfRoller() != 0) {
        inOut.setPAPERNumberOfRoller(inOut.getPAPERNumberOfRoller() - 1);
      }
      OBDal.getInstance().save(inOut);
      OBDal.getInstance().flush();
    }

  }
}
