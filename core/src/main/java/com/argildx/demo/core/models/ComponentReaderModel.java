package com.argildx.demo.core.models;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.argildx.demo.core.services.QueryBuilderUtil;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.crx.JcrConstants;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import org.apache.commons.collections.iterators.TransformIterator;
import org.apache.commons.collections4.KeyValue;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.osgi.service.component.annotations.Reference;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class})
public class ComponentReaderModel {


  @Inject
  SlingHttpServletRequest slingHttpServletRequest;

  private List<KeyValue> keyValueList;

  @OSGiService
  QueryBuilderUtil queryBuilderUtil;

  @PostConstruct
  public void init() {
    keyValueList = new ArrayList<>();

//    KeyValue keyValue = new KeyValue("key1","value1");
//    keyValueList.add(keyValue);
//    keyValue = new KeyValue("key2","value2");
    keyValueList = getKeyValueList();
    ResourceResolver resourceResolver = slingHttpServletRequest.getResourceResolver();
    DataSource ds =
        new SimpleDataSource(
            new TransformIterator(
                keyValueList.iterator(),
                input -> {
                  KeyValue keyValueObj = (KeyValue) input;
                  ValueMap vm = new ValueMapDecorator(new HashMap<>());
                  vm.put("value", keyValueObj.text);
                  vm.put("text", keyValueObj.value);
                  return new ValueMapResource(
                      resourceResolver, new ResourceMetadata(),
                      JcrConstants.NT_UNSTRUCTURED, vm);
                }));

    slingHttpServletRequest.setAttribute(DataSource.class.getName(), ds);
  }

  private List<KeyValue> getKeyValueList() {
    String instance = null;
    Map<String, String> predicateMap = new HashMap<>();

    List<KeyValue> list = new ArrayList<>();

    predicateMap.put("path", "/content/page_Nidhi/DemoPage_Nidhi/jcr:content");
    predicateMap.put("property", "sling:resourceType");
    predicateMap.put("property.value", "demoProject/components/content/Comp1");
    predicateMap.put("type", "nt:unstructured");
    predicateMap.put("p.limit", "-1");
    ResourceResolver resourceResolver = slingHttpServletRequest.getResourceResolver();
    SearchResult pages = queryBuilderUtil.getQueryResult(predicateMap, resourceResolver);
    KeyValue keyValue = null;
    if (pages != null) {
      for (Hit hit : pages.getHits()) {
        try {
          instance = hit.getResource().getValueMap().get("Instance", String.class);
          if (StringUtils.isNotBlank(instance)) {
            keyValue = new KeyValue(hit.getPath(), instance);
            list.add(keyValue);
          }


        } catch (RepositoryException e) {
          e.printStackTrace();
        }

      }
      Collections.sort(list);
    }
    return list;
  }

  private class KeyValue implements Comparable {

    /**
     * key property.
     */
    private String text;
    /**
     * value property.
     */
    private String value;

    /**
     * constructor instance intance.
     *
     * @param newKey   -
     * @param newValue -
     */
    private KeyValue(final String newKey, final String newValue) {
      this.text = newKey;
      this.value = newValue;
    }

    @Override
    public int compareTo(Object o) {
      String value = ((KeyValue) o).value;
      return this.value.compareTo(value);
    }
  }
}
