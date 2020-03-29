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
	
	<%--
	<c:if test='${controller.formValJsFileName!=null}'>
		<script type="text/javascript"
		 	src="resource/platform/js/platform/${controller.formValJsFileName}"></script>
	</c:if>
	--%>
	<c:if test='${controller.jsFileNameSet!=null}'>
		<c:forEach items="${controller.jsFileNameSet}" var="jsFileName">
			<script type="text/javascript"
				src="resource/platform/js/platform/${jsFileName}"></script>
		</c:forEach>
	</c:if>
	<c:if test='${controller.jsFileNameUserDefinePathSet!=null}'>
		<c:forEach items="${controller.jsFileNameUserDefinePathSet}" var="jsFileName">
			<script type="text/javascript"
				src="${jsFileName}"></script>
		</c:forEach>
	</c:if>
	 
	<script type="text/javascript">
	
<%-- 刷新列表数据方法 --%>
function refreshList_${identifier}(){
	<%-- 刷新当前页面 --%>
	dataStore_${identifier}.load();
}

<%-- 删除单条数据方法 --%>
function deleteSingleData_${identifier}(deleteUrl,id){
	Ext.Msg.confirm("提示信息","确定要删除此条数据吗？",function (btn) {
		if( btn=="yes" ){
			Ext.Ajax.request({
				url: '<%=basePath%>${controllerBaseUrl}'+deleteUrl,
				params: {
					ids: id
				},
				method:'POST',
				success: function(response){
					var responseStr = response.responseText;
					var responseJsonObj = Ext.JSON.decode( responseStr );

					if( responseJsonObj.success ){
						Ext.Msg.alert('提示', '删除成功！',function(){
							refreshList_${identifier}();
						});
					}else{
						Ext.Msg.alert('提示', '抱歉，删除失败！'+responseJsonObj.msg );
					}

				},
				failure:function(response){
					Ext.Msg.alert('提示', '抱歉，删除失败，出错了！' );
				}
			});
		}
	});

}

<%-- 操作单条数据异步请求方法 --%>
function singleBaseAjaxReq_${identifier}(url,id){
    Ext.Msg.confirm("提示信息","确定要操作此条数据吗？",function (btn) {
        if( btn=="yes" ){
            Ext.Ajax.request({
                url: '<%=basePath%>'+url,
                params: {
                    id: id
                },
                method:'POST',
                success: function(response){
                    var responseStr = response.responseText;
                    var responseJsonObj = Ext.JSON.decode( responseStr );

                    if( responseJsonObj.success ){
                        Ext.Msg.alert('提示', '操作成功！',function(){
                            refreshList_${identifier}();
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

	<%-- 定义下拉框数据模型 --%>
	Ext.define('comboBoxDataModel${identifier}', {
			extend: 'Ext.data.Model',
			fields: ['id', 'name']
	});
	
	<%-- 添加窗口方法开始 --%>
	<c:if test='${baseAddPriv}'><%-- 有添加权限则生成添加的相关方法 --%>
	function openAddWindow_${identifier}(){
		<%-- 存放需要调整样式left值的级联comboBox。解决不对齐问题 --%>
		var resetLeftComboArray=new Array();
		var comboCascadeActivateState = "init";
		<%-- 下拉框初始化数据开始 --%>
		<c:forEach items="${comboBoxAddList}" var="comboBoxInfo" varStatus="comboBoxStatus">
			<%-- 定义下拉框store开始 --%>
			<c:choose>
				<%-- 使用json串初始化下拉框数据 --%>
				<c:when test='${comboBoxInfo.loadDataImplModel=="json"}'>
					var comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier} = Ext.create('Ext.data.Store', {
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
					var comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier} = new Ext.data.Store({
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
					<c:choose>
						<%-- 加载第一个下拉框的数据(级联有多个下拉框，非级联只有一个下拉框) --%>
						<c:when test='${ !comboBoxInfo.isCascade || ( comboBoxInfo.isCascade && comboBoxInfo.isFirst )}'>
							comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.on("beforeload",function(){
								Ext.apply(comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.proxy.extraParams, {"comboBoxFlag":"${comboBoxInfo.comboBoxFlag}","value":"${comboBoxInfo.firstComboBoxParamValue}"});
							});
							comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.load();
						</c:when>
					</c:choose>
				</c:when>
	
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			<%-- 定义下拉框store结束 --%>
			
			<%-- 定义下拉框 --%>
			var  field_${comboBoxInfo.comboBoxName}_${identifier} = Ext.create('Ext.form.ComboBox', {
				id:'comboBoxId${comboBoxInfo.comboBoxName}${identifier}'
				,name:'${comboBoxInfo.comboBoxName}'
				,xtype: 'combobox'
				,store: comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}
				,triggerAction: 'all'
				//,queryMode: 'local'
				,displayField: 'name'
				,valueField: 'id'
				,width:100
				,loadingText: '正在加载...'
				,emptyText: "请选择"
				,mode: "local"
				,typeAhead: true  //延时查询
				<c:if test='${comboBoxInfo.configs!=null}'>
					,${comboBoxInfo.configs}
				</c:if>
				<c:if test='${comboBoxInfo.validatorFunName!=null}'>
				,validator: function(value){
					return ${comboBoxInfo.validatorFunName}(value ${comboBoxInfo.validatorFunField});
				}
				</c:if>
				,listeners : {focus:function(){
						<%--任何一个下拉框获得焦点，则激活起用级联框级联效果。--%>
						comboCascadeActivateState = "activateCascade";
					}
				}
			});
			<%-- 最后再设置值，防止comboBoxInfo.configs中的配置覆盖。
				 不能通过配置value设置值，通过配置设置value，级联框清空子级联框架值时有bug，需要通过setValue方法设置。 --%>
			if( '${comboBoxInfo.value}'.length>0 ){
				field_${comboBoxInfo.comboBoxName}_${identifier}.setValue( '${comboBoxInfo.value}' );
			}
			<c:if test='${comboBoxInfo.isCascade}'>
				resetLeftComboArray.push( field_${comboBoxInfo.comboBoxName}_${identifier} );	
			</c:if>
			
			<c:choose>
				<%-- 绑定下拉框级联事件 --%>
				<c:when test='${ comboBoxInfo.loadDataImplModel=="sql" && comboBoxInfo.isCascade && !comboBoxInfo.isLast }'>
					field_${comboBoxInfo.comboBoxName}_${identifier}.on('change', function() {
						<%--级联状态被激活才起用级联效果。
							为了解决给级联框设置初始化值时，所有子级联框值都被清空问题。调用sevValue方法会触发chang事件。
							任何一个下拉框获得焦点后，再激活起用级联框级联效果。
							获得焦点说明界面初始化工作都已结束。--%>
						if( comboCascadeActivateState=="activateCascade" ){
							<c:forEach items="${comboBoxInfo.cascadeList}" var="cascadeComboBoxInfo" varStatus="cascadeComboBoxStatus">
								<%-- 给comboBox赋了初始值使用clearValue()方法不起作用，清除不了值。使用setValue("")清除。问题比较奇怪 --%>
								//field_${cascadeComboBoxInfo.comboBoxName}_${identifier}.clearValue();
								field_${cascadeComboBoxInfo.comboBoxName}_${identifier}.setValue( null );
								field_${cascadeComboBoxInfo.comboBoxName}_${identifier}.reset();
								
								comboBoxStore_${cascadeComboBoxInfo.comboBoxName}_${identifier}.removeAll();
							</c:forEach>
							Ext.apply(comboBoxStore_${comboBoxInfo.childComboBox}_${identifier}.proxy.extraParams, 
										{"comboBoxFlag":"${comboBoxInfo.comboBoxFlag}",
										"value":field_${comboBoxInfo.comboBoxName}_${identifier}.getValue()});
							comboBoxStore_${comboBoxInfo.childComboBox}_${identifier}.load();
						}
					}); 
				</c:when>
			</c:choose>
			
			<c:choose>
				<%-- 请求后台查询数据库初始化下拉框数据(所有的下拉框store都要初始化) --%>
				<c:when test='${comboBoxInfo.loadDataImplModel=="sql"}'>
					comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.on("beforeload",function(){
								Ext.apply(comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.proxy.extraParams, 
									{"comboBoxFlag":"${comboBoxInfo.comboBoxFlag}",
										<c:choose>
											<%-- 加载第一个下拉框的数据(级联有多个下拉框，非级联只有一个下拉框) --%>
											<c:when test='${ !comboBoxInfo.isCascade || ( comboBoxInfo.isCascade && comboBoxInfo.isFirst )}'>			
												"value":"${comboBoxInfo.firstComboBoxParamValue}"
											</c:when>
											<c:otherwise>
												"value":field_${comboBoxInfo.parentComboBox}_${identifier}.getValue()
											</c:otherwise>
										</c:choose>
								});
					});
					<c:choose>
						<%-- 加载级联第一个下拉框或非级联下拉框(非级联下拉框只有一个) --%>
						<c:when test='${ !comboBoxInfo.isCascade || ( comboBoxInfo.isCascade && comboBoxInfo.isFirst )}'>			
							comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.load();
						</c:when>
						<c:otherwise>
							<%-- 加载级联下拉框数据(除第一个下拉框),父级联框有数据时才加载子级联框 --%>
						    if( field_${comboBoxInfo.parentComboBox}_${identifier}.getValue() ){
								comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.load();
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
		<c:forEach items="${tagAddList}" var="tagInfo" varStatus="tagStatus">
			<%-- 定义下拉框store开始 --%>
			<c:choose>
				<%-- 使用json串初始化下拉框数据 --%>
				<c:when test='${tagInfo.loadDataImplModel=="json"}'>
					var tagStore_${tagInfo.comboBoxName}_${identifier} = Ext.create('Ext.data.Store', {
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
					var tagStore_${tagInfo.comboBoxName}_${identifier} = new Ext.data.Store({
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
					tagStore_${tagInfo.comboBoxName}_${identifier}.on("beforeload",function(){
						Ext.apply(tagStore_${tagInfo.comboBoxName}_${identifier}.proxy.extraParams, {"comboBoxFlag":"${tagInfo.comboBoxFlag}","value":"${tagInfo.firstComboBoxParamValue}"});
					});
					tagStore_${tagInfo.comboBoxName}_${identifier}.load();
				</c:when>
	
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			<%-- 定义下拉框store结束 --%>
		</c:forEach>
		<%-- tag控件下拉框初始化数据结束 --%>		
		
		<%-- 生成所有字段表单元素开始 --%>
		<c:forEach items="${addFieldList}" var="fieldInfo" varStatus="validFieldStatus">
			<c:choose>
				<%-- 单选按钮开始 --%>
				<c:when test='${fieldInfo.xtype=="radiogroup"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: '${fieldInfo.xtype}',
					id: field_${fieldInfo.name}_${identifier},
					fieldLabel: '${fieldInfo.fieldLabel}',
					labelStyle:'vertical-align: middle;',
					columns: 10,
					vertical: true,
					items: [
						<c:forEach items="${fieldInfo.sysEnFieldAttr.radioList}" var="radioFieldInfo" varStatus="radioFieldStatus">
							{ boxLabel: '${radioFieldInfo.boxLabel}', name: '${fieldInfo.name}',
							inputValue: '${radioFieldInfo.inputValue}',
							checked:${radioFieldInfo.checked},width:60
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
					<c:if test='${!fieldInfo.isAllowBlank}'>
						,beforeLabelTextTpl: ['<span class="required">*</span>']
					</c:if>
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
				});
				</c:when>
				<%-- 单选按钮结束 --%>
				<%-- 多选按钮开始 --%>
				<c:when test='${fieldInfo.xtype=="checkboxgroup"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: '${fieldInfo.xtype}',
					id: field_${fieldInfo.name}_${identifier},
					fieldLabel: '${fieldInfo.fieldLabel}',
					labelStyle:'vertical-align: middle;',
					columns: 10,
					vertical: true,
					items: [
						<c:forEach items="${fieldInfo.sysEnFieldAttr.checkboxList}" var="checkboxFieldInfo" varStatus="checkboxFieldStatus">
								{ boxLabel: '${checkboxFieldInfo.boxLabel}', name: '${fieldInfo.name}',
									inputValue: '${checkboxFieldInfo.inputValue}',
									checked:${checkboxFieldInfo.checked},width:60
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
					<c:if test='${!fieldInfo.isAllowBlank}'>
						,beforeLabelTextTpl: ['<span class="required">*</span>']
					</c:if>
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
				});
				</c:when>
				<%-- 多选按钮结束 --%>
				<%-- 下拉框开始 --%>
				<c:when test='${fieldInfo.xtype=="combobox"}'>
					<c:if test='${fieldInfo.sysEnFieldAttr.isCascade}'>
					 var  field_${fieldInfo.name}_${identifier} =
						Ext.create({
							xtype: 'fieldcontainer',
							id: field_${fieldInfo.name}_${identifier},
							fieldLabel: '${fieldInfo.fieldLabel}',
							labelStyle:'vertical-align: middle;',
							layout: 'hbox',
							items:[
								<c:forEach items="${fieldInfo.sysEnFieldAttr.comboBoxList}" var="comboBoxFieldInfo" varStatus="comboBoxFieldStatus">
									field_${comboBoxFieldInfo.comboBoxName}_${identifier}
									<c:choose>
										<c:when test="${comboBoxFieldStatus.last}"></c:when>
										<c:otherwise>,</c:otherwise>
									</c:choose>
								</c:forEach>
							]
							<c:if test='${!fieldInfo.isAllowBlank}'>
								,beforeLabelTextTpl: ['<span class="required">*</span>']
							</c:if>
							<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
								,${fieldInfo.sysEnFieldAttr.configs}
							</c:if>
						});
					</c:if>
					<c:if test='${!fieldInfo.sysEnFieldAttr.isCascade}'>
						<%-- 非级联下拉框comboBoxList的size一定为1 --%>
						<c:forEach items="${fieldInfo.sysEnFieldAttr.comboBoxList}" var="comboBoxFieldInfo" varStatus="comboBoxFieldStatus">
							//field_${comboBoxFieldInfo.comboBoxName}_${identifier};
							field_${comboBoxFieldInfo.comboBoxName}_${identifier}.fieldLabel='${fieldInfo.fieldLabel}';
							<c:if test='${!fieldInfo.isAllowBlank}'>
								field_${comboBoxFieldInfo.comboBoxName}_${identifier}.beforeLabelTextTpl= ['<span class="required">*</span>'];
							</c:if>
						</c:forEach>
					</c:if>
				
				</c:when>
				<%-- 下拉框结束 --%>
				<%-- Tag控件开始 --%>
				<c:when test='${fieldInfo.xtype=="tagfield"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
						xtype: 'tagfield',
						id:'field_${fieldInfo.name}_${identifier}',
						name:'${fieldInfo.name}',
						fieldLabel: '${fieldInfo.fieldLabel}',
						store: tagStore_${fieldInfo.name}_${identifier},
						mode: "local",
						displayField: 'name',
						valueField: 'id',
						//filterPickList: true,
						triggerAction: 'all',
						//queryMode: 'local',
						loadingText: '正在加载...',
						emptyText: "请选择",
						typeAhead: true  //延时查询
						<c:if test='${!fieldInfo.isAllowBlank}'>
							,beforeLabelTextTpl: ['<span class="required">*</span>']
						</c:if>
						<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
							,${fieldInfo.sysEnFieldAttr.configs}
						</c:if>
						<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
							,validator: function(value){
								return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
							}
						</c:if>
				});
				</c:when>
				<%-- Tag控件结束 --%>
				<%-- 下拉树开始 --%>
				<c:when test='${fieldInfo.xtype=="comboboxtree"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create("Ext.ux.ComboBoxTree",{
					id:'field_${fieldInfo.name}_${identifier}',
					name: '${fieldInfo.name}',
					fieldLabel: '${fieldInfo.fieldLabel}',
					editable: false,
					loadTreeDataUrl:'<%=basePath%>${fieldInfo.sysEnFieldAttr.url}'
					<c:if test='${!fieldInfo.isAllowBlank}'>
						,beforeLabelTextTpl: ['<span class="required">*</span>']
					</c:if>
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
						,validator: function(value){
							return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
						}
					</c:if>
				});
				</c:when>
				<%-- 下拉树结束 --%>
				<%-- 附件开始 --%>
				<c:when test='${fieldInfo.xtype=="filefield"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: '${fieldInfo.xtype}',
					id:'field_${fieldInfo.name}_${identifier}',
					name: '${fieldInfo.name}',
					fieldLabel: '${fieldInfo.fieldLabel}',
					buttonText:'选择文件',
					afterBodyEl:'<span onclick="deleteFileLocal(\'field_${fieldInfo.name}_${identifier}\')" style="cursor:pointer;" ><font color="red">删除</font></span>',
					validator: function(value){
						<c:if test='${fieldInfo.sysEnFieldAttr!=null}'>
							return validateSuffix(value,'${fieldInfo.sysEnFieldAttr.allowFileType}');
						</c:if>
						
						<c:if test='${fieldInfo.sysEnFieldAttr==null}'>
							return "文件格式校验失败！缺少文件格式校验配置";
						</c:if>
					}
					//afterLabelTextTpl:['<font color=red>*</font>']
					<c:if test='${!fieldInfo.isAllowBlank}'>
						,beforeLabelTextTpl: ['<span class="required">*</span>']
					</c:if>
					
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
						,validator: function(value){
							return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
						}
					</c:if>
				});
				</c:when>
				<%-- 附件结束 --%>
				<%-- 富文本编辑器开始 --%>
				<c:when test='${fieldInfo.xtype=="htmleditor"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: '${fieldInfo.xtype}',
					id:'field_${fieldInfo.name}_${identifier}',
					name: '${fieldInfo.name}',
					fieldLabel: '${fieldInfo.fieldLabel}',
					fontFamilies :['宋体','隶书','黑体','Arial', 'Courier New', 'Tahoma', 'Times New Roman', 'Verdana']
					<c:if test='${!fieldInfo.isAllowBlank}'>
						,beforeLabelTextTpl: ['<span class="required">*</span>']
					</c:if>
					
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
				});
				</c:when>
				<%-- 富文本编辑器结束 --%>
				<%-- 隐藏域或display开始 --%>
				<c:when test='${fieldInfo.xtype=="hiddenfield" || fieldInfo.xtype=="displayfield"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: '${fieldInfo.xtype}',
					id:'field_${fieldInfo.name}_${identifier}',
					name: '${fieldInfo.name}',
					fieldLabel: '${fieldInfo.fieldLabel}'
					<c:if test='${!fieldInfo.isAllowBlank}'>
						,beforeLabelTextTpl: ['<span class="required">*</span>']
					</c:if>
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
				});
				</c:when>
				<%-- 隐藏域或display结束 --%>
				<%-- 继承了text控件的表单元素开始 --%>
				<c:otherwise>
					<c:choose>
						<c:when test='${fieldInfo.isEncryption}'>
							<%-- 加密字段处理开始，加密字段处理需要有隐藏域配合 --%>
							var  field_${fieldInfo.name}_${identifier} =
							Ext.create({
								xtype: '${fieldInfo.xtype}',
								id: 'field_${fieldInfo.name}_${identifier}_encryption',
								name: '${fieldInfo.name}_encryption',
								<%-- 不提交表单，只用于页面显示 --%>
								fieldLabel: '${fieldInfo.fieldLabel}'
								<c:if test='${!fieldInfo.isAllowBlank}'>
									,beforeLabelTextTpl: ['<span class="required">*</span>']
								</c:if>
								<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
									,${fieldInfo.sysEnFieldAttr.configs}
								</c:if>
								<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
									,validator: function(value){
										return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
									}
								</c:if>
								<%-- 加密字段在编辑页面中初始值设置为空、非必填 --%>
								<c:if test='${fieldInfo.isEncryption}'>
									,value:'',submitValue :false
								</c:if>
							});
							
							var  field_${fieldInfo.name}_${identifier}_encryption_hidden =
							Ext.create({
								xtype: 'hiddenfield',
								id: 'field_${fieldInfo.name}_${identifier}',
								name: '${fieldInfo.name}',
								<%-- 提交表单，存放加密后的值 --%>
								fieldLabel: '${fieldInfo.fieldLabel}'
								<%-- 加密字段初始值设置为空 --%>
								<c:if test='${fieldInfo.isEncryption}'>
									,value:''
								</c:if>
							});
							<%-- 加密字段处理结束 --%>
						</c:when>
						<c:otherwise>
							var  field_${fieldInfo.name}_${identifier} =
							Ext.create({
								xtype: '${fieldInfo.xtype}',
								id:'field_${fieldInfo.name}_${identifier}',
								name: '${fieldInfo.name}',
								fieldLabel: '${fieldInfo.fieldLabel}'
								<c:if test='${!fieldInfo.isAllowBlank}'>
									,beforeLabelTextTpl: ['<span class="required">*</span>']
								</c:if>
								<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
									,${fieldInfo.sysEnFieldAttr.configs}
								</c:if>
								<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
									,validator: function(value){
										return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
									}
								</c:if>
							});
						</c:otherwise>
					</c:choose>
				</c:otherwise>
				<%-- 继承了text控件的表单元素结束 --%>
			</c:choose>
		</c:forEach>
		<%-- 生成所有字段表单元素结束 --%>
		var addPanelFormElemeLeftMargin = 8;	
		var  addPanel_${identifier}=new Ext.FormPanel({
			id:'addPanel_${identifier}',
			frame : true,
			bodyBorder:false,
			bodyStyle : 'padding:0px 0px 0px 0px',
			/*border:true,*/
			// The form will submit an AJAX request to this URL when submitted
			url: '<%=basePath%>${controllerBaseUrl}save.do',
			defaultType: 'textfield',
			fieldDefaults: {
				labelWidth: 100,
				labelAlign: "right",
				width:400,
				flex: 0,//每项item的宽度权重。值为0或未设置此属性时，item的width值才起作用。
				margin: addPanelFormElemeLeftMargin
			},
			items: [
					<c:forEach items="${addFieldList}" var="fieldInfo" varStatus="fieldStatus">
						field_${fieldInfo.name}_${identifier}
						<c:choose>
							<c:when test="${fieldInfo.isEncryption}">
								,field_${fieldInfo.name}_${identifier}_encryption_hidden
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${fieldStatus.last}"></c:when>
							<c:otherwise>,</c:otherwise>
						</c:choose>
					</c:forEach>
			],
			// Reset and Submit buttons
			buttons: [{
				text: '重置',
				handler: function() {
					<%-- 保存并继续按钮也有相同的操作来重置表单，有改动需要一起修改 --%>
					<c:forEach items="${addFieldList}" var="fieldInfo" varStatus="validFieldStatus">
						<c:if test='${fieldInfo.xtype=="comboboxtree"}'>
						<%-- 下拉树是自定义组件，重置表单需要特殊处理。 --%>
						field_${fieldInfo.name}_${identifier}.clearValue();
						</c:if>
					</c:forEach>
					this.up('form').getForm().reset();
				}
			}, {
				<%-- "保存并继续"按钮与"保存"按钮代码基本一致（除提交表单后的success回调函数）。
					  有改动需要一并修改--%>
				text: '保存',
				formBind: true, //only enabled once the form is valid
				disabled: true,
				handler: function() {
					var form = this.up('form').getForm();
					if (form.isValid()) {
						<c:forEach items="${addFieldList}" var="fieldInfo" varStatus="fieldStatus">
						<c:if test='${fieldInfo.isEncryption}'>
							var fieldInfo_${fieldInfo.name}_${identifier}_temp_val = field_${fieldInfo.name}_${identifier}.getValue();
							if( fieldInfo_${fieldInfo.name}_${identifier}_temp_val!=null  
								&& fieldInfo_${fieldInfo.name}_${identifier}_temp_val!=""
								&& $.trim( fieldInfo_${fieldInfo.name}_${identifier}_temp_val ).length>0	){
									
								fieldInfo_${fieldInfo.name}_${identifier}_temp_val = $.trim( fieldInfo_${fieldInfo.name}_${identifier}_temp_val );
								fieldInfo_${fieldInfo.name}_${identifier}_temp_val = $.md5 ( fieldInfo_${fieldInfo.name}_${identifier}_temp_val );
								field_${fieldInfo.name}_${identifier}_encryption_hidden.setValue( fieldInfo_${fieldInfo.name}_${identifier}_temp_val );
							}
						</c:if>
						</c:forEach>
						
						form.submit({
							submitEmptyText :false,
							waitMsg :'正在保存，请耐心等待......',
							success: function(form, action) {
								Ext.Msg.show({
									title:"提示信息",
									message:action.result.msg,
									buttons:Ext.Msg.OK,
									icon: Ext.Msg.INFO,
									fn: function(btn) {
										//关闭添加窗口
										addWindow${identifier}.close();
										//刷新当前页面
										dataStore_${identifier}.load();
									}
	
								});
							},
							failure: function(form, action) {
								Ext.Msg.alert('错误提示', "抱歉，出错了！"+action.result.msg);
							}
						});
					}
	
				}
			}, {
				text: '保存并继续',
				formBind: true, //only enabled once the form is valid
				disabled: true,
				handler: function() {
					var form = this.up('form').getForm();
					if (form.isValid()) {
						<c:forEach items="${addFieldList}" var="fieldInfo" varStatus="fieldStatus">
						<c:if test='${fieldInfo.isEncryption}'>
							var fieldInfo_${fieldInfo.name}_${identifier}_temp_val = field_${fieldInfo.name}_${identifier}.getValue();
							if( fieldInfo_${fieldInfo.name}_${identifier}_temp_val!=null  
								&& fieldInfo_${fieldInfo.name}_${identifier}_temp_val!=""
								&& $.trim( fieldInfo_${fieldInfo.name}_${identifier}_temp_val ).length>0	){
									
								fieldInfo_${fieldInfo.name}_${identifier}_temp_val = $.trim( fieldInfo_${fieldInfo.name}_${identifier}_temp_val );
								fieldInfo_${fieldInfo.name}_${identifier}_temp_val = $.md5 ( fieldInfo_${fieldInfo.name}_${identifier}_temp_val );
								field_${fieldInfo.name}_${identifier}_encryption_hidden.setValue( fieldInfo_${fieldInfo.name}_${identifier}_temp_val );
							}
						</c:if>
						</c:forEach>
						
						form.submit({
							submitEmptyText :false,
							waitMsg :'正在保存，请耐心等待......',
							success: function(form, action) {
								Ext.Msg.show({
									title:"提示信息",
									message:action.result.msg,
									buttons:Ext.Msg.OK,
									icon: Ext.Msg.INFO,
									fn: function(btn) {
										<c:forEach items="${addFieldList}" var="fieldInfo" varStatus="validFieldStatus">
										<c:if test='${fieldInfo.xtype=="comboboxtree"}'>
											<%-- 下拉树是自定义组件，重置表单需要特殊处理。 --%>
											field_${fieldInfo.name}_${identifier}.clearValue();
										</c:if>
										</c:forEach>
										form.reset();
	
										dataStore_${identifier}.load();//刷新当前页面
									}
	
								});
							},
							failure: function(form, action) {
								Ext.Msg.alert('错误提示', "抱歉，出错了！"+action.result.msg);
							}
						});
					}
	
				}
			}]
		});
	
		var addWindow${identifier} = new Ext.Window({
			id:'addWindow${identifier}',
			title: '添加窗口',
			width:550,
			maxHeight:500,
			scrollable:true,
			//autoHeight:true,
			resizable:false,
			bodyStyle : 'padding:0px 0px 0px 0px',
			<%-- closeAction应该使用destroy --%>
			//closeAction : 'hide',   默认为destroy
			modal : true,
			plain:false,
			//listeners   : {'hide':{fn: closeWindowPlatform}},
			listeners   : {afterlayout:function(){
					<%-- 纠正级联框左侧与其它控件对齐问题。须在afterlayout后执行，
						 ext计算布局完毕后，重新设置left值。--%>
					resetComLeft(resetLeftComboArray,addPanelFormElemeLeftMargin);
				}
			},
			items: [
				addPanel_${identifier}
			]
			<c:if test='${controller.addWindowConfigs!=null}'>
				,${controller.addWindowConfigs}
			</c:if>
		});
		
		addWindow${identifier}.show();
  }
  </c:if><%-- 判断是否有添加权限结束标签 --%>
  <%-- 添加窗口方法结束 --%>
	
	<%-- 编辑窗口方法开始--%>
	<c:if test='${baseEditPriv}'><%-- 有编辑权限则生成编辑的相关方法 --%>
	function openEditWindow_${identifier}( beanValJson ){
		<%-- 存放需要调整样式left值的级联comboBox。解决不对齐问题 --%>
		var resetLeftComboArray=new Array();
		var comboCascadeActivateState = "init";
		<%-- 下拉框初始化数据开始 --%>
		<c:forEach items="${comboBoxUpdateList}" var="comboBoxInfo" varStatus="comboBoxStatus">
			<%-- 定义下拉框store开始 --%>
			<c:choose>
				<%-- 使用json串初始化下拉框数据 --%>
				<c:when test='${comboBoxInfo.loadDataImplModel=="json"}'>
					var comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier} = Ext.create('Ext.data.Store', {
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
					var comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier} = new Ext.data.Store({
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
			var  field_${comboBoxInfo.comboBoxName}_${identifier} = Ext.create('Ext.form.ComboBox', {
					id:'field_${comboBoxInfo.comboBoxName}_${identifier}',
					name:'${comboBoxInfo.comboBoxName}',
					//value:beanValJson.${comboBoxInfo.comboBoxName},		
					xtype: 'combobox',
					store: comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier},
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
					,listeners : {focus:function(){
						<%--任何一个下拉框获得焦点，则激活起用级联框级联效果。--%>
						comboCascadeActivateState = "activateCascade";
					}
				}
			});
			<%-- 最后再设置值，防止comboBoxInfo.configs中的配置覆盖 --%>
			if( '${comboBoxInfo.value}'.length>0 ){
				<%-- 默认值 --%>
				field_${comboBoxInfo.comboBoxName}_${identifier}.setValue( '${comboBoxInfo.value}' );
			}
			if( beanValJson.${comboBoxInfo.comboBoxName} ){
				<%-- 数据库中的值 --%>
				field_${comboBoxInfo.comboBoxName}_${identifier}.setValue( beanValJson.${comboBoxInfo.comboBoxName} );
			}
			<c:if test='${comboBoxInfo.isCascade}'>
				resetLeftComboArray.push( field_${comboBoxInfo.comboBoxName}_${identifier} );	
			</c:if>
			
			
			<c:choose>
				<%-- 绑定下拉框级联事件 --%>
				<c:when test='${ comboBoxInfo.loadDataImplModel=="sql" && comboBoxInfo.isCascade && !comboBoxInfo.isLast }'>
					field_${comboBoxInfo.comboBoxName}_${identifier}.on('change', function() {
						if( comboCascadeActivateState=="activateCascade" ){
							<c:forEach items="${comboBoxInfo.cascadeList}" var="cascadeComboBoxInfo" varStatus="cascadeComboBoxStatus">
								<%-- 编辑时，给comboBox赋了初始值使用clearValue()方法不气作用，清除不了值。使用setValue("")清除。问题比较奇怪 --%>
								//field_${cascadeComboBoxInfo.comboBoxName}_${identifier}.clearValue();
								field_${cascadeComboBoxInfo.comboBoxName}_${identifier}.setValue( null );
								field_${cascadeComboBoxInfo.comboBoxName}_${identifier}.reset();
								
								comboBoxStore_${cascadeComboBoxInfo.comboBoxName}_${identifier}.removeAll();
							</c:forEach>
							
							Ext.apply(comboBoxStore_${comboBoxInfo.childComboBox}_${identifier}.proxy.extraParams, 
								{"comboBoxFlag":"${comboBoxInfo.comboBoxFlag}",
								"value":field_${comboBoxInfo.comboBoxName}_${identifier}.getValue()});
							comboBoxStore_${comboBoxInfo.childComboBox}_${identifier}.load();
						}
					}); 
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<%-- 请求后台查询数据库初始化下拉框数据(所有的下拉框store都要初始化) --%>
				<c:when test='${comboBoxInfo.loadDataImplModel=="sql"}'>
					comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.on("beforeload",function(){
								Ext.apply(comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.proxy.extraParams, 
									{"comboBoxFlag":"${comboBoxInfo.comboBoxFlag}",
										<c:choose>
											<%-- 加载第一个下拉框的数据(级联有多个下拉框，非级联只有一个下拉框) --%>
											<c:when test='${ !comboBoxInfo.isCascade || ( comboBoxInfo.isCascade && comboBoxInfo.isFirst )}'>			
												"value":"${comboBoxInfo.firstComboBoxParamValue}"
											</c:when>
											<c:otherwise>
												"value":field_${comboBoxInfo.parentComboBox}_${identifier}.getValue()
											</c:otherwise>
										</c:choose>
								});
					});
					<c:choose>
						<%-- 加载级联第一个下拉框或非级联下拉框(非级联下拉框只有一个) --%>
						<c:when test='${ !comboBoxInfo.isCascade || ( comboBoxInfo.isCascade && comboBoxInfo.isFirst )}'>			
							comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.load();
						</c:when>
						<c:otherwise>
							<%-- 加载级联下拉框数据(除第一个下拉框),父级联框有数据时才加载子级联框 --%>
						    if( field_${comboBoxInfo.parentComboBox}_${identifier}.getValue() ){
								comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.load();
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
		<c:forEach items="${tagUpdateList}" var="tagInfo" varStatus="tagStatus">
			<%-- 定义下拉框store开始 --%>
			<c:choose>
				<%-- 使用json串初始化下拉框数据 --%>
				<c:when test='${tagInfo.loadDataImplModel=="json"}'>
					var tagStore_${tagInfo.comboBoxName}_${identifier} = Ext.create('Ext.data.Store', {
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
					var tagStore_${tagInfo.comboBoxName}_${identifier} = new Ext.data.Store({
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
					tagStore_${tagInfo.comboBoxName}_${identifier}.on("beforeload",function(){
						Ext.apply(tagStore_${tagInfo.comboBoxName}_${identifier}.proxy.extraParams, {"comboBoxFlag":"${tagInfo.comboBoxFlag}","value":"${tagInfo.firstComboBoxParamValue}"});
					});
					tagStore_${tagInfo.comboBoxName}_${identifier}.load();
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			<%-- 定义下拉框store结束 --%>
		</c:forEach>
		<%-- tag控件下拉框初始化数据结束 --%>
		
		<%-- 生成所有编辑字段表单元素开始 --%>
		<c:forEach items="${updateFieldList}" var="fieldInfo" varStatus="validFieldStatus">
			
			<c:choose>
				<%-- 单选按钮开始 --%>
				<c:when test='${fieldInfo.xtype=="radiogroup"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: '${fieldInfo.xtype}',
					id: 'field_${fieldInfo.name}_${identifier}',
					fieldLabel: '${fieldInfo.fieldLabel}',
					labelStyle:'vertical-align: middle;',
					columns: 10,
					vertical: true,
					items: [
						<c:forEach items="${fieldInfo.sysEnFieldAttr.radioList}" var="radioFieldInfo" varStatus="radioFieldStatus">
							{id:'field_${fieldInfo.name}_${radioFieldInfo.eleId}_${identifier}', 
							 boxLabel: '${radioFieldInfo.boxLabel}', name: '${fieldInfo.name}',
							 inputValue: '${radioFieldInfo.inputValue}',
							 checked:${radioFieldInfo.checked},width:60
							 <c:if test='${radioFieldInfo.configs!=null}'>
									,${radioFieldInfo.configs}
							 </c:if>
							 <%-- 最后再设置值，防止radioFieldInfo.configs中的配置覆盖 --%>
							 ,checked:( '${radioFieldInfo.inputValue}'==beanValJson.${fieldInfo.name}+""?true:false )
							}
							<c:choose>
								<c:when test="${radioFieldStatus.last}"></c:when>
								<c:otherwise>,</c:otherwise>
							</c:choose>
						</c:forEach>
					]
					<c:if test='${!fieldInfo.isAllowBlank}'>
						,beforeLabelTextTpl: ['<span class="required">*</span>']
					</c:if>
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
				});
				</c:when>
				<%-- 单选按钮结束 --%>
				<%-- 多选按钮开始 --%>
				<c:when test='${fieldInfo.xtype=="checkboxgroup"}'>
					<c:forEach items="${fieldInfo.sysEnFieldAttr.checkboxList}" var="checkboxFieldInfo" varStatus="checkboxFieldStatus">
						var val_${fieldInfo.name}_${checkboxFieldInfo.eleId}_${identifier}=isCheckedCheckbox( beanValJson.${fieldInfo.name},'${checkboxFieldInfo.inputValue}' );
					</c:forEach>
				
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: '${fieldInfo.xtype}',
					id: 'field_${fieldInfo.name}_${identifier}',
					fieldLabel: '${fieldInfo.fieldLabel}',
					labelStyle:'vertical-align: middle;',
					columns: 10,
					vertical: true,
					items: [
						<c:forEach items="${fieldInfo.sysEnFieldAttr.checkboxList}" var="checkboxFieldInfo" varStatus="checkboxFieldStatus">
								{   id:'field_${fieldInfo.name}_${checkboxFieldInfo.eleId}_${identifier}',
									boxLabel: '${checkboxFieldInfo.boxLabel}', name: '${fieldInfo.name}',
									inputValue: '${checkboxFieldInfo.inputValue}',
									checked:${checkboxFieldInfo.checked},width:60
									<c:if test='${checkboxFieldInfo.configs!=null}'>
										,${checkboxFieldInfo.configs}
									</c:if>
									<%-- 最后再设置值，防止checkboxFieldInfo.configs中的配置覆盖 --%>
									,checked: val_${fieldInfo.name}_${checkboxFieldInfo.eleId}_${identifier}
								}
							<c:choose>
								<c:when test="${checkboxFieldStatus.last}"></c:when>
								<c:otherwise>,</c:otherwise>
							</c:choose>
						</c:forEach>
					]
					<c:if test='${!fieldInfo.isAllowBlank}'>
						,beforeLabelTextTpl: ['<span class="required">*</span>']
					</c:if>
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
				});
				</c:when>
				<%-- 多选按钮结束 --%>
				<%-- 下拉框开始 --%>
				<c:when test='${fieldInfo.xtype=="combobox"}'>
					<c:if test='${fieldInfo.sysEnFieldAttr.isCascade}'>	
						<%-- 生成级联下拉框 --%>
						var  field_${fieldInfo.name}_${identifier} =
						Ext.create({
							xtype: 'fieldcontainer',
							id: 'fieldcontainer_${fieldInfo.name}_${identifier}',
							fieldLabel: '${fieldInfo.fieldLabel}',
							labelStyle:'vertical-align: middle;',
							layout: 'hbox',
							items:[
								<c:forEach items="${fieldInfo.sysEnFieldAttr.comboBoxList}" var="comboBoxFieldInfo" varStatus="comboBoxFieldStatus">
									field_${comboBoxFieldInfo.comboBoxName}_${identifier}
									<c:choose>
										<c:when test="${comboBoxFieldStatus.last}"></c:when>
										<c:otherwise>,</c:otherwise>
									</c:choose>
								</c:forEach>
							]
							<c:if test='${!fieldInfo.isAllowBlank}'>
								,beforeLabelTextTpl: ['<span class="required">*</span>']
							</c:if>
							<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
								,${fieldInfo.sysEnFieldAttr.configs}
							</c:if>
						});
					</c:if>
					<c:if test='${!fieldInfo.sysEnFieldAttr.isCascade}'>
						<%-- 生成单个下拉框 --%>
						<%-- 非级联下拉框comboBoxList的size一定为1 --%>
						<c:forEach items="${fieldInfo.sysEnFieldAttr.comboBoxList}" var="comboBoxFieldInfo" varStatus="comboBoxFieldStatus">
							//field_${comboBoxFieldInfo.comboBoxName}_${identifier};
							field_${comboBoxFieldInfo.comboBoxName}_${identifier}.fieldLabel='${fieldInfo.fieldLabel}';
							<c:if test='${!fieldInfo.isAllowBlank}'>
								field_${comboBoxFieldInfo.comboBoxName}_${identifier}.beforeLabelTextTpl= ['<span class="required">*</span>'];
							</c:if>
						</c:forEach>
					</c:if>
				</c:when>
				<%-- 下拉框结束 --%>
				<%-- Tag控件开始 --%>
				<c:when test='${fieldInfo.xtype=="tagfield"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
							xtype: 'tagfield',
							id:'field_${fieldInfo.name}_${identifier}',
							name:'${fieldInfo.name}',
							fieldLabel: '${fieldInfo.fieldLabel}',
							store: tagStore_${fieldInfo.name}_${identifier},
							mode: "local",
							displayField: 'name',
							valueField: 'id',
							//filterPickList: true,
							triggerAction: 'all',
							//queryMode: 'local',
							loadingText: '正在加载...',
							emptyText: "请选择",
							typeAhead: true  //延时查询
							<c:if test='${!fieldInfo.isAllowBlank}'>
								,beforeLabelTextTpl: ['<span class="required">*</span>']
							</c:if>
							<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
								,${fieldInfo.sysEnFieldAttr.configs}
							</c:if>
							<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
								,validator: function(value){
									return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
								}
							</c:if>
				});
				if( beanValJson.${fieldInfo.name} ){
					<%-- 最后设置值，防止fieldInfo.sysEnFieldAttr.configs中的配置覆盖 --%>
					field_${fieldInfo.name}_${identifier}.setValue( beanValJson.${fieldInfo.name} );
				}
				</c:when>
				<%-- Tag控件结束 --%>
				<%-- 下拉树开始 --%>
				<c:when test='${fieldInfo.xtype=="comboboxtree"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create("Ext.ux.ComboBoxTree",{
					id: 'field_${fieldInfo.name}_${identifier}',
					name: '${fieldInfo.name}',
					fieldLabel: '${fieldInfo.fieldLabel}',
					editable: false,
					loadTreeDataUrl:'<%=basePath%>${fieldInfo.sysEnFieldAttr.url}'
					<c:if test='${!fieldInfo.isAllowBlank}'>
						,beforeLabelTextTpl: ['<span class="required">*</span>']
					</c:if>
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
						,validator: function(value){
							return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
						}
					</c:if>
					<%-- 最后再设置值，防止fieldInfo.sysEnFieldAttr.configs中的配置覆盖 --%>
					,selectedIds:beanValJson.${fieldInfo.name}
				});
				</c:when>
				<%-- 下拉树结束 --%>
				<%-- 附件开始 --%>
				<c:when test='${fieldInfo.xtype=="filefield"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: '${fieldInfo.xtype}',
					id:'field_${fieldInfo.name}_${identifier}',
					name: '${fieldInfo.name}',
					//value: beanValJson.${fieldInfo.name},
					fieldLabel: '${fieldInfo.fieldLabel}',
					buttonText:'选择文件',
					html:'<font color="red">红色</font>',
					afterBodyEl:'<span onclick="deleteFile(\'field_${fieldInfo.name}_${identifier}\',\''+beanValJson.id+'\',\'${fieldInfo.name}\',\'<%=basePath%>${controllerBaseUrl}deleteAtta.do\',\'${identifier}\')" style="cursor:pointer;"><font color="red">删除</font></span>',
					validator: function(value){
						<c:if test='${fieldInfo.sysEnFieldAttr!=null}'>
							return validateSuffix(value,'${fieldInfo.sysEnFieldAttr.allowFileType}');
						</c:if>
						
						<c:if test='${fieldInfo.sysEnFieldAttr==null}'>
							return "文件格式校验失败！缺少文件格式校验配置";
						</c:if>
					}
					//afterLabelTextTpl:['<font color=red>*</font>']
					<c:if test='${!fieldInfo.isAllowBlank}'>
						,beforeLabelTextTpl: ['<span class="required">*</span>']
					</c:if>
					
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
						,validator: function(value){
							return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
						}
					</c:if>
				});
				if( beanValJson.${fieldInfo.name} ){
					<%-- 初始化配置value不起作用，使用如下方法在文本框中显示文件名称 --%>
					field_${fieldInfo.name}_${identifier}.setRawValue( beanValJson.${fieldInfo.name} );
				}
				</c:when>
				<%-- 附件结束 --%>
				<%-- 富文本编辑器开始 --%>
				<c:when test='${fieldInfo.xtype=="htmleditor"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: '${fieldInfo.xtype}',
					id: 'field_${fieldInfo.name}_${identifier}',
					name: '${fieldInfo.name}',
					//value: beanValJson.${fieldInfo.name},
					fieldLabel: '${fieldInfo.fieldLabel}',
					fontFamilies :['宋体','隶书','黑体','Arial', 'Courier New', 'Tahoma', 'Times New Roman', 'Verdana']
					<c:if test='${!fieldInfo.isAllowBlank}'>
						,beforeLabelTextTpl: ['<span class="required">*</span>']
					</c:if>
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
				});
				if( beanValJson.${fieldInfo.name} ){
					<%-- 最后再设置值，防止fieldInfo.sysEnFieldAttr.configs中的配置覆盖 --%>
					field_${fieldInfo.name}_${identifier}.setValue( beanValJson.${fieldInfo.name} );
				}
				</c:when>
				<%-- 富文本编辑器结束 --%>
				<%-- 隐藏域或display开始 --%>
				<c:when test='${fieldInfo.xtype=="hiddenfield" || fieldInfo.xtype=="displayfield"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: '${fieldInfo.xtype}',
					id: 'field_${fieldInfo.name}_${identifier}',
					name: '${fieldInfo.name}',
					fieldLabel: '${fieldInfo.fieldLabel}'
					<c:if test='${!fieldInfo.isAllowBlank}'>
						,beforeLabelTextTpl: ['<span class="required">*</span>']
					</c:if>
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
				});
				if( beanValJson.${fieldInfo.name} ){
					<%-- 最后设置值，防止fieldInfo.sysEnFieldAttr.configs中的配置覆盖 --%>
					field_${fieldInfo.name}_${identifier}.setValue( beanValJson.${fieldInfo.name} );
				}
				</c:when>
				<%-- 隐藏域或display结束 --%>
				<%-- 继承了text控件的表单元素开始 --%>
				<c:otherwise>
					<c:choose>
						<c:when test='${fieldInfo.isEncryption}'>
							<%-- 加密字段处理开始，加密字段处理需要有隐藏域配合 --%>
							var  field_${fieldInfo.name}_${identifier} =
							Ext.create({
								xtype: '${fieldInfo.xtype}',
								id: 'field_${fieldInfo.name}_${identifier}_encryption',
								name: '${fieldInfo.name}_encryption',
								<%-- 不提交表单，只用于页面显示 --%>
								fieldLabel: '${fieldInfo.fieldLabel}'
								<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
									,${fieldInfo.sysEnFieldAttr.configs}
								</c:if>
								<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
									,validator: function(value){
										return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
									}
								</c:if>
								<%-- 加密字段在编辑页面中初始值设置为空、非必填 --%>
								<c:if test='${fieldInfo.isEncryption}'>
									,value:'',beforeLabelTextTpl: ['']
									,allowBlank:true,submitValue :false
								</c:if>
							});
							
							var  field_${fieldInfo.name}_${identifier}_encryption_hidden =
							Ext.create({
								xtype: 'hiddenfield',
								id: 'field_${fieldInfo.name}_${identifier}',
								name: '${fieldInfo.name}',
								<%-- 提交表单，存放加密后的值 --%>
								fieldLabel: '${fieldInfo.fieldLabel}'
								<%-- 加密字段在编辑页面中初始值设置为空、非必填 --%>
								<c:if test='${fieldInfo.isEncryption}'>
									//,value:new Object(null)
								</c:if>
							});
							<%-- 加密字段处理结束 --%>
						</c:when>
						<c:otherwise>
							var  field_${fieldInfo.name}_${identifier} =
							Ext.create({
								xtype: '${fieldInfo.xtype}',
								id: 'field_${fieldInfo.name}_${identifier}',
								name: '${fieldInfo.name}',
								fieldLabel: '${fieldInfo.fieldLabel}'
								<c:if test='${!fieldInfo.isAllowBlank}'>
									,beforeLabelTextTpl: ['<span class="required">*</span>']
								</c:if>
								<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
									,${fieldInfo.sysEnFieldAttr.configs}
								</c:if>
								<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
									,validator: function(value){
										return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
									}
								</c:if>
							});
							if( beanValJson.${fieldInfo.name} ){
								<%-- 最后设置值，防止fieldInfo.sysEnFieldAttr.configs中的配置覆盖 --%>
								field_${fieldInfo.name}_${identifier}.setValue( beanValJson.${fieldInfo.name} );
							}
						</c:otherwise>
					</c:choose>
				</c:otherwise>
				<%-- 继承了text控件的表单元素结束 --%>
			</c:choose>
		</c:forEach>
		<%-- 生成所有编辑字段表单元素结束 --%>		
		
		
	var editPanelFormElemeLeftMargin =8;	
	var  editPanel${identifier}=new Ext.FormPanel({
        id:'editPanel${identifier}',
        frame : true,
        bodyBorder:false,
        bodyStyle : 'padding:0px 0px 0px 0px',
        /*border:true,*/
        // The form will submit an AJAX request to this URL when submitted
        url: '<%=basePath%>${controllerBaseUrl}update.do',
        defaultType: 'textfield',
        fieldDefaults: {
            labelWidth: 100,
            labelAlign: "right",
            width:400,
            flex: 0,//每项item的宽度权重。值为0或未设置此属性时，item的width值才起作用。
            margin: editPanelFormElemeLeftMargin
        },
        items: [
			<c:forEach items="${updateFieldList}" var="fieldInfo" varStatus="fieldStatus">
						field_${fieldInfo.name}_${identifier}
						<c:choose>
							<c:when test="${fieldInfo.isEncryption}">
								,field_${fieldInfo.name}_${identifier}_encryption_hidden
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${fieldStatus.last}"></c:when>
							<c:otherwise>,</c:otherwise>
						</c:choose>
			</c:forEach>
        ],

        // Reset and Submit buttons
        buttons: [{
            text: '重置',
            handler: function() {
				<c:forEach items="${updateFieldList}" var="fieldInfo" varStatus="validFieldStatus">
					<c:if test='${fieldInfo.xtype=="comboboxtree"}'>
					<%-- 下拉树是自定义组件，重置表单需要特殊处理。 --%>
					field_${fieldInfo.name}_${identifier}.clearValue();
					</c:if>
				</c:forEach>
				var tempId = field_${primaryKeyField}_${identifier}.getValue();
				this.up('form').getForm().reset();
				field_${primaryKeyField}_${identifier}.setValue( tempId );
            }
        }, {
            text: '提交',
            formBind: true, //only enabled once the form is valid
            disabled: true,
            handler: function() {
                var form = this.up('form').getForm();
                if (form.isValid()) {
					<c:forEach items="${updateFieldList}" var="fieldInfo" varStatus="fieldStatus">
						<c:if test='${fieldInfo.isEncryption}'>
							var fieldInfo_${fieldInfo.name}_${identifier}_temp_val = field_${fieldInfo.name}_${identifier}.getValue();
							if( fieldInfo_${fieldInfo.name}_${identifier}_temp_val!=null  
								&& fieldInfo_${fieldInfo.name}_${identifier}_temp_val!=""
								&& $.trim( fieldInfo_${fieldInfo.name}_${identifier}_temp_val ).length>0	){
								
								fieldInfo_${fieldInfo.name}_${identifier}_temp_val = $.trim( fieldInfo_${fieldInfo.name}_${identifier}_temp_val );
								fieldInfo_${fieldInfo.name}_${identifier}_temp_val = $.md5 ( fieldInfo_${fieldInfo.name}_${identifier}_temp_val );
								field_${fieldInfo.name}_${identifier}_encryption_hidden.setValue( fieldInfo_${fieldInfo.name}_${identifier}_temp_val );
							}
						</c:if>
					</c:forEach>
										
                    form.submit({
						submitEmptyText :false,
						waitMsg :'正在保存，请耐心等待......',
                        success: function(form, action) {
                            //Ext.Msg.alert('提示信息', action.result.msg);
                            Ext.Msg.show({
                                title:"提示信息",
                                message:action.result.msg,
                                buttons:Ext.Msg.OK,
                                icon: Ext.Msg.INFO,
                                fn: function(btn) {
                                    //重置表单不起作用
                                    //var searForm = 	searPanel${identifier}.getForm( );
                                    //searForm.reset( false );

                                    //关闭编辑窗口
                                    editWindow_${identifier}.close();

                                    //重新加载列表页面数据
                                    //dataStore_${identifier}.load({params:{page:1,start:0,limit:${pageSize}}});
                                    dataStore_${identifier}.load();//刷新当前页面
                                }

                            });
                        },
                        failure: function(form, action) {
                            Ext.Msg.alert('错误提示', "抱歉，出错了！"+action.result.msg);
                        }
                    });
                }

            }
        }]
    });

    var editWindow_${identifier} = new Ext.Window({
        id:'editWindow_${identifier}',
        title: '编辑窗口',
        width:550,
		maxHeight:500,
		scrollable:true,
        //autoHeight:true,
        resizable:false,
        bodyStyle : 'padding:0px 0px 0px 0px',
        <%-- closeAction应该使用destroy --%>
		//closeAction : 'hide',   默认为destroy
        modal : true,
        plain:false,
        //listeners   : {'hide':{fn: closeWindowPlatform}},
		listeners   : {afterlayout:function(){
					resetComLeft(resetLeftComboArray,editPanelFormElemeLeftMargin);
				}
		},
        items: [
            editPanel${identifier}
        ]
		<c:if test='${controller.editWindowConfigs!=null}'>
				,${controller.editWindowConfigs}
		</c:if>
    });
	
	editWindow_${identifier}.show();
}
</c:if><%-- 判断是否有编辑权限结束标签 --%>
	<%-- 编辑窗口方法结束 --%>

<%-- 查看详情区域开始 --%>
	function openViewWindow_${identifier}( viewBeanValJson ){
		<%-- 存放需要调整样式left值的级联comboBox。解决不对齐问题 --%>
		var resetLeftComboArray=new Array();
		
		<%-- 下拉框初始化数据开始 --%>
		<c:forEach items="${comboBoxViewList}" var="comboBoxInfo" varStatus="comboBoxStatus">
			<%-- 定义下拉框store开始 --%>
			<c:choose>
				<%-- 使用json串初始化下拉框数据 --%>
				<c:when test='${comboBoxInfo.loadDataImplModel=="json"}'>
					var comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier} = Ext.create('Ext.data.Store', {
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
					var comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier} = new Ext.data.Store({
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
			var  field_${comboBoxInfo.comboBoxName}_${identifier} = Ext.create('Ext.form.ComboBox', {
							id:'field_${comboBoxInfo.comboBoxName}_${identifier}',
							name:'${comboBoxInfo.comboBoxName}',
							//value:viewBeanValJson.${comboBoxInfo.comboBoxName},		
							xtype: 'combobox',
							store: comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier},
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
							,value:'',readOnly:true,allowBlank:true,emptyText:''
			});
			<%-- 最后再设置值，防止comboBoxInfo.configs中的配置覆盖 --%>
			if( '${comboBoxInfo.value}'.length>0 ){
				<%-- 默认值 --%>
				field_${comboBoxInfo.comboBoxName}_${identifier}.setValue( '${comboBoxInfo.value}' );
			}
			if( viewBeanValJson.${comboBoxInfo.comboBoxName} ){
				<%-- 数据库中的值 --%>
				field_${comboBoxInfo.comboBoxName}_${identifier}.setValue( viewBeanValJson.${comboBoxInfo.comboBoxName} );
			}
			<c:if test='${comboBoxInfo.isCascade}'>
				resetLeftComboArray.push( field_${comboBoxInfo.comboBoxName}_${identifier} );	
			</c:if>
						
			<c:choose>
				<%-- 绑定下拉框级联事件 --%>
				<c:when test='${ comboBoxInfo.loadDataImplModel=="sql" && comboBoxInfo.isCascade && !comboBoxInfo.isLast }'>
					field_${comboBoxInfo.comboBoxName}_${identifier}.on('select', function() {
						<c:forEach items="${comboBoxInfo.cascadeList}" var="cascadeComboBoxInfo" varStatus="cascadeComboBoxStatus">
							field_${cascadeComboBoxInfo.comboBoxName}_${identifier}.setValue( null );
							field_${cascadeComboBoxInfo.comboBoxName}_${identifier}.reset();
							
							comboBoxStore_${cascadeComboBoxInfo.comboBoxName}_${identifier}.removeAll();
						</c:forEach>
						
						Ext.apply(comboBoxStore_${comboBoxInfo.childComboBox}_${identifier}.proxy.extraParams, 
							{"comboBoxFlag":"${comboBoxInfo.comboBoxFlag}",
							 "value":field_${comboBoxInfo.comboBoxName}_${identifier}.getValue()});
						comboBoxStore_${comboBoxInfo.childComboBox}_${identifier}.load();
					}); 
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<%-- 请求后台查询数据库初始化下拉框数据(所有的下拉框store都要初始化) --%>
				<c:when test='${comboBoxInfo.loadDataImplModel=="sql"}'>
					comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.on("beforeload",function(){
								Ext.apply(comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.proxy.extraParams, 
									{"comboBoxFlag":"${comboBoxInfo.comboBoxFlag}",
										<c:choose>
											<%-- 加载第一个下拉框的数据(级联有多个下拉框，非级联只有一个下拉框) --%>
											<c:when test='${ !comboBoxInfo.isCascade || ( comboBoxInfo.isCascade && comboBoxInfo.isFirst )}'>			
												"value":"${comboBoxInfo.firstComboBoxParamValue}"
											</c:when>
											<c:otherwise>
												"value":field_${comboBoxInfo.parentComboBox}_${identifier}.getValue()
											</c:otherwise>
										</c:choose>
								});
					});
					<c:choose>
						<%-- 加载级联第一个下拉框或非级联下拉框(非级联下拉框只有一个) --%>
						<c:when test='${ !comboBoxInfo.isCascade || ( comboBoxInfo.isCascade && comboBoxInfo.isFirst )}'>			
							comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.load();
						</c:when>
						<c:otherwise>
							<%-- 加载级联下拉框数据(除第一个下拉框),父级联框有数据时才加载子级联框 --%>
						    if( field_${comboBoxInfo.parentComboBox}_${identifier}.getValue() ){
								comboBoxStore_${comboBoxInfo.comboBoxName}_${identifier}.load();
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
		<c:forEach items="${tagViewList}" var="tagInfo" varStatus="tagStatus">
			<%-- 定义下拉框store开始 --%>
			<c:choose>
				<%-- 使用json串初始化下拉框数据 --%>
				<c:when test='${tagInfo.loadDataImplModel=="json"}'>
					var tagStore_${tagInfo.comboBoxName}_${identifier} = Ext.create('Ext.data.Store', {
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
					var tagStore_${tagInfo.comboBoxName}_${identifier} = new Ext.data.Store({
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
					tagStore_${tagInfo.comboBoxName}_${identifier}.on("beforeload",function(){
						Ext.apply(tagStore_${tagInfo.comboBoxName}_${identifier}.proxy.extraParams, {"comboBoxFlag":"${tagInfo.comboBoxFlag}","value":"${tagInfo.firstComboBoxParamValue}"});
					});
					tagStore_${tagInfo.comboBoxName}_${identifier}.load();
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			<%-- 定义下拉框store结束 --%>
		</c:forEach>
		<%-- tag控件下拉框初始化数据结束 --%>
		
		<%-- 生成所有查看详情字段表单元素开始 --%>
		<c:forEach items="${viewFieldList}" var="fieldInfo" varStatus="validFieldStatus">
			
			<c:choose>
				<%-- 单选按钮开始 --%>
				<c:when test='${fieldInfo.xtype=="radiogroup"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: '${fieldInfo.xtype}',
					id: 'field_${fieldInfo.name}_${identifier}',
					fieldLabel: '${fieldInfo.fieldLabel}',
					labelStyle:'vertical-align: middle;',
					columns: 10,
					vertical: true,
					items: [
						<c:forEach items="${fieldInfo.sysEnFieldAttr.radioList}" var="radioFieldInfo" varStatus="radioFieldStatus">
							{id:'field_${fieldInfo.name}_${radioFieldInfo.eleId}_${identifier}', 
							 boxLabel: '${radioFieldInfo.boxLabel}', name: '${fieldInfo.name}',
							 inputValue: '${radioFieldInfo.inputValue}',
							 width:60
							 <c:if test='${radioFieldInfo.configs!=null}'>
									,${radioFieldInfo.configs}
							 </c:if>
							 ,checked:false,readOnly:true
							 <%-- 最后再设置值，防止radioFieldInfo.configs中的配置覆盖 --%>
							 ,checked:( '${radioFieldInfo.inputValue}'==viewBeanValJson.${fieldInfo.name}+""?true:false )
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
				});
				</c:when>
				<%-- 单选按钮结束 --%>
				<%-- 多选按钮开始 --%>
				<c:when test='${fieldInfo.xtype=="checkboxgroup"}'>
					<c:forEach items="${fieldInfo.sysEnFieldAttr.checkboxList}" var="checkboxFieldInfo" varStatus="checkboxFieldStatus">
						var val_${fieldInfo.name}_${checkboxFieldInfo.eleId}_${identifier}=isCheckedCheckbox( viewBeanValJson.${fieldInfo.name},'${checkboxFieldInfo.inputValue}' );
					</c:forEach>
				
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: '${fieldInfo.xtype}',
					id: 'field_${fieldInfo.name}_${identifier}',
					fieldLabel: '${fieldInfo.fieldLabel}',
					labelStyle:'vertical-align: middle;',
					columns: 10,
					vertical: true,
					items: [
						<c:forEach items="${fieldInfo.sysEnFieldAttr.checkboxList}" var="checkboxFieldInfo" varStatus="checkboxFieldStatus">
								{   id:'field_${fieldInfo.name}_${checkboxFieldInfo.eleId}_${identifier}',
									boxLabel: '${checkboxFieldInfo.boxLabel}', name: '${fieldInfo.name}',
									inputValue: '${checkboxFieldInfo.inputValue}',
									width:60
									<c:if test='${checkboxFieldInfo.configs!=null}'>
										,${checkboxFieldInfo.configs}
									</c:if>
									,checked:false,readOnly:true
									<%-- 最后再设置值，防止checkboxFieldInfo.configs中的配置覆盖 --%>
									,checked: val_${fieldInfo.name}_${checkboxFieldInfo.eleId}_${identifier}
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
				});
				</c:when>
				<%-- 多选按钮结束 --%>
				<%-- 下拉框开始 --%>
				<c:when test='${fieldInfo.xtype=="combobox"}'>
					<c:if test='${fieldInfo.sysEnFieldAttr.isCascade}'>	
						<%-- 生成级联下拉框 --%>
						var  field_${fieldInfo.name}_${identifier} =
						Ext.create({
							xtype: 'fieldcontainer',
							id: 'fieldcontainer_${fieldInfo.name}_${identifier}',
							fieldLabel: '${fieldInfo.fieldLabel}',
							labelStyle:'vertical-align: middle;',
							layout: 'hbox',
							items:[
								<c:forEach items="${fieldInfo.sysEnFieldAttr.comboBoxList}" var="comboBoxFieldInfo" varStatus="comboBoxFieldStatus">
									field_${comboBoxFieldInfo.comboBoxName}_${identifier}
									<c:choose>
										<c:when test="${comboBoxFieldStatus.last}"></c:when>
										<c:otherwise>,</c:otherwise>
									</c:choose>
								</c:forEach>
							]
							<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
								,${fieldInfo.sysEnFieldAttr.configs}
							</c:if>
						});
					</c:if>
					<c:if test='${!fieldInfo.sysEnFieldAttr.isCascade}'>
						<%-- 生成单个下拉框 --%>
						<%-- 非级联下拉框comboBoxList的size一定为1 --%>
						<c:forEach items="${fieldInfo.sysEnFieldAttr.comboBoxList}" var="comboBoxFieldInfo" varStatus="comboBoxFieldStatus">
							field_${comboBoxFieldInfo.comboBoxName}_${identifier}.fieldLabel='${fieldInfo.fieldLabel}';
						</c:forEach>
					</c:if>
				</c:when>
				<%-- 下拉框结束 --%>
				<%-- Tag控件开始 --%>
				<c:when test='${fieldInfo.xtype=="tagfield"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
							xtype: 'tagfield',
							id:'field_${fieldInfo.name}_${identifier}',
							name:'${fieldInfo.name}',
							fieldLabel: '${fieldInfo.fieldLabel}',
							store: tagStore_${fieldInfo.name}_${identifier},
							mode: "local",
							displayField: 'name',
							valueField: 'id',
							triggerAction: 'all',
							//queryMode: 'local',
							loadingText: '正在加载...',
							emptyText: "请选择",
							typeAhead: true  //延时查询
							<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
								,${fieldInfo.sysEnFieldAttr.configs}
							</c:if>
							,value:'',readOnly:true,allowBlank:true,emptyText:''
				});
				if( viewBeanValJson.${fieldInfo.name} ){
					<%-- 最后设置值，防止fieldInfo.sysEnFieldAttr.configs中的配置覆盖 --%>
					field_${fieldInfo.name}_${identifier}.setValue( viewBeanValJson.${fieldInfo.name} );
				}
				</c:when>
				<%-- Tag控件结束 --%>
				<%-- 下拉树开始 --%>
				<c:when test='${fieldInfo.xtype=="comboboxtree"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create("Ext.ux.ComboBoxTree",{
					id: 'field_${fieldInfo.name}_${identifier}',
					name: '${fieldInfo.name}',
					fieldLabel: '${fieldInfo.fieldLabel}',
					editable: false,
					loadTreeDataUrl:'<%=basePath%>${fieldInfo.sysEnFieldAttr.url}'
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
					,selectedIds:'',allowBlank:true,emptyText:'',editable :false
					<%-- 最后再设置值，防止fieldInfo.sysEnFieldAttr.configs中的配置覆盖 --%>
					,selectedIds:viewBeanValJson.${fieldInfo.name}
				});
				</c:when>
				<%-- 下拉树结束 --%>
				<%-- 附件开始 --%>
				<c:when test='${fieldInfo.xtype=="filefield"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: 'displayfield',
					id: 'field_${fieldInfo.name}_${identifier}',
					name: '${fieldInfo.name}',
					fieldLabel: '${fieldInfo.fieldLabel}'
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
					,value:''
					//,html:'asdfafsadfa'
					//,contentEl:'<font color=red>sadds红</font>'
				});
				if( viewBeanValJson.${fieldInfo.name} ){
					<%-- 最后设置值，防止fieldInfo.sysEnFieldAttr.configs中的配置覆盖 --%>
					var tempUrl = "<%=basePath%>${controllerBaseUrl}download.do?beanId="+viewBeanValJson.id+"&fieldName=${fieldInfo.name}";
					var tempHtml = "<a href='javascript:void(0);' onclick='downloadFile(\""+tempUrl+"\")'>"+viewBeanValJson.${fieldInfo.name}+"</a>";
					field_${fieldInfo.name}_${identifier}.setValue( tempHtml );
				}
				</c:when>
				<%-- 附件结束 --%>
				<%-- 富文本编辑器开始 --%>
				<c:when test='${fieldInfo.xtype=="htmleditor"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: '${fieldInfo.xtype}',
					id: 'field_${fieldInfo.name}_${identifier}',
					name: '${fieldInfo.name}',
					fieldLabel: '${fieldInfo.fieldLabel}',
					fontFamilies :['宋体','隶书','黑体','Arial', 'Courier New', 'Tahoma', 'Times New Roman', 'Verdana']
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
					,value:''
				});
				field_${fieldInfo.name}_${identifier}.setReadOnly(true);
				if( viewBeanValJson.${fieldInfo.name} ){
					<%-- 最后再设置值，防止fieldInfo.sysEnFieldAttr.configs中的配置覆盖 --%>
					field_${fieldInfo.name}_${identifier}.setValue( viewBeanValJson.${fieldInfo.name} );
				}
				</c:when>
				<%-- 富文本编辑器结束 --%>
				<%-- display开始 --%>
				<c:when test='${fieldInfo.xtype=="hiddenfield" || fieldInfo.xtype=="displayfield"}'>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: '${fieldInfo.xtype}',
					id: 'field_${fieldInfo.name}_${identifier}',
					name: '${fieldInfo.name}',
					fieldLabel: '${fieldInfo.fieldLabel}'
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
					,value:''
				});
				if( viewBeanValJson.${fieldInfo.name} ){
					<%-- 最后设置值，防止fieldInfo.sysEnFieldAttr.configs中的配置覆盖 --%>
					field_${fieldInfo.name}_${identifier}.setValue( viewBeanValJson.${fieldInfo.name} );
				}
				</c:when>
				<%-- display结束 --%>
				<%-- 继承了text控件的表单元素开始 --%>
				<c:otherwise>
				var  field_${fieldInfo.name}_${identifier} =
				Ext.create({
					xtype: '${fieldInfo.xtype}',
					id: 'field_${fieldInfo.name}_${identifier}',
					name: '${fieldInfo.name}',
					fieldLabel: '${fieldInfo.fieldLabel}'
					<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
						,${fieldInfo.sysEnFieldAttr.configs}
					</c:if>
					,value:'',readOnly:true,allowBlank:true,emptyText:''
				});
				if( viewBeanValJson.${fieldInfo.name} ){
					<%-- 最后设置值，防止fieldInfo.sysEnFieldAttr.configs中的配置覆盖 --%>
					field_${fieldInfo.name}_${identifier}.setValue( viewBeanValJson.${fieldInfo.name} );
				}
				</c:otherwise>
				<%-- 继承了text控件的表单元素结束 --%>
			</c:choose>
		</c:forEach>
		<%-- 生成所有查看详情字段表单元素结束 --%>		
	var viewPanelFormElemeLeftMargin =8;	
	var  viewPanel${identifier}=new Ext.FormPanel({
        id:'viewPanel${identifier}',
        frame : true,
        bodyBorder:false,
        bodyStyle : 'padding:0px 0px 0px 0px',
        /*border:true,*/
        defaultType: 'textfield',
        fieldDefaults: {
            labelWidth: 100,
            labelAlign: "right",
            width:400,
            flex: 0,//每项item的宽度权重。值为0或未设置此属性时，item的width值才起作用。
            margin: viewPanelFormElemeLeftMargin
        },
        items: [
			<c:forEach items="${viewFieldList}" var="fieldInfo" varStatus="fieldStatus">
						field_${fieldInfo.name}_${identifier}
						<c:choose>
							<c:when test="${fieldStatus.last}"></c:when>
							<c:otherwise>,</c:otherwise>
						</c:choose>
			</c:forEach>
        ]        
    });

    var viewWindow_${identifier} = new Ext.Window({
        id:'viewWindow_${identifier}',
        title: '查看详情窗口',
        width:550,
		maxHeight:500,
		scrollable:true,
        //autoHeight:true,
        resizable:false,
        bodyStyle : 'padding:0px 0px 0px 0px',
        <%-- closeAction应该使用destroy --%>
		//closeAction : 'hide',   默认为destroy
        modal : true,
        plain:false,
		listeners: {afterlayout:function(){
					resetComLeft(resetLeftComboArray,viewPanelFormElemeLeftMargin);
			}
		},
        items: [
            viewPanel${identifier}
        ]
		<c:if test='${controller.viewWindowConfigs!=null}'>
				,${controller.viewWindowConfigs}
		</c:if>
    });
	
	viewWindow_${identifier}.show();
}


function viewDetail_${identifier}(id){
	<%--  查看详情开始 --%>
	Ext.Ajax.request({
		<%--  查看详情数据使用edit查询数据 --%>
		url:'<%=basePath%>${controllerBaseUrl}edit.do',
		params: {
			beanId: id
		},
		method:'POST',
		success: function(response){
			var responseStr = response.responseText;
			var responseJsonObj = Ext.JSON.decode( responseStr );
	
			if( responseJsonObj.success ){
				var viewBean = responseJsonObj.bean;
				openViewWindow_${identifier}( viewBean );
			}else{
				Ext.Msg.alert('提示', '抱歉，查看详情失败！'+responseJsonObj.msg );
			}
		},
		failure:function(response){
			Ext.Msg.alert('提示', '抱歉，查看详情失败，出错了！' );
		}
	});
	<%--  查看详情结束 --%>
}
<%-- 查看详情区域结束 --%>
	
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
	<%--相当于点击分页栏的首页按钮--%>
	platformPageBar${identifier}.moveFirst( );
	<%--直接重新加载stroe无法刷新分页栏及Ext.grid.RowNumberer()的值--%>
	//gridPanel${identifier}.getStore().load({params:{page:1,start:0,limit:${pageSize}}});
}
<%-- 搜索区域结束 --%>
	
<%-- 加载数据列表开始 --%>
var sm${identifier} = new Ext.selection.CheckboxModel({checkOnly: false});

Ext.define("Platform.model.Entity${identifier}", {
	extend: "Ext.data.Model",
	fields: [
   		 <c:forEach items="${showFieldList}" var="fieldInfo" varStatus="fieldStatus">
			<c:choose>
				<c:when test='${fieldInfo.xtype=="combobox"}'>
					<c:choose>
						<c:when test='${fieldInfo.sysEnFieldAttr.isCascade}'>
							<c:forEach items="${fieldInfo.sysEnFieldAttr.comboBoxList}" var="comboBoxFieldInfo" varStatus="comboBoxFieldStatus">
								{ name: '${comboBoxFieldInfo.comboBoxName}', type: '' }
								<c:if test='${!comboBoxFieldStatus.last}'>,
								</c:if>										
							</c:forEach>
						</c:when>
						<c:when test='${!fieldInfo.sysEnFieldAttr.isCascade}'>
							{ name: '${fieldInfo.name}', type: '${fieldInfo.type}' }
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					{ name: '${fieldInfo.name}', type: '${fieldInfo.type}' }
				</c:otherwise>
			</c:choose>
       	 	
       	 	<c:choose>
           	 	<c:when test="${fieldStatus.last}"></c:when>
           	 	<c:otherwise>,</c:otherwise>
        	</c:choose>
    	</c:forEach>
	]
});

var dataStore_${identifier} = new Ext.data.Store({
	model:"Platform.model.Entity${identifier}",
	pageSize: ${pageSize},
	proxy: {
    	type: 'ajax',
    	//actionMethods:{create: 'POST'},
    	url: '<%=basePath%>${loadDataUrl}',
		getMethod: function(){ return 'POST'; },//设置为post提交，避免乱码
	    //form:searForm, 此属性无效
		reader: {
			type: 'json',
			totalProperty: 'total',
			rootProperty: 'items'
		}
	},
	remoteSort: true
});

dataStore_${identifier}.on("beforeload",function(){
	<%--示例 Ext.apply(dataStore_${identifier}.proxy.extraParams, {"id":"11","name":"name","namespace":"命名空间","note":"备注","code":"codevalue"});--%>
	var searForm = 	searPanel${identifier}.getForm( );//获取表单
	<%--获取表单的值，获取到的是字段显示的值，非表单提交的值
	var formFieldsValues = searForm.getFieldValues();
	var formJsonStr = Ext.JSON.encode(formFieldsValues);
	var formJsonObj = Ext.JSON.decode( formJsonStr );
	//console.log("formJsonStr-->"+formJsonStr);--%>
	
	<%--获取表单提交的值--%>
	var formValues = searForm.getValues();
	
	<%--提交表单前，要对extraParams参数进行清空。
		extraParams存在缓存，会缓存上一次的查询条件。
		searForm.getValues()取值方式与extraParams缓存问题会导致提交的查询条件不正确。对radio、checkbox有影响。--%>
	dataStore_${identifier}.proxy.setExtraParams({});
	Ext.apply(dataStore_${identifier}.proxy.extraParams, formValues );
	//dataStore_${identifier}.proxy.setExtraParams( formValues );
});

var columnsHead${identifier} = [
       			new Ext.grid.RowNumberer(),
				<c:forEach items="${showFieldList}" var="fieldInfo" varStatus="fieldStatus">
					<c:choose>
						<c:when test='${fieldInfo.xtype=="combobox"}'>
							<c:choose>
								<c:when test='${fieldInfo.sysEnFieldAttr.isCascade}'>
									<c:forEach items="${fieldInfo.sysEnFieldAttr.comboBoxList}" var="comboBoxFieldInfo" varStatus="comboBoxFieldStatus">
										{ header:'${comboBoxFieldInfo.comboBoxFieldLabel}',
										  dataIndex:'${comboBoxFieldInfo.comboBoxName}',
										  sortable:true,
										  hidden:
										  	<c:choose>
										  		<c:when test='${fieldInfo.isShowList}'>false</c:when>
										  		<c:otherwise>true</c:otherwise>
										  	</c:choose>
										}
										<c:if test='${!comboBoxFieldStatus.last}'>,
										</c:if>										
									</c:forEach>
								</c:when>
								<c:when test='${!fieldInfo.sysEnFieldAttr.isCascade}'>
									{ header:'${fieldInfo.fieldLabel}',
									  dataIndex:'${fieldInfo.name}',
									  sortable:true,
									  hidden:
										<c:choose>
											<c:when test='${fieldInfo.isShowList}'>false</c:when>
											<c:otherwise>true</c:otherwise>
										</c:choose>
									}
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							{ header:'${fieldInfo.fieldLabel}',
							  dataIndex:'${fieldInfo.name}',
							  sortable:true,
							  hidden:
							  	<c:choose>
							  		<c:when test='${fieldInfo.isShowList}'>false</c:when>
							  		<c:otherwise>true</c:otherwise>
							  	</c:choose>
							}
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${fieldStatus.last}"></c:when>
						<c:otherwise>,</c:otherwise>
					</c:choose>
				</c:forEach>
           ];
<%-- 加载数据列表结束 --%>

<%-- 数据列表操作按钮开始 --%>
var operateMenu${identifier} = [
	<c:forEach items="${functionBarList}" var="functionInfo" varStatus="functionStatus">
		{
			id:'${functionInfo.identify}${identifier}',
			text:'${functionInfo.name}',
			xtype: 'button',
			iconCls: '${functionInfo.iconCls}',
			listeners: {
				click: function(){
					<c:choose>
					<c:when test='${functionInfo.menuTypeCode=="a1"}'>
						openAddWindow_${identifier}();
					</c:when>
					<c:when test='${functionInfo.menuTypeCode=="a2"}'>
						var selection = gridPanel${identifier}.getSelection( );
						
						if( selection.length==0 ){
							Ext.Msg.alert('提示', '请先勾选要操作的数据！');
							return;
						}

						<c:if test='${functionInfo.isSingle}'>
						if( selection.length!=1 ) {
							Ext.Msg.alert('提示', '只能勾选一条数据进行操作！');
							return;
						}
						</c:if>
						Ext.Msg.confirm("提示信息","确定要操作选中的数据吗？",function (btn) {
							if( btn=="yes" ){

								var ids = "";
								for( var i=0 ;i<selection.length;i++ ){
									var tempSelect = selection[i];
									var selecData = tempSelect.getData();
									<%-- alert("selecData-->"+selecData+"     selecData.id-->"+selecData.id+"     selecData.name-->"+selecData.name ); --%>
									ids += selecData.id+",";
								}

								Ext.Ajax.request({
									<c:choose>
									<c:when test='${functionInfo.identify=="baseDelete"}'>
										url:'<%=basePath%>${controllerBaseUrl}${functionInfo.url}',
									</c:when>
									<c:otherwise>
										url: '<%=basePath%>${functionInfo.url}',
									</c:otherwise>
									</c:choose>

									params: {
										ids: ids
									},
									method:'POST',
									<%--
									//form：     指定要提交的表单id或者表单数据对象
									//isUpload： 指定要提交的表单是否是文件上传表单，默认情况下会自动检查。
									//headers： 指定要请求的Header信息
									callback：指定Ajax请求的回调函数，该函数不管是调用成功还是失败，都会执行。传递给回调函数的参数有三个，
									第一个参数options表示执行request方法时的参数，
									第二个参数表示success请求是否成功，
									第三个参数表示response用来执行Ajax请求的XMLHttpRequest对象--%>
									success: function(response){
										var responseStr = response.responseText;
										var responseJsonObj = Ext.JSON.decode( responseStr );

										if( responseJsonObj.success ){
											var tipMsg = responseJsonObj.msg;
											if( tipMsg.length>0 ){
												Ext.Msg.alert('提示', tipMsg );
											}else{
												Ext.Msg.alert('提示', '操作成功！' );	
											}
											
											gridPanel${identifier}.getStore().load();
										}else{
											Ext.Msg.alert('提示', '抱歉，操作失败！'+responseJsonObj.msg );
										}
										<%-- Ext.Msg.alert('提示', '响应结果：text--->'+responseStr+'           responseJson-->'+responseJsonObj.success+'           msg-->'+responseJsonObj.msg ); --%>
										
									},
									failure:function(response){
										Ext.Msg.alert('提示', '抱歉，操作失败，出错了！' );
									}
								});

							}
						});
					</c:when>
					<c:when test='${functionInfo.menuTypeCode=="baseAjaxIndependent"}'>
						
						Ext.Msg.confirm("提示信息","确定执行此操作吗？",function (btn) {
							if( btn=="yes" ){
								Ext.Ajax.request({
									url: '<%=basePath%>${functionInfo.url}',
									method:'POST',
									success: function(response){
										var responseStr = response.responseText;
										var responseJsonObj = Ext.JSON.decode( responseStr );

										if( responseJsonObj.success ){
											var tipMsg = responseJsonObj.msg;
											if( tipMsg.length>0 ){
												Ext.Msg.alert('提示', tipMsg );
											}else{
												Ext.Msg.alert('提示', '操作成功！' );	
											}
											
											gridPanel${identifier}.getStore().load();
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
					</c:when>
					<c:when test='${functionInfo.menuTypeCode=="a3"}'>
						var selection = gridPanel${identifier}.getSelection( );
						
						if( selection.length==0 ){
							Ext.Msg.alert('提示', '请先勾选要操作的数据！');
							return;
						}

						<c:if test='${functionInfo.isSingle}'>
						if( selection.length!=1 ) {
							Ext.Msg.alert('提示', '只能勾选一条数据进行操作！');
							return;
						}
						</c:if>
						Ext.Msg.confirm("提示信息","确定要操作选中的数据吗？",function (btn) {
							if( btn=="yes" ){
								var ids = "";
								for( var i=0 ;i<selection.length;i++ ){
									var tempSelect = selection[i];
									var selecData = tempSelect.getData();
									<%-- alert("selecData-->"+selecData+"     selecData.id-->"+selecData.id+"     selecData.name-->"+selecData.name ); --%>
									ids += selecData.id+",";
								}

								<c:forEach items="${functionInfo.updateFieldList}" var="fieldInfo" varStatus="fieldStatus">
									var  ajax_update_field_${fieldInfo.name}_${identifier} =
									Ext.create({
										xtype: '${fieldInfo.xtype}',
										id: 'ajax_update_field_${fieldInfo.name}_${identifier}',
										name: '${fieldInfo.name}',
										fieldLabel: '${fieldInfo.fieldLabel}'
										<c:if test='${!fieldInfo.isAllowBlank}'>
											,beforeLabelTextTpl: ['<span class="required">*</span>']
										</c:if>
										<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.configs!=null}'>
											,${fieldInfo.sysEnFieldAttr.configs}
										</c:if>
										<c:if test='${fieldInfo.sysEnFieldAttr!=null && fieldInfo.sysEnFieldAttr.validatorFunName!=null}'>
											,validator: function(value){
												return ${fieldInfo.sysEnFieldAttr.validatorFunName}(value ${fieldInfo.sysEnFieldAttr.validatorFunField});
											}
										</c:if>
										,value:''
									});								
								</c:forEach>

								var  ajax_update_panel_${identifier}=new Ext.FormPanel({
									id:'ajax_update_panel_${identifier}',
									frame : true,
									bodyBorder:false,
									bodyStyle : 'padding:0px 0px 0px 0px',
									// The form will submit an AJAX request to this URL when submitted
									url: '<%=basePath%>${functionInfo.url}',
									defaultType: 'textfield',
									fieldDefaults: {
										labelWidth: 100,
										labelAlign: "right",
										width:400,
										flex: 0,//每项item的宽度权重。值为0或未设置此属性时，item的width值才起作用。
										margin: 8
									},
									items: [
											  <c:forEach items="${functionInfo.updateFieldList}" var="fieldInfo" varStatus="fieldStatus">
											  	ajax_update_field_${fieldInfo.name}_${identifier},
											  </c:forEach>
											  {
											  	xtype: 'hiddenfield',
												name: 'ids',
												value: ids,
											  },
											  {
											  	xtype: 'hiddenfield',
												name: 'ajaxUpdateFields',
												value: '${functionInfo.ajaxUpdateFields}',
											  }
											],
									// Reset and Submit buttons
									buttons: [{
										text: '重置',
										handler: function() {
											this.up('form').getForm().reset();
										}
									}, {
										text: '提交',
										formBind: true, //only enabled once the form is valid
										disabled: true,
										handler: function() {
											var form = this.up('form').getForm();
											if (form.isValid()) {
												form.submit({
													submitEmptyText :false,
													waitMsg :'正在保存，请耐心等待......',
													success: function(form, action) {
														//Ext.Msg.alert('提示信息', action.result.msg);
														Ext.Msg.show({
															title:"提示信息",
															message:action.result.msg,
															buttons:Ext.Msg.OK,
															icon: Ext.Msg.INFO,
															fn: function(btn) {
																//关闭修改窗口
																ajax_update_window_${identifier}.close();
							
																//重新加载列表页面数据
																//dataStore_${identifier}.load({params:{page:1,start:0,limit:${pageSize}}});
																dataStore_${identifier}.load();//刷新当前页面
															}
							
														});
													},
													failure: function(form, action) {
														Ext.Msg.alert('错误提示', "抱歉，出错了！"+action.result.msg);
													}
												});
											}
							
										}
									}]
								});
							
								var ajax_update_window_${identifier} = new Ext.Window({
									id:'ajax_update_window_${identifier}',
									title: '修改值窗口',
									width:550,
									maxHeight:500,
									scrollable:true,
									autoHeight:true,
									resizable:false,
									bodyStyle : 'padding:0px 0px 0px 0px',
									<%-- closeAction应该使用destroy --%>
									//closeAction : 'hide',   默认为destroy
									modal : true,
									plain:false,
									items: [
										ajax_update_panel_${identifier}
									]
									<c:if test='${functionInfo.ajaxUpdateWindowConfigs!=null}'>
										,${functionInfo.ajaxUpdateWindowConfigs}
									</c:if>
								});
								
								ajax_update_window_${identifier}.show();
							}
						});
					</c:when>
					<c:when test='${functionInfo.menuTypeCode=="a4"}'>
						var selection = gridPanel${identifier}.getSelection();
						var ids = "";
						for( var i=0 ;i<selection.length;i++ ){
							var tempSelect = selection[i];
							var selecData = tempSelect.getData();
							ids += selecData.id+",";
						}
						${functionInfo.functionName}(ids);
					</c:when>
					<c:when test='${functionInfo.menuTypeCode=="b1"}'>
						Ext.Msg.alert('${functionInfo.name}', '${functionInfo.instructions}' );
					</c:when>
					<c:otherwise>
						Ext.Msg.alert('提示', '没有定义对应的响应事件！${functionInfo.menuTypeCode}');
					</c:otherwise>
					</c:choose>
				}
			}
		}

		<c:choose>
			<c:when test="${functionStatus.last}"></c:when>
			<c:otherwise>,</c:otherwise>
		</c:choose>
	</c:forEach>
];

var pagingBarMenu${identifier} = [{
	text:'每页显示${pageSize}条',
},{
	text:'导出',
}];

var platformPageBar${identifier} = new Ext.PagingToolbar({
    store: dataStore_${identifier},
    displayInfo: true,
    padding: "0 20 0 20",
    items: pagingBarMenu${identifier}
});
<%-- 数据列表操作按钮结束 --%>

<%-- 数据列表面板开始 --%>
var gridPanel${identifier} = new Ext.grid.GridPanel({
	title:'查询结果',
	region: 'center',
	store: dataStore_${identifier},
    columns: columnsHead${identifier},
    selModel: {
        injectCheckbox: 1,
        mode: "SIMPLE",     //"SINGLE"/"SIMPLE"/"MULTI"
        checkOnly: true     //只能通过checkbox选择
    },
    selType: "checkboxmodel",
    autoHeight: true,
    resizable:true,
    layout:'fit', 
    viewConfig:{
    	forceFit:true
    },
    tbar: operateMenu${identifier} ,
    bbar: platformPageBar${identifier},
	listeners:{
		itemdblclick:function (dataview, record, item, index, e, eOpts) {
			<%--  编辑事件开始 --%>
			<c:if test='${baseEditPriv}'><%-- 有编辑权限则生成编辑的相关方法 --%>
			var tempModel = record.getData();
            <%--查看model结构信息
				var tempModelJSONStr = Ext.JSON.encode( tempModel );
				Ext.Msg.alert('提示', '双击了一行！'+"    tempModel.name-->"+tempModel.name+"    tempModel.id-->"+tempModel.id );
			--%>
			
            Ext.Ajax.request({
                url:'<%=basePath%>${controllerBaseUrl}edit.do',
                params: {
                    beanId: tempModel.id
                },
                method:'POST',
				<%--  
                 callback：指定Ajax请求的回调函数，该函数不管是调用成功还是失败，都会执行。传递给回调函数的参数有三个，
                 第一个参数options表示执行request方法时的参数，
                 第二个参数表示success请求是否成功，
                 第三个参数表示response用来执行Ajax请求的XMLHttpRequest对象--%>
                success: function(response){
                    var responseStr = response.responseText;
                    var responseJsonObj = Ext.JSON.decode( responseStr );

                    if( responseJsonObj.success ){
                        var updateBean = responseJsonObj.bean;
                        openEditWindow_${identifier}( updateBean );
                    }else{
                        Ext.Msg.alert('提示', '抱歉，操作失败！'+responseJsonObj.msg );
                    }
                },
                failure:function(response){
                    Ext.Msg.alert('提示', '抱歉，操作失败，出错了！' );
                }
            });
			</c:if>
            <%--  编辑事件结束 --%>
		}
	}

});
<%-- 数据列表面板结束 --%>

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
    items:[searPanel${identifier},gridPanel${identifier}]
});

dataStore_${identifier}.load({params:{page:1,start:0,limit:${pageSize}}});

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
