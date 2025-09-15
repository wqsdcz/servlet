/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates and others.
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
 * <p>Servlet映射类型的枚举。</p>
 *
 * @since 4.0
 */
public enum MappingMatch {
    /**
     * <p>当映射是通过与应用程序上下文根完全匹配实现时使用。</p>
     */
    CONTEXT_ROOT,
    /**
     * <p>当映射是通过与应用程序的默认Servlet（即'{@code /}'字符）匹配实现时使用。</p>
     */
    DEFAULT,
    /**
     * <p>当映射是通过与传入请求完全匹配实现时使用。</p>
     */
    EXACT,
    /**
     * <p>当映射是通过扩展名（如"{@code *.xhtml}"）实现时使用。</p>
     */
    EXTENSION,
    /**
     * <p>当映射是通过路径模式（如"{@code /faces/*}"）实现时使用。</p>
     */
    PATH
}
