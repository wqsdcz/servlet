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
 * 回调通知机制，向开发者发出可以无阻塞写入内容的信号。
 *
 * @since Servlet 3.1
 */
public interface WriteListener extends EventListener {

    /**
     * 当 WriteListener 实例注册到 {@link ServletOutputStream} 时，容器将在首次可以写入数据时调用此方法。
     * 随后，只有当调用 {@link javax.servlet.ServletOutputStream#isReady()}方法返回 <code>false</code> 值后，
     * 写入操作又变为可行时，容器才会再次调用此方法。
     *
     * @throws IOException 如果在处理过程中发生与 I/O 相关的错误
     */
    public void onWritePossible() throws IOException;

    /**
     * 在使用非阻塞API写入数据发生错误时调用。
     *
     * @param t 表示写入操作失败原因的异常对象
     */
    public void onError(final Throwable t);

}
