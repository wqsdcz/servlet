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
 * 用于接收关于HttpSession属性变化通知事件的接口。
 *
 * <p>
 *     要接收这些通知事件，实现类必须在Web应用程序的部署描述符中声明、
 *     使用{@link javax.servlet.annotation.WebListener}注解标注、
 *     或通过{@link javax.servlet.ServletContext}上定义的addListener方法之一进行注册。
 *
 * <p>
 * 此接口实现的调用顺序未指定。
 *
 * @since Servlet 2.3
 */
public interface HttpSessionAttributeListener extends EventListener {

    /**
     * 接收属性已添加到会话的通知。
     *
     * @param event 包含会话以及添加的属性名称和值的HttpSessionBindingEvent事件对象
     */
    default public void attributeAdded(HttpSessionBindingEvent event) {
    }

    /**
     * 接收属性已从会话中移除的通知。
     *
     * @param event 包含会话以及被移除的属性名称和值的HttpSessionBindingEvent事件对象
     */
    default public void attributeRemoved(HttpSessionBindingEvent event) {
    }

    /**
     * 接收属性已在会话中被替换的通知。
     *
     * @param event 包含会话以及被替换属性的名称和（旧）值的HttpSessionBindingEvent事件对象
     */
    default public void attributeReplaced(HttpSessionBindingEvent event) {
    }

}
