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

package javax.servlet.http;

import java.io.*;
import java.util.*;

/**
 * <p>该类表示在<code>multipart/form-data</code>类型的POST请求中接收到的部件或表单项。
 * 部件的Header（包括ContentType）、
 *
 * @since Servlet 3.0
 */
public interface Part {

    /**
     * 以<tt>InputStream</tt>形式获取此部件的内容
     *
     * @return 此部件内容的<tt>InputStream</tt>输入流
     * @throws IOException 如果以<tt>InputStream</tt>形式检索内容时发生错误
     */
    public InputStream getInputStream() throws IOException;

    /**
     * 获取此部件的内容类型
     *
     * @return 此部件的内容类型
     */
    public String getContentType();

    /**
     * 获取此部件的名称
     *
     * @return 此部件的名称，以<tt>String</tt>形式返回
     */
    public String getName();

    /**
     * 获取客户端指定的文件名
     *
     * @return 客户端提交的文件名
     * @since Servlet 3.1
     */
    public String getSubmittedFileName();

    /**
     * 返回此文件的大小。
     *
     * @return 指定此部件大小的<code>long</code>值，单位为字节。
     */
    public long getSize();

    /**
     * 一个便捷方法，用于将此上传的项目写入磁盘。
     *
     * <p>
     *     如果对同一部件多次调用此方法，不能保证一定会成功。
     *     这允许特定的实现尽可能使用文件重命名等操作，而不是复制所有底层数据，从而获得显著的性能优势。
     *
     * @param fileName 上传部件应存储的位置。该值可以是文件名或路径。
     *                 文件在文件系统中的实际位置相对于{@link javax.servlet.MultipartConfigElement#getLocation()}。
     *                 绝对路径按原样使用，并相对于<code>getLocation()</code>。
     *                 注意：这是一个系统相关的字符串，URI表示法可能并非在所有系统上都可用。
     *                 为了可移植性，应使用File或Path API生成此字符串。
     *
     * @throws IOException 如果发生错误。
     */
    public void write(String fileName) throws IOException;

    /**
     * 删除文件项的底层存储，包括删除任何关联的临时磁盘文件。
     *
     * @throws IOException 如果发生错误。
     */
    public void delete() throws IOException;

    /**
     * 以<code>String</code>形式返回指定MIME头的值。
     * 如果该部件未包含指定名称的头，则此方法返回<code>null</code>。
     * 如果存在多个同名头部，此方法返回部件中的第一个头。
     * 头部名称不区分大小写。此方法可用于任何请求头。
     *
     * @param name 指定头名称的<code>String</code>
     * @return 包含请求头值的<code>String</code>，如果该部件没有该名称的头则返回<code>null</code>
     */
    public String getHeader(String name);

    /**
     * 获取具有给定名称的部件头对应的所有值。
     * <p>对返回的<code>Collection</code>的任何修改不得影响此<code>Part</code>对象。
     * <p>部件头名称不区分大小写。
     *
     * @param name 要返回值的头名称
     * @return 具有给定名称的头对应的值组成的（可能为空的）<code>Collection</code>集合
     */
    public Collection<String> getHeaders(String name);

    /**
     * 获取此部件的头名称集合。
     * <p>某些servlet容器不允许servlet使用此方法访问头部，在这种情况下，此方法返回<code>null</code>。
     * <p>对返回的<code>Collection</code>的任何修改不得影响此<code>Part</code>对象。
     *
     * @return 此部件的头名称组成的（可能为空的）<code>Collection</code>集合
     */
    public Collection<String> getHeaderNames();

}
