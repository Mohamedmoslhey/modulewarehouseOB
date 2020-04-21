package com.ibox.paper;

import java.nio.file.Path;
import java.nio.file.Paths;



public class App {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    String path = System.getProperty("user.dir");
    Path p = Paths.get(path, "images", "test.jpg");
//    String[] datas = BarcodeScanner.Scan("D:/imagebarcode/1900913.jpg", BarcodeType.Code128);
  }

}
