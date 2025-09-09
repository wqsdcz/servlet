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

import java.util.*;

/**
 * 通过该接口可对 {@link Filter} 进行进一步配置。
 *
 * @since Servlet 3.0
 */
public interface FilterRegistration extends Registration {

    /**
     * 为此 FilterRegistration 所代表的过滤器添加具有指定 Servlet 名称和调度器类型的过滤器映射。
     *
     * <p>
     * 过滤器映射按照添加的顺序进行匹配。
     *
     * <p>
     * 根据 <tt>isMatchAfter</tt> 参数的值，给定的过滤器映射将在从中获取此 FilterRegistration 的 ServletContext
     * 的任何<i>已声明</i>过滤器映射之后或之前进行匹配。
     *
     * <p>
     * 如果多次调用此方法，每次后续调用都会累加到前一次调用的效果上。
     *
     * @param dispatcherTypes 过滤器映射的调度器类型，如果使用默认的 <tt>DispatcherType.REQUEST</tt> 则为 null
     * @param isMatchAfter    如果为 true，则表示给定的过滤器映射应在任何已声明的过滤器映射之后进行匹配；
     *                        如果为 false，则表示应在从中获取此 FilterRegistration 的 ServletContext 的
     *                        任何已声明过滤器映射之前进行匹配
     * @param servletNames    过滤器映射的 Servlet 名称
     * @throws IllegalArgumentException 如果 <tt>servletNames</tt> 为 null 或空
     * @throws IllegalStateException    如果从中获取此 FilterRegistration 的 ServletContext 已被初始化
     */
    public void addMappingForServletNames(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter,
            String... servletNames);

    /**
     * 获取由此 <code>FilterRegistration</code> 表示的过滤器当前可用的 servlet 名称映射。
     *
     * <p>如果允许，对返回的 <code>Collection</code> 的任何更改不得影响此 <code>FilterRegistration</code>。
     *
     * @return 一个（可能为空的）<code>Collection</code>，包含由此 <code>FilterRegistration</code> 表示的过滤器
     *         当前可用的 servlet 名称映射
     */
    public Collection<String> getServletNameMappings();

    /**
     * 为此 FilterRegistration 所代表的过滤器添加具有指定 URL 模式和调度器类型的过滤器映射。
     *
     * <p>过滤器映射按照添加的顺序进行匹配。
     *
     * <p>
     * 根据 <tt>isMatchAfter</tt> 参数的值，给定的过滤器映射将在从中获取此 FilterRegistration 的 ServletContext
     * 的任何<i>已声明</i>过滤器映射之后或之前进行匹配。
     *
     * <p>如果多次调用此方法，每次后续调用都会累加到前一次调用的效果上。
     *
     * @param dispatcherTypes 过滤器映射的调度器类型，如果使用默认的 <tt>DispatcherType.REQUEST</tt> 则为 null
     * @param isMatchAfter    如果为 true，则表示给定的过滤器映射应在任何已声明的过滤器映射之后进行匹配；
     *                        如果为 false，则表示应在从中获取此 FilterRegistration 的 ServletContext 的
     *                        任何已声明过滤器映射之前进行匹配
     * @param urlPatterns     过滤器映射的 URL 模式
     * @throws IllegalArgumentException 如果 <tt>urlPatterns</tt> 为 null 或空
     * @throws IllegalStateException    如果从中获取此 FilterRegistration 的 ServletContext 已被初始化
     */
    public void addMappingForUrlPatterns(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter,
            String... urlPatterns);

    /**
     * 获取由此 <code>FilterRegistration</code> 表示的过滤器当前可用的 URL 模式映射。
     *
     * <p>如果允许，对返回的 <code>Collection</code> 的任何更改不得影响此 <code>FilterRegistration</code>。
     *
     * @return 一个（可能为空的）<code>Collection</code>，包含由此 <code>FilterRegistration</code> 表示的过滤器
     *         当前可用的 URL 模式映射
     */
    public Collection<String> getUrlPatternMappings();

    /**
     * 通过该接口可对通过 {@link ServletContext} 的某个 <tt>addFilter</tt> 方法注册的 {@link Filter} 进行进一步配置。
     */
    interface Dynamic extends FilterRegistration, Registration.Dynamic {
    }
}
