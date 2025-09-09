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

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 这个注解用于声明 {@link javax.servlet.ServletContainerInitializer ServletContainerInitializer}表示感兴趣的类。
 *
 * @see javax.servlet.ServletContainerInitializer
 *
 * @since Servlet 3.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlesTypes {

    /**
     * {@link javax.servlet.ServletContainerInitializer ServletContainerInitializer}可以处理的class的集合。
     *
     * <p>如果一个<tt>ServletContainerInitializer</tt>的实现类指定了这个注解，那么Servlet容器必须将该注解所列类类型、及其扩展、实现的所有类组成的<tt>Set</tt>集合，
     * 传递给{@link javax.servlet.ServletContainerInitializer#onStartup}方法（如果未找到匹配的类，则必须传递<tt>null</tt>值）。
     * 
     * @return {@link javax.servlet.ServletContainerInitializer ServletContainerInitializer}表示感兴趣的类
     */
    Class<?>[] value();
}
