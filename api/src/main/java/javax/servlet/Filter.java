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
 * <p>
 * 过滤器是一种对资源（Servlet 或静态内容）的请求、响应或同时对两者执行过滤任务的对象。
 * </p>
 *
 * <p>
 * 过滤器通过 <code>doFilter</code> 方法执行过滤操作。每个过滤器都可以访问一个 FilterConfig 对象，
 * 从中获取初始化参数，以及一个 ServletContext 引用，可用于加载过滤任务所需的资源。
 *
 * <p>
 * 过滤器在 Web 应用程序的部署描述符中进行配置。
 *
 * <p>
 * 此设计模式已确定的示例包括：
 * <ol>
 * <li>身份验证过滤器
 * <li>日志记录和审计过滤器
 * <li>图像转换过滤器
 * <li>数据压缩过滤器
 * <li>加密过滤器
 * <li>令牌化过滤器
 * <li>触发资源访问事件的过滤器
 * <li>XSL/T 过滤器
 * <li>MIME 类型链过滤器
 * </ol>
 *
 * @since Servlet 2.3
 */
public interface Filter {

    /**
     * <p>由 Web 容器调用，指示过滤器正在被投入使用。</p>
     *
     * <p>Servlet 容器在实例化过滤器后仅调用一次 init 方法。在要求过滤器执行任何过滤工作之前，init 方法必须成功完成。</p>
     *
     * <p>如果 init 方法出现以下任一情况，Web 容器无法将过滤器投入使用：</p>
     * <ol>
     * <li>抛出 ServletException
     * <li>未在 Web 容器定义的时间段内返回
     * </ol>
     *
     * @implSpec 默认实现不执行任何操作。
     * @param filterConfig 包含过滤器配置和初始化参数的 <code>FilterConfig</code> 对象
     * @throws ServletException 如果发生异常干扰过滤器的正常操作
     */
    default public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * 容器每次将请求/响应对传递给过滤链时（由于客户端请求链末端的资源），都会调用过滤器的 <code>doFilter</code> 方法。
     * 传入此方法的 FilterChain 允许过滤器将请求和响应传递给链中的下一个实体。
     *
     * <p>
     * 此方法的典型实现遵循以下模式：
     * <ol>
     * <li>检查请求
     * <li>可选择使用自定义实现包装请求对象，以过滤输入内容或头部信息
     * <li>可选择使用自定义实现包装响应对象，以过滤输出内容或头部信息
     * <li>
     * <ul>
     * <li><strong>要么</strong>使用 FilterChain 对象调用链中的下一个实体（<code>chain.doFilter()</code>），
     * <li><strong>要么</strong>不将请求/响应对传递给过滤链中的下一个实体，以阻断请求处理
     * </ul>
     * <li>在调用过滤链中的下一个实体后，直接在响应上设置头部信息
     * </ol>
     *
     * @param request  包含客户端请求的 <code>ServletRequest</code> 对象
     * @param response 包含过滤器响应的 <code>ServletResponse</code> 对象
     * @param chain    用于调用下一个过滤器或资源的 <code>FilterChain</code> 对象
     * @throws IOException      如果在处理过程中发生与I/O相关的错误
     * @throws ServletException 如果发生影响过滤器正常操作的异常
     *
     * @see UnavailableException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException;

    /**
     * <p>由 Web 容器调用，用于通知过滤器它正在被停用。</p>
     *
     * <p>只有在过滤器 doFilter 方法中的所有线程都已退出或超时时间过后，才会调用此方法。Web 容器调用此方法后，将不会再对此过滤器实例调用 doFilter 方法。</p>
     *
     * <p>此方法为过滤器提供了清理占用的任何资源（例如，内存、文件句柄、线程）的机会，并确保任何持久状态与过滤器在内存中的当前状态保持同步。</p>
     *
     * @implSpec 默认实现不执行任何操作。
     */
    default public void destroy() {
    }
}
