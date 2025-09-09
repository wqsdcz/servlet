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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.servlet.DispatcherType;

/**
 * 用于声明servlet的filter的注解。
 *
 * <p>在部署时，由容器负责处理这个注解，并且针对指定的 URL 模式、Servlet 和调度器类型所应用的相应的filter。
 * 
 * @see javax.servlet.Filter
 *
 * @since Servlet 3.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebFilter {

    /**
     * 这个 filter 的说明描述
     *
     * @return 这个 filter 的说明描述
     */
    String description() default "";

    /**
     * 这个 filter 的展示名称
     *
     * @return 这个 filter 的说明描述
     */
    String displayName() default "";

    /**
     * 这个 filter 的初始化参数
     *
     * @return 这个 filter 的说明描述
     */
    WebInitParam[] initParams() default {};

    /**
     * 这个 filter 的名称
     *
     * @return 这个 filter 的名称
     */
    String filterName() default "";

    /**
     * 这个 filter 的 small-icon
     *
     * @return 这个 filter 的 small-icon
     */
    String smallIcon() default "";

    /**
     * 这个 filter 的 large-icon
     *
     * @return 这个 filter 的 large-icon
     */
    String largeIcon() default "";

    /**
     * 这个 filter 作用到的 servlet 的名称
     *
     * @return 这个 filter 作用的 servlet 的名称
     */
    String[] servletNames() default {};

    /**
     * 这个 filter 作用到的 URL 模版。默认值是一个空数组
     *
     * @return 这个 filter 作用到的 URL 模版
     */
    String[] value() default {};

    /**
     * 这个 filter 作用到的 URL 模版
     *
     * @return 这个 filter 作用到的 URL 模版
     */
    String[] urlPatterns() default {};

    /**
     * 这个 filter 作用到的调度器类型
     *
     * @return 这个 filter 作用到的调度器类型
     */
    DispatcherType[] dispatcherTypes() default { DispatcherType.REQUEST };

    /**
     * 声明这个 filter 是否支持异步的工作方式
     *
     * @return {@code true} 如果这个 filter 支持异步的工作方式
     * @see javax.servlet.ServletRequest#startAsync
     * @see javax.servlet.ServletRequest#startAsync( javax.servlet.ServletRequest,javax.servlet.ServletResponse)
     */
    boolean asyncSupported() default false;

}
