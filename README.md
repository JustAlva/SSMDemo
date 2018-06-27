# SSMDemo
Java Spring Spring-MVC Mybatis 框架整合

1.新建 Maven 项目
File -> new -> Project -> Maven -> 勾选 Createt from archetype -> 选择 org.apache.maven.archetypes:maven-archetype-webapp -> next -> GroupId:(例：com.zkd),ArtifactId:(项目名称) -> next ->next ->Finish .

2.配置 pom.xml 文件

3.配置web.xml
相关属性意义：https://www.cnblogs.com/hafiz/p/5715523.html

4.添加 spring-common.xml 配置文件
在web.xml中添加以下代码，并配置添加的 applicationContext.xml 文件。
  <!-- 加载spring的配置文件 -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath*:/config/spring-*.xml</param-value>
    </context-param>
    <listener>
       <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener> 

5.配置  SpingMVC servlet 的配置文件
spring mvc 的配置文件主要配置 Controller 的组件扫描器和视图解析器
在web.xml 中添加以下代码，并配置添加的 spring mvc 的配置文件。
 <!-- Spring MVC servlet -->
    <servlet>
        <servlet-name>SpringMVC</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:/config/spring-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
        <async-supported>true</async-supported>
    </servlet>
    <servlet-mapping>
        <servlet-name>SpringMVC</servlet-name>
        <!-- 此处可以可以配置成*.do，对应struts的后缀习惯 -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>

6.配置 mybatis-config.xml 文件

7.配置 jdbc.properties 文件

8.配置 log4j.properties 文件

9.配置 mybatis 自动生成插件 配置文件 generatorConfig.xml
使用默认配置文件名，放在resources文件夹下
