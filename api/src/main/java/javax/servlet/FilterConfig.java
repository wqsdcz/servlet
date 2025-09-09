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
 * 过滤器配置对象，由 Servlet 容器在初始化期间用于向过滤器传递信息。
 *
 * @see Filter
 * @since Servlet 2.3
 */
public interface FilterConfig {

    /**
     * 返回部署描述符中定义的此过滤器的过滤器名称。
     *
     * @return 此过滤器的名称
     */
    public String getFilterName();

    /**
     * 返回调用者正在执行的 {@link ServletContext} 的引用。
     *
     * @return 一个 {@link ServletContext} 对象，调用者使用该对象与其 servlet 容器进行交互
     * @see ServletContext
     */
    public ServletContext getServletContext();

    /**
     * 返回包含指定初始化参数值的 <code>String</code>，如果该初始化参数不存在，则返回 <code>null</code>。
     *
     * @param name 指定初始化参数名称的 <code>String</code>
     * @return 包含初始化参数值的 <code>String</code>，如果初始化参数不存在，则返回 <code>null</code>
     */
    public String getInitParameter(String name);

    /**
     * 以<code>String</code>对象的<code>Enumeration</code>形式返回过滤器的初始化参数名称，
     * 如果过滤器没有初始化参数，则返回空的<code>Enumeration</code>。
     *
     * @return 包含过滤器初始化参数名称的<code>String</code>对象的<code>Enumeration</code>
     */
    public Enumeration<String> getInitParameterNames();

}
