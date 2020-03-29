<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Display Content</title>
	<script type="text/javascript" src="ueditor.parse.min.js"></script>
</head>
<%request.setCharacterEncoding("UTF-8"); %>
<center>
<table width="600" border="0" bordercolor="000000"
	style="table-layout: fixed;">

	<tr>
		<td valign="top" bordercolor="ffffff">内容:</td>
		<td valign="top" bordercolor="ffffff" id="content" >${param.content}</td>
	</tr>
</table>
	<script type="text/javascript">
		uParse('.content', {
			rootPath: '../'
		})
	</script>
</center>
</html>