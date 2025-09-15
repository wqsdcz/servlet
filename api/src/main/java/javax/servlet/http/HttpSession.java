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

import java.util.Enumeration;
import javax.servlet.ServletContext;

/**
 * 一种在多个页面请求或访问网站时，提供了识别用户并存储用户信息的方法。
 *
 * <p>
 *     Servlet容器使用此接口在HTTP客户端和HTTP服务器之间创建会话。
 *     会话会在特定时间段内持续存在，跨越用户的多个连接或页面请求。
 *     会话通常对应一个用户，该用户可能多次访问网站。
 *     服务器可以通过多种方式维护会话，例如：使用Cookie或URL重写。
 *
 * <p>
 *     此接口允许Servlet：
 *     <ul>
 *         <li>查看和操作会话信息，例如：会话标识符、创建时间和最后访问时间
 *         <li>将对象绑定到会话，使用户信息在多个用户连接间持久保持
 *     </ul>
 *
 * <p>
 *     当应用程序在会话中存储或移除对象时，会话会检查对象是否实现了{@link HttpSessionBindingListener}。
 *     如果实现，Servlet会通知对象它已被绑定到会话或从会话中解除绑定。
 *     通知在绑定方法完成后发送。
 *     对于失效或过期的会话，通知在会话失效或过期后发送。
 *
 * <p>
 *     在分布式容器环境中，当容器在虚拟机之间迁移会话时，所有实现{@link HttpSessionActivationListener}接口的会话属性都会收到通知。
 *
 * <p>
 *     Servlet应该能够处理客户端选择不加入会话的情况，例如：故意关闭Cookie时。
 *     在客户端加入会话之前，<code>isNew</code>返回<code>true</code>。
 *     如果客户端选择不加入会话，则每次请求时<code>getSession</code>将返回不同的会话，且<code>isNew</code>将始终返回<code>true</code>。
 *
 * <p>
 *     会话信息仅作用于当前Web应用程序（<code>ServletContext</code>）范围内，因此在一个上下文中存储的信息不会直接在另一个上下文中可见。
 *
 * @author Various
 *
 * @see HttpSessionBindingListener
 * @see HttpSessionContext
 */
public interface HttpSession {

    /**
     * 返回此会话的创建时间，以自1970年1月1日GMT午夜以来的毫秒数计量。
     *
     * @return 一个<code>long</code>值，指定此会话的创建时间，表示为自1970年1月1日GMT以来的毫秒数
     * @exception IllegalStateException 如果对已失效的会话调用此方法
     */
    public long getCreationTime();

    /**
     * 返回包含分配给此会话的唯一标识符的字符串。该标识符由Servlet容器分配且依赖于具体实现。
     *
     * @return 指定分配给此会话的标识符的字符串
     */
    public String getId();

    /**
     * 返回客户端最后一次发送与此会话关联的请求的时间，表示为自1970年1月1日GMT午夜以来的毫秒数，该时间以容器接收到请求的时刻为准。
     *
     * <p>应用程序执行的操作（如获取或设置与会话关联的值）不会影响访问时间。
     *
     * @return 一个<code>long</code>值，表示客户端最后一次发送与此会话关联请求的时间，表示为自1970年1月1日GMT以来的毫秒数
     * @exception IllegalStateException 如果对已失效的会话调用此方法
     */
    public long getLastAccessedTime();

    /**
     * 返回此会话所属的ServletContext。
     *
     * @return 当前Web应用程序的ServletContext对象
     * @since Servlet 2.3
     */
    public ServletContext getServletContext();

    /**
     * 指定客户端请求之间的最长时间（以秒为单位），超过此时间Servlet容器将使此会话失效。
     *
     * <p>零或负值的<tt>interval</tt>表示会话永不过期。
     *
     * @param interval 指定秒数的整数值
     */
    public void setMaxInactiveInterval(int interval);

    /**
     * 返回Servlet容器在客户端访问之间保持此会话开启的最大时间间隔（以秒为单位）。
     * 超过此时间间隔后，Servlet容器将使会话失效。
     * 该最大时间间隔可通过<code>setMaxInactiveInterval</code>方法设置。
     *
     * <p>返回零或负数表示会话永不过期。
     *
     * @return 一个整数，指定此会话在客户端请求之间保持开启的秒数
     * @see #setMaxInactiveInterval
     */
    public int getMaxInactiveInterval();

    /**
     * @deprecated 自版本2.1起，此方法已弃用且无替代方案。它将在Jakarta Servlets的未来版本中被移除。
     *
     * @return 此会话的 {@link HttpSessionContext}
     */
    @Deprecated
    public HttpSessionContext getSessionContext();

    /**
     * 返回此会话中与指定名称绑定的对象，如果该名称下没有绑定对象则返回<code>null</code>。
     *
     * @param name 指定对象名称的字符串
     * @return 具有指定名称的对象
     * @exception IllegalStateException 如果对已失效的会话调用此方法
     */
    public Object getAttribute(String name);

    /**
     * @deprecated 自版本2.2起，此方法已被 {@link #getAttribute} 取代。
     *
     * @param name 指定对象名称的字符串
     * @return 具有指定名称的对象
     * @exception IllegalStateException 如果对已失效的会话调用此方法
     */
    @Deprecated
    public Object getValue(String name);

    /**
     * 返回一个包含所有绑定到此会话的对象名称的<code>String</code>对象<code>Enumeration</code>。
     *
     * @return 一个<code>String</code>对象的<code>Enumeration</code>，指定所有绑定到此会话的对象名称
     * @exception IllegalStateException 如果对已失效的会话调用此方法
     */
    public Enumeration<String> getAttributeNames();

    /**
     * @deprecated 自版本2.2起，此方法已被 {@link #getAttributeNames} 取代
     *
     * @return 一个<code>String</code>对象数组，指定所有绑定到此会话的对象名称
     * @exception IllegalStateException 如果对已失效的会话调用此方法
     */
    @Deprecated
    public String[] getValueNames();


    /**
     * 使用指定名称将对象绑定到此会话。如果已存在同名对象绑定到会话，则该对象将被替换。
     *
     * <p>
     *     此方法执行后，如果新对象实现了<code>HttpSessionBindingListener</code>，
     *     容器将调用<code>HttpSessionBindingListener.valueBound</code>。
     *     然后容器会通知Web应用程序中的所有<code>HttpSessionAttributeListener</code>。
     *
     * <p>
     *     如果已绑定到此会话的同名对象实现了<code>HttpSessionBindingListener</code>，
     *     则会调用其<code>HttpSessionBindingListener.valueUnbound</code>方法。
     *
     * <p>如果传入的值为null，则效果与调用<code>removeAttribute()</code>相同。
     *
     *
     * @param name  绑定对象的名称；不能为null
     * @param value 要绑定的对象
     * @exception IllegalStateException 如果对已失效的会话调用此方法
     */
    public void setAttribute(String name, Object value);

    /**
     * @deprecated 自版本2.2起，此方法已被 {@link #setAttribute} 取代
     *
     * @param name  绑定对象的名称；不能为null
     * @param value 要绑定的对象；不能为null
     * @exception IllegalStateException 如果对已失效的会话调用此方法
     */
    @Deprecated
    public void putValue(String name, Object value);

    /**
     * 从此会话中移除与指定名称绑定的对象。如果会话中没有与该名称绑定的对象，则此方法不执行任何操作。
     *
     * <p>
     *     此方法执行后，如果对象实现了<code>HttpSessionBindingListener</code>，
     *     容器将调用<code>HttpSessionBindingListener.valueUnbound</code>。
     *     然后容器会通知Web应用程序中的所有<code>HttpSessionAttributeListener</code>。
     *
     * @param name 要从此会话中移除的对象的名称
     * @exception IllegalStateException 如果对已失效的会话调用此方法
     */
    public void removeAttribute(String name);

    /**
     * @deprecated 自版本2.2起，此方法已被 {@link #removeAttribute} 取代
     *
     * @param name 要从此会话中移除的对象的名称
     * @exception IllegalStateException 如果对已失效的会话调用此方法
     */
    @Deprecated
    public void removeValue(String name);

    /**
     * 使此会话失效，然后解除绑定到该会话的所有对象。
     *
     * @exception IllegalStateException 如果对已失效的会话调用此方法
     */
    public void invalidate();

    /**
     * 如果客户端尚未知晓该会话或客户端选择不加入会话，则返回<code>true</code>。
     * 例如，如果服务器仅使用基于Cookie的会话，而客户端禁用了Cookie的使用，则每次请求时会话都将被视为新会话。
     *
     * @return 如果服务器已创建会话但客户端尚未加入，则返回<code>true</code>
     * @exception IllegalStateException 如果对已失效的会话调用此方法
     */
    public boolean isNew();

}
