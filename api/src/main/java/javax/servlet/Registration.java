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

import java.util.Map;
import java.util.Set;

/**
 * 通过该接口可对 {@link Servlet} 或 {@link Filter} 进行进一步配置。
 *
 * <p>
 * 当其 {@link #getClassName} 方法返回 null 的注册对象被视为<i>初步</i>注册。
 * 实现类特定于容器实现的 Servlets 和 Filters 可以分别在没有 <tt>servlet-class</tt> 或 <tt>filter-class</tt>
 * 元素的情况下声明，并将表示为初步注册对象。必须通过调用 {@link ServletContext} 上的某个 <tt>addServlet</tt>
 * 或 <tt>addFilter</tt> 方法，并分别传入 Servlet 或 Filter 名称（通过 {@link #getName} 获取）以及相应的
 * Servlet 或 Filter 实现类名称、Class 对象或实例来完成初步注册。在大多数情况下，初步注册将由适当的容器提供的
 * {@link ServletContainerInitializer} 完成。
 *
 * @since Servlet 3.0
 */
public interface Registration {

    /**
     * 获取此注册对象所代表的 Servlet 或 Filter 的名称。
     *
     * @return 此注册对象所代表的 Servlet 或 Filter 的名称
     */
    public String getName();

    /**
     * 获取此注册对象所代表的 Servlet 或 Filter 的完全限定类名。
     *
     * @return 此注册对象所代表的 Servlet 或 Filter 的完全限定类名，如果此注册是初步的，则返回 null
     */
    public String getClassName();

    /**
     * 在此注册对象所代表的 Servlet 或 Filter 上设置具有指定名称和值的初始化参数。
     *
     * @param name  初始化参数名称
     * @param value 初始化参数值
     * @return 如果更新成功（即此注册对象所代表的 Servlet 或 Filter 中尚未存在具有指定名称的初始化参数）则返回 true，
     *         否则返回 false
     * @throws IllegalStateException    如果从中获取此注册对象的 ServletContext 已被初始化
     * @throws IllegalArgumentException 如果给定的名称或值为 <tt>null</tt>
     */
    public boolean setInitParameter(String name, String value);

    /**
     * 获取用于初始化此注册对象所代表的 Servlet 或 Filter 的、具有指定名称的初始化参数值。
     *
     * @param name 要获取值的初始化参数名称
     * @return 具有指定名称的初始化参数值，如果不存在该名称的初始化参数，则返回 <tt>null</tt>
     */
    public String getInitParameter(String name);

    /**
     * 在此注册对象所代表的 Servlet 或 Filter 上设置给定的初始化参数。
     *
     * <p>
     * 给定的初始化参数映射按<i>值传递</i>方式处理，即对于映射中包含的每个初始化参数，
     * 此方法调用 {@link #setInitParameter(String,String)}。如果该方法对给定映射中的任何
     * 初始化参数返回 false，则不执行任何更新操作，并返回 false。同样，如果映射包含名称或值
     * 为 <tt>null</tt> 的初始化参数，也不执行任何更新操作，并抛出 IllegalArgumentException。
     *
     * <p>返回的集合不受 {@code Registration} 对象支持，因此返回集合中的更改不会反映在{@code Registration} 对象中，反之亦然。</p>
     *
     * @param initParameters 初始化参数映射
     * @return 存在冲突的初始化参数名称的（可能为空的）Set
     * @throws IllegalStateException    如果从中获取此注册对象的 ServletContext 已被初始化
     * @throws IllegalArgumentException 如果给定映射包含名称或值为 <tt>null</tt> 的初始化参数
     */
    public Set<String> setInitParameters(Map<String, String> initParameters);

    /**
     * 获取一个不可变的（可能为空的）Map，其中包含将用于初始化此注册对象所代表的 Servlet 或 Filter 的当前可用初始化参数。
     *
     * @return 包含将用于初始化此注册对象所代表的 Servlet 或 Filter 的当前可用初始化参数的 Map
     */
    public Map<String, String> getInitParameters();

    /**
     * 通过该接口可对分别通过 {@link ServletContext} 的 <tt>addServlet</tt> 或 <tt>addFilter</tt> 方法
     * 注册的 {@link Servlet} 或 {@link Filter} 进行进一步配置。
     */
    interface Dynamic extends Registration {

        /**
         * 配置此动态注册对象所代表的 Servlet 或 Filter 是否支持异步操作。
         *
         * <p>默认情况下，servlet 和 filter 不支持异步操作。
         *
         * <p>调用此方法将覆盖之前的任何设置。
         *
         * @param isAsyncSupported 如果此动态注册对象所代表的 Servlet 或 Filter 支持异步操作，则为 true；否则为 false
         * @throws IllegalStateException 如果从中获取此动态注册对象的 ServletContext 已被初始化
         */
        public void setAsyncSupported(boolean isAsyncSupported);
    }
}
