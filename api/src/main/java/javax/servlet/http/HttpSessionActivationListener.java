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
 * 绑定到会话的对象可以监听容器事件，这些事件会通知它们会话即将被钝化和激活。
 * 需要在虚拟机之间迁移会话或持久化会话的容器必须通知所有实现了HttpSessionActivationListener
 * 接口的会话绑定属性。
 *
 * @since Servlet 2.3
 */
public interface HttpSessionActivationListener extends EventListener {

    /**
     * 通知会话即将被钝化。
     *
     * @implSpec 默认实现不执行任何操作。
     * @param se 指示会话钝化的{@link HttpSessionEvent}事件
     */
    default public void sessionWillPassivate(HttpSessionEvent se) {
    }

    /**
     * 通知会话刚刚被激活。
     *
     * @implSpec 默认实现不执行任何操作。
     * @param se 指示会话激活的{@link HttpSessionEvent}事件
     */
    default public void sessionDidActivate(HttpSessionEvent se) {
    }
}
