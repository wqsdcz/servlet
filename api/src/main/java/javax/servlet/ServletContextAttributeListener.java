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

import java.util.EventListener;

/**
 * 用于接收关于ServletContext属性更改通知事件的接口。
 *
 * <p>
 * 为接收这些通知事件，实现类必须在Web应用程序的部署描述符中声明，
 * 使用{@link javax.servlet.annotation.WebListener}注解标注，
 * 或通过{@link ServletContext}上定义的addListener方法之一进行注册。
 *
 * <p>
 * 此接口实现的调用顺序未指定。
 *
 * @see ServletContextAttributeEvent
 *
 * @since Servlet 2.3
 */
public interface ServletContextAttributeListener extends EventListener {

    /**
     * 接收已向ServletContext添加属性的通知。
     *
     * @param event 包含添加属性的ServletContext以及属性名称和值的ServletContextAttributeEvent事件对象
     * @implSpec 默认实现不执行任何操作。
     */
    default public void attributeAdded(ServletContextAttributeEvent event) {
    }

    /**
     * 接收已从ServletContext中移除属性的通知。
     *
     * @param event 包含被移除属性的ServletContext以及属性名称和值的ServletContextAttributeEvent事件对象
     * @implSpec 默认实现不执行任何操作。
     */
    default public void attributeRemoved(ServletContextAttributeEvent event) {
    }

    /**
     * 接收ServletContext中属性已被替换的通知。
     *
     * @param event 包含发生属性替换的ServletContext以及属性名称及其旧值的ServletContextAttributeEvent事件对象
     * @implSpec 默认实现不执行任何操作。
     */
    default public void attributeReplaced(ServletContextAttributeEvent event) {
    }
}
