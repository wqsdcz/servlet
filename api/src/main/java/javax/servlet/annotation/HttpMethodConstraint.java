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

package javax.servlet.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

/**
 * 这个注解用于 {@link ServletSecurity} 注解的内部，用于表示针对特定 HTTP 协议消息的安全约束。
 *
 * @since Servlet 3.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpMethodConstraint {

    /**
     * HTTP协议的方法的名称
     *
     * @return 一种 HTTP 协议的方法的名称。<code>value</code> 不可以为空，也不可以为空字符串，并且必须是符合 RFC 2616 规定的合法 HTTP 方法名称。
     */
    String value();

    /**
     * 默认的授权语义。当<code>rolesAllowed</code>返回非空数组时，此值无效，且当为<tt>rolesAllowed</tt>指定非空数组时不应设置此值。
     *
     * @return 当<code>rolesAllowed</code>返回空数组（即长度为零的数组）时所应用的{@link EmptyRoleSemantic}。
     */
    EmptyRoleSemantic emptyRoleSemantic() default EmptyRoleSemantic.PERMIT;

    /**
     * 这些数据的保护要求（即是否需要使用 SSL/TLS 加密协议）必须在接收请求的连接中得到满足。
     *
     * @return 返回 {@link TransportGuarantee} 类型值，用于表明连接必须提供的数据保护级别。
     */
    TransportGuarantee transportGuarantee() default TransportGuarantee.NONE;

    /**
     * 授权角色的名称集合。
     *
     * 在 rolesAllowed 中出现重复角色名的情形将被视为无实际意义，并可能在注解的运行期处理过程中被忽略。字符串 <tt>"*"</tt> 作为角色名称时不具有特殊含义（即使出现在 rolesAllowed 中）。
     *
     * @return 返回一个包含零个或多个角色名称的数组。当数组为空时，其具体语义取决于 <code>emptyRoleSemantic</code> 方法的返回值：
     *          若 <code>emptyRoleSemantic</code> 返回 <tt>DENY</tt> 且 <code>rolesAllowed</code> 返回空数组时，将无条件拒绝访问（与认证状态和用户身份无关）；
     *          反之，若 <code>emptyRoleSemantic</code> 返回 <code>PERMIT</code>，则表示无条件允许访问（与认证状态和用户身份无关）；
     *          当数组包含一个或多个角色名称时，表示访问必须满足用户至少属于其中一个命名角色的条件（此时 <code>emptyRoleSemantic</code> 的返回值将被忽略）。
     */
    String[] rolesAllowed() default {};
}
