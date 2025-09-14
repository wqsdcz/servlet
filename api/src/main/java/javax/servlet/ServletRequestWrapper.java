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

package javax.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * 为 ServletRequest 接口提供了一个便捷的实现，开发人员若希望使请求适应 Servlet 可对此类进行子类化。
 * 此类实现了包装器（Wrapper）或装饰器（Decorator）模式。其方法默认调用被包装的请求对象。
 *
 * @see javax.servlet.ServletRequest
 *
 * @since Servlet 2.3
 */
public class ServletRequestWrapper implements ServletRequest {

    private ServletRequest request;

    /**
     * 创建一个用于包装给定请求对象的 ServletRequest 适配器。
     *
     * @param request 要被包装的 {@link ServletRequest}
     * @throws java.lang.IllegalArgumentException 如果请求为 null
     */
    public ServletRequestWrapper(ServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        this.request = request;
    }

    /**
     * 返回被包装的请求对象。
     *
     * @return 被包装的 {@link ServletRequest}
     */
    public ServletRequest getRequest() {
        return this.request;
    }

    /**
     * 设置要被包装的请求对象。
     *
     * @param request 要安装的 {@link ServletRequest}
     * @throws java.lang.IllegalArgumentException 如果请求为 null
     */
    public void setRequest(ServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        this.request = request;
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getAttribute(String name) 方法。
     */
    @Override
    public Object getAttribute(String name) {
        return this.request.getAttribute(name);
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getAttributeNames() 方法。
     */
    @Override
    public Enumeration<String> getAttributeNames() {
        return this.request.getAttributeNames();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getCharacterEncoding() 方法。
     */
    @Override
    public String getCharacterEncoding() {
        return this.request.getCharacterEncoding();
    }

    /**
     * 此方法的默认行为是在被包装请求对象上设置字符编码。
     */
    @Override
    public void setCharacterEncoding(String enc) throws UnsupportedEncodingException {
        this.request.setCharacterEncoding(enc);
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getContentLength() 方法。
     */
    @Override
    public int getContentLength() {
        return this.request.getContentLength();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getContentLengthLong() 方法。
     *
     * @since Servlet 3.1
     */
    @Override
    public long getContentLengthLong() {
        return this.request.getContentLengthLong();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getContentType() 方法。
     */
    @Override
    public String getContentType() {
        return this.request.getContentType();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getInputStream() 方法。
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return this.request.getInputStream();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getParameter(String name) 方法。
     */
    @Override
    public String getParameter(String name) {
        return this.request.getParameter(name);
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getParameterMap() 方法。
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        return this.request.getParameterMap();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getParameterNames() 方法。
     */
    @Override
    public Enumeration<String> getParameterNames() {
        return this.request.getParameterNames();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getParameterValues(String name) 方法。
     */
    @Override
    public String[] getParameterValues(String name) {
        return this.request.getParameterValues(name);
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getProtocol() 方法。
     */
    @Override
    public String getProtocol() {
        return this.request.getProtocol();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getScheme() 方法。
     */
    @Override
    public String getScheme() {
        return this.request.getScheme();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getServerName() 方法。
     */
    @Override
    public String getServerName() {
        return this.request.getServerName();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getServerPort() 方法。
     */
    @Override
    public int getServerPort() {
        return this.request.getServerPort();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getReader() 方法。
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return this.request.getReader();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getRemoteAddr() 方法。
     */
    @Override
    public String getRemoteAddr() {
        return this.request.getRemoteAddr();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 getRemoteHost() 方法。
     */
    @Override
    public String getRemoteHost() {
        return this.request.getRemoteHost();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 setAttribute(String name, Object o) 方法。
     */
    @Override
    public void setAttribute(String name, Object o) {
        this.request.setAttribute(name, o);
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 removeAttribute(String name) 方法。
     */
    @Override
    public void removeAttribute(String name) {
        this.request.removeAttribute(name);
    }

    /**
     * 此方法的默认行为是返回被包装请求对象上的 getLocale() 方法值。
     */
    @Override
    public Locale getLocale() {
        return this.request.getLocale();
    }

    /**
     * 此方法的默认行为是返回被包装请求对象上的 getLocales() 方法值。
     */
    @Override
    public Enumeration<Locale> getLocales() {
        return this.request.getLocales();
    }

    /**
     * 此方法的默认行为是返回被包装请求对象上的 isSecure() 方法值。
     */
    @Override
    public boolean isSecure() {
        return this.request.isSecure();
    }

    /**
     * 此方法的默认行为是返回被包装请求对象上的 getRequestDispatcher(String path) 方法值。
     */
    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return this.request.getRequestDispatcher(path);
    }

    /**
     * 此方法的默认行为是返回被包装请求对象上的 getRealPath(String path) 方法值。
     *
     * @deprecated 自 Java Servlet API 2.1 版本起，改用 {@link ServletContext#getRealPath}
     */
    @Override
    @Deprecated
    public String getRealPath(String path) {
        return this.request.getRealPath(path);
    }

    /**
     * 此方法的默认行为是返回被包装请求对象上的 getRemotePort() 方法值。
     *
     * @since Servlet 2.4
     */
    @Override
    public int getRemotePort() {
        return this.request.getRemotePort();
    }

    /**
     * 此方法的默认行为是返回被包装请求对象上的 getLocalName() 方法值。
     *
     * @since Servlet 2.4
     */
    @Override
    public String getLocalName() {
        return this.request.getLocalName();
    }

    /**
     * 此方法的默认行为是返回被包装请求对象上的 getLocalAddr() 方法值。
     *
     * @since Servlet 2.4
     */
    @Override
    public String getLocalAddr() {
        return this.request.getLocalAddr();
    }

    /**
     * 此方法的默认行为是返回被包装请求对象上的 getLocalPort() 方法值。
     *
     * @since Servlet 2.4
     */
    @Override
    public int getLocalPort() {
        return this.request.getLocalPort();
    }

    /**
     * 获取被包装的 Servlet 请求最后一次被分发到的 Servlet 上下文。
     *
     * @return 被包装的 Servlet 请求最后一次被分发到的 Servlet 上下文
     * @since Servlet 3.0
     */
    @Override
    public ServletContext getServletContext() {
        return request.getServletContext();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 {@link ServletRequest#startAsync} 方法。
     *
     * @return （重新）初始化的 AsyncContext
     * @throws IllegalStateException 如果请求位于不支持异步操作的过滤器或 Servlet 范围内（即 {@link #isAsyncSupported} 返回 false），
     *                               或在没有任何异步分发（由某个 {@link AsyncContext#dispatch} 方法引发）的情况下
     *                               再次调用此方法，在任何此类分发的范围之外调用，或在同一分发范围内再次调用，或响应已关闭
     *
     * @see ServletRequest#startAsync
     * @since Servlet 3.0
     */
    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return request.startAsync();
    }

    /**
     * 此方法的默认行为是调用被包装请求对象上的 {@link ServletRequest#startAsync(ServletRequest, ServletResponse)} 方法。
     *
     * @param servletRequest  用于初始化 AsyncContext 的 ServletRequest
     * @param servletResponse 用于初始化 AsyncContext 的 ServletResponse
     * @return （重新）初始化的 AsyncContext
     * @throws IllegalStateException 如果请求位于不支持异步操作的过滤器或 Servlet 范围内（即 {@link #isAsyncSupported} 返回 false），
     *                               或在没有任何异步分发（由某个 {@link AsyncContext#dispatch} 方法引发）的情况下
     *                               再次调用此方法，在任何此类分发的范围之外调用，或在同一分发范围内再次调用，或响应已关闭
     * @see ServletRequest#startAsync(ServletRequest, ServletResponse)
     * @since Servlet 3.0
     */
    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IllegalStateException {
        return request.startAsync(servletRequest, servletResponse);
    }

    /**
     * 检查被包装的请求是否已处于异步模式。
     *
     * @return 如果此请求已处于异步模式则返回 true，否则返回 false
     * @see ServletRequest#isAsyncStarted
     * @since Servlet 3.0
     */
    @Override
    public boolean isAsyncStarted() {
        return request.isAsyncStarted();
    }

    /**
     * 检查被包装的请求是否支持异步操作。
     *
     * @return 如果此请求支持异步操作则返回 true，否则返回 false
     * @see ServletRequest#isAsyncSupported
     * @since Servlet 3.0
     */
    @Override
    public boolean isAsyncSupported() {
        return request.isAsyncSupported();
    }

    /**
     * 获取通过最近调用被包装请求上的 {@link #startAsync()} 或 {@link #startAsync(ServletRequest,ServletResponse)} 方法
     * 创建或重新初始化的 AsyncContext。
     *
     * @return 通过最近调用被包装请求上的 {@link #startAsync()} 或 {@link #startAsync(ServletRequest,ServletResponse)} 方法
     *         创建或重新初始化的 AsyncContext
     * @throws IllegalStateException 如果此请求未处于异步模式，即既未调用{@link #startAsync}
     *                               也未调用 {@link #startAsync(ServletRequest,ServletResponse)}
     * @see ServletRequest#getAsyncContext
     * @since Servlet 3.0
     */
    @Override
    public AsyncContext getAsyncContext() {
        return request.getAsyncContext();
    }

    /**
     * （递归地）检查此 ServletRequestWrapper 是否包装了指定的 {@link ServletRequest} 实例。
     *
     * @param wrapped 要搜索的 ServletRequest 实例
     * @return 如果此 ServletRequestWrapper 包装了给定的 ServletRequest 实例则返回 true，否则返回 false
     * @since Servlet 3.0
     */
    public boolean isWrapperFor(ServletRequest wrapped) {
        if (request == wrapped) {
            return true;
        } else if (request instanceof ServletRequestWrapper) {
            return ((ServletRequestWrapper) request).isWrapperFor(wrapped);
        } else {
            return false;
        }
    }

    /**
     * （递归地）检查此ServletRequestWrapper是否包装了指定类类型的{@link ServletRequest}。
     *
     * @param wrappedType 要搜索的ServletRequest类类型
     * @return 如果此ServletRequestWrapper包装了给定类类型的ServletRequest则返回true，否则返回false
     * @throws IllegalArgumentException 如果给定类未实现{@link ServletRequest}
     * @since Servlet 3.0
     */
    public boolean isWrapperFor(Class<?> wrappedType) {
        if (!ServletRequest.class.isAssignableFrom(wrappedType)) {
            throw new IllegalArgumentException("Given class " + wrappedType.getName() + " not a subinterface of "
                    + ServletRequest.class.getName());
        }
        if (wrappedType.isAssignableFrom(request.getClass())) {
            return true;
        } else if (request instanceof ServletRequestWrapper) {
            return ((ServletRequestWrapper) request).isWrapperFor(wrappedType);
        } else {
            return false;
        }
    }

    /**
     * 获取被包装请求的调度器类型。
     *
     * @return 被包装请求的调度器类型
     * @see ServletRequest#getDispatcherType
     * @since Servlet 3.0
     */
    @Override
    public DispatcherType getDispatcherType() {
        return request.getDispatcherType();
    }

}
