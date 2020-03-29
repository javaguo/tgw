<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	String browserLang=request.getLocale().toString();
%>

<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">

	<title>通用管理系统工程</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="管理系统">
	<meta http-equiv="description" content="通用管理系统">

	<link rel="stylesheet" type="text/css"
		  href="resource/platform/css/platform/manage/index/index.css">
	<link rel="stylesheet" type="text/css"
		  href="resource/platform/plugin/extjs/extjs5/extjs5/packages/ext-theme-${theme}/build/resources/ext-theme-${theme}-all.css">
	<%--<link rel="stylesheet" type="text/css"
		  href="resource/platform/plugin/extjs/extjs5/extjs5/packages/ext-theme-classic/build/resources/ext-theme-classic-all.css">--%>
	<link rel="stylesheet" type="text/css"
		  href="resource/platform/plugin/extjs/extjs5/extjs5/resources/css/icon.css">

	<script type="text/javascript" src="<%=basePath%>resource/platform/plugin/jquery/jquery/jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>resource/platform/plugin/jquery/md5/jquery.md5.js"></script>
	<!-- 需要放在index.jsp中提前加载，如果在业务页面中引入时报echarts is not define -->
	<script type="text/javascript" src="<%=basePath%>resource/platform/plugin/echart/echarts.min.js"></script>

	<script type="text/javascript"
			src="resource/platform/plugin/extjs/extjs5/extjs5/ext-all.js"></script>
	<!-- 语言包要在ext-all.js之后引入才能生效 -->
	<script type="text/javascript"
			src="resource/platform/plugin/extjs/extjs5/extjs5/locale/ext-lang-<%=browserLang%>.js"></script>

	<script type="text/javascript"
			src="resource/platform/plugin/extjs/extjs5/common/extPlatformCommon.js"></script>
	<script type="text/javascript"
			src="resource/platform/plugin/extjs/extjs5/extend/validate/PlatformVTypes.js"></script>

	<script type="text/javascript"
			src="resource/platform/js/platform/common/common.js"></script>
	<script type="text/javascript"
			src="resource/platform/js/platform/manage/index/index.js"></script>
	<script type="text/javascript"
			src="resource/platform/js/platform/common/platformValidate.js"></script>

	<script type="text/javascript">
		var globalBasePath = '<%=basePath%>';
		var globalTheme = '${theme}';
	</script>
</head>
<body>
<!-- 为了优化加载页面体验效果，默认不显示，ext加载完再显示。 -->
<div id="head-region-container" style="display: none">

</div>
<div id="foot-region-container" style="display: none">

</div>
</body>
</html>
