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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * 定义了一个用于协助Servlet向客户端发送响应的对象。
 * Servlet容器会创建一个<code>ServletResponse</code>对象，
 * 并将其作为参数传递给servlet的<code>service</code>方法。
 *
 * <p>
 *     在MIME正文响应中,
 *     要发送二进制数据，请使用通过{@link #getOutputStream}返回的{@link ServletOutputStream}对象，
 *     要发送字符数据，请使用通过{@link #getWriter}返回的{@link PrintWriter}对象。
 *     要混合发送二进制和文本数据（例如：创建多部分响应），请使用{@link ServletOutputStream}对象，并手动管理字符部分。
 *
 * <p>
 *     MIME正文响应的字符集可以通过以下任一技术显式指定：
 *     <ul>
 *         <li>按请求指定</li>
 *         <li>按Web应用指定（使用{@link ServletContext#setRequestCharacterEncoding}或部署描述符配置）</li>
 *         <li>按容器指定（对于部署在该容器中的所有Web应用，使用供应商特定配置）</li>
 *     </ul>
 *     如果采用了多种前述技术，则优先级按列出顺序递减。
 *     对于每个请求，可以使用{@link #setCharacterEncoding}和{@link #setContentType}方法显式指定响应的字符集，或使用{@link #setLocale}方法隐式指定。
 *     显式规范优先于隐式规范。
 *     如果未显式指定字符集，则将使用ISO-8859-1。
 *
 * <p>
 *     要确保使用正确的字符编码，必须在调用<code>getWriter</code> 以及提交响应之前，调用如下方法：
 *     <ul>
 *         <li><code>setCharacterEncoding</code></li>
 *         <li><code>setContentType</code></li>
 *         <li><code>setLocale</code></li>
 *     </ul>
 * <p>
 *     有关MIME的更多信息，请参阅Internet RFC（例如<a href="http://www.ietf.org/rfc/rfc2045.txt">RFC 2045</a>）。
 *     SMTP和HTTP等协议定义了MIME的配置文件，且这些标准仍在不断发展中。
 *
 * @author Various
 * @see ServletOutputStream
 */
public interface ServletResponse {

    /**
     * 设置发送给客户端的响应的字符编码（MIME字符集），例如设置为UTF-8。
     * 如果响应字符编码已通过{@link ServletContext#setResponseCharacterEncoding}、
     * 部署描述符或使用setContentType()、setLocale()方法设置，则此方法设置的值将覆盖之前的所有值。
     * 使用字符串<code>text/html</code>调用{@link #setContentType}并结合使用字符串<code>UTF-8</code>
     * 调用此方法，等同于使用字符串<code>text/html; charset=UTF-8</code>调用<code>setContentType</code>。
     * <p>
     *     此方法可重复调用以更改字符编码。
     *     如果在调用<code>getWriter</code>之后或响应已提交后调用，则该方法无效。
     *
     * <p>
     * 如果协议支持，容器必须将用于Servlet响应写入器的字符编码告知客户端。
     * 对于HTTP协议，字符编码会作为文本媒体类型的<code>Content-Type</code>头部的一部分进行传递。
     * 请注意，如果Servlet未指定内容类型，则无法通过HTTP头部传递字符编码；但该编码仍会用于编码通过Servlet响应写入器写入的文本。
     *
     * @param charset 指定仅包含IANA字符集（http://www.iana.org/assignments/character-sets）定义的字符集的字符串
     *
     * @see #setContentType
     * @see #setLocale
     *
     * @since Servlet 2.4
     */
    public void setCharacterEncoding(String charset);

    /**
     * 返回此响应体中发送内容所使用的字符编码名称（MIME字符集）。
     * <p>
     *     确定响应字符编码时按以下方法优先级递减顺序采用：
     *     <ul>
     *         <li>按请求指定</li>
     *         <li>按Web应用指定（使用{@link ServletContext#setResponseCharacterEncoding}或部署描述符配置）</li>
     *         <li>按容器指定（对于部署在该容器中的所有Web应用，使用供应商特定配置）</li>
     *     </ul>
     *     将返回这些方法中首个产生结果的值。
     *     对于每个请求，可以使用{@link #setCharacterEncoding}和{@link #setContentType}方法显式指定响应字符集，
     *     或使用setLocale(java.util.Locale)方法隐式指定。显式规范优先于隐式规范。
     *     在调用<code>getWriter</code>或提交响应后调用这些方法不会影响字符编码。
     *     如果未指定字符编码，则返回<code>ISO-8859-1</code>。
     * <p>
     *     有关字符编码和MIME的更多信息，请参阅RFC 2047 (http://www.ietf.org/rfc/rfc2047.txt)。
     *
     * @return 指定字符编码名称的<code>String</code>，例如<code>UTF-8</code>
     */
    public String getCharacterEncoding();

    /**
     * 设置要发送给客户端的响应的内容类型（前提是响应尚未提交）。
     * 给定的内容类型可以包含字符编码规范，例如: <code>text/html;charset=UTF-8</code>。
     * 只有在调用<code>getWriter</code>之前调用此方法时，才会根据给定的内容类型设置响应的字符编码。
     *
     * <p>
     *     该方法可重复调用以更改内容类型和字符编码。
     *     若在响应提交后调用，则不会产生任何效果。
     *     如果在调用<code>getWriter</code>之后或响应已提交后调用，则不会设置响应的字符编码。
     *
     * <p>
     *     如果协议支持，容器必须将用于Servlet响应写入器的内容类型和字符编码告知客户端。
     *     对于HTTP协议，是通过<code>Content-Type</code>头部来实现的。
     *
     * @param type 指定内容MIME类型的<code>String</code>
     * @see #setLocale
     * @see #setCharacterEncoding
     * @see #getOutputStream
     * @see #getWriter
     *
     */
    public void setContentType(String type);

    /**
     * 返回此响应中发送的MIME正文所使用的内容类型。
     * 必须在响应提交前使用{@link #setContentType}指定具体的内容类型。
     * 如果未指定内容类型，此方法返回null。
     * 如果已指定内容类型，并且已通过{@link #getCharacterEncoding}中描述的方式显式或隐式指定了字符编码，或已调用了{@link #getWriter}，
     * 则返回的字符串中将包含charset参数。
     * 如果未指定字符编码，则省略charset参数。
     *
     * @return 指定内容类型的<code>String</code>，例如<code>text/html; charset=UTF-8</code>，或null
     * @since Servlet 2.4
     */
    public String getContentType();

    /**
     * 返回适用于在响应中写入二进制数据的{@link ServletOutputStream}。
     * Servlet容器不会对二进制数据进行编码。
     *
     * <p>
     *     在ServletOutputStream上调用flush()方法将提交响应。
     *     可以调用此方法或{@link #getWriter}之一来写入响应体，但不能同时调用两者，除非已调用{@link #reset}方法。
     *
     * @return 用于写入二进制数据的{@link ServletOutputStream}
     * @exception IllegalStateException 如果已在此响应上调用<code>getWriter</code>方法
     * @exception IOException           如果发生输入或输出异常
     * @see #getWriter
     * @see #reset
     */
    public ServletOutputStream getOutputStream() throws IOException;

    /**
     * 返回一个可向客户端发送字符文本的<code>PrintWriter</code>对象。
     * 该<code>PrintWriter</code>使用{@link #getCharacterEncoding}返回的字符编码。
     * 如果响应的字符编码未按<code>getCharacterEncoding</code>中所述的方式指定
     * （即该方法仅返回默认值<code>ISO-8859-1</code>），则<code>getWriter</code>会将其更新为<code>ISO-8859-1</code>。
     *
     * <p>
     *     在<code>PrintWriter</code>上调用flush()方法将提交响应。
     *     可以调用此方法或{@link #getOutputStream}之一来写入响应体，但不能同时调用两者，除非已调用{@link #reset}方法。
     *
     * @return 可向客户端返回字符数据的<code>PrintWriter</code>对象
     * @exception java.io.UnsupportedEncodingException 如果<code>getCharacterEncoding</code>返回的字符编码无法使用
     * @exception IllegalStateException 如果已在此响应对象上调用<code>getOutputStream</code>方法
     * @exception IOException           如果发生输入或输出异常
     *
     * @see #getOutputStream
     * @see #setCharacterEncoding
     * @see #reset
     */
    public PrintWriter getWriter() throws IOException;

    /**
     * 设置响应中内容主体的长度。
     * 在HTTP servlet中，此方法用于设置HTTP Content-Length头。
     *
     * @param len 指定返回给客户端的内容长度的整数；用于设置Content-Length头
     */
    public void setContentLength(int len);

    /**
     * 设置响应中内容主体的长度。
     * 在HTTP servlet中，此方法用于设置HTTP Content-Length头。
     *
     * @param len 指定返回给客户端的内容长度的长整型值；用于设置Content-Length头
     * @since Servlet 3.1
     */
    public void setContentLengthLong(long len);

    /**
     * 设置响应体的首选缓冲区大小。
     * Servlet容器将使用至少等于请求大小的缓冲区。
     * 实际使用的缓冲区大小可通过<code>getBufferSize</code>方法获取。
     *
     * <p>
     *     较大的缓冲区允许在实际发送任何内容之前写入更多内容，从而为servlet提供更多时间来设置适当的状态码和头部。
     *     较小的缓冲区可减少服务器内存负载，并允许客户端更快开始接收数据。
     *
     * <p>
     *     此方法必须在写入任何响应体内容之前调用；
     *     如果已写入内容或响应对象已提交，该方法将抛出<code>IllegalStateException</code>异常。
     *
     * @param size 首选缓冲区大小
     * @exception IllegalStateException 如果在写入内容后调用此方法
     * @see #getBufferSize
     * @see #flushBuffer
     * @see #isCommitted
     * @see #reset
     */
    public void setBufferSize(int size);

    /**
     * 返回响应实际使用的缓冲区大小。如果未使用缓冲，该方法返回0。
     *
     * @return 实际使用的缓冲区大小
     * @see #setBufferSize
     * @see #flushBuffer
     * @see #isCommitted
     * @see #reset
     */
    public int getBufferSize();

    /**
     * 强制将缓冲区中的任何内容写入客户端。
     * 调用此方法会自动提交响应，这意味着状态码和头部信息将被写入。
     *
     * @see #setBufferSize
     * @see #getBufferSize
     * @see #isCommitted
     * @see #reset
     * @throws IOException 如果无法完成缓冲区的刷新操作
     */
    public void flushBuffer() throws IOException;

    /**
     * 清除响应底层缓冲区的内容，而不清除头部或状态码。
     * 如果响应已被提交，此方法将抛出<code>IllegalStateException</code>异常。
     *
     * @see #setBufferSize
     * @see #getBufferSize
     * @see #isCommitted
     * @see #reset
     * @since Servlet 2.3
     */
    public void resetBuffer();

    /**
     * 返回一个布尔值，指示响应是否已被提交。
     * 已提交的响应表示其状态码和头部信息已被写入。
     *
     * @return 指示响应是否已被提交的布尔值
     * @see #setBufferSize
     * @see #getBufferSize
     * @see #flushBuffer
     * @see #reset
     */
    public boolean isCommitted();

    /**
     * 清除缓冲区中存在的任何数据以及状态码和头部信息。
     * 调用{@link #getWriter}或{@link #getOutputStream}的状态也会被清除。
     * 例如，先调用{@link #getWriter}、再调用{@link #reset}，然后调用{@link #getOutputStream}是合法的操作。
     * 如果在调用此方法之前已经调用了{@link #getWriter}或{@link #getOutputStream}，
     * 则相应的返回Writer或OutputStream将变为已过时状态，使用已过时对象的行为是未定义的。
     * 如果响应已被提交，此方法将抛出<code>IllegalStateException</code>异常。
     *
     * @exception IllegalStateException 如果响应已被提交
     * @see #setBufferSize
     * @see #getBufferSize
     * @see #flushBuffer
     * @see #isCommitted
     */
    public void reset();

    /**
     * 设置响应的区域设置（如果响应尚未提交）。
     * 如果尚未使用{@link #setContentType}或{@link #setCharacterEncoding}显式设置字符编码，
     * 且尚未调用<code>getWriter</code>方法，同时响应尚未提交，该方法还会根据区域设置相应地设置响应的字符编码。
     * 如果部署描述符包含<code>locale-encoding-mapping-list</code>元素，且该元素提供了给定区域设置的映射，则使用该映射。
     * 否则，从区域设置到字符编码的映射取决于容器实现。
     * <p>
     *     该方法可重复调用以更改区域设置和字符编码。
     *     如果在响应提交后调用，则该方法不产生任何效果。
     *     如果在已使用字符集规范调用{@link #setContentType}后、调用{@link #setCharacterEncoding}后、
     *     调用<code>getWriter</code>后或响应提交后调用，则不会设置响应的字符编码。
     * <p>
     *     如果协议支持，容器必须将用于Servlet响应写入器的区域设置和字符编码告知客户端。
     *     对于HTTP协议，区域设置通过<code>Content-Language</code>头传递，
     *     字符编码则作为文本媒体类型<code>Content-Type</code>头的一部分进行传递。
     *     请注意，如果Servlet未指定内容类型，则无法通过HTTP头传递字符编码；
     *     但仍会使用该编码来编码通过Servlet响应写入器写入的文本。
     *
     * @param loc 响应的区域设置
     *
     * @see #getLocale
     * @see #setContentType
     * @see #setCharacterEncoding
     */
    public void setLocale(Locale loc);

    /**
     * 返回通过{@link #setLocale}方法为此响应指定的区域设置。
     * 在响应提交后调用<code>setLocale</code>方法不会产生任何效果。
     * 如果未指定任何区域设置，则返回容器的默认区域设置。
     *
     * @return 此响应的Locale区域设置对象
     * @see #setLocale
     */
    public Locale getLocale();

}
