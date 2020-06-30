package com.argildx.demo.core.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SubjectHelperModel {

  @Inject
  private Resource multi;

  @SlingObject
  ResourceResolver resolver;

  String test[] = {"akash", "Nidhi"};

  Map<String,String> subjectMap = new HashMap<String, String>();

  @PostConstruct
  void activate() {
    if (multi != null) {
      Resource pathResource = resolver.getResource(multi.getPath());
      if (null != pathResource && pathResource.hasChildren()) {
        for (Resource items : pathResource.getChildren()) {
          ValueMap resourceValueMap = items.getValueMap();
          subjectMap.put(resourceValueMap.get("Name", String.class),resourceValueMap.get("Code",String.class));
        }
      }
    }
  }

  public Map<String, String> getSubject() {
    return subjectMap;
  }

}
