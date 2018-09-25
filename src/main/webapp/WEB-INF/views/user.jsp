<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="com.zkd.controller.UserController" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="com.zkd.entity.User" %>
<%@ page import="com.google.gson.Gson" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <title>jQuery</title>
    <link rel="shortcut icon" href="<%=basePath%>logo.ico"  type="image/x-icon"/>
    <script type="text/javascript" src="<%=basePath%>statics/js/jquery.js" ></script>
    <%--  <script type="text/javascript" src="<%=basePath%>statics/js/vue.js" ></script>--%>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("input#login_user_submit").click(function () {
                var userId = document.getElementById("login_user_id").value;
                var userPsd = document.getElementById("login_user_psd").value;

                $.ajax({
                    url:"<%=basePath%>user/userLogin",
                    data:{"userId":userId,"psd":userPsd},
                    type:"POST",
                    dataType:"html",
                    success: function(data,textstatus){
                        //console.log(data);
                        var userGet = JSON.parse(data);
                        alert(userGet.userId+";"+textstatus);
                    },
                    error:function(jqxhr,textstatus,error){
                        alert(error);
                    }
                })
            });
        });
    </script>
    <script >
        var app = new Vue({
            el:'#app',
            data:{
                message: "Hello Vue!"
            }
        });
    </script>
</head>
<body>
<h2>Hello World!</h2>
${user.userId}
<div>
    <div id="app">
        {{ message }}
    </div>
    <input type="text" id="login_user_id"/>
    <input type="text" id="login_user_psd"/>
    <input type="button" id="login_user_submit"  value="查看"  />
</div>
</body>
</html>
