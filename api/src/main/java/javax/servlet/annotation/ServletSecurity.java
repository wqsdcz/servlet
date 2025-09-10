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
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 这个注解被用在 Servlet 的实现类上，用来指定 Servlet 容器施加的安全约束（在HTTP协议的消息上）。
 * Servlet容器将在映射到该注解类的Servlet所对应的url-patterns上强制执行这些约束。
 *
 * @since Servlet 3.0
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServletSecurity {

    /**
     * 定义“rolesAllowed”数组为空时的访问语义。
     */
    enum EmptyRoleSemantic {
    /**
     * 允许访问，不受身份验证状态和身份的影响
     */
    PERMIT,
    /**
     * 拒绝访问，不受身份验证状态和身份的影响
     */
    DENY
    }

    /**
     * 定义传输过程所必须满足的数据保护要求
     */
    enum TransportGuarantee {
        /**
         * 传输过程不为任何用户数据提供保护。
         */
        NONE,
        /**
         * 所有用户数据必须进行加密传输（通常使用SSL/TLS）。
         */
        CONFIDENTIAL
    }

    /**
     * 获取定义应用于所有未包含在 <tt>httpMethodConstraints</tt> 方法返回数组中的 HTTP 方法的保护约束的 {@link HttpConstraint}。
     *
     * @return 一个 <code>HttpConstraint</code> 对象。
     */
    HttpConstraint value() default @HttpConstraint;

    /**
     * 获取HTTP方法特定的约束。每个 {@link HttpMethodConstraint} 指定一个HTTP协议方法并定义应用于该方法的保护设置。
     *
     * @return 返回一个由 {@link HttpMethodConstraint} 元素组成的数组，其中每个元素定义应用于一个HTTP协议方法的保护设置。
     *         对于任何HTTP方法名称，返回的数组中最多只能有一个对应元素。如果返回的数组长度为零，则表示未定义任何HTTP方法特定的约束。
     */
    HttpMethodConstraint[] httpMethodConstraints() default {};
}
