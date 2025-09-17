/*
 * Copyright (c) 1997-2018 Oracle and/or its affiliates and others.
 * All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.servlet.http;

import java.io.IOException;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

/**
 * 扩展 {@link javax.servlet.ServletRequest} 接口，为 HTTP Servlet 提供请求信息。
 *
 * <p>
 *     Servlet 容器会创建一个 <code>HttpServletRequest</code> 对象，
 *     并将其作为参数传递给 Servlet 的服务方法（<code>doGet</code>、<code>doPost</code> 等）。
 *
 * @author Various
 */
public interface HttpServletRequest extends ServletRequest {

    /** 基本认证的字符串标识符。值为"BASIC" */
    public static final String BASIC_AUTH = "BASIC";

    /** 表单认证的字符串标识符。值为"FORM" */
    public static final String FORM_AUTH = "FORM";

    /** 客户端证书认证的字符串标识符。值为"CLIENT_CERT" */
    public static final String CLIENT_CERT_AUTH = "CLIENT_CERT";

    /** 摘要认证的字符串标识符。值为"DIGEST" */
    public static final String DIGEST_AUTH = "DIGEST";

    /**
     * 返回用于保护 Servlet 的认证方案名称。
     * 所有 Servlet 容器都支持基本认证、表单认证和客户端证书认证，并可能额外支持摘要认证。
     * 如果 Servlet 未经过认证，则返回 <code>null</code>。
     *
     * <p>与 CGI 变量 AUTH_TYPE 的值相同。
     *
     * @return 用于 == 比较的静态成员 BASIC_AUTH, FORM_AUTH, CLIENT_CERT_AUTH, DIGEST_AUTH 之一，
     *         或表示认证方案的容器特定字符串，如果请求未认证则返回 <code>null</code>
     */
    public String getAuthType();

    /**
     * 返回包含客户端随此请求发送的所有 <code>Cookie</code> 对象的数组。
     * 如果未发送任何 cookies，则此方法返回 <code>null</code>。
     *
     * @return 包含此请求中所有 <code>Cookie</code> 的数组，如果请求没有 cookies 则返回 <code>null</code>
     */
    public Cookie[] getCookies();

    /**
     * 返回指定请求头的值作为表示 <code>Date</code> 对象的 <code>long</code> 值。
     * 将此方法用于包含日期信息的请求头，例如 <code>If-Modified-Since</code>。
     *
     * <p>
     *     日期返回为自 1970 年 1 月 1 日 GMT 以来的毫秒数。请求头名称不区分大小写。
     *
     * <p>
     *     如果请求中没有指定名称的请求头，则该方法返回 -1。
     *     如果请求头值无法转换为日期，该方法将抛出 <code>IllegalArgumentException</code>。
     *
     * @param name 指定请求头名称的 <code>String</code>
     * @return 表示请求头中指定日期的 <code>long</code> 值（自 1970 年 1 月 1 日 GMT 以来的毫秒数），
     *         如果请求中未包含指定请求头则返回 -1
     * @exception IllegalArgumentException 如果请求头值无法转换为日期
     */
    public long getDateHeader(String name);

    /**
     * 返回指定请求头的值作为 <code>String</code>。
     * 如果请求不包含指定名称的请求头，则此方法返回 <code>null</code>。
     * 如果存在多个相同名称的请求头，则此方法返回请求中的第一个头。请求头名称不区分大小写。
     * 此方法可用于任何请求头。
     *
     * @param name 指定请求头名称的 <code>String</code>
     * @return 包含请求头值的 <code>String</code>，如果请求没有该名称的请求头则返回 <code>null</code>
     */
    public String getHeader(String name);


    /**
     * 以 <code>String</code> 对象的 <code>Enumeration</code> 形式返回指定请求头的所有值。
     *
     * <p>
     *     某些请求头（例如 <code>Accept-Language</code>）可能被客户端作为多个具有不同值的头部发送，
     *     而不是以逗号分隔列表的形式发送单个头部。
     *
     * <p>
     *     如果请求不包含任何指定名称的请求头，则此方法返回一个空的 <code>Enumeration</code>。
     *     请求头名称不区分大小写。此方法可用于任何请求头。
     *
     * @param name 指定请求头名称的 <code>String</code>
     * @return 包含请求头值的 <code>Enumeration</code>。如果请求没有任何该名称的请求头，则返回空枚举。
     *         如果容器不允许访问请求头信息，则返回 null
     */
    public Enumeration<String> getHeaders(String name);

    /**
     * 返回此请求包含的所有请求头名称的枚举。如果请求没有任何请求头，则此方法返回空枚举。
     *
     * <p>
     *     某些 Servlet 容器不允许 Servlet 使用此方法访问请求头，在这种情况下，此方法返回 <code>null</code>。
     *
     * @return 包含此请求发送的所有请求头名称的枚举；如果请求没有请求头，则返回空枚举；
     *         如果 Servlet 容器不允许 Servlet 使用此方法，则返回 <code>null</code>
     */
    public Enumeration<String> getHeaderNames();

    /**
     * 将指定请求头的值作为 <code>int</code> 类型返回。
     * 如果请求不包含指定名称的请求头，则此方法返回 -1。
     * 如果请求头值无法转换为整数，则此方法抛出 <code>NumberFormatException</code>。
     *
     * <p>请求头名称不区分大小写。
     *
     * @param name 指定请求头名称的 <code>String</code>
     * @return 表示请求头值的整数，如果请求没有该名称的请求头则返回 -1
     * @exception NumberFormatException 如果请求头值无法转换为 <code>int</code>
     */
    public int getIntHeader(String name);

    /**
     * <p>
     *     返回调用此 {@code HttpServletRequest} 对应的 {@link HttpServlet} 时所使用的 {@link HttpServletMapping}。
     *     结果中不包含任何适用的 {@link javax.servlet.Filter} 的映射信息。
     *
     * <p>
     *     如果当前活动的 {@link javax.servlet.Servlet} 调用是通过调用 {@link ServletRequest#getRequestDispatcher}
     *     后再调用 {@link RequestDispatcher#forward} 获得的，则返回的 {@code HttpServletMapping}
     *     对应于用于获取 {@link RequestDispatcher} 的路径。
     *
     * <p>
     *     如果当前活动的 {@code Servlet} 调用是通过调用 {@link ServletRequest#getRequestDispatcher} 后
     *     再调用 {@link RequestDispatcher#include} 获得的，
     *     则返回的 {@code HttpServletMapping} 对应于导致调用序列中第一个 {@code Servlet} 的路径。
     *
     * <p>
     *     如果当前活动的 {@code Servlet} 调用是通过调用 {@link javax.servlet.AsyncContext#dispatch} 获得的，
     *     则返回的 {@code HttpServletMapping} 对应于导致调用序列中第一个 {@code Servlet} 的路径。
     *
     * <p>
     *     有关 {@code HttpServletMapping} 的其他请求属性，请参阅 {@link javax.servlet.RequestDispatcher#FORWARD_MAPPING}、
     *     {@link javax.servlet.RequestDispatcher#INCLUDE_MAPPING} 和 {@link javax.servlet.AsyncContext#ASYNC_MAPPING}。
     *
     * <p>
     *     如果当前活动的 {@code Servlet} 调用是通过调用 {@link javax.servlet.ServletContext#getNamedDispatcher} 获得的，
     *     则返回的 {@code HttpServletMapping} 对应于最后应用于此请求的映射路径。
     *
     * <p>
     *     返回的对象是不可变的。符合 Servlet 4.0 规范的实现必须重写此方法。
     *
     * @implSpec 默认实现返回一个 {@code HttpServletMapping}，其匹配值、模式和 servlet 名称均返回空字符串，匹配类型返回 {@code null}。
     * @return 描述当前请求调用方式的 {@code HttpServletMapping} 实例
     * @since 4.0
     */
    default public HttpServletMapping getHttpServletMapping() {
        return new HttpServletMapping() {
            @Override
            public String getMatchValue() {
                return "";
            }

            @Override
            public String getPattern() {
                return "";
            }

            @Override
            public String getServletName() {
                return "";
            }

            @Override
            public MappingMatch getMappingMatch() {
                return null;
            }

            @Override
            public String toString() {
                return "MappingImpl{" + "matchValue=" + getMatchValue() + ", pattern=" + getPattern() + ", servletName="
                        + getServletName() + ", mappingMatch=" + getMappingMatch() + "} HttpServletRequest {"
                        + HttpServletRequest.this.toString() + '}';
            }

        };
    }

    /**
     * 返回此请求使用的 HTTP 方法名称，例如 GET、POST 或 PUT。
     * 与 CGI 变量 REQUEST_METHOD 的值相同。
     *
     * @return 指定此请求所用方法名称的 <code>String</code>
     */
    public String getMethod();

    /**
     * 返回客户端发送此请求时 URL 中包含的额外路径信息。
     * 额外路径信息位于 servlet 路径之后、查询字符串之前，并以 "/" 字符开头。
     *
     * <p>如果没有额外路径信息，则此方法返回 <code>null</code>。
     *
     * <p>与 CGI 变量 PATH_INFO 的值相同。
     *
     * @return 经过 Web 容器解码的 <code>String</code>，指定请求 URL 中位于 servlet 路径之后、
     *         查询字符串之前的额外路径信息；如果 URL 没有任何额外路径信息则返回 <code>null</code>
     */
    public String getPathInfo();

    /**
     * 返回 servlet 名称之后、查询字符串之前的额外路径信息，并将其转换为真实路径。与 CGI 变量 PATH_TRANSLATED 的值相同。
     *
     * <p>
     *     如果 URL 没有任何额外路径信息，则此方法返回 <code>null</code>；
     *     或者当 servlet 容器因任何原因（例如从归档文件执行 Web 应用程序时）无法将虚拟路径转换为真实路径时也会返回 null。
     *
     * <p>Web 容器不会对此字符串进行解码。
     *
     * @return 指定真实路径的 <code>String</code>，如果 URL 没有任何额外路径信息则返回 <code>null</code>
     */
    public String getPathTranslated();

    /**
     * 实例化一个新的 {@link PushBuilder} 实例，用于从当前请求发出服务器推送响应。
     * 如果当前连接不支持服务器推送，或客户端通过值为 {@code 0}（零）的 {@code SETTINGS_ENABLE_PUSH}
     * 设置帧禁用了服务器推送，则此方法返回 null。
     *
     * @implSpec 默认实现返回 null。
     * @return 用于从当前请求发出服务器推送响应的 {@link PushBuilder}，如果不支持推送则返回 null
     * @since Servlet 4.0
     */
    default public PushBuilder newPushBuilder() {
        return null;
    }

    /**
     * 返回请求 URI 中指示请求上下文的部分。上下文路径总是位于请求 URI 的开头。
     * 路径以 "/" 字符开头但不以 "/" 字符结尾。对于默认（根）上下文中的 servlet，此方法返回 ""。
     * 容器不会解码此字符串。
     *
     * <p>
     * Servlet 容器可能会通过多个上下文路径匹配上下文。在这种情况下，此方法将返回请求实际使用的上下文路径，
     * 该路径可能与 {@link javax.servlet.ServletContext#getContextPath()} 方法返回的路径不同。
     * {@link javax.servlet.ServletContext#getContextPath()} 返回的上下文路径应视为应用程序的主上下文路径或首选上下文路径。
     *
     * @return 指定请求 URI 中指示请求上下文部分的 <code>String</code>
     * @see javax.servlet.ServletContext#getContextPath()
     */
    public String getContextPath();

    /**
     * Returns the query string that is contained in the request URL after the path. This method returns
     * <code>null</code> if the URL does not have a query string. Same as the value of the CGI variable QUERY_STRING.
     *
     * @return a <code>String</code> containing the query string or <code>null</code> if the URL contains no query
     *         string. The value is not decoded by the container.
     */
    public String getQueryString();

    /**
     * Returns the login of the user making this request, if the user has been authenticated, or <code>null</code> if
     * the user has not been authenticated. Whether the user name is sent with each subsequent request depends on the
     * browser and type of authentication. Same as the value of the CGI variable REMOTE_USER.
     *
     * @return a <code>String</code> specifying the login of the user making this request, or <code>null</code> if the
     *         user login is not known
     */
    public String getRemoteUser();

    /**
     * Returns a boolean indicating whether the authenticated user is included in the specified logical "role". Roles
     * and role membership can be defined using deployment descriptors. If the user has not been authenticated, the
     * method returns <code>false</code>.
     *
     * <p>
     * The role name "*" should never be used as an argument in calling <code>isUserInRole</code>. Any call to
     * <code>isUserInRole</code> with "*" must return false. If the role-name of the security-role to be tested is "**",
     * and the application has NOT declared an application security-role with role-name "**", <code>isUserInRole</code>
     * must only return true if the user has been authenticated; that is, only when {@link #getRemoteUser} and
     * {@link #getUserPrincipal} would both return a non-null value. Otherwise, the container must check the user for
     * membership in the application role.
     *
     * @param role a <code>String</code> specifying the name of the role
     *
     * @return a <code>boolean</code> indicating whether the user making this request belongs to a given role;
     *         <code>false</code> if the user has not been authenticated
     */
    public boolean isUserInRole(String role);

    /**
     * Returns a <code>java.security.Principal</code> object containing the name of the current authenticated user. If
     * the user has not been authenticated, the method returns <code>null</code>.
     *
     * @return a <code>java.security.Principal</code> containing the name of the user making this request;
     *         <code>null</code> if the user has not been authenticated
     */
    public java.security.Principal getUserPrincipal();

    /**
     * Returns the session ID specified by the client. This may not be the same as the ID of the current valid session
     * for this request. If the client did not specify a session ID, this method returns <code>null</code>.
     *
     * @return a <code>String</code> specifying the session ID, or <code>null</code> if the request did not specify a
     *         session ID
     *
     * @see #isRequestedSessionIdValid
     */
    public String getRequestedSessionId();

    /**
     * Returns the part of this request's URL from the protocol name up to the query string in the first line of the
     * HTTP request. The web container does not decode this String. For example:
     *
     * <table summary="Examples of Returned Values">
     * <tr align=left>
     * <th>First line of HTTP request</th>
     * <th>Returned Value</th>
     * <tr>
     * <td>POST /some/path.html HTTP/1.1
     * <td>
     * <td>/some/path.html
     * <tr>
     * <td>GET http://foo.bar/a.html HTTP/1.0
     * <td>
     * <td>/a.html
     * <tr>
     * <td>HEAD /xyz?a=b HTTP/1.1
     * <td>
     * <td>/xyz
     * </table>
     *
     * <p>
     * To reconstruct an URL with a scheme and host, use {@link HttpUtils#getRequestURL}.
     *
     * @return a <code>String</code> containing the part of the URL from the protocol name up to the query string
     *
     * @see HttpUtils#getRequestURL
     */
    public String getRequestURI();

    /**
     * Reconstructs the URL the client used to make the request. The returned URL contains a protocol, server name, port
     * number, and server path, but it does not include query string parameters.
     *
     * <p>
     * If this request has been forwarded using {@link javax.servlet.RequestDispatcher#forward}, the server path in the
     * reconstructed URL must reflect the path used to obtain the RequestDispatcher, and not the server path specified
     * by the client.
     *
     * <p>
     * Because this method returns a <code>StringBuffer</code>, not a string, you can modify the URL easily, for
     * example, to append query parameters.
     *
     * <p>
     * This method is useful for creating redirect messages and for reporting errors.
     *
     * @return a <code>StringBuffer</code> object containing the reconstructed URL
     */
    public StringBuffer getRequestURL();

    /**
     * Returns the part of this request's URL that calls the servlet. This path starts with a "/" character and includes
     * either the servlet name or a path to the servlet, but does not include any extra path information or a query
     * string. Same as the value of the CGI variable SCRIPT_NAME.
     *
     * <p>
     * This method will return an empty string ("") if the servlet used to process this request was matched using the
     * "/*" pattern.
     *
     * @return a <code>String</code> containing the name or path of the servlet being called, as specified in the
     *         request URL, decoded, or an empty string if the servlet used to process the request is matched using the
     *         "/*" pattern.
     */
    public String getServletPath();

    /**
     * Returns the current <code>HttpSession</code> associated with this request or, if there is no current session and
     * <code>create</code> is true, returns a new session.
     *
     * <p>
     * If <code>create</code> is <code>false</code> and the request has no valid <code>HttpSession</code>, this method
     * returns <code>null</code>.
     *
     * <p>
     * To make sure the session is properly maintained, you must call this method before the response is committed. If
     * the container is using cookies to maintain session integrity and is asked to create a new session when the
     * response is committed, an IllegalStateException is thrown.
     *
     * @param create <code>true</code> to create a new session for this request if necessary; <code>false</code> to
     *               return <code>null</code> if there's no current session
     *
     * @return the <code>HttpSession</code> associated with this request or <code>null</code> if <code>create</code> is
     *         <code>false</code> and the request has no valid session
     *
     * @see #getSession()
     */
    public HttpSession getSession(boolean create);

    /**
     * Returns the current session associated with this request, or if the request does not have a session, creates one.
     *
     * @return the <code>HttpSession</code> associated with this request
     *
     * @see #getSession(boolean)
     */
    public HttpSession getSession();

    /**
     * Change the session id of the current session associated with this request and return the new session id.
     *
     * @return the new session id
     *
     * @throws IllegalStateException if there is no session associated with the request
     *
     * @since Servlet 3.1
     */
    public String changeSessionId();

    /**
     * Checks whether the requested session ID is still valid.
     *
     * <p>
     * If the client did not specify any session ID, this method returns <code>false</code>.
     *
     * @return <code>true</code> if this request has an id for a valid session in the current session context;
     *         <code>false</code> otherwise
     *
     * @see #getRequestedSessionId
     * @see #getSession
     * @see HttpSessionContext
     */
    public boolean isRequestedSessionIdValid();

    /**
     * <p>
     * Checks whether the requested session ID was conveyed to the server as an HTTP cookie.
     * </p>
     *
     * @return <code>true</code> if the session ID was conveyed to the server an an HTTP cookie; otherwise,
     *         <code>false</code>
     *
     * @see #getSession
     */
    public boolean isRequestedSessionIdFromCookie();

    /**
     * <p>
     * Checks whether the requested session ID was conveyed to the server as part of the request URL.
     * </p>
     *
     * @return <code>true</code> if the session ID was conveyed to the server as part of a URL; otherwise,
     *         <code>false</code>
     *
     * @see #getSession
     */
    public boolean isRequestedSessionIdFromURL();

    /**
     * @deprecated As of Version 2.1 of the Java Servlet API, use {@link #isRequestedSessionIdFromURL} instead.
     *
     * @return <code>true</code> if the session ID was conveyed to the server as part of a URL; otherwise,
     *         <code>false</code>
     */
    @Deprecated
    public boolean isRequestedSessionIdFromUrl();

    /**
     * Use the container login mechanism configured for the <code>ServletContext</code> to authenticate the user making
     * this request.
     *
     * <p>
     * This method may modify and commit the argument <code>HttpServletResponse</code>.
     *
     * @param response The <code>HttpServletResponse</code> associated with this <code>HttpServletRequest</code>
     *
     * @return <code>true</code> when non-null values were or have been established as the values returned by
     *         <code>getUserPrincipal</code>, <code>getRemoteUser</code>, and <code>getAuthType</code>. Return
     *         <code>false</code> if authentication is incomplete and the underlying login mechanism has committed, in
     *         the response, the message (e.g., challenge) and HTTP status code to be returned to the user.
     *
     * @throws IOException           if an input or output error occurred while reading from this request or writing to
     *                               the given response
     *
     * @throws IllegalStateException if the login mechanism attempted to modify the response and it was already
     *                               committed
     *
     * @throws ServletException      if the authentication failed and the caller is responsible for handling the error
     *                               (i.e., the underlying login mechanism did NOT establish the message and HTTP status
     *                               code to be returned to the user)
     *
     * @since Servlet 3.0
     */
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException;

    /**
     * Validate the provided username and password in the password validation realm used by the web container login
     * mechanism configured for the <code>ServletContext</code>.
     *
     * <p>
     * This method returns without throwing a <code>ServletException</code> when the login mechanism configured for the
     * <code>ServletContext</code> supports username password validation, and when, at the time of the call to login,
     * the identity of the caller of the request had not been established (i.e, all of <code>getUserPrincipal</code>,
     * <code>getRemoteUser</code>, and <code>getAuthType</code> return null), and when validation of the provided
     * credentials is successful. Otherwise, this method throws a <code>ServletException</code> as described below.
     *
     * <p>
     * When this method returns without throwing an exception, it must have established non-null values as the values
     * returned by <code>getUserPrincipal</code>, <code>getRemoteUser</code>, and <code>getAuthType</code>.
     *
     * @param username The <code>String</code> value corresponding to the login identifier of the user.
     *
     * @param password The password <code>String</code> corresponding to the identified user.
     *
     * @exception ServletException if the configured login mechanism does not support username password authentication,
     *                             or if a non-null caller identity had already been established (prior to the call to
     *                             login), or if validation of the provided username and password fails.
     *
     * @since Servlet 3.0
     */
    public void login(String username, String password) throws ServletException;

    /**
     * Establish <code>null</code> as the value returned when <code>getUserPrincipal</code>, <code>getRemoteUser</code>,
     * and <code>getAuthType</code> is called on the request.
     *
     * @exception ServletException if logout fails
     *
     * @since Servlet 3.0
     */
    public void logout() throws ServletException;

    /**
     * Gets all the {@link Part} components of this request, provided that it is of type
     * <code>multipart/form-data</code>.
     *
     * <p>
     * If this request is of type <code>multipart/form-data</code>, but does not contain any <code>Part</code>
     * components, the returned <code>Collection</code> will be empty.
     *
     * <p>
     * Any changes to the returned <code>Collection</code> must not affect this <code>HttpServletRequest</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the <code>Part</code> components of this request
     *
     * @throws IOException           if an I/O error occurred during the retrieval of the {@link Part} components of
     *                               this request
     *
     * @throws ServletException      if this request is not of type <code>multipart/form-data</code>
     *
     * @throws IllegalStateException if the request body is larger than <code>maxRequestSize</code>, or any
     *                               <code>Part</code> in the request is larger than <code>maxFileSize</code>, or there
     *                               is no <code>@MultipartConfig</code> or <code>multipart-config</code> in deployment
     *                               descriptors
     *
     * @see javax.servlet.annotation.MultipartConfig#maxFileSize
     * @see javax.servlet.annotation.MultipartConfig#maxRequestSize
     *
     * @since Servlet 3.0
     */
    public Collection<Part> getParts() throws IOException, ServletException;

    /**
     * Gets the {@link Part} with the given name.
     *
     * @param name the name of the requested <code>Part</code>
     *
     * @return The <code>Part</code> with the given name, or <code>null</code> if this request is of type
     *         <code>multipart/form-data</code>, but does not contain the requested <code>Part</code>
     *
     * @throws IOException           if an I/O error occurred during the retrieval of the requested <code>Part</code>
     * @throws ServletException      if this request is not of type <code>multipart/form-data</code>
     * @throws IllegalStateException if the request body is larger than <code>maxRequestSize</code>, or any
     *                               <code>Part</code> in the request is larger than <code>maxFileSize</code>, or there
     *                               is no <code>@MultipartConfig</code> or <code>multipart-config</code> in deployment
     *                               descriptors
     *
     * @see javax.servlet.annotation.MultipartConfig#maxFileSize
     * @see javax.servlet.annotation.MultipartConfig#maxRequestSize
     *
     * @since Servlet 3.0
     */
    public Part getPart(String name) throws IOException, ServletException;

    /**
     * Creates an instance of <code>HttpUpgradeHandler</code> for a given class and uses it for the http protocol
     * upgrade processing.
     *
     * @param              <T> The {@code Class}, which extends {@link HttpUpgradeHandler}, of the {@code handlerClass}.
     *
     * @param handlerClass The <code>HttpUpgradeHandler</code> class used for the upgrade.
     *
     * @return an instance of the <code>HttpUpgradeHandler</code>
     *
     * @exception IOException      if an I/O error occurred during the upgrade
     * @exception ServletException if the given <code>handlerClass</code> fails to be instantiated
     *
     * @see javax.servlet.http.HttpUpgradeHandler
     * @see javax.servlet.http.WebConnection
     *
     * @since Servlet 3.1
     */
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException;

    /**
     * Get the request trailer fields.
     *
     * <p>
     * The returned map is not backed by the {@code HttpServletRequest} object, so changes in the returned map are not
     * reflected in the {@code HttpServletRequest} object, and vice-versa.
     * </p>
     *
     * <p>
     * {@link #isTrailerFieldsReady()} should be called first to determine if it is safe to call this method without
     * causing an exception.
     * </p>
     *
     * @implSpec The default implementation returns an empty map.
     *
     * @return A map of trailer fields in which all the keys are in lowercase, regardless of the case they had at the
     *         protocol level. If there are no trailer fields, yet {@link #isTrailerFieldsReady} is returning true, the
     *         empty map is returned.
     *
     * @throws IllegalStateException if {@link #isTrailerFieldsReady()} is false
     *
     * @since Servlet 4.0
     */
    default public Map<String, String> getTrailerFields() {
        return Collections.emptyMap();
    }

    /**
     * Return a boolean indicating whether trailer fields are ready to read using {@link #getTrailerFields}.
     *
     * This methods returns true immediately if it is known that there is no trailer in the request, for instance, the
     * underlying protocol (such as HTTP 1.0) does not supports the trailer fields, or the request is not in chunked
     * encoding in HTTP 1.1. And the method also returns true if both of the following conditions are satisfied:
     * <ol type="a">
     * <li>the application has read all the request data and an EOF indication has been returned from the
     * {@link #getReader} or {@link #getInputStream}.
     * <li>all the trailer fields sent by the client have been received. Note that it is possible that the client has
     * sent no trailer fields.
     * </ol>
     *
     * @implSpec The default implementation returns false.
     *
     * @return a boolean whether trailer fields are ready to read
     *
     * @since Servlet 4.0
     */
    default public boolean isTrailerFieldsReady() {
        return true;
    }
}
