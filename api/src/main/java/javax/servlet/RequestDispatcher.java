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

import java.io.IOException;

/**
 * 定义了一个从客户端接收请求并将其发送到服务器上任何资源（如 Servlet、HTML 文件或 JSP 文件）的对象。
 * Servlet 容器创建 <code>RequestDispatcher</code> 对象，该对象用作位于特定路径或由特定名称指定的服务器资源的包装器。
 *
 * <p>此接口主要用于包装 Servlet，但 Servlet 容器也可以创建 <code>RequestDispatcher</code> 对象来包装任何类型的资源。
 *
 * @author Various
 *
 * @see ServletContext#getRequestDispatcher(java.lang.String)
 * @see ServletContext#getNamedDispatcher(java.lang.String)
 * @see ServletRequest#getRequestDispatcher(java.lang.String)
 */
public interface RequestDispatcher {

    /**
     * 请求属性的名称，通过该属性可将原始请求URI提供给
     * {@link #forward(ServletRequest,ServletResponse)} 操作的目标对象
     */
    static final String FORWARD_REQUEST_URI = "javax.servlet.forward.request_uri";

    /**
     * 请求属性的名称，通过该属性可将原始上下文路径提供给
     * {@link #forward(ServletRequest,ServletResponse)} 操作的目标对象
     */
    static final String FORWARD_CONTEXT_PATH = "javax.servlet.forward.context_path";

    /**
     * 请求属性的名称，通过该属性可将原始 {@link javax.servlet.http.HttpServletMapping} 对象提供给
     * {@link #forward(ServletRequest,ServletResponse)} 操作的目标对象
     *
     * @since 4.0
     */
    static final String FORWARD_MAPPING = "javax.servlet.forward.mapping";

    /**
     * 请求属性的名称，通过该属性可将原始路径信息提供给
     * {@link #forward(ServletRequest,ServletResponse)} 操作的目标对象
     */
    static final String FORWARD_PATH_INFO = "javax.servlet.forward.path_info";

    /**
     * 请求属性的名称，通过该属性可将原始servlet路径提供给
     * {@link #forward(ServletRequest,ServletResponse)} 操作的目标对象
     */
    static final String FORWARD_SERVLET_PATH = "javax.servlet.forward.servlet_path";

    /**
     * 请求属性的名称，通过该属性可将原始查询字符串提供给
     * {@link #forward(ServletRequest,ServletResponse)} 操作的目标对象
     */
    static final String FORWARD_QUERY_STRING = "javax.servlet.forward.query_string";

    /**
     * 请求属性的名称，通过该属性存储 {@link #include(ServletRequest,ServletResponse)} 操作目标的请求URI
     */
    static final String INCLUDE_REQUEST_URI = "javax.servlet.include.request_uri";

    /**
     * 请求属性的名称，通过该属性存储 {@link #include(ServletRequest,ServletResponse)} 操作目标的上下文路径
     */
    static final String INCLUDE_CONTEXT_PATH = "javax.servlet.include.context_path";

    /**
     * 请求属性的名称，通过该属性存储 {@link #include(ServletRequest,ServletResponse)} 操作目标的路径信息
     */
    static final String INCLUDE_PATH_INFO = "javax.servlet.include.path_info";

    /**
     * 请求属性的名称，通过该属性存储 {@link #include(ServletRequest,ServletResponse)} 操作目标的
     * {@link javax.servlet.http.HttpServletMapping} 对象
     */
    static final String INCLUDE_MAPPING = "javax.servlet.include.mapping";

    /**
     * 请求属性的名称，通过该属性存储 {@link #include(ServletRequest,ServletResponse)} 操作目标的servlet路径
     */
    static final String INCLUDE_SERVLET_PATH = "javax.servlet.include.servlet_path";

    /**
     * 请求属性的名称，通过该属性存储 {@link #include(ServletRequest,ServletResponse)} 操作目标的查询字符串
     */
    static final String INCLUDE_QUERY_STRING = "javax.servlet.include.query_string";

    /**
     * 请求属性的名称，在错误分发期间通过该属性传播异常对象
     */
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";

    /**
     * 请求属性的名称，在错误分发期间通过该属性传播异常对象的类型
     */
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";

    /**
     * 请求属性的名称，在错误分发期间通过该属性传播异常消息
     */
    public static final String ERROR_MESSAGE = "javax.servlet.error.message";

    /**
     * 请求属性的名称，在错误分发期间通过该属性传播引发错误的请求URI
     */
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";

    /**
     * 请求属性的名称，在错误分发期间通过该属性传播发生错误的servlet名称
     */
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";

    /**
     * 请求属性的名称，在错误分发期间通过该属性传播响应状态
     */
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    /**
     * 将请求从一个 servlet 转发到服务器上的另一个资源（servlet、JSP 文件或 HTML 文件）。此方法允许一个 servlet
     * 对请求进行预处理，而由另一个资源生成响应。
     *
     * <p>
     * 对于通过 <code>getRequestDispatcher()</code> 获取的 <code>RequestDispatcher</code>，
     * <code>ServletRequest</code> 对象的路径元素和参数会被调整以匹配目标资源的路径。
     *
     * <p>
     * <code>forward</code> 应在响应提交给客户端之前（响应体输出刷新之前）调用。如果响应已提交，
     * 此方法将抛出 <code>IllegalStateException</code>。转发前，响应缓冲区中未提交的输出将自动清除。
     *
     * <p>
     * 请求和响应参数必须是传递给调用 servlet 的 service 方法的相同对象，或者是包装这些对象的
     * {@link ServletRequestWrapper} 或 {@link ServletResponseWrapper} 类的子类。
     *
     * <p>
     * 此方法将给定请求的调度器类型设置为 <code>DispatcherType.FORWARD</code>。
     *
     * @param request  代表客户端向 servlet 发出请求的 {@link ServletRequest} 对象
     * @param response 代表 servlet 返回给客户端响应的 {@link ServletResponse} 对象
     *
     * @throws ServletException      如果目标资源抛出此异常
     * @throws IOException           如果目标资源抛出此异常
     * @throws IllegalStateException 如果响应已提交
     *
     * @see ServletRequest#getDispatcherType
     */
    public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException;

    /**
     * 在响应中包含资源（Servlet、JSP页面、HTML文件）的内容。本质上，此方法实现了程序化的服务器端包含。
     *
     * <p>{@link ServletResponse} 对象的路径元素和参数保持与调用者相同。被包含的servlet无法更改响应状态码或设置响应头；任何更改尝试都将被忽略。
     *
     * <p>
     * 请求和响应参数必须是传递给调用servlet的service方法的相同对象，或者是包装这些对象的
     * {@link ServletRequestWrapper} 或 {@link ServletResponseWrapper} 类的子类。
     *
     * <p>此方法将给定请求的调度器类型设置为 <code>DispatcherType.INCLUDE</code>。
     *
     * @param request  包含客户端请求的 {@link ServletRequest} 对象
     * @param response 包含servlet响应的 {@link ServletResponse} 对象
     *
     * @throws ServletException 如果包含的资源抛出此异常
     * @throws IOException      如果包含的资源抛出此异常
     *
     * @see ServletRequest#getDispatcherType
     */
    public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException;
}
