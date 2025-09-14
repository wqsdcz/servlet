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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;
import javax.servlet.descriptor.JspConfigDescriptor;

/**
 * 定义了一组方法，这些方法由 Servlet 用于与其 Servlet 容器进行通信，例如：获取文件的 MIME 类型、处理请求 或 写入日志文件。
 *
 * <p>
 *     每个 Java 虚拟机中的每个"Web 应用程序"都拥有一个独立的上下文。（"Web 应用程序"是指安装在服务器 URL 命名空间特定子集
 *     （如 <code>/catalog</code>）下的一组 Servlet 和内容，可能通过 <code>.war</code> 文件安装。）
 *
 * <p>
 *     对于在其部署描述符中标记为"分布式"的 Web 应用程序，每个虚拟机都将有一个上下文实例。
 *     在这种情况下，上下文不能用作共享全局信息的位置（因为信息不会真正全局）。请改用数据库等外部资源。
 *
 * <p>
 *     <code>ServletContext</code> 对象被包含在 {@link ServletConfig} 对象中，
 *     Web 服务器在初始化 Servlet 时会向 Servlet 提供该对象。
 *
 * @author 众多作者
 *
 * @see Servlet#getServletConfig
 * @see ServletConfig#getServletContext
 */
public interface ServletContext {

    /**
     * <tt>ServletContext</tt> 属性的名称，该属性用于存储由 Servlet 容器为 <tt>ServletContext</tt> 提供的私有临时目录（类型为 <tt>java.io.File</tt> ）
     */
    public static final String TEMPDIR = "javax.servlet.context.tempdir";

    /**
     * 该 <code>ServletContext</code> 属性的名称，其值（类型为 <code>java.util.List&lt;java.lang.String&gt;</code>）
     * 包含 <code>WEB-INF/lib</code> 目录中 JAR 文件名称的列表，这些名称按其 Web 片段名称排序
     * （如果使用了没有任何 <code>&lt;others/&gt;</code> 的 <code>&lt;absolute-ordering&gt;</code>，则可能排除某些项目），
     * 如果未指定绝对或相对排序，则值为 null
     */
    public static final String ORDERED_LIBS = "javax.servlet.context.orderedLibs";

    /**
     * 返回 Web 应用程序的上下文路径。
     *
     * <p>
     *     上下文路径是请求 URI 中用于选择请求上下文的部分。上下文路径始终位于请求 URI 的首位。
     *     如果此上下文是基于 Web 服务器 URL 命名空间根目录的"根"上下文，则此路径将为空字符串。
     *     否则，如果上下文不是基于服务器命名空间的根目录，则路径以 / 字符开头但不以 / 字符结尾。
     *
     * <p>
     *     Servlet 容器可能会通过多个上下文路径匹配一个上下文。
     *     在这种情况下，{@link javax.servlet.http.HttpServletRequest#getContextPath()} 将返回请求实际使用的上下文路径，
     *     该路径可能与此方法返回的路径不同。此方法返回的上下文路径应视为应用程序的主上下文路径或首选上下文路径。
     *
     * @return Web 应用程序的上下文路径，对于根上下文返回 ""
     * @see javax.servlet.http.HttpServletRequest#getContextPath()
     * @since Servlet 2.5
     */
    public String getContextPath();

    /**
     * 返回与服务器上指定 URL 对应的 <code>ServletContext</code> 对象。
     *
     * <p>
     *     此方法允许 servlet 访问服务器不同部分的上下文，并根据需要从该上下文获取 {@link RequestDispatcher} 对象。
     *     给定路径必须以 <tt>/</tt> 开头，相对于服务器的文档根目录进行解析，并与在此容器上托管的其他 Web 应用程序的上下文根进行匹配。
     *
     * <p>
     *     在安全敏感的环境中，servlet 容器可能会针对给定 URL 返回 <code>null</code>。
     *
     * @param uripath 指定容器中其他 Web 应用程序的上下文路径的 <code>String</code>
     * @return 与指定 URL 对应的 <code>ServletContext</code> 对象，如果不存在对应上下文或容器希望限制此访问，则返回 null
     * @see RequestDispatcher
     */
    public ServletContext getContext(String uripath);

    /**
     * 返回此容器支持的 Jakarta Servlet 主版本号。
     * 所有符合版本 4.0 的实现必须使此方法返回整数 4。
     *
     * @return 4
     */
    public int getMajorVersion();

    /**
     * 返回此容器支持的 Jakarta Servlet 次版本号。
     * 所有符合版本 4.0 的实现必须使此方法返回整数 0。
     *
     * @return 0
     */
    public int getMinorVersion();

    /**
     * 获取此 ServletContext 所代表的应用程序所基于的 Servlet 规范的主版本号。
     *
     * <p>
     *     返回的值可能与 {@link #getMajorVersion} 不同，后者返回的是 Servlet 容器所支持的 Servlet 规范的主版本号。
     *
     * @return 此 ServletContext 所代表的应用程序所基于的 Servlet 规范的主版本号
     * @throws UnsupportedOperationException 如果此 ServletContext 被传递给了既未在 <code>web.xml</code> 或
     *                                       <code>web-fragment.xml</code> 中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener} 注解的
     *                                       {@link ServletContextListener} 的
     *                                       {@link ServletContextListener#contextInitialized} 方法
     *
     * @since Servlet 3.0
     */
    public int getEffectiveMajorVersion();

    /**
     * 获取此 ServletContext 所代表的应用程序所基于的 Servlet 规范的次版本号。
     *
     * <p>
     *     返回的值可能与 {@link #getMinorVersion} 不同，后者返回的是 Servlet 容器所支持的 Servlet 规范的次版本号。
     *
     * @return 此 ServletContext 所代表的应用程序所基于的 Servlet 规范的次版本号
     * @throws UnsupportedOperationException 如果此 ServletContext 被传递给了既未在 <code>web.xml</code> 或
     *                                       <code>web-fragment.xml</code> 中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener} 注解的
     *                                       {@link ServletContextListener} 的
     *                                       {@link ServletContextListener#contextInitialized} 方法
     *
     * @since Servlet 3.0
     */
    public int getEffectiveMinorVersion();

    /**
     * 返回指定文件的 MIME 类型，如果 MIME 类型未知则返回 <code>null</code>。
     * MIME 类型由 servlet 容器的配置决定，并且可以在 Web 应用程序部署描述符中指定。
     * 常见的 MIME 类型包括 <code>text/html</code> 和 <code>image/gif</code>。
     *
     * @param file 指定文件名称的 <code>String</code>
     * @return 指定文件 MIME 类型的 <code>String</code>
     */
    public String getMimeType(String file);

    /**
     * 返回 Web 应用程序内所有资源路径的目录式列表，这些资源路径的最长子路径与提供的路径参数匹配。
     *
     * <p>
     *     表示子目录的路径以 <tt>/</tt> 结尾。
     *
     * <p>
     *     返回的路径全部相对于 Web 应用程序的根目录，或者相对于 Web 应用程序 <tt>/WEB-INF/lib</tt> 目录中
     *     JAR 文件内的 <tt>/META-INF/resources</tt> 目录，并且以 <tt>/</tt> 开头。
     *
     * <p>
     *     返回的集合不受 {@code ServletContext} 对象支持，因此返回集合中的更改不会反映在 {@code ServletContext} 对象中，反之亦然。
     * </p>
     *
     * <p>
     *     例如，对于包含以下内容的 Web 应用程序：
     *
     * <pre>
     * {@code
     *   /welcome.html
     *   /catalog/index.html
     *   /catalog/products.html
     *   /catalog/offers/books.html
     *   /catalog/offers/music.html
     *   /customer/login.jsp
     *   /WEB-INF/web.xml
     *   /WEB-INF/classes/com.acme.OrderServlet.class
     *   /WEB-INF/lib/catalog.jar!/META-INF/resources/catalog/moreOffers/books.html
     * }
     * </pre>
     *
     * <tt>getResourcePaths("/")</tt> 将返回 <tt>{"/welcome.html", "/catalog/", "/customer/", "/WEB-INF/"}</tt>，
     * 而 <tt>getResourcePaths("/catalog/")</tt> 将返回 <tt>{"/catalog/index.html", "/catalog/products.html",
     * "/catalog/offers/", "/catalog/moreOffers/"}</tt>。
     *
     * @param path 用于匹配资源的局部路径，必须以 <tt>/</tt> 开头
     * @return 包含目录列表的 Set，如果 Web 应用程序中没有路径以提供路径开头的资源，则返回 null
     * @since Servlet 2.3
     */
    public Set<String> getResourcePaths(String path);

    /**
     * 返回映射到指定路径的资源的 URL。
     *
     * <p>
     *     路径必须以 <tt>/</tt> 开头，并被解释为相对于当前上下文根目录，
     *     或者相对于 Web 应用程序<tt>/WEB-INF/lib</tt> 目录中 JAR 文件内的 <tt>/META-INF/resources</tt> 目录。
     *     此方法将首先在 Web 应用程序的文档根目录中搜索请求的资源，然后再搜索 <tt>/WEB-INF/lib</tt> 中的任何 JAR 文件。
     *     搜索 <tt>/WEB-INF/lib</tt> 中 JAR 文件的顺序未定义。
     *
     * <p>
     *     此方法允许 servlet 容器从任何来源向 servlet 提供资源。
     *     资源可以位于本地或远程文件系统、数据库或 <code>.war</code> 文件中。
     *
     * <p>
     *     Servlet 容器必须实现访问资源所需的 URL 处理程序和 <code>URLConnection</code> 对象。
     *
     * <p>
     *     如果没有资源映射到该路径，则此方法返回 <code>null</code>。
     *
     * <p>
     *     某些容器可能允许使用 URL 类的方法写入此方法返回的 URL。
     *
     * <p>
     *     资源内容直接返回，因此请注意请求 <code>.jsp</code> 页面将返回 JSP 源代码。
     *     请改用 <code>RequestDispatcher</code> 来包含执行结果。
     *
     * <p>
     *     此方法的用途不同于 <code>java.lang.Class.getResource</code>，后者基于类加载器查找资源。
     *     此方法不使用类加载器。
     *
     * <p>
     *     此方法绕过隐式（不能直接访问 WEB-INF 或 META-INF）和显式（由 Web 应用程序定义）的安全约束。
     *     在构建路径时（例如，避免使用未净化的用户提供数据）和使用结果时都应小心，以免在应用程序中造成安全漏洞。
     *
     * @param path 指定资源路径的 <code>String</code>
     * @return 位于指定路径的资源，如果该路径没有资源则返回 <code>null</code>
     * @exception MalformedURLException 如果路径名的格式不正确
     */
    public URL getResource(String path) throws MalformedURLException;

    /**
     * 将指定路径的资源作为 <code>InputStream</code> 对象返回。
     *
     * <p>
     *     <code>InputStream</code> 中的数据可以是任何类型或长度。
     *     路径必须按照 <code>getResource</code> 方法给定的规则指定。
     *     如果指定路径不存在资源，则此方法返回 <code>null</code>。
     *
     * <p>
     *     通过 <code>getResource</code> 方法可用的元信息（如内容长度和内容类型）在使用此方法时会丢失。
     *
     * <p>
     *     Servlet 容器必须实现访问资源所需的 URL 处理程序和 <code>URLConnection</code> 对象。
     *
     * <p>
     *     此方法与 <code>java.lang.Class.getResourceAsStream</code> 不同，后者使用类加载器。
     *     此方法允许 servlet 容器从任何位置向 servlet 提供资源，而无需使用类加载器。
     *
     * <p>
     *     此方法绕过隐式（不能直接访问 WEB-INF 或 META-INF）和显式（由 Web 应用程序定义）的安全约束。
     *     在构建路径时（例如，避免使用未净化的用户提供数据）和使用结果时都应小心，以免在应用程序中造成安全漏洞。
     *
     * @param path 指定资源路径的 <code>String</code>
     *
     * @return 返回给 servlet 的 <code>InputStream</code>，如果指定路径不存在资源则返回 <code>null</code>
     */
    public InputStream getResourceAsStream(String path);

    /**
     * 返回一个 {@link RequestDispatcher} 对象，该对象充当指定 Servlet 的包装器。
     * <code>RequestDispatcher</code> 对象可用于将请求转发到资源或将资源包含在响应中。
     * 资源可以是动态的或静态的。
     *
     * <p>
     *     路径名必须以 <tt>/</tt> 开头，并被解释为相对于当前上下文根目录。
     *     使用 <code>getContext</code> 方法可获取外部上下文中资源的 <code>RequestDispatcher</code>。
     *
     * <p>
     *     如果 <code>ServletContext</code> 无法返回 <code>RequestDispatcher</code>，则此方法返回 <code>null</code>。
     *
     * @param path 指定资源路径名的 <code>String</code>
     * @return 作为指定路径资源包装器的 <code>RequestDispatcher</code> 对象，
     *         如果 <code>ServletContext</code> 无法返回 <code>RequestDispatcher</code>，则返回 <code>null</code>
     *
     * @see RequestDispatcher
     * @see ServletContext#getContext
     */
    public RequestDispatcher getRequestDispatcher(String path);

    /**
     * 返回一个 {@link RequestDispatcher} 对象，该对象充当指定 Servlet 的包装器。
     *
     * <p>
     *     Servlet（以及 JSP 页面）可以通过服务器管理或 Web 应用程序部署描述符来命名。
     *     Servlet 实例可以使用 {@link ServletConfig#getServletName} 方法确定其名称。
     *
     * <p>
     *     如果 <code>ServletContext</code> 因任何原因无法返回 <code>RequestDispatcher</code>，则此方法返回 <code>null</code>。
     *
     * @param name 指定要包装的 Servlet 名称的 <code>String</code>
     * @return 作为指定名称 Servlet 包装器的 <code>RequestDispatcher</code> 对象，
     *         如果 <code>ServletContext</code> 无法返回 <code>RequestDispatcher</code>，则返回 <code>null</code>
     * @see RequestDispatcher
     * @see ServletContext#getContext
     * @see ServletConfig#getServletName
     */
    public RequestDispatcher getNamedDispatcher(String name);

    /**
     * @deprecated 自 Java Servlet API 2.1 起弃用，无直接替代方案。
     *
     *             <p>
     *                 此方法最初设计用于从 <code>ServletContext</code> 中获取 servlet。
     *                 在此版本中，此方法始终返回 <code>null</code>，仅为保持二进制兼容性而保留。
     *                 此方法将在 Jakarta Servlets 的未来版本中永久移除。
     *
     *             <p>
     *                 替代此方法的是，servlet 可以使用 <code>ServletContext</code> 类共享信息，
     *                 并通过调用普通非 servlet 类的方法来执行业务逻辑。
     *
     * @param name servlet 名称
     * @return 具有给定名称的 {@code javax.servlet.Servlet Servlet}
     * @throws ServletException 如果发生了影响 servlet 正常操作的异常
     */
    @Deprecated
    public Servlet getServlet(String name) throws ServletException;

    /**
     * @deprecated 自 Java Servlet API 2.0 起弃用，无替代方案。
     *
     *             <p>
     *                 此方法最初设计用于返回此 servlet 上下文已知的所有 servlet 的 <code>Enumeration</code>。
     *                 在此版本中，此方法始终返回空枚举，仅为保持二进制兼容性而保留。
     *                 此方法将在 Jakarta Servlets 的未来版本中永久移除。
     *
     * @return {@code javax.servlet.Servlet Servlet} 的 <code>Enumeration</code>
     */
    @Deprecated
    public Enumeration<Servlet> getServlets();

    /**
     * @deprecated 自 Java Servlet API 2.1 起弃用，无替代方案。
     *
     *             <p>
     *                 此方法最初设计用于返回此上下文已知的所有 servlet 名称的 <code>Enumeration</code>。
     *                 在此版本中，此方法始终返回空的 <code>Enumeration</code>，仅为保持二进制兼容性而保留。
     *                 此方法将在 Jakarta Servlets 的未来版本中永久移除。
     *
     * @return {@code javax.servlet.Servlet Servlet} 名称的 <code>Enumeration</code>
     */
    @Deprecated
    public Enumeration<String> getServletNames();

    /**
     * 将指定消息写入 servlet 日志文件（通常是事件日志）。servlet 日志文件的名称和类型特定于 servlet 容器。
     *
     * @param msg 要写入日志文件的消息字符串
     */
    public void log(String msg);

    /**
     * @deprecated 自 Java Servlet API 2.1 起弃用，请使用 {@link #log(String message, Throwable throwable)} 替代。
     *
     *             <p>
     *                 此方法最初设计用于将异常堆栈跟踪和说明性错误消息写入 servlet 日志文件。
     *
     * @param exception 异常错误对象
     * @param msg       描述异常的字符串
     */
    @Deprecated
    public void log(Exception exception, String msg);

    /**
     * 将说明性消息和给定 <code>Throwable</code> 异常的堆栈跟踪写入 servlet 日志文件。
     * servlet 日志文件的名称和类型特定于 servlet 容器，通常为事件日志。
     *
     * @param message   描述错误或异常的字符串
     * @param throwable 要记录的 <code>Throwable</code> 错误或异常
     */
    public void log(String message, Throwable throwable);

    /**
     * 获取与给定<i>虚拟</i>路径对应的<i>实际</i>路径。
     *
     * <p>
     *     例如，如果 <tt>path</tt> 等于 <tt>/index.html</tt>，则此方法将返回服务器文件系统上的绝对文件路径，
     *     该路径将映射到形式为 <tt>http://&lt;host&gt;:&lt;port&gt;/&lt;contextPath&gt;/index.html</tt> 的请求，
     *     其中 <tt>&lt;contextPath&gt;</tt> 对应此 ServletContext 的上下文路径。
     *
     * <p>
     *     返回的实际路径将采用适合于运行 servlet 容器的计算机和操作系统的形式，包括正确的路径分隔符。
     *
     * <p>
     *     只有当容器已从包含的 JAR 文件中解包时，才必须考虑应用程序 <tt>/WEB-INF/lib</tt> 目录中 JAR 文件
     *     <tt>/META-INF/resources</tt> 目录内的资源，这种情况下必须返回解包位置的路径。
     *
     * <p>
     *     如果 servlet 容器无法将给定的<i>虚拟</i>路径转换为<i>实际</i>路径，则此方法返回 <code>null</code>。
     *
     * @param path 要转换为<i>实际</i>路径的<i>虚拟</i>路径
     * @return <i>实际</i>路径，如果无法执行转换则返回 <tt>null</tt>
     */
    public String getRealPath(String path);

    /**
     * 返回运行该 servlet 的 servlet 容器的名称和版本。
     *
     * <p>
     *     返回字符串的格式为 <i>服务器名称</i>/<i>版本号</i>。
     *     例如，JavaServer Web 开发工具包可能返回字符串 <code>JavaServer Web Dev Kit/1.0</code>。
     *
     * <p>
     *     servlet 容器可以在主字符串后的括号内返回其他可选信息，例如：
     *     <code>JavaServer Web Dev Kit/1.0 (JDK 1.1.6; Windows NT 4.0 x86)</code>。
     *
     * @return 包含至少 servlet 容器名称和版本号的 <code>String</code>
     */
    public String getServerInfo();

    /**
     * 返回包含指定上下文范围初始化参数值的 <code>String</code>，如果该参数不存在，则返回 <code>null</code>。
     *
     * <p>
     *     此方法可以提供对整个 Web 应用程序有用的配置信息。
     *     例如，它可以提供网站管理员的电子邮件地址或保存关键数据的系统名称。
     *
     * @param name 包含要获取值的参数名称的 <code>String</code>
     * @return 包含上下文初始化参数值的 <code>String</code>，如果该初始化参数不存在，则返回 <code>null</code>
     * @throws NullPointerException 如果参数 {@code name} 为 {@code null}
     * @see ServletConfig#getInitParameter
     */
    public String getInitParameter(String name);

    /**
     * 返回该 context 的初始化参数的名称，其形式为一个由 <code>String</code> 对象组成的 <code>Enumeration</code> 对象；
     * 如果 context 没有初始化参数，则返回空的<code>Enumeration</code>。
     *
     * @return 包含上下文初始化参数名称的<code>String</code>对象的<code>Enumeration</code>
     * @see ServletConfig#getInitParameter
     */
    public Enumeration<String> getInitParameterNames();

    /**
     * 在此 ServletContext 上设置具有指定名称和值的上下文初始化参数。
     *
     * @param name  要设置的上下文初始化参数名称
     * @param value 要设置的上下文初始化参数值
     * @return 如果在此 ServletContext 上成功设置了具有指定名称和值的上下文初始化参数，则返回 true；
     *         如果未设置，则是因为此 ServletContext 已包含具有匹配名称的上下文初始化参数，此时返回 false
     * @throws IllegalStateException         如果此 ServletContext 已被初始化
     * @throws NullPointerException          如果 name 参数为 {@code null}
     * @throws UnsupportedOperationException 如果此 ServletContext 被传递给了既未在 <code>web.xml</code> 或
     *                                       <code>web-fragment.xml</code> 中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener} 注解的
     *                                       {@link ServletContextListener} 的
     *                                       {@link ServletContextListener#contextInitialized} 方法
     * @since Servlet 3.0
     */
    public boolean setInitParameter(String name, String value);

    /**
     * 返回具有指定名称的 servlet 容器属性，如果不存在该名称的属性，则返回 <code>null</code>。
     *
     * <p>
     *     属性允许 servlet 容器向 servlet 提供此接口未提供的附加信息。
     *     有关其属性的信息，请参阅服务器文档。
     *     可以使用 <code>getAttributeNames</code> 方法检索支持的属性列表。
     *
     * <p>
     *     属性以 <code>java.lang.Object</code> 或其某个子类的形式返回。
     *
     * <p>
     *     属性名称应遵循与包名称相同的约定。
     *     Jakarta Servlet 规范保留与 <code>java.*</code>、<code>javax.*</code> 和 <code>sun.*</code> 匹配的名称。
     *
     * @param name 指定属性名称的 <code>String</code>
     * @return 包含属性值的 <code>Object</code>，如果没有与给定名称匹配的属性，则返回 <code>null</code>
     * @see ServletContext#getAttributeNames
     * @throws NullPointerException 如果参数 {@code name} 为 {@code null}
     *
     */
    public Object getAttribute(String name);

    /**
     * 返回包含此 ServletContext 中可用属性名称的 <code>Enumeration</code>。
     *
     * <p>
     *     使用带有属性名称的 {@link #getAttribute} 方法可获取属性值。
     *
     * @return 属性名称的 <code>Enumeration</code>
     * @see #getAttribute
     */
    public Enumeration<String> getAttributeNames();

    /**
     * 将对象绑定到此 ServletContext 中的指定属性名称。
     * 如果指定名称已用于某个属性，此方法将使用新属性替换原有属性。
     * <p>
     *     如果在 <code>ServletContext</code> 上配置了监听器，容器会相应地通知它们。
     * <p>
     *     如果传入 null 值，效果等同于调用 <code>removeAttribute()</code>。
     *
     * <p>
     *     属性名称应遵循与包名称相同的约定。
     *     Jakarta Servlet 规范保留与 <code>java.*</code>、<code>javax.*</code> 和 <code>sun.*</code> 匹配的名称。
     *
     * @param name   指定属性名称的 <code>String</code>
     * @param object 表示要绑定的属性的 <code>Object</code>
     *
     * @throws NullPointerException 如果 name 参数为 {@code null}
     */
    public void setAttribute(String name, Object object);

    /**
     * 从此 ServletContext 中移除指定名称的属性。
     * 移除后，后续调用 {@link #getAttribute} 获取该属性值将返回 <code>null</code>。
     *
     * <p>
     *     如果在 <code>ServletContext</code> 上配置了监听器，容器会相应地通知它们。
     *
     * @param name 指定要移除的属性名称的 <code>String</code>
     */
    public void removeAttribute(String name);

    /**
     * 返回与此 ServletContext 对应的 Web 应用程序名称，该名称在部署描述符中通过 display-name 元素指定。
     *
     * @return Web 应用程序的名称，如果部署描述符中未声明名称，则返回 null
     * @since Servlet 2.3
     */
    public String getServletContextName();

    /**
     * 将具有指定名称和类名的 servlet 添加到此 servlet 上下文中。
     *
     * <p>
     *     可以通过返回的 {@link ServletRegistration} 对象对注册的 servlet 进行进一步配置。
     *
     * <p>
     *     将使用与此 ServletContext 所代表的应用程序关联的类加载器加载指定的 <tt>className</tt>。
     *
     * <p>
     *     如果此 ServletContext 已包含具有给定 <tt>servletName</tt> 的 servlet 的初步 ServletRegistration，
     *     则将通过为其分配给定的 <tt>className</tt> 来完成该注册并返回。
     *
     * <p>
     *     此方法会对具有给定 <tt>className</tt> 的类进行内省，检查{@link javax.servlet.annotation.ServletSecurity}、
     *     {@link javax.servlet.annotation.MultipartConfig}、<tt>javax.annotation.security.RunAs</tt>
     *     和 <tt>javax.annotation.security.DeclareRoles</tt> 注解。
     *     此外，如果具有给定 <tt>className</tt> 的类表示托管 Bean，则此方法支持资源注入。
     *     有关托管 Bean 和资源注入的其他详细信息，请参阅 Jakarta EE 平台和 CDI 规范。
     *
     * @param servletName servlet 的名称
     * @param className   servlet 的完全限定类名
     * @return 可用于进一步配置已注册 servlet 的 ServletRegistration 对象，如果此 ServletContext
     *         已包含具有给定 <tt>servletName</tt> 的完整 ServletRegistration，则返回 <tt>null</tt>
     * @throws IllegalStateException         如果此 ServletContext 已被初始化
     * @throws IllegalArgumentException      如果 <code>servletName</code> 为 null 或空字符串
     * @throws UnsupportedOperationException 如果此 ServletContext 被传递给了既未在 <code>web.xml</code> 或
     *                                       <code>web-fragment.xml</code> 中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener} 注解的
     *                                       {@link ServletContextListener} 的
     *                                       {@link ServletContextListener#contextInitialized} 方法
     *
     * @since Servlet 3.0
     */
    public ServletRegistration.Dynamic addServlet(String servletName, String className);

    /**
     * 使用给定的 <tt>servletName</tt> 将指定的 servlet 实例注册到此 ServletContext。
     *
     * <p>
     *     可以通过返回的 {@link ServletRegistration} 对象对注册的 servlet 进行进一步配置。
     *
     * <p>
     *     如果此 ServletContext 已包含具有给定 <tt>servletName</tt> 的 servlet 的初步 ServletRegistration，
     *     则将通过为其分配给定 servlet 实例的类名来完成该注册并返回。
     *
     * @param servletName servlet 的名称
     * @param servlet     要注册的 servlet 实例
     *
     * @return 可用于进一步配置给定 servlet 的 ServletRegistration 对象，如果此 ServletContext
     *         已包含具有给定 <tt>servletName</tt> 的完整 ServletRegistration，或者如果相同的 servlet 实例
     *         已在此容器中的此 ServletContext 或其他 ServletContext 中注册，则返回 <tt>null</tt>
     *
     * @throws IllegalStateException         如果此 ServletContext 已被初始化
     * @throws UnsupportedOperationException 如果此 ServletContext 被传递给了既未在 <code>web.xml</code> 或
     *                                       <code>web-fragment.xml</code> 中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener} 注解的
     *                                       {@link ServletContextListener} 的
     *                                       {@link ServletContextListener#contextInitialized} 方法
     * @throws IllegalArgumentException      如果给定的 servlet 实例实现了 {@link SingleThreadModel}，
     *                                       或者 <code>servletName</code> 为 null 或空字符串
     *
     * @since Servlet 3.0
     */
    public ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet);

    /**
     * 将具有指定名称和类类型的 servlet 添加到此 servlet 上下文中。
     *
     * <p>
     *     可以通过返回的 {@link ServletRegistration} 对象对注册的 servlet 进行进一步配置。
     *
     * <p>
     *     如果此 ServletContext 已包含具有给定 <tt>servletName</tt> 的 servlet 的初步 ServletRegistration，
     *     则将通过为其分配给定 <tt>servletClass</tt> 的名称来完成该注册并返回。
     *
     * <p>
     *     此方法会对给定的 <tt>servletClass</tt> 进行内省，检查 {@link javax.servlet.annotation.ServletSecurity}、
     *     {@link javax.servlet.annotation.MultipartConfig}、<tt>javax.annotation.security.RunAs</tt>
     *     和 <tt>javax.annotation.security.DeclareRoles</tt> 注解。
     *     此外，如果给定的 <tt>servletClass</tt> 表示托管 Bean，则此方法支持资源注入。
     *     有关托管 Bean 和资源注入的其他详细信息，请参阅 Jakarta EE 平台和 CDI 规范。
     *
     * @param servletName  servlet 的名称
     * @param servletClass 将从中实例化 servlet 的类对象
     *
     * @return 可用于进一步配置已注册 servlet 的 ServletRegistration 对象，如果此 ServletContext
     *         已包含具有给定 <tt>servletName</tt> 的完整 ServletRegistration，则返回 <tt>null</tt>
     *
     * @throws IllegalStateException         如果此 ServletContext 已被初始化
     * @throws IllegalArgumentException      如果 <code>servletName</code> 为 null 或空字符串
     * @throws UnsupportedOperationException 如果此 ServletContext 被传递给了既未在 <code>web.xml</code> 或
     *                                       <code>web-fragment.xml</code> 中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener} 注解的
     *                                       {@link ServletContextListener} 的
     *                                       {@link ServletContextListener#contextInitialized} 方法
     *
     * @since Servlet 3.0
     */
    public ServletRegistration.Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass);

    /**
     * 将具有指定 JSP 文件的 servlet 添加到此 servlet 上下文中。
     *
     * <p>
     *     可以通过返回的 {@link ServletRegistration} 对象对注册的 servlet 进行进一步配置。
     *
     * <p>
     *     如果此 ServletContext 已包含具有给定 <tt>servletName</tt> 的 servlet 的初步 ServletRegistration，
     *     则将通过为其分配给定的 <tt>jspFile</tt> 来完成该注册并返回。
     *
     * @param servletName servlet 的名称
     * @param jspFile     Web 应用程序中以 `/` 开头的 JSP 文件的完整路径
     *
     * @return 可用于进一步配置已注册 servlet 的 ServletRegistration 对象，如果此 ServletContext
     *         已包含具有给定 <tt>servletName</tt> 的完整 ServletRegistration，则返回 <tt>null</tt>
     *
     * @throws IllegalStateException         如果此 ServletContext 已被初始化
     * @throws IllegalArgumentException      如果 <code>servletName</code> 为 null 或空字符串
     * @throws UnsupportedOperationException 如果此 ServletContext 被传递给了既未在 <code>web.xml</code> 或
     *                                       <code>web-fragment.xml</code> 中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener} 注解的
     *                                       {@link ServletContextListener} 的
     *                                       {@link ServletContextListener#contextInitialized} 方法
     *
     * @since Servlet 4.0
     */
    public ServletRegistration.Dynamic addJspFile(String servletName, String jspFile);

    /**
     * 实例化指定的Servlet类。
     *
     * <p>
     *     返回的Servlet实例在通过调用{@link #addServlet(String,Servlet)}注册到ServletContext之前可进一步定制。
     *
     * <p>
     *     给定的Servlet类必须定义一个无参数构造函数，用于实例化该类。
     *
     * <p>
     *     此方法会内省给定的<tt>clazz</tt>类以检查以下注解：
     *     <ul>
     *         <li>{@link javax.servlet.annotation.ServletSecurity}</li>
     *         <li>{@link javax.servlet.annotation.MultipartConfig}</li>
     *         <li><tt>javax.annotation.security.RunAs</tt></li>
     *         <li><tt>javax.annotation.security.DeclareRoles</tt></li>
     *     </ul>
     *     此外，如果给定的<tt>clazz</tt>表示一个托管Bean(Managed Bean)，则此方法支持资源注入。
     *     有关托管Bean和资源注入的更多详细信息，请参阅Jakarta EE平台和CDI规范。
     *
     * @param       <T> 要创建的Servlet的类
     * @param clazz 要实例化的Servlet类
     * @return 新的Servlet实例
     * @throws ServletException              如果给定的<tt>clazz</tt>实例化失败
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 3.0
     */    public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException;

    /**
     * 获取与给定<tt>servletName</tt>对应的servlet注册信息。
     *
     * @param servletName servlet名称
     * @return 具有给定<tt>servletName</tt>的servlet的（完整或初步）ServletRegistration，
     *         如果该名称下不存在ServletRegistration则返回null
     * @throws UnsupportedOperationException 如果此ServletContext被传递给
     *                                       {@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在
     *                                       <code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       也未使用{@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 3.0
     */
    public ServletRegistration getServletRegistration(String servletName);

    /**
     * 获取与此ServletContext注册的所有servlet对应的（可能为空的）ServletRegistration对象Map（以servlet名称为键）。
     *
     * <p>
     *     返回的Map包括对应于所有声明和注解servlet的ServletRegistration对象，
     *     以及通过任一<tt>addServlet</tt>和<tt>addJspFile</tt>方法添加的所有servlet对应的ServletRegistration对象。
     *
     * <p>
     *     如果允许，对返回Map的任何更改不得影响此ServletContext。
     *
     * @return 对应于当前在此ServletContext注册的所有servlet的（完整和初步）ServletRegistration对象的Map
     * @throws UnsupportedOperationException 如果此ServletContext被传递给
     *                                       {@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在
     *                                       <code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       也未使用{@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 3.0
     */
    public Map<String, ? extends ServletRegistration> getServletRegistrations();

    /**
     * 向此servlet上下文添加具有给定名称和类名的过滤器。
     *
     * <p>
     *     已注册的过滤器可以通过返回的{@link FilterRegistration}对象进行进一步配置。
     *
     * <p>
     *     指定的<tt>className</tt>将使用与此ServletContext所代表的应用程序关联的类加载器进行加载。
     *
     * <p>
     *     如果此ServletContext已包含具有给定<tt>filterName</tt>的过滤器的初步FilterRegistration，
     *     则将通过将给定的<tt>className</tt>分配给它来完成注册并返回。
     *
     * <p>
     *     如果具有给定<tt>className</tt>的类代表一个托管Bean，则此方法支持资源注入。
     *     有关托管Bean和资源注入的更多详细信息，请参阅Jakarta EE平台和CDI规范。
     *
     * @param filterName 过滤器的名称
     * @param className  过滤器的完全限定类名
     * @return 可用于进一步配置已注册过滤器的FilterRegistration对象，如果此ServletContext
     *         已包含具有给定<tt>filterName</tt>的过滤器的完整FilterRegistration，则返回<tt>null</tt>
     * @throws IllegalStateException         如果此ServletContext已被初始化
     * @throws IllegalArgumentException      如果<code>filterName</code>为null或空字符串
     * @throws UnsupportedOperationException 如果此ServletContext被传递给
     *                                       {@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在
     *                                       <code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       也未使用{@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 3.0
     */
    public FilterRegistration.Dynamic addFilter(String filterName, String className);

    /**
     * 使用给定的<tt>filterName</tt>将指定的过滤器实例注册到此ServletContext。
     *
     * <p>
     *     注册的过滤器可以通过返回的{@link FilterRegistration}对象进行进一步配置。
     *
     * <p>
     *     如果此ServletContext已包含具有给定<tt>filterName</tt>的过滤器的初步FilterRegistration，
     *     则它将完成（通过将给定过滤器实例的类名分配给它）并返回。
     *
     * @param filterName 过滤器的名称
     * @param filter     要注册的过滤器实例
     * @return 可用于进一步配置给定过滤器的FilterRegistration对象，如果此ServletContext
     *         已包含具有给定<tt>filterName</tt>的过滤器的完整FilterRegistration，或者
     *         如果相同的过滤器实例已经注册到此容器中的此ServletContext或其他ServletContext，则返回<tt>null</tt>
     * @throws IllegalStateException         如果此ServletContext已被初始化
     * @throws IllegalArgumentException      如果<code>filterName</code>为null或空字符串
     * @throws UnsupportedOperationException 如果此ServletContext被传递给
     *                                       {@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在
     *                                       <code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       也未使用{@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 3.0
     */
    public FilterRegistration.Dynamic addFilter(String filterName, Filter filter);

    /**
     * 向此servlet上下文添加具有给定名称和类类型的过滤器。
     *
     * <p>
     *     注册的过滤器可以通过返回的{@link FilterRegistration}对象进行进一步配置。
     *
     * <p>
     *     如果此ServletContext已包含具有给定<tt>filterName</tt>的过滤器的初步FilterRegistration，
     *     则它将完成（通过将给定的<tt>filterClass</tt>名称分配给它）并返回。
     *
     * <p>
     *     如果给定的<tt>filterClass</tt>表示一个托管Bean，则此方法支持资源注入。
     *     有关托管Bean和资源注入的更多详细信息，请参阅Java EE平台和CDI规范。
     *
     * @param filterName  过滤器的名称
     * @param filterClass 将从中实例化过滤器的类对象
     * @return 可用于进一步配置已注册过滤器的FilterRegistration对象，如果此ServletContext
     *         已包含具有给定<tt>filterName</tt>的过滤器的完整FilterRegistration，则返回<tt>null</tt>
     * @throws IllegalStateException         如果此ServletContext已被初始化
     * @throws IllegalArgumentException      如果<code>filterName</code>为null或空字符串
     * @throws UnsupportedOperationException 如果此ServletContext被传递给
     *                                       {@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在
     *                                       <code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       也未使用{@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 3.0
     */
    public FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass);

    /**
     * 实例化指定的Filter类。
     *
     * <p>
     *     返回的Filter实例在通过调用{@link #addFilter(String,Filter)}注册到ServletContext之前可进一步定制。
     *
     * <p>
     *     给定的Filter类必须定义一个无参数构造函数，用于实例化该类。
     *
     * <p>
     *     如果给定的<tt>clazz</tt>表示一个托管Bean(Managed Bean)，则此方法支持资源注入。
     *     有关托管Bean和资源注入的更多详细信息，请参阅Jakarta EE平台和CDI规范。
     *
     * @param       <T> 要创建的Filter的类
     * @param clazz 要实例化的Filter类
     * @return 新的Filter实例
     * @throws ServletException              如果给定的<tt>clazz</tt>实例化失败
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 3.0
     */
    public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException;

    /**
     * 获取与给定<tt>filterName</tt>对应的过滤器注册信息。
     *
     * @param filterName 过滤器名称
     * @return 具有给定<tt>filterName</tt>的过滤器的（完整或初步）FilterRegistration，
     *         如果该名称下不存在FilterRegistration则返回null
     * @throws UnsupportedOperationException 如果此ServletContext被传递给
     *                                       {@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在
     *                                       <code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       也未使用{@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 3.0
     */
    public FilterRegistration getFilterRegistration(String filterName);

    /**
     * 获取与此ServletContext注册的所有过滤器对应的（可能为空的）FilterRegistration对象Map（以过滤器名称为键）。
     *
     * <p>
     *     返回的Map包括对应于所有声明和注解过滤器的FilterRegistration对象，
     *     以及通过任一<tt>addFilter</tt>方法添加的所有过滤器对应的FilterRegistration对象。
     *
     * <p>
     *     对返回Map的任何更改不得影响此ServletContext。
     *
     * @return 对应于当前在此ServletContext注册的所有过滤器的（完整和初步）FilterRegistration对象的Map
     * @throws UnsupportedOperationException 如果此ServletContext被传递给
     *                                       {@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在
     *                                       <code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       也未使用{@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 3.0
     */
    public Map<String, ? extends FilterRegistration> getFilterRegistrations();

    /**
     * 获取 {@link SessionCookieConfig} 对象，通过该对象可以配置代表此 <tt>ServletContext</tt> 创建的
     * 会话跟踪 Cookie 的各种属性。
     *
     * <p>
     *     重复调用此方法将返回相同的 <tt>SessionCookieConfig</tt> 实例。
     *
     * @return 用于配置代表此 <tt>ServletContext</tt> 创建的会话跟踪 Cookie 各种属性的<tt>SessionCookieConfig</tt> 对象
     * @throws UnsupportedOperationException 如果此 ServletContext 被传递给
     *                                       {@link ServletContextListener#contextInitialized} 方法，
     *                                       而该 {@link ServletContextListener} 既未在
     *                                       <code>web.xml</code> 或 <code>web-fragment.xml</code> 中声明，
     *                                       也未使用 {@link javax.servlet.annotation.WebListener} 注解标注
     * @since Servlet 3.0
     */
    public SessionCookieConfig getSessionCookieConfig();

    /**
     * 设置将对此<tt>ServletContext</tt>生效的会话跟踪模式。
     *
     * <p>
     *     给定的<tt>sessionTrackingModes</tt>将替换之前通过此方法在此<tt>ServletContext</tt>上设置的任何会话跟踪模式。
     *
     * @param sessionTrackingModes 将对此<tt>ServletContext</tt>生效的会话跟踪模式集合
     * @throws IllegalStateException         如果此ServletContext已被初始化
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @throws IllegalArgumentException      如果<tt>sessionTrackingModes</tt>指定了<tt>SessionTrackingMode.SSL</tt>与
     *                                       除<tt>SessionTrackingMode.SSL</tt>之外的会话跟踪模式的组合，或者如果
     *                                       <tt>sessionTrackingModes</tt>指定了servlet容器不支持的会话跟踪模式
     * @since Servlet 3.0
     */
    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes);

    /**
     * 获取此<tt>ServletContext</tt>默认支持的会话跟踪模式。
     *
     * <p>
     *     返回的集合不受{@code ServletContext}对象支持，因此返回集合中的更改不会反映在{@code ServletContext}对象中，反之亦然。
     *
     * @return 此<tt>ServletContext</tt>默认支持的会话跟踪模式集合
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 3.0
     */
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes();

    /**
     * 获取对此<tt>ServletContext</tt>生效的会话跟踪模式。
     *
     * <p>
     *     生效的会话跟踪模式是提供给{@link #setSessionTrackingModes setSessionTrackingModes}的那些模式。
     *
     * <p>
     *     返回的集合不受{@code ServletContext}对象支持，因此返回集合中的更改不会反映在{@code ServletContext}对象中，反之亦然。
     *
     * @return 此<tt>ServletContext</tt>生效的会话跟踪模式集合
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 3.0
     */
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes();

    /**
     * 将具有给定类名的监听器添加到此ServletContext。
     *
     * <p>
     *     具有给定名称的类将使用与此ServletContext所代表的应用程序关联的类加载器加载，
     *     并且必须实现以下一个或多个接口：
     *     <ul>
     *         <li>{@link ServletContextAttributeListener}
     *         <li>{@link ServletRequestListener}
     *         <li>{@link ServletRequestAttributeListener}
     *         <li>{@link javax.servlet.http.HttpSessionAttributeListener}
     *         <li>{@link javax.servlet.http.HttpSessionIdListener}
     *         <li>{@link javax.servlet.http.HttpSessionListener}
     *     </ul>
     *
     * <p>
     *     如果此ServletContext被传递给{@link ServletContainerInitializer#onStartup}，那么除了上面列出的接口外，
     *     具有给定名称的类还可以实现{@link ServletContextListener}。
     *
     * <p>
     *     作为此方法调用的一部分，容器必须加载具有指定类名的类，以确保它实现了所需的接口之一。
     *
     * <p>
     *     如果具有给定名称的类实现了调用顺序与声明顺序相对应的监听器接口
     *     （即实现了{@link ServletRequestListener}、{@link ServletContextListener}或
     *     {@link javax.servlet.http.HttpSessionListener}），则新监听器将被添加到该接口监听器有序列表的末尾。
     *
     * <p>
     *     如果具有给定<tt>className</tt>的类表示一个托管Bean，则此方法支持资源注入。
     *     有关托管Bean和资源注入的更多详细信息，请参阅Jakarta EE平台和CDI规范。
     *
     * @param className 监听器的完全限定类名
     * @throws IllegalArgumentException      如果具有给定名称的类未实现上述任何接口，
     *                                       或者它实现了{@link ServletContextListener}但此ServletContext
     *                                       未被传递给{@link ServletContainerInitializer#onStartup}
     * @throws IllegalStateException         如果此ServletContext已被初始化
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 3.0
     */
    public void addListener(String className);

    /**
     * 将给定的监听器添加到此ServletContext。
     *
     * <p>
     *     给定的监听器必须是以下一个或多个接口的实例：
     *     <ul>
     *          <li>{@link ServletContextAttributeListener}
     *          <li>{@link ServletRequestListener}
     *          <li>{@link ServletRequestAttributeListener}
     *          <li>{@link javax.servlet.http.HttpSessionAttributeListener}
     *          <li>{@link javax.servlet.http.HttpSessionIdListener}
     *          <li>{@link javax.servlet.http.HttpSessionListener}
     *     </ul>
     *
     * <p>
     *     如果此ServletContext被传递给{@link ServletContainerInitializer#onStartup}，那么除了上面列出的接口外，
     *     给定的监听器还可以是{@link ServletContextListener}的实例。
     *
     * <p>
     *     如果给定的监听器是调用顺序与声明顺序相对应的监听器接口的实例
     *     （即是{@link ServletRequestListener}、{@link ServletContextListener}或
     *     {@link javax.servlet.http.HttpSessionListener}的实例），则该监听器将被添加到该接口监听器有序列表的末尾。
     *
     * @param   <T> 要添加的EventListener的类
     * @param t 要添加的监听器
     * @throws IllegalArgumentException      如果给定的监听器不是上述任何接口的实例，
     *                                       或者是{@link ServletContextListener}的实例但此ServletContext
     *                                       未被传递给{@link ServletContainerInitializer#onStartup}
     * @throws IllegalStateException         如果此ServletContext已被初始化
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 3.0
     */
    public <T extends EventListener> void addListener(T t);

    /**
     * 将给定类类型的监听器添加到此ServletContext。
     *
     * <p>
     *     给定的<tt>listenerClass</tt>必须实现以下一个或多个接口：
     * <ul>
     *     <li>{@link ServletContextAttributeListener}
     *     <li>{@link ServletRequestListener}
     *     <li>{@link ServletRequestAttributeListener}
     *     <li>{@link javax.servlet.http.HttpSessionAttributeListener}
     *     <li>{@link javax.servlet.http.HttpSessionIdListener}
     *     <li>{@link javax.servlet.http.HttpSessionListener}
     * </ul>
     *
     * <p>
     *     如果此ServletContext被传递给{@link ServletContainerInitializer#onStartup}，那么除了上面列出的接口外，
     *     给定的<tt>listenerClass</tt>还可以实现{@link ServletContextListener}。
     *
     * <p>
     *     如果给定的<tt>listenerClass</tt>实现了调用顺序与声明顺序相对应的监听器接口
     *     （即实现了{@link ServletRequestListener}、{@link ServletContextListener}或
     *     {@link javax.servlet.http.HttpSessionListener}），则新监听器将被添加到该接口监听器有序列表的末尾。
     *
     * <p>
     *     如果给定的<tt>listenerClass</tt>表示一个托管Bean，则此方法支持资源注入。
     *     有关托管Bean和资源注入的更多详细信息，请参阅Jakarta EE平台和CDI规范。
     *
     * @param listenerClass 要实例化的监听器类
     * @throws IllegalArgumentException      如果给定的<tt>listenerClass</tt>未实现上述任何接口，
     *                                       或者它实现了{@link ServletContextListener}但此ServletContext
     *                                       未被传递给{@link ServletContainerInitializer#onStartup}
     * @throws IllegalStateException         如果此ServletContext已被初始化
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 3.0
     */
    public void addListener(Class<? extends EventListener> listenerClass);

    /**
     * 实例化指定的EventListener类。
     *
     * <p>
     *     指定的EventListener类必须至少实现以下接口之一：
     *     <ul>
     *         <li>{@link ServletContextListener}</li>
     *         <li>{@link ServletContextAttributeListener}</li>
     *         <li>{@link ServletRequestListener}</li>
     *         <li>{@link ServletRequestAttributeListener}</li>
     *         <li>{@link javax.servlet.http.HttpSessionListener}</li>
     *         <li>{@link javax.servlet.http.HttpSessionAttributeListener}</li>
     *         <li>{@link javax.servlet.http.HttpSessionIdListener}</li>
     *     </ul>
     *
     * <p>
     *     返回的EventListener实例在通过调用{@link #addListener(EventListener)}注册到ServletContext之前可进一步定制。
     *
     * <p>
     *     给定的EventListener类必须定义一个无参数构造函数，用于实例化该类。
     *
     * <p>
     *     如果给定的<tt>clazz</tt>表示一个托管Bean(Managed Bean)，则此方法支持资源注入。
     *     有关托管Bean和资源注入的更多详细信息，请参阅Jakarta EE平台和CDI规范。
     *
     * @param       <T> 要创建的EventListener的类
     * @param clazz 要实例化的EventListener类
     * @return 新的EventListener实例
     * @throws ServletException              如果给定的<tt>clazz</tt>实例化失败
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @throws IllegalArgumentException      如果指定的EventListener类未实现以下任一接口：
     *                                       {@link ServletContextListener}、{@link ServletContextAttributeListener}、
     *                                       {@link ServletRequestListener}、{@link ServletRequestAttributeListener}、
     *                                       {@link javax.servlet.http.HttpSessionAttributeListener}、
     *                                       {@link javax.servlet.http.HttpSessionIdListener}或
     *                                       {@link javax.servlet.http.HttpSessionListener}
     * @since Servlet 3.0
     */
    public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException;

    /**
     * 获取从此ServletContext所代表的Web应用程序的<code>web.xml</code>和<code>web-fragment.xml</code>
     * 描述符文件中聚合的<code>&lt;jsp-config&gt;</code>相关配置。
     *
     * @return 从此ServletContext所代表的Web应用程序的<code>web.xml</code>和<code>web-fragment.xml</code>
     *         描述符文件中聚合的<code>&lt;jsp-config&gt;</code>相关配置，如果不存在此类配置则返回null
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @see javax.servlet.descriptor.JspConfigDescriptor
     * @since Servlet 3.0
     */
    public JspConfigDescriptor getJspConfigDescriptor();

    /**
     * 获取由此ServletContext表示的Web应用程序的类加载器。
     *
     * <p>
     * 如果存在安全管理器，且调用者的类加载器与请求的类加载器不同或不是其祖先，
     * 则将使用<code>RuntimePermission("getClassLoader")</code>权限调用安全管理器的
     * <code>checkPermission</code>方法，以检查是否应授予对请求的类加载器的访问权限。
     *
     * @return 由此ServletContext表示的Web应用程序的类加载器
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @throws SecurityException             如果安全管理器拒绝访问请求的类加载器
     * @since Servlet 3.0
     */
    public ClassLoader getClassLoader();

    /**
     * 声明使用<code>isUserInRole</code>进行测试的角色名称。
     *
     * <p>
     *     由于在{@link ServletRegistration.Dynamic#setServletSecurity setServletSecurity}或
     *     {@link ServletRegistration.Dynamic#setRunAsRole setRunAsRole}方法中使用而隐式声明的角色无需再次声明。
     *
     * @param roleNames 被声明的角色名称
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @throws IllegalArgumentException      如果任何参数roleNames为null或空字符串
     * @throws IllegalStateException         如果ServletContext已被初始化
     * @since Servlet 3.0
     */
    public void declareRoles(String... roleNames);

    /**
     * 返回ServletContext所部署的逻辑主机的配置名称。
     *
     * <p>
     *     Servlet容器可能支持多个逻辑主机。
     *     此方法必须为部署在同一逻辑主机上的所有servlet上下文返回相同的名称，
     *     且该名称必须具有唯一性、在每个逻辑主机上保持稳定，并适用于将服务器配置信息与逻辑主机关联。
     *     返回值不需要也不要求等同于逻辑主机的网络地址或主机名。
     *
     * @return 包含servlet上下文所部署的逻辑主机配置名称的<code>String</code>
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 3.1
     */
    public String getVirtualServerName();

    /**
     * 获取此<tt>ServletContext</tt>默认支持的会话超时时间（以分钟为单位）。
     *
     * @return 此<tt>ServletContext</tt>默认支持的会话超时时间（分钟）
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 4.0
     */
    public int getSessionTimeout();

    /**
     * 设置此ServletContext的会话超时时间（以分钟为单位）。
     *
     * @param sessionTimeout 会话超时时间（分钟）
     * @throws IllegalStateException         如果此ServletContext已被初始化
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 4.0
     */
    public void setSessionTimeout(int sessionTimeout);

    /**
     * 获取此<tt>ServletContext</tt>默认支持的请求字符编码。
     * 如果在部署描述符或容器特定配置中（针对容器中的所有Web应用程序）未指定请求字符编码，则此方法返回null。
     *
     * @return 此<tt>ServletContext</tt>默认支持的请求字符编码
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 4.0
     */
    public String getRequestCharacterEncoding();

    /**
     * 设置此ServletContext的请求字符编码。
     *
     * @param encoding 请求字符编码
     * @throws IllegalStateException         如果此ServletContext已被初始化
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 4.0
     */
    public void setRequestCharacterEncoding(String encoding);

    /**
     * 获取此<tt>ServletContext</tt>默认支持的响应字符编码。
     * 如果在部署描述符或容器特定配置中（针对容器中的所有Web应用程序）未指定响应字符编码，则此方法返回null。
     *
     * @return 此<tt>ServletContext</tt>默认支持的响应字符编码
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 4.0
     */
    public String getResponseCharacterEncoding();

    /**
     * 设置此ServletContext的响应字符编码。
     *
     * @param encoding 响应字符编码
     * @throws IllegalStateException         如果此ServletContext已被初始化
     * @throws UnsupportedOperationException 如果此ServletContext被传递给{@link ServletContextListener#contextInitialized}方法，
     *                                       而该{@link ServletContextListener}既未在<code>web.xml</code>或
     *                                       <code>web-fragment.xml</code>中声明，也未使用
     *                                       {@link javax.servlet.annotation.WebListener}注解标注
     * @since Servlet 4.0
     */
    public void setResponseCharacterEncoding(String encoding);
}
