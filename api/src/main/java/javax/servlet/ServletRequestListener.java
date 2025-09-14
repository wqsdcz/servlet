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
 * 用于接收关于请求进入和离开Web应用程序范围的通知事件接口。
 *
 * <p>
 *     当ServletRequest即将进入Web应用程序的第一个servlet或filter时，被定义为进入Web应用程序范围；
 *     当它退出链中的最后一个servlet或第一个过滤器时，被定义为离开范围。
 *
 * <p>
 *     为接收这些通知事件，实现类必须在Web应用程序的部署描述符中声明，
 *     使用{@link javax.servlet.annotation.WebListener}注解标注，
 *     或通过{@link ServletContext}上定义的addListener方法之一进行注册。
 *
 * <p>
 *     此接口的实现按照声明顺序调用其{@link #requestInitialized}方法，并按照逆序调用其{@link #requestDestroyed}方法。
 *
 * @since Servlet 2.4
 */
public interface ServletRequestListener extends EventListener {

    /**
     * 接收ServletRequest即将离开Web应用程序范围的通知。
     *
     * @param sre 包含ServletRequest和代表Web应用程序的ServletContext的ServletRequestEvent事件对象
     * @implSpec 默认实现不执行任何操作。
     */
    default public void requestDestroyed(ServletRequestEvent sre) {
    }

    /**
     * 接收ServletRequest即将进入Web应用程序范围的通知。
     *
     * @param sre 包含ServletRequest和代表Web应用程序的ServletContext的ServletRequestEvent事件对象
     * @implSpec 默认实现不执行任何操作。
     */
    default public void requestInitialized(ServletRequestEvent sre) {
    }
}
