/**
 * Created by zjg on 2017/10/7.
 */

//引用manageLogin.js的页面要给contextPath赋值。contextPath的值应该为http://域名/项目名/
var contextPath = "";
function setContextPath ( basePath ){
    contextPath = basePath;
}

function login(){
    $("#tip").html("");

    var userName = document.getElementById("userName").value;
    var password = document.getElementById("password").value;

    if( userName == "" || $.trim( userName ) == ""){
        $("#tip").html("请输入用户名！");
        return;
    }else{
        userName =  $.trim( userName );
    }

    if( password == "" || $.trim( password ) == "" ){
        $("#tip").html("请输入密码！");
        return;
    }else{
        password =  $.trim( password );
    }

    var passwordMd5 = $.md5 ( password );
    $.ajax({
        url : contextPath+"login/login.do",
        type : "post",
        data : {
            userName : userName,
            password : passwordMd5
        },
        dataType : "json",
        success:function(data){
            if(data.success==true){
                var theme = data.theme;
                if( theme.length>0 ){
                    document.location.href = contextPath+"login/toManageIndex.do?theme="+theme;
                }else{
                    document.location.href = contextPath+"login/toManageIndex.do";
                }

            }else{
                $("#tip").html( data.info );
            }
        },error:function(){
            $("#tip").html( "登录出错！" );
        }
    });
}

function loginEnter(e) {
    var currKey = 0, e = e || event;
    currKey = e.keyCode || e.which || e.charCode;// 支持IE、FF
    if(currKey == 13){
        login();
    }
}