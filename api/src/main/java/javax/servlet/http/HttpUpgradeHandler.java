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

/**
 * 该接口封装了协议升级处理过程。HttpUpgradeHandler 的实现将允许Servlet容器与其进行通信。
 *
 * @since Servlet 3.1
 */
public interface HttpUpgradeHandler {

    /**
     * 当HTTP升级过程完成且升级后的连接准备开始使用新协议时调用。
     *
     * @param wc 与此升级请求关联的WebConnection对象
     */
    public void init(WebConnection wc);

    /**
     * 当客户端断开连接时调用。
     */
    public void destroy();
}
