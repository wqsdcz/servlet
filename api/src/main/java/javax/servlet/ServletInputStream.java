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

import java.io.InputStream;
import java.io.IOException;

/**
 * 提供一个用于从客户端请求读取二进制数据的输入流，包含一个高效的<code>readLine</code>方法用于逐行读取数据。
 * 对于某些协议（如HTTP POST和PUT），可以使用<code>ServletInputStream</code>对象读取客户端发送的数据。
 *
 * <p>
 *     通常通过{@link ServletRequest#getInputStream}方法获取<code>ServletInputStream</code>对象。
 *
 * <p>
 *     这是一个由servlet容器实现的抽象类。此类的子类必须实现<code>java.io.InputStream.read()</code>方法。
 *
 * @author Various
 * @see ServletRequest
 *
 */
public abstract class ServletInputStream extends InputStream {

    /**
     * 什么都不做，因为这是一个抽象类。
     */
    protected ServletInputStream() {
    }

    /**
     * 逐行读取输入流。从指定偏移量开始，将字节读入数组，直到读取指定数量的字节或遇到换行符（换行符也会被读入数组）。
     *
     * <p>
     *     如果在读取最大字节数之前到达输入流末尾，该方法将返回 -1。
     * @param b   要读入数据的字节数组
     * @param off 指定开始读取位置的整数偏移量
     * @param len 指定要读取的最大字节数的整数
     * @return 表示实际读取字节数的整数，如果到达流末尾则返回 -1
     * @exception IOException 如果发生输入或输出异常
     */
    public int readLine(byte[] b, int off, int len) throws IOException {

        if (len <= 0) {
            return 0;
        }
        int count = 0, c;

        while ((c = read()) != -1) {
            b[off++] = (byte) c;
            count++;
            if (c == '\n' || count == len) {
                break;
            }
        }
        return count > 0 ? count : -1;
    }

    /**
     * 当从流中读取所有数据后返回 true，否则返回 false。
     *
     * @return 当此特定请求的所有数据都已读取时返回 <code>true</code>，否则返回 <code>false</code>。
     * @since Servlet 3.1
     */
    public abstract boolean isFinished();

    /**
     * 如果可以在不阻塞的情况下读取数据则返回 true，否则返回 false。
     *
     * @return 如果可以无阻塞地获取数据则返回 <code>true</code>，否则返回 <code>false</code>。
     * @since Servlet 3.1
     */
    public abstract boolean isReady();

    /**
     * 指示<code>ServletInputStream</code>在可读取时调用提供的{@link ReadListener}
     *
     * @param readListener 当可读取时应被通知的{@link ReadListener}
     * @exception IllegalStateException 如果满足以下任一条件：
     *                                  <ul>
     *                                      <li>关联的请求既不是升级请求也不是异步启动的请求
     *                                      <li>在同一请求范围内多次调用setReadListener方法
     *                                  </ul>

     * @throws NullPointerException 如果readListener为null
     * @since Servlet 3.1
     */
    public abstract void setReadListener(ReadListener readListener);
}
