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

package javax.servlet.http;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 创建一个cookie，这是由servlet发送给Web浏览器的一小段信息，由浏览器保存并在之后发送回服务器。
 * cookie的值可以唯一标识客户端，因此通常用于会话管理。
 *
 * <p>
 *     cookie具有名称、单个值以及可选属性，如注释、路径和域限定符、最大生存时间和版本号。
 *     某些Web浏览器在处理可选属性时存在缺陷，因此应谨慎使用这些属性以提高servlet的互操作性。
 *
 * <p>
 *     servlet通过使用{@link HttpServletResponse#addCookie}方法将cookie发送给浏览器，
 *     该方法会向HTTP响应头添加字段来逐个发送cookie。
 *     预期每个Web服务器支持20个cookie，总共支持300个cookie，并且可能将每个cookie的大小限制为4KB。
 *
 * <p>
 *     浏览器通过向HTTP请求头添加字段将cookie返回给servlet。
 *     可以使用{@link HttpServletRequest#getCookies}方法从请求中获取cookie。
 *     多个cookie可能具有相同名称但不同的路径属性。
 *
 * <p>
 *     cookie会影响使用它们的网页缓存。
 *     HTTP 1.0不会缓存使用此类创建的cookie的页面。
 *     此类不支持HTTP 1.1定义的缓存控制。
 *
 * <p>
 *     此类同时支持版本0（由Netscape制定）和版本1（由RFC 2109定义）的cookie规范。
 *     默认情况下，使用版本0创建cookie以确保最佳的互操作性。
 *
 * @author Various
 */
public class Cookie implements Cloneable, Serializable {

    private static final long serialVersionUID = -6454587001725327448L;

    private static final String TSPECIALS;

    private static final String LSTRING_FILE = "javax.servlet.http.LocalStrings";

    private static ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    static {
        if (Boolean.valueOf(System.getProperty("org.glassfish.web.rfc2109_cookie_names_enforced", "true"))
                .booleanValue()) {
            TSPECIALS = "/()<>@,;:\\\"[]?={} \t";
        } else {
            TSPECIALS = ",; ";
        }
    }

    //
    // cookie本身的值。
    //

    private String name; // NAME= ... "$Name" style is reserved
    private String value; // value of NAME

    //
    // Attributes encoded in the header's cookie fields.
    //

    private String comment; // ;Comment=VALUE ... describes cookie's use
    // ;Discard ... implied by maxAge < 0
    private String domain; // ;Domain=VALUE ... domain that sees cookie
    private int maxAge = -1; // ;Max-Age=VALUE ... cookies auto-expire
    private String path; // ;Path=VALUE ... URLs that see the cookie
    private boolean secure; // ;Secure ... e.g. use SSL
    private int version = 0; // ;Version=1 ... means RFC 2109++ style
    private boolean isHttpOnly = false;

    /**
     * 使用指定的名称和值构造一个cookie。
     *
     * <p>名称必须符合RFC 2109规范。但供应商可能提供配置选项，允许接受符合原始Netscape Cookie规范的cookie名称。
     *
     * <p>cookie一旦创建，其名称不可更改。
     *
     * <p>值可以是服务器选择发送的任何内容。该值可能仅对服务器有意义。cookie的值可以在创建后通过<code>setValue</code>方法进行修改。
     *
     * <p>默认情况下，cookie根据Netscape cookie规范创建。可通过<code>setVersion</code>方法更改版本。
     *
     * @param name  cookie的名称
     * @param value cookie的值
     * @throws IllegalArgumentException 如果cookie名称为空、为空字符串、包含非法字符（如逗号、空格或分号），或与cookie协议保留的令牌冲突
     * @see #setValue
     * @see #setVersion
     */
    public Cookie(String name, String value) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException(lStrings.getString("err.cookie_name_blank"));
        }
        if (!isToken(name) || name.equalsIgnoreCase("Comment") || // rfc2019
                name.equalsIgnoreCase("Discard") || // 2019++
                name.equalsIgnoreCase("Domain") || name.equalsIgnoreCase("Expires") || // (old cookies)
                name.equalsIgnoreCase("Max-Age") || // rfc2019
                name.equalsIgnoreCase("Path") || name.equalsIgnoreCase("Secure") || name.equalsIgnoreCase("Version")
                || name.startsWith("$")) {
            String errMsg = lStrings.getString("err.cookie_name_is_token");
            Object[] errArgs = new Object[1];
            errArgs[0] = name;
            errMsg = MessageFormat.format(errMsg, errArgs);
            throw new IllegalArgumentException(errMsg);
        }

        this.name = name;
        this.value = value;
    }

    /**
     * 指定描述cookie用途的注释。
     * 如果浏览器需要向用户展示cookie，此注释将很有用。
     * Netscape版本0的cookie不支持注释功能。
     *
     * @param purpose 指定向用户显示的注释的<code>String</code>
     * @see #getComment
     */
    public void setComment(String purpose) {
        comment = purpose;
    }

    /**
     * 返回描述此cookie用途的注释，如果cookie没有注释则返回<code>null</code>。
     *
     * @return cookie的注释，如果未指定则返回<code>null</code>
     * @see #setComment
     */
    public String getComment() {
        return comment;
    }

    /**
     * 指定此cookie应呈现的域名范围。
     *
     * <p>
     *     域名的格式遵循RFC 2109规范。
     *     以点号开头的域名（如<code>.foo.com</code>）表示该cookie对指定DNS区域内的服务器可见
     *     （例如，对<code>www.foo.com</code>可见，但对<code>a.b.foo.com</code>不可见）。
     *     默认情况下，cookie仅返回给发送它们的服务器。
     *
     * @param domain 此cookie可见的域名，格式需符合RFC 2109规范
     * @see #getDomain
     */
    public void setDomain(String domain) {
        this.domain = domain.toLowerCase(Locale.ENGLISH); // IE allegedly needs this
    }

    /**
     * 获取此cookie的域名。
     *
     * <p>
     *     域名格式遵循RFC 2109规范。
     *
     * @return 此cookie的域名
     * @see #setDomain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * 设置此Cookie的最大存活时间（以秒为单位）。
     *
     * <p>正值表示Cookie将在指定秒数后过期。请注意，此值是Cookie将过期的<i>最大</i>存活时间，而非Cookie的当前存在时间。
     *
     * <p>
     *     负值表示Cookie不会持久存储，并在Web浏览器退出时被删除。
     *     零值会导致Cookie立即被删除。
     *
     * @param expiry 指定Cookie最大存活时间的整数值（以秒为单位）；若为负值，表示Cookie不持久存储；若为零，则删除Cookie
     * @see #getMaxAge
     */
    public void setMaxAge(int expiry) {
        maxAge = expiry;
    }

    /**
     * 获取此Cookie的最大存活时间（以秒为单位）。
     *
     * <p>默认返回<code>-1</code>，表示Cookie将持续存在直到浏览器关闭。
     *
     * @return 指定Cookie最大存活时间的整数值（以秒为单位）；若为负值，表示Cookie将持续存在直到浏览器关闭
     * @see #setMaxAge
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * 指定客户端应返回cookie的路径。
     *
     * <p>
     *     cookie对指定目录及其所有子目录中的所有页面可见。
     *     cookie的路径必须包含设置该cookie的servlet，例如：<i>/catalog</i>路径会使cookie对服务器上<i>/catalog</i>下的所有目录可见。
     *
     * <p>
     *     有关设置cookie路径名称的更多信息，请参阅RFC 2109（可在互联网上获取）。
     *
     * @param uri 指定路径的<code>String</code>
     * @see #getPath
     */
    public void setPath(String uri) {
        path = uri;
    }

    /**
     * 返回浏览器应返回此cookie的服务器路径。
     * cookie对服务器上的所有子路径可见。
     *
     * @return 指定包含servlet名称的路径的<code>String</code>，例如<i>/catalog</i>
     * @see #setPath
     */
    public String getPath() {
        return path;
    }

    /**
     * 向浏览器指示是否应仅使用安全协议（如HTTPS或SSL）发送cookie。
     *
     * <p>默认值为<code>false</code>。
     *
     * @param flag 如果为<code>true</code>，则仅在使用安全协议时从浏览器向服务器发送cookie；
     *             如果为<code>false</code>，则可通过任何协议发送
     * @see #getSecure
     */
    public void setSecure(boolean flag) {
        secure = flag;
    }

    /**
     * 返回<code>true</code>表示浏览器仅通过安全协议发送cookie，返回<code>false</code>表示
     * 浏览器可使用任何协议发送cookie。
     *
     * @return 如果浏览器使用安全协议则返回<code>true</code>，否则返回<code>false</code>
     * @see #setSecure
     */
    public boolean getSecure() {
        return secure;
    }

    /**
     * 返回cookie的名称。
     * 名称在创建后不可更改。
     *
     * @return cookie的名称
     */
    public String getName() {
        return name;
    }

    /**
     * 为此Cookie分配新值。
     *
     * <p>如果使用二进制值，建议采用BASE64编码。
     *
     * <p>
     *     对于版本0的cookie，值不应包含空格、括号、圆括号、等号、逗号、双引号、斜杠、问号、@符号、冒号和分号。
     *     空值在不同浏览器中的行为可能不一致。
     *
     * @param newValue cookie的新值
     * @see #getValue
     */
    public void setValue(String newValue) {
        value = newValue;
    }

    /**
     * 获取此Cookie的当前值。
     *
     * @return 此Cookie的当前值
     * @see #setValue
     */
    public String getValue() {
        return value;
    }

    /**
     * 返回此cookie遵循的协议版本。
     * <p>版本1符合RFC 2109规范，版本0符合Netscape起草的原始cookie规范。
     * <p>浏览器提供的cookie使用并标识浏览器的cookie版本。
     *
     * @return 如果cookie符合原始Netscape规范则返回0；如果符合RFC 2109则返回1
     * @see #setVersion
     */
    public int getVersion() {
        return version;
    }

    /**
     * 设置此cookie遵循的cookie协议版本。
     * <p>版本0符合原始Netscape cookie规范。版本1符合RFC 2109规范。
     * <p>由于RFC 2109相对较新，请将版本1视为实验性版本；暂不建议在生产站点中使用。
     *
     * @param v 如果cookie应符合原始Netscape规范则设置为0；如果应符合RFC 2109则设置为1
     * @see #getVersion
     */
    public void setVersion(int v) {
        version = v;
    }

    /**
     * 测试字符串，如果该字符串是Java语言中的保留词则返回true。
     *
     * @param value 要测试的<code>String</code>
     * @return 如果<code>String</code>是保留词则返回<code>true</code>；否则返回<code>false</code>
     */
    private boolean isToken(String value) {
        int len = value.length();
        for (int i = 0; i < len; i++) {
            char c = value.charAt(i);
            if (c < 0x20 || c >= 0x7f || TSPECIALS.indexOf(c) != -1) {
                return false;
            }
        }

        return true;
    }

    /**
     * 重写标准的<code>java.lang.Object.clone</code>方法以返回此Cookie的副本。
     */
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 标记或取消标记此Cookie为<i>HttpOnly</i>。
     * <p>如果<tt>isHttpOnly</tt>设置为<tt>true</tt>，则通过添加<tt>HttpOnly</tt>属性将此cookie标记为<i>HttpOnly</i>。
     * <p><i>HttpOnly</i> cookie不应暴露给客户端脚本代码，因此可能有助于减轻某些类型的跨站脚本攻击。
     *
     * @param isHttpOnly 如果要将此cookie标记为<i>HttpOnly</i>则为true，否则为false
     * @since Servlet 3.0
     */
    public void setHttpOnly(boolean isHttpOnly) {
        this.isHttpOnly = isHttpOnly;
    }

    /**
     * 检查此Cookie是否已被标记为<i>HttpOnly</i>。
     *
     * @return 如果此Cookie已被标记为<i>HttpOnly</i>则返回true，否则返回false
     * @since Servlet 3.0
     */
    public boolean isHttpOnly() {
        return isHttpOnly;
    }
}
