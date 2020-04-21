package com.ibox.paper.invevtorybriefbygram.entity;

import java.math.BigDecimal;

import org.openbravo.data.FieldProvider;

public class InventoryBriefbygram_Entity2 implements FieldProvider {

  private String warehouse;

  private BigDecimal bno;
  private BigDecimal b230a;
  private BigDecimal b230aw;
  private BigDecimal gsm;
  private BigDecimal gsmw;
  // // MEDIUM 230
  // private BigDecimal b230a;
  //
  // private BigDecimal b230b;
  // private BigDecimal b230c;
  // private BigDecimal b230d;
  // private BigDecimal b230e;
  // private BigDecimal b230f;
  // private BigDecimal b230g;
  // private BigDecimal b230h;
  // private BigDecimal b230j;
  // private BigDecimal b230k;
  // private BigDecimal b230l;
  //
  // // MEDIUM 250
  // private BigDecimal b250a;
  // private BigDecimal b250b;
  // private BigDecimal b250c;
  // private BigDecimal b250d;
  // private BigDecimal b250e;
  // private BigDecimal b250f;
  // private BigDecimal b250g;
  // private BigDecimal b250h;
  // private BigDecimal b250j;
  // private BigDecimal b250k;
  // private BigDecimal b250l;
  //
  // // MEDIUM 300
  // private BigDecimal b300a;
  // private BigDecimal b300b;
  // private BigDecimal b300c;
  // private BigDecimal b300d;
  // private BigDecimal b300e;
  // private BigDecimal b300f;
  // private BigDecimal b300g;
  // private BigDecimal b300h;
  // private BigDecimal b300j;
  // private BigDecimal b300k;
  // private BigDecimal b300l;
  //
  // // MEDIUM 300
  // private BigDecimal b350a;
  // private BigDecimal b350b;
  // private BigDecimal b350c;
  // private BigDecimal b350d;
  // private BigDecimal b350e;
  // private BigDecimal b350f;
  // private BigDecimal b350g;
  // private BigDecimal b350h;
  // private BigDecimal b350j;
  // private BigDecimal b350k;
  // private BigDecimal b350l;
  //
  // // MEDIUM 370
  // private BigDecimal b370a;
  // private BigDecimal b370b;
  // private BigDecimal b370c;
  // private BigDecimal b370d;
  // private BigDecimal b370e;
  // private BigDecimal b370f;
  // private BigDecimal b370g;
  // private BigDecimal b370h;
  // private BigDecimal b370j;
  // private BigDecimal b370k;
  // private BigDecimal b370l;
  //
  // // MEDIUM 300
  // private BigDecimal b380a;
  // private BigDecimal b380b;
  // private BigDecimal b380c;
  // private BigDecimal b380d;
  // private BigDecimal b380e;
  // private BigDecimal b380f;
  // private BigDecimal b380g;
  // private BigDecimal b380h;
  // private BigDecimal b380j;
  // private BigDecimal b380k;
  // private BigDecimal b380l;
  //
  // // MEDIUM 300
  // private BigDecimal b400a;
  // private BigDecimal b400b;
  // private BigDecimal b400c;
  // private BigDecimal b400d;
  // private BigDecimal b400e;
  // private BigDecimal b400f;
  // private BigDecimal b400g;
  // private BigDecimal b400h;
  // private BigDecimal b400j;
  // private BigDecimal b400k;
  // private BigDecimal b400l;

  // special core bored

  private BigDecimal wno;
  private BigDecimal width;
  private BigDecimal widthw;
  private String producttype;

  private String machine; //

  private String qualitydegree;

  // this is for make sure that wahst you set in java model will
  // be assign for its position in jrxml

  @Override
  public String getField(String fieldName) {
    // TODO Auto-generated method stub

    if (fieldName.equalsIgnoreCase("warehouse"))
      return warehouse;
    if (fieldName.equalsIgnoreCase("width"))
      return width.toString();

    if (fieldName.equalsIgnoreCase("gsm"))
      return gsm.toString();

    if (fieldName.equalsIgnoreCase("widthw"))
      return widthw.toString();

    if (fieldName.equalsIgnoreCase("gsmw"))
      return gsmw.toString();

    if (fieldName.equalsIgnoreCase("producttype")) {
      return producttype;
    }

    else if (fieldName.equalsIgnoreCase("bno")) {
      return bno.toString();
    }
    // else if (fieldName.equalsIgnoreCase("b230a")) {
    // return b230a.toString();
    // }
    // Mf

    if (fieldName.equalsIgnoreCase("b230a")) {
      return b230a.toString();
    }
    if (fieldName.equalsIgnoreCase("b230aw")) {
      return b230aw.toString();
    }
    // } else if (fieldName.equalsIgnoreCase("b230c")) {
    // return b230c.toString();
    // } else if (fieldName.equalsIgnoreCase("b230d")) {
    // return b230d.toString();
    // } else if (fieldName.equalsIgnoreCase("b230e")) {
    // return b230e.toString();
    // } else if (fieldName.equalsIgnoreCase("b230f")) {
    // return b230f.toString();
    // } else if (fieldName.equalsIgnoreCase("b230g")) {
    // return b230g.toString();
    // } else if (fieldName.equalsIgnoreCase("b230h")) {
    // return b230h.toString();
    // } else if (fieldName.equalsIgnoreCase("b230j")) {
    // return b230j.toString();
    // } else if (fieldName.equalsIgnoreCase("b230k")) {
    // return b230k.toString();
    // } else if (fieldName.equalsIgnoreCase("b230l")) {
    // return b230l.toString();
    // }
    //
    // // core bored
    // else if (fieldName.equalsIgnoreCase("b250a")) {
    // return b250a.toString();
    // } else if (fieldName.equalsIgnoreCase("b250b")) {
    // return b250b.toString();
    // } else if (fieldName.equalsIgnoreCase("b250c")) {
    // return b250c.toString();
    // } else if (fieldName.equalsIgnoreCase("b250d")) {
    // return b250d.toString();
    // } else if (fieldName.equalsIgnoreCase("b250e")) {
    // return b250e.toString();
    // } else if (fieldName.equalsIgnoreCase("b250f")) {
    // return b250f.toString();
    // } else if (fieldName.equalsIgnoreCase("b250g")) {
    // return b250g.toString();
    // } else if (fieldName.equalsIgnoreCase("b250h")) {
    // return b250h.toString();
    // } else if (fieldName.equalsIgnoreCase("b250j")) {
    // return b250j.toString();
    // } else if (fieldName.equalsIgnoreCase("b250k")) {
    // return b250k.toString();
    // } else if (fieldName.equalsIgnoreCase("b250l")) {
    // return b250l.toString();
    // } // test liner
    //
    // else if (fieldName.equalsIgnoreCase("b300a")) {
    // return b300a.toString();
    // } else if (fieldName.equalsIgnoreCase("b300b")) {
    // return b300b.toString();
    // } else if (fieldName.equalsIgnoreCase("b300c")) {
    // return b300c.toString();
    // } else if (fieldName.equalsIgnoreCase("b300d")) {
    // return b300d.toString();
    // } else if (fieldName.equalsIgnoreCase("b300e")) {
    // return b300e.toString();
    // } else if (fieldName.equalsIgnoreCase("b300f")) {
    // return b300f.toString();
    // } else if (fieldName.equalsIgnoreCase("b300g")) {
    // return b300g.toString();
    // } else if (fieldName.equalsIgnoreCase("b300h")) {
    // return b300h.toString();
    // } else if (fieldName.equalsIgnoreCase("b300j")) {
    // return b300j.toString();
    // } else if (fieldName.equalsIgnoreCase("b300k")) {
    // return b300k.toString();
    // } else if (fieldName.equalsIgnoreCase("b300l")) {
    // return b300l.toString();
    // } // special corebored else
    // if (fieldName.equalsIgnoreCase("b350a")) {
    // return b350a.toString();
    // } else if (fieldName.equalsIgnoreCase("b350b")) {
    // return b350b.toString();
    // } else if (fieldName.equalsIgnoreCase("b350c")) {
    // return b350c.toString();
    // } else if (fieldName.equalsIgnoreCase("b350d")) {
    // return b350d.toString();
    // } else if (fieldName.equalsIgnoreCase("b350e")) {
    // return b350e.toString();
    // } else if (fieldName.equalsIgnoreCase("b350f")) {
    // return b350f.toString();
    // } else if (fieldName.equalsIgnoreCase("b350g")) {
    // return b350g.toString();
    // } else if (fieldName.equalsIgnoreCase("b350h")) {
    // return b350h.toString();
    // } else if (fieldName.equalsIgnoreCase("b350j")) {
    // return b350j.toString();
    // } else if (fieldName.equalsIgnoreCase("b350k")) {
    // return b350k.toString();
    // } else if (fieldName.equalsIgnoreCase("b350l")) {
    // return b350l.toString();
    // }
    //
    // ///////////
    // else if (fieldName.equalsIgnoreCase("b370a")) {
    // return b370a.toString();
    // } else if (fieldName.equalsIgnoreCase("b370b")) {
    // return b370b.toString();
    // } else if (fieldName.equalsIgnoreCase("b370c")) {
    // return b370c.toString();
    // } else if (fieldName.equalsIgnoreCase("b370d")) {
    // return b370d.toString();
    // } else if (fieldName.equalsIgnoreCase("b370e")) {
    // return b370e.toString();
    // } else if (fieldName.equalsIgnoreCase("b370f")) {
    // return b370f.toString();
    // } else if (fieldName.equalsIgnoreCase("b370g")) {
    // return b370g.toString();
    // } else if (fieldName.equalsIgnoreCase("b370h")) {
    // return b370h.toString();
    // } else if (fieldName.equalsIgnoreCase("b370j")) {
    // return b370j.toString();
    // } else if (fieldName.equalsIgnoreCase("b370k")) {
    // return b370k.toString();
    // } else if (fieldName.equalsIgnoreCase("b370l")) {
    // return b370l.toString();
    // }
    //
    // //////
    //
    // else if (fieldName.equalsIgnoreCase("b380a")) {
    // return b380a.toString();
    // } else if (fieldName.equalsIgnoreCase("b380b")) {
    // return b380b.toString();
    // } else if (fieldName.equalsIgnoreCase("b380c")) {
    // return b380c.toString();
    // } else if (fieldName.equalsIgnoreCase("b380d")) {
    // return b380d.toString();
    // } else if (fieldName.equalsIgnoreCase("b380e")) {
    // return b380e.toString();
    // } else if (fieldName.equalsIgnoreCase("b380f")) {
    // return b380f.toString();
    // } else if (fieldName.equalsIgnoreCase("b380g")) {
    // return b380g.toString();
    // } else if (fieldName.equalsIgnoreCase("b380h")) {
    // return b380h.toString();
    // } else if (fieldName.equalsIgnoreCase("b380j")) {
    // return b380j.toString();
    // } else if (fieldName.equalsIgnoreCase("b380k")) {
    // return b380k.toString();
    // } else if (fieldName.equalsIgnoreCase("b380l")) {
    // return b380l.toString();
    // }
    //
    // else if (fieldName.equalsIgnoreCase("b400a")) {
    // return b400a.toString();
    // } else if (fieldName.equalsIgnoreCase("b400b")) {
    // return b400b.toString();
    // } else if (fieldName.equalsIgnoreCase("b400c")) {
    // return b400c.toString();
    // } else if (fieldName.equalsIgnoreCase("b400d")) {
    // return b400d.toString();
    // } else if (fieldName.equalsIgnoreCase("b400e")) {
    // return b400e.toString();
    // } else if (fieldName.equalsIgnoreCase("b400f")) {
    // return b400f.toString();
    // } else if (fieldName.equalsIgnoreCase("b400g")) {
    // return b400g.toString();
    // } else if (fieldName.equalsIgnoreCase("b400h")) {
    // return b400h.toString();
    // } else if (fieldName.equalsIgnoreCase("b400j")) {
    // return b400j.toString();
    // } else if (fieldName.equalsIgnoreCase("b400k")) {
    // return b400k.toString();
    // } else if (fieldName.equalsIgnoreCase("b400l")) {
    // return b400l.toString();
    // }

    else if (fieldName.equalsIgnoreCase("wno")) {
      return wno.toString();
    } else if (fieldName.equalsIgnoreCase("machine")) {
      return machine;

    }

    else if (fieldName.equalsIgnoreCase("qualitydegree")) {
      return qualitydegree;

    } else

      return null;
  }

  public BigDecimal getGsmw() {
    return gsmw;
  }

  public void setGsmw(BigDecimal gsmw) {
    this.gsmw = gsmw;
  }

  public BigDecimal getWidthw() {
    return widthw;
  }

  public void setWidthw(BigDecimal widthw) {
    this.widthw = widthw;
  }

  public BigDecimal getB230aw() {
    return b230aw;
  }

  public void setB230aw(BigDecimal b230aw) {
    this.b230aw = b230aw;
  }

  public BigDecimal getWidth() {
    return width;
  }

  public void setWidth(BigDecimal width) {
    this.width = width;
  }

  public String getwarehouse() {
    return warehouse;
  }

  public void setwarehouse(String warehouse) {
    this.warehouse = warehouse;
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

  public String getWarehouse() {
    return warehouse;
  }

  public void setWarehouse(String warehouse) {
    this.warehouse = warehouse;
  }

  public BigDecimal getB230a() {
    return b230a;
  }

  public void setB230a(BigDecimal b230a) {
    this.b230a = b230a;
  }

  public BigDecimal getGsm() {
    return gsm;
  }

  public void setGsm(BigDecimal gsm) {
    this.gsm = gsm;
  }

  // public BigDecimal getB230b() {
  // return b230b;
  // }
  //
  // public void setB230b(BigDecimal b230b) {
  // this.b230b = b230b;
  // }
  //
  // public BigDecimal getB230c() {
  // return b230c;
  // }
  //
  // public void setB230c(BigDecimal b230c) {
  // this.b230c = b230c;
  // }
  //
  // public BigDecimal getB230d() {
  // return b230d;
  // }
  //
  // public void setB230d(BigDecimal b230d) {
  // this.b230d = b230d;
  // }
  //
  // public BigDecimal getB230e() {
  // return b230e;
  // }
  //
  // public void setB230e(BigDecimal b230e) {
  // this.b230e = b230e;
  // }
  //
  // public BigDecimal getB230f() {
  // return b230f;
  // }
  //
  // public void setB230f(BigDecimal b230f) {
  // this.b230f = b230f;
  // }
  //
  // public BigDecimal getB230g() {
  // return b230g;
  // }
  //
  // public void setB230g(BigDecimal b230g) {
  // this.b230g = b230g;
  // }
  //
  // public BigDecimal getB230h() {
  // return b230h;
  // }
  //
  // public void setB230h(BigDecimal b230h) {
  // this.b230h = b230h;
  // }
  //
  // public BigDecimal getB230j() {
  // return b230j;
  // }
  //
  // public void setB230j(BigDecimal b230j) {
  // this.b230j = b230j;
  // }
  //
  // public BigDecimal getB230k() {
  // return b230k;
  // }
  //
  // public void setB230k(BigDecimal b230k) {
  // this.b230k = b230k;
  // }
  //
  // public BigDecimal getB230l() {
  // return b230l;
  // }
  //
  // public void setB230l(BigDecimal b230l) {
  // this.b230l = b230l;
  // }
  //
  // public BigDecimal getB250a() {
  // return b250a;
  // }
  //
  // public void setB250a(BigDecimal b250a) {
  // this.b250a = b250a;
  // }
  //
  // public BigDecimal getB250b() {
  // return b250b;
  // }
  //
  // public void setB250b(BigDecimal b250b) {
  // this.b250b = b250b;
  // }
  //
  // public BigDecimal getB250c() {
  // return b250c;
  // }
  //
  // public void setB250c(BigDecimal b250c) {
  // this.b250c = b250c;
  // }
  //
  // public BigDecimal getB250d() {
  // return b250d;
  // }
  //
  // public void setB250d(BigDecimal b250d) {
  // this.b250d = b250d;
  // }
  //
  // public BigDecimal getB250e() {
  // return b250e;
  // }
  //
  // public void setB250e(BigDecimal b250e) {
  // this.b250e = b250e;
  // }
  //
  // public BigDecimal getB250f() {
  // return b250f;
  // }
  //
  // public void setB250f(BigDecimal b250f) {
  // this.b250f = b250f;
  // }
  //
  // public BigDecimal getB250g() {
  // return b250g;
  // }
  //
  // public void setB250g(BigDecimal b250g) {
  // this.b250g = b250g;
  // }
  //
  // public BigDecimal getB250h() {
  // return b250h;
  // }
  //
  // public void setB250h(BigDecimal b250h) {
  // this.b250h = b250h;
  // }
  //
  // public BigDecimal getB250j() {
  // return b250j;
  // }
  //
  // public void setB250j(BigDecimal b250j) {
  // this.b250j = b250j;
  // }
  //
  // public BigDecimal getB250k() {
  // return b250k;
  // }
  //
  // public void setB250k(BigDecimal b250k) {
  // this.b250k = b250k;
  // }
  //
  // public BigDecimal getB250l() {
  // return b250l;
  // }
  //
  // public void setB250l(BigDecimal b250l) {
  // this.b250l = b250l;
  // }
  //
  // public BigDecimal getB300a() {
  // return b300a;
  // }
  //
  // public void setB300a(BigDecimal b300a) {
  // this.b300a = b300a;
  // }
  //
  // public BigDecimal getB300b() {
  // return b300b;
  // }
  //
  // public void setB300b(BigDecimal b300b) {
  // this.b300b = b300b;
  // }
  //
  // public BigDecimal getB300c() {
  // return b300c;
  // }
  //
  // public void setB300c(BigDecimal b300c) {
  // this.b300c = b300c;
  // }
  //
  // public BigDecimal getB300d() {
  // return b300d;
  // }
  //
  // public void setB300d(BigDecimal b300d) {
  // this.b300d = b300d;
  // }
  //
  // public BigDecimal getB300e() {
  // return b300e;
  // }
  //
  // public void setB300e(BigDecimal b300e) {
  // this.b300e = b300e;
  // }
  //
  // public BigDecimal getB300f() {
  // return b300f;
  // }
  //
  // public void setB300f(BigDecimal b300f) {
  // this.b300f = b300f;
  // }
  //
  // public BigDecimal getB300g() {
  // return b300g;
  // }
  //
  // public void setB300g(BigDecimal b300g) {
  // this.b300g = b300g;
  // }
  //
  // public BigDecimal getB300h() {
  // return b300h;
  // }
  //
  // public void setB300h(BigDecimal b300h) {
  // this.b300h = b300h;
  // }
  //
  // public BigDecimal getB300j() {
  // return b300j;
  // }
  //
  // public void setB300j(BigDecimal b300j) {
  // this.b300j = b300j;
  // }
  //
  // public BigDecimal getB300k() {
  // return b300k;
  // }
  //
  // public void setB300k(BigDecimal b300k) {
  // this.b300k = b300k;
  // }
  //
  // public BigDecimal getB300l() {
  // return b300l;
  // }
  //
  // public void setB300l(BigDecimal b300l) {
  // this.b300l = b300l;
  // }
  //
  // public BigDecimal getB350a() {
  // return b350a;
  // }
  //
  // public void setB350a(BigDecimal b350a) {
  // this.b350a = b350a;
  // }
  //
  // public BigDecimal getB350b() {
  // return b350b;
  // }
  //
  // public void setB350b(BigDecimal b350b) {
  // this.b350b = b350b;
  // }
  //
  // public BigDecimal getB350c() {
  // return b350c;
  // }
  //
  // public void setB350c(BigDecimal b350c) {
  // this.b350c = b350c;
  // }
  //
  // public BigDecimal getB350d() {
  // return b350d;
  // }
  //
  // public void setB350d(BigDecimal b350d) {
  // this.b350d = b350d;
  // }
  //
  // public BigDecimal getB350e() {
  // return b350e;
  // }
  //
  // public void setB350e(BigDecimal b350e) {
  // this.b350e = b350e;
  // }
  //
  // public BigDecimal getB350f() {
  // return b350f;
  // }
  //
  // public void setB350f(BigDecimal b350f) {
  // this.b350f = b350f;
  // }
  //
  // public BigDecimal getB350g() {
  // return b350g;
  // }
  //
  // public void setB350g(BigDecimal b350g) {
  // this.b350g = b350g;
  // }
  //
  // public BigDecimal getB350h() {
  // return b350h;
  // }
  //
  // public void setB350h(BigDecimal b350h) {
  // this.b350h = b350h;
  // }
  //
  // public BigDecimal getB350j() {
  // return b350j;
  // }
  //
  // public void setB350j(BigDecimal b350j) {
  // this.b350j = b350j;
  // }
  //
  // public BigDecimal getB350k() {
  // return b350k;
  // }
  //
  // public void setB350k(BigDecimal b350k) {
  // this.b350k = b350k;
  // }
  //
  // public BigDecimal getB350l() {
  // return b350l;
  // }
  //
  // public void setB350l(BigDecimal b350l) {
  // this.b350l = b350l;
  // }
  //
  // public BigDecimal getB370a() {
  // return b370a;
  // }
  //
  // public void setB370a(BigDecimal b370a) {
  // this.b370a = b370a;
  // }
  //
  // public BigDecimal getB370b() {
  // return b370b;
  // }
  //
  // public void setB370b(BigDecimal b370b) {
  // this.b370b = b370b;
  // }
  //
  // public BigDecimal getB370c() {
  // return b370c;
  // }
  //
  // public void setB370c(BigDecimal b370c) {
  // this.b370c = b370c;
  // }
  //
  // public BigDecimal getB370d() {
  // return b370d;
  // }
  //
  // public void setB370d(BigDecimal b370d) {
  // this.b370d = b370d;
  // }
  //
  // public BigDecimal getB370e() {
  // return b370e;
  // }
  //
  // public void setB370e(BigDecimal b370e) {
  // this.b370e = b370e;
  // }
  //
  // public BigDecimal getB370f() {
  // return b370f;
  // }
  //
  // public void setB370f(BigDecimal b370f) {
  // this.b370f = b370f;
  // }

  // public BigDecimal getB370g() {
  // return b370g;
  // }
  //
  // public void setB370g(BigDecimal b370g) {
  // this.b370g = b370g;
  // }
  //
  // public BigDecimal getB370h() {
  // return b370h;
  // }
  //
  // public void setB370h(BigDecimal b370h) {
  // this.b370h = b370h;
  // }
  //
  // public BigDecimal getB370j() {
  // return b370j;
  // }
  //
  // public void setB370j(BigDecimal b370j) {
  // this.b370j = b370j;
  // }
  //
  // public BigDecimal getB370k() {
  // return b370k;
  // }
  //
  // public void setB370k(BigDecimal b370k) {
  // this.b370k = b370k;
  // }
  //
  // public BigDecimal getB370l() {
  // return b370l;
  // }
  //
  // public void setB370l(BigDecimal b370l) {
  // this.b370l = b370l;
  // }
  //
  // public BigDecimal getB380a() {
  // return b380a;
  // }
  //
  // public void setB380a(BigDecimal b380a) {
  // this.b380a = b380a;
  // }
  //
  // public BigDecimal getB380b() {
  // return b380b;
  // }
  //
  // public void setB380b(BigDecimal b380b) {
  // this.b380b = b380b;
  // }
  //
  // public BigDecimal getB380c() {
  // return b380c;
  // }
  //
  // public void setB380c(BigDecimal b380c) {
  // this.b380c = b380c;
  // }
  //
  // public BigDecimal getB380d() {
  // return b380d;
  // }
  //
  // public void setB380d(BigDecimal b380d) {
  // this.b380d = b380d;
  // }
  //
  // public BigDecimal getB380e() {
  // return b380e;
  // }
  //
  // public void setB380e(BigDecimal b380e) {
  // this.b380e = b380e;
  // }
  //
  // public BigDecimal getB380f() {
  // return b380f;
  // }
  //
  // public void setB380f(BigDecimal b380f) {
  // this.b380f = b380f;
  // }
  //
  // public BigDecimal getB380g() {
  // return b380g;
  // }
  //
  // public void setB380g(BigDecimal b380g) {
  // this.b380g = b380g;
  // }
  //
  // public BigDecimal getB380h() {
  // return b380h;
  // }
  //
  // public void setB380h(BigDecimal b380h) {
  // this.b380h = b380h;
  // }
  //
  // public BigDecimal getB380j() {
  // return b380j;
  // }
  //
  // public void setB380j(BigDecimal b380j) {
  // this.b380j = b380j;
  // }
  //
  // public BigDecimal getB380k() {
  // return b380k;
  // }
  //
  // public void setB380k(BigDecimal b380k) {
  // this.b380k = b380k;
  // }
  //
  // public BigDecimal getB380l() {
  // return b380l;
  // }
  //
  // public void setB380l(BigDecimal b380l) {
  // this.b380l = b380l;
  // }
  //
  // public BigDecimal getB400a() {
  // return b400a;
  // }
  //
  // public void setB400a(BigDecimal b400a) {
  // this.b400a = b400a;
  // }
  //
  // public BigDecimal getB400b() {
  // return b400b;
  // }
  //
  // public void setB400b(BigDecimal b400b) {
  // this.b400b = b400b;
  // }
  //
  // public BigDecimal getB400c() {
  // return b400c;
  // }
  //
  // public void setB400c(BigDecimal b400c) {
  // this.b400c = b400c;
  // }
  //
  // public BigDecimal getB400d() {
  // return b400d;
  // }
  //
  // public void setB400d(BigDecimal b400d) {
  // this.b400d = b400d;
  // }
  //
  // public BigDecimal getB400e() {
  // return b400e;
  // }
  //
  // public void setB400e(BigDecimal b400e) {
  // this.b400e = b400e;
  // }
  //
  // public BigDecimal getB400f() {
  // return b400f;
  // }
  //
  // public void setB400f(BigDecimal b400f) {
  // this.b400f = b400f;
  // }

  // public BigDecimal getB400g() {
  // return b400g;
  // }
  //
  // public void setB400g(BigDecimal b400g) {
  // this.b400g = b400g;
  // }
  //
  // public BigDecimal getB400h() {
  // return b400h;
  // }
  //
  // public void setB400h(BigDecimal b400h) {
  // this.b400h = b400h;
  // }
  //
  // public BigDecimal getB400j() {
  // return b400j;
  // }
  //
  // public void setB400j(BigDecimal b400j) {
  // this.b400j = b400j;
  // }
  //
  // public BigDecimal getB400k() {
  // return b400k;
  // }
  //
  // public void setB400k(BigDecimal b400k) {
  // this.b400k = b400k;
  // }
  //
  // public BigDecimal getB400l() {
  // return b400l;
  // }
  //
  // public void setB400l(BigDecimal b400l) {
  // this.b400l = b400l;
  // }

}
