/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates and others.
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

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <p>
 * 提供一个抽象类，可通过继承该类来创建适用于网站的HTTP过滤器。
 * <code>HttpFilter</code>的子类应重写
 * {@link #doFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain) }方法。
 * </p>
 *
 * <p>
 *     过滤器通常运行在多线程服务器上，因此请注意过滤器必须处理并发请求，并小心同步对共享资源的访问。
 *     共享资源包括内存中的数据（如实例或类变量）和外部对象（如文件、数据库连接和网络连接）。
 *     有关Java程序中处理多线程的更多信息，
 *     请参阅<a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/">Java多线程编程教程</a>。
 *
 * @author Various
 *
 * @since Servlet 4.0
 */
public abstract class HttpFilter extends GenericFilter {

    private static final long serialVersionUID = 7478463438252262094L;

    /**
     * <p>此方法为空实现，因为这是一个抽象类。</p>
     *
     * @since 4.0
     */
    public HttpFilter() {
    }

    /**
     * <p>
     *     每次由于客户端请求链末端的资源而导致请求/响应对通过过滤器链传递时，容器都会调用Filter的<code>doFilter</code>方法。
     *     传入此方法的FilterChain允许过滤器将请求和响应传递给链中的下一个实体。
     *     无需重写此方法。
     * </p>
     *
     * <p>
     *     默认实现会检查传入的{@code req}和{@code res}对象，
     *     以确定它们是否分别为{@link HttpServletRequest}和{@link HttpServletResponse}的实例。
     *     如果不是，则抛出{@link ServletException}。
     *     否则，将调用受保护的
     *     {@link #doFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)}方法。
     * </p>
     *
     * @param req   包含客户端对过滤器发出的请求的{@link ServletRequest}对象
     * @param res   包含过滤器发送给客户端的响应的{@link ServletResponse}对象
     * @param chain 用于调用下一个过滤器或资源的<code>FilterChain</code>
     * @throws IOException      如果过滤器处理请求时检测到输入或输出错误
     * @throws ServletException 如果无法处理请求，或者参数不是相应的{@link HttpServletRequest}
     *                          或{@link HttpServletResponse}实例
     * @since Servlet 4.0
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if (!(req instanceof HttpServletRequest && res instanceof HttpServletResponse)) {
            throw new ServletException("non-HTTP request or response");
        }

        this.doFilter((HttpServletRequest) req, (HttpServletResponse) res, chain);
    }

    /**
     * <p>
     *     每次由于客户端请求链末端的资源而导致请求/响应对通过过滤器链传递时，容器都会调用Filter的<code>doFilter</code>方法。
     *     传入此方法的FilterChain允许过滤器将请求和响应传递给链中的下一个实体。
     * </p>
     *
     * <p>默认实现仅调用{@link FilterChain#doFilter}</p>
     *
     * @param req   包含客户端对过滤器发出的请求的{@link HttpServletRequest}对象
     * @param res   包含过滤器发送给客户端的响应的{@link HttpServletResponse}对象
     * @param chain 用于调用下一个过滤器或资源的<code>FilterChain</code>
     * @throws IOException      如果过滤器处理请求时检测到输入或输出错误
     * @throws ServletException 如果无法处理请求
     * @since Servlet 4.0
     */
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(req, res);
    }

}
