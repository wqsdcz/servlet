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

package javax.servlet.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation that may be specified on a {@link javax.servlet.Servlet} class, indicating that instances of the
 * <tt>Servlet</tt> expect requests that conform to the <tt>multipart/form-data</tt> MIME type.
 *
 * <p>
 * Servlets annotated with <tt>MultipartConfig</tt> may retrieve the {@link javax.servlet.http.Part} components of a
 * given <tt>multipart/form-data</tt> request by calling {@link javax.servlet.http.HttpServletRequest#getPart getPart}
 * or {@link javax.servlet.http.HttpServletRequest#getParts getParts}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MultipartConfig {

    /**
     * 用于存储文件的目录位置
     *
     * @return 用于存储文件的目录位置
     */
    String location() default "";

    /**
     * 上传文件允许的最大大小。
     * 
     * <p>
     * 默认值是<tt>-1L</tt>，这意味着无限制
     *
     * @return 上传文件允许的最大大小
     */
    long maxFileSize() default -1L;

    /**
     * <tt>multipart/form-data</tt>请求允许的最大大小
     *
     * <p>
     * 默认值是<tt>-1L</tt>，这意味着无限制
     *
     * @return <tt>multipart/form-data</tt>请求允许的最大大小
     */
    long maxRequestSize() default -1L;

    /**
     * 达到该阈值大小后，文件将被写入磁盘。
     *
     * @return 达到该阈值大小后，文件将被写入磁盘。
     */
    int fileSizeThreshold() default 0;
}
