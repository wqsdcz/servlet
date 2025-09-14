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
 * 用于接收关于ServletRequest属性更改通知事件的接口。
 *
 * <p>
 *     当请求处于Web应用程序范围内时将会生成通知。
 *     ServletRequest在即将进入Web应用程序的第一个servlet或过滤器时被定义为进入Web应用程序范围，
 *     在退出链中的最后一个servlet或第一个过滤器时被定义为离开范围。
 *
 * <p>
 *     为接收这些通知事件，实现类必须在Web应用程序的部署描述符中声明，
 *     使用{@link javax.servlet.annotation.WebListener}注解标注，
 *     或通过{@link ServletContext}上定义的addListener方法之一进行注册。
 *
 * <p>
 * 此接口实现的调用顺序未指定。
 *
 * @since Servlet 2.4
 */
public interface ServletRequestAttributeListener extends EventListener {

    /**
     * 接收已向ServletRequest添加属性的通知。
     *
     * @param srae 包含ServletRequest以及被添加属性名称和值的ServletRequestAttributeEvent事件对象
     * @implSpec 默认实现不执行任何操作。
     */
    default public void attributeAdded(ServletRequestAttributeEvent srae) {
    }

    /**
     * 接收已从ServletRequest中移除属性的通知。
     *
     * @param srae 包含ServletRequest以及被移除属性名称和值的ServletRequestAttributeEvent事件对象
     * @implSpec 默认实现不执行任何操作。
     */
    default public void attributeRemoved(ServletRequestAttributeEvent srae) {
    }

    /**
     * 接收ServletRequest上的属性已被替换的通知。
     *
     * @param srae 包含ServletRequest以及被替换属性的名称和（旧）值的ServletRequestAttributeEvent事件对象
     * @implSpec 默认实现不执行任何操作。
     */
    default public void attributeReplaced(ServletRequestAttributeEvent srae) {
    }
}
