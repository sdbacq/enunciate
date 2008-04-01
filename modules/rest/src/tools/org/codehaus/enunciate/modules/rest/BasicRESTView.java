package org.codehaus.enunciate.modules.rest;

import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Basic view for REST responses. Renders only the HTTP status code and HTTP status message.
 *
 * @author Ryan Heaton
 */
public class BasicRESTView implements View {

  public static final String MODEL_STATUS = "status";
  public static final String MODEL_STATUS_MESSAGE = "status_message";
  public static final String MODEL_CONTENT_TYPE = "content-type";

  /**
   * Renders only the HTTP status code and HTTP status message.
   *
   * @param model The model.
   * @param request The request.
   * @param response The response.
   */
  public void render(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Integer status = HttpServletResponse.SC_OK;
    if (model.get(MODEL_STATUS) != null) {
      status = (Integer) model.get(MODEL_STATUS);
    }

    String statusMessage = null;
    if (model.get(MODEL_STATUS_MESSAGE) != null) {
      statusMessage = model.get(MODEL_STATUS_MESSAGE).toString();
    }

    if (statusMessage == null) {
      response.setStatus(status);
    }
    else {
      response.setStatus(status, statusMessage);
    }

    String contentType = getContentType(model);
    if (contentType != null) {
      response.setContentType(contentType);
    }

    renderBody(model, request, response);
  }

  /**
   * Gets the content type (MIME type) for this REST view.
   *
   * @param model The model.
   * @return The content type, or null if no content type is to be specified.
   */
  protected String getContentType(Map model) {
    String contentType = null;

    if (model.get(MODEL_CONTENT_TYPE) != null) {
      contentType = model.get(MODEL_CONTENT_TYPE).toString();
    }

    return contentType;
  }

  /**
   * Render the body of the response. Nothing is rendered for the basic REST view.
   *
   * @param model The model.
   * @param request The request.
   * @param response The response.
   */
  protected void renderBody(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
  }
}