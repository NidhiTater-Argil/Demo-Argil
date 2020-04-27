package com.argildx.demo.core.services;


import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

@Component(immediate = true, service = DropdownService.class)
@Designate(ocd=DropdownServiceImpl.DropdownConfig.class)

public class DropdownServiceImpl implements DropdownService{

    private String display_Value;

    @Activate
    protected void activate(DropdownConfig config)
    {
        this.display_Value = config.display_Value();
    }

    @Modified
    protected void modified(DropdownConfig config)
    {
        this.display_Value= config.display_Value();
    }

    @Override
    public String getDisplayValue(){
        return this.display_Value;
    }

    @ObjectClassDefinition(name = "Dropdown Configuration")
    public @interface DropdownConfig
    {
        @AttributeDefinition(
                name = "Display Value",
                description = "select value to be shown",
                options = {
                @Option(label = "Image", value = "Image"),
                @Option(label = "Link", value = "Link"),
                @Option(label = "Text", value = "Text")
        }
        )
        String display_Value() default "Link";

    }
}
