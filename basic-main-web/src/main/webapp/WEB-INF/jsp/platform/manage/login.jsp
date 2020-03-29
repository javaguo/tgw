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

		<link rel="stylesheet" type="text/css" href="resource/platform/css/platform/manage/login/login.css">


		<script type="text/javascript" src="<%=basePath%>resource/platform/plugin/jquery/jquery/jquery-1.12.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/platform/plugin/jquery/md5/jquery.md5.js"></script>
		<script type="text/javascript" src="<%=basePath%>resource/platform/js/platform/manage/login/manageLogin.js"></script>

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
							<img src="resource/platform/img/platform/manage/login/btnlogin.png"/>
						</a>
					</div>
					<div class="reset">
						<a href="#">
							<img src="resource/platform/img/platform/manage/login/reset.png"/>
						</a>
					</div>
				</form>
			</div>
		</div>

		<div style="width: 420px;height:80px;float:right;">
			<iframe width="420" scrolling="no" height="60" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=12&color=%23&icon=1&num=5&site=14"></iframe>
			<%--<iframe name="weather_inc" src="http://i.tianqi.com/index.php?c=code&id=2" width="770" height="70" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>--%>
		</div>
	</body>
</html>
