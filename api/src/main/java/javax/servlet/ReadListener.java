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
 * <p>该类代表一种回调机制，当 HTTP 请求数据可非阻塞读取时，会通知实现类。</p>
 *
 * @since Servlet 3.1
 */
public interface ReadListener extends EventListener {

    /**
     * 当 <code>ReadListener</code> 实例注册到 {@link ServletInputStream} 时，
     * 容器将在首次可以读取数据时调用此方法。随后，容器调用此方法的条件是：
     * 只有当 {@link javax.servlet.ServletInputStream#isReady()} 方法被调用且返回 <code>false</code> 值，
     * <em>并且</em>之后又有数据可读时才会触发。
     *
     * @throws IOException 如果在处理过程中发生与 I/O 相关的错误
     */
    public void onDataAvailable() throws IOException;

    /**
     * 当当前请求的所有数据都已读取时调用。
     *
     * @throws IOException 如果在处理过程中发生与I/O相关的错误
     */
    public void onAllDataRead() throws IOException;

    /**
     * 当处理请求过程中发生错误时调用。
     *
     * @param t 表示读取操作失败原因的 throwable 对象
     */
    public void onError(Throwable t);

}
