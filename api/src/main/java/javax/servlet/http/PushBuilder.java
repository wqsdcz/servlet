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

package javax.servlet.http;

import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 构建要推送的请求。
 *
 * 根据RFC 7540第8.2节的规定，承诺的请求必须是可缓存且安全的，且不能包含请求体。
 *
 * <p>
 * 通过调用{@link HttpServletRequest#newPushBuilder()}获取PushBuilder。
 * 每次调用此方法都会基于当前{@code HttpServletRequest}返回一个新的PushBuilder实例，或者返回null。
 * 对返回的PushBuilder的任何修改不会影响后续的返回结果。
 * </p>
 *
 * <p>
 * 实例初始化如下：
 * </p>
 *
 * <ul>
 *
 * <li>方法初始化为"GET"</li>
 *
 * <li>当前{@link HttpServletRequest}的现有请求头会被添加到构建器中，除了：
 *
 * <ul>
 * <li>条件头（在RFC 7232中定义）
 * <li>范围头
 * <li>Expect头
 * <li>授权头
 * <li>Referrer头
 * </ul>
 *
 * </li>
 *
 * <li>如果请求已通过认证，将设置一个Authorization头，其中包含容器生成的令牌，
 * 该令牌将为推送的请求提供等效的授权。</li>
 *
 * <li>会话ID将是{@link HttpServletRequest#getRequestedSessionId()}返回的值，除非
 * 在创建{@code PushBuilder}之前已调用{@link HttpServletRequest#getSession(boolean)}
 * 创建了新的{@link HttpSession}，在这种情况下，新的会话ID将用作PushBuilder的请求会话ID。
 * 请注意，从请求返回的会话ID实际上可以来自两个"来源"之一：Cookie或URL
 * （分别由{@link HttpServletRequest#isRequestedSessionIdFromCookie}和
 * {@link HttpServletRequest#isRequestedSessionIdFromURL}指定）。{@code PushBuilder}的
 * 会话ID也将来自与请求相同的来源。</li>
 *
 * <li>Referer头（原文如此）将被设置为{@link HttpServletRequest#getRequestURL()}加上
 * {@link HttpServletRequest#getQueryString()}（如果有的话）</li>
 *
 * <li>如果在关联的响应上调用了{@link HttpServletResponse#addCookie(Cookie)}，则相应的
 * Cookie头将被添加到PushBuilder中，除非{@link Cookie#getMaxAge()} &lt;=0，
 * 在这种情况下，Cookie将从构建器中移除。</li>
 *
 * </ul>
 *
 * <p>
 * 在调用{@link #push}之前，必须在{@code PushBuilder}实例上调用{@link #path}方法。
 * 如该方法所述，未能这样做必须导致从{@link #push}抛出异常。
 * </p>
 *
 * <p>
 * 在调用{@link #push()}方法以使用构建器的当前状态启动异步推送请求之前，
 * 可以通过链式调用突变方法来定制PushBuilder。调用{@link #push()}之后，
 * 构建器可以重用于另一次推送，但是实现必须确保在从{@link #push}返回之前
 * 清除{@link #path(String)}和条件头（在RFC 7232中定义）的值。
 * 所有其他值在多次调用{@link #push()}期间都会保留。
 *
 * @since Servlet 4.0
 */
public interface PushBuilder {

    /**
     * <p>设置用于推送的HTTP方法。</p>
     *
     * @param method 用于推送的HTTP方法
     * @throws NullPointerException     如果参数为{@code null}
     * @throws IllegalArgumentException 如果参数为空字符串，或任何RFC 7231中定义的不可缓存或不安全的方法，
     *                                  包括POST、PUT、DELETE、CONNECT、OPTIONS和TRACE。
     * @return 当前构建器
     */
    public PushBuilder method(String method);

    /**
     * 设置用于推送的查询字符串。
     *
     * 查询字符串将附加到调用{@link #path(String)}时包含的任何查询字符串。
     * 必须保留所有重复的参数。
     * 当需要多次使用相同查询字符串进行{@link #push()}调用时，应使用此方法而不是在{@link #path(String)}中包含查询。
     *
     * @param queryString 用于推送的查询字符串
     * @return 当前构建器
     */
    public PushBuilder queryString(String queryString);

    /**
     * 设置用于推送的会话ID。
     * 会话ID的设置方式将与关联请求中的方式相同
     * （即如果关联请求使用了cookie，则作为cookie设置；如果关联请求使用了URL参数，则作为URL参数设置）。
     * 默认为请求的会话ID或新创建会话中新分配的会话ID。
     *
     * @param sessionId 用于推送的会话ID
     * @return 当前构建器
     */
    public PushBuilder sessionId(String sessionId);

    /**
     * <p>设置用于推送的请求头。如果构建器已存在同名的请求头，则其值将被覆盖。</p>
     *
     * @param name  要设置的请求头名称
     * @param value 要设置的请求头值
     * @return 当前构建器
     */
    public PushBuilder setHeader(String name, String value);

    /**
     * <p>添加用于推送的请求头。</p>
     *
     * @param name  要添加的请求头名称
     * @param value 要添加的请求头值
     * @return 当前构建器
     */
    public PushBuilder addHeader(String name, String value);

    /**
     * <p>移除指定名称的请求头。如果该请求头不存在，则不执行任何操作。</p>
     *
     * @param name 要移除的请求头名称
     * @return 当前构建器
     */
    public PushBuilder removeHeader(String name);

    /**
     * 设置用于推送的URI路径。
     * 路径可以以"/"开头，此时将被视为绝对路径；否则将被视为相对于关联请求的上下文路径的相对路径。
     * 没有默认路径，每次调用{@link #push()}之前都必须调用{@link #path(String)}方法。
     * 如果参数{@code path}中包含查询字符串，则其内容必须与先前传递给{@link #queryString}的内容合并，并保留重复参数。
     *
     * @param path 用于推送的URI路径，可以包含查询字符串
     * @return 当前构建器
     */
    public PushBuilder path(String path);

    /**
     * 根据构建器的当前状态推送资源，该方法必须是非阻塞的。
     *
     * <p>
     *     基于PushBuilder的当前状态推送资源。
     *     调用此方法并不保证资源一定会被实际推送，因为客户端可能使用底层HTTP/2协议拒绝接收推送的资源。
     *
     * <p>
     *     如果构建器包含会话ID，则推送的请求将根据需要包含该会话ID，可能是作为Cookie或URI参数。
     *     构建器的查询字符串将与任何传递的查询字符串合并。
     *
     * <p>
     *     从此方法返回之前，构建器会将其路径和条件头（在RFC 7232中定义）置为空。
     *     所有其他字段保持不变，以便在可能的另一次推送中重用。
     *
     * @throws IllegalStateException 如果在此实例实例化后或上次未抛出IllegalStateException的
     *                              {@code push()}调用之后，没有调用{@link #path}方法
     */
    public void push();

    /**
     * 返回用于推送的HTTP方法。
     *
     * @return 用于推送的HTTP方法
     */
    public String getMethod();

    /**
     * 返回用于推送的查询字符串。
     *
     * @return 用于推送的查询字符串
     */
    public String getQueryString();

    /**
     * 返回用于推送的会话ID。
     *
     * @return 用于推送的会话ID
     */
    public String getSessionId();

    /**
     * 返回用于推送的请求头集合。
     *
     * <p>
     *     返回的集合不受{@code PushBuilder}对象支持，因此对返回集合的更改不会反映在{@code PushBuilder}对象中，反之亦然。
     *
     * @return 用于推送的请求头集合
     */
    public Set<String> getHeaderNames();

    /**
     * 返回用于推送的指定名称的请求头。
     *
     * @param name 请求头名称
     * @return 用于推送的指定名称的请求头
     */
    public String getHeader(String name);

    /**
     * 返回用于推送的URI路径。
     *
     * @return 用于推送的URI路径
     */
    public String getPath();
}
