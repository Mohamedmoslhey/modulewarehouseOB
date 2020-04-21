package com.ibox.paper.invevtorydeatil.entity;

import java.math.BigDecimal;

import org.openbravo.data.FieldProvider;

public class InventoryDetail_Entity implements FieldProvider {

  // private String machine;
  // private String qdeg;
  // private String faresres;
  // private String bakarnum;
  private BigDecimal bakarweigth;
  private String bakaracode;
  // private String fromdate;
  // private String todate;
  // private String monthdate;
  private BigDecimal bno;
  private BigDecimal wno;

  // private String notes; //
  // private String prodate; //
  private String producttype;
  private String bussinesspartnercode; // String
  private BigDecimal width;

  private BigDecimal gsm;
  // private String average;
  private String machine; //
  private String qotr;
  private String qualitydegree;
  private String firstresonfars;
  private String secondresonfars; //
  // // private String productionres;
  //
  // // private String maqsmasool;
  // private String qualitymasool;
  // private String productionshift; //
  // private String maqsshift;
  // private String weigthone; //
  //
  // private String weigthtwo;
  // private String weigththree; // لازم
  // private String weigthfour; //
  // private String weigthfive; //////
  // private String weigthsix; //
  // private String weigthseven;
  // private String weigtheight;
  // private String weigthnine;
  // private String weigthten; // لازم تكون رقم عشان
  // /////////////////////////////////////////////////////////////////

  // private String somcone; //
  // private String somctwo; //
  // private String somcthree; //
  // private String somcfour;
  // private String somcfive;//
  // private String somcsix; //

  // private String somcseven; //
  // private String somceigth; //
  // private String somcnine; //
  // private String somcten; //
  // private String somcaverage;
  //
  // private BigDecimal shadtolly;
  // private BigDecimal shadardy; //
  // private BigDecimal fasleltbaaat; //
  // private BigDecimal rtoppa; //
  // private Long tashrbammamy; //
  // private Long tashrabkalfy; // // ok

  // // private String waslano; // لازم تكون رقم عشان يقراها صح // ok
  //

  // //

  // this is for make sure that wahst you set in java model will
  // be assign for its position in jrxml

  @Override
  public String getField(String fieldName) {
    // TODO Auto-generated method stub

    if (fieldName.equalsIgnoreCase("bakaracode"))
      return bakaracode;
    // else if (fieldName.equalsIgnoreCase("bno")) {
    // return bno.toString();
    // }
    //
    else if (fieldName.equalsIgnoreCase("producttype")) {
      return producttype;
    }

    else if (fieldName.equalsIgnoreCase("bussinesspartnercode")) {
      return bussinesspartnercode;
    }

    else if (fieldName.equalsIgnoreCase("width"))

    {
      return width.toString();
    }
    //
    else if (fieldName.equalsIgnoreCase("gsm")) {
      return gsm.toString();

    } else if (fieldName.equalsIgnoreCase("bakarweigth")) {
      return bakarweigth.toString();

    }
    // if (fieldName.equalsIgnoreCase("productionres"))
    // return productionres;
    //
    else if (fieldName.equalsIgnoreCase("bno")) {
      return bno.toString();
    }

    else if (fieldName.equalsIgnoreCase("wno")) {
      return wno.toString();
    }

    // } else if (fieldName.equalsIgnoreCase("productionshift")) {
    // return productionshift.toString();
    // }
    //
    // else if (fieldName.equalsIgnoreCase("maqsshift")) {
    // return maqsshift.toString();
    //
    // }
    //
    // if (fieldName.equalsIgnoreCase("weigthone"))
    // return weigthone.toString();
    //
    // else if (fieldName.equalsIgnoreCase("weigthtwo")) {
    // return weigthtwo.toString();
    // }
    //
    // else if (fieldName.equalsIgnoreCase("weigththree")) {
    // return weigththree.toString();
    //
    // } else if (fieldName.equalsIgnoreCase("weigthfour")) {
    // return weigthfour.toString();
    // }
    //
    // else if (fieldName.equalsIgnoreCase("weigthfive")) {
    // return weigthfive.toString();
    //
    // }
    // if (fieldName.equalsIgnoreCase("weigthsix"))
    // return weigthsix.toString();
    //
    // else if (fieldName.equalsIgnoreCase("weigthseven")) {
    // return weigthseven.toString();
    // }
    //
    // else if (fieldName.equalsIgnoreCase("weigtheight")) {
    // return weigtheight.toString();
    //
    // } else if (fieldName.equalsIgnoreCase("weigthnine")) {
    // return weigthnine.toString();
    // }
    //
    // else if (fieldName.equalsIgnoreCase("weigthten")) {
    // return weigthten.toString();
    //
    //
    // if (fieldName.equalsIgnoreCase("somcone")) {
    // return somcone.toString();
    //
    // }
    // if (fieldName.equalsIgnoreCase("somctwo")) {
    // return somctwo.toString();
    //
    // }
    // if (fieldName.equalsIgnoreCase("somcthree")) {
    // return somcthree.toString();
    //
    // }
    // if (fieldName.equalsIgnoreCase("somcfour")) {
    // return somcfour.toString();
    //
    // }
    // if (fieldName.equalsIgnoreCase("somcfive")) {
    // return somcfive.toString();
    //
    // }
    // if (fieldName.equalsIgnoreCase("somcsix")) {
    // return somcsix.toString();
    //
    // }
    // if (fieldName.equalsIgnoreCase("somcseven")) {
    // return somcseven.toString();
    //
    // }
    // if (fieldName.equalsIgnoreCase("somceigth")) {
    // return somceigth.toString();
    //
    // }
    // if (fieldName.equalsIgnoreCase("somcnine")) {
    // return somcnine.toString();
    //
    // }
    // if (fieldName.equalsIgnoreCase("somcten")) {
    // return somcten.toString();
    //
    // }
    // if (fieldName.equalsIgnoreCase("somcaverage")) {
    // return somcaverage.toString();
    //
    // }
    // if (fieldName.equalsIgnoreCase("fromdate")) {
    // return fromdate.toString();
    //
    // }
    // if (fieldName.equalsIgnoreCase("todate")) {
    // return todate.toString();
    //
    // }
    // // if (fieldName.equalsIgnoreCase("shadtolly")) {
    // // return shadtolly.toString();
    // //
    // // }
    // // if (fieldName.equalsIgnoreCase("shadardy")) {
    // // return shadardy.toString();
    // //
    // // }
    // else if (fieldName.equalsIgnoreCase("notes")) {
    // return notes;
    //
    // }
    // if (fieldName.equalsIgnoreCase("prodate")) {
    // return prodate;
    //
    // }
    // if (fieldName.equalsIgnoreCase("monthdate")) {
    // return monthdate;
    //
    // }
    //
    // // if (fieldName.equalsIgnoreCase("fasleltbaaat")) {
    // // return fasleltbaaat.toString();
    // //
    // // }
    // // if (fieldName.equalsIgnoreCase("rtoppa")) {
    // // return rtoppa.toString();
    // //
    // // }
    // // if (fieldName.equalsIgnoreCase("tashrbammamy")) {
    // // return tashrbammamy.toString();
    // //
    // // }
    // // if (fieldName.equalsIgnoreCase("tashrabkalfy")) {
    // // return tashrabkalfy.toString();
    //
    // // }
    //
    else if (fieldName.equalsIgnoreCase("machine")) {
      return machine;

    }

    else if (fieldName.equalsIgnoreCase("qotr")) {
      return qotr;

    }
    //
    // // if (fieldName.equalsIgnoreCase("backcolor")) {
    // // return backcolor;
    // //
    // // }
    // // if (fieldName.equalsIgnoreCase("waslano")) {
    // // return waslano;
    // //
    // // }
    else if (fieldName.equalsIgnoreCase("qualitydegree")) {
      return qualitydegree;

    } else if (fieldName.equalsIgnoreCase("firstresonfars")) {
      return firstresonfars;

    } else if (fieldName.equalsIgnoreCase("secondresonfars")) {
      return secondresonfars;

    } else

      return null;
  }

  // get and set for all entity in jrxml sheet افهم بقى
  // public BigDecimal getBno() {
  // return bno;
  // }
  //
  // public void setBno(BigDecimal bno) {
  // this.bno = bno;
  // }

  public String getBakaracode() {
    return bakaracode;
  }

  public void setBakaracode(String bakaracode) {
    this.bakaracode = bakaracode;
  }

  // public String getMonthdate() {
  // return monthdate;
  // }
  //
  // public void setMonthdate(String monthdate) {
  // this.monthdate = monthdate;
  // }
  //
  // public String getFromdate() {
  // return fromdate;
  // }
  //
  public String getMachine() {
    return machine;
  }

  public void setMachine(String machine) {
    this.machine = machine;
  }

  public String getQotr() {
    return qotr;
  }

  public void setQotr(String qotr) {
    this.qotr = qotr;
  }

  //
  // public void setFromdate(String fromdate) {
  // this.fromdate = fromdate;
  // }
  //
  // public String getTodate() {
  // return todate;
  // }
  //
  // public void setTodate(String todate) {
  // this.todate = todate;
  // }
  //

  public String getProducttype() {
    return producttype;
  }

  public BigDecimal getBno() {
    return bno;
  }

  public void setBno(BigDecimal bno) {
    this.bno = bno;
  }

  public BigDecimal getWno() {
    return wno;
  }

  public void setWno(BigDecimal wno) {
    this.wno = wno;
  }

  public void setProducttype(String producttype) {
    this.producttype = producttype;
  }

  public String getBussinesspartnercode() {
    return bussinesspartnercode;
  }

  public void setBussinesspartnercode(String bussinesspartnercode) {
    this.bussinesspartnercode = bussinesspartnercode;
  }

  public BigDecimal getWidth() {
    return width;
  }

  public void setWidth(BigDecimal width) {
    this.width = width;
  }

  public BigDecimal getBakarweigth() {
    return bakarweigth;
  }

  public void setBakarweigth(BigDecimal bakarweigth) {
    this.bakarweigth = bakarweigth;
  }

  public BigDecimal getGsm() {
    return gsm;
  }

  public void setGsm(BigDecimal gsm) {
    this.gsm = gsm;
  }

  //
  // public String getProductionres() {
  // return productionres;
  // }
  //
  // public void setProductionres(String productionres) {
  // this.productionres = productionres;
  // }
  //
  // public String getMaqsmasool() {
  // return maqsmasool;
  // }
  //
  // public void setMaqsmasool(String maqsmasool) {
  // this.maqsmasool = maqsmasool;
  // }
  //
  // public String getQualitymasool() {
  // return qualitymasool;
  // }
  //
  // public void setQualitymasool(String qualitymasool) {
  // this.qualitymasool = qualitymasool;
  // }
  //
  // public String getProductionshift() {
  // return productionshift;
  // }
  //
  // public void setProductionshift(String productionshift) {
  // this.productionshift = productionshift;
  // }
  //
  // public String getMaqsshift() {
  // return maqsshift;
  // }
  //
  // public void setMaqsshift(String maqsshift) {
  // this.maqsshift = maqsshift;
  // }
  //
  // public String getWeigthone() {
  // return weigthone;
  // }
  //
  // public void setWeigthone(String weigthone) {
  // this.weigthone = weigthone;
  // }
  //
  // public String getWeigthtwo() {
  // return weigthtwo;
  // }
  //
  // public void setWeigthtwo(String weigthtwo) {
  // this.weigthtwo = weigthtwo;
  // }
  //
  // public String getWeigththree() {
  // return weigththree;
  // }
  //
  // public void setWeigththree(String weigththree) {
  // this.weigththree = weigththree;
  // }
  //
  // public String getWeigthfour() {
  // return weigthfour;
  // }
  //
  // public void setWeigthfour(String weigthfour) {
  // this.weigthfour = weigthfour;
  // }
  //
  // public String getWeigthfive() {
  // return weigthfive;
  // }
  //
  // public void setWeigthfive(String weigthfive) {
  // this.weigthfive = weigthfive;
  // }
  //
  // public String getWeigthsix() {
  // return weigthsix;
  // }
  //
  // public void setWeigthsix(String weigthsix) {
  // this.weigthsix = weigthsix;
  // }
  //
  // public String getWeigthseven() {
  // return weigthseven;
  // }
  //
  // public void setWeigthseven(String weigthseven) {
  // this.weigthseven = weigthseven;
  // }
  //
  // public String getWeigtheight() {
  // return weigtheight;
  // }
  //
  // public void setWeigtheight(String weigtheight) {
  // this.weigtheight = weigtheight;
  // }
  //
  // public String getWeigthnine() {
  // return weigthnine;
  // }
  //
  // public void setWeigthnine(String weigthnine) {
  // this.weigthnine = weigthnine;
  // }
  //
  // public String getWeigthten() {
  // return weigthten;
  // }
  //
  // public void setWeigthten(String weigthten) {
  // this.weigthten = weigthten;
  // }
  //
  // public String getAverage() {
  // return average;
  // }
  //
  // public void setAverage(String average) {
  // this.average = average;
  // }

  // public String getSomcone() {
  // return somcone;
  // }
  //
  // public void setSomcone(String somcone) {
  // this.somcone = somcone;
  // }
  //
  // public String getSomctwo() {
  // return somctwo;
  // }
  //
  // public void setSomctwo(String somctwo) {
  // this.somctwo = somctwo;
  // }
  //
  // public String getSomcthree() {
  // return somcthree;
  // }
  //
  // public void setSomcthree(String somcthree) {
  // this.somcthree = somcthree;
  // }
  //
  // public String getSomcfour() {
  // return somcfour;
  // }
  //
  // public void setSomcfour(String somcfour) {
  // this.somcfour = somcfour;
  // }
  //
  // public String getSomcfive() {
  // return somcfive;
  // }
  //
  // public void setSomcfive(String somcfive) {
  // this.somcfive = somcfive;
  // }
  //
  // public String getSomcsix() {
  // return somcsix;
  // }
  //
  // public void setSomcsix(String somcsix) {
  // this.somcsix = somcsix;
  // }
  //
  // public String getSomcseven() {
  // return somcseven;
  // }
  //
  // public void setSomcseven(String somcseven) {
  // this.somcseven = somcseven;
  // }
  //
  // public String getSomceigth() {
  // return somceigth;
  // }
  //
  // public void setSomceigth(String somceigth) {
  // this.somceigth = somceigth;
  // }
  //
  // public String getSomcnine() {
  // return somcnine;
  // }
  //
  // public void setSomcnine(String somcnine) {
  // this.somcnine = somcnine;
  // }
  //
  // public String getSomcten() {
  // return somcten;
  // }
  //
  // public void setSomcten(String somcten) {
  // this.somcten = somcten;
  // }
  //
  // public String getSomcaverage() {
  // return somcaverage;
  // }
  //
  // public void setSomcaverage(String somcaverage) {
  // this.somcaverage = somcaverage;
  // }
  //
  // public BigDecimal getShadtolly() {
  // return shadtolly;
  // }
  //
  // public void setShadtolly(BigDecimal shadtolly) {
  // this.shadtolly = shadtolly;
  // }
  //
  // public BigDecimal getShadardy() {
  // return shadardy;
  // }
  //
  // public void setShadardy(BigDecimal shadardy) {
  // this.shadardy = shadardy;
  // }

  // public BigDecimal getFasleltbaaat() {
  // return fasleltbaaat;
  // }
  //
  // public void setFasleltbaaat(BigDecimal fasleltbaaat) {
  // this.fasleltbaaat = fasleltbaaat;
  // }

  //
  // public BigDecimal getRtoppa() {
  // return rtoppa;
  // }
  //
  // //
  // public void setRtoppa(BigDecimal rtoppa) {
  // this.rtoppa = rtoppa;
  // }

  //
  // public Long getTashrbammamy() {
  // return tashrbammamy;
  // }
  //
  // //
  // public void setTashrbammamy(Long tashrbammamy) {
  // this.tashrbammamy = tashrbammamy;
  // }
  //
  // //
  // public Long getTashrabkalfy() {
  // return tashrabkalfy;
  // }
  //
  // //
  // public void setTashrabkalfy(Long tashrabkalfy) {
  // this.tashrabkalfy = tashrabkalfy;
  // }

  // public String getProdate() {
  // return prodate;
  // }

  //
  // //
  // // //
  // public void setProdate(String prodate) {
  // this.prodate = prodate;
  // }

  //
  // public String getWaghcolor() {
  // return waghcolor;
  // }
  //
  // public void setWaghcolor(String waghcolor) {
  // this.waghcolor = waghcolor;
  // }
  //
  // public String getBackcolor() {
  // return backcolor;
  // }
  //
  // public void setBackcolor(String backcolor) {
  // this.backcolor = backcolor;
  // }
  //
  // public String getWaslano() {
  // return waslano;
  // }
  //
  // public void setWaslano(String waslano) {
  // this.waslano = waslano;
  // }

  public String getQualitydegree() {
    return qualitydegree;
  }

  public void setQualitydegree(String qualitydegree) {
    this.qualitydegree = qualitydegree;
  }

  public String getFirstresonfars() {
    return firstresonfars;
  }

  public void setFirstresonfars(String firstresonfars) {
    this.firstresonfars = firstresonfars;
  }

  public String getSecondresonfars() {
    return secondresonfars;
  }

  //
  public void setSecondresonfars(String secondresonfars) {
    this.secondresonfars = secondresonfars;
  }

  // //
  // public String getNotes() {
  // return notes;
  // }
  //
  // //
  // public void setNotes(String notes) {
  // this.notes = notes;
  // }

  // @Override
  // public String toString() {
  // return "ProductDeatilPerMonth_Entity [bakaracode=" + bakaracode + ", producttype=" +
  // producttype
  // + ", bussinesspartnercode=" + bussinesspartnercode + ", width=" + width + ", gsm=" + gsm
  // + ", productionres=" + productionres + ", fromdate=" + fromdate + ", todate=" + todate
  // + ", maqsmasool=" + maqsmasool + ", qualitymasool=" + qualitymasool + ", productionshift="
  // + productionshift + ", maqsshift=" + maqsshift + ", weigthone=" + weigthone + ", weigthtwo="
  // + weigthtwo + ", weigththree=" + weigththree + ", weigthfour=" + weigthfour
  // + ", weigthfive=" + weigthfive + ", weigthsix=" + weigthsix + ", weigthseven=" + weigthseven
  // + ", weigtheight=" + weigtheight + ", weigthnine=" + weigthnine + ", weigthten=" + weigthten
  // + ", average=" + average + ", somcone=" + somcone + ", somctwo=" + somctwo + ", somcthree="
  // + somcthree + ", somcfour=" + somcfour + ", somcfive=" + somcfive + ", somcsix=" + somcsix
  // + ", bno=" + bno + ", somcseven=" + somcseven + ", somceigth=" + somceigth + ", somcnine="
  // + somcnine + ", somcten=" + somcten + ", somcaverage=" + somcaverage + ", shadtolly="
  // + shadtolly + ", shadardy=" + shadardy + ", fasleltbaaat=" + fasleltbaaat + ", rtoppa="
  // + rtoppa + ", tashrbammamy=" + tashrbammamy + ", tashrabkalfy=" + tashrabkalfy
  // + ", waghcolor=" + waghcolor + ", backcolor=" + backcolor + ", waslano=" + waslano
  // + ", qualitydegree=" + qualitydegree + ", firstresonfars=" + ", secondresonfars="
  // + ", notes=" + notes + ", prodate=" + prodate + "]";
  // }

}
