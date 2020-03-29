package com.tgw.basic.framework.controller;

import com.github.pagehelper.Page;
import com.tgw.basic.common.beans.PlatformCustomDateEditor;
import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.PlatformInfo;
import com.tgw.basic.common.utils.PlatformUtils;
import com.tgw.basic.common.utils.collections.PlatformCollectionsUtils;
import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.common.utils.file.PlatformFileUtils;
import com.tgw.basic.common.utils.string.PlatformStringUtils;
import com.tgw.basic.common.utils.tree.PlatformTreeUtils;
import com.tgw.basic.common.utils.tree.model.SysEnTreeNode;
import com.tgw.basic.core.model.AbstractBaseBean;
import com.tgw.basic.framework.model.controller.SysEnController;
import com.tgw.basic.framework.model.controller.SysEnControllerField;
import com.tgw.basic.framework.model.controller.SysEnControllerFunction;
import com.tgw.basic.framework.model.form.field.SysEnFieldComboBox;
import com.tgw.basic.framework.model.form.field.SysEnFieldComboBoxGroup;
import com.tgw.basic.framework.model.form.field.SysEnFieldDate;
import com.tgw.basic.framework.model.form.field.SysEnFieldDisplay;
import com.tgw.basic.framework.model.form.field.SysEnFieldListExtend;
import com.tgw.basic.framework.model.form.field.SysEnFieldTag;
import com.tgw.basic.framework.service.BaseService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * BaseController中增、删、查、改各操作的主方法不声明throws  PlatformException。
 * 各操作的辅助方法声明throws  PlatformException，以明确指出调用此些方法的代码需要对业务异常信息进行处理。
 * @param <T>
 */
@Controller
public class BaseController<T extends AbstractBaseBean> implements Serializable {
	private static final Log LOG = LogFactory.getLog(BaseController.class);

	private T bean;
	private Class entityClass;
	public static final String VIEW_PLATFORM = "platform/";// 平台框架view视图基础路径
	public static final String VIEW_BUSINESS = "business/";// 业务view视图基础路径
	public static final String VIEW_JSON = VIEW_PLATFORM+"common/json";//输出json字符串视图路径
	public static final String PATH_RESOURCE_PLATFORM = "resource/platform/";// 平台框架资源基础路径
	public static final String PATH_RESOURCE_BUSINESS = "resource/business/";// 业务资源基础路径

	@Resource
	private BaseService baseService;

	/**
	 * 为满足框架结构的需要，一定要在具体的业务Controller调用此方法。
	 * 作用：具体的业务Controller必须覆写，将具体业务的service赋值给baseService
	 *
	 * 主要原因是BaseModelMapper在BaseServiceImpl中无法注入，导致baseService调用BaseModelMapper时报空指针异常。
	 * BaseController中的方法调用BaseService的方法时会报错（在BaseService的方法使用了BaseModelMapper的情况下）。
	 * @param baseService
	 * @exception PlatformException
     */
	public void initService(BaseService baseService){
		this.setBaseService(baseService);
		this.getBaseService().initMapper();
	}

	/**
	 * 初始化controller基本信息
	 * @param controller
	 * @throws PlatformException
     */
	public void initControllerBaseInfo(SysEnController controller) throws PlatformException{

	}

	/**
	 * 初始化controller相关信息方法。
	 * @param request
	 * @param response
	 * @param modelAndView
	 * @param controller
	 * @param bean
	 * @throws PlatformException
     */
	public void initSearch(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, T bean) throws PlatformException{
		LOG.debug("----------------- BaseController  initSearch -----------------");
	}

	/**
	 * 初始化列表页面上的字段信息。配置列表页面需要展示哪些字段。
	 * @param controller
	 * @throws PlatformException
     */
	public void initField( SysEnController controller ) throws PlatformException{

	}

	/**
	 * 可通过覆写此方法为功能列表按钮栏配置功能按钮。
	 * @param controller
	 * @throws PlatformException
     */
	public void initFunction( SysEnController controller ) throws PlatformException{

	}

	/**
	 * 执行查询方法最先调用的方法
	 * @throws PlatformException
	 */
	public void beforeSearch(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{
		LOG.debug("----------------- BaseController  beforeSearch -----------------");
	}



	/**
	 * 进入查询列表页面
	 * @param request
	 * @param response
	 * @param bean
     * @return
     */
	@RequestMapping("/search.do")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response, T bean){
		ModelAndView modelAndView = new ModelAndView();

		try{
			this.beforeSearch(request,response,bean);
			LOG.debug("----------------- BaseController  search -----------------");
			SysEnController controller = new SysEnController();

			/**
			 * 以下初始化顺序不能变。
			 *
			 * initField必须在initFunction之前，添加列表中的menubar时，menuFunction依赖field。
			 */
			this.initControllerBaseInfo(controller);
			this.initSearch(request,response,modelAndView,controller,bean);
			this.initField( controller );
			this.initFunction( controller );

			this.dealSearchCore(request, response, modelAndView, controller,  bean);

			this.afterSearch(request,response,bean);
			modelAndView.setViewName(VIEW_PLATFORM+"common/showDataSVP");

		}catch( PlatformException e ){
			modelAndView.setViewName(VIEW_PLATFORM+"common/showDataError");
			LOG.error(e);
		}catch( Exception e ){
			modelAndView.setViewName(VIEW_PLATFORM+"common/showDataError");
			LOG.error(e);
		}

		return  modelAndView;
	}

	/**
	 * 执行查询方法最后调用的方法。
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
	 */
	public void afterSearch(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{
		LOG.debug("----------------- BaseController  afterSearch -----------------");
	}


	public void beforeSearchModel(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{
		LOG.debug("----------------- BaseController  beforeSearch -----------------");
	}

	public void initControllerBaseInfoSearchModel(SysEnController controller) throws PlatformException{

	}

	public void initSearchModel(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, T bean) throws PlatformException{

	}

	public void initFieldSearchModel( SysEnController controller ) throws PlatformException{

	}


	public void initFunctionSearchModel( SysEnController controller ) throws PlatformException{

	}

	@RequestMapping("/searchModel.do")
	public ModelAndView searchModel(HttpServletRequest request, HttpServletResponse response, T bean){
		ModelAndView modelAndView = new ModelAndView();

		try{
			this.beforeSearchModel(request,response,bean);
			SysEnController controller = new SysEnController();

			/**
			 * 以下初始化顺序不能变。
			 *
			 * initField必须在initFunction之前，添加列表中的menubar时，menuFunction依赖field。
			 */
			this.initControllerBaseInfoSearchModel(controller);
			this.initSearchModel(request,response,modelAndView,controller,bean);
			this.initFieldSearchModel( controller );
			this.initFunctionSearchModel( controller );

			//设置一个非空字符串，只是为了dealSearchCore判空时不抛异常，没有其它作用。
			controller.setLoadDataUrl( "loadDataUrl" );

			this.dealSearchCore(request, response, modelAndView, controller,  bean);

			this.afterSearchModel(request,response,bean);
			modelAndView.setViewName(VIEW_PLATFORM+"common/showSearchModel");

		}catch( PlatformException e ){
			modelAndView.setViewName(VIEW_PLATFORM+"common/showDataError");
			LOG.error(e);
		}catch( Exception e ){
			modelAndView.setViewName(VIEW_PLATFORM+"common/showDataError");
			LOG.error(e);
		}

		return  modelAndView;
	}


	public void afterSearchModel(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{
	}


	public void dealSearchCore(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, T bean){
		try{
			//当前controllerr的所有功能权限
			List<Map<String,Object>> funcMenuMapList = this.getBaseService().queryFuncByMenuIdentify( controller.getIdentifier() );
			List<Object> funcMenuList = PlatformCollectionsUtils.extractMapValToList( funcMenuMapList,"func_code" );

			//获取用户session信息
			Object userSessionInfo = request.getSession().getAttribute( "userSessionInfo" );
			if( null==userSessionInfo ){
				throw new PlatformException("没有登录或登录已超时，请先登录！");
			}
			//获取用户详情信息
			Method metGetSysEnUser=userSessionInfo.getClass().getDeclaredMethod( "getSysEnUser" );
			Object sysEnUser = metGetSysEnUser.invoke(userSessionInfo);
			if( null == sysEnUser ){
				throw new PlatformException("获取用户登录信息失败！");
			}
			//获取用户id
			Method metGetUserId=sysEnUser.getClass().getDeclaredMethod( "getId" );
			Integer userId = (Integer)metGetUserId.invoke(sysEnUser);
			//当前用户在当前controller中所拥有的功能权限
			List<Map<String,Object>> funcMenuUserMapList = this.getBaseService().queryFuncByMenuIdentifyUserId(controller.getIdentifier(),userId);
			List<Object> funcMenuUserList = PlatformCollectionsUtils.extractMapValToList( funcMenuUserMapList,"func_code" );


			//初始化页面上的字段信息开始
			if( null!=controller.getSysEnControllerFieldList() && controller.getSysEnControllerFieldList().size()>0 ){

				/**
				 * 初始化页面上的字段信息
				 */
				//所有有效字段
				List<SysEnControllerField> validFieldList = new ArrayList<SysEnControllerField>();
				//可被添加的字段
				List<SysEnControllerField> addFieldList = new ArrayList<SysEnControllerField>();
				//可被更新的字段
				List<SysEnControllerField> updateFieldList = new ArrayList<SysEnControllerField>();
				//详情页面中展示的字段
				List<SysEnControllerField> viewFieldList = new ArrayList<SysEnControllerField>();
				//可被搜索的字段
				List<SysEnControllerField> searFieldList = new ArrayList<SysEnControllerField>();
				//可在列表显示的字段
				List<SysEnControllerField> showFieldList = new ArrayList<SysEnControllerField>();
				//列表中的可操作字段
				List<SysEnControllerField> operateFieldList = new ArrayList<SysEnControllerField>();

				//页面上的所有下拉框
				List<SysEnFieldComboBox> comboBoxList = new ArrayList<SysEnFieldComboBox>();
				List<SysEnFieldComboBox> comboBoxAddList = new ArrayList<SysEnFieldComboBox>();
				List<SysEnFieldComboBox> comboBoxUpdateList = new ArrayList<SysEnFieldComboBox>();
				List<SysEnFieldComboBox> comboBoxViewList = new ArrayList<SysEnFieldComboBox>();
				List<SysEnFieldComboBox> comboBoxSearchList = new ArrayList<SysEnFieldComboBox>();
				//页面上的所有tag控件，tag继承自comboBox
				List<SysEnFieldTag> tagList = new ArrayList<SysEnFieldTag>();
				List<SysEnFieldTag> tagAddList = new ArrayList<SysEnFieldTag>();
				List<SysEnFieldTag> tagUpdateList = new ArrayList<SysEnFieldTag>();
				List<SysEnFieldTag> tagViewList = new ArrayList<SysEnFieldTag>();
				List<SysEnFieldTag> tagSearchList = new ArrayList<SysEnFieldTag>();

				List<String> allowedXtypeEncryption = new ArrayList<String>();//允许加密的表单字段类型
				allowedXtypeEncryption.add( PlatformSysConstant.FORM_XTYPE_TEXT );
				allowedXtypeEncryption.add( PlatformSysConstant.FORM_XTYPE_TEXTAREA );

				for( SysEnControllerField conField : controller.getSysEnControllerFieldList() ){
               		 /*if( "id".equals( conField.getName() ) ){//编辑页面一定需要id，不管id字段配置成什么状态，都要加上id
                    	updateFieldList.add( conField );
               		 }*/

					//如果是列表中操作类型的字段，先判断是否有权限。
					if( PlatformSysConstant.FIELD_TYPE_OPERATE.equals( conField.getFieldType() ) ){
						if( funcMenuList.contains( conField.getName() ) ){//功能权限管理中配置了此功能点权限
							if( funcMenuUserList.contains( conField.getName() ) ){//当前用户有此功能点权限，不拦截

							}else{//当前用户没有此功能点权限，进行拦截
								continue;
							}
						}else{//功能权限管理中没有配置此功能点权限。没有配置的功能点，默认所有用户都有此功能权限，不拦截。

						}
					}

					if( conField.isEncryption() ){//加密字段
						if( !allowedXtypeEncryption.contains( conField.getXtype() ) ){
							throw new PlatformException("只支持文本、文本域字段进行加密！");
						}
					}

					if( !conField.isValid() ){
						continue;
					}
					validFieldList.add( conField );

					if( conField.isPrimaryKey() ){
						modelAndView.addObject("primaryKeyField",conField.getName() );
					}

					if( PlatformSysConstant.FIELD_TYPE_OPERATE.equals( conField.getFieldType() ) ){
						operateFieldList.add( conField );
					}

					if( conField.isAllowAdd() ){
						addFieldList.add( conField );
					}

					if( conField.isAllowUpdate()  || "id".equals( conField.getName() )  ){
						updateFieldList.add( conField );
					}


					if( conField.isAllowSearch() ){
						searFieldList.add( conField );
					}

					if( conField.isShowList() ){
						showFieldList.add( conField );
					}

					if( conField.isShowOnView() ){
						viewFieldList.add( conField );
					}

					if( PlatformSysConstant.FORM_XTYPE_COMBOBOX.equals( conField.getXtype() ) ){
						SysEnFieldComboBoxGroup comboBoxGroup = (SysEnFieldComboBoxGroup)conField.getSysEnFieldAttr();
						comboBoxGroup.getComboBoxList();
						for( SysEnFieldComboBox comboBox:comboBoxGroup.getComboBoxList() ){
							comboBoxList.add( comboBox );
							if( conField.isAllowAdd() ){
								comboBoxAddList.add( comboBox );
							}
							if( conField.isAllowUpdate() ){
								comboBoxUpdateList.add( comboBox );
							}
							if( conField.isAllowSearch() ){
								comboBoxSearchList.add( comboBox );
							}
							if( conField.isShowOnView() ){
								comboBoxViewList.add(comboBox);
							}

						}
					}

					if( PlatformSysConstant.FORM_XTYPE_TAG.equals( conField.getXtype() ) ){
						SysEnFieldTag sysEnFieldTag = (SysEnFieldTag)conField.getSysEnFieldAttr();
						tagList.add( sysEnFieldTag );
						if( conField.isAllowAdd() ){
							tagAddList.add(sysEnFieldTag);
						}
						if( conField.isAllowUpdate() ){
							tagUpdateList.add(sysEnFieldTag);
						}
						if( conField.isAllowSearch() ){
							tagSearchList.add( sysEnFieldTag );
						}
						if( conField.isShowOnView() ){
							tagViewList.add(sysEnFieldTag);
						}
					}
				}

				modelAndView.addObject("fieldList",controller.getSysEnControllerFieldList() );
				modelAndView.addObject("validFieldList",validFieldList);
				modelAndView.addObject("addFieldList",addFieldList );
				modelAndView.addObject("updateFieldList",updateFieldList );
				modelAndView.addObject("viewFieldList",viewFieldList );
				modelAndView.addObject("searFieldList",searFieldList );
				modelAndView.addObject("showFieldList",showFieldList );
				modelAndView.addObject("operateFieldList",operateFieldList );

				modelAndView.addObject("comboBoxList",comboBoxList);
				modelAndView.addObject("comboBoxAddList",comboBoxAddList);
				modelAndView.addObject("comboBoxUpdateList",comboBoxUpdateList);
				modelAndView.addObject("comboBoxViewList",comboBoxViewList);
				modelAndView.addObject("comboBoxSearchList",comboBoxSearchList);
				modelAndView.addObject("tagList",tagList);
				modelAndView.addObject("tagAddList",tagAddList);
				modelAndView.addObject("tagUpdateList",tagUpdateList);
				modelAndView.addObject("tagViewList",tagViewList);
				modelAndView.addObject("tagSearchList",tagSearchList);

				int searchConditionColNum = 0;
				if( controller.getSearchConditionColNum() <= 0 ){
					searchConditionColNum = PlatformSysConstant.LAYOUT_NUM_HORI_SEARCH_CONDITION;
				}else{
					searchConditionColNum = controller.getSearchConditionColNum();
				}

				int searchConditionFieldNum = searFieldList.size();
				int searchConditionRowNum = 0;//列表页面上搜索条件区域布局的行数
				/**
				 * searchConditionFieldNum+1是因为搜索区域的重置与搜索按钮也需要占用一个位置
				 */
				if( (searchConditionFieldNum+1) % searchConditionColNum == 0 ){
					searchConditionRowNum = (searchConditionFieldNum+1) / searchConditionColNum ;
				}else{
					searchConditionRowNum = (searchConditionFieldNum+1) / searchConditionColNum + 1;
				}

				modelAndView.addObject("searchConditionRowNum", searchConditionRowNum );
				modelAndView.addObject("searchConditionColNum", searchConditionColNum );


			}else{

			}
			//初始化页面上的字段信息结束


			//初始化页面上的菜单按钮信息开始
			List<SysEnControllerFunction> sysEnControllerFunctionList = new ArrayList<SysEnControllerFunction>();

			SysEnControllerFunction addFunction = new SysEnControllerFunction("baseAdd","添加","",PlatformSysConstant.MENU_TYPE_ADD,true,"Add",-100);
			if( funcMenuList.contains( "baseAdd" ) ){//功能权限管理中配置了此添加功能点权限
				if( funcMenuUserList.contains( "baseAdd" ) ){//当前用户有此功能点权限
					sysEnControllerFunctionList.add( addFunction );
					modelAndView.addObject("baseAddPriv", true );//baseAddPriv变量在页面上判断使用
				}else{
					modelAndView.addObject("baseAddPriv", false );
				}
			}else{//功能权限管理中没有配置添加功能点权限。没有配置的功能点，默认所有用户都有此功能权限
				sysEnControllerFunctionList.add( addFunction );
				modelAndView.addObject("baseAddPriv", true );
			}

			SysEnControllerFunction delFunction = new SysEnControllerFunction("baseDelete","删除","delete.do",PlatformSysConstant.MENU_TYPE_BASE_AJAX,false,"Delete",-99);
			if( funcMenuList.contains( "baseDelete" ) ){//功能权限管理中配置了此删除功能点权限
				if( funcMenuUserList.contains( "baseDelete" ) ){//当前用户有此功能点权限
					sysEnControllerFunctionList.add( delFunction );
					modelAndView.addObject("baseDeletePriv", true );//baseDeletePriv变量在页面上判断使用
				}else{
					modelAndView.addObject("baseDeletePriv", false );
				}
			}else{//功能权限管理中没有配置删除功能点权限。没有配置的功能点，默认所有用户都有此功能权限
				sysEnControllerFunctionList.add( delFunction );
				modelAndView.addObject("baseDeletePriv", true );
			}

			if( null!=controller.getSysEnControllerFunctionList() ){
				for( SysEnControllerFunction func : controller.getSysEnControllerFunctionList() ){
					if( funcMenuList.contains( func.getIdentify() ) ){//功能权限管理中配置了此添加功能点权限
						if( funcMenuUserList.contains( func.getIdentify() ) ){//当前用户有此功能点权限
							sysEnControllerFunctionList.add( func );
						}
					}else{//功能权限管理中没有配置添加功能点权限。没有配置的功能点，默认所有用户都有此功能权限
						sysEnControllerFunctionList.add( func );
					}
				}
			}

			modelAndView.addObject("functionBarList", sysEnControllerFunctionList );
			//初始化页面上的菜单按钮信息结束

			//双击列表中的某行数据可进行编辑操作。
			//判断是否有编辑权限。编辑权限的控件需要单独处理。
			if( funcMenuList.contains( "baseEdit" ) ){//功能权限管理中配置了编辑功能点权限
				if( funcMenuUserList.contains( "baseEdit" ) ){//当前用户有此功能点权限
					modelAndView.addObject("baseEditPriv", true );//baseEditPriv变量在页面上作判断使用
				}else{
					modelAndView.addObject("baseEditPriv", false );
				}
			}else{//功能权限管理中没有配置删除功能点权限。没有配置的功能点，默认所有用户都有此功能权限
				modelAndView.addObject("baseEditPriv", true );
			}


			//每页大小
			if( controller.getPageSize()!=null && controller.getPageSize()>0 ){
				modelAndView.addObject("pageSize", controller.getPageSize() );
			}else{
				modelAndView.addObject("pageSize", PlatformSysConstant.PAGE_SIZE );
			}
			if( StringUtils.isNotBlank( controller.getIdentifier() ) ){
				modelAndView.addObject("identifier",controller.getIdentifier() );// 每一个列表页面的唯一身份id
			}else{
				throw new PlatformException("controller配置错误，没有配置identifier");
			}

			if( StringUtils.isNotBlank( controller.getLoadDataUrl() ) ){
				modelAndView.addObject("loadDataUrl",controller.getLoadDataUrl());
			}else{
				throw new PlatformException("controller配置错误，没有配置loadDataUrl");
			}

			if( StringUtils.isNotBlank( controller.getControllerBaseUrl() ) ){
				modelAndView.addObject("controllerBaseUrl",controller.getControllerBaseUrl() );
			}else{
				throw new PlatformException("controller配置错误，没有配置controllerBaseUrl");
			}
			modelAndView.addObject("controller",controller );
		}catch (PlatformException e){
			throw e;
		}catch (Exception e){
			LOG.error(e);
			throw new PlatformException("未知异常！");
		}
	}

	/**
	 * 加载列表数据最先调用。
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void beforeSearchData(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{

	}

	/**
	 * 根据条件分页查询列表页面数据
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping("/searchData.do")
	public ModelAndView searchData(HttpServletRequest request, HttpServletResponse response, T bean){
		LOG.debug("----------------- BaseController  searchData -----------------");
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jo = JSONObject.fromObject("{}");

		try {
			beforeSearchData(request,response,bean);
            /**
             * 处理分页参数
             * mysql和oracle分页使用pageNum和pageSize即可，mybatis底层会自动转换
             */
            String pageNumStr = request.getParameter("page");
			//String pageStartStr = request.getParameter("start");//数据开始的索引。屏蔽，暂时用不到
			String pageSizeStr = request.getParameter("limit");
			int pageNum = Integer.parseInt(pageNumStr);
			//int pageStart = Integer.parseInt(pageStartStr);
			int pageSize = Integer.parseInt(pageSizeStr);

			//查询数据
			Page queryResPage = this.getBaseService().searchData(pageNum,pageSize,bean);
			List items = queryResPage.getResult();

			//将自定义列的相关配置添加到查询结果items中
			items = addFieldConfigListExtend(request,response,bean,items);
			//处理附件字段，在列表中点击附件名称下载附件。
			items = dealListQueryResultForFile(request,response,bean,items);
			items = dealListQueryResult(request,response,bean,items);

			afterSearchData(request,response, bean);

			//组装查询结果
			jo.put("total",queryResPage.getTotal() );
			jo.put("items", items );
			jo.put("isLastPage",pageNum*pageSize>=queryResPage.getTotal()?true:false);
			jo.put("success",true);
			jo.put("msg","查询成功！");
		}catch( PlatformException e ){
			jo.put("success",false);
			jo.put("msg", ""+e.getMsg() );
			LOG.error(e);
		}catch(Exception e) {
			jo.put("success",false);
			jo.put("msg", "发生异常！" );
			LOG.error(e);
		}

		modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
		modelAndView.setViewName( this.getJsonView() );
		return modelAndView;
	}

	/**
	 * 加载列表数据最后调用。
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void afterSearchData(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{

	}



	/**
	 * 配置添加列表中操作类型的字段。
	 * 例：列表中每一行数据后的删除按钮等。
	 * @param request
	 * @param response
	 * @param bean
	 * @param dataList
     * @return
	 * @throws PlatformException
     */
	public List addFieldConfigListExtend(HttpServletRequest request, HttpServletResponse response, T bean, List dataList) throws PlatformException{
		SysEnController controller = new SysEnController();
		this.initControllerBaseInfo( controller );
		this.initField( controller );

		List<SysEnControllerField> controllerFieldList = controller.getSysEnControllerFieldList();//所有字段配置
		List<SysEnControllerField> operateFieldList = new ArrayList<SysEnControllerField>();//操作类型的字段配置

		//把操作类型的字段配置查找出来。
		for(SysEnControllerField field:controllerFieldList){
			if(  PlatformSysConstant.FIELD_TYPE_OPERATE.equals( field.getFieldType() ) ){
				operateFieldList.add( field );
			}
		}

		if( operateFieldList.size()==0 ){
			return dataList;
		}

		for( int i=0;i<dataList.size();i++ ){
			HashMap<String,Object> listFieldValMap = (HashMap<String,Object>)dataList.get(i);

			for( SysEnControllerField field:operateFieldList ){
				SysEnFieldListExtend fieldListExtendAttr = (SysEnFieldListExtend) field.getSysEnFieldAttr();

				StringBuffer tempParamValStr = new StringBuffer();
				if( PlatformSysConstant.LIST_FIELD_VIEW_DETAIL.equals( fieldListExtendAttr.getListFieldOperateTypeCode() ) ){

					tempParamValStr.append( this.createJsFunParam( fieldListExtendAttr.getParam(),field,listFieldValMap ) );

				}else if( PlatformSysConstant.LIST_FIELD_SINGLE_BASE_AJAX_REQ.equals( fieldListExtendAttr.getListFieldOperateTypeCode() ) ){

					tempParamValStr.append("'"+fieldListExtendAttr.getUrl()+"',");
					tempParamValStr.append( this.createJsFunParam( fieldListExtendAttr.getParam(),field,listFieldValMap ) );

				}else if( PlatformSysConstant.LIST_FIELD_SINGLE_DELETE.equals( fieldListExtendAttr.getListFieldOperateTypeCode() ) ){

					tempParamValStr.append("'"+fieldListExtendAttr.getUrl()+"',");
					tempParamValStr.append( this.createJsFunParam( fieldListExtendAttr.getParam(),field,listFieldValMap ) );

				}else if( PlatformSysConstant.LIST_FIELD_USER_DEFINE_OPERATE.equals( fieldListExtendAttr.getListFieldOperateTypeCode() ) ){

					if( StringUtils.isNotBlank( fieldListExtendAttr.getUrl() ) ){
						tempParamValStr.append("'"+fieldListExtendAttr.getUrl()+"',");
					}
					tempParamValStr.append( this.createJsFunParam( fieldListExtendAttr.getParam(),field,listFieldValMap ) );

				}else if( PlatformSysConstant.LIST_FIELD_OPEN_NEW_TAB.equals( fieldListExtendAttr.getListFieldOperateTypeCode() ) ){

					tempParamValStr.append("'"+fieldListExtendAttr.getUrl()+"',");
					tempParamValStr.append("'"+fieldListExtendAttr.getTabTitle()+"',");
					tempParamValStr.append( this.createJsFunParam( fieldListExtendAttr.getParam(),field,listFieldValMap ) );

				}else if( PlatformSysConstant.LIST_FIELD_OPEN_NEW_TAB_LIST.equals( fieldListExtendAttr.getListFieldOperateTypeCode() ) ){

					tempParamValStr.append("'"+fieldListExtendAttr.getUrl()+"',");
					tempParamValStr.append("'"+fieldListExtendAttr.getTabTitle()+"',");
					tempParamValStr.append( this.createJsFunParam( fieldListExtendAttr.getParam(),field,listFieldValMap ) );
					tempParamValStr.append( "'"+ fieldListExtendAttr.getTabFlag() +"'," );

				}else if( PlatformSysConstant.LIST_FIELD_OPEN_NEW_BROWSER_WINDOW.equals( fieldListExtendAttr.getListFieldOperateTypeCode() ) ){

					tempParamValStr.append("'"+fieldListExtendAttr.getUrl()+"',");
					tempParamValStr.append( this.createJsFunParam( fieldListExtendAttr.getParam(),field,listFieldValMap ) );

				}else{
					throw new PlatformException("配置列表字段出错，不存在的列表字段操作类型");
				}

				if( tempParamValStr.toString().endsWith(",") ){
					tempParamValStr.deleteCharAt( tempParamValStr.length()-1 );
				}

				StringBuffer javaScriptStr = new StringBuffer();
				javaScriptStr.append(" <a onclick=\"");
				javaScriptStr.append( fieldListExtendAttr.getFunctionName() );
				javaScriptStr.append( "("+tempParamValStr );
				javaScriptStr.append( ")\">"+field.getFieldLabel()+"</a>");

				listFieldValMap.put( field.getName(), javaScriptStr.toString() );

			}

			dataList.set(i,listFieldValMap);
		}

		return dataList;
	}

	/**
	 * 拼接js 函数的参数
	 * @param param
	 * @param field
	 * @param listFieldValMap
	 * @return
	 * @throws PlatformException
     */
	private String createJsFunParam(String param , SysEnControllerField field, HashMap<String,Object> listFieldValMap) throws PlatformException{
		StringBuffer res = new StringBuffer();
		if( StringUtils.isNotBlank( param ) ){
			String[] tempParam = param.split(",");
			for( int j=0;j<tempParam.length;j++ ){
				if( StringUtils.isBlank( tempParam[j] ) ){
					throw new PlatformException("列表操作字段("+field.getName()+")配置错误！脚本参数配置错误！");
				}
				Object tempParamVal = listFieldValMap.get( tempParam[j].trim() );
				if( tempParamVal!=null && StringUtils.isNotBlank( tempParamVal.toString() ) ){
					res.append( "'"+tempParamVal.toString()+"'," );
				}else{
					res.append( "''," );
				}
			}
		}

		return res.toString();
	}


	/**
	 * 处理列表中的附件字段。
	 * 将附件名称用<a></a>标签包裹起来，列表中点击附件名称下载附件。
	 * @param request
	 * @param response
	 * @param bean
	 * @param dataList
	 * @return
	 * @throws PlatformException
     */
	public List dealListQueryResultForFile(HttpServletRequest request, HttpServletResponse response, T bean, List dataList) throws PlatformException{
		SysEnController controller = new SysEnController();
		this.initControllerBaseInfo( controller );

		//查找出当前bean对象中所有的附件字段
		Class beanClass = bean.getClass();
		Field[] fields = beanClass.getDeclaredFields();
		List<String> fieldNameFileType = new ArrayList<String>();//存储所有附件字段名称
		for( Field field:fields ){
			//查找所有附件字段
			if( field.getName().endsWith( "OrigFileName" ) ){//根据框架定义，存储文件名称字段以OrigFileName结尾
				fieldNameFileType.add( field.getName().replace("OrigFileName","") );
			}
		}

		String basePath = PlatformInfo.fullContextUrlEndWidthSlash( request );
		String controllerBaseUrl =controller.getControllerBaseUrl().endsWith("/")?controller.getControllerBaseUrl():(controller.getControllerBaseUrl()+"/");
		for( int i=0;i<dataList.size();i++ ){
			HashMap<String,Object> map = (HashMap<String,Object>)dataList.get(i);
			for( String fieldName:fieldNameFileType ){
				if( map.get( fieldName+"OrigFileName" )!=null && StringUtils.isNotBlank( map.get( fieldName+"OrigFileName" ).toString() )
					&& map.get( fieldName+"Url" )!=null && StringUtils.isNotBlank( map.get( fieldName+"Url" ).toString() )	){
					String url = basePath+controllerBaseUrl+"download.do?beanId="+map.get( "id" ).toString()+"&fieldName="+fieldName;
					map.put( fieldName+"OrigFileName" , "<a href='javascript:void(0);' onclick='downloadFile(\""+url+"\")'>"+map.get( fieldName+"OrigFileName" ).toString()+"</a>" );
				}
			}
			dataList.set(i,map);
		}
		return dataList;
	}

	/**
	 * 对查询结果进行处理。
	 * 如果需要对从数据库中查询出的结果进行处理，可以在具体业务中覆写此方法。
	 * @param request
	 * @param response
	 * @param bean
	 * @param dataList
	 * @return
	 * @throws PlatformException
     */
	public List dealListQueryResult(HttpServletRequest request, HttpServletResponse response, T bean, List dataList) throws PlatformException{
		return dataList;
	}

	/**
	 * 下载文件
	 * @param request
	 * @param response
	 * @param bean
     */
	@RequestMapping("/download.do")
	public void download(HttpServletRequest request, HttpServletResponse response, T bean){
		String path = null;
		String fileName = null;
		try {
			String beanId = request.getParameter( "beanId" );
			String fieldName = request.getParameter("fieldName");

			Class beanClass = bean.getClass();
			Object obj = beanClass.newInstance();
			Integer tempId = Integer.parseInt( beanId );
			Method idMethod = beanClass.getDeclaredMethod("setId",new Class[]{ Integer.class } );
			idMethod.invoke(obj,tempId );

			obj = this.getBaseService().selectUniqueBeanByPrimaryKey( obj );
			if( null==obj ){
				throw new PlatformException("没有查找到要下载文件的文件信息！");
			}

			String filedNameFileUrl = fieldName+"Url";
			String filedNameFileName = fieldName+"OrigFileName";
			Method fileUrlMethod = beanClass.getDeclaredMethod("get"+StringUtils.capitalize(filedNameFileUrl) );
			Method fileNameMethod = beanClass.getDeclaredMethod("get"+StringUtils.capitalize(filedNameFileName) );

			path = (String)fileUrlMethod.invoke(obj);
			path = path.replace("/",File.separator).replace("\\",File.separator);
			fileName = (String)fileNameMethod.invoke(obj);
		}catch (Exception e){
			LOG.error(e);
			path = File.separator+"resource"+File.separator+"other"+File.separator+"downloadError.txt";
			fileName = "文件下载出错提示.txt";
		}

		String absolutePath = PlatformInfo.getAbsolutePath( request,path );
		PlatformFileUtils.download( request,response,absolutePath,fileName );
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void beforeAdd(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{

	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param bean
     * @return
     */
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response, T bean){
		ModelAndView modelAndView = new ModelAndView();

		try{
			//框架目前没有用到此方法
			this.beforeAdd(request,response,bean);
			this.afterAdd(request,response,bean);
		}catch (PlatformException e){
			LOG.error(e);
		}catch (Exception e){
			LOG.error(e);
		}

		return  modelAndView;
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void afterAdd(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{

	}

	/**
	 * 开始执行保存方法时调用
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void beforeSave(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{

    }

	/**
	 * 执行保存对象操作之前调用此方法。
	 * 可用于在保存对象之前，修改对象的值
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void beforeSaveBean(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{

	}

	/**
	 * 保存数据
	 * @param request
	 * @param response
	 * @param bean
     * @return
     */
    @RequestMapping("/save.do")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response, T bean ){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        /**
         * extjs的form表单提交后根据返回值中的success值判断走success回调函数或failure函数
         */
        try{
			this.beforeSave(request,response,bean);
			this.saveCore(request,response,bean );
        	this.afterSave(request,response,bean);

			jo.put("success",true);
            jo.put("msg","保存成功！");
        }catch( PlatformException e){
			jo.put("success",false);
			jo.put("msg",""+e.getMsg() );
		}catch (Exception e){
            LOG.error(e);
            jo.put("success",false);
            jo.put("msg","发生异常！");
        }

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.getJsonView() );

        return  modelAndView;
    }

	/**
	 * 保存数据的核心方法
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void saveCore(HttpServletRequest request, HttpServletResponse response, T bean ) throws PlatformException{

		/**
		 * 查找上传的文件，将文件保存到指定路径中，将路径地址保存到数据库中。
		 */
		//处理附件开始
			/*
			//自己实现的查找文件类型字段
			Class beanClass = bean.getClass();
			Field[] tempFields = beanClass.getDeclaredFields();
			Class multipartFileClass = MultipartFile.class;
			for( int i =0;i<tempFields.length;i++ ){
				Class fieldClass = tempFields[i].getType();
				if( fieldClass.equals( multipartFileClass ) ){//字段是文件类型
					String fieldName = tempFields[i].getName();
					Method method = beanClass.getDeclaredMethod("get"+PlatformUtils.firstLetterToUpperCase(fieldName));
					MultipartFile temp = (MultipartFile)method.invoke(bean);
				}
			}*/

		//使用spring mvc 自带的方法查找上传的附件
		//将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver( request.getSession().getServletContext() );
		//检查form中是否有enctype="multipart/form-data"
		if(multipartResolver.isMultipart(request)){
			//将request变成多部分request
			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			//获取multiRequest 中所有的文件名
			Iterator iter=multiRequest.getFileNames();

			while(iter.hasNext())
			{
				//一次遍历所有文件
				MultipartFile file=multiRequest.getFile(iter.next().toString());
				if(file!=null)
				{

					/**
					 * 页面上有文件表单字段时，即使用户在浏览器中没有选择附件，MultipartFile也不为空。但其实文件内容是空的。
					 * 在此通过判断文件名是否为空来判断是否上传了文件 。
					 */
					if( StringUtils.isBlank( file.getOriginalFilename() ) ){
						//continue;
						LOG.debug("OriginalFilename为空！");
					}

					if( file.isEmpty() ){
						continue;
					}

					//获取文件保存路径
					String savePath = request.getParameter( file.getName()+"SavePathHidden" );
					if( StringUtils.isBlank( savePath ) ){
						throw new PlatformException("没有获取到上传文件的保存路径！");
					}

					//路径修正
					savePath = savePath.replace("/",File.separator);
					savePath = savePath.replace("\\",File.separator);
					if( !savePath.startsWith( File.separator ) ){
						savePath = File.separator+savePath;
					}
					//所有上传的文件都放在项目根目录下的atta目录中
					savePath = File.separator+"atta"+savePath;

					//创建目录
					String realPath= PlatformInfo.getAbsolutePath(request,savePath);
					File fileDir = new File(realPath);
					if( !fileDir.exists() ){
						boolean mkdirRes = fileDir.mkdirs();
						if( !mkdirRes ){
							throw new PlatformException("创建上传的文件保存目录失败！");
						}
					}

					//重命名文件名
					String renameFileName = PlatformFileUtils.renameFileNameByTimeRandom( file.getOriginalFilename() );
					if( realPath.endsWith( File.separator ) ){
						realPath = realPath+renameFileName;
					}else{
						realPath = realPath+File.separator+renameFileName;
					}

					File objFile = new File(realPath);
					//保存文件
					try{
						file.transferTo( objFile );
					}catch (Exception e){
						LOG.error(e);
						throw new PlatformException("保存附件内容出错！");
					}

					//将文件的存储路径及原文件名保存到数据库中
					if( savePath.endsWith( File.separator ) ){
						savePath = savePath+renameFileName;
					}else{
						savePath = savePath+File.separator+renameFileName;
					}
					try{
						Class beanClass = bean.getClass();
						Method fileUrlMethod = beanClass.getDeclaredMethod( "set"+ PlatformStringUtils.firstLetterToUpperCase(file.getName()+"Url"),String.class );
						Method fileNameMethod = beanClass.getDeclaredMethod( "set"+PlatformStringUtils.firstLetterToUpperCase(file.getName()+"OrigFileName"),String.class );
						fileUrlMethod.invoke(bean,savePath);
						fileNameMethod.invoke(bean,file.getOriginalFilename());
					}catch (NoSuchMethodException e){
						LOG.error(e);
						throw new PlatformException("上传附件配置错误，缺少相关字段！");
					}catch (Exception e){
						LOG.error(e);
						throw new PlatformException("保存附件信息出错！");
					}

				}

			}

		}
		//处理附件结束

		beforeSaveBean(request,response,bean);
		this.getBaseService().saveBean(bean);
	}

	/**
	 * 执行完保存方法调用
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
    public void afterSave(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{

    }

	/**
	 *
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void beforeEdit(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{

	}

	/**
	 * 初始化编辑页面。
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 * @throws PlatformException
     */
	public ModelAndView initEdit(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{
		ModelAndView modelAndView = new ModelAndView();
		LOG.debug("----------------- BaseController  initEdit -----------------");
		return  modelAndView;
	}

	/**
	 * 初始化bean对象的值。
	 * 框架加载完bean对象后调用，通过覆写此方法可修改在编辑页面上的字段値。
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void initBenaVal(HttpServletRequest request, HttpServletResponse response, Object bean) throws PlatformException{

	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param bean
     * @return
     */
	@RequestMapping("/edit.do")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, T bean){
		ModelAndView modelAndView =new ModelAndView();
		JSONObject jo = JSONObject.fromObject("{}");

		try{
			this.beforeEdit(request,response,bean);
			modelAndView =initEdit(request,response,bean);

			SysEnController controller = new SysEnController();
			this.initField( controller );
			String beanId = request.getParameter("beanId");
			if( StringUtils.isBlank( beanId ) ){
				throw new PlatformException("参数不能为空！");
			}

			if( null!=controller.getSysEnControllerFieldList() && controller.getSysEnControllerFieldList().size()>0 ) {

				//可被更新的字段
				List<SysEnControllerField> updateFieldList = new ArrayList<SysEnControllerField>();
				for (SysEnControllerField conField : controller.getSysEnControllerFieldList()) {
					if (!conField.isValid()) {
						continue;
					}

					if ( conField.isAllowUpdate() || "id".equals( conField.getName() ) ) {
						if( conField.isEncryption() ){//对于需要加密的字段，编辑页面中不需要初始化值，所以在此处不需要查询需要加密字段的值。
							continue;
						}else{
							updateFieldList.add(conField);
						}
					}

				}

				if( null!=updateFieldList && updateFieldList.size()>0 ){
					Class beanClass = bean.getClass();
					Object obj = beanClass.newInstance();
					Integer tempId = Integer.parseInt( beanId );
					Method idMethod = beanClass.getDeclaredMethod("setId",new Class[]{ Integer.class } );
					idMethod.invoke(obj,tempId );

					obj = this.getBaseService().selectUniqueBeanByPrimaryKey( obj );
					if( null==obj ){
						throw new PlatformException("没有查找到要更新的信息！");
					}else{
						bean = (T)obj;
					}
					initBenaVal( request, response, obj);

					/**
					 * 将对象的各属性值组成json串
					 */
					JSONObject objJSON = JSONObject.fromObject("{}");
					for( SysEnControllerField updateField : updateFieldList ){
						Method met=null;
						Object tempObj = null;

						if( PlatformSysConstant.FORM_XTYPE_COMBOBOX.equals( updateField.getXtype() ) ){//下拉框类型

							SysEnFieldComboBoxGroup sysEnFieldComboBoxGroup = (SysEnFieldComboBoxGroup)updateField.getSysEnFieldAttr();
							if( sysEnFieldComboBoxGroup.isCascade() ){//级联下拉框
								List<SysEnFieldComboBox>  comboBoxList = sysEnFieldComboBoxGroup.getComboBoxList();
								/**
                                 * 遍历级联下拉框组的每一个下拉框
								 */
								for( SysEnFieldComboBox sysEnFieldComboBox:comboBoxList ){
									met=obj.getClass().getDeclaredMethod( "get"+PlatformStringUtils.firstLetterToUpperCase( sysEnFieldComboBox.getComboBoxName() ) );
									tempObj = met.invoke(obj);

									objJSON.put( sysEnFieldComboBox.getComboBoxName(),tempObj );
								}

							}else{//单个下拉框
								met=obj.getClass().getDeclaredMethod( "get"+PlatformStringUtils.firstLetterToUpperCase(updateField.getName()) );
								tempObj = met.invoke(obj);

								objJSON.put( updateField.getName(),tempObj );
							}

						}else if( PlatformSysConstant.FORM_XTYPE_FILE.equals( updateField.getXtype() ) ){
							//编辑页面中显示文件的原始文件名
							met=obj.getClass().getDeclaredMethod( "get"+PlatformStringUtils.firstLetterToUpperCase(updateField.getName())+"OrigFileName" );
							tempObj = met.invoke(obj);

							objJSON.put( updateField.getName(),tempObj );
						}else if( updateField.getName().contains( "SavePathHidden" ) ){
							/**
							 * 包含SavePathHidden，则说明此字段为存储附件的路径字段。
                             * 保存文件的路径，此字段为框架自动加的，不需要给赋值，具体业务的controller在添加附件字段时已经给此字段配置了值。
							 */
						}else if( PlatformSysConstant.FORM_XTYPE_DISPLAY.equals( updateField.getXtype() ) ){
							SysEnFieldDisplay sysEnFieldDisplay = (SysEnFieldDisplay)updateField.getSysEnFieldAttr();
							if( sysEnFieldDisplay.getWhiteBoard()!=null && sysEnFieldDisplay.getWhiteBoard() ){
								//白板类型的字段，只用于显示提示信息。此处不作处理。
							}else{
								//非白板类型的字段
								met=obj.getClass().getDeclaredMethod( "get"+PlatformStringUtils.firstLetterToUpperCase(updateField.getName()) );
								tempObj = met.invoke(obj);
								objJSON.put( updateField.getName(),tempObj );
							}
						}else{//其他类型字段
							met=obj.getClass().getDeclaredMethod( "get"+PlatformStringUtils.firstLetterToUpperCase(updateField.getName()) );
							tempObj = met.invoke(obj);

							Class returnClass = met.getReturnType();
							if( PlatformSysConstant.FORM_XTYPE_DATE.equals( updateField.getXtype() ) ||
								PlatformSysConstant.FORM_XTYPE_DATE_TIME.equals(  updateField.getXtype()  )	){

								if( Date.class.equals( returnClass ) ){
									//java类中定义的时间属性为Date类型
									if( null!=tempObj ){
										SysEnFieldDate sysEnFieldDate = (SysEnFieldDate)updateField.getSysEnFieldAttr();
										SimpleDateFormat sdf = new SimpleDateFormat( sysEnFieldDate.getFormatJava() );
										Date tempDate = (Date)tempObj;
										objJSON.put( updateField.getName(),sdf.format(tempDate) );
									}
								}else{
									//java类中定义的时间属性为String类型
									objJSON.put( updateField.getName(),tempObj );
								}
							}else{
								objJSON.put( updateField.getName(),tempObj );
							}

						}


					}
					jo.put( "bean",objJSON );
				}else{
					throw new PlatformException("没有可更新的字段！");
				}
			}else{
				throw new PlatformException("没有可操作的字段！");
			}

			this.afterEdit(request,response,bean);
			this.afterEdit(request,response,bean,jo);

			jo.put("success",true);
		} catch( PlatformException e ){
			jo.put("success",false);
			jo.put("msg",""+e.getMsg());
		} catch (Exception e){
			LOG.error(e);
			jo.put("success",false);
			jo.put("msg","发生异常！");
		}

		modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
		modelAndView.setViewName( this.getJsonView() );

		return  modelAndView;
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void afterEdit(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{

	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param bean
	 * @param jo
	 * @throws PlatformException
	 */
	public void afterEdit(HttpServletRequest request, HttpServletResponse response, T bean,JSONObject jo) throws PlatformException{

	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, T bean  ) throws PlatformException{

	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param bean
     * @return
     */
	@RequestMapping("/update.do")
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, T bean ){
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jo = JSONObject.fromObject("{}");

		/**
		 * extjs的form表单提交后根据返回值中的success值判断走success回调函数或failure函数
		 */
		try{
			this.beforeUpdate( request,response,bean );
			this.updateCore( request,response,bean );
			this.afterUpdate(request,response,bean);

			jo.put("success",true);
			jo.put("msg","修改成功！");
		}catch (PlatformException e){
			jo.put("success",false);
			jo.put("msg",""+e.getMsg());
		}catch (Exception e){
			LOG.error(e);
			jo.put("success",false);
			jo.put("msg","发生异常！");
		}

		modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
		modelAndView.setViewName( this.getJsonView() );

		return  modelAndView;
	}

	/**
	 * 执行更新对象操作之前调用此方法。
	 * 可用于在保存更新对象之前，修改对象的值
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
	 */
	public void beforeUpdateBean(HttpServletRequest request, HttpServletResponse response, Object bean  ) throws PlatformException{

	}

	/**
	 * 更新保存数据核心方法
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void updateCore(HttpServletRequest request, HttpServletResponse response, T bean ) throws PlatformException{
		/**
		 * 更新方法实现思路
		 *
		 * 1.根据表单提交的id，从数据库中获取到原先的oldBean对象。
		 * 2.将表单提交的bean对象的各字段的值赋值给oldBean对象。
		 * 3.更新保存oldBean对象。
		 */
		try{
			SysEnController controller = new SysEnController();
			this.initField( controller );

			Class beanClass = bean.getClass();

			//获取表单提交的id
			Method getIdMet=beanClass.getDeclaredMethod( "getId" );
			Object tempIdObj = getIdMet.invoke(bean);
			Integer tempId = Integer.parseInt( tempIdObj.toString() );

			//根据表单提交的id获取oldBean对象
			Object oldObj = beanClass.newInstance();
			Method setIdMethod = beanClass.getDeclaredMethod("setId",new Class[]{ Integer.class } );
			setIdMethod.invoke(oldObj,tempId );
			oldObj = this.getBaseService().selectUniqueBeanByPrimaryKey( oldObj );

			//将表单提交的bean对象的各字段的值赋值给oldBean对象
			if( null!=controller.getSysEnControllerFieldList() && controller.getSysEnControllerFieldList().size()>0 ) {

				//获取所有可被更新的字段
				List<SysEnControllerField> updateFieldList = new ArrayList<SysEnControllerField>();
				for (SysEnControllerField conField : controller.getSysEnControllerFieldList()) {
					if (!conField.isValid()) {
						continue;
					}

					if ( conField.isAllowUpdate() ) {
						updateFieldList.add(conField);
					}

				}

				if( null!=updateFieldList && updateFieldList.size()>0 ){
					for( SysEnControllerField updateField : updateFieldList ){
						Method getMet=null;
						Object tempObj =null;

						Method setMet=null;

						if( PlatformSysConstant.FORM_XTYPE_COMBOBOX.equals( updateField.getXtype() ) ){//下拉框类型

							SysEnFieldComboBoxGroup sysEnFieldComboBoxGroup = (SysEnFieldComboBoxGroup)updateField.getSysEnFieldAttr();
							if( sysEnFieldComboBoxGroup.isCascade() ){//级联下拉框
								List<SysEnFieldComboBox>  comboBoxList = sysEnFieldComboBoxGroup.getComboBoxList();
								/**
								 * 遍历级联下拉框组的每一个下拉框
								 */
								for( SysEnFieldComboBox sysEnFieldComboBox:comboBoxList ){
									getMet=beanClass.getDeclaredMethod( "get"+PlatformStringUtils.firstLetterToUpperCase(sysEnFieldComboBox.getComboBoxName()) );
									tempObj = getMet.invoke( bean );

									setMet=beanClass.getDeclaredMethod( "set"+PlatformStringUtils.firstLetterToUpperCase(sysEnFieldComboBox.getComboBoxName()),getMet.getReturnType() );
									setMet.invoke(oldObj,tempObj);
								}

							}else{//单个下拉框
								getMet=beanClass.getDeclaredMethod( "get"+PlatformStringUtils.firstLetterToUpperCase(updateField.getName()) );
								tempObj = getMet.invoke( bean );

								setMet=beanClass.getDeclaredMethod( "set"+PlatformStringUtils.firstLetterToUpperCase(updateField.getName()),getMet.getReturnType() );
								setMet.invoke(oldObj,tempObj);
							}

						}else if( PlatformSysConstant.FORM_XTYPE_FILE.equals( updateField.getXtype() ) ){//文件类型字段
							//文件类型字段不在此处更新，在后面对文件类型字段特殊处理
							continue;
						}else if( updateField.getName().contains( "SavePathHidden" ) ){
							/**
							 * 包含SavePathHidden，则说明此字段为存储附件的路径字段。
							 * 保存文件的路径，此字段为框架自动加的，不需要处理，在后面处理附件时使用。
							 */
						}else if( PlatformSysConstant.FORM_XTYPE_DISPLAY.equals( updateField.getXtype() ) ){
							SysEnFieldDisplay sysEnFieldDisplay = (SysEnFieldDisplay)updateField.getSysEnFieldAttr();
							if( sysEnFieldDisplay.getWhiteBoard()!=null && sysEnFieldDisplay.getWhiteBoard() ){
								//白板类型的字段，只用于显示提示信息。此处不作处理。
							}else{
								//非白板类型的字段
								getMet=beanClass.getDeclaredMethod( "get"+PlatformStringUtils.firstLetterToUpperCase(updateField.getName()) );
								tempObj = getMet.invoke( bean );

								setMet=beanClass.getDeclaredMethod( "set"+PlatformStringUtils.firstLetterToUpperCase(updateField.getName()),getMet.getReturnType() );
								setMet.invoke(oldObj,tempObj);
							}
						}else{//其它类型字段
							getMet=beanClass.getDeclaredMethod( "get"+PlatformStringUtils.firstLetterToUpperCase(updateField.getName()) );
							tempObj = getMet.invoke( bean );

							if( updateField.isEncryption() && ( tempObj==null || StringUtils.isBlank( tempObj.toString() ) ) ){//加密字段
								//编辑页面提交的加密字段的值为空时，不修改加密字段原来的值
							}else{
								setMet=beanClass.getDeclaredMethod( "set"+PlatformStringUtils.firstLetterToUpperCase(updateField.getName()),getMet.getReturnType() );
								setMet.invoke(oldObj,tempObj);
							}

						}

					}

					/**
					 * 查找上传的文件，将文件保存到指定路径中，将路径地址保存到数据库中。
					 */
					//处理附件开始

					//使用spring mvc 自带的方法查找上传的附件
					//将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
					CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver( request.getSession().getServletContext() );
					//检查form中是否有enctype="multipart/form-data"
					if(multipartResolver.isMultipart(request)){
						//将request变成多部分request
						MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
						//获取multiRequest 中所有的文件名
						Iterator iter=multiRequest.getFileNames();

						while(iter.hasNext())
						{
							//一次遍历所有文件
							MultipartFile file=multiRequest.getFile(iter.next().toString());
							if(file!=null)
							{

								/**
								 * 页面上有文件表单字段时，即使用户在浏览器中没有选择附件，MultipartFile也不为空。但其实文件内容是空的。
								 * 判断是否上传了文件 。
								 */
								if( file.isEmpty() ){
									continue;
								}
								//获取文件保存路径
								String savePath = request.getParameter( file.getName()+"SavePathHidden" );
								if( StringUtils.isBlank( savePath ) ){
									throw new PlatformException("没有获取到上传文件的保存路径！");
								}

								//路径修正
								savePath = savePath.replace("/",File.separator);
								savePath = savePath.replace("\\",File.separator);
								if( !savePath.startsWith( File.separator ) ){
									savePath = File.separator+savePath;
								}
								//所有上传的文件都放在项目根目录下的atta目录中
								savePath = File.separator+"atta"+savePath;

								//创建目录
								String realPath= PlatformInfo.getAbsolutePath(request,savePath);
								File fileDir = new File(realPath);
								if( !fileDir.exists() ){
									boolean mkdirRes = fileDir.mkdirs();
									if( !mkdirRes ){
										throw new PlatformException("创建上传的文件保存目录失败！");
									}
								}

								//重命名文件名
								String renameFileName = PlatformFileUtils.renameFileNameByTimeRandom( file.getOriginalFilename() );
								if( realPath.endsWith( File.separator ) ){
									realPath = realPath+renameFileName;
								}else{
									realPath = realPath+File.separator+renameFileName;
								}

								File objFile = new File(realPath);
								//保存文件
								file.transferTo( objFile );

								//将文件的存储路径及原文件名保存到数据库中
								if( savePath.endsWith( File.separator ) ){
									savePath = savePath+renameFileName;
								}else{
									savePath = savePath+File.separator+renameFileName;
								}
								try{
									Method fileUrlMethod = beanClass.getDeclaredMethod( "set"+PlatformStringUtils.firstLetterToUpperCase(file.getName()+"Url"),String.class );
									Method fileNameMethod = beanClass.getDeclaredMethod( "set"+PlatformStringUtils.firstLetterToUpperCase(file.getName()+"OrigFileName"),String.class );
									fileUrlMethod.invoke(oldObj,savePath);
									fileNameMethod.invoke(oldObj,file.getOriginalFilename());
								}catch (NoSuchMethodException e){
									LOG.error(e);
									throw new PlatformException("上传附件配置错误，缺少相关字段！");
								}

							}

						}

					}
					//处理附件结束

					beforeUpdateBean(request,response,oldObj);

					this.getBaseService().updateBean( oldObj );
				}else{
					throw new PlatformException("没有可更新的字段！");
				}
			}else{
				throw new PlatformException("没有可操作的字段！");
			}


		}catch (PlatformException e){
			throw new PlatformException( e.getMsg() );
		}catch (Exception e){
			LOG.error(e);
			throw new PlatformException( "发生异常！" );
		}

	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void afterUpdate(HttpServletRequest request, HttpServletResponse response, T bean  ) throws PlatformException{

	}

	/**
	 *删除单个附件
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping("/deleteAtta.do")
	public ModelAndView deleteAtta(HttpServletRequest request, HttpServletResponse response, T bean ){
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jo = JSONObject.fromObject("{}");

		/**
		 * extjs的form表单提交后根据返回值中的success值判断走success回调函数或failure函数
		 */
		try{
			Class beanClass = bean.getClass();

			String beanId =  request.getParameter("beanId");
			String fieldName = request.getParameter("fieldName");
			Integer tempId = Integer.parseInt( beanId );

			//根据表单提交的id获取oldBean对象
			Object oldObj = beanClass.newInstance();
			Method setIdMethod = beanClass.getDeclaredMethod("setId",new Class[]{ Integer.class } );
			setIdMethod.invoke(oldObj,tempId );
			oldObj = this.getBaseService().selectUniqueBeanByPrimaryKey( oldObj );

			Method setUrlMethod = beanClass.getDeclaredMethod("set"+StringUtils.capitalize( fieldName+"Url" ) ,new Class[]{ String.class } );
			Method setOrigFileNameMethod = beanClass.getDeclaredMethod("set"+StringUtils.capitalize( fieldName+"OrigFileName" ),new Class[]{ String.class } );

			setUrlMethod.invoke( oldObj,new Object[]{null} );
			setOrigFileNameMethod.invoke( oldObj,new Object[]{null} );

			this.getBaseService().updateBean( oldObj );

			jo.put("success",true);
			jo.put("msg","删除附件成功！");
		}catch (PlatformException e){
			jo.put("success",false);
			jo.put("msg",""+e.getMsg());
		}catch (Exception e){
			LOG.error(e);
			jo.put("success",false);
			jo.put("msg","发生异常！");
		}

		modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
		modelAndView.setViewName( this.getJsonView() );

		return  modelAndView;
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{

    }

	/**
	 *
	 * @param request
	 * @param response
	 * @param bean
     * @return
     */
    @RequestMapping("/delete.do")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, T bean){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        try{
        	this.beforeDelete(request,response,bean);
			this.deleteCore( request,response,bean );
       	 	this.afterDelete(request,response,bean);

            jo.put("success",true);
            jo.put("msg","删除成功！");
        } catch( PlatformException e ){
            jo.put("success",false);
            jo.put("msg",""+e.getMsg());
        } catch (Exception e){
            LOG.error(e);
            jo.put("success",false);
            jo.put("msg","发生异常！");
        }

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.getJsonView() );

        return  modelAndView;
    }

	/**
	 * 删除数据核心方法。处理删除数据业务。
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void deleteCore(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{
		try{
			String ids = request.getParameter("ids");
			if( StringUtils.isBlank( ids ) ){
				throw new PlatformException("参数不能为空！");
			}

			List<String> idList = PlatformUtils.idsToList( request );
			List<Object>  beanList = new ArrayList<Object>();

			Class beanClass = bean.getClass();
			Method idMethod = beanClass.getDeclaredMethod("setId",new Class[]{ Integer.class } );
			for( String idStr:idList ){
				Object obj = beanClass.newInstance();
				Integer tempId = Integer.parseInt( idStr );
				idMethod.invoke(obj,tempId );

				beanList.add(obj);
			}

			this.deleteBatchBean( beanList );
		} catch( PlatformException e ){
			throw new PlatformException( e.getMsg() );
		} catch (Exception e){
			LOG.error(e);
			throw new PlatformException( "发生异常！" );
		}

	}

	/**
	 * 批量删除bean对象
	 * @param beanList
	 * @throws PlatformException
     */
	public void deleteBatchBean( List<Object>  beanList ) throws PlatformException{
		this.getBaseService().deleteBatchBean( beanList );
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
    public void afterDelete(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{

    }

	/**
	 *
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void beforeMenuAjaxUpdate(HttpServletRequest request, HttpServletResponse response, T bean  ) throws PlatformException{

	}

	/**
	 * 执行更新对象操作之前调用此方法。
	 * 可用于在保存更新对象之前，修改对象的值
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
	 */
	public void beforeMenuAjaxUpdateBean(HttpServletRequest request, HttpServletResponse response, Object bean  ) throws PlatformException{

	}

	/**
	 * 菜单异步更新部分字段值
	 * @param request
	 * @param response
	 * @param bean
     * @return
     */
	@RequestMapping("/menuAjaxUpdate.do")
	public ModelAndView menuAjaxUpdate(HttpServletRequest request, HttpServletResponse response, T bean ){
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jo = JSONObject.fromObject("{}");

		/**
		 * extjs的form表单提交后根据返回值中的success值判断走success回调函数或failure函数
		 */
		try{
			this.beforeMenuAjaxUpdate( request,response,bean );
			this.menuAjaxUpdateCore( request,response,bean );
			this.afterMenuAjaxUpdate(request,response,bean);

			jo.put("success",true);
			jo.put("msg","修改成功！");
		}catch (PlatformException e){
			jo.put("success",false);
			jo.put("msg",""+e.getMsg());
		}catch (Exception e){
			LOG.error(e);
			jo.put("success",false);
			jo.put("msg","发生异常！");
		}

		modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
		modelAndView.setViewName( this.getJsonView() );

		return  modelAndView;
	}

	/**
	 * 更新数据核心方法
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void menuAjaxUpdateCore(HttpServletRequest request, HttpServletResponse response, T bean ) throws PlatformException{
		/**
		 * 更新方法实现思路
		 *
		 * 1.根据表单提交的id，从数据库中获取到原先的oldBean对象。
		 * 2.将表单提交的bean对象的各字段的值赋值给oldBean对象。
		 * 3.更新保存oldBean对象。
		 */
		try{
			String ids = request.getParameter("ids");
			String ajaxUpdateFields = request.getParameter("ajaxUpdateFields");
			if( StringUtils.isBlank( ids ) || StringUtils.isBlank( ajaxUpdateFields ) ){
				throw new PlatformException("参数不能为空！");
			}

			List<Object> updateBeanList = new ArrayList<Object>();//所有要更新的对象
			String[] idArray = ids.split(",");
			String[] fieldArray = ajaxUpdateFields.split(",");
			if( idArray!=null && idArray.length>0 && fieldArray!=null && fieldArray.length>0 ){
				for( int i=0;i<idArray.length;i++ ){
					Class beanClass = bean.getClass();
					Integer tempId = Integer.parseInt( idArray[i] );

					//根据id获取oldBean对象
					Object oldObj = beanClass.newInstance();
					Method setIdMethod = beanClass.getDeclaredMethod("setId",new Class[]{ Integer.class } );
					setIdMethod.invoke(oldObj,tempId );
					oldObj = this.getBaseService().selectUniqueBeanByPrimaryKey( oldObj );

					//将表单提交的bean对象的各字段的值赋值给oldBean对象
					if( null!=fieldArray && fieldArray.length>0 ){
						for( int j=0;j<fieldArray.length;j++ ){
							Method getMet=null;
							Method setMet=null;
							Object tempObj =null;

							getMet=beanClass.getDeclaredMethod( "get"+PlatformStringUtils.firstLetterToUpperCase(fieldArray[j]) );
							tempObj = getMet.invoke( bean );

							setMet=beanClass.getDeclaredMethod( "set"+PlatformStringUtils.firstLetterToUpperCase(fieldArray[j]),getMet.getReturnType() );
							setMet.invoke(oldObj,tempObj);
						}

						beforeMenuAjaxUpdateBean( request,response,oldObj );
						updateBeanList.add( oldObj );

					}else{
						throw new PlatformException("没有可更新的字段！");
					}

				}
			}else{
				throw new PlatformException("参数不能为空！");
			}

			this.getBaseService().updateBeans( updateBeanList );

		}catch (PlatformException e){
			throw new PlatformException( e.getMsg() );
		}catch (Exception e){
			LOG.error(e);
			throw new PlatformException( "发生异常！" );
		}
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param bean
	 * @throws PlatformException
     */
	public void afterMenuAjaxUpdate(HttpServletRequest request, HttpServletResponse response, T bean  ) throws PlatformException{

	}

	/**
	 * 加载下拉框数据
	 * @param request
	 * @param response
	 * @param bean
     * @return
     */
	@RequestMapping("/loadComboxData.do")
	public ModelAndView loadComboxData(HttpServletRequest request, HttpServletResponse response, T bean){
		LOG.debug("----------------- BaseController --> loadComboxData()   -----------------");
		ModelAndView modelAndView = new ModelAndView();

		String json;
		try {
			//String loadDataMethodName = request.getParameter("loadDataMethodName");
			String value = request.getParameter("value");
			value = StringUtils.isBlank( value )?null:value.toString();
			/*if( StringUtils.isBlank( loadDataMethodName )  ){
				throw new PlatformException("加载下来框数据请求参数错误！");
			}*/

			String cascChild = request.getParameter("cascChild");
			if( StringUtils.isNotBlank( cascChild ) && "true".equals( cascChild.trim() ) && StringUtils.isBlank( value ) ){
				//级联框的非第一个下拉框请求数据时，value必须有值。
				//value为父级联框选中的值。
				throw new PlatformException("级联下拉框加载数据请求参数错误！");
			}

			//查询数据
			List<Map<String,Object>> resList = null;
			String comboBoxFlag = request.getParameter("comboBoxFlag");
			if( comboBoxFlag.startsWith( "constant_" ) ){
				resList = this.getBaseService().loadComboboxDataConstant( comboBoxFlag.replaceFirst("constant_","") );
			}else{
				resList = this.loadComboBoxDataMap(request,response,bean,value);
			}

			JSONObject jo = JSONObject.fromObject("{}");
			jo.put("comboboxData", resList );
			json = jo.toString();
		} catch(PlatformException e) {
			json = "{\"success\":false,\"msg\":\"加载数据失败！"+e.getMsg()+"\",\"comboboxData\":[]}";
		}catch(Exception e) {
			LOG.error(e);
			json = "{\"success\":false,\"msg\":\"加载数据失败，发生异常！\",\"comboboxData\":[]}";
		}

		modelAndView.addObject( PlatformSysConstant.JSONSTR, json );
		modelAndView.setViewName( this.getJsonView() );
		return modelAndView;
	}

	/**
	 * 查询下拉框数据方法
	 *
	 * 1.如果在具体业务的controller中覆写此方法，可以根据具体业务返回所需要的数据。
	 * 2.具体业务controller不覆写此方法，则调用BaseController类本身方法，走系统统一的接口查询数据。此情况之前的实现方式过于依赖具体业务的service,暂时放弃，后续再实现。
	 * @param request
	 * @param response
	 * @param bean
	 * @param parentId
     * @return
	 * @throws PlatformException
     */
	public List<Map<String,Object>> loadComboBoxDataMap(HttpServletRequest request, HttpServletResponse response, T bean, String parentId) throws PlatformException{
		List<Map<String,Object>> queryResList = new ArrayList<Map<String,Object>>();
		return queryResList;
	}

	/**
	 * 加载树节点json串
	 * @param request
	 * @param response
	 * @param bean
     * @return
     */
	@RequestMapping("/loadTreeData.do")
	public ModelAndView loadTreeData(HttpServletRequest request, HttpServletResponse response, T bean){
		LOG.debug("----------------- BaseController --> loadTreeData()   -----------------");
		ModelAndView modelAndView = new ModelAndView();

		try {
			String fieldMap = request.getParameter("fieldMap");//查询结果字段与ext树节点属性对应关系
			String resType = request.getParameter("resType");//sql查询结果集
			String treeRootVal = request.getParameter("treeRootVal");//树根节点值
			String multiSelect = request.getParameter("multiSelect");//是否多选，多选时，树节点前有checkbox，单选没有

			if( StringUtils.isBlank( fieldMap )  ){
				throw new PlatformException("加载树节点请求参数错误！");
			}

			List<SysEnTreeNode> sysEnTreeNodeList = null;

			if( StringUtils.isBlank( resType ) || "map".equals( resType ) ){
				/**
				 * sql查询结果集映射为List<Map<String,Object>>，然后对map进行处理。
				 *
				 * loadTreeNodeDataMap方法返回树节点数据。
				 * 在具体业务controller中实现loadTreeNodeDataMap方法，
				 */
				List<Map<String,Object>> queryResList = this.loadTreeNodeDataMap(request,response,bean);
				sysEnTreeNodeList = PlatformTreeUtils.copyMapValueToTreeNode( queryResList,fieldMap );
			}else{
				/**
				 * sql查询结果集映射为List<Object>，后续再实现
				 */
				//sysEnTreeNodeList = PlatformTreeUtils.copyBeanProToTreeNode(null,fieldMap);
			}
			String treeNodeJson = PlatformTreeUtils.createExtTreeList( sysEnTreeNodeList,"true".equals( multiSelect )?true:false,treeRootVal );

			modelAndView.addObject("jsonStr", treeNodeJson );
		} catch (PlatformException e){
			modelAndView.addObject("jsonStr", "[{\"success\":false,\"msg\":\"加载数据失败！"+e.getMsg()+"\"}]" );
		}catch(Exception e) {
			LOG.error(e);
			modelAndView.addObject("jsonStr", "[{\"success\":false,\"msg\":\"加载数据失败，发生异常！\"}]" );
		}

		modelAndView.setViewName( this.getJsonView() );
		return modelAndView;
	}

	/**
	 * 查询树节点数据方法。
	 *
	 * 1.如果在具体业务的controller中覆写此方法，可以根据具体业务返回所需要的树节点数据。
	 * 2.具体业务controller不覆写此方法，则调用BaseController类本身方法，走系统统一的接口查询树结点数据。此情况实现了一半，后续再实现。
	 * @param request
	 * @param response
	 * @param bean
     * @return
	 * @throws PlatformException
     */
	public List<Map<String,Object>> loadTreeNodeDataMap(HttpServletRequest request, HttpServletResponse response, T bean) throws PlatformException{
		//List<Map<String,Object>> queryResList = this.getBaseService().loadTreeNodeDataMap("loadTreeNodeDataMap");
		List<Map<String,Object>> queryResList = new ArrayList<Map<String,Object>>();
		return queryResList;
	}

	/**
	 * 请求提交的参数给java基本类型及日期类型需要进行类型转换处理，否则请求报400
	 * 解决参数基本类型及日期类型传递问题
	 * @param binder
     */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		/**
		 * 提交的参数转换为Date类型
		 */
		//使用如下new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true)只能正确处理年月日格式
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		//使用new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true)只能正确处理年月日时分秒格式
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		/**
		 * 使用PlatformCustomDateEditor可以同时处理yyyy-MM-dd和yyyy-MM-dd HH:mm:ss两种格式
		 */
		binder.registerCustomEditor(Date.class, new PlatformCustomDateEditor());

		/**
		 * 基本类型可以正常解析，暂不需要进行类型转换处理   201705
		 *
		 * 基本类型无法正常解析，加上转换器也不起作用   20170621
		 * java类型是基本类型的时候，页面参数为空或非数字时，无法进行类型转换，请求页面时报400错误。
		 *
		 */
		/*binder.registerCustomEditor(short.class,new ShortEditor());
		binder.registerCustomEditor(int.class, new IntEditor());
		binder.registerCustomEditor(long.class, new LongEditor());
		binder.registerCustomEditor(float.class, new FloatEditor());
		binder.registerCustomEditor(double.class, new DoubleEditor());*/
	}

	public T getBean() {
		return bean;
	}

	public void setBean(T bean) {
		this.bean = bean;
	}

	public Class getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	@Deprecated
	public String getJsonView() {
		return this.VIEW_JSON;
	}

	public BaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}
}
