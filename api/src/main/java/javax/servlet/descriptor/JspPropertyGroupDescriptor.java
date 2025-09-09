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
 * 这个接口提供了访问web应用中与<code>&lt;jsp-property-group&gt;</code> 相关的配置的入口。
 *
 * <p>这个配置聚集了来自web应用中的<code>web.xml</code> 和 <code>web-fragment.xml</code>描述符文件的内容。
 *
 * @since Servlet 3.0
 */
public interface JspPropertyGroupDescriptor {

    /**
     * 获取由此<code>JspPropertyGroupDescriptor</code>所表示的JSP属性组的URL模式。
     *
     * <p>
     * 对返回的<code>Collection</code>进行的任何修改均不得影响此<code>JspPropertyGroupDescriptor</code>对象。
     *
     * @return 可能为空的<code>Collection</code>，包含由此<code>JspPropertyGroupDescriptor</code>表示的JSP属性组的URL模式
     */
    public Collection<String> getUrlPatterns();

    /**
     * 获取<code>el-ignored</code>配置项的值，该配置项指定是否对映射到当前
     * <code>JspPropertyGroupDescriptor</code>所表示JSP属性组的所有JSP页面启用表达式语言(EL)求值功能。
     *
     * @return <code>el-ignored</code>配置项的值，若未指定则返回null
     */
    public String getElIgnored();

    /**
     * 获取<code>page-encoding</code>配置项的值，该配置项指定映射到当前
     * <code>JspPropertyGroupDescriptor</code>所表示JSP属性组的所有JSP页面的默认页面编码格式。
     *
     * @return <code>page-encoding</code>配置项的值，若未指定则返回null
     */
    public String getPageEncoding();

    /**
     * 获取<code>scripting-invalid</code>配置项的值，该配置项指定是否对映射到当前
     * <code>JspPropertyGroupDescriptor</code>所表示JSP属性组的所有JSP页面启用脚本功能。
     *
     * @return <code>scripting-invalid</code>配置项的值，若未指定则返回null
     */
    public String getScriptingInvalid();

    /**
     * 获取<code>is-xml</code>配置项的值，该配置项指定映射到当前<code>JspPropertyGroupDescriptor</code>所表示
     * JSP属性组的所有JSP页面是否将被视为JSP文档（XML语法）进行处理。
     *
     * @return <code>is-xml</code>配置项的值，若未指定则返回null
     */
    public String getIsXml();

    /**
     * 获取由此<code>JspPropertyGroupDescriptor</code>所表示的JSP属性组的<code>include-prelude</code>配置。
     *
     * <p>
     * 对返回的<code>Collection</code>进行的任何修改均不得影响此<code>JspPropertyGroupDescriptor</code>对象。
     *
     * @return 可能为空的<code>Collection</code>，包含由此<code>JspPropertyGroupDescriptor</code>表示的JSP属性组的<code>include-prelude</code>配置
     */
    public Collection<String> getIncludePreludes();

    /**
     * 获取由此<code>JspPropertyGroupDescriptor</code>所表示的JSP属性组的<code>include-coda</code>配置。
     *
     * <p>
     * 对返回的<code>Collection</code>进行的任何修改均不得影响此<code>JspPropertyGroupDescriptor</code>对象。
     *
     * @return 可能为空的<code>Collection</code>，包含由此<code>JspPropertyGroupDescriptor</code>表示的JSP属性组的<code>include-coda</code>配置
     */
    public Collection<String> getIncludeCodas();

    /**
     * 获取<code>deferred-syntax-allowed-as-literal</code>配置项的值，该配置项指定字符序列<code>"#{"</code>
     * （通常保留用于表达式语言(EL)表达式）在映射到当前<code>JspPropertyGroupDescriptor</code>所表示JSP属性组的
     * 任何JSP页面中作为字符串字面量出现时，是否会导致翻译时错误。
     *
     * @return <code>deferred-syntax-allowed-as-literal</code>配置项的值，若未指定则返回null
     */
    public String getDeferredSyntaxAllowedAsLiteral();

    /**
     * 获取<code>trim-directive-whitespaces</code>配置项的值，该配置项指定是否必须从映射到当前
     * <code>JspPropertyGroupDescriptor</code>所表示JSP属性组的所有JSP页面的响应输出中移除仅包含空白的模板文本。
     *
     * @return <code>trim-directive-whitespaces</code>配置项的值，若未指定则返回null
     */
    public String getTrimDirectiveWhitespaces();

    /**
     * 获取<code>default-content-type</code>配置项的值，该配置项指定了映射到当前
     * <code>JspPropertyGroupDescriptor</code>所表示JSP属性组的所有JSP页面的默认响应内容类型。
     *
     * @return <code>default-content-type</code>配置项的值，若未指定则返回null
     */
    public String getDefaultContentType();

    /**
     * 获取<code>buffer</code>配置项的值，该配置项指定了映射到当前<code>JspPropertyGroupDescriptor</code>所表示JSP属性组的所有JSP页面的响应缓冲区默认大小。
     *
     * @return <code>buffer</code>配置项的值，若未指定则返回null
     */
    public String getBuffer();

    /**
     * 获取<code>error-on-undeclared-namespace</code>配置项的值，该配置项指定了在翻译时若在任何映射到由此
     * <code>JspPropertyGroupDescriptor</code>所表示的JSP属性组的JSP页面中使用了未声明命名空间的标签，是否将引发错误。
     *
     * @return <code>error-on-undeclared-namespace</code>配置项的值，如未指定则返回null
     */
    public String getErrorOnUndeclaredNamespace();
}
