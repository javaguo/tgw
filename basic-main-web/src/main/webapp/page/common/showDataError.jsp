<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>抱歉，加载页面出错了！</title>

	<script type="text/javascript">

	</script>

  </head>
  <body>
  	<div style="width:100%;height: 100%;text-align: center;">
		<h2>抱歉，加载页面出错了！</h2>
	</div>
  </body>
</html>
