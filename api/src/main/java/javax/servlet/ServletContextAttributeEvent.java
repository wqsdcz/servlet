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

/**
 * 用于通知有关 Web 应用程序的 ServletContext 属性变更情况的事件类。
 *
 * @see ServletContextAttributeListener
 * @since Servlet 2.3
 */
public class ServletContextAttributeEvent extends ServletContextEvent {

    private static final long serialVersionUID = -5804680734245618303L;

    private String name;
    private Object value;

    /**
     * 根据给定的 ServletContext、属性名称和属性值构造一个 ServletContextAttributeEvent。
     *
     * @param source 属性发生变化的 ServletContext
     * @param name   发生变化的 ServletContext 属性名称
     * @param value  发生变化的 ServletContext 属性值
     */
    public ServletContextAttributeEvent(ServletContext source, String name, Object value) {
        super(source);
        this.name = name;
        this.value = value;
    }

    /**
     * 获取发生变化的 ServletContext 属性名称。
     *
     * @return 发生变化的 ServletContext 属性名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 获取发生变化的 ServletContext 的属性值。
     *
     * <p>
     *     如果是添加属性，此为属性的值。
     *     如果是移除属性，此为被移除属性的值。
     *     如果是替换属性，此为属性的旧值。
     *
     * @return 发生变化的 ServletContext 属性值
     */
    public Object getValue() {
        return this.value;
    }
}
