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

import java.util.Collection;

/**
 * 这个接口提供了访问web应用中与<code>&lt;jsp-config&gt;</code> 相关的配置的入口。
 *
 * <p>这个配置聚集了来自web应用中的<code>web.xml</code> 和 <code>web-fragment.xml</code>描述符文件的内容。
 *
 * @since Servlet 3.0
 */
public interface JspConfigDescriptor {

    /**
     * 获取由此<code>JspConfigDescriptor</code>所表示的<code>&lt;jsp-config&gt;</code>元素中包含的所有<code>&lt;taglib&gt;</code>子元素。
     *
     * <p>
     * 对返回的<code>Collection</code>进行的任何修改均不得影响本<code>JspConfigDescriptor</code>对象。
     *
     * @return 可能为空的<code>Collection</code>，包含由此<code>JspConfigDescriptor</code>表示的<code>&lt;jsp-config&gt;</code>元素中的<code>&lt;taglib&gt;</code>子元素
     */
    public Collection<TaglibDescriptor> getTaglibs();

    /**
     * 获取由此<code>JspConfigDescriptor</code>表示的<code>&lt;jsp-config&gt;</code>元素中包含的所有<code>&lt;jsp-property-group&gt;</code>子元素。
     *
     * <p>
     * 对返回的<code>Collection</code>进行的任何修改均不得影响本<code>JspConfigDescriptor</code>对象。
     *
     * @return 可能为空的<code>Collection</code>，包含由此<code>JspConfigDescriptor</code>表示的<code>&lt;jsp-config&gt;</code>元素中的<code>&lt;jsp-property-group&gt;</code>子元素
     */
    public Collection<JspPropertyGroupDescriptor> getJspPropertyGroups();
}
