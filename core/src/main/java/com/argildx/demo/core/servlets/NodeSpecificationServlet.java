package com.argildx.demo.core.servlets;

import com.argildx.demo.core.services.NodeSpecificationService;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.Servlet;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    service = {Servlet.class},
    property = {"sling.servlet.methods=GET", "sling.servlet.paths=/bin/nidhi",
        "sling.servlet.selectors=test", "sling.servlet.extension=json"}
)
public class NodeSpecificationServlet extends SlingAllMethodsServlet {


  @Reference
  NodeSpecificationService nodeSpecificationService;

  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws IOException {
    FileReader reader = new FileReader("C:\\Users\\ArgilDX\\Downloads\\MockData.json");
    try {
      JSONObject data = new JSONObject(IOUtils.toString(reader));
      ResourceResolver resourceResolver = request.getResourceResolver();
      nodeSpecificationService.createCustomFolder(resourceResolver);
      //nodeSpecificationService.createCustomNode(resourceResolver, data);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    response.getWriter().println("NodeSpecificationServlet called ");
    response.getWriter().println(nodeSpecificationService.getFolderName());
    response.getWriter().println(nodeSpecificationService.getNodeName());
    response.getWriter().println(nodeSpecificationService.getPath());


  }

}

