/*
 * Copyright 2006 Web Cohesion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codehaus.enunciate.modules.xfire_client;

import org.codehaus.enunciate.modules.xfire_client.annotations.RequestWrapperAnnotation;
import org.codehaus.enunciate.modules.xfire_client.annotations.ResponseWrapperAnnotation;
import org.codehaus.enunciate.modules.xfire_client.annotations.WebFaultAnnotation;
import org.codehaus.enunciate.modules.xfire_client.annotations.XmlRootElementAnnotation;
import org.codehaus.xfire.annotations.*;
import org.codehaus.xfire.annotations.soap.SOAPBindingAnnotation;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * WebAnnotations implementation used to explicitly state the annotations on a class.
 *
 * @author Ryan Heaton
 */
public class ExplicitWebAnnotations implements WebAnnotations, Serializable {

  protected HashMap class2XmlRootElement = new HashMap();
  protected HashMap class2WebService = new HashMap();
  protected HashMap class2SOAPBinding = new HashMap();
  protected HashMap class2HandlerChain = new HashMap();
  protected HashMap class2PropertyOrder = new HashMap();
  protected HashMap method2WebMethod = new HashMap();
  protected HashMap method2WebResult = new HashMap();
  protected HashMap method2WebParam = new HashMap();
  protected HashMap method2RequestWrapper = new HashMap();
  protected HashMap method2ResponseWrapper = new HashMap();
  protected HashMap method2SOAPBinding = new HashMap();
  protected HashSet oneWayMethods = new HashSet();
  protected HashMap fault2WebFault = new HashMap();

  /**
   * Writes these annotations to the specified output stream.
   *
   * @param out The output stream.
   * @throws IOException If there was a problem writing it to the output stream.
   */
  public void writeTo(OutputStream out) throws IOException {
    ObjectOutputStream oos = new ObjectOutputStream(out);
    oos.writeObject(this);
    oos.flush();
  }

  /**
   * Reads the annotations from the specified input stream.
   *
   * @param in The input stream.
   * @return The annotations.
   */
  public static ExplicitWebAnnotations readFrom(InputStream in) throws IOException, ClassNotFoundException {
    ObjectInputStream oin = new ObjectInputStream(in);
    try {
      return (ExplicitWebAnnotations) oin.readObject();
    }
    finally {
      oin.close();
    }
  }

  /**
   * Create a key for a class.
   *
   * @param clazz The class for which to create the key.
   * @return The key.
   */
  protected String createKey(Class clazz) {
    return clazz.getName();
  }

  /**
   * Create a key for a method.
   *
   * @param method The method for which to create the key.
   * @return The key.
   */
  protected String createKey(Method method) {
    return createKey(method.getDeclaringClass()) + "." + method.getName();
  }

  /**
   * Create a key for a parameter.
   *
   * @param method The method.
   * @param param  The parameter.
   * @return The key.
   */
  protected String createKey(Method method, int param) {
    return createKey(method) + "." + param;
  }

  // Inherited.
  public boolean hasWebServiceAnnotation(Class clazz) {
    return class2WebService.containsKey(createKey(clazz));
  }

  // Inherited.
  public WebServiceAnnotation getWebServiceAnnotation(Class clazz) {
    return (WebServiceAnnotation) class2WebService.get(createKey(clazz));
  }

  // Inherited.
  public boolean hasWebMethodAnnotation(Method method) {
    return method2WebMethod.containsKey(createKey(method));
  }

  // Inherited.
  public WebMethodAnnotation getWebMethodAnnotation(Method method) {
    return (WebMethodAnnotation) method2WebMethod.get(createKey(method));
  }

  // Inherited.
  public boolean hasWebResultAnnotation(Method method) {
    return method2WebResult.containsKey(createKey(method));
  }

  // Inherited.
  public WebResultAnnotation getWebResultAnnotation(Method method) {
    return (WebResultAnnotation) method2WebResult.get(createKey(method));
  }

  // Inherited.
  public boolean hasWebParamAnnotation(Method method, int parameter) {
    return method2WebParam.containsKey(createKey(method, parameter));
  }

  // Inherited.
  public WebParamAnnotation getWebParamAnnotation(Method method, int parameter) {
    return (WebParamAnnotation) method2WebParam.get(createKey(method, parameter));
  }

  // Inherited.
  public boolean hasOnewayAnnotation(Method method) {
    return oneWayMethods.contains(createKey(method));
  }

  // Inherited.
  public boolean hasSOAPBindingAnnotation(Class clazz) {
    return class2SOAPBinding.containsKey(createKey(clazz));
  }

  // Inherited.
  public boolean hasSOAPBindingAnnotation(Method method) {
    return method2SOAPBinding.containsKey(createKey(method));
  }

  // Inherited.
  public SOAPBindingAnnotation getSOAPBindingAnnotation(Class clazz) {
    return (SOAPBindingAnnotation) class2SOAPBinding.get(createKey(clazz));
  }

  // Inherited.
  public SOAPBindingAnnotation getSOAPBindingAnnotation(Method method) {
    return (SOAPBindingAnnotation) method2SOAPBinding.get(createKey(method));
  }

  // Inherited.
  public boolean hasHandlerChainAnnotation(Class clazz) {
    return class2HandlerChain.containsKey(createKey(clazz));
  }

  // Inherited.
  public HandlerChainAnnotation getHandlerChainAnnotation(Class clazz) {
    return (HandlerChainAnnotation) class2HandlerChain.get(createKey(clazz));
  }

  // Inherited.
  public Map getServiceProperties(Class clazz) {
    return null;
  }

  public RequestWrapperAnnotation getRequestWrapperAnnotation(Method method) {
    return (RequestWrapperAnnotation) method2RequestWrapper.get(createKey(method));
  }

  public ResponseWrapperAnnotation getResponseWrapperAnnotation(Method method) {
    return (ResponseWrapperAnnotation) method2ResponseWrapper.get(createKey(method));
  }

  public WebFaultAnnotation getWebFaultAnnotation(Class fault) {
    return (WebFaultAnnotation) fault2WebFault.get(createKey(fault));
  }

  public XmlRootElementAnnotation getXmlRootElementAnnotation(Class clazz) {
    return (XmlRootElementAnnotation) class2XmlRootElement.get(createKey(clazz));
  }

  public String[] getPropertyOrder(Class clazz) {
    return (String[]) class2PropertyOrder.get(createKey(clazz));
  }
}
