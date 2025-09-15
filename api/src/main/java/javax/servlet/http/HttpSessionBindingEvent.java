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

/**
 * 此类事件会在两种情况下被发送：
 * <ul>
 *     <li>一是实现了 {@link HttpSessionBindingListener} 的对象在绑定到会话或从会话解绑时；</li>
 *     <li>二是当部署描述符中配置的 {@link HttpSessionAttributeListener} 监测到会话中的属性被绑定、解绑或替换时。</li>
 * </ul>
 *
 * <p>
 *     会话通过调用 <code>HttpSession.setAttribute</code> 来绑定对象，
 *     通过调用<code>HttpSession.removeAttribute</code> 来解绑对象。
 *
 * @author Various
 *
 * @see HttpSession
 * @see HttpSessionBindingListener
 * @see HttpSessionAttributeListener
 */
public class HttpSessionBindingEvent extends HttpSessionEvent {

    private static final long serialVersionUID = 7308000419984825907L;

    /* The name to which the object is being bound or unbound */
    private String name;

    /* The object is being bound or unbound */
    private Object value;

    /**
     * 构造一个事件，用于通知对象它已被绑定到会话或从会话解绑。
     * 要接收此事件，对象必须实现 {@link HttpSessionBindingListener}。
     *
     * @param session 对象绑定或解绑所属的会话
     * @param name    对象绑定或解绑时使用的名称
     * @see #getName
     * @see #getSession
     */
    public HttpSessionBindingEvent(HttpSession session, String name) {
        super(session);
        this.name = name;
    }


    /**
     * 构造一个事件，用于通知对象它已被绑定到会话或从会话解绑。
     * 要接收此事件，对象必须实现 {@link HttpSessionBindingListener}。
     *
     * @param session 对象绑定或解绑所属的会话
     * @param name    对象绑定或解绑时使用的名称
     * @param value   被绑定或解绑的对象
     * @see #getName
     * @see #getSession
     */
    public HttpSessionBindingEvent(HttpSession session, String name, Object value) {
        super(session);
        this.name = name;
        this.value = value;
    }

    /** 返回发生变化的会话。 */
    @Override
    public HttpSession getSession() {
        return super.getSession();
    }

    /**
     * 返回属性绑定到会话或从会话解绑时使用的名称。
     *
     * @return 指定属性绑定到会话或从会话解绑时使用的名称的字符串
     */
    public String getName() {
        return name;
    }

    /**
     * 返回已添加、移除或替换的属性的值。
     * 如果属性是添加（或绑定）的，则此为属性的值。
     * 如果属性是移除（或解绑）的，则此为被移除属性的值。
     * 如果属性是被替换的，则此为属性的旧值。
     *
     * @return 已添加、移除或替换的属性的值
     * @since Servlet 2.3
     */
    public Object getValue() {
        return this.value;
    }
}
