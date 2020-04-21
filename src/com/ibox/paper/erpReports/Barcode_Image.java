package com.ibox.paper.erpReports;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class Barcode_Image {

  public void createPdfWithImageAsStream() {
    try {
      System.out.println("pdf report creation started .... ");

      String outFileNamePDF = "D:/worknew2-4/newgalal/openbravo/modules/com.ibox.paper/src/com/ibox/paper/erpReports/image/createPdfWithImageAsStream.pdf";

      new File(outFileNamePDF).getParentFile().mkdirs();
      String containerJrxmlFile = "D:/worknew2-4/newgalal/openbravo/modules/com.ibox.paper/src/com/ibox/paper/erpReports/createPdfWithImageAsStream.jrxml";

      // pass logo as stream.
      Map<String, Object> parameters = new HashMap<String, Object>();
      File img = new File("/Users/user/Desktop/wallpaper.jpg");
      InputStream fis = new FileInputStream(img);
      System.out.println("printnng");
      parameters.put("barcodelogo", fis);

      InputStream reportStream = new FileInputStream(containerJrxmlFile);
      BufferedInputStream bufferedInputStream = new BufferedInputStream(reportStream);
      JasperDesign jasperDesign = JRXmlLoader.load(bufferedInputStream);

      JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
          new JREmptyDataSource());

      JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
      exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileNamePDF);
      exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
      exporter.exportReport();

      System.out.println("pdf report created .... ");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void createImage(String image_name, String myString) {
    try {
      Code128Bean code128 = new Code128Bean();
      code128.setHeight(15f);
      code128.setModuleWidth(1);
      code128.setQuietZone(10);
      code128.doQuietZone(true);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      BitmapCanvasProvider canvas = new BitmapCanvasProvider(baos, "image/jpeg", 300,
          BufferedImage.TYPE_BYTE_BINARY, false, 0);
      code128.generateBarcode(canvas, myString);
      canvas.finish();
      // write to png file
      FileOutputStream fos = new FileOutputStream("D:\\imagebarcode\\" + image_name);
      fos.write(baos.toByteArray());
      fos.flush();
      fos.close();
    } catch (Exception e) {
      // TODO: handle exception
    }
  }
}
