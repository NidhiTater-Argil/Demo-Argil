package com.argildx.demo.core.services;


import com.google.common.collect.Maps;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(immediate = true, service = NodeSpecificationService.class)
@Designate(ocd = NodeSpecificationServiceImpl.NodeSpecificationConfig.class)

public class NodeSpecificationServiceImpl implements NodeSpecificationService {

  private String folderName;
  private String nodeName;
  private String path;

  @Reference
  ResourceResolverFactory resourceResolverFactory;


  @Activate
  @Modified
  protected void activate(NodeSpecificationConfig config)
      throws PersistenceException, LoginException {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put(ResourceResolverFactory.SUBSERVICE, "NodeSpecificationServiceImpl");
    ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(param);

    this.folderName = config.FolderName();
    this.nodeName = config.NodeName();
    this.path = config.Path();
    createCustomFolder(resolver);
    createCustomNode(resolver);
  }

  public String getFolderName() {
    return folderName;
  }

  public String getNodeName() {
    return nodeName;
  }

  public String getPath() {
    return path;
  }

  @Override
  public void createCustomFolder(ResourceResolver resourceResolver) throws PersistenceException {
    Map<String, Object> properties = Maps.newHashMap();
    String completePath = path + '/' + folderName;
    properties.put("jcr:primaryType", "sling:Folder");
    ResourceUtil
        .getOrCreateResource(resourceResolver, completePath, properties,
            "sling:Folder", true);
  }

  @Override
  public void createCustomNode(ResourceResolver resourceResolver) {
    Map<String, Object> properties = Maps.newHashMap();
    String completePath = path + '/' + folderName + '/' + nodeName;
    try {
      FileReader reader = new FileReader("C:\\Users\\ArgilDX\\Downloads\\MockData.json");
      JSONObject data = new JSONObject(IOUtils.toString(reader));
      for (int iterator = 0; iterator < data.getJSONArray("data").length(); iterator++) {
        String department = data.getJSONArray("data").getJSONObject(iterator)
            .getString("department");
        //Object value = data.getJSONArray("data").get(iterator);
        String jobj = data.getJSONArray("data").getJSONObject(iterator).toString();
        String[] jObjValue = (String[]) properties.get(department);
        List<String> propValue = null;
        if (jObjValue != null) {
          propValue = Arrays.asList(jObjValue);
        }

        if (propValue == null) {
          propValue = new ArrayList<>();
        } else {
          propValue = new ArrayList<>(propValue);
        }
        propValue.add(jobj);
        Object[] obj = propValue.toArray(new String[0]);
        properties.put(department, obj);
      }
      properties.put("jcr:primaryType", "cq:Component");
      ResourceUtil
          .getOrCreateResource(resourceResolver, completePath, properties,
              "cq:Component", true);
    } catch (JSONException | IOException e) {
      e.printStackTrace();
    }

  }


  @ObjectClassDefinition(name = "Node Specification Configuration")
  public @interface NodeSpecificationConfig {

    @AttributeDefinition(
        name = "Folder.Name"
    )
    String FolderName();

    @AttributeDefinition(
        name = "Node.Name"
    )
    String NodeName();

    @AttributeDefinition(
        name = "Path"
    )
    String Path();
//    @AttributeDefinition(
//        name = "Data"
//    )
//    String Data();

  }
}
