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

/**
 * 过滤器分发器类型的枚举。
 * 转发、包含、请求、异步、错误
 * <p>
 *     {@link #FORWARD}:
 *     指在服务器中，某一内部资源接收到来自其他内部资源传入的请求时，请求所被标记的一种传播类型。
 *     原资源将完全失去请求的控制权，即请求被移交后，原资源将不再对请求做任何处理。
 *     当 Servlet 使用 RequestDispatcher.forward() 方法将请求转发给另一个资源时，被转发到另一个资源的请求将被设置为 FORWARD 类型。
 * </p>
 * <p>
 *     {@link #INCLUDE}:
 *     指在服务器中，某一内部资源接收到来自其他内部资源传入的请求时，请求所被标记的一种传播类型。
 *     原资源将保留请求的控制权，即请求被移交后，在受理请求的资源在处理完成后，原资源可以继续处理请求。
 *     当 Servlet 使用 RequestDispatcher.include() 方法将另一个资源的输出引入到当前响应中时，被传入到待引入资源的请求就属于 INCLUDE 类型。
 * </p>
 * <p>
 *     {@link #REQUEST}:
 *     指直接来自客户端的原始请求（例如，用户在浏览器地址栏输入URL、点击链接、提交表单等）。这是最常见的一种。
 *     在没有其他分发类型的情况下，默认就是 REQUEST。
 * </p>
 * <p>
 *     {@link #ASYNC}:
 *     指在异步处理（Servlet 3.0+ 特性）模式下，从异步上下文（AsyncContext）中分发出来的请求。它不是初始请求的延续，而是一个全新的分发。
 * </p>
 * <p>
 *     {@link #ERROR}:
 *     指当服务器处理请求发生错误（HTTP状态码 >= 400）时，被错误页面机制（通过在 web.xml 中配置 <error-page>）处理的那个请求。
 * </p>
 *
 * @since Servlet 3.0
 */
public enum DispatcherType {
    FORWARD, INCLUDE, REQUEST, ASYNC, ERROR
}
