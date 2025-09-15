/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package javax.servlet.http;

import java.util.EventListener;

/**
 * 用于接收关于HttpSession ID变化通知事件的接口。
 *
 * <p>
 *     要接收这些通知事件，实现类必须在Web应用程序的部署描述符中声明、
 *     使用{@link javax.servlet.annotation.WebListener}注解标注、
 *     或通过{@link javax.servlet.ServletContext}上定义的addListener方法之一进行注册。
 *
 * <p>此接口实现的调用顺序未指定。
 *
 * @since Servlet 3.1
 */
public interface HttpSessionIdListener extends EventListener {

    /**
     * 接收会话ID已在会话中发生变化的通知。
     *
     * @param event        包含会话以及被替换属性的名称和（旧）值的HttpSessionBindingEvent事件对象
     * @param oldSessionId 旧的会话ID
     */
    public void sessionIdChanged(HttpSessionEvent event, String oldSessionId);

}
