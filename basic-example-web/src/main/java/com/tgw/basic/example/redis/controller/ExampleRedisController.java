package com.tgw.basic.example.redis.controller;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.example.exampleBean.model.ExampleBean;
import com.tgw.basic.example.exampleBean.service.ExampleBeanService;
import com.tgw.basic.framework.controller.BaseController;
import com.tgw.basic.framework.model.controller.SysEnController;
import com.tgw.basic.redis.utils.PlatformRedisStringUtil;
import com.tgw.basic.redis.utils.PlatformRedisUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Set;

/**
 * Created by zhaojg on 2020/06/07
 */
@Controller
@RequestMapping("/exampleRedis")
public class ExampleRedisController extends BaseController<ExampleBean> {
    private static final Logger LOG = LoggerFactory.getLogger(ExampleRedisController.class);

    @Resource
    private ExampleBeanService exampleBeanService;
    @Autowired
    private PlatformRedisUtil platformRedisUtil;
    @Autowired
    private PlatformRedisStringUtil platformRedisStringUtil;

    // StringRedisTemplate 相关前缀
    private static final  String SRT_PREFIX_STRING = "SRT_";
    // RedisTemplate 相关前缀
    private static final  String RT_PREFIX_STRING = "RT_";


    @Override
    public void initControllerBaseInfo(SysEnController controller) throws PlatformException {
        controller.setIdentifier( "ExampleRedisList" );// 每一个列表页面的唯一身份id
        controller.setLoadDataUrl( "exampleRedis/searchData.do" );//加载列表页面数据的方法
        controller.setControllerBaseUrl( "exampleRedis/" );//控制器的请求地址
    }

    @Override
    public void initSearch(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, ExampleBean bean ) throws PlatformException {
        //覆写了initSearch方法，则只调用覆写的initSearch方法
        /**
         * 将具体的业务的service对象赋值给baseservice，必须的操作。
         * service层需要将具体业务的mapper赋值给BaseModelMapper
         *
         * 此操作主要解决的问题是BaseModelMapper无法注入到BaseServiceImpl中的问题。手动赋值。
         *
         * 要点：
         * 1.BaseController会调用统一的searchData()接口查询具体的业务数据。
         * 2.具体业务的mapper文件中实现searchData查询语句
         *
         *
         */
        if( null!=this.getExampleBeanService() ){
            super.initService(  this.getExampleBeanService()  );
        }else{

        }
    }

    @Override
    public void initField( SysEnController controller ) throws PlatformException {
        controller.addFieldId("id","ID",null);

        /**--------------------- 隐藏域、文本框、密码框、文本域  示例开始 -----------------------------------------------------------------*/
        /**
         *
         * 添加、编辑窗口中的显示样式相同，在各controller中分别配置，搜索区域样式已在前端页面中统一配置好。
         */
        String formHiddenConfigs = "value:'hidden隐藏域值'";
        String formTextConfigs = "labelWidth:100,width:400,emptyText:'文本提示信息',value:'初始值'";
        String formPasswordConfigs = "labelWidth:100,width:400,emptyText:'密码提示信息',value:'123456'";
        String formTextAreaConfigs = "labelWidth:100,width:400,height:80,emptyText:'文本域内容......',maxLength:50,maxLengthText:'最长为50个字',minLength:5,minLengthText:'最小为5个字'";

        controller.addFieldHidden( "formHidden","form隐藏域",true,true,true,formHiddenConfigs );
        controller.addFieldText("formText","文本框",true,true,true,true,false,formTextConfigs);
        controller.addFieldPassword("formPassword","密码框",true,true,true,false,formPasswordConfigs);
        controller.addFieldTextArea("formTextArea","文本域",true,true,true,false,false,formTextAreaConfigs);
        /**--------------------- 隐藏域、文本框、密码框、文本域  示例结束 -----------------------------------------------------------------*/

        /**--------------------- 添加时间、更新时间固定字段 示例开始 -----------------------------------------------------------------*/
        controller.addFieldDatetime("addTime","添加时间",true,false,false,false,false,null);
        controller.addFieldDatetime("updateTime","更新时间",true,false,false,false,false,null);
        /**--------------------- 添加时间、更新时间固定字段 示例结束 -----------------------------------------------------------------*/

    }

    @Override
    public void initFunction(SysEnController controller) throws PlatformException {
        controller.addFunctionBaseAjaxIndepe("redisKeysSize","查询key数量","exampleRedis//keysSize.do",true,"Application",1);
        controller.addFunctionBaseAjaxIndepe("redisDeleteAllKeys","删除所有key","exampleRedis//redisDeleteAllKeys.do",true,"Application",2);

        controller.addFunctionBaseAjaxIndepe("SRT_set","SRT_set","exampleRedis//stringRedisTemplateSet.do",true,"Application",10);
        controller.addFunctionBaseAjaxIndepe("RT_set","RT_set","exampleRedis//redisTemplateSet.do",true,"Application",50);

        /*controller.addFunctionBaseAjaxIndepe("stringRedisTemplate","StringRedisTemplate测试","exampleRedis//ajaxStringRedisTemplateTest.do",true,"Application",6);
        controller.addFunctionBaseAjaxIndepe("redisTemplate","RedisTemplate测试","exampleRedis//ajaxRedisTemplateTest.do",true,"Application",6);*/

        StringBuilder strIns = new StringBuilder();
        strIns.append("此页面是调用redis示例页面！");
        controller.addFunctionInstructions("instructions1","功能说明","Zoom",10,strIns.toString());
    }

    @RequestMapping("/keysSize.do")
    public ModelAndView keysSize(){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        long startTime = System.currentTimeMillis();
        Set keys = platformRedisUtil.keys("*");
        long endTime = System.currentTimeMillis();
        String time = "用时"+ ((endTime-startTime)/1000)+"秒"+((endTime-startTime)%1000)+"毫秒！";

        LOG.info("本次查询到"+(keys!=null?keys.size():0)+"个key！"+time);
        jo.put("success",true);
        jo.put("msg","共"+(keys!=null?keys.size():0)+"个key！"+time);

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.VIEW_JSON );

        return  modelAndView;
    }

    @RequestMapping("/redisDeleteAllKeys.do")
    public ModelAndView redisDeleteAllKeys(){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        long startTime = System.currentTimeMillis();
        Set keys = platformRedisUtil.keys("*");
        platformRedisUtil.del( keys );
        long endTime = System.currentTimeMillis();
        String time = "用时"+ ((endTime-startTime)/1000)+"秒"+((endTime-startTime)%1000)+"毫秒！";

        LOG.info("共删除"+(keys!=null?keys.size():0)+"个key！"+time);
        jo.put("success",true);
        jo.put("msg","执行完毕！"+"共删除"+(keys!=null?keys.size():0)+"个key！"+time);

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.VIEW_JSON );

        return  modelAndView;
    }

    @RequestMapping("/stringRedisTemplateSet.do")
    public ModelAndView stringRedisTemplateSet(){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        String prefix = SRT_PREFIX_STRING;

        long startTime = System.currentTimeMillis();
        int total = 10000*10;
        for(int i=0;i<total;i++){
            String key = prefix+i;
            platformRedisStringUtil.set(key, RandomStringUtils.randomAlphanumeric(10));
        }
        long endTime = System.currentTimeMillis();
        String time = "用时"+ ((endTime-startTime)/1000)+"秒"+((endTime-startTime)%1000)+"毫秒！";

        String info = "共添加了"+total+"个key！值为String对象！"+time;
        LOG.info(info);
        jo.put("success",true);
        jo.put("msg","执行完毕！"+info);

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.VIEW_JSON );

        return  modelAndView;
    }

    @RequestMapping("/redisTemplateSet.do")
    public ModelAndView redisTemplateSet(){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        String prefix = RT_PREFIX_STRING;

        long startTime = System.currentTimeMillis();
        int total = 10000*10;
        /*for(int i=0;i<total;i++){
            String key = prefix+i;
            platformRedisUtil.set(key, RandomStringUtils.randomAlphanumeric(10));
        }*/
       /* for(int i=0;i<total;i++){
            LOG.info("RedisTemplate val_"+i+": "+platformRedisUtil.get(prefix+t+"_"+i));
        }*/

        for(int i=0;i<total;i++){
            ExampleBean bean = new ExampleBean();
            bean.setId(i);
            bean.setFormText("value_汉字_"+RandomStringUtils.randomAlphanumeric(10));
            bean.setAddTime(new Date());

            platformRedisUtil.set( prefix+i, bean);
        }
        /*for(int i=0;i<total;i++){
            try {
                ExampleBean temp = (ExampleBean)platformRedisUtil.get(prefix+i);
                LOG.info("RedisTemplate bean_val_"+i+": "+JSONObject.fromObject(temp).toString());
            } catch (Exception e) {
                LOG.error("",e);
            }
        }*/
        long endTime = System.currentTimeMillis();
        String time = "用时"+ ((endTime-startTime)/1000)+"秒"+((endTime-startTime)%1000)+"毫秒！";

        String info = "共添加了"+total+"个key！值为ExampleBean对象！"+time;
        LOG.info(info);
        jo.put("success",true);
        jo.put("msg","执行完毕！"+info);

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.VIEW_JSON );

        return  modelAndView;
    }


    public ExampleBeanService getExampleBeanService() {
        return exampleBeanService;
    }

    public void setExampleBeanService(ExampleBeanService exampleBeanService) {
        this.exampleBeanService = exampleBeanService;
    }

    public PlatformRedisUtil getPlatformRedisUtil() {
        return platformRedisUtil;
    }

    public void setPlatformRedisUtil(PlatformRedisUtil platformRedisUtil) {
        this.platformRedisUtil = platformRedisUtil;
    }

    public PlatformRedisStringUtil getPlatformRedisStringUtil() {
        return platformRedisStringUtil;
    }

    public void setPlatformRedisStringUtil(PlatformRedisStringUtil platformRedisStringUtil) {
        this.platformRedisStringUtil = platformRedisStringUtil;
    }
}
