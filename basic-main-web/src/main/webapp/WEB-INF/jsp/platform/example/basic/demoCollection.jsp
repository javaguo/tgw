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
    
    <title>demo集合</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="">
	<meta http-equiv="description" content="">
	<script type="text/javascript">

function baseAjaxReq(url){
    Ext.Msg.confirm("提示信息","确定执行此操作？",function (btn) {
        if( btn=="yes" ){
            Ext.Ajax.request({
                url: '<%=basePath%>'+url,
                params: {
                },
                method:'POST',
                success: function(response){
                    var responseStr = response.responseText;
                    var responseJsonObj = Ext.JSON.decode( responseStr );

                    if( responseJsonObj.success ){
                        Ext.Msg.alert('提示', '操作成功！'+responseJsonObj.msg,function(){

                        });
                    }else{
                        Ext.Msg.alert('提示', '抱歉，操作失败！'+responseJsonObj.msg );
                    }

                },
                failure:function(response){
                    Ext.Msg.alert('提示', '抱歉，操作失败，出错了！' );
                }
            });
        }
    });
}
</script>
<style type="text/css">
    .wrapDiv{
        padding: 0px 10px 0px 10px;
        margin: 10px 10px 10px 10px;
    }
    .demoGroupTitle{
        color: #111;
        margin: 15px 5px 15px 5px;
        font-weight: bold;
        font-size: 13px;
    }
    .demoGroup span {
        font-size: 0.9em;
        color: #333;
        background: #d5d6e37a;
        outline: none;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        padding: 8px 10px;
        margin: 5px 5px;
        -webkit-appearance: none;
        display: inline-block;
    }
    .demoGroup span:hover {
        background: #4cb0f9;
        color: #333;
        transition: 0.5s all ease;
        -webkit-transition: 0.5s all ease;
        -moz-transition: 0.5s all ease;
        /*-- w3layouts --*/
        -o-transition: 0.5s all ease;
        -ms-transition: 0.5s all ease;
    }
</style>
  </head>
  <body>
  <div class="wrapDiv">
    <div class="demoGroupTitle">定时任务：</div>
    <div class="demoGroup">
        <span onclick="baseAjaxReq('/demoCollection/simpleQuartz.do')">创建simple定时任务</span>
        <span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span><span onclick="baseAjaxReq('/demoCollection/cronQuartz.do')">创建cron表达式定时任务</span>
    </div>
  </div>
  </body>
</html>
