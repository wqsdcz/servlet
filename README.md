# Jakarta Servlet

这个库包含了Jakarta Servlet 的代码。

[Online JavaDoc](https://javadoc.io/doc/jakarta.servlet/jakarta.servlet-api/)

关于 Jakarta Servlet
---------------------
Jakarta Servlet 定义一个用于处理HTTP请求和响应的服务器端API。

构建
--------
先决条件:

* JDK8+
* Maven 3.0.3+

运行构建指令: 

`mvn install`

默认情况下，运行构建过程时，将会检查版权，并生成jar、sources-jar和javadoc-jar。
API jar 将在/api/target目录中进行构建。

检查 findbugs
-----------------
`mvn -DskipTests -Dfindbugs.threshold=Low findbugs:findbugs`

