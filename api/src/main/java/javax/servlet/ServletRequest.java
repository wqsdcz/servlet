/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates and others.
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

import java.io.*;
import java.util.*;

/**
 * 定义了一个向Servlet提供客户端请求信息的对象。
 * Servlet容器会创建一个 <code>ServletRequest</code>对象，
 * 并将其作为参数传递给servlet的<code>service</code>方法。
 *
 * <p>
 *     <code>ServletRequest</code>对象提供的数据包括参数名称和值、属性以及输入流。
 *     扩展<code>ServletRequest</code>的接口可以提供特定于协议的额外数据
 *     （例如，通过{@link javax.servlet.http.HttpServletRequest}提供HTTP数据）。
 *
 * @author Various
 *
 * @see javax.servlet.http.HttpServletRequest
 *
 */
public interface ServletRequest {

    /**
     * 返回指定属性名称的属性值（以<code>Object</code>类型返回），如果给定名称的属性不存在则返回<code>null</code>。
     *
     * <p>
     *     属性可以通过两种方式设置。
     *     Servlet容器可以设置属性来提供关于请求的自定义信息，例如，对于使用HTTPS发出的请求，
     *     可以使用<code>javax.servlet.request.X509Certificate</code>属性来检索客户端证书信息。
     *     也可以通过编程方式使用{@link ServletRequest#setAttribute}方法设置属性，
     *     这允许在调用{@link RequestDispatcher}之前将信息嵌入到请求中。
     *
     * <p>
     *     属性命名应遵循与包名相同的约定。
     *     本规范保留了与<code>java.*</code>、<code>javax.*</code> 和 <code>sun.*</code>匹配的名称。
     *
     * @param name 指定属性名称的<code>String</code>
     * @return 包含属性值的<code>Object</code>对象，如果属性不存在则返回<code>null</code>
     */
    public Object getAttribute(String name);

    /**
     * 返回一个包含该请求所有可用属性名称的<code>Enumeration</code>枚举对象。
     * 如果该请求没有任何可用属性，则返回空的<code>Enumeration</code>枚举对象。
     *
     * @return 包含请求属性名称的字符串枚举对象
     */
    public Enumeration<String> getAttributeNames();

    /**
     * 在此请求中存储一个属性。
     * 属性在不同请求之间会被重置。
     * 该方法通常与{@link RequestDispatcher}联合使用。
     *
     * <p>属性命名应遵循与包名相同的规范。<br>
     * 如果传入的对象为null，其效果等同于调用{@link #removeAttribute}方法。<br>
     * 需要注意的是：当请求通过<code>RequestDispatcher</code>从不同Web应用中的servlet派发时，
     * 通过此方法设置的对象可能在调用方servlet中无法被正确获取。
     *
     * @param name 指定属性名称的<code>String</code>
     * @param o    要存储的<code>Object</code>对象
     *
     */
    public void setAttribute(String name, Object o);

    /**
     * 从此请求中移除一个属性。
     * 该方法通常不需要使用，因为属性仅在请求处理期间持续存在。
     *
     * <p>
     *     属性命名应遵循与包名相同的规范。
     *     以<code>java.*</code>、<code>javax.*</code>和<code>com.sun.*</code> 开头的名称保留由Sun Microsystems使用。
     *
     * @param name 指定要移除的属性名称的<code>String</code>
     */
    public void removeAttribute(String name);

    /**
     * 返回请求参数的值（以<code>String</code>类型返回），如果参数不存在则返回<code>null</code>。
     * 请求参数是随请求发送的额外信息。
     * 对于HTTP servlet，参数包含在【查询字符串】或【提交的表单数据】中。
     *
     * <p>
     *     只有在确定参数只有一个值时才应使用此方法。
     *     如果参数可能有多个值，请使用{@link #getParameterValues}。
     *
     * <p>
     *     如果将此方法用于多值参数，返回值将等于<code>getParameterValues</code>返回数组中的第一个值。
     *
     * <p>
     *     如果参数的数据是在请求正文中发送的（例如HTTP POST请求），
     *     则直接通过{@link #getInputStream}或{@link #getReader}读取正文可能会干扰此方法的执行。
     *
     * @param name 指定参数名称的<code>String</code>
     * @return 表示参数单个值的<code>String</code>
     * @see #getParameterValues
     */
    public String getParameter(String name);

    /**
     * 返回包含指定请求参数所有值的<code>String</code>对象数组，如果该参数不存在则返回<code>null</code>。
     *
     * <p>
     *     如果参数只有一个值，则数组长度为1。
     *
     * @param name 包含请求参数名称的<code>String</code>对象
     * @return 包含参数值的<code>String</code>对象数组
     * @see #getParameter
     */
    public String[] getParameterValues(String name);

    /**
     * 返回一个包含此请求中所有参数名称的<code>String</code>对象枚举。
     * 如果请求没有参数，则返回空的<code>Enumeration</code>枚举对象。
     *
     * @return <code>String</code>对象的枚举，每个<code>String</code>包含一个请求参数的名称；
     *         如果请求没有参数，则返回空的<code>Enumeration</code>枚举对象
     */
    public Enumeration<String> getParameterNames();

    /**
     * 返回此请求参数的java.util.Map映射对象。
     *
     * <p>
     *     请求参数是随请求发送的额外信息。
     *     对于HTTP servlet，参数包含在【查询字符串】或【提交的表单数据】中。
     *
     * @return 一个不可变的java.util.Map对象，其中参数名称作为键，参数值作为映射值。
     *         参数映射中的键为String类型，参数映射中的值为String数组类型。
     */
    public Map<String, String[]> getParameterMap();

    /**
     * 返回此请求正文使用的字符编码名称。
     * 如果未指定请求编码字符集，则返回<code>null</code>。
     * 以下用于指定请求字符编码的方法按优先级递减顺序被采用：每个Request单独设置、每个Web应用设置
     * （使用{@link ServletContext#setRequestCharacterEncoding}或部署描述符配置）、
     * 以及容器级设置（对于部署在该容器中的所有Web应用，使用供应商特定配置）。
     *
     * @return 包含字符编码名称的<code>String</code>，如果请求未指定字符编码则返回<code>null</code>
     */
    public String getCharacterEncoding();

    /**
     * 重写此请求正文中使用的字符编码名称。
     * 此方法必须在读取请求参数或使用getReader()方法读取输入之前调用，否则该方法将不产生任何效果。
     *
     * @param env 包含字符编码名称的<code>String</code>
     * @throws UnsupportedEncodingException 如果此ServletRequest仍处于可设置字符编码的状态，但指定的编码无效时抛出
     */
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException;

    /**
     * 返回请求正文的长度（以字节为单位），该长度可通过输入流获取。
     * 如果长度未知或大于Integer.MAX_VALUE，则返回-1。
     * 对于HTTP servlet，该值与CGI变量CONTENT_LENGTH的值相同。
     *
     * @return 包含请求正文长度的整数值，如果长度未知或大于Integer.MAX_VALUE则返回-1
     */
    public int getContentLength();

    /**
     * 返回请求正文的长度（以字节为单位），该长度可通过输入流获取。
     * 如果长度未知，则返回-1。
     * 对于HTTP servlet，该值与CGI变量CONTENT_LENGTH的值相同。
     *
     * @return 包含请求正文长度的长整型值，如果长度未知则返回-1L
     * @since Servlet 3.1
     */
    public long getContentLengthLong();

    /**
     * 返回请求正文的MIME类型，如果类型未知则返回<code>null</code>。
     * 对于HTTP servlet，该值与CGI变量CONTENT_TYPE的值相同。
     *
     * @return 包含请求MIME类型名称的<code>String</code>，如果类型未知则返回null
     */
    public String getContentType();

    /**
     * 使用{@link ServletInputStream}以二进制数据形式获取请求体。
     * 可以调用此方法或 {@link #getReader}方法之一来读取请求体，但不能同时调用两者。
     *
     * @return 包含请求体的{@link ServletInputStream}对象
     * @exception IllegalStateException 如果已为此请求调用了{@link #getReader}方法
     * @exception IOException           如果发生输入或输出异常
     */
    public ServletInputStream getInputStream() throws IOException;

    /**
     * 使用<code>BufferedReader</code>以字符数据形式读取请求体。
     * 读取器会根据请求体使用的字符编码进行字符转换。
     * 可以调用此方法或{@link #getInputStream}方法之一来读取请求体，但不能同时调用两者。
     *
     * @return 包含请求体的<code>BufferedReader</code>对象
     * @exception UnsupportedEncodingException 如果使用了不支持的字符集编码且文本无法解码
     * @exception IllegalStateException        如果已在此请求上调用过{@link #getInputStream}方法
     * @exception IOException                  如果发生输入或输出异常
     * @see #getInputStream
     */
    public BufferedReader getReader() throws IOException;

    /**
     * 返回请求所使用的协议名称及版本号，格式为<i>协议/主版本号.次版本号</i>，例如：HTTP/1.1。
     * 对于HTTP servlet，返回值与CGI变量<code>SERVER_PROTOCOL</code>的值相同。
     *
     * @return 包含协议名称和版本号的<code>String</code>
     */
    public String getProtocol();

    /**
     * 返回此请求所使用的协议方案名称，例如：<code>http</code>、<code>https</code> 或 <code>ftp</code>。
     * 如RFC 1738所述，不同协议方案具有不同的URL构造规则。
     *
     * @return 包含此请求所使用的协议方案名称的<code>String</code>
     */
    public String getScheme();

    /**
     * 返回一个boolean值，指示该请求是否通过安全通道（如:HTTPS）发出。
     *
     * @return 一个boolean值，指示请求是否通过安全通道发出
     */
    public boolean isSecure();

    /**
     * 返回请求所发送至的服务器主机名。
     * 如果有"Host"头字段值，则返回其中":"前的部分；
     * 否则返回已解析的服务器名称或服务器IP地址。
     * 示例：Host: www.example.com:8080，返回www.example.com
     *
     * @return 包含服务器名称的<code>String</code>
     */
    public String getServerName();

    /**
     * 返回请求所被发送至的端口号。
     * 如果有"Host"头字段值，则返回其中":"后的部分；
     * 否则返回服务器接受客户端连接时所使用的端口号。
     * 示例：Host: www.example.com:8080，返回8080
     *
     * @return 指定端口号的整数值
     */
    public int getServerPort();

    /**
     * 返回发送请求的客户端或最后一个代理的互联网协议（IP）地址。
     * 对于HTTP servlets，该值与CGI变量<code>REMOTE_ADDR</code>的值相同。
     *
     * @return 包含发送请求的客户端IP地址的<code>String</code>
     */
    public String getRemoteAddr();

    /**
     * 返回发送请求的客户端或最后一个代理的完全限定域名。
     * 如果引擎不能或选择不解析主机名（为了提升性能），则该方法返回IP地址的点分字符串形式。
     * 对于HTTP servlets，该值与CGI变量<code>REMOTE_HOST</code>的值相同。
     *
     * @return 包含客户端完全限定域名的<code>String</code>
     */
    public String getRemoteHost();

    /**
     * 返回发送请求的客户端或最后一个代理的互联网协议（IP）源端口号。
     *
     * @return 指定端口号的整数值
     * @since Servlet 2.4
     */
    public int getRemotePort();

    /**
     * 返回接收请求的接口的互联网协议（IP）地址。
     *
     * @return 包含接收请求的IP地址的<code>String</code>
     * @since Servlet 2.4
     */
    public String getLocalAddr();

    /**
     * 返回接收请求的互联网协议（IP）接口的主机名。
     *
     * @return 包含接收请求的IP的主机名的<code>String</code>
     * @since Servlet 2.4
     */
    public String getLocalName();

    /**
     * 返回接收请求的接口的互联网协议（IP）端口号。
     *
     * @return 指定端口号的整数值
     * @since Servlet 2.4
     */
    public int getLocalPort();

    /**
     * 返回客户端基于Accept-Language头首选的<code>Locale</code>（用于接收内容）。
     * 如果客户端请求未提供Accept-Language头，则返回服务器的默认区域设置。
     *
     * @return 客户端首选的<code>Locale</code>
     */
    public Locale getLocale();

    /**
     * 返回一个<code>Locale</code>对象的枚举，根据Accept-Language头信息，按优先级降序排列客户端可接受的区域设置。
     * 如果客户端请求未提供Accept-Language头，则返回包含服务器默认区域设置的单个<code>Locale</code>的枚举。
     *
     * @return 客户端首选<code>Locale</code>对象的枚举
     */
    public Enumeration<Locale> getLocales();

    /**
     * 返回一个{@link RequestDispatcher}对象，该对象作为位于给定路径资源的包装器。
     * <code>RequestDispatcher</code>对象可用于将请求转发到资源，或将资源包含在响应中。
     * 该资源可以是动态的也可以是静态的。
     *
     * <p>
     *     指定的路径可以是相对路径，但不能超出当前servlet上下文范围。
     *     如果路径以"/"开头，则被解释为相对于当前上下文根目录。
     *     如果servlet容器无法返回<code>RequestDispatcher</code>，则此方法返回<code>null</code>。
     *
     * <p>
     *     使用RequestDispatcher可以将请求分发到Web应用程序的任何部分，
     *     绕过隐式（无法直接访问WEB-INF或META-INF）和显式（由Web应用程序定义）的安全约束。
     *     必须避免使用未经处理的用户提供数据来构造传递给RequestDispatcher的路径，
     *     因为这极有可能在应用程序中造成安全漏洞。
     *
     * <p>
     *     此方法与{@link ServletContext#getRequestDispatcher}的区别在于，本方法可以使用相对路径。
     *
     * @param path 指定资源路径名的<code>String</code>。如果是相对路径，则必须相对于当前servlet
     * @return 作为指定路径资源包装器的<code>RequestDispatcher</code>对象，
     *         如果servlet容器无法返回<code>RequestDispatcher</code>则返回<code>null</code>
     * @see RequestDispatcher
     * @see ServletContext#getRequestDispatcher
     */
    public RequestDispatcher getRequestDispatcher(String path);

    /**
     * 获取此请求的分发器类型。
     *
     * <p>
     *     容器使用请求的分发器类型来选择需要应用于该请求的过滤器：
     *     只有具有匹配的【分发器类型】和【URL模式】的过滤器才会被应用。
     * <p>
     *     允许为多种分发器类型配置的过滤器查询请求的分发器类型，
     *     使得过滤器能够根据不同的分发器类型以不同方式处理请求。
     *
     * <p>
     *     请求的初始分发器类型定义为<code>DispatcherType.REQUEST</code>。
     *     通过{@link RequestDispatcher#forward(ServletRequest, ServletResponse)}或
     *     {@link RequestDispatcher#include(ServletRequest, ServletResponse)}分发的请求的分发器类型
     *     分别定义为<code>DispatcherType.FORWARD</code>或<code>DispatcherType.INCLUDE</code>，
     *     而通过{@link AsyncContext#dispatch}方法之一分发的异步请求的分发器类型定义为<code>DispatcherType.ASYNC</code>。
     *     最后，由容器的错误处理机制分发给错误页面的请求的分发器类型定义为<code>DispatcherType.ERROR</code>。
     *
     * @return 此请求的分发器类型
     * @see DispatcherType
     * @since Servlet 3.0
     */
    public DispatcherType getDispatcherType();

    /**
     * @param path 需要返回真实路径的路径字符串
     * @return 路径对应的<i>真实</i>路径，如果无法完成转换则返回<tt>null</tt>
     * @deprecated 自Java Servlet API 2.1版本起，请改用{@link ServletContext#getRealPath}
     */
    public String getRealPath(String path);

    /**
     * 获取此ServletRequest最后被分派到的ServletContext。
     *
     * @return 此ServletRequest最后被分派到的ServletContext
     * @since Servlet 3.0
     */
    public ServletContext getServletContext();

    /**
     * 将此请求置入异步模式，并使用原始的（未包装的）ServletRequest和ServletResponse对象初始化其{@link AsyncContext}。
     *
     * <p>
     * 调用此方法将延迟相关响应的提交，直到在返回的{@link AsyncContext}上调用{@link AsyncContext#complete}方法，
     * 或异步操作超时为止。
     *
     * <p>
     * 在返回的AsyncContext上调用{@link AsyncContext#hasOriginalRequestAndResponse()}将返回<code>true</code>。
     * 在此请求进入异步模式后，在<i>出站</i>方向调用的任何过滤器可以将此作为指示，表明它们在<i>入站</i>调用期间添加的
     * 任何请求和/或响应包装器不需要在整个异步操作期间保持存在，因此可以释放它们相关的任何资源。
     *
     * <p>
     * 该方法会清除之前通过任意startAsync方法调用返回的AsyncContext中注册的{@link AsyncListener}实例列表（如果有），
     * 并在清除前调用每个AsyncListener的{@link AsyncListener#onStartAsync onStartAsync}方法。
     *
     * <p>
     * 后续调用此方法或其重载方法，将返回相同的AsyncContext实例（会根据情况进行重新初始化）。
     *
     * @return （重新）初始化后的AsyncContext
     *
     * @throws IllegalStateException 如果此请求处于不支持异步操作的过滤器或Servlet范围内（即{@link #isAsyncSupported}返回false），
     *                               或如果在没有任何异步分派（由某个{@link AsyncContext#dispatch}方法导致）的情况下再次调用此方法，
     *                               或在任何此类分派范围之外调用，或在同一分派范围内再次调用，或如果响应已关闭
     *
     * @see AsyncContext#dispatch()
     * @since Servlet 3.0
     */
    public AsyncContext startAsync() throws IllegalStateException;

    /**
     * 将此请求置入异步模式，并使用给定的请求和响应对象初始化其{@link AsyncContext}。
     *
     * <p>
     * ServletRequest和ServletResponse参数必须是传递给Servlet的{@link Servlet#service service}方法
     * 或Filter的{@link Filter#doFilter doFilter}方法的相同实例（或包装这些实例的
     * {@link ServletRequestWrapper}和{@link ServletResponseWrapper}实例），具体取决于调用此方法所在的作用域。
     *
     * <p>
     * 调用此方法将延迟关联响应的提交，直到在返回的{@link AsyncContext}上调用{@link AsyncContext#complete}方法，
     * 或异步操作超时为止。
     *
     * <p>
     * 在返回的AsyncContext上调用{@link AsyncContext#hasOriginalRequestAndResponse()}将返回<code>false</code>，
     * 除非传入的ServletRequest和ServletResponse参数是原始实例或不包含任何应用提供的包装器。
     * 在此请求进入异步模式后，在<i>出站</i>方向调用的任何过滤器可以将此作为指示，表明它们在<i>入站</i>调用期间添加的
     * 某些请求和/或响应包装器可能需要在整个异步操作期间保持存在，并且它们相关的资源可能不会被释放。
     * 只有在用于初始化AsyncContext并将通过调用{@link AsyncContext#getRequest()}返回的给定<code>servletRequest</code>
     * 不包含所述ServletRequestWrapper的情况下，过滤器在<i>入站</i>调用期间应用的ServletRequestWrapper才可以在
     * 该过滤器的<i>出站</i>调用期间被释放。同样的情况也适用于ServletResponseWrapper实例。
     *
     * <p>
     * 该方法会清除之前通过任意startAsync方法调用返回的AsyncContext中注册的{@link AsyncListener}实例列表（如果有），
     * 并在清除前调用每个AsyncListener的{@link AsyncListener#onStartAsync onStartAsync}方法。
     *
     * <p>
     * 后续调用此方法或其无参重载方法，将返回相同的AsyncContext实例（会根据情况进行重新初始化）。
     * 如果在调用此方法后调用其无参重载方法，则指定的（可能经过包装的）请求和响应对象将<i>锁定</i>在返回的AsyncContext中。
     *
     * @param servletRequest  用于初始化AsyncContext的ServletRequest
     * @param servletResponse 用于初始化AsyncContext的ServletResponse
     *
     * @return （重新）初始化后的AsyncContext
     *
     * @throws IllegalStateException 如果此请求处于不支持异步操作的过滤器或Servlet范围内（即{@link #isAsyncSupported}返回false），
     *                               或如果在没有任何异步分派（由某个{@link AsyncContext#dispatch}方法导致）的情况下再次调用此方法，
     *                               或在任何此类分派范围之外调用，或在同一分派范围内再次调用，或如果响应已关闭
     *
     * @since Servlet 3.0
     */
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IllegalStateException;

    /**
     * 检查此请求是否已被置于异步模式。
     *
     * <p>
     *     通过对ServletRequest调用{@link #startAsync}或{@link #startAsync(ServletRequest,ServletResponse)}方法可将其置于异步模式。
     *
     * <p>
     *     如果此请求曾被置于异步模式，但之后通过{@link AsyncContext#dispatch}方法之一进行了分派，
     *     或通过调用{@link AsyncContext#complete}脱离了异步模式，则该方法返回<tt>false</tt>。
     *
     * @return 如果此请求已被置于异步模式则返回true，否则返回false
     * @since Servlet 3.0
     */
    public boolean isAsyncStarted();

    /**
     * 检查此请求是否支持异步操作。
     *
     * <p>
     * 如果此请求处于未标注或未在部署描述符中声明支持异步处理的过滤器或Servlet范围内，
     * 则对该请求禁用异步操作。
     *
     * @return 如果此请求支持异步操作则返回true，否则返回false
     *
     * @since Servlet 3.0
     */
    public boolean isAsyncSupported();

    /**
     * 获取通过最近调用{@link #startAsync}或{@link #startAsync(ServletRequest,ServletResponse)}方法
     * 创建或重新初始化的AsyncContext。
     *
     * @return 通过最近调用{@link #startAsync}或{@link #startAsync(ServletRequest,ServletResponse)}
     *         方法创建或重新初始化的AsyncContext
     * @throws IllegalStateException 如果此请求尚未被置于异步模式，即如果既未调用{@link #startAsync}
     *                               也未调用{@link #startAsync(ServletRequest,ServletResponse)}方法
     * @since Servlet 3.0
     */
    public AsyncContext getAsyncContext();

}
