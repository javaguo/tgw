<%@ page language="java" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String browserLang=request.getLocale().toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>打开新tab窗口示例</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="">
	<meta http-equiv="description" content="">

<script type="text/javascript">
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();  // enable tooltips
	new Ext.panel.Panel({
		title: 'HTML Editor',
		renderTo: 'newTabContent',
		width: 700,
		height: 400,
		frame: true,
		layout: 'fit',
		items: {
			xtype: 'htmleditor',
			enableColors: false,
			enableAlignments: false
		}
	});
});
</script>

  </head>
  <body>
<div style="width:100%;height:35px;color:orangered;background:darkgrey;text-align: center;"><h1>此页面是打开新tab窗口示例页面</h1></div>
<div id="newTabContent"></div>
  </body>
</html>
