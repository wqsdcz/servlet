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
 * 组成：名称、类型名、初始化参数、异步支持、映射URL、runAs角色、启动时机、安全、多部份请求的处理配置
 * 用于进一步配置 {@link Servlet} 的接口。
 *
 * @since Servlet 3.0
 */
public interface ServletRegistration extends Registration {

    /**
     * 为此 ServletRegistration 所代表的 Servlet 添加具有指定 URL 模式的 servlet 映射。
     *
     * <p>如果任何指定的 URL 模式已映射到其他 Servlet，则不执行任何更新操作。
     *
     * <p>如果多次调用此方法，每次后续调用都会累加到前一次调用的效果上。
     *
     * <p>返回的集合并非由 {@code ServletRegistration} 对象所支持，因此返回集合中的任何更改都不会反映在 {@code ServletRegistration} 对象中，反之亦然。</p>
     *
     * @param urlPatterns servlet 映射的 URL 模式
     *
     * @return 已映射到其他 Servlet 的 URL 模式的（可能为空的）Set 集合
     *
     * @throws IllegalArgumentException 如果 <tt>urlPatterns</tt> 为 null 或空
     * @throws IllegalStateException    如果从中获取此 ServletRegistration 的 ServletContext 已被初始化
     */
    public Set<String> addMapping(String... urlPatterns);

    /**
     * 获取由此 <code>ServletRegistration</code> 表示的 Servlet 当前可用的映射。
     *
     * <p>如果允许，对返回的 <code>Collection</code> 的任何更改不得影响此 <code>ServletRegistration</code>。
     *
     * @return 一个（可能为空的）<code>Collection</code>，包含由此 <code>ServletRegistration</code> 表示的
     *         Servlet 当前可用的映射
     */
    public Collection<String> getMappings();

    /**
     * 获取由此 <code>ServletRegistration</code> 表示的 Servlet 的 runAs 角色名称。
     *
     * @return runAs 角色名称，如果该 Servlet 配置为以其调用者身份运行，则返回 null
     */
    public String getRunAsRole();

    /**
     * 通过该接口可对通过 {@link ServletContext} 的 <tt>addServlet</tt> 方法注册的 {@link Servlet} 进行进一步配置。
     */
    interface Dynamic extends ServletRegistration, Registration.Dynamic {

        /**
         * 在此动态 ServletRegistration 所代表的 Servlet 上设置 <code>loadOnStartup</code> 优先级。
         *
         * <p>
         *     大于或等于零的 <tt>loadOnStartup</tt> 值向容器指示 Servlet 的初始化优先级。
         *     在这种情况下，容器必须在 ServletContext 的初始化阶段（即在调用为 ServletContext 配置的
         *     所有ServletContextListener 对象的 {@link ServletContextListener#contextInitialized} 方法之后）
         *     实例化并初始化该 Servlet。
         * <p>
         *     如果 <tt>loadOnStartup</tt> 是负整数，容器可以延迟实例化和初始化该 Servlet。
         * <p>
         *     <tt>loadOnStartup</tt> 的默认值为 <code>-1</code>。
         * <p>
         *     调用此方法将覆盖之前的任何设置。
         *
         * @param loadOnStartup Servlet 的初始化优先级
         * @throws IllegalStateException 如果从中获取此 ServletRegistration 的 ServletContext 已被初始化
         */
        public void setLoadOnStartup(int loadOnStartup);

        /**
         * 设置要应用于为此 <code>ServletRegistration</code> 定义的映射的 {@link ServletSecurityElement}。
         *
         * <p>
         *     此方法适用于添加到该 <code>ServletRegistration</code> 的所有映射，
         *     直到获取它的 <code>ServletContext</code> 被初始化为止。
         *
         * <p>
         *     如果此 ServletRegistration 的 URL 模式是通过便携式部署描述符建立的 <code>security-constraint</code> 的精确目标，
         *     则此方法不会更改该模式的 <code>security-constraint</code>，并且该模式将包含在返回值中。
         *
         * <p>
         *     如果此 ServletRegistration 的 URL 模式是通过 {@link javax.servlet.annotation.ServletSecurity} 注解
         *     或先前对此方法的调用建立的安全约束的精确目标，则此方法将替换该模式的安全约束。
         *
         * <p>
         *     如果此 ServletRegistration 的 URL 模式既不是通过 {@link javax.servlet.annotation.ServletSecurity} 注解
         *     或先前对此方法的调用建立的安全约束的精确目标，也不是便携式部署描述符中 <code>security-constraint</code> 的精确目标，
         *     则此方法将从参数 <code>ServletSecurityElement</code> 为该模式建立安全约束。
         *
         * <p>
         *     返回的集合不受 {@code Dynamic} 对象支持，因此返回集合中的更改不会反映在 {@code Dynamic} 对象中，反之亦然。
         *
         * @param constraint 要应用于映射到此 ServletRegistration 的模式的 {@link ServletSecurityElement}
         * @return （可能为空的）URL 模式集合，这些模式已经是通过便携式部署描述符建立的
         *         <code>security-constraint</code> 的精确目标。此方法对返回集中包含的模式没有影响
         * @throws IllegalArgumentException 如果 <tt>constraint</tt> 为 null
         * @throws IllegalStateException    如果获取此 <code>ServletRegistration</code> 的 {@link ServletContext} 已被初始化
         */
        public Set<String> setServletSecurity(ServletSecurityElement constraint);

        /**
         * 设置要应用于为此 <code>ServletRegistration</code> 定义的映射的 {@link MultipartConfigElement}。
         * 如果多次调用此方法，每次后续调用都会覆盖之前调用的效果。
         *
         * @param multipartConfig 要应用于映射到该注册对象的模式的 {@link MultipartConfigElement}
         * @throws IllegalArgumentException 如果 <tt>multipartConfig</tt> 为 null
         * @throws IllegalStateException    如果从中获取此 ServletRegistration 的 {@link ServletContext} 已被初始化
         */
        public void setMultipartConfig(MultipartConfigElement multipartConfig);

        /**
         * 设置此<code>ServletRegistration</code>的<code>runAs</code>角色名称。
         *
         * @param roleName <code>runAs</code>角色的名称
         * @throws IllegalArgumentException 如果<tt>roleName</tt>参数为null
         * @throws IllegalStateException    如果获取此ServletRegistration的{@link ServletContext} 已被初始化
         */
        public void setRunAsRole(String roleName);

    }

}
