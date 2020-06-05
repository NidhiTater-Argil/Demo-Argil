package com.argildx.demo.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Comp2HelperModel {

  @Inject
  String list;

  @SlingObject
  Resource resource;



  ComponentModel componentModel;
  @PostConstruct
  void activate()
  {
    ResourceResolver resourceResolver = resource.getResourceResolver();
    Resource resourceComp1 = resourceResolver.getResource(list);
    componentModel = resourceComp1.adaptTo(ComponentModel.class);
  }

  public ComponentModel getComponentModel() {
    return componentModel;
  }




}
