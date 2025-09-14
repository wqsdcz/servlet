/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates and others.
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
 * 当在 ServletRequest 上启动的异步操作（通过调用 {@link ServletRequest#startAsync()}
 * 或 {@link ServletRequest#startAsync(ServletRequest, ServletResponse)}）
 * 已完成、超时或产生错误时触发的事件。
 *
 * @since Servlet 3.0
 */
public class AsyncEvent {

    private AsyncContext context;
    private ServletRequest request;
    private ServletResponse response;
    private Throwable throwable;

    /**
     * 从给定的 AsyncContext 构造一个 AsyncEvent。
     *
     * @param context 要随此 AsyncEvent 传递的 AsyncContext
     */
    public AsyncEvent(AsyncContext context) {
        this(context, context.getRequest(), context.getResponse(), null);
    }

    /**
     * 从给定的 AsyncContext、ServletRequest 和 ServletResponse 构造一个 AsyncEvent。
     *
     * @param context  要随此 AsyncEvent 传递的 AsyncContext
     * @param request  要随此 AsyncEvent 传递的 ServletRequest
     * @param response 要随此 AsyncEvent 传递的 ServletResponse
     */
    public AsyncEvent(AsyncContext context, ServletRequest request, ServletResponse response) {
        this(context, request, response, null);
    }

    /**
     * 从给定的 AsyncContext 和 Throwable 构造一个 AsyncEvent。
     *
     * @param context   要随此 AsyncEvent 传递的 AsyncContext
     * @param throwable 要随此 AsyncEvent 传递的 Throwable
     */
    public AsyncEvent(AsyncContext context, Throwable throwable) {
        this(context, context.getRequest(), context.getResponse(), throwable);
    }

    /**
     * 从给定的 AsyncContext、ServletRequest、ServletResponse 和 Throwable 构造一个 AsyncEvent。
     *
     * @param context   要随此 AsyncEvent 传递的 AsyncContext
     * @param request   要随此 AsyncEvent 传递的 ServletRequest
     * @param response  要随此 AsyncEvent 传递的 ServletResponse
     * @param throwable 要随此 AsyncEvent 传递的 Throwable
     */
    public AsyncEvent(AsyncContext context, ServletRequest request, ServletResponse response, Throwable throwable) {
        this.context = context;
        this.request = request;
        this.response = response;
        this.throwable = throwable;
    }

    /**
     * 从此 AsyncEvent 获取 AsyncContext。
     *
     * @return 用于初始化此 AsyncEvent 的 AsyncContext
     */
    public AsyncContext getAsyncContext() {
        return context;
    }

    /**
     * 从此 AsyncEvent 获取 ServletRequest。
     *
     * <p>
     * 如果此 AsyncEvent 正在传递到的 AsyncListener 是通过
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)} 添加的，
     * 则返回的 ServletRequest 将与提供给上述方法的 ServletRequest 相同。
     * 如果 AsyncListener 是通过 {@link AsyncContext#addListener(AsyncListener)} 添加的，则此方法必须返回 null。
     *
     * @return 用于初始化此 AsyncEvent 的 ServletRequest；如果此 AsyncEvent 初始化时未提供 ServletRequest，则返回 null
     */
    public ServletRequest getSuppliedRequest() {
        return request;
    }

    /**
     * 从此 AsyncEvent 获取 ServletResponse。
     *
     * <p>
     * 如果此 AsyncEvent 正在传递到的 AsyncListener 是通过
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)} 添加的，
     * 则返回的 ServletResponse 将与提供给上述方法的 ServletResponse 相同。
     * 如果 AsyncListener 是通过 {@link AsyncContext#addListener(AsyncListener)} 添加的，则此方法必须返回 null。
     *
     * @return 用于初始化此 AsyncEvent 的 ServletResponse；如果此 AsyncEvent 初始化时未提供 ServletResponse，则返回 null
     */
    public ServletResponse getSuppliedResponse() {
        return response;
    }

    /**
     * 从此 AsyncEvent 获取 Throwable。
     *
     * @return 用于初始化此 AsyncEvent 的 Throwable；如果此 AsyncEvent 初始化时未提供 Throwable，则返回 null
     */
    public Throwable getThrowable() {
        return throwable;
    }

}
