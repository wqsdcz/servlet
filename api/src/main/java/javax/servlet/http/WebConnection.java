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

package javax.servlet.http;

import java.io.IOException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;

/**
 * 此接口封装了升级请求的连接。
 * 它允许协议处理程序向容器发送服务请求和状态查询。
 *
 * @since Servlet 3.1
 */
public interface WebConnection extends AutoCloseable {

    /**
     * 返回此Web连接的输入流。
     *
     * @return 用于读取二进制数据的ServletInputStream
     * @exception IOException 如果发生I/O错误
     */
    public ServletInputStream getInputStream() throws IOException;

    /**
     * 返回此Web连接的输出流。
     *
     * @return 用于写入二进制数据的ServletOutputStream
     * @exception IOException 如果发生I/O错误
     */
    public ServletOutputStream getOutputStream() throws IOException;
}
