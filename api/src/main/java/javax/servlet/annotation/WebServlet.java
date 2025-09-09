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

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;

/**
 * 用于声明servlet的注解。
 *
 * <p>在部署时，由容器负责处理这个注解，并且在指定的 URL 模式下提供了相应的 servlet。
 * 
 * @see javax.servlet.Servlet
 *
 * @since Servlet 3.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebServlet {

    /**
     * 这个 servlet 的名称
     *
     * @return 这个 servlet 的名称
     */
    String name() default "";

    /**
     * 这个 servlet 的 URL 模式
     *
     * @return 这个 servlet 的 URL 模式
     */
    String[] value() default {};

    /**
     * 这个 servlet 的 URL 模式
     *
     * @return 这个 servlet 的 URL 模式
     */
    String[] urlPatterns() default {};

    /**
     * 这个 servlet 的启动时加载顺序
     *
     * @return 这个 servlet 的启动时加载顺序
     */
    int loadOnStartup() default -1;

    /**
     * 这个 servlet 的 初始化参数
     *
     * @return 这个 servlet 的 初始化参数
     */
    WebInitParam[] initParams() default {};

    /**
     * 声明这个 servlet 是否支持异步的工作方式
     *
     * @return {@code true} 如果这个 servlet 支持异步的工作方式
     * @see javax.servlet.ServletRequest#startAsync
     * @see javax.servlet.ServletRequest#startAsync( javax.servlet.ServletRequest,javax.servlet.ServletResponse)
     */
    boolean asyncSupported() default false;

    /**
     * 这个 servlet 的 small-icon
     *
     * @return 这个 servlet 的 small-icon
     */
    String smallIcon() default "";

    /**
     * 这个 servlet 的 large-icon
     *
     * @return 这个 servlet 的 large-icon
     */
    String largeIcon() default "";

    /**
     * 这个 servlet 的说明描述
     *
     * @return 这个 servlet 的 large-icon
     */
    String description() default "";

    /**
     * 这个 servlet 的展示名称
     *
     * @return 这个 servlet 的展示名称
     */
    String displayName() default "";

}
