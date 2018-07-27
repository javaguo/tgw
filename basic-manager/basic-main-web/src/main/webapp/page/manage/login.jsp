<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">

		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1" />
		<title>通用后台管理系统</title>

		<link rel="stylesheet" type="text/css" href="resource/css/platform/manage/login/login.css">


		<script type="text/javascript" src="<%=basePath%>resource/plugin/jquery/jquery/jquery-1.12.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/plugin/jquery/md5/jquery.md5.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/js/platform/manage/login/manageLogin.js"></script>

		<script type="text/javascript">
			setContextPath('<%=basePath%>');
		</script>
		<style>

		</style>
	</head>
	<body>
		<div id="bgcc">
			<div id="content">
				<form name="form1" id="form1" method="post" action="<%=basePath%>/sys/user/login">
					<div id="tip" class="inputcss-tip"></div>
					<div>
						<input name="userName" type="text" class="inputcss-userName" id="userName" value=""/><br>
						<input name="password" type="password" class="inputcss-password" id="password" value="" onkeydown="loginEnter(event)" />
					</div>
					<div class="save">
						<a href="javascript:login();"> 
							<img src="resource/img/platform/manage/login/btnlogin.png"/>
						</a>
					</div>
					<div class="reset">
						<a href="#">
							<img src="resource/img/platform/manage/login/reset.png"/>
						</a>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>
