package com.argildx.demo.core.models;

import com.argildx.demo.core.services.DropdownService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = Resource.class)

public class DropDownHelperModel {

    String dropdownValue;
    String value;

    @SlingObject
    Resource resource;
    @Inject
    private DropdownService dropdownService;

    @PostConstruct
    void activate(){

       dropdownValue =  dropdownService.getDisplayValue();
       if(dropdownValue!=null)
        value = resource.getValueMap().get(dropdownValue,String.class);
       else
           value = "None";

    }

    public String getDropdownValue(){
        return this.value;
    }

}
