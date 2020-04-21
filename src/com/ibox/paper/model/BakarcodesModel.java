package com.ibox.paper.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ibox.paper.data.ProductionQuality;
import com.ibox.paper.entity.BakaracodesEntity;

public class BakarcodesModel {

  public BakaracodesEntity[] get_data(ProductionQuality currentBakaracode) {

    DateFormat formate = new SimpleDateFormat("dd-MM-yyyy");
    List<BakaracodesEntity> entityList = new ArrayList<BakaracodesEntity>();
    BakaracodesEntity[] returnedData = null;
    BigDecimal one = BigDecimal.ONE;
    BigDecimal unitno = BigDecimal.ZERO;
    BigDecimal totalprice = BigDecimal.ZERO;

    // HashMap<String, String[]> map = new HashMap<String, String[]>();
    //
    // // 1
    // map.put("1", new
    // String{currentBakaracode.getBakaracode(),currentBakaracode.getProductType()});
    //
    // Iterator iterator = map.entrySet().iterator();
    // while (iterator.hasNext()) {
    // Map.Entry me2 = (Map.Entry) iterator.next();
    //
    // String key = (String) me2.getKey();
    // String value = (String) me2.getValue();

    ///////////////////////

    BakaracodesEntity entity = new BakaracodesEntity();
    String rt = currentBakaracode.getBakaracode();
    entity.setBakaracode(rt);

    if (currentBakaracode.getProductType().equals("3")) {
      entity.setProductType("Core Board");
      entity.setArabicpro("كور بورد");
    }
    if (currentBakaracode.getProductType().equals("2")) {
      entity.setProductType("Meduim Fluting");
      entity.setArabicpro("فلاتينج متوسط");
    }
    if (currentBakaracode.getProductType().equals("1")) {
      entity.setProductType("Manilla");
      entity.setArabicpro("مانيلا");
    }
    if (currentBakaracode.getProductType().equals("4")) {
      entity.setProductType("Test Liner");
      entity.setArabicpro("تيست لينير");
    }
    if (currentBakaracode.getProductType().equals("5")) {
      entity.setProductType("Special Core Board");
      entity.setArabicpro("كور بورد  فاخر");
    }

    if (currentBakaracode.getPaperQualityDegree().getCommercialName().equals("DEG01")) {
      entity.setDegree("1");
    }
    if (currentBakaracode.getPaperQualityDegree().getCommercialName().equals("DEG02")) {
      entity.setDegree("2");
    }
    if (currentBakaracode.getPaperQualityDegree().getCommercialName().equals("DEG03")) {
      entity.setDegree("3");
    }
    if (currentBakaracode.getPaperQualityDegree().getCommercialName().equals("DEG04")) {
      entity.setDegree("4");
    }

    entity.setBakarawidth(currentBakaracode.getBakarawidth());
    entity.setGsm(currentBakaracode.getGsm());
    entity.setBakaraweight(new BigDecimal(currentBakaracode.getBakaraweight()));
    entity.setSomc(new BigDecimal(currentBakaracode.getSomcaverage()));
    entity.setDiameter(currentBakaracode.getBakaraqotr());

    entity.setNotes(currentBakaracode.getNotes());

    entityList.add(entity);

    // }

    // ------------------------------------------------------------------

    returnedData = new BakaracodesEntity[entityList.size()];
    entityList.toArray(returnedData);
    return returnedData;

  }
}
