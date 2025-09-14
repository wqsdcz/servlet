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
 * 用于接收关于ServletContext生命周期更改通知事件的接口。
 *
 * <p>
 *     为接收这些通知事件，实现类必须在Web应用程序的部署描述符中声明，
 *     使用{@link javax.servlet.annotation.WebListener}注解标注，
 *     或通过{@link ServletContext}上定义的addListener方法之一进行注册。
 *
 * <p>
 *     此接口的实现按照声明顺序调用其{@link #contextInitialized}方法，并按照逆序调用其{@link #contextDestroyed}方法。
 *
 * @see ServletContextEvent
 *
 * @since Servlet 2.3
 */
public interface ServletContextListener extends EventListener {

    /**
     * 接收Web应用程序初始化过程开始的通知。
     *
     * <p>在初始化Web应用程序中的任何过滤器或Servlet之前，会通知所有ServletContextListener上下文初始化事件。
     *
     * @param sce 包含正在初始化的ServletContext的ServletContextEvent事件对象
     * @implSpec 默认实现不执行任何操作。
     */
    default public void contextInitialized(ServletContextEvent sce) {
    }

    /**
     * 接收ServletContext即将被关闭的通知。
     *
     * <p>在所有ServletContextListener接收到上下文销毁通知之前，所有的servlet和过滤器都已被销毁。
     *
     * @param sce 包含正在被销毁的ServletContext的ServletContextEvent事件对象
     * @implSpec 默认实现不执行任何操作。
     */
    default public void contextDestroyed(ServletContextEvent sce) {
    }
}
