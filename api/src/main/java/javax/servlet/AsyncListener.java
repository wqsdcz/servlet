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

import java.io.IOException;
import java.util.EventListener;

/**
 * 当添加到ServletRequest的异步操作完成、超时或发生错误时，将被通知的监听器。
 * @since Servlet 3.0
 */
public interface AsyncListener extends EventListener {

    /**
     * 通知此异步监听器：一个异步操作已完成。
     *
     * <p>
     * 可通过调用给定<tt>event</tt>上的{@link AsyncEvent#getAsyncContext getAsyncContext}方法，
     * 获取与已完成的异步操作对应的{@link AsyncContext}。
     *
     * <p>
     * 此外，如果此异步监听器是通过调用
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}注册的，
     * 可分别通过调用给定<tt>event</tt>上的{@link AsyncEvent#getSuppliedRequest getSuppliedRequest}和
     * {@link AsyncEvent#getSuppliedResponse getSuppliedResponse}方法获取提供的ServletRequest和ServletResponse对象。
     *
     * @param event 表示异步操作已完成的AsyncEvent事件
     *
     * @throws IOException 如果在处理给定AsyncEvent期间发生与I/O相关的错误
     */
    public void onComplete(AsyncEvent event) throws IOException;

    /**
     * 通知此异步监听器：异步操作已超时。
     *
     * <p>
     * 可通过调用给定<tt>event</tt>上的{@link AsyncEvent#getAsyncContext getAsyncContext}方法，
     * 获取与已超时的异步操作对应的{@link AsyncContext}。
     *
     * <p>
     * 此外，如果此异步监听器是通过调用
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}注册的，
     * 可分别通过调用给定<tt>event</tt>上的{@link AsyncEvent#getSuppliedRequest getSuppliedRequest}和
     * {@link AsyncEvent#getSuppliedResponse getSuppliedResponse}方法获取提供的ServletRequest和ServletResponse对象。
     *
     * @param event 表示异步操作已超时的AsyncEvent事件
     *
     * @throws IOException 如果在处理给定AsyncEvent期间发生与I/O相关的错误
     */
    public void onTimeout(AsyncEvent event) throws IOException;

    /**
     * 通知此 AsyncListener 异步操作未能完成。
     *
     * <p>
     * 可通过在给定 <tt>event</tt> 上调用 {@link AsyncEvent#getAsyncContext getAsyncContext} 方法，
     * 获取与未能完成的异步操作对应的 {@link AsyncContext}。
     *
     * <p>
     * 此外，如果此 AsyncListener 是通过调用
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)} 方法注册的，
     * 则可通过在给定 <tt>event</tt> 上分别调用 {@link AsyncEvent#getSuppliedRequest getSuppliedRequest} 和
     * {@link AsyncEvent#getSuppliedResponse getSuppliedResponse} 方法，获取提供的 ServletRequest 和
     * ServletResponse 对象。
     *
     * @param event 表示异步操作未能完成的 AsyncEvent
     *
     * @throws IOException 如果在处理给定 AsyncEvent 期间发生与 I/O 相关的错误
     */
    public void onError(AsyncEvent event) throws IOException;

    /**
     * 通知此异步监听器：正在通过调用{@link ServletRequest#startAsync}方法之一启动新的异步周期。
     *
     * <p>
     * 可通过调用给定<tt>event</tt>上的{@link AsyncEvent#getAsyncContext getAsyncContext}方法，
     * 获取与正在重新初始化的异步操作对应的{@link AsyncContext}。
     *
     * <p>
     * 此外，如果此异步监听器是通过调用
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}注册的，
     * 可分别通过调用给定<tt>event</tt>上的{@link AsyncEvent#getSuppliedRequest getSuppliedRequest}和
     * {@link AsyncEvent#getSuppliedResponse getSuppliedResponse}方法获取提供的ServletRequest和ServletResponse对象。
     *
     * <p>
     * 此异步监听器将不会接收到与新异步周期相关的任何事件，除非它通过调用{@link AsyncContext#addListener}方法，
     * 向作为给定AsyncEvent一部分传递的AsyncContext重新注册自身。
     *
     * @param event 表示正在启动新异步周期的AsyncEvent事件
     *
     * @throws IOException 如果在处理给定AsyncEvent期间发生与I/O相关的错误
     */
    public void onStartAsync(AsyncEvent event) throws IOException;

}
