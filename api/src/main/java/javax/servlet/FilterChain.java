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

import java.io.IOException;

/**
 * FilterChain 是由 Servlet 容器提供给开发者的对象，用于展示针对资源的过滤请求调用链视图。
 * 过滤器使用 FilterChain 来调用链中的下一个过滤器，若当前过滤器为链中的最后一个过滤器，则调用链末端的资源。
 *
 * @see Filter
 * @since Servlet 2.3
 */
public interface FilterChain {

    /**
     * 触发调用链中的下一个过滤器被调用，若当前过滤器为链中的最后一个过滤器，则触发链末端的资源被调用。
     *
     * @param request  沿传递链传递的请求
     * @param response 沿传递链传递的响应
     * @throws IOException      如果在处理过程中发生与I/O相关的错误
     * @throws ServletException 如果发生影响过滤链正常操作的异常
     */
    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException;

}
