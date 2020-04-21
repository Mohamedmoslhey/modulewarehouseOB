package com.ibox.paper.productionpermonthdatebrief.entity;

import java.math.BigDecimal;

import org.openbravo.data.FieldProvider;

public class ProductionPerMonthDateBrief_Entity implements FieldProvider {

  private String todate;
  private String fromdate;
  private String prodate;

  private BigDecimal bno;
  private BigDecimal bakarwno;
  private BigDecimal bakarno;

  private BigDecimal wno;

  private String producttype;

  private String machine; //

  private String qualitydegree;

  // this is for make sure that wahst you set in java model will
  // be assign for its position in jrxml

  @Override
  public String getField(String fieldName) {
    // TODO Auto-generated method stub

    if (fieldName.equalsIgnoreCase("todate"))
      return todate;
    if (fieldName.equalsIgnoreCase("fromdate"))
      return fromdate;

    if (fieldName.equalsIgnoreCase("prodate"))
      return prodate;

    if (fieldName.equalsIgnoreCase("producttype")) {
      return producttype;
    }

    else if (fieldName.equalsIgnoreCase("bno")) {
      return bno.toString();
    } else if (fieldName.equalsIgnoreCase("bakarno")) {
      return bakarno.toString();
    } else if (fieldName.equalsIgnoreCase("bakarwno")) {
      return bakarwno.toString();
    }

    if (fieldName.equalsIgnoreCase("wno")) {
      return wno.toString();
    }

    else if (fieldName.equalsIgnoreCase("machine")) {
      return machine;

    }

    else if (fieldName.equalsIgnoreCase("qualitydegree")) {
      return qualitydegree;

    } else

      return null;
  }

  public String getTodate() {
    return todate;
  }

  public void setTodate(String todate) {
    this.todate = todate;
  }

  public String getProdate() {
    return prodate;
  }

  public void setProdate(String prodate) {
    this.prodate = prodate;
  }

  public String getFromdate() {
    return fromdate;
  }

  public void setFromdate(String fromdate) {
    this.fromdate = fromdate;
  }

  public String getMachine() {
    return machine;
  }

  public void setMachine(String machine) {
    this.machine = machine;
  }

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

  public String getQualitydegree() {
    return qualitydegree;
  }

  public void setQualitydegree(String qualitydegree) {
    this.qualitydegree = qualitydegree;
  }

  public BigDecimal getBakarwno() {
    return bakarwno;
  }

  public void setBakarwno(BigDecimal bakarwno) {
    this.bakarwno = bakarwno;
  }

  public BigDecimal getBakarno() {
    return bakarno;
  }

  public void setBakarno(BigDecimal bakarno) {
    this.bakarno = bakarno;
  }

  // public BigDecimal getBdno() {
  // return bdno;
  // }
  //
  // public void setBdno(BigDecimal bdno) {
  // this.bdno = bdno;
  // }
  //
  // public BigDecimal getB2no() {
  // return b2no;
  // }
  //
  // public void setB2no(BigDecimal b2no) {
  // this.b2no = b2no;
  // }
  //
  // public BigDecimal getB3no() {
  // return b3no;
  // }
  //
  // public void setB3no(BigDecimal b3no) {
  // this.b3no = b3no;
  // }
  //
  // public BigDecimal getB4no() {
  // return b4no;
  // }
  //
  // public void setB4no(BigDecimal b4no) {
  // this.b4no = b4no;
  // }
  //
  // public BigDecimal getBnomf() {
  // return bnomf;
  // }
  //
  // public void setBnomf(BigDecimal bnomf) {
  // this.bnomf = bnomf;
  // }
  //
  // public BigDecimal getB2nomf() {
  // return b2nomf;
  // }
  //
  // public void setB2nomf(BigDecimal b2nomf) {
  // this.b2nomf = b2nomf;
  // }
  //
  // public BigDecimal getB3nomf() {
  // return b3nomf;
  // }
  //
  // public void setB3nomf(BigDecimal b3nomf) {
  // this.b3nomf = b3nomf;
  // }
  //
  // public BigDecimal getB4nomf() {
  // return b4nomf;
  // }
  //
  // public void setB4nomf(BigDecimal b4nomf) {
  // this.b4nomf = b4nomf;
  // }
  //
  // public BigDecimal getBnocb() {
  // return bnocb;
  // }
  //
  // public void setBnocb(BigDecimal bnocb) {
  // this.bnocb = bnocb;
  // }
  //
  // public BigDecimal getB2nocb() {
  // return b2nocb;
  // }
  //
  // public void setB2nocb(BigDecimal b2nocb) {
  // this.b2nocb = b2nocb;
  // }
  //
  // public BigDecimal getB3nocb() {
  // return b3nocb;
  // }
  //
  // public void setB3nocb(BigDecimal b3nocb) {
  // this.b3nocb = b3nocb;
  // }
  //
  // public BigDecimal getB4nocb() {
  // return b4nocb;
  // }
  //
  // public void setB4nocb(BigDecimal b4nocb) {
  // this.b4nocb = b4nocb;
  // }
  //
  // public BigDecimal getBnotl() {
  // return bnotl;
  // }
  //
  // public void setBnotl(BigDecimal bnotl) {
  // this.bnotl = bnotl;
  // }
  //
  // public BigDecimal getB2notl() {
  // return b2notl;
  // }
  //
  // public void setB2notl(BigDecimal b2notl) {
  // this.b2notl = b2notl;
  // }
  //
  // public BigDecimal getB3notl() {
  // return b3notl;
  // }
  //
  // public void setB3notl(BigDecimal b3notl) {
  // this.b3notl = b3notl;
  // }
  //
  // public BigDecimal getB4notl() {
  // return b4notl;
  // }
  //
  // public void setB4notl(BigDecimal b4notl) {
  // this.b4notl = b4notl;
  // }
  //
  // public BigDecimal getBnoscb() {
  // return bnoscb;
  // }
  //
  // public void setBnoscb(BigDecimal bnoscb) {
  // this.bnoscb = bnoscb;
  // }
  //
  // public BigDecimal getB2noscb() {
  // return b2noscb;
  // }
  //
  // public void setB2noscb(BigDecimal b2noscb) {
  // this.b2noscb = b2noscb;
  // }
  //
  // public BigDecimal getB3noscb() {
  // return b3noscb;
  // }
  //
  // public void setB3noscb(BigDecimal b3noscb) {
  // this.b3noscb = b3noscb;
  // }
  //
  // public BigDecimal getB4noscb() {
  // return b4noscb;
  // }
  //
  // public void setB4noscb(BigDecimal b4noscb) {
  // this.b4noscb = b4noscb;
  // }
  //
  // public BigDecimal getWdeg1() {
  // return wdeg1;
  // }
  //
  // public void setWdeg1(BigDecimal wdeg1) {
  // this.wdeg1 = wdeg1;
  // }
  //
  // public BigDecimal getWdeg1mf() {
  // return wdeg1mf;
  // }
  //
  // public void setWdeg1mf(BigDecimal wdeg1mf) {
  // this.wdeg1mf = wdeg1mf;
  // }
  //
  // public BigDecimal getWdeg1cb() {
  // return wdeg1cb;
  // }
  //
  // public void setWdeg1cb(BigDecimal wdeg1cb) {
  // this.wdeg1cb = wdeg1cb;
  // }
  //
  // public BigDecimal getWdeg1tl() {
  // return wdeg1tl;
  // }
  //
  // public void setWdeg1tl(BigDecimal wdeg1tl) {
  // this.wdeg1tl = wdeg1tl;
  // }
  //
  // public BigDecimal getWdeg1scb() {
  // return wdeg1scb;
  // }
  //
  // public void setWdeg1scb(BigDecimal wdeg1scb) {
  // this.wdeg1scb = wdeg1scb;
  // }
  //
  // public BigDecimal getWdeg2() {
  // return wdeg2;
  // }
  //
  // public void setWdeg2(BigDecimal wdeg2) {
  // this.wdeg2 = wdeg2;
  // }
  //
  // public BigDecimal getWdeg3() {
  // return wdeg3;
  // }
  //
  // public void setWdeg3(BigDecimal wdeg3) {
  // this.wdeg3 = wdeg3;
  // }
  //
  // public BigDecimal getWdeg4() {
  // return wdeg4;
  // }
  //
  // public void setWdeg4(BigDecimal wdeg4) {
  // this.wdeg4 = wdeg4;
  // }
  //
  // public BigDecimal getWdeg2mf() {
  // return wdeg2mf;
  // }
  //
  // public void setWdeg2mf(BigDecimal wdeg2mf) {
  // this.wdeg2mf = wdeg2mf;
  // }
  //
  // public BigDecimal getWdeg2cb() {
  // return wdeg2cb;
  // }
  //
  // public void setWdeg2cb(BigDecimal wdeg2cb) {
  // this.wdeg2cb = wdeg2cb;
  // }
  //
  // public BigDecimal getWdeg2tl() {
  // return wdeg2tl;
  // }
  //
  // public void setWdeg2tl(BigDecimal wdeg2tl) {
  // this.wdeg2tl = wdeg2tl;
  // }
  //
  // public BigDecimal getWdeg2scb() {
  // return wdeg2scb;
  // }
  //
  // public void setWdeg2scb(BigDecimal wdeg2scb) {
  // this.wdeg2scb = wdeg2scb;
  // }
  //
  // public BigDecimal getWdeg3mf() {
  // return wdeg3mf;
  // }
  //
  // public void setWdeg3mf(BigDecimal wdeg3mf) {
  // this.wdeg3mf = wdeg3mf;
  // }
  //
  // public BigDecimal getWdeg3cb() {
  // return wdeg3cb;
  // }
  //
  // public void setWdeg3cb(BigDecimal wdeg3cb) {
  // this.wdeg3cb = wdeg3cb;
  // }
  //
  // public BigDecimal getWdeg3tl() {
  // return wdeg3tl;
  // }
  //
  // public void setWdeg3tl(BigDecimal wdeg3tl) {
  // this.wdeg3tl = wdeg3tl;
  // }
  //
  // public BigDecimal getWdeg3scb() {
  // return wdeg3scb;
  // }
  //
  // public void setWdeg3scb(BigDecimal wdeg3scb) {
  // this.wdeg3scb = wdeg3scb;
  // }
  //
  // public BigDecimal getWdeg4mf() {
  // return wdeg4mf;
  // }
  //
  // public void setWdeg4mf(BigDecimal wdeg4mf) {
  // this.wdeg4mf = wdeg4mf;
  // }
  //
  // public BigDecimal getWdeg4cb() {
  // return wdeg4cb;
  // }
  //
  // public void setWdeg4cb(BigDecimal wdeg4cb) {
  // this.wdeg4cb = wdeg4cb;
  // }
  //
  // public BigDecimal getWdeg4tl() {
  // return wdeg4tl;
  // }
  //
  // public void setWdeg4tl(BigDecimal wdeg4tl) {
  // this.wdeg4tl = wdeg4tl;
  // }
  //
  // public BigDecimal getWdeg4scb() {
  // return wdeg4scb;
  // }
  //
  // public void setWdeg4scb(BigDecimal wdeg4scb) {
  // this.wdeg4scb = wdeg4scb;
  // }

}
