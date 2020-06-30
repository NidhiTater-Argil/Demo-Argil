package com.argildx.demo.core.models;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SubjectModel {

  @SlingObject
  Resource resource;

  SubjectHelperModel subjectHelperModel;

  String path = "/content/page_Nidhi/DemoPage_Nidhi/jcr:content/par1/subject";

  @ChildResource
  private List<Resource> multi;

  String test[] = {"as","ar"};
  @PostConstruct
  void activate()
  {
    ResourceResolver resourceResolver = resource.getResourceResolver();
    Resource subjectResource = resourceResolver.getResource(path);
    subjectHelperModel = subjectResource.adaptTo(SubjectHelperModel.class);
    String Test = "test";
  }

  public SubjectHelperModel getSubjectHelperModel(){
    return subjectHelperModel;
  }
}
