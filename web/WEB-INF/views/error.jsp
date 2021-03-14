<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2021/3/14
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
服务器出错了-----
<div style="display: none"><%=((Exception)request.getAttribute("ex")).getMessage()%></div>
</body>
</html>
