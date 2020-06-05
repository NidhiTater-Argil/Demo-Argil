package com.argildx.demo.core.models;

import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy =  DefaultInjectionStrategy.OPTIONAL)

public class ComponentModel {

  @Inject
  String Title;
  @Inject
  String Instance;
  @Inject
  String FirstName;
  @Inject
  String LastName;
  @Inject
  String CompanyName;
  @Inject
  String City;

  public String getTitle() {
    return Title;
  }

  public String getInstance() {
    return Instance;
  }

  public String getFirstName() {
    return FirstName;
  }

  public String getLastName() {
    return LastName;
  }

  public String getCompanyName() {
    return CompanyName;
  }

  public String getCity() {
    return City;
  }




}
