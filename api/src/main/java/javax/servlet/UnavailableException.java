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
 * 定义一个 servlet 或 filter 抛出的异常，用于指示该组件永久或暂时不可用。
 *
 * <p>
 * 当 servlet 或过滤器永久不可用时，表示组件本身存在问题，在采取某些措施之前无法处理请求。
 * 例如，servlet 可能配置错误，或过滤器的状态可能已损坏。组件应记录错误信息以及需要采取的纠正措施。
 *
 * <p>
 * 如果由于系统级问题导致 servlet 或过滤器暂时无法处理请求，则属于暂时不可用。
 * 例如，第三层服务器可能无法访问，或者内存或磁盘空间不足无法处理请求。
 * 系统管理员可能需要采取纠正措施。
 *
 * <p>
 * Servlet 容器可以安全地以相同方式处理这两种不可用异常。然而，有效处理暂时不可用情况能使 servlet 容器更加健壮。
 * 具体来说，servlet 容器可能会根据异常建议的时间段内阻塞对该 servlet 或过滤器的请求，而不是直接拒绝请求直到 servlet 容器重启。
 *
 * @author 多方作者
 */
public class UnavailableException extends ServletException {

    private static final long serialVersionUID = 5622686609215003468L;

    private Servlet servlet; // what's unavailable
    private boolean permanent; // needs admin action?
    private int seconds; // unavailability estimate

    /**
     * @deprecated 自 Java Servlet API 2.2 起，改用 {@link #UnavailableException(String)}。
     *
     * @param servlet 不可用的 <code>Servlet</code> 实例
     * @param msg     指定描述性消息的 <code>String</code>
     */
    @Deprecated
    public UnavailableException(Servlet servlet, String msg) {
        super(msg);
        this.servlet = servlet;
        permanent = true;
    }


    /**
     * @deprecated 自 Java Servlet API 2.2 起，改用 {@link #UnavailableException(String, int)}。
     *
     * @param seconds 指定 servlet 预计不可用秒数的整数；如果为零或负数，表示 servlet 无法预估时间
     * @param servlet 不可用的 <code>Servlet</code>
     * @param msg     指定描述性消息的 <code>String</code>，可写入日志文件或向用户显示
     */
    @Deprecated
    public UnavailableException(int seconds, Servlet servlet, String msg) {
        super(msg);
        this.servlet = servlet;
        if (seconds <= 0)
            this.seconds = -1;
        else
            this.seconds = seconds;
        permanent = false;
    }

    /**
     * 构造一个新异常，其中包含指示 servlet 永久不可用的描述性消息。
     *
     * @param msg 指定描述性消息的 <code>String</code>
     */
    public UnavailableException(String msg) {
        super(msg);

        permanent = true;
    }

    /**
     * 构造一个新异常，其中包含指示 servlet 暂时不可用的描述性消息，并给出预计不可用时间的估计值。
     *
     * <p>
     *     在某些情况下，servlet 可能无法给出估计值。
     *     例如，servlet 可能知道其依赖的服务器未运行，但无法报告需要多长时间才能恢复功能。
     *     这种情况下可以使用零或负值的 <code>seconds</code> 参数来表示。
     *
     * @param msg     指定描述性消息的 <code>String</code>，可写入日志文件或向用户显示
     * @param seconds 指定 servlet 预计不可用秒数的整数；如果为零或负数，表示 servlet 无法给出预估时间
     */
    public UnavailableException(String msg, int seconds) {
        super(msg);

        if (seconds <= 0)
            this.seconds = -1;
        else
            this.seconds = seconds;

        permanent = false;
    }

    /**
     * 返回一个 <code>boolean</code> 值，指示 servlet 是否永久不可用。如果是永久不可用，
     * 则表示 servlet 存在问题，系统管理员必须采取一些纠正措施。
     *
     * @return 如果 servlet 永久不可用则返回 <code>true</code>；如果 servlet 可用或暂时不可用则返回 <code>false</code>
     */
    public boolean isPermanent() {
        return permanent;
    }


    /**
     * @deprecated 自 Java Servlet API 2.2 起，无替代方法。返回报告其不可用性的 servlet。
     *
     * @return 正在抛出 <code>UnavailableException</code> 的 <code>Servlet</code> 对象
     */
    @Deprecated
    public Servlet getServlet() {
        return servlet;
    }

    /**
     * 返回 servlet 预计将暂时不可用的秒数。
     *
     * <p>
     *     如果此方法返回负数，则表示 servlet 永久不可用或无法提供不可用时间的估计值。
     *     不会对自异常首次报告以来经过的时间进行校正。
     *
     * @return 指定 servlet 暂时不可用秒数的整数，如果 servlet 永久不可用或无法给出估计值则返回负数
     */
    public int getUnavailableSeconds() {
        return permanent ? -1 : seconds;
    }
}
