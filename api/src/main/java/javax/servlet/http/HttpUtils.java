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

import javax.servlet.ServletInputStream;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.io.IOException;

/**
 * @deprecated 自 Java(tm) Servlet API 2.3 起已弃用。这些方法仅在使用默认编码时有用，并已被移至请求接口中。
 */
@Deprecated
public class HttpUtils {

    private static final String LSTRING_FILE = "javax.servlet.http.LocalStrings";
    private static ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    /**
     * 构造一个空的 <code>HttpUtils</code> 对象。
     */
    public HttpUtils() {
    }

    /**
     * 解析从客户端传递到服务器的查询字符串，并构建一个包含键值对的 <code>HashTable</code> 对象。
     * 查询字符串应采用 GET 或 POST 方法打包的字符串形式，即应具有 <i>key=value</i> 形式的键值对，
     * 每对之间用 &amp; 字符分隔。
     *
     * <p>
     * 一个键可以在查询字符串中出现多次并具有不同的值。但在哈希表中，该键仅出现一次，
     * 其值是一个字符串数组，包含查询字符串发送的多个值。
     *
     * <p>
     * 哈希表中的键和值以解码后的形式存储，因此任何 + 字符会被转换为空格，
     * 以十六进制表示法发送的字符（如 <i>%xx</i>）会被转换为 ASCII 字符。
     *
     * @param s 包含要解析的查询的字符串
     * @return 从解析后的键值对构建的 <code>HashTable</code> 对象
     * @exception IllegalArgumentException 如果查询字符串无效
     */
    public static Hashtable<String, String[]> parseQueryString(String s) {

        String valArray[] = null;

        if (s == null) {
            throw new IllegalArgumentException();
        }

        Hashtable<String, String[]> ht = new Hashtable<>();
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(s, "&");
        while (st.hasMoreTokens()) {
            String pair = st.nextToken();
            int pos = pair.indexOf('=');
            if (pos == -1) {
                // XXX
                // should give more detail about the illegal argument
                throw new IllegalArgumentException();
            }
            String key = parseName(pair.substring(0, pos), sb);
            String val = parseName(pair.substring(pos + 1, pair.length()), sb);
            if (ht.containsKey(key)) {
                String oldVals[] = ht.get(key);
                valArray = new String[oldVals.length + 1];
                for (int i = 0; i < oldVals.length; i++) {
                    valArray[i] = oldVals[i];
                }
                valArray[oldVals.length] = val;
            } else {
                valArray = new String[1];
                valArray[0] = val;
            }
            ht.put(key, valArray);
        }

        return ht;
    }

    /**
     * 解析客户端使用HTTP POST方法和<i>application/x-www-form-urlencoded</i> MIME类型发送到服务器的HTML表单数据。
     *
     * <p>
     * POST方法发送的数据包含键值对。一个键可以在POST数据中出现多次并具有不同的值。但在哈希表中，该键仅出现一次，
     * 其值是一个字符串数组，包含POST方法发送的多个值。
     *
     * <p>
     * 哈希表中的键和值以解码后的形式存储，因此任何 + 字符会被转换为空格，
     * 以十六进制表示法发送的字符（如 <i>%xx</i>）会被转换为 ASCII 字符。
     *
     * @param len 指定同时传递给此方法的<code>ServletInputStream</code>对象长度的整数（以字符为单位）
     * @param in  包含客户端发送的数据的<code>ServletInputStream</code>对象
     * @return 从解析后的键值对构建的<code>HashTable</code>对象
     * @exception IllegalArgumentException 如果POST方法发送的数据无效
     */
    public static Hashtable<String, String[]> parsePostData(int len, ServletInputStream in) {
        // XXX
        // should a length of 0 be an IllegalArgumentException

        if (len <= 0) {
            // cheap hack to return an empty hash
            return new Hashtable<>();
        }

        if (in == null) {
            throw new IllegalArgumentException();
        }

        //
        // Make sure we read the entire POSTed body.
        //
        byte[] postedBytes = new byte[len];
        try {
            int offset = 0;

            do {
                int inputLen = in.read(postedBytes, offset, len - offset);
                if (inputLen <= 0) {
                    String msg = lStrings.getString("err.io.short_read");
                    throw new IllegalArgumentException(msg);
                }
                offset += inputLen;
            } while ((len - offset) > 0);

        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        // XXX we shouldn't assume that the only kind of POST body
        // is FORM data encoded using ASCII or ISO Latin/1 ... or
        // that the body should always be treated as FORM data.
        //

        try {
            String postedBody = new String(postedBytes, 0, len, "8859_1");
            return parseQueryString(postedBody);
        } catch (java.io.UnsupportedEncodingException e) {
            // XXX function should accept an encoding parameter & throw this
            // exception. Otherwise throw something expected.
            throw new IllegalArgumentException(e.getMessage());
        }
    }


    /**
     * 解析查询字符串中的名称。
     */
    private static String parseName(String s, StringBuilder sb) {
        sb.setLength(0);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
            case '+':
                sb.append(' ');
                break;
            case '%':
                try {
                    sb.append((char) Integer.parseInt(s.substring(i + 1, i + 3), 16));
                    i += 2;
                } catch (NumberFormatException e) {
                    // XXX
                    // need to be more specific about illegal arg
                    throw new IllegalArgumentException();
                } catch (StringIndexOutOfBoundsException e) {
                    String rest = s.substring(i);
                    sb.append(rest);
                    if (rest.length() == 2)
                        i++;
                }

                break;
            default:
                sb.append(c);
                break;
            }
        }

        return sb.toString();
    }

    /**
     *
     * 使用<code>HttpServletRequest</code>对象中的信息重建客户端用于发出请求的URL。
     * 返回的URL包含协议、服务器名称、端口号和服务器路径，但不包含查询字符串参数。
     *
     * <p>
     * 由于此方法返回的是<code>StringBuffer</code>而不是字符串，因此您可以轻松修改URL，
     * 例如追加查询参数。
     *
     * <p>
     * 此方法对于创建重定向消息和报告错误非常有用。
     *
     * @param req 包含客户端请求的<code>HttpServletRequest</code>对象
     * @return 包含重建URL的<code>StringBuffer</code>对象
     */
    public static StringBuffer getRequestURL(HttpServletRequest req) {
        StringBuffer url = new StringBuffer();
        String scheme = req.getScheme();
        int port = req.getServerPort();
        String urlPath = req.getRequestURI();

        // String servletPath = req.getServletPath ();
        // String pathInfo = req.getPathInfo ();

        url.append(scheme); // http, https
        url.append("://");
        url.append(req.getServerName());
        if ((scheme.equals("http") && port != 80) || (scheme.equals("https") && port != 443)) {
            url.append(':');
            url.append(req.getServerPort());
        }
        // if (servletPath != null)
        // url.append (servletPath);
        // if (pathInfo != null)
        // url.append (pathInfo);
        url.append(urlPath);

        return url;
    }
}
