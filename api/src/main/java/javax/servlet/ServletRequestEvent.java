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
 * 此类事件表示 ServletRequest 的生命周期事件。
 * 事件源为此 Web 应用程序的 ServletContext。
 *
 * @see ServletRequestListener
 * @since Servlet 2.4
 */
public class ServletRequestEvent extends java.util.EventObject {

    private static final long serialVersionUID = -7467864054698729101L;

    private final transient ServletRequest request;

    /**
     * 为给定的 ServletContext 和 ServletRequest 构造一个 ServletRequestEvent。
     *
     * @param sc      当前 Web 应用程序的 ServletContext
     * @param request 发送事件的 ServletRequest
     */
    public ServletRequestEvent(ServletContext sc, ServletRequest request) {
        super(sc);
        this.request = request;
    }

    /**
     * 返回正在发生变化的 ServletRequest。
     *
     * @return 与此事件对应的 {@link ServletRequest}
     */
    public ServletRequest getServletRequest() {
        return this.request;
    }

    /**
     * 返回当前 Web 应用程序的 ServletContext。
     *
     * @return 当前 Web 应用程序的 {@link ServletContext}
     */
    public ServletContext getServletContext() {
        return (ServletContext) super.getSource();
    }
}
