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

package javax.servlet.descriptor;

/**
 * 这个接口提供了访问web应用中与 <code>&lt;taglib&gt;</code> 相关的配置的入口。
 *
 * <p>这个配置聚集了来自web应用中的<code>web.xml</code> 和 <code>web-fragment.xml</code>描述符文件的内容。
 *
 * @since Servlet 3.0
 */
public interface TaglibDescriptor {

    /**
     * 获取由此TaglibDescriptor实例所表示的标签库的唯一标识符。
     *
     * @return 由此TaglibDescriptor实例表示的标签库的唯一标识符
     */
    public String getTaglibURI();

    /**
     * 获取由此TaglibDescriptor实例所表示的标签库的位置信息。
     *
     * @return 由此TaglibDescriptor实例表示的标签库的位置信息
     */
    public String getTaglibLocation();
}
