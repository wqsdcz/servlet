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
 * 为 ServletResponse 接口提供了一个便捷的实现，开发人员若希望适配来自 Servlet 的响应可对此类进行子类化。
 * 此类实现了包装器（Wrapper）或装饰器（Decorator）模式。其方法默认调用被包装的响应对象。
 *
 * @author 多方贡献
 * @since Servlet 2.3
 * @see javax.servlet.ServletResponse
 */
public class ServletResponseWrapper implements ServletResponse {
    private ServletResponse response;

    /**
     * 创建一个用于包装给定响应对象的 ServletResponse 适配器。
     *
     * @param response 要被包装的 {@link ServletResponse}
     * @throws java.lang.IllegalArgumentException 如果响应为 null
     */
    public ServletResponseWrapper(ServletResponse response) {
        if (response == null) {
            throw new IllegalArgumentException("Response cannot be null");
        }
        this.response = response;
    }

    /**
     * 返回被包装的 ServletResponse 对象。
     *
     * @return 被包装的 {@link ServletResponse}
     */
    public ServletResponse getResponse() {
        return this.response;
    }

    /**
     * 设置要被包装的响应对象。
     *
     * @param response 要安装的 {@link ServletResponse}
     * @throws java.lang.IllegalArgumentException 如果响应为 null
     */
    public void setResponse(ServletResponse response) {
        if (response == null) {
            throw new IllegalArgumentException("Response cannot be null");
        }
        this.response = response;
    }

    /**
     * 此方法的默认行为是调用被包装响应对象上的 setCharacterEncoding(String charset) 方法。
     *
     * @since Servlet 2.4
     */
    @Override
    public void setCharacterEncoding(String charset) {
        this.response.setCharacterEncoding(charset);
    }

    /**
     * 此方法的默认行为是调用被包装响应对象上的 getCharacterEncoding() 方法。
     */
    @Override
    public String getCharacterEncoding() {
        return this.response.getCharacterEncoding();
    }

    /**
     * 此方法的默认行为是调用被包装响应对象上的 getOutputStream() 方法。
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return this.response.getOutputStream();
    }

     /**
     * 此方法的默认行为是调用被包装响应对象上的 getWriter() 方法。
     */
    @Override
    public PrintWriter getWriter() throws IOException {
        return this.response.getWriter();
    }

    /**
     * 此方法的默认行为是调用被包装响应对象上的 setContentLength(int len) 方法。
     */
    @Override
    public void setContentLength(int len) {
        this.response.setContentLength(len);
    }

    /**
     * 此方法的默认行为是调用被包装响应对象上的 setContentLengthLong(long len) 方法。
     */
    @Override
    public void setContentLengthLong(long len) {
        this.response.setContentLengthLong(len);
    }

    /**
     * 此方法的默认行为是调用被包装响应对象上的 setContentType(String type) 方法。
     */
    @Override
    public void setContentType(String type) {
        this.response.setContentType(type);
    }

    /**
     * 此方法的默认行为是调用被包装响应对象上的 getContentType() 方法。
     *
     * @since Servlet 2.4
     */
    @Override
    public String getContentType() {
        return this.response.getContentType();
    }

    /**
     * 此方法的默认行为是调用被包装响应对象上的 setBufferSize(int size) 方法。
     */
    @Override
    public void setBufferSize(int size) {
        this.response.setBufferSize(size);
    }

    /**
     * 此方法的默认行为是调用被包装响应对象上的 getBufferSize() 方法。
     */
    @Override
    public int getBufferSize() {
        return this.response.getBufferSize();
    }

    /**
     * 此方法的默认行为是调用被包装响应对象上的 flushBuffer() 方法。
     */
    @Override
    public void flushBuffer() throws IOException {
        this.response.flushBuffer();
    }

    /**
     * 此方法的默认行为是调用被包装响应对象上的 isCommitted() 方法。
     */
    @Override
    public boolean isCommitted() {
        return this.response.isCommitted();
    }

    /**
     * 此方法的默认行为是调用被包装响应对象上的 reset() 方法。
     */
    @Override
    public void reset() {
        this.response.reset();
    }

    /**
     * 此方法的默认行为是调用被包装响应对象上的 resetBuffer() 方法。
     */
    @Override
    public void resetBuffer() {
        this.response.resetBuffer();
    }

    /**
     * 此方法的默认行为是调用被包装响应对象上的 setLocale(Locale loc) 方法。
     */
    @Override
    public void setLocale(Locale loc) {
        this.response.setLocale(loc);
    }

    /**
     * 此方法的默认行为是调用被包装响应对象上的 getLocale() 方法。
     */
    @Override
    public Locale getLocale() {
        return this.response.getLocale();
    }

    /**
     * （递归地）检查此 ServletResponseWrapper 是否包装了指定的 {@link ServletResponse} 实例。
     *
     * @param wrapped 要搜索的 ServletResponse 实例
     * @return 如果此 ServletResponseWrapper 包装了给定的 ServletResponse 实例则返回 true，否则返回 false
     * @since Servlet 3.0
     */
    public boolean isWrapperFor(ServletResponse wrapped) {
        if (response == wrapped) {
            return true;
        } else if (response instanceof ServletResponseWrapper) {
            return ((ServletResponseWrapper) response).isWrapperFor(wrapped);
        } else {
            return false;
        }
    }


    /**
     * （递归地）检查此 ServletResponseWrapper 是否包装了指定类类型的 {@link ServletResponse}。
     *
     * @param wrappedType 要搜索的 ServletResponse 类类型
     * @return 如果此 ServletResponseWrapper 包装了给定类类型的 ServletResponse 则返回 true，否则返回 false
     * @throws IllegalArgumentException 如果给定类未实现 {@link ServletResponse}
     * @since Servlet 3.0
     */
    public boolean isWrapperFor(Class<?> wrappedType) {
        if (!ServletResponse.class.isAssignableFrom(wrappedType)) {
            throw new IllegalArgumentException("Given class " + wrappedType.getName() + " not a subinterface of "
                    + ServletResponse.class.getName());
        }
        if (wrappedType.isAssignableFrom(response.getClass())) {
            return true;
        } else if (response instanceof ServletResponseWrapper) {
            return ((ServletResponseWrapper) response).isWrapperFor(wrappedType);
        } else {
            return false;
        }
    }

}
