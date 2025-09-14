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

import java.util.Enumeration;

/**
 * Servlet 配置对象，由 Servlet 容器在初始化期间用于向 Servlet 传递信息。
 */
public interface ServletConfig {

    /**
     * 返回此 Servlet 实例的名称。该名称可能通过服务器管理提供、在 Web 应用部署描述符中指定，
     * 或者对于未注册（因此未命名）的 Servlet 实例，将返回该 Servlet 的类名。
     *
     * @return 此 Servlet 实例的名称
     */
    public String getServletName();

    /**
     * 返回一个指向调用者所在执行环境中的 {@link ServletContext} 的引用。
     *
     * @return 一个 {@link ServletContext} 对象，调用者使用该对象与其 servlet 容器进行交互
     * @see ServletContext
     */
    public ServletContext getServletContext();

    /**
     * 获取指定名称的初始化参数的值。
     *
     * @param name 要获取值的初始化参数名称
     * @return 包含初始化参数值的 <code>String</code>，如果该初始化参数不存在，则返回 <code>null</code>
     */
    public String getInitParameter(String name);

    /**
     * 返回该 servlet 的初始化参数的名称，其形式为一个由 <code>String</code> 对象组成的 <code>Enumeration</code> 对象；
     * 如果该 servlet 没有初始化参数，则返回一个空的 <code>Enumeration</code> 对象。
     *
     * @return 包含servlet初始化参数名称的<code>String</code>对象的<code>Enumeration</code>
     */
    public Enumeration<String> getInitParameterNames();

}
