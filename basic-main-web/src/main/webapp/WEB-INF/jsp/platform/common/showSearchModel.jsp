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
    
    <title>信息列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="">
	<meta http-equiv="description" content="">
	
	<c:if test='${controller.jsFileNameUserDefinePathSet!=null}'>
		<c:forEach items="${controller.jsFileNameUserDefinePathSet}" var="jsFileName">
			<script type="text/javascript"
				src="${jsFileName}"></script>
		</c:forEach>
	</c:if>
	 
	<script type="text/javascript">

	<%-- 定义下拉框数据模型 --%>
	Ext.define('comboBoxDataModel${identifier}', {
			extend: 'Ext.data.Model',
			fields: ['id', 'name']
	});
	
	
<%-- 搜索区域开始 --%>
function createSearPanel_${identifier}(){
	<%-- 相关的变量名以sear_开头，与添加、编辑窗口进行变量名区分。
		 搜索区域的相关变量名与添加、编辑窗口变量名一样时，页面搜索区域展开与关闭异常。
	--%>
	var resetFieldConLeftArray=new Array();
	var comboCascadeActivateState = "init";
	<%-- 下拉框初始化数据开始 --%>
	<c:forEach items="${comboBoxSearchList}" var="comboBoxInfo" varStatus="comboBoxStatus">
		<%-- 定义下拉框store开始 --%>
		<c:choose>
			<%-- 使用json串初始化下拉框数据 --%>
			<c:when test='${comboBoxInfo.loadDataImplModel=="json"}'>
				var sear_comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier} = Ext.create('Ext.data.Store', {
					fields: ['id', 'name'],
					data : [
						<c:forEach items="${comboBoxInfo.comboBoxOptionList}" var="comboBoxOptionInfo" varStatus="comboBoxOptionStatus">
							{"id":"${comboBoxOptionInfo.value}", "name":"${comboBoxOptionInfo.name}"}
							<c:choose>
								<c:when test="${comboBoxOptionStatus.last}"></c:when>
								<c:otherwise>,</c:otherwise>
							</c:choose>
						</c:forEach>
					]
				});
			</c:when>
			<%-- 请求后台查询数据库初始化下拉框数据 --%>
			<c:when test='${comboBoxInfo.loadDataImplModel=="sql"}'>
				<c:choose>
					<c:when test='${comboBoxInfo.isCascade && !comboBoxInfo.isFirst }'>
						var cascChild_${comboBoxInfo.comboBoxName}_${identifier} = "true";
					</c:when>
					<c:otherwise>
						var cascChild_${comboBoxInfo.comboBoxName}_${identifier} = "false";
					</c:otherwise>
				</c:choose>
				var sear_comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier} = new Ext.data.Store({
					model:comboBoxDataModel${identifier},
					proxy: new Ext.data.HttpProxy({
						url: '<%=basePath%>${controllerBaseUrl}loadComboxData.do?cascChild='+cascChild_${comboBoxInfo.comboBoxName}_${identifier},
						noCache:false,
						reader:{
							type:'json',
							rootProperty: 'comboboxData'
						}
					}),
					remoteSort: true
				});
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
		<%-- 定义下拉框store结束 --%>
		
		<%-- 定义下拉框 --%>
		var  sear_field_${comboBoxInfo.comboBoxName}_${identifier} = Ext.create('Ext.form.ComboBox', {
			id:'sear_field_${comboBoxInfo.comboBoxName}_${identifier}',
			name:'${comboBoxInfo.comboBoxName}',		
			xtype: 'combobox',
			store: sear_comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier},
			triggerAction: 'all',
			//queryMode: 'local',
			displayField: 'name',
			valueField: 'id',
			width:80,
			loadingText: '正在加载...',
			emptyText: "请选择",
			mode: "local",
			typeAhead: true  //延时查询
			<c:if test='${comboBoxInfo.configs!=null}'>
				,${comboBoxInfo.configs}
			</c:if>
			<c:if test='${comboBoxInfo.validatorFunName!=null}'>
			,validator: function(value){
				return ${comboBoxInfo.validatorFunName}(value ${comboBoxInfo.validatorFunField});
			}
			</c:if>
			,allowBlank:true,value:''
			,listeners : {focus:function(){
				<%--任何一个下拉框获得焦点，则激活起用级联框级联效果。--%>
				comboCascadeActivateState = "activateCascade";
				}
			}
		});
		<c:if test='${comboBoxInfo.isCascade}'>
			resetFieldConLeftArray.push( sear_field_${comboBoxInfo.comboBoxName}_${identifier} );	
		</c:if>
		
		<c:choose>
			<%-- 绑定下拉框级联事件 --%>
			<c:when test='${ comboBoxInfo.loadDataImplModel=="sql" && comboBoxInfo.isCascade && !comboBoxInfo.isLast }'>
				sear_field_${comboBoxInfo.comboBoxName}_${identifier}.on('change', function() {
					if( comboCascadeActivateState=="activateCascade" ){
						<c:forEach items="${comboBoxInfo.cascadeList}" var="cascadeComboBoxInfo" varStatus="cascadeComboBoxStatus">
							<%-- 编辑时，给comboBox赋了初始值使用clearValue()方法不气作用，清除不了值。使用setValue("")清除。问题比较奇怪 --%>
							//sear_field_${cascadeComboBoxInfo.comboBoxName}_${identifier}.clearValue();
							sear_field_${cascadeComboBoxInfo.comboBoxName}_${identifier}.setValue( null );
							sear_field_${cascadeComboBoxInfo.comboBoxName}_${identifier}.reset();
							
							sear_comboBoxStore_${cascadeComboBoxInfo.comboBoxName}_${identifier}.removeAll();
						</c:forEach>
						
						Ext.apply(sear_comboBoxStore_${comboBoxInfo.childComboBox}_${identifier}.proxy.extraParams, 
							{"comboBoxFlag":"${comboBoxInfo.comboBoxFlag}",
							"value":sear_field_${comboBoxInfo.comboBoxName}_${identifier}.getValue()});
						sear_comboBoxStore_${comboBoxInfo.childComboBox}_${identifier}.load();
					}
				}); 
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<%-- 请求后台查询数据库初始化下拉框数据(所有的下拉框store都要初始化) --%>
			<c:when test='${comboBoxInfo.loadDataImplModel=="sql"}'>
				sear_comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.on("beforeload",function(){
							Ext.apply(sear_comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.proxy.extraParams, 
								{"comboBoxFlag":"${comboBoxInfo.comboBoxFlag}",
									<c:choose>
										<%-- 加载第一个下拉框的数据(级联有多个下拉框，非级联只有一个下拉框) --%>
										<c:when test='${ !comboBoxInfo.isCascade || ( comboBoxInfo.isCascade && comboBoxInfo.isFirst )}'>			
											"value":"${comboBoxInfo.firstComboBoxParamValue}"
										</c:when>
										<c:otherwise>
											"value":sear_field_${comboBoxInfo.parentComboBox}_${identifier}.getValue()
										</c:otherwise>
									</c:choose>
							});
				});
				<c:choose>
					<%-- 加载级联第一个下拉框或非级联下拉框(非级联下拉框只有一个) --%>
					<c:when test='${ !comboBoxInfo.isCascade || ( comboBoxInfo.isCascade && comboBoxInfo.isFirst )}'>			
						sear_comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.load();
					</c:when>
					<c:otherwise>
						<%-- 加载级联下拉框数据(除第一个下拉框),父级联框有数据时才加载子级联框 --%>
					    if( sear_field_${comboBoxInfo.parentComboBox}_${identifier}.getValue() ){
							sear_comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.load();
						}
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<%-- 下拉框初始化数据结束 --%>
	
	<%-- tag控件下拉框初始化数据开始 --%>
	<c:forEach items="${tagSearchList}" var="tagInfo" varStatus="tagStatus">
		<%-- 定义下拉框store开始 --%>
		<c:choose>
			<%-- 使用json串初始化下拉框数据 --%>
			<c:when test='${tagInfo.loadDataImplModel=="json"}'>
				var sear_tagStore_${tagInfo.comboBoxName}_${identifier} = Ext.create('Ext.data.Store', {
					fields: ['id', 'name'],
					data : [
						<c:forEach items="${tagInfo.comboBoxOptionList}" var="comboBoxOptionInfo" varStatus="comboBoxOptionStatus">
							{"id":"${comboBoxOptionInfo.value}", "name":"${comboBoxOptionInfo.name}"}
							<c:choose>
								<c:when test="${comboBoxOptionStatus.last}"></c:when>
								<c:otherwise>,</c:otherwise>
							</c:choose>
						</c:forEach>
					]
				});
			</c:when>
			<%-- 请求后台查询数据库初始化下拉框数据 --%>
			<c:when test='${tagInfo.loadDataImplModel=="sql"}'>
				var sear_tagStore_${tagInfo.comboBoxName}_${identifier} = new Ext.data.Store({
					model:comboBoxDataModel${identifier},
					proxy: new Ext.data.HttpProxy({
						url: '<%=basePath%>${controllerBaseUrl}loadComboxData.do',
						noCache:false,
						reader:{
							type:'json',
							rootProperty: 'comboboxData'
						}
					}),
					remoteSort: true
				});
				sear_tagStore_${tagInfo.comboBoxName}_${identifier}.on("beforeload",function(){
					Ext.apply(sear_tagStore_${tagInfo.comboBoxName}_${identifier}.proxy.extraParams, {"comboBoxFlag":"${tagInfo.comboBoxFlag}","value":"${tagInfo.firstComboBoxParamValue}"});
				});
				sear_tagStore_${tagInfo.comboBoxName}_${identifier}.load();
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
		<%-- 定义下拉框store结束 --%>
	</c:forEach>
	<%-- tag控件下拉框初始化数据结束 --%>

	<%-- 生成所有搜索字段表单元素开始 --%>
	<c:forEach items="${searFieldList}" var="fieldInfo" varStatus="validFieldStatus">
		
		<c:choose>
			<%-- 单选按钮开始 --%>
			<c:when test='${fieldInfo.xtype=="radiogroup"}'>
			var  sear_field_${fieldInfo.name}_${identifier} =
			Ext.create({
				xtype: '${fieldInfo.xtype}',
				id: 'sear_field_${fieldInfo.name}_${identifier}',
				fieldLabel: '${fieldInfo.fieldLabel}',
				labelStyle:'vertical-align: middle;',
				columns: 10,
				vertical: true,
				items: [
					<c:forEach items="${fieldInfo.sysEnFieldAttr.radioList}" var="radioFieldInfo" varStatus="radioFieldStatus">
						{id:'sear_field_${fieldInfo.name}_${radioFieldInfo.eleId}_${identifier}', 
						 boxLabel: '${radioFieldInfo.boxLabel}', name: '${fieldInfo.name}',
						 inputValue: '${radioFieldInfo.inputValue}',width:60
						 <c:if test='${radioFieldInfo.configs!=null}'>
								,${radioFieldInfo.configs}
						 </c:if>
						}
						<c:choose>
							<c:when test="${radioFieldStatus.last}"></c:when>
							<c:otherwise>,</c:otherwise>
						</c:choose>
					</c:forEach>
				]
				<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
					,${fieldInfo.sysEnFieldAttr.configs}
				</c:if>
				,margin:0 <%-- 按区间范围搜索表单嵌套了一层fieldcontainer，margin要设置为0 --%>	
			});
			</c:when>
			<%-- 单选按钮结束 --%>
			<%-- 多选按钮开始 --%>
			<c:when test='${fieldInfo.xtype=="checkboxgroup"}'>
				var  sear_field_${fieldInfo.name}_${identifier} =
			Ext.create({
				xtype: '${fieldInfo.xtype}',
				id: 'sear_field_${fieldInfo.name}_${identifier}',
				fieldLabel: '${fieldInfo.fieldLabel}',
				labelStyle:'vertical-align: middle;',
				columns: 10,
				vertical: true,
				items: [
					<c:forEach items="${fieldInfo.sysEnFieldAttr.checkboxList}" var="checkboxFieldInfo" varStatus="checkboxFieldStatus">
							{   id:'sear_field_${fieldInfo.name}_${checkboxFieldInfo.eleId}_${identifier}',
								boxLabel: '${checkboxFieldInfo.boxLabel}', name: '${fieldInfo.name}',
								inputValue: '${checkboxFieldInfo.inputValue}',width:60
								<c:if test='${checkboxFieldInfo.configs!=null}'>
									,${checkboxFieldInfo.configs}
								</c:if>
							}
						<c:choose>
							<c:when test="${checkboxFieldStatus.last}"></c:when>
							<c:otherwise>,</c:otherwise>
						</c:choose>
					</c:forEach>
				]
				<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
					,${fieldInfo.sysEnFieldAttr.configs}
				</c:if>
				,margin:0 <%-- 按区间范围搜索表单嵌套了一层fieldcontainer，margin要设置为0 --%>	
			});
			</c:when>
			<%-- 多选按钮结束 --%>
			<%-- 下拉框开始 --%>
			<c:when test='${fieldInfo.xtype=="combobox"}'>
				<c:if test='${fieldInfo.sysEnFieldAttr.isCascade}'>	
					<%-- 生成级联下拉框 --%>
					var  sear_field_${fieldInfo.name}_${identifier} =
					Ext.create({
						xtype: 'fieldcontainer',
						id: 'sear_fieldcontainer_${fieldInfo.name}_${identifier}',
						fieldLabel: '${fieldInfo.fieldLabel}',
						labelStyle:'vertical-align: middle;',
						layout: 'hbox',
						items:[
							<c:forEach items="${fieldInfo.sysEnFieldAttr.comboBoxList}" var="comboBoxFieldInfo" varStatus="comboBoxFieldStatus">
								sear_field_${comboBoxFieldInfo.comboBoxName}_${identifier}
								<c:choose>
									<c:when test="${comboBoxFieldStatus.last}"></c:when>
									<c:otherwise>,</c:otherwise>
								</c:choose>
							</c:forEach>
						]
						<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
							,${fieldInfo.sysEnFieldAttr.configs}
						</c:if>
						,margin:0 <%-- 按区间范围搜索表单嵌套了一层fieldcontainer，margin要设置为0 --%>	
					});
				</c:if>
				<c:if test='${!fieldInfo.sysEnFieldAttr.isCascade}'>
					<%-- 生成单个下拉框 --%>
					<%-- 非级联下拉框comboBoxList的size一定为1 --%>
					<c:forEach items="${fieldInfo.sysEnFieldAttr.comboBoxList}" var="comboBoxFieldInfo" varStatus="comboBoxFieldStatus">
						sear_field_${comboBoxFieldInfo.comboBoxName}_${identifier}.fieldLabel='${fieldInfo.fieldLabel}';
					</c:forEach>
				</c:if>
			</c:when>
			<%-- 下拉框结束 --%>
			<%-- Tag控件开始 --%>
			<c:when test='${fieldInfo.xtype=="tagfield"}'>
			var  sear_field_${fieldInfo.name}_${identifier} =
			Ext.create({
						xtype: 'tagfield',
						id:'sear_field_${fieldInfo.name}_${identifier}',
						name:'${fieldInfo.name}',
						fieldLabel: '${fieldInfo.fieldLabel}',
						store: sear_tagStore_${fieldInfo.name}_${identifier},
						mode: "local",
						displayField: 'name',
						valueField: 'id',
						//filterPickList: true,
						triggerAction: 'all',
						//queryMode: 'local',
						loadingText: '正在加载...',
						emptyText: "请选择",
						typeAhead: true  //延时查询
						<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
							,${fieldInfo.sysEnFieldAttr.configs}
						</c:if>
						<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
							,validator: function(value){
								return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
							}
						</c:if>
						,width:250,allowBlank:true,value:''
			});
			</c:when>
			<%-- Tag控件结束 --%>
			<%-- 下拉树开始 --%>
			<c:when test='${fieldInfo.xtype=="comboboxtree"}'>
			var  sear_field_${fieldInfo.name}_${identifier} =
			Ext.create("Ext.ux.ComboBoxTree",{
				id: 'sear_field_${fieldInfo.name}_${identifier}',
				name: '${fieldInfo.name}',
				fieldLabel: '${fieldInfo.fieldLabel}',
				editable: false,
				loadTreeDataUrl:'<%=basePath%>${fieldInfo.sysEnFieldAttr.url}'
				<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
					,${fieldInfo.sysEnFieldAttr.configs}
				</c:if>
				<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
					,validator: function(value){
						return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
					}
				</c:if>
				,width:250,allowBlank:true,value:''
			});
			</c:when>
			<%-- 下拉树结束 --%>
			<%-- 继承了text控件的表单元素开始 --%>
			<c:otherwise>
			
				<c:choose>
				  <c:when test='${fieldInfo.isSearByRange}'>
					var  sear_field_${fieldInfo.name}_${identifier}_start =
					Ext.create({
								xtype: '${fieldInfo.xtype}',
								id: 'sear_field_${fieldInfo.name}_${identifier}_start',
								name: '${fieldInfo.name}Start'
								<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
									,${fieldInfo.sysEnFieldAttr.configs}
								</c:if>
								<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
									,validator: function(value){
										return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
									}
								</c:if>
								,width:150,
								labelWidth:0,allowBlank:true,value:''
								<c:if test='${ fieldInfo.xtype=="datetimefield" && fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.startTimeForRange!=null}'>
									,value:'${fieldInfo.sysEnFieldAttr.startTimeForRange}'
								</c:if>
							});
					resetFieldConLeftArray.push( sear_field_${fieldInfo.name}_${identifier}_start );	
							
					var  sear_field_${fieldInfo.name}_${identifier}_connector =
					Ext.create({
								xtype: 'container',html: '至',
								width:18,labelWidth:0,
								style:{lineHeight:'22px',textAlign: 'center'}
							});
					resetFieldConLeftArray.push( sear_field_${fieldInfo.name}_${identifier}_connector );	
							
					var  sear_field_${fieldInfo.name}_${identifier}_end =
					Ext.create({
								xtype: '${fieldInfo.xtype}',
								id: 'sear_field_${fieldInfo.name}_${identifier}_end',
								name: '${fieldInfo.name}End'
								<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
									,${fieldInfo.sysEnFieldAttr.configs}
								</c:if>
								<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
									,validator: function(value){
										return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
									}
								</c:if>
								,width:150,
								labelWidth:0,allowBlank:true,value:''
								<c:if test='${ fieldInfo.xtype=="datetimefield" && fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.endTimeForRange!=null}'>
									,value:'${fieldInfo.sysEnFieldAttr.endTimeForRange}'
								</c:if>
							});
					resetFieldConLeftArray.push( sear_field_${fieldInfo.name}_${identifier}_end );	
							
					var  sear_field_${fieldInfo.name}_${identifier} =
					Ext.create({
						xtype: 'fieldcontainer'
						,id: 'sear_fieldcontainer_${fieldInfo.name}_${identifier}'
						,fieldLabel: '${fieldInfo.fieldLabel}'
						,labelStyle:'vertical-align: middle;'
						,layout: 'hbox'
						,width:450
						,margin:0 <%-- 按区间范围搜索表单嵌套了一层fieldcontainer，margin要设置为0 --%>	
						,items:[
							sear_field_${fieldInfo.name}_${identifier}_start,
							sear_field_${fieldInfo.name}_${identifier}_connector,
							sear_field_${fieldInfo.name}_${identifier}_end
						]
					});	
					
				  </c:when>
				  <c:otherwise>
					var  sear_field_${fieldInfo.name}_${identifier} =
					Ext.create({
						xtype: '${fieldInfo.xtype}',
						id: 'sear_field_${fieldInfo.name}_${identifier}',
						name: '${fieldInfo.name}',
						fieldLabel: '${fieldInfo.fieldLabel}'
						<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
							,${fieldInfo.sysEnFieldAttr.configs}
						</c:if>
						<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
							,validator: function(value){
								return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
							}
						</c:if>
						,width:250,allowBlank:true,value:''
					});
				  </c:otherwise>
				</c:choose>
			</c:otherwise>
			<%-- 继承了text控件的表单元素结束 --%>
		</c:choose>
	</c:forEach>
	<%-- 生成所有搜索字段表单元素结束 --%>
	var searPanelFormElemeLeftMargin =3;
	var searPanel${identifier}=new Ext.FormPanel({
		id:'searPanel${identifier}',
		title:'查询条件',
		url: '<%=basePath%>${fwSearchModelSearchUrl}',
		collapsible:true,
		region: 'north',
		//height:${searchConditionRowNum*28+60},
		scrollable:true,
		resizable:true,
		frame:true,
		defaultType: 'textfield',
		fieldDefaults: {
			labelWidth: 100,
			labelAlign: "right",
			width:250,
			flex: 0,//每项item的宽度权重。值为0或未设置此属性时，item的width值才起作用。
			margin: searPanelFormElemeLeftMargin
		},
		layout: {
			type: 'table',
			columns: ${searchConditionColNum}
		},
		items: [
			<c:forEach items="${searFieldList}" var="fieldInfo" varStatus="fieldStatus">
				sear_field_${fieldInfo.name}_${identifier},
			</c:forEach>
			{
				xtype: "container",
				style:{paddingLeft:'103px'},
				width:250,
				layout:{
					type:"hbox",
					align: 'middle'
				},
				items: [
					{ id:'searReset${identifier}', xtype: 'button',text:'清空',width:60,height:25,
					  margin:'0 10 0 10',
					  listeners: {
					  	click: function() {
							<c:forEach items="${searFieldList}" var="fieldInfo" varStatus="validFieldStatus">
								<c:if test='${fieldInfo.xtype=="comboboxtree"}'>
								<%-- 下拉树是自定义组件，重置表单需要特殊处理。 --%>
								sear_field_${fieldInfo.name}_${identifier}.clearValue();
								</c:if>
							</c:forEach>
					  		searReset${identifier}();
					  	}
					  }
					},
					{ id:'searSubmit${identifier}', xtype: 'button',text:'查询',width:60,height:25,
					  margin:'0 10 0 10',
					  listeners: {
					  	click: function() {
							<c:forEach items="${searFieldList}" var="fieldInfo" varStatus="validFieldStatus">
								<c:choose>
									<%-- 单选按钮开始 --%>
									<c:when test='${fieldInfo.xtype=="radiogroup"}'>
									    var temp_${fieldInfo.name} = sear_field_${fieldInfo.name}_${identifier}.getValue();
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					  		searSubmit${identifier}();
					  	}
					  }
					}
				]
			}
			
		],
		listeners   : {afterlayout:function(){
				<%-- 纠正级联框左侧与其它控件对齐问题。须在afterlayout后执行，
					 ext计算布局完毕后，重新设置left值。
				--%>
				searPanelFormElemeLeftMargin =0;
				resetComLeft(resetFieldConLeftArray,searPanelFormElemeLeftMargin);
			}
		},
	});

	return searPanel${identifier};
}

<%-- 创建搜索区域面板 --%>
var searPanel${identifier} = createSearPanel_${identifier}();

function searReset${identifier}() {
	var searForm = 	searPanel${identifier}.getForm( );
	//searForm.reset( true );
	searForm.reset( false );
}

function searSubmit${identifier}() {
	${fwSearchModelSearchCallbackFunctionName}("${fwSearchModelFreeAreaElementId}",searPanel${identifier}.getForm() );
	
	
}
<%-- 搜索区域结束 --%>

	var serarchModelFreeArea = Ext.create('Ext.panel.Panel', {
           bodyPadding: 5,  
           title: '查询结果',
           region: 'center',
           width:'100%',
           autoHeight: true,
           layout:'fit',
           viewConfig:{
               forceFit:true
           },
           html:'<div id="${fwSearchModelFreeAreaElementId}" style="width: 100%;height:100%;" ></div>'
       });

Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();	
	<%-- 组装渲染展示列表页面开始 --%>
	var pagePanel${identifier} =new Ext.container.Container({
		id:'pagePanel${identifier}',  
		//renderTo:'pageContainer${identifier}',
		region: 'center',
		layout:'border',
		resizable:true, 
		border:false,  
		width:'100%',
		height:'100%', 
		bodyStyle: {
			background: '#aa312f2'
		},
		//contentEl: 'containerId',
		items:[searPanel${identifier},serarchModelFreeArea]
	});
	
	/**
	* 说明1：此代码意义非凡，绝对属于核心代码
	* loader异步加载页面时，loader加载到的页面无法自动布局进行页面大小自适应
	* 解决方法：
	* 			pagePanel不指定renderTo属性，使用Ext.getCmp(parentId).add( pagePanel )方法加载页面
	* 说明2：getCmp的add方法在非IE内核浏览器中加载部分tab页面时，第一次无法成功加载，
	*			需要关闭tab页面后，再次打开tab页面才能加载成功。此现象在IE内核浏览器中不存在。
	*		   经调试发现，使用try..catch块包住后，在非IE内核中部分tab页面无法第一次加载成功的现象消失。	
	*/
	try{
		Ext.getCmp('right_tab_${identifier}').add( pagePanel${identifier} );	
	}catch(e){
	}

	<%-- 组装渲染展示列表页面结束 --%>
});	
</script>

  </head>
  <body>
  </body>
</html>
