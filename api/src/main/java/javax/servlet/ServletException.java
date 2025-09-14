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


/**
 * 定义 servlet 在遇到困难时，可以抛出的通用异常。
 *
 * @author 多方作者
 */
public class ServletException extends Exception {

    private static final long serialVersionUID = 4221302886851315160L;

    private Throwable rootCause;

    /**
     * 构造一个新的 servlet 异常。
     */
    public ServletException() {
        super();
    }

    /**
     * 使用指定消息构造一个新的 servlet 异常。该消息可被写入服务器日志和/或向用户显示。
     *
     * @param message 指定异常消息文本的 <code>String</code>
     */
    public ServletException(String message) {
        super(message);
    }

    /**
     * 当 servlet 需要抛出异常并包含有关导致其正常操作中断的"根本原因"异常信息时（包括描述消息），构造一个新的 servlet 异常。
     *
     * @param message   包含异常消息文本的 <code>String</code>
     * @param rootCause 导致 servlet 正常操作中断的 <code>Throwable</code> 异常，使得此 servlet 异常成为必要
     */
    public ServletException(String message, Throwable rootCause) {
        super(message, rootCause);
        this.rootCause = rootCause;
    }

    /**
     * 当 servlet 需要抛出异常并包含有关导致其正常操作中断的"根本原因"异常信息时，构造一个新的 servlet 异常。
     * 异常消息基于底层异常的本地化消息。
     *
     * <p>
     *     此方法调用 <code>Throwable</code> 异常上的 <code>getLocalizedMessage</code> 方法来获取本地化的异常消息。
     *     当子类化 <code>ServletException</code> 时，可以重写此方法以创建针对特定区域设计设计的异常消息。
     *
     * @param rootCause 导致 servlet 正常操作中断的 <code>Throwable</code> 异常，使得 servlet 异常成为必要
     */
    public ServletException(Throwable rootCause) {
        super(rootCause);
        this.rootCause = rootCause;
    }

    /**
     * 返回导致此 servlet 异常的原因异常。
     *
     * @return 导致此 servlet 异常的 <code>Throwable</code>
     */
    public Throwable getRootCause() {
        return rootCause;
    }
}
