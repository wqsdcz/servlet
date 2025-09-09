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

package javax.servlet;

/**
 * 表示在ServletRequest上发起的异步操作的执行上下文类。
 *
 * <p>
 * AsyncContext通过调用{@link ServletRequest#startAsync()}或{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}方法创建并初始化。
 * 重复调用这些方法将返回相同的AsyncContext实例，并会根据情况进行重新初始化。
 *
 * <p>当异步操作超时时，容器必须执行以下步骤：
 * <ol>
 * <li>调用所有注册在发起异步操作的ServletRequest上的{@link AsyncListener}实例的 {@link AsyncListener#onTimeout onTimeout}方法。</li>
 * <li>如果没有任何监听器调用{@link #complete}或任何{@link #dispatch}方法，则执行错误分发，状态码等于<tt>HttpServletResponse.SC_INTERNAL_SERVER_ERROR</tt>。</li>
 * <li>如果未找到匹配的错误页面，或者错误页面没有调用{@link #complete}或任何{@link #dispatch}方法，则调用{@link #complete}。</li>
 * </ol>
 *
 * @since Servlet 3.0
 */
public interface AsyncContext {

    /**
     * 请求属性的名称，通过该属性可将原始请求URI提供给
     * {@link #dispatch(String)} 或 {@link #dispatch(ServletContext,String)} 方法调用的目标对象
     */
    static final String ASYNC_REQUEST_URI = "javax.servlet.async.request_uri";

    /**
     * 请求属性的名称，通过该属性可将原始上下文路径提供给
     * {@link #dispatch(String)} 或 {@link #dispatch(ServletContext,String)} 方法调用的目标对象
     */
    static final String ASYNC_CONTEXT_PATH = "javax.servlet.async.context_path";

    /**
     * 请求属性的名称，通过该属性可将原始{@link javax.servlet.http.HttpServletMapping}对象提供给
     * {@link #dispatch(String)}或{@link #dispatch(ServletContext,String)}方法调用的目标对象
     */
    static final String ASYNC_MAPPING = "javax.servlet.async.mapping";

    /**
     * 请求属性的名称，通过该属性可将原始路径信息提供给{@link #dispatch(String)}或{@link #dispatch(ServletContext,String)}方法调用的目标对象
     */
    static final String ASYNC_PATH_INFO = "javax.servlet.async.path_info";

    /**
     * 请求属性的名称，通过该属性可将原始servlet路径提供给{@link #dispatch(String)}或{@link #dispatch(ServletContext,String)}方法调用的目标对象
     */
    static final String ASYNC_SERVLET_PATH = "javax.servlet.async.servlet_path";

    /**
     * 请求属性的名称，通过该属性可将原始查询字符串提供给{@link #dispatch(String)}或{@link #dispatch(ServletContext,String)}方法调用的目标对象
     */
    static final String ASYNC_QUERY_STRING = "javax.servlet.async.query_string";

    /**
     * 获取通过调用{@link ServletRequest#startAsync()}或{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}
     * 初始化此异步上下文时使用的请求。
     *
     * @return 用于初始化此异步上下文的请求
     *
     * @exception IllegalStateException 如果在异步周期内已调用{@link #complete}或任意{@link #dispatch}方法
     */
    public ServletRequest getRequest();

    /**
     * 获取通过调用 {@link ServletRequest#startAsync()} 或
     * {@link ServletRequest#startAsync(ServletRequest, ServletResponse)} 方法
     * 初始化此 AsyncContext 时使用的响应对象。
     *
     * @return 用于初始化此 AsyncContext 的响应对象
     *
     * @exception IllegalStateException 如果在异步周期中已经调用了 {@link #complete} 或任何 {@link #dispatch} 方法
     */
    public ServletResponse getResponse();

    /**
     * 检查此 AsyncContext 是否使用原始或应用包装的请求和响应对象进行初始化。
     *
     * <p>
     * 此信息可被在请求进入异步模式后于<i>出站</i>方向调用的过滤器使用，以确定它们在<i>入站</i>调用期间添加的任何请求和/或响应包装器
     * 是否需要在整个异步操作期间保留，或者是否可以释放。
     *
     * @return 如果此 AsyncContext 是通过调用 {@link ServletRequest#startAsync()} 使用原始请求和响应对象初始化的，
     *         或者是通过调用 {@link ServletRequest#startAsync(ServletRequest, ServletResponse)} 初始化，
     *         且 ServletRequest 和 ServletResponse 参数均未携带任何应用提供的包装器，则返回 true；否则返回 false
     */
    public boolean hasOriginalRequestAndResponse();

    /**
     * 将此 AsyncContext 的请求和响应对象分发给 Servlet 容器。
     *
     * <p>
     * 如果异步周期是通过 {@link ServletRequest#startAsync(ServletRequest, ServletResponse)} 启动的，
     * 且传入的请求是 HttpServletRequest 实例，则分发目标为 {@link javax.servlet.http.HttpServletRequest#getRequestURI}
     * 返回的 URI。否则，分发目标为容器最后一次分发该请求时的 URI。
     *
     * <p>
     * 以下序列说明了此方法的工作方式：
     *
     * <pre>
     * {@code
     * // 请求分发到 /url/A
     * AsyncContext ac = request.startAsync();
     * ...
     * ac.dispatch(); // 异步分发到 /url/A
     *
     * // 请求到 /url/A
     * // 转发分发到 /url/B
     * request.getRequestDispatcher("/url/B").forward(request,response);
     * // 在 FORWARD 分发的目标中启动异步操作
     * ac = request.startAsync();
     * ...
     * ac.dispatch(); // 异步分发到 /url/A
     *
     * // 请求到 /url/A
     * // 转发分发到 /url/B
     * request.getRequestDispatcher("/url/B").forward(request,response);
     * // 在 FORWARD 分发的目标中启动异步操作
     * ac = request.startAsync(request,response);
     * ...
     * ac.dispatch(); // 异步分发到 /url/B
     * }
     * </pre>
     *
     * <p>
     * 此方法在将请求和响应对象传递给容器管理的线程后立即返回，分发操作将在该线程上执行。如果在调用 <tt>startAsync</tt> 的
     * 容器初始化分发返回到容器之前调用此方法，则分发操作将被延迟，直到容器初始化分发返回到容器之后。
     *
     * <p>
     * 请求的分发器类型设置为 <tt>DispatcherType.ASYNC</tt>。与
     * {@link RequestDispatcher#forward(ServletRequest, ServletResponse) 转发分发} 不同，响应缓冲区和头部不会被重置，
     * 并且即使在响应已经提交的情况下，分发也是合法的。
     *
     * <p>
     * 对请求和响应的控制权委托给分发目标，并且除非调用 {@link ServletRequest#startAsync()} 或
     * {@link ServletRequest#startAsync(ServletRequest, ServletResponse)}，否则响应将在分发目标完成执行时关闭。
     *
     * <p>
     * 此方法执行期间可能发生的任何错误或异常必须由容器捕获和处理，具体如下：
     * <ol>
     * <li>调用所有注册在创建此 AsyncContext 的 ServletRequest 上的 {@link AsyncListener} 实例的
     * {@link AsyncListener#onError onError} 方法，并通过 {@link AsyncEvent#getThrowable} 提供捕获的 <tt>Throwable</tt>。</li>
     * <li>如果没有任何监听器调用 {@link #complete} 或任何 {@link #dispatch} 方法，则执行错误分发，状态码为
     * <tt>HttpServletResponse.SC_INTERNAL_SERVER_ERROR</tt>，并将上述 <tt>Throwable</tt> 作为
     * <tt>RequestDispatcher.ERROR_EXCEPTION</tt> 请求属性的值提供。</li>
     * <li>如果未找到匹配的错误页面，或者错误页面没有调用 {@link #complete} 或任何 {@link #dispatch} 方法，则调用 {@link #complete}。</li>
     * </ol>
     *
     * <p>
     * 每个异步周期（通过调用 {@link ServletRequest#startAsync} 方法之一启动）最多只能有一个异步分发操作。
     * 在同一异步周期内尝试执行额外的异步分发操作将导致 IllegalStateException。如果在分发的请求上随后调用了 startAsync，
     * 则可以调用任何分发或 {@link #complete} 方法。
     *
     * @throws IllegalStateException 如果已调用过分发方法且在结果分发期间未调用 startAsync 方法，或者已调用 {@link #complete}
     *
     * @see ServletRequest#getDispatcherType
     */
    public void dispatch();

    /**
     * 将此 AsyncContext 的请求和响应对象分发到指定的 <tt>path</tt>。
     *
     * <p>
     * <tt>path</tt> 参数的解析方式与 {@link ServletRequest#getRequestDispatcher(String)} 相同，
     * 但其范围限定于初始化此 AsyncContext 的 {@link ServletContext} 内。
     *
     * <p>
     * 请求的所有路径相关查询方法必须反映分发目标，而原始请求 URI、上下文路径、路径信息、servlet 路径和查询字符串
     * 可以从请求的 {@link #ASYNC_REQUEST_URI}、{@link #ASYNC_CONTEXT_PATH}、{@link #ASYNC_PATH_INFO}、
     * {@link #ASYNC_SERVLET_PATH} 和 {@link #ASYNC_QUERY_STRING} 属性中恢复。这些属性将始终反映原始路径元素，
     * 即使在重复分发的情况下也是如此。
     *
     * <p>
     * 每个异步周期（通过调用 {@link ServletRequest#startAsync} 方法之一启动）最多只能有一个异步分发操作。
     * 在同一异步周期内尝试执行额外的异步分发操作将导致 IllegalStateException。如果在分发的请求上随后调用了 startAsync，
     * 则可以调用任何分发或 {@link #complete} 方法。
     *
     * <p>
     * 有关错误处理等其他详细信息，请参阅 {@link #dispatch()}。
     *
     * @param path 分发目标的路径，范围限定于初始化此 AsyncContext 的 ServletContext
     *
     * @throws IllegalStateException 如果已调用过分发方法且在结果分发期间未调用 startAsync 方法，或者已调用 {@link #complete}
     *
     * @see ServletRequest#getDispatcherType
     */
    public void dispatch(String path);

    /**
     * 将此AsyncContext的请求和响应对象分发到给定<tt>context</tt>范围内指定的<tt>path</tt>。
     *
     * <p>
     * <tt>path</tt>参数的解析方式与{@link ServletRequest#getRequestDispatcher(String)}相同，
     * 不同之处在于其范围限定于给定的<tt>context</tt>。
     *
     * <p>
     * 请求的所有路径相关查询方法必须反映分发目标，而原始请求URI、上下文路径、路径信息、servlet路径和查询字符串
     * 可以从请求的{@link #ASYNC_REQUEST_URI}、{@link #ASYNC_CONTEXT_PATH}、{@link #ASYNC_PATH_INFO}、
     * {@link #ASYNC_SERVLET_PATH}和{@link #ASYNC_QUERY_STRING}属性中恢复。这些属性将始终反映原始路径元素，
     * 即使在重复分发的情况下也是如此。
     *
     * <p>
     * 每个异步周期（通过调用{@link ServletRequest#startAsync}方法之一启动）最多只能有一个异步分发操作。
     * 在同一异步周期内尝试执行额外的异步分发操作将导致IllegalStateException。如果在分发的请求上随后调用了startAsync，
     * 则可以调用任何分发或{@link #complete}方法。
     *
     * <p>有关错误处理等其他详细信息，请参阅{@link #dispatch()}。
     *
     * @param context 分发目标的ServletContext
     * @param path    分发目标的路径，范围限定于给定的ServletContext
     *
     * @throws IllegalStateException 如果已调用过分发方法且在结果分发期间未调用startAsync方法，或者已调用{@link #complete}
     *
     * @see ServletRequest#getDispatcherType
     */
    public void dispatch(ServletContext context, String path);

    /**
     * 完成在用于初始化此AsyncContext的请求上启动的异步操作，并关闭用于初始化此AsyncContext的响应。
     *
     * <p>
     * 所有注册在创建此AsyncContext的ServletRequest上的{@link AsyncListener}类型监听器，
     * 都将在其{@link AsyncListener#onComplete(AsyncEvent) onComplete}方法中被调用。
     *
     * <p>
     * 在调用{@link ServletRequest#startAsync()}或{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}之后，
     * 以及调用此类的任何<tt>dispatch</tt>方法之前，都可以合法地调用此方法。如果在调用<tt>startAsync</tt>的容器初始化分发
     * 返回到容器之前调用此方法，则该调用不会立即生效（并且任何对{@link AsyncListener#onComplete(AsyncEvent)}的调用都将被延迟），
     * 直到容器初始化分发返回到容器之后才会生效。
     */
    public void complete();

    /**
     * 使容器分派一个线程（可能来自托管线程池）来运行指定的<tt>Runnable</tt>。
     * 容器可能会将适当的上下文信息传播给该<tt>Runnable</tt>。
     *
     * @param run 异步处理器
     */
    public void start(Runnable run);

    /**
     * 将给定的{@link AsyncListener}注册到通过调用{@link ServletRequest#startAsync}方法之一启动的最新异步周期中。
     *
     * <p>当异步周期成功完成、超时、产生错误，或者通过{@link ServletRequest#startAsync}方法之一启动新的异步周期时，给定的AsyncListener将收到一个{@link AsyncEvent}。
     *
     * <p>AsyncListener实例将按照它们被添加的顺序被通知。
     *
     * <p>
     * 如果调用了{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}或
     * {@link ServletRequest#startAsync}方法，当通知{@link AsyncListener}时，可以从{@link AsyncEvent}
     * 中获取完全相同的请求和响应对象。
     *
     * @param listener 要注册的AsyncListener
     * @throws IllegalStateException 如果在容器发起的分发（在此期间调用了{@link ServletRequest#startAsync}方法之一）
     *                               已返回到容器后调用此方法
     */
    public void addListener(AsyncListener listener);

    /**
     * 将给定的{@link AsyncListener}注册到通过调用{@link ServletRequest#startAsync}方法之一启动的最新异步周期中。
     *
     * <p>当异步周期成功完成、超时、产生错误，或者通过{@link ServletRequest#startAsync}方法之一启动新的异步周期时，给定的AsyncListener将收到一个{@link AsyncEvent}。
     *
     * <p>AsyncListener实例将按照它们被添加的顺序被通知。
     *
     * <p>
     * 给定的ServletRequest和ServletResponse对象将分别通过传递给它的{@link AsyncEvent}的
     * {@link AsyncEvent#getSuppliedRequest getSuppliedRequest}和{@link AsyncEvent#getSuppliedResponse
     * getSuppliedResponse}方法提供给给定的AsyncListener。在传递AsyncEvent时，不应分别从这些对象
     * 读取或写入它们，因为自给定AsyncListener注册以来可能发生了额外的包装，但是可以使用它们来
     * 释放与它们关联的任何资源。
     *
     * @param listener        要注册的AsyncListener
     * @param servletRequest  将包含在AsyncEvent中的ServletRequest
     * @param servletResponse 将包含在AsyncEvent中的ServletResponse
     * @throws IllegalStateException 如果在容器发起的分发（在此期间调用了{@link ServletRequest#startAsync}方法之一）
     *                               已返回到容器后调用此方法
     */
    public void addListener(AsyncListener listener, ServletRequest servletRequest, ServletResponse servletResponse);

    /**
     * 实例化给定的{@link AsyncListener}类。
     *
     * <p>返回的AsyncListener实例在通过调用某个<code>addListener</code>方法注册到此AsyncContext之前可以进行进一步定制。
     *
     * <p>给定的AsyncListener类必须定义一个无参构造函数，该构造函数将用于实例化此类。
     *
     * <p>如果给定的<tt>clazz</tt>表示托管Bean(Managed Bean)，则此方法支持资源注入。有关托管Bean和资源注入的更多详细信息，请参阅Jakarta EE平台和CDI规范。
     *
     * <p>此方法支持适用于AsyncListener的任何注解。
     *
     * @param <T> 要实例化的对象的类
     * @param clazz 要实例化的AsyncListener类
     * @return 新的AsyncListener实例
     * @throws ServletException 如果给定的<tt>clazz</tt>实例化失败
     */
    public <T extends AsyncListener> T createListener(Class<T> clazz) throws ServletException;

    /**
     * 设置此AsyncContext的超时时间（以毫秒为单位）。
     *
     * <p>超时设置将在容器发起的分发（在此期间调用了{@link ServletRequest#startAsync}方法之一）返回到容器后生效。
     *
     * <p>如果既未调用{@link #complete}方法也未调用任何分发方法，超时时间将到期。超时值为零或负数表示无超时限制。
     *
     * <p>如果未调用{@link #setTimeout}方法，则将应用容器的默认超时时间（可通过调用{@link #getTimeout}获取）。
     *
     * <p>默认值为<code>30000</code>毫秒。
     *
     * @param timeout 超时时间（毫秒）
     * @throws IllegalStateException 如果在容器发起的分发（在此期间调用了{@link ServletRequest#startAsync}方法之一）
     *                               已返回到容器后调用此方法
     */
    public void setTimeout(long timeout);

    /**
     * 获取此AsyncContext的超时时间（以毫秒为单位）。
     *
     * <p>
     * 此方法返回容器针对异步操作的默认超时时间，或返回最近一次调用{@link #setTimeout}方法时设置的超时值。
     *
     * <p>
     * 超时值为零或负数表示无超时限制。
     *
     * @return 超时时间（毫秒）
     */
    public long getTimeout();

}
