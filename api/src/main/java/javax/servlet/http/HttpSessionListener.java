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
 * 用于接收关于HttpSession生命周期变化通知事件的接口。
 *
 * <p>
 *     要接收这些通知事件，实现类必须在Web应用程序的部署描述符中声明、
 *     使用{@link javax.servlet.annotation.WebListener}注解标注、
 *     或通过{@link javax.servlet.ServletContext}上定义的addListener方法之一进行注册。
 *
 * <p>
 *     此接口的实现按其声明顺序调用{@link #sessionCreated}方法，并按逆序调用{@link #sessionDestroyed}方法。
 *
 * @see HttpSessionEvent
 *
 * @since Servlet 2.3
 */
public interface HttpSessionListener extends EventListener {

    /**
     * 接收会话已创建的通知。
     *
     * @implSpec 默认实现不执行任何操作。
     * @param se 包含会话的HttpSessionEvent事件对象
     */
    default public void sessionCreated(HttpSessionEvent se) {
    }

    /**
     * 接收会话即将失效的通知。
     *
     * @implSpec 默认实现不执行任何操作。
     * @param se 包含会话的HttpSessionEvent事件对象
     */
    default public void sessionDestroyed(HttpSessionEvent se) {
    }
}
