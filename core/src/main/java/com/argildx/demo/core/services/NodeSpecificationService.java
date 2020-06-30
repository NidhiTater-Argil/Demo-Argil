package com.argildx.demo.core.services;

import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONException;
import org.json.JSONObject;

public interface NodeSpecificationService {
  void createCustomNode(ResourceResolver resourceResolver);
  void createCustomFolder(ResourceResolver resourceResolver) throws PersistenceException;
  String getFolderName();
  String getNodeName();
  String getPath();

}
