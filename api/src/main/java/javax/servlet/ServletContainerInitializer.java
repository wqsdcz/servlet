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

import java.util.Set;

/**
 * 该接口允许库/运行时环境接收Web应用启动阶段的通知，并据此执行所需的Servlet、Filter及监听器的程序化注册操作。
 *
 * <p>
 *     此接口的实现类可以用{@link javax.servlet.annotation.HandlesTypes}注解进行修饰，
 *     以便在其{@link #onStartup}方法中接收实现了指定类型、继承了指定类型或被指定类型注解修饰的应用类集合。
 *
 * <p>
 *     若此接口的实现类未使用<tt>HandlesTypes</tt>注解，或没有任何应用类符合注解所指定的类型要求，
 *     则容器必须向{@link #onStartup}方法传递一个<tt>null</tt>类集合。
 *
 * <p>
 *     当容器检查应用类是否符合<tt>ServletContainerInitializer</tt>的<tt>HandlesTypes</tt>注解所设定的条件时，
 *     如果应用缺失某些可选JAR文件，可能会遇到类加载问题。由于容器无法判断这类类加载失败是否会影响应用正常运行，
 *     所以容器虽然会选择忽略类加载失败的问题继续启动，但也会提供需记录相关失败的日志的配置选项。
 *
 * <p>
 *     此接口的实现类必须通过位于<tt>META-INF/services</tt>目录下的JAR文件资源进行声明，
 *     该资源需以本接口的全限定类名命名，并将通过运行时的服务提供者查找机制或容器特有的语义等效机制被发现。
 *     无论采用哪种方式，从绝对排序中排除的Web片段JAR文件中的<tt>ServletContainerInitializer</tt>服务必须被忽略，
 *     且这些服务的发现顺序必须遵循应用的类加载委托模型。
 *
 * @see javax.servlet.annotation.HandlesTypes
 *
 * @since Servlet 3.0
 */
public interface ServletContainerInitializer {

    /**
     * 通知此 <tt>ServletContainerInitializer</tt> 由给定 <tt>ServletContext</tt> 所代表的应用程序已启动。
     *
     * <p>
     *     若此 <tt>ServletContainerInitializer</tt> 被置于应用程序 <tt>WEB-INF/lib</tt> 目录下的 JAR 文件中，
     *     则其 <tt>onStartup</tt> 方法仅在捆绑应用程序启动时调用一次。
     *     若此 <tt>ServletContainerInitializer</tt>被置于任何 <tt>WEB-INF/lib</tt> 目录之外的 JAR 文件中（但仍可通过上述机制被发现），
     *     则每次应用程序启动时都会调用其 <tt>onStartup</tt> 方法。
     *
     * @param c   扩展、实现或被 {@link javax.servlet.annotation.HandlesTypes HandlesTypes} 注解所指定类型标注的应用程序类集合；
     *            若不存在匹配项，或此 <tt>ServletContainerInitializer</tt> 未标注 <tt>HandlesTypes</tt> 注解，则返回 <tt>null</tt>
     * @param ctx 正在启动的 Web 应用的 <tt>ServletContext</tt>，其中包含了在 <tt>c</tt> 中发现的类
     * @throws ServletException 若发生错误时抛出
     */
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException;
}
