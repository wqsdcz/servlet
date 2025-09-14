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
 * 确保Servlet每次只能处理一个请求。此接口没有定义任何方法。
 *
 * <p>
 *     如果Servlet实现了此接口，将<i>保证</i>不会有多个线程同时执行该Servlet的<code>service</code>方法。
 *     Servlet容器可以通过同步访问Servlet单实例，或者通过维护一个Servlet实例池并将每个新请求分发给空闲的Servlet来实现此保证。
 *
 * <p>
 *     请注意SingleThreadModel并不能解决所有线程安全问题。
 *     例如，即使使用SingleThreadModel servlet，会话属性和静态变量仍然可能被多个线程的多个请求同时访问。
 *     建议开发者采取其他方式来解决这些问题，而不是实现此接口，例如避免使用实例变量或同步访问这些资源的代码块。
 *     此接口在Servlet API 2.4版本中已被弃用。
 *
 * @author Various
 * @deprecated 自Java Servlet API 2.4起弃用，没有直接替代方案。
 */
@Deprecated
public interface SingleThreadModel {
}
