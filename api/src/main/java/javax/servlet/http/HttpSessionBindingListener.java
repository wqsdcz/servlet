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

import java.util.EventListener;

/**
 * 当对象被绑定到会话或从会话解绑时，此接口会通知该对象。
 * 对象通过{@link HttpSessionBindingEvent}对象接收通知。
 * 这可能由于Servlet程序员显式地从会话中解绑属性、会话失效或会话超时导致。
 *
 * @author Various
 *
 * @see HttpSession
 * @see HttpSessionBindingEvent
 *
 */
public interface HttpSessionBindingListener extends EventListener {


    /**
     * 通知对象它正在被绑定到会话，并标识会话。
     *
     * @implSpec 默认实现不执行任何操作。
     * @param event 标识会话的事件对象
     * @see #valueUnbound
     */
    default public void valueBound(HttpSessionBindingEvent event) {
    }


    /**
     * 通知对象它正在从会话解绑，并标识会话。
     *
     * @implSpec 默认实现不执行任何操作。
     * @param event 标识会话的事件对象
     * @see #valueBound
     */
    default public void valueUnbound(HttpSessionBindingEvent event) {
    }
}
