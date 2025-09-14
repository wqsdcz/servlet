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
 * 这是用于通知应用程序中 servlet 请求属性发生更改的事件类。
 *
 * @see ServletRequestAttributeListener
 * @since Servlet 2.4
 */
public class ServletRequestAttributeEvent extends ServletRequestEvent {

    private static final long serialVersionUID = -1466635426192317793L;

    private String name;
    private Object value;

    /**
     * 通过给定此Web应用程序的servlet上下文、属性正在发生变化的ServletRequest，
     * 以及属性的名称和值，构建一个ServletRequestAttributeEvent。
     *
     * @param sc      发送事件的ServletContext
     * @param request 发送事件的ServletRequest
     * @param name    请求属性的名称
     * @param value   请求属性的值
     */
    public ServletRequestAttributeEvent(ServletContext sc, ServletRequest request, String name, Object value) {
        super(sc, request);
        this.name = name;
        this.value = value;
    }

    /**
     * 返回ServletRequest上发生变化的属性名称。
     *
     * @return 发生变化的请求属性名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 返回已添加、移除或替换的属性值。
     *
     * <p>
     *     如果是添加属性，则此为属性值；
     *     如果是移除属性，则此为被移除属性的值；
     *     如果是替换属性，则此为属性的旧值。
     *
     * @return 发生变化的请求属性值
     */
    public Object getValue() {
        return this.value;
    }
}
