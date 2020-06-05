package com.argildx.demo.core.services;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import java.io.Serializable;
import java.util.Map;
import javax.jcr.Session;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Service will provide utility methods related to resource API.
 *
 * @author +Citizen
 */
@Component(name = "QueryBuilder Utility Service", enabled = true, immediate = true, service = QueryBuilderUtil.class)
public class QueryBuilderUtil implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 536601621128497910L;

  /** The Constant LOGGER. */
  private static final Logger LOGGER = LoggerFactory.getLogger(QueryBuilderUtil.class);

  /**
   * Gets query result.
   *
   * @param map the map
   * @param resourceResolver the resource resolver
   * @return the query result
   */
  public SearchResult getQueryResult(Map map , ResourceResolver resourceResolver) {
    QueryBuilder builder = resourceResolver.adaptTo(QueryBuilder.class);
    final String methodName = "getQueryResult";
    LOGGER.debug("start" + methodName);
    SearchResult result = null;
    if (builder != null) {
      Query query =
          builder.createQuery(PredicateGroup.create(map), resourceResolver.adaptTo(Session.class));
      result = query.getResult();
    }
    LOGGER.debug("end" + methodName);
    return result;
  }
}

