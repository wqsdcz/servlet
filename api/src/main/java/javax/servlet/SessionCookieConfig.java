/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package javax.servlet;

/**
 * 用于配置跟踪会话所使用的Cookie的各种属性的类。
 *
 * <p>通过调用{@link ServletContext#getSessionCookieConfig}可获得此类的实例。
 *
 * @since Servlet 3.0
 */
public interface SessionCookieConfig {

    /**
     * 设置将分配给会话跟踪Cookie的名称。
     * 会话跟踪Cookie是为了代表<tt>ServletContext</tt>所对应应用程序创建的。
     * 当前<tt>SessionCookieConfig</tt>来自于<tt>ServletContext</tt>。
     *
     * <p>
     *     注意：
     *     更改会话跟踪Cookie的名称可能会破坏其他层级（例如：负载均衡前端），
     *     因为这些层级可能假定Cookie名称等于默认的<tt>JSESSIONID</tt>，因此应谨慎进行此操作。
     *
     * @param name 要使用的Cookie名称
     * @throws IllegalStateException 如果获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>已被初始化
     */
    public void setName(String name);

    /**
     * 获取将分配给会话跟踪Cookie的名称。
     * 会话跟踪Cookie是为了代表<tt>ServletContext</tt>所对应应用程序创建的。
     * 当前<tt>SessionCookieConfig</tt>来自于<tt>ServletContext</tt>。
     *
     * <p>
     *     默认情况下，将使用<tt>JSESSIONID</tt>作为Cookie名称。
     *
     * @return 通过{@link #setName}设置的Cookie名称，如果从未调用{@link #setName}则返回<tt>null</tt>
     * @see javax.servlet.http.Cookie#getName()
     */
    public String getName();

    /**
     * 设置将分配给会话跟踪Cookie的域名。
     * 会话跟踪Cookie是为了代表<tt>ServletContext</tt>所对应应用程序创建的。
     * 当前<tt>SessionCookieConfig</tt>来自于<tt>ServletContext</tt>。
     *
     * @param domain 要使用的Cookie域名
     * @throws IllegalStateException 如果获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>已被初始化
     * @see javax.servlet.http.Cookie#setDomain(String)
     */
    public void setDomain(String domain);

    /**
     * 获取将分配给会话跟踪Cookie的域名。
     * 会话跟踪Cookie是为了代表<tt>ServletContext</tt>所对应应用程序创建的。
     * 当前<tt>SessionCookieConfig</tt>来自于<tt>ServletContext</tt>。
     *
     * @return 通过{@link #setDomain}设置的Cookie域名，如果从未调用{@link #setDomain}则返回<tt>null</tt>
     * @see javax.servlet.http.Cookie#getDomain()
     */
    public String getDomain();

    /**
     * 设置将分配给会话跟踪Cookie的路径。
     * 会话跟踪Cookie是为了代表<tt>ServletContext</tt>所对应应用程序创建的。
     * 当前<tt>SessionCookieConfig</tt>来自于<tt>ServletContext</tt>。
     *
     * @param path 要使用的Cookie路径
     * @throws IllegalStateException 如果获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>已被初始化
     * @see javax.servlet.http.Cookie#setPath(String)
     */
    public void setPath(String path);

    /**
     * 获取将分配给会话跟踪Cookie的路径。
     * 会话跟踪Cookie是为了代表<tt>ServletContext</tt>所对应应用程序创建的。
     * 当前<tt>SessionCookieConfig</tt>来自于<tt>ServletContext</tt>。
     *
     * <p>
     *     默认情况下，将使用获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>的上下文路径。
     *
     * @return 通过{@link #setPath}设置的Cookie路径，如果从未调用{@link #setPath}则返回<tt>null</tt>
     * @see javax.servlet.http.Cookie#getPath()
     */
    public String getPath();

    /**
     * 设置会话跟踪Cookie的注释。
     * 会话跟踪Cookie是为了代表<tt>ServletContext</tt>所对应应用程序创建的。
     * 当前<tt>SessionCookieConfig</tt>来自于<tt>ServletContext</tt>。
     *
     * <p>
     *     此调用的副作用是，会话跟踪Cookie将被标记一个等于<code>1</code>的<code>Version</code>属性。
     *
     * @param comment 要使用的Cookie注释
     * @throws IllegalStateException 如果获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>已被初始化
     * @see javax.servlet.http.Cookie#setComment(String)
     * @see javax.servlet.http.Cookie#getVersion
     */
    public void setComment(String comment);

    /**
     * 获取会话跟踪Cookie的注释。
     * 会话跟踪Cookie是为了代表<tt>ServletContext</tt>所对应应用程序创建的。
     * 当前<tt>SessionCookieConfig</tt>来自于<tt>ServletContext</tt>。
     *
     * @return 通过{@link #setComment}设置的Cookie注释，如果从未调用{@link #setComment}则返回<tt>null</tt>
     * @see javax.servlet.http.Cookie#getComment()
     */
    public String getComment();

    /**
     * 标记或取消标记会话跟踪Cookie的<i>HttpOnly</i>属性。
     * 会话跟踪Cookie是为了代表<tt>ServletContext</tt>所对应应用程序创建的。
     * 当前<tt>SessionCookieConfig</tt>来自于<tt>ServletContext</tt>。
     *
     * <p>
     *     通过添加<tt>HttpOnly</tt>属性可将Cookie标记为<tt>HttpOnly</tt>。
     *     <i>HttpOnly</i>Cookie不应暴露给客户端脚本代码，因此可能有助于减轻某些类型的跨站脚本攻击。
     *
     * @param httpOnly 如果为true，则代表获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>
     *                 所对应应用程序创建的会话跟踪Cookie将被标记为<i>HttpOnly</i>，否则为false
     * @throws IllegalStateException 如果获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>已被初始化
     * @see javax.servlet.http.Cookie#setHttpOnly(boolean)
     */
    public void setHttpOnly(boolean httpOnly);

    /**
     * 检查会话跟踪Cookie是否会被标记为<i>HttpOnly</i>。
     * 会话跟踪Cookie是为了代表<tt>ServletContext</tt>所对应应用程序创建的。
     * 当前<tt>SessionCookieConfig</tt>来自于<tt>ServletContext</tt>。
     *
     * @return 如果为true，则表示代表获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>
     *         所对应应用程序创建的会话跟踪Cookie将被标记为<i>HttpOnly</i>，否则为false
     * @see javax.servlet.http.Cookie#isHttpOnly()
     */
    public boolean isHttpOnly();

    /**
     * 标记或取消标记会话跟踪Cookie的<i>secure</i>属性。
     * 会话跟踪Cookie是为了代表<tt>ServletContext</tt>所对应应用程序创建的。
     * 当前<tt>SessionCookieConfig</tt>来自于<tt>ServletContext</tt>。
     *
     * <p>
     *     即使启动会话的请求是通过HTTP传来的，
     *     标记会话跟踪Cookie为<tt>secure</tt>的一个用例是支持Web容器前端部署SSL卸载负载均衡器的拓扑结构。
     *     在这种情况下，客户端与负载均衡器之间的通信将通过HTTPS进行，而负载均衡器与Web容器之间的通信则使用HTTP。
     *
     * @param secure 如果为true，则代表获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>
     *               所对应应用程序创建的会话跟踪Cookie将被标记为<i>secure</i>（即使启动相应会话的请求
     *               使用的是普通HTTP而不是HTTPS）；如果为false，则仅当启动相应会话的请求也是安全请求时
     *               才会将会话跟踪Cookie标记为<i>secure</i>
     * @throws IllegalStateException 如果获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>已被初始化
     * @see javax.servlet.http.Cookie#setSecure(boolean)
     * @see ServletRequest#isSecure()
     */
    public void setSecure(boolean secure);

    /**
     * 检查会话跟踪Cookie是否会被标记为<i>secure</i>，即使启动相应会话的请求使用的是普通HTTP而不是HTTPS。
     * 会话跟踪Cookie是为了代表<tt>ServletContext</tt>所对应应用程序创建的。
     * 当前<tt>SessionCookieConfig</tt>来自于<tt>ServletContext</tt>。
     *
     * @return 如果为true，则表示代表获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>
     *         所对应应用程序创建的会话跟踪Cookie将被标记为<i>secure</i>（即使启动相应会话的请求
     *         使用的是普通HTTP而不是HTTPS）；如果为false，则表示仅当启动相应会话的请求也是安全请求时
     *         才会将会话跟踪Cookie标记为<i>secure</i>
     * @see javax.servlet.http.Cookie#getSecure()
     * @see ServletRequest#isSecure()
     */
    public boolean isSecure();

    /**
     * 设置会话跟踪Cookie的存活时间（以秒为单位）。
     * 会话跟踪Cookie是为了代表<tt>ServletContext</tt>所对应应用程序创建的。
     * 当前<tt>SessionCookieConfig</tt>来自于<tt>ServletContext</tt>。
     *
     * @param maxAge 代表获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>
     *               所对应应用程序创建的会话跟踪Cookie的生命周期（以秒为单位）
     * @throws IllegalStateException 如果获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>已被初始化
     * @see javax.servlet.http.Cookie#setMaxAge
     */
    public void setMaxAge(int maxAge);

    /**
     * 获取会话跟踪Cookie的存活时间（以秒为单位）。
     * 会话跟踪Cookie是为了代表<tt>ServletContext</tt>所对应应用程序创建的。
     * 当前<tt>SessionCookieConfig</tt>来自于<tt>ServletContext</tt>。
     * <p>
     *     默认情况下返回<tt>-1</tt>。
     *
     * @return 代表获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>
     *         所对应应用程序创建的会话跟踪Cookie的生命周期（以秒为单位），或默认值<tt>-1</tt>
     * @see javax.servlet.http.Cookie#getMaxAge
     */
    public int getMaxAge();
}
