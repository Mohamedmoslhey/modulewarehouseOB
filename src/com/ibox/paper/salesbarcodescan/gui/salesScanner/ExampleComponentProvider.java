package com.ibox.paper.salesbarcodescan.gui.salesScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.openbravo.client.kernel.BaseComponentProvider;
import org.openbravo.client.kernel.Component;

@ApplicationScoped

public class ExampleComponentProvider extends BaseComponentProvider {

  @Override
  public Component getComponent(String componentId, Map<String, Object> parameters) {
    // TODO Auto-generated method stub
    return null;
  }

  public List<ComponentResource> getGlobalComponentResources() {
    final List<ComponentResource> globalResources = new ArrayList<ComponentResource>();
    globalResources.add(createStaticResource("web/com.ibox.paper/js/jquery-1.9.0.min.js", true));
    globalResources.add(createStaticResource("web/com.ibox.paper/js/quagga.js", true));
    globalResources.add(createStyleSheetResource("web/com.ibox.paper/css/styles.css", false));
    return globalResources;
  }
}
