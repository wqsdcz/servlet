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

import java.io.CharConversionException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * 提供一个用于向客户端发送二进制数据的输出流。
 * 通常通过{@link ServletResponse#getOutputStream}方法获取<code>ServletOutputStream</code>对象。
 *
 * <p>
 *     这是一个由servlet容器实现的抽象类。此类的子类必须实现<code>java.io.OutputStream.write(int)</code>方法。
 *
 * @author Various
 *
 * @see ServletResponse
 *
 */
public abstract class ServletOutputStream extends OutputStream {

    private static final String LSTRING_FILE = "javax.servlet.LocalStrings";
    private static ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    /**
     * 什么都不做，因为这是一个抽象类。
     */
    protected ServletOutputStream() {
    }

    /**
     * 向客户端写入一个<code>String</code>值，末尾不附带回车换行(CRLF)。
     *
     * @param s 要发送给客户端的<code>String</code>值
     * @exception IOException 如果发生输入或输出异常
     */
    public void print(String s) throws IOException {
        if (s == null)
            s = "null";
        int len = s.length();
        byte[] out = new byte[len];
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);

            //
            // XXX NOTE: This is clearly incorrect for many strings,
            // but is the only consistent approach within the current
            // servlet framework. It must suffice until servlet output
            // streams properly encode their output.
            //
            if ((c & 0xff00) != 0) { // high order byte must be zero
                String errMsg = lStrings.getString("err.not_iso8859_1");
                Object[] errArgs = new Object[1];
                errArgs[0] = Character.valueOf(c);
                errMsg = MessageFormat.format(errMsg, errArgs);
                throw new CharConversionException(errMsg);
            }
            out[i] = (byte) (0xff & c);
        }
        write(out,0,len);
    }

    /**
     * 向客户端写入一个<code>boolean</code>值，末尾不附带回车换行(CRLF)。
     *
     * @param b 要发送给客户端的<code>boolean</code>值
     * @exception IOException 如果发生输入或输出异常
     */
    public void print(boolean b) throws IOException {
        print(lStrings.getString(b ? "value.true" : "value.false"));
    }

    /**
     * 向客户端写入一个<code>char</code>值，末尾不附带回车换行(CRLF)。
     *
     * @param c 要发送给客户端的<code>char</code>值
     * @exception IOException 如果发生输入或输出异常
     */
    public void print(char c) throws IOException {
        print(String.valueOf(c));
    }

    /**
     * 向客户端写入一个<code>int</code>值，末尾不附带回车换行(CRLF)。
     *
     * @param i 要发送给客户端的<code>int</code>值
     * @exception IOException 如果发生输入或输出异常
     *
     */
    public void print(int i) throws IOException {
        print(String.valueOf(i));
    }

    /**
     * 向客户端写入一个<code>long</code>值，末尾不附带回车换行(CRLF)。
     *
     * @param l 要发送给客户端的<code>long</code>值
     * @exception IOException 如果发生输入或输出异常
     */
    public void print(long l) throws IOException {
        print(String.valueOf(l));
    }

    /**
     * 向客户端写入一个<code>float</code>值，末尾不附带回车换行(CRLF)。
     *
     * @param f 要发送给客户端的<code>float</code>值
     * @exception IOException 如果发生输入或输出异常
     */
    public void print(float f) throws IOException {
        print(String.valueOf(f));
    }

    /**
     * 向客户端写入一个<code>double</code>值，末尾不附带回车换行(CRLF)。
     *
     * @param d 要发送给客户端的<code>double</code>值
     * @exception IOException 如果发生输入或输出异常
     */
    public void print(double d) throws IOException {
        print(String.valueOf(d));
    }

    /**
     * 向客户端写入一个回车换行符（CRLF）。
     *
     * @exception IOException 如果发生输入或输出异常
     */
    public void println() throws IOException {
        print("\r\n");
    }

    /**
     * 向客户端写入一个<code>String</code>字符串，后跟一个回车换行符(CRLF)。
     *
     * @param s 要写入客户端的<code>String</code>字符串
     * @exception IOException 如果发生输入或输出异常
     */
    public void println(String s) throws IOException {
        print(s == null ? "null\r\n" : (s + "\r\n"));
    }

    /**
     * 向客户端写入一个<code>boolean</code>布尔值，后跟一个回车换行符(CRLF)。
     *
     * @param b 要写入客户端的<code>boolean</code>布尔值
     * @exception IOException 如果发生输入或输出异常
     */
    public void println(boolean b) throws IOException {
        println(lStrings.getString(b ? "value.true" : "value.false"));
    }

    /**
     * 向客户端写入一个字符，后跟一个回车换行符(CRLF)。
     *
     * @param c 要写入客户端的字符
     * @exception IOException 如果发生输入或输出异常
     */
    public void println(char c) throws IOException {
        println(String.valueOf(c));
    }

    /**
     * 向客户端写入一个整数值，后跟一个回车换行符(CRLF)。
     *
     * @param i 要写入客户端的整数值
     * @exception IOException 如果发生输入或输出异常
     */
    public void println(int i) throws IOException {
        println(String.valueOf(i));
    }

    /**
     * 向客户端写入一个<code>long</code>值，后跟一个回车换行符(CRLF)。
     *
     * @param l 要写入客户端的<code>long</code>值
     * @exception IOException 如果发生输入或输出异常
     */
    public void println(long l) throws IOException {
        println(String.valueOf(l));
    }

    /**
     * 向客户端写入一个<code>float</code>值，后跟一个回车换行符(CRLF)。
     *
     * @param f 要写入客户端的<code>float</code>值
     * @exception IOException 如果发生输入或输出异常
     */
    public void println(float f) throws IOException {
        println(String.valueOf(f));
    }

    /**
     * 向客户端写入一个<code>double</code>值，后跟一个回车换行符(CRLF)。
     *
     * @param d 要写入客户端的<code>double</code>值
     * @exception IOException 如果发生输入或输出异常
     */
    public void println(double d) throws IOException {
        println(String.valueOf(d));
    }

    /**
     * 此方法可用于判断是否能在不阻塞的情况下写入数据。
     *
     * @return 如果写入此<code>ServletOutputStream</code>能够成功则返回<code>true</code>，否则返回<code>false</code>。
     * @since Servlet 3.1
     */
    public abstract boolean isReady();

    /**
     * 指示<code>ServletOutputStream</code>在可写入时调用提供的{@link WriteListener}
     *
     * @param writeListener 当可写入时应被通知的{@link WriteListener}
     * @exception IllegalStateException 如果满足以下任一条件：
     *                                  <ul>
     *                                      <li>关联的请求既不是升级请求也不是异步启动的请求
     *                                      <li>在同一请求范围内多次调用setWriteListener方法
     *                                  </ul>
     * @throws NullPointerException 如果writeListener为null
     * @since Servlet 3.1
     */
    public abstract void setWriteListener(WriteListener writeListener);

}
