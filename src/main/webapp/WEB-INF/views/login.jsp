<%--
  Created by IntelliJ IDEA.
  User: keding.zhou
  Date: 2018/6/29
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zkd.controller.LoginController" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="http://unpkg.com/iview/dist/styles/iview.css">
    <script type="text/javascript" src="http://vuejs.org/js/vue.js"></script>
    <script type="text/javascript" src="http://unpkg.com/iview/dist/iview.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>statics/common/css-page/main.css">
    <style>
        html, body {
            background-image: url("<%=basePath%>statics/common/images/bg_black.jpg");
            overflow: hidden;
        }
    </style>
</head>
<body>
<div>
    <canvas id="cas"></canvas>
    <div style="position: absolute;left:0;top:0;height: 100%;width: 100%;text-align:center;">
        <div id="app" style="  height: 100%;width:  100%; " class="center_parent" >

            <Card :bordered="false" style="width: 400px;height: 300px; background: rgba(255, 255, 255, 0.5)  " >
                <div >
                    <i-button @click="show">Click me!</i-button>
                    <Modal v-model="visible" title="Welcome">Welcome to iView</Modal>

                </div>

            </Card>

        </div>

    </div>
</div>

<script>
    new Vue({
        el: '#app',
        data: {
            visible: false
        },
        methods: {
            show: function () {
                this.visible = true;
            }
        }
    })
</script>


<%--粒子效果--%>
<script type="text/javascript" src="<%=basePath%>statics/common/js-utils/granule-bg.js"></script>

</body>


</html>
