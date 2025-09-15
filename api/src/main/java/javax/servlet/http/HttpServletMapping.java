/*
 * Copyright (c) 2017, 2019 Oracle and/or its affiliates and others.
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

/**
 * <p>
 * 允许运行时发现当前 {@link HttpServletRequest} 所对应的 {@link HttpServlet} 被调用的方式。
 * 调用该对象的任何方法时都不得阻塞调用者。实现必须是线程安全的。
 * 该实例不可变，并通过 {@link HttpServletRequest#getHttpServletMapping} 方法返回。
 * </p>
 *
 * <p>
 * 以下展示了各种映射组合的示例。考虑如下 Servlet 声明：
 * </p>
 *
 * <pre>
 * <code>
 * &lt;servlet&gt;
 *     &lt;servlet-name&gt;MyServlet&lt;/servlet-name&gt;
 *     &lt;servlet-class&gt;MyServlet&lt;/servlet-class&gt;
 * &lt;/servlet&gt;
 * &lt;servlet-mapping&gt;
 *     &lt;servlet-name&gt;MyServlet&lt;/servlet-name&gt;
 *     &lt;url-pattern&gt;/MyServlet&lt;/url-pattern&gt;
 *     &lt;url-pattern&gt;""&lt;/url-pattern&gt;
 *     &lt;url-pattern&gt;*.extension&lt;/url-pattern&gt;
 *     &lt;url-pattern&gt;/path/*&lt;/url-pattern&gt;
 * &lt;/servlet-mapping&gt;
 * </code>
 * </pre>
 *
 * <p>
 * 针对不同传入的URI路径值，各属性的预期值如下表所示。
 * 表中省略了 {@code servletName} 列，因为其值始终为 {@code MyServlet}。
 * </p>
 *
 * <table border="1">
 * <caption>不同URI路径对应的属性预期值</caption>
 * <tr>
 * <th>URI路径（带引号）</th>
 * <th>matchValue</th>
 * <th>pattern</th>
 * <th>mappingMatch</th>
 * </tr>
 * <tr>
 * <td>""</td>
 * <td>""</td>
 * <td>""</td>
 * <td>CONTEXT_ROOT</td>
 * </tr>
 * <tr>
 * <td>"/index.html"</td>
 * <td>""</td>
 * <td>/</td>
 * <td>DEFAULT</td>
 * </tr>
 * <tr>
 * <td>"/MyServlet"</td>
 * <td>MyServlet</td>
 * <td>/MyServlet</td>
 * <td>EXACT</td>
 * </tr>
 * <tr>
 * <td>"/foo.extension"</td>
 * <td>foo</td>
 * <td>*.extension</td>
 * <td>EXTENSION</td>
 * </tr>
 * <tr>
 * <td>"/path/foo"</td>
 * <td>foo</td>
 * <td>/path/*</td>
 * <td>PATH</td>
 * </tr>
 *
 * </table>
 *
 * @since 4.0
 */
public interface HttpServletMapping {

    /**
     * <p>
     * 返回导致此请求被匹配的URI路径部分。
     *     <ul>
     *         <li>如果{@link #getMappingMatch}的值为{@code CONTEXT_ROOT}或{@code DEFAULT}，则此方法必须返回空字符串。</li>
     *         <li>如果{@link #getMappingMatch}的值为{@code EXACT}，则此方法必须返回与servlet匹配的路径部分（省略前导斜杠）。</li>
     *         <li>如果{@link #getMappingMatch}的值为{@code EXTENSION}或{@code PATH}，则此方法必须返回与'*'通配符匹配的值。</li>
     *     </ul>
     *     具体示例请参阅类文档。
     * </p>
     *
     * @return 匹配值
     * @since 4.0
     */
    public String getMatchValue();

    /**
     * <p>
     * 返回此映射对应的{@code url-pattern}的字符串表示形式。
     *     <ul>
     *         <li>如果{@link #getMappingMatch}的值为{@code CONTEXT_ROOT}，则此方法必须返回空字符串。</li>
     *         <li>如果{@link #getMappingMatch}的值为{@code EXTENSION}，则此方法必须返回模式字符串（不含任何前导斜杠）。</li>
     *         <li>其他情况下，此方法返回在描述符或Java配置中指定的确切模式。</li>
     *     </ul>
     * </p>
     *
     * @return 此映射对应的{@code url-pattern}的字符串表示形式。
     * @since 4.0
     */
    public String getPattern();

    /**
     * <p>
     * 返回此映射对应的{@code servlet-name}的字符串表示形式。
     * 如果提供响应的Servlet是默认servlet，则此方法返回默认servlet的名称（该名称是容器特定的）。
     * </p>
     *
     * @return 此映射对应的{@code servlet-name}的字符串表示形式。
     * @since 4.0
     */
    public String getServletName();

    /**
     * <p>
     * 返回此实例的 {@link MappingMatch} 类型。
     * </p>
     *
     * @return 此实例的 {@code MappingMatch} 类型。
     * @since 4.0
     */
    public MappingMatch getMappingMatch();

}
