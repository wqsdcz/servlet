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
 * 定义了所有 Servlet 必须实现的方法。
 *
 * <p>
 *     Servlet 是在 Web 服务器中运行的小型 Java 程序。
 *     Servlet 接收并响应来自 Web 客户端的请求，通常通过超文本传输协议（HTTP）进行通信。
 *
 * <p>
 *     要实现此接口，您可以编写扩展 <code>javax.servlet.GenericServlet</code> 的通用 Servlet，
 *     或扩展 <code>javax.servlet.http.HttpServlet</code> 的 HTTP Servlet。
 *
 * <p>
 *     此接口定义了初始化 Servlet、处理请求以及从服务器移除 Servlet 的方法。这些方法称为生命周期方法，按以下顺序调用：
 *     <ol>
 *         <li>构建 Servlet，然后使用 <code>init</code> 方法进行初始化。
 *         <li>处理客户端对 <code>service</code> 方法的所有调用。
 *         <li>Servlet 被停用，随后通过 <code>destroy</code> 方法销毁，最后进行垃圾回收和终结。
 *     </ol>
 *
 * <p>
 *     除了生命周期方法外，此接口还提供 <code>getServletConfig</code> 方法（Servlet 可用其获取启动信息）
 *     和 <code>getServletInfo</code> 方法（允许 Servlet 返回有关自身的基本信息，如作者、版本和版权）。
 *
 * @author 众多作者
 * @see GenericServlet
 * @see javax.servlet.http.HttpServlet
 *
 */
public interface Servlet {

    /**
     * 由 Servlet 容器调用，以通知 Servlet 它正在被投入使用。
     *
     * <p>
     *     Servlet 容器在实例化 Servlet 后，会精确调用一次 <code>init</code> 方法。
     *     在 Servlet 接收处理请求之前，<code>init</code> 方法必须成功完成。
     *
     * <p>
     *     如果 <code>init</code> 方法出现以下情况，Servlet 容器无法将该 Servlet 投入使用：
     *     <ol>
     *         <li>抛出 <code>ServletException</code> 异常
     *         <li>未在 Web 服务器定义的时间段内返回
     *     </ol>
     *
     * @param config 包含 Servlet 配置和初始化参数的 <code>ServletConfig</code> 对象
     * @exception ServletException 如果发生了影响 Servlet 正常操作的异常
     * @see UnavailableException
     * @see #getServletConfig
     */
    public void init(ServletConfig config) throws ServletException;

    /**
     * 返回一个包含该servlet初始化和启动参数的 {@link ServletConfig} 对象。
     * 返回的 <code>ServletConfig</code> 对象即是传递给 <code>init</code> 方法的对象。
     *
     * <p>
     *     此接口的实现负责存储 <code>ServletConfig</code> 对象，以便此方法能够返回它。
     *     实现此接口的 {@link GenericServlet} 类已经完成了此操作。
     *
     * @return 初始化此servlet的 <code>ServletConfig</code> 对象
     * @see #init
     */
    public ServletConfig getServletConfig();

    /**
     * 由 Servlet 容器调用，以允许 servlet 响应请求。
     *
     * <p>此方法仅在该 servlet 的 <code>init()</code> 方法成功完成后才会调用。
     *
     * <p>对于抛出或发送错误的 servlet，应始终设置响应的状态码。
     *
     * <p>
     *     Servlet 通常运行在多线程的 Servlet 容器中，该容器可以同时处理多个请求。
     *     开发人员必须注意同步访问任何共享资源，如：文件、网络连接以及 servlet 的类和实例变量。
     *
     * @param req 包含客户端请求的 <code>ServletRequest</code> 对象
     * @param res 包含 servlet 响应的 <code>ServletResponse</code> 对象
     * @exception ServletException 如果发生影响 servlet 正常操作的异常
     * @exception IOException      如果发生输入或输出异常
     */
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException;

    /**
     * 返回有关 servlet 的信息，例如作者、版本和版权。
     *
     * <p>此方法返回的字符串应为纯文本，不得包含任何标记（如 HTML、XML 等）。
     *
     * @return 包含 servlet 信息的 <code>String</code>
     */
    public String getServletInfo();

    /**
     * 由 Servlet 容器调用，以通知 Servlet 它正在被停用。
     * 只有在 Servlet 的 <code>service</code> 方法中的所有线程都已退出或超时时间过后，才会调用此方法。
     * Servlet 容器调用此方法后，将不会再对此 Servlet 调用 <code>service</code> 方法。
     *
     * <p>
     *     此方法为 Servlet 提供了清理占用的任何资源（例如，内存、文件句柄、线程）的机会，
     *     并确保任何持久状态与 Servlet 在内存中的当前状态保持同步。
     *
     */
    public void destroy();
}
