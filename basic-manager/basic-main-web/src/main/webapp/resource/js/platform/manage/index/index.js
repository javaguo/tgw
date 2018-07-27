Ext.Ajax.timeout = 60000;
Ext.Loader.setConfig({
	enabled : true,
	paths:{
		'Ext':'resource/plugin/extjs/extjs5/extjs5/examples',
		'app':'resource/plugin/extjs/extjs5/extjs5/examples',
		'Common.app':'resource/js/privateExt/manage/app',//与具体业务相关的定义
		'Common.auth':'resource/js/privateExt/manage/auth'//权限相关的定义
	}
});
Ext.require([ 'Ext.ux.IFrame']);
Ext.require([ 'Ext.ux.TreePicker']);
Ext.require([ 'Ext.ux.ComboBoxTree']);//自定义扩展的多选下拉树控件
Ext.require([ 'app.ux.DateTimeField']);//自定义扩展的日期时间控件

//打开一个新的tab窗口
function openNewTab(url,tabTitle,id){
	var objUrl;
	if( id!=null && id!="" && id!=undefined ){
		objUrl =  url+"?id="+id;
	}else{
		objUrl =  url;
	}

	globalOpenNewTab(objUrl,tabTitle,'');
}
//打开一个新的tab窗口
function openNewTab(url,tabTitle,id,tabFlag){
	var objUrl;
	if( id!=null && id!="" && id!=undefined ){
		objUrl =  url+"?id="+id;
	}else{
		objUrl =  url;
	}

	globalOpenNewTab(objUrl,tabTitle,tabFlag);
}
//打开一个新的tab窗口
function globalOpenNewTab(url,tabTitle,controllerIdentifier){
	var tabFlag = '';
	if( controllerIdentifier!=null && controllerIdentifier!="" && controllerIdentifier!=undefined ){
		tabFlag = controllerIdentifier;
	}else{
		tabFlag = Ext.Date.format( new Date(), 'YmdHisu');
	}

	var openedTab = rightTabs.getComponent( 'right_tab_'+tabFlag );
	if( openedTab ) {
		rightTabs.setActiveTab(openedTab);
		return;
	}

	openedTab = Ext.create('Ext.panel.Panel', {
		closable : true,
		id : 'right_tab_'+tabFlag,
		title : tabTitle,
		layout : 'border',
		loadMask: '页面加载中...',
		autoScroll : true,
		resizable:true,
		border : false,
		bodyStyle: {
			//background: '#ffc'
		},
		loader:{
			url:globalBasePath+url,
			autoLoad: true,
			scripts:true,
			renderer: function (loader, response, active) {//重写此方法实现执行scripts脚本
				var text = response.responseText;
				loader.getTarget().update(text, true);
				return true;
			}
		},
		listeners:{
			resize:function(){
			}
		}
	});

	rightTabs.add(openedTab);
	rightTabs.setActiveTab( openedTab );
}

//打开一个新的浏览器窗口
function openNewBrowserWindow(url,id){
	var objUrl;
	if( id!=null && id!="" && id!=undefined ){
		objUrl =  url+"?id="+id;
	}else{
		objUrl =  url;
	}

	window.open(objUrl);
}

//加载页面相关信息，欢迎语、版权等
function loadPageInfo(){
	$.ajax({
		url : globalBasePath+"login/loadPageInfo.do",
		type : "get",
		dataType : "json",
		success:function(data){
			if(data.success==true){
				$("#head-region-container").html( data.manageIndexTopTitle );
				$("#foot-region-container").html( data.manageIndexBottomCopyright );
			}else{

			}
		},error:function(){

		}
	});
}

//加载用户信息
var userInfo_userName = "";
var userInfo_loginName= "";
var userInfo_roleName= "";
function loadUserInfo(){
	$.ajax({
		url : globalBasePath+"login/loadUserInfo.do",
		type : "get",
		dataType : "json",
		success:function(data){
			if(data.success==true){
				userInfo_userName = data.userName;
				userInfo_loginName = data.loginName;
				userInfo_roleName = data.role;

				$("#loginUserInfoUserName").html( data.userName );
			}else{

			}
		},error:function(){

		}
	});
}
//显示用户详细信息
function showLoginUserDetailInfo(){
	var  showLoginUserInfoPanel=new Ext.FormPanel({
		id:'showLoginUserInfoPanel',
		frame : true,
		bodyBorder:false,
		bodyStyle : 'padding:0px 0px 0px 0px',
		defaultType: 'displayfield',
		fieldDefaults: {
			labelWidth: 60,
			labelAlign: "right",
			width:250,
			flex: 0,//每项item的宽度权重。值为0或未设置此属性时，item的width值才起作用。
			margin: 0
		},
		items: [
			{fieldLabel: '用户名',value:userInfo_userName},
			{fieldLabel: '登录名',value:userInfo_loginName},
			{fieldLabel: '所属角色',value:userInfo_roleName}
		]
	});

	var showLoginUserInfoWindow= new Ext.Window({
		id:'showLoginUserInfoWindow',
		title: '用户信息',
		width:300,
		maxHeight:500,
		scrollable:true,
		resizable:false,
		bodyStyle : 'padding:0px 0px 0px 0px',
		//closeAction : 'hide',   默认为destroy closeAction应该使用destroy
		modal : true,
		plain:false,
		items: [
			showLoginUserInfoPanel
		]
	});

	showLoginUserInfoWindow.show();
}


//修改登录密码
function modifyUserPassword(){
	//修改密码的相关变量
	var oldPasswordShow = Ext.create({
		xtype: 'textfield',
		inputType:'password',
		id:'oldPasswordShow',
		name:'oldPasswordShow',
		fieldLabel: '旧密码',
		vtype:'generalPassword',
		submitValue :false
	});
	var newPasswordShow = Ext.create({
		xtype: 'textfield',
		inputType:'password',
		id:'newPasswordShow',
		name:'newPasswordShow',
		fieldLabel: '新密码',
		vtype:'generalPassword',
		submitValue :false,
		validator: function(value){
			if( value!=undefined && value!=null && value!="" ){
				if( value.length != $.trim( value ).length ){
					return "密码不能包含空格！";
				}
			}else{
				return true;
			}

			var password2 = confirmPasswordShow.getValue();
			if( password2!=undefined && password2!=null && password2!="" ){//两次密码都已经输入完毕，进行密码比较比较验证
				if( password2.length != $.trim( password2 ).length ){
					return "两次输入密码不一致，密码不能包含空格！";
				}
				if( value!=password2 ){
					return "两次输入密码不一致！";
				}else{
					return true;
				}
			}else{//两次密码还没有都输入完毕。此时不作比较验证。
				return true;
			}
		}
	});
	var confirmPasswordShow = Ext.create({
		xtype: 'textfield',
		inputType:'password',
		id:'confirmPasswordShow',
		name:'confirmPasswordShow',
		fieldLabel: '确认密码',
		vtype:'generalPassword',
		submitValue :false,
		validator: function(value){
			if( value!=undefined && value!=null && value!=""  ){
				if( value.length != $.trim( value ).length ){
					return "密码不能包含空格！";
				}
			}else{
				return true;
			}

			var password2 = newPasswordShow.getValue();
			if( password2!=undefined && password2!=null && password2!="" ){//两次密码都已经输入完毕，进行密码比较比较验证
				if( password2.length != $.trim( password2 ).length ){
					return "两次输入密码不一致，密码不能包含空格！";
				}
				if( value!=password2 ){
					return "两次输入密码不一致！";
				}else{
					return true;
				}
			}else{//两次密码还没有都输入完毕。此时不作比较验证。
				return true;
			}
		}
	});

	var oldPasswordEncryptionHidden =Ext.create({
		xtype: 'hiddenfield',
		id: 'oldPassword',
		name: 'oldPassword'
	});
	var newPasswordEncryptionHidden = Ext.create({
		xtype: 'hiddenfield',
		id: 'newPassword',
		name: 'newPassword'
	});

	var  modifyUserPasswordPanel=new Ext.FormPanel({
		id:'modifyUserPasswordPanel',
		url : globalBasePath+"login/modifyUserPassword.do",
		frame : true,
		bodyBorder:false,
		bodyStyle : 'padding:0px 0px 0px 0px',
		defaultType: 'textfield',
		fieldDefaults: {
			xtype: 'textfield',
			allowBlank:false,
			maxLength:20,
			labelWidth: 60,
			labelAlign: "right",
			width:250,
			flex: 0,//每项item的宽度权重。值为0或未设置此属性时，item的width值才起作用。
			margin: 8
		},
		items: [oldPasswordShow,newPasswordShow,confirmPasswordShow,oldPasswordEncryptionHidden,newPasswordEncryptionHidden],
		buttons: [{
			text: '重置',
			handler: function() {
				this.up('form').getForm().reset();
			}
		},
			{
				text: '提交',
				formBind: true, //only enabled once the form is valid
				disabled: true,
				handler: function() {
					var form = this.up('form').getForm();
					if (form.isValid()) {
						var oldPassword = oldPasswordShow.getValue();
						var newPassword = newPasswordShow.getValue();
						oldPasswordEncryptionHidden.setValue( $.md5 ( oldPassword ) );
						newPasswordEncryptionHidden.setValue( $.md5 ( newPassword ) );

						form.submit({
							submitEmptyText :false,
							waitMsg :'正在保存，请耐心等待......',
							success: function(form, action) {
								Ext.Msg.alert('提示信息', action.result.msg);
								modifyUserPasswordWindow.close();
							},
							failure: function(form, action) {
								Ext.Msg.alert('错误提示', "抱歉，出错了！"+action.result.msg);
							}
						});
					}

				}
			}]
	});

	var modifyUserPasswordWindow= new Ext.Window({
		id:'modifyUserPasswordWindow',
		title: '修改密码',
		width:300,
		maxHeight:500,
		scrollable:true,
		resizable:false,
		bodyStyle : 'padding:0px 0px 0px 0px',
		//closeAction : 'hide',   默认为destroy closeAction应该使用destroy
		modal : true,
		plain:false,
		items: [
			modifyUserPasswordPanel
		]
	});

	modifyUserPasswordWindow.show();
}

//退出系统
function loginOut(){
	Ext.Msg.confirm("提示信息","确定要退出系统吗？",function (btn) {
		if( btn=="yes" ){
			$.ajax({
				url : globalBasePath+"login/loginOut.do",
				type : "get",
				dataType : "json",
				success:function(data){
					if(data.success==true){
						window.location.href =data.url;
					}else{
							window.location.reload();
							//Ext.Msg.alert('提示', '抱歉，退出系统失败！'+data.info );
					}
				},error:function(){
					window.location.reload();
					//Ext.Msg.alert('提示', '抱歉，退出系统失败，退出系统出错！' );
				}
			});
		}
	});
}

var rightTabs = Ext.create('Ext.tab.Panel', {
	region: 'center',
	xtype: 'panel',
	autoScroll : true,
	resizable:true,
	margin: '2 2 2 2',
	plugins : Ext.create('Ext.ux.TabCloseMenu', {
		closeTabText : '关闭面板',
		closeOthersTabsText : '关闭其他',
		closeAllTabsText : '关闭所有'
	}),
	items: [],
	listeners:{
		resize:function(){

		}
	}
});

var themeComboox = Ext.create({
	xtype: 'combobox',
	width: 150,
	labelWidth: 50,
	labelAlign: 'right',
	fieldLabel: '主题',
	displayField: 'name',
	valueField: 'value',
	//labelStyle: 'cursor:move;',
	margin: '5 5 5 5',
	//flex: 1,
	queryMode: 'local',
	store: Ext.create('Ext.data.Store', {
		fields: ['value', 'name'],
		data : [
			{ value: 'classic', name: 'Classic主题' },
			{ value: 'gray', name: 'Gray主题' },
			{ value: 'crisp', name: 'Crisp主题' }
			//{ value: 'neptune', name: 'Neptune主题' },
			//{ value: 'aria', name: 'aria主题' }
		]
	}),
	//value: theme,
	listeners: {
		select: function(combo) {
			var  theme = combo.getValue();
			window.location.href=globalBasePath+"login/toManageIndex.do?theme="+theme;
			//以下方式切换主题时，页面内部嵌套部分无法切换主题
			/*var href = 'resource/js/extjs/extjs5/packages/ext-theme-'+theme+'/build/resources/ext-theme-'+theme+'-all.css';
			var link = Ext.fly('theme');

			if(!link) {
				link = Ext.getHead().appendChild({
					tag:'link',
					id:'theme',
					rel:'stylesheet',
					href:''
				});
			};
			link.set({href:Ext.String.format(href, theme)});*/
		}
	}
});

//顶部区域
var topPanel = Ext.create('Ext.panel.Panel', {
	region: 'north',
	margin: '0 2 0 2',
	width:'100%',
	collapsible:true,
	frame:true,//渲染时是否应用当前主题
	items:[
		{
			xtype:'container',
			layout: {
				type: 'hbox',
				align: 'middle'
			},
			defaults : {
				xtype : 'component'
			},
			items:[{
					contentEl: 'head-region-container',
					flex: 4
					//html:'通用管理系统 '
				},
				{
					html : '欢迎您：<span id="loginUserInfoUserName" class="loginUserInfoUserName" onclick="showLoginUserDetailInfo()"></span>',
					style : 'text-align:right;font-size:14px;',
					width:300
				},
				{
					id:'modifyPasswordBtn',
					tooltip:'修改密码',
					xtype: 'button',
					frame:true,
					iconCls: 'Userkey',
					listeners: {
						click: function(){
							modifyUserPassword();
						}
					}
				},
				{
					id:'userLoginOutBtn',
					//text:'退出',
					tooltip:'退出',
					xtype: 'button',
					frame:false,
					iconCls: 'Usergo',
					listeners: {
						click: function(){
							loginOut();
						}
					}
				},
				themeComboox
			]
		},
	]
});




var leftFunctionTree = new Ext.tree.TreePanel({
	id: 'west-region-container',
	title: '功能列表',
	width: 200,
	rootVisible: false,
	region: 'west',
	xtype: 'panel',
	margin: '2 2 2 2',
	collapsible: true,
	store: new Ext.data.TreeStore({
		proxy: {
			type: 'ajax',
			url: 'sysUser/queryMenuByUser.do'
		},
		root: {
			text: '我的功能菜单',
			expanded: true
		}
	})
});

leftFunctionTree.on("itemclick", function( myself, record, item, index, e, eOpts ){

	if( !record.raw.leaf ){
		return;
	}

	globalOpenNewTab(record.raw.link,record.raw.text,record.raw.menuIdentify);

	/*
	 //下面的方法已经测试成功
	 openedTab = Ext.create('Common.auth.test.Test4', {
	 id : 'right_tab_default4'
	 });*/
});

Ext.onReady(function() {
	/**onReady开始*/
	Ext.QuickTips.init();
	$("#head-region-container").css('display','block');
	$("#foot-region-container").css('display','block');

	var viewport = new Ext.Viewport({
		layout: 'border',
		scrollable:true,
		items: [/*{
		 region: 'north',
		 contentEl: 'head-region-container',
		 collapsible:true
		 },*/
			topPanel,
			leftFunctionTree,
			rightTabs,
			{
				region: 'south',
				contentEl: 'foot-region-container',
				margin: '0 2 2 2'
			}
		]
	});

	themeComboox.setValue( globalTheme );
	loadPageInfo();
	loadUserInfo();
	//设置打开管理后台显示的默认tab窗口
	//globalOpenNewTab('url地址','tab窗口名称','窗口标识');
	/**onReady结束*/
});