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
 * 此类代表Web应用程序中会话发生变化时的事件通知。
 *
 * @since Servlet 2.3
 */
public class HttpSessionEvent extends java.util.EventObject {

    private static final long serialVersionUID = -7622791603672342895L;

    /**
     * 从给定源构造会话事件。
     *
     * @param source 与此事件对应的{@link HttpSession}
     */
    public HttpSessionEvent(HttpSession source) {
        super(source);
    }

    /**
     * 返回发生变化的会话。
     *
     * @return 此事件对应的{@link HttpSession}
     */
    public HttpSession getSession() {
        return (HttpSession) super.getSource();
    }
}
