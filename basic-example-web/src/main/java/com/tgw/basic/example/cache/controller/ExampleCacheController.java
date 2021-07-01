package com.tgw.basic.example.cache.controller;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.common.utils.json.PlatformJsonUtils;
import com.tgw.basic.example.cache.service.ExampleCacheService;
import com.tgw.basic.example.exampleBean.model.ExampleBean;
import com.tgw.basic.example.exampleBean.service.ExampleBeanService;
import com.tgw.basic.framework.controller.BaseController;
import com.tgw.basic.framework.model.controller.SysEnController;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/exampleCache")
public class ExampleCacheController extends BaseController<ExampleBean> {
    private static final Logger LOG = LoggerFactory.getLogger(ExampleCacheController.class);

    @Resource
    private ExampleBeanService exampleBeanService;
    @Resource
    private ExampleCacheService exampleCacheService;

    @Override
    public void initControllerBaseInfo(SysEnController controller) throws PlatformException {
        controller.setIdentifier( "ExampleCacheList" );// 每一个列表页面的唯一身份id
        controller.setLoadDataUrl( "exampleCache/searchData.do" );//加载列表页面数据的方法
        controller.setControllerBaseUrl( "exampleCache/" );//控制器的请求地址
    }

    @Override
    public void initSearch(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, ExampleBean bean ) throws PlatformException {
        if( null!=this.getExampleBeanService() ){
            super.initService(  this.getExampleBeanService()  );
        }else{

        }
    }

    @Override
    public void initField( SysEnController controller ) throws PlatformException {
        controller.addFieldId("id","ID",null);

        String formHiddenConfigs = "value:'hidden隐藏域值'";
        String formTextConfigs = "labelWidth:100,width:400,emptyText:'文本提示信息',value:'初始值'";

        controller.addFieldHidden( "formHidden","form隐藏域",true,true,true,formHiddenConfigs );
        controller.addFieldText("formText","文本框",true,true,true,true,false,formTextConfigs);

        /**--------------------- 添加时间、更新时间固定字段 示例开始 -----------------------------------------------------------------*/
        controller.addFieldDatetime("addTime","添加时间",true,false,false,false,false,null);
        controller.addFieldDatetime("updateTime","更新时间",true,false,false,false,false,null);
        /**--------------------- 添加时间、更新时间固定字段 示例结束 -----------------------------------------------------------------*/

    }

    @Override
    public void initFunction(SysEnController controller) throws PlatformException {
        controller.addFunctionBaseAjaxIndepe("exampleCacheable","cacheable","exampleCache//cacheable.do",true,"Application",2);
        controller.addFunctionBaseAjaxIndepe("exampleCachePut","cachePut","exampleCache//cachePut.do",true,"Application",3);
        controller.addFunctionBaseAjaxIndepe("exampleCacheEvict","cacheEvict","exampleCache//cacheEvict.do",true,"Application",4);
        controller.addFunctionBaseAjaxIndepe("exampleCaching","caching","exampleCache//caching.do",true,"Application",5);

        StringBuilder strIns = new StringBuilder();
        strIns.append("使用 Spring Cache 注解示例！");
        controller.addFunctionInstructions("instructions1","功能说明","Zoom",10,strIns.toString());
    }

    @RequestMapping("/cacheable.do")
    public ModelAndView cacheable(){
        ModelAndView modelAndView = new ModelAndView();
        Map map = PlatformJsonUtils.stringToMap("{}");

        String paramStr = RandomStringUtils.randomAlphanumeric(5);
        LOG.info("prepare invoke Controller's cacheable 1,param:"+paramStr);
        testCacheable(paramStr);// 调用所在类的使用了Cacheable注解的方法，缓存不生效（例：调用本Controller的Cacheable注解的方法，缓存不生效）。需要做相应调整才能生效。
        LOG.info("prepare invoke Controller's cacheable 2,param:"+paramStr);
        testCacheable(paramStr);// 缓存不生效，故会被再次调用

        String paramStr1 = RandomStringUtils.randomAlphanumeric(5);
        LOG.info("prepare invoke Service's cacheable 1,param:"+paramStr1);
        getExampleCacheService().cacheable(paramStr1);// 缓存中有值，则不会调用调用执行方法，直接返回缓存值；否则调用调用执行方法并将返回值添加到缓存
        LOG.info("prepare invoke Service's cacheable 2,param:"+paramStr1);
        getExampleCacheService().cacheable(paramStr1);// 传了相同参数，直接返回缓存值，方法肯定不会被调用执行

        String paramStr2 = RandomStringUtils.randomAlphanumeric(5);
        LOG.info("prepare invoke Service's cacheable 3,param:"+paramStr2);
        getExampleCacheService().cacheable(paramStr2);// 与前面的参数值不同。缓存中有值，则不会调用调用执行方法，直接返回缓存值；否则调用调用执行方法并将返回值添加到缓存

        String p1 = RandomStringUtils.randomAlphanumeric(2);
        String p2 = RandomStringUtils.randomAlphanumeric(2);
        LOG.info("prepare invoke Service's cacheable1ParamAsKey 1th");
        getExampleCacheService().cacheable1ParamAsKey(p1,p2);// 缓存中有值，则不会调用调用执行方法，直接返回缓存值；否则调用调用执行方法并将返回值添加到缓存
        LOG.info("prepare invoke Service's cacheable1ParamAsKey 2th");
        getExampleCacheService().cacheable1ParamAsKey(p1,p2);// 传了相同参数，直接返回缓存值，方法肯定不会被调用执行

        String p3 = RandomStringUtils.randomAlphanumeric(3);
        String p4 = RandomStringUtils.randomAlphanumeric(4);
        LOG.info("prepare invoke Service's cacheable2ParamAsKey 1th");
        getExampleCacheService().cacheable2ParamAsKey(p3,p4);// 缓存中有值，则不会调用调用执行方法，直接返回缓存值；否则调用调用执行方法并将返回值添加到缓存
        LOG.info("prepare invoke Service's cacheable2ParamAsKey 2th");
        getExampleCacheService().cacheable2ParamAsKey(p3,p4);// 传了相同参数，直接返回缓存值，方法肯定不会被调用执行

        LOG.info("prepare invoke Service's cacheableSync 1th");
        getExampleCacheService().cacheableSync("abc");// 多线程同步

        LOG.info("prepare invoke Service's cacheableCondition 1th");
        getExampleCacheService().cacheableCondition("a");// 此参数不满足缓存条件，结果不会缓存
        LOG.info("prepare invoke Service's cacheableCondition 2th");
        getExampleCacheService().cacheableCondition("abc");// 此参数满足缓存条件，结果会被缓存

        LOG.info("prepare invoke Service's cacheableUnless 1th");
        getExampleCacheService().cacheableUnless("d");
        LOG.info("prepare invoke Service's cacheableUnless 2th");
        getExampleCacheService().cacheableUnless("abcdefghigklmn,abcdefghigklmn");

        map.put("success",true);
        map.put("msg","执行完毕！");
        modelAndView.addObject( PlatformSysConstant.JSONSTR, PlatformJsonUtils.toJsonString(map) );
        modelAndView.setViewName( this.VIEW_JSON );

        return  modelAndView;
    }

    @Cacheable("cacheable")
    public String testCacheable(String param){
        LOG.info("invoke Controller's testExampleCacheable, param: "+param);
        return param+" value";
    }

    @RequestMapping("/cachePut.do")
    public ModelAndView cachePut(){
        ModelAndView modelAndView = new ModelAndView();
        Map map = PlatformJsonUtils.stringToMap("{}");

        String cachePutParam = "abc";
        LOG.info("prepare invoke Service's cachePut 1th");
        getExampleCacheService().cachePut(cachePutParam);// 调用执行方法并更新缓存
        LOG.info("prepare invoke Service's cachePut 2th");
        getExampleCacheService().cachePut(cachePutParam);// 再次调用，还是会调用执行方法并更新缓存

        map.put("success",true);
        map.put("msg","执行完毕！");
        modelAndView.addObject( PlatformSysConstant.JSONSTR, PlatformJsonUtils.toJsonString(map) );
        modelAndView.setViewName( this.VIEW_JSON );

        return  modelAndView;
    }

    @RequestMapping("/cacheEvict.do")
    public ModelAndView cacheEvict(){
        ModelAndView modelAndView = new ModelAndView();
        Map map = PlatformJsonUtils.stringToMap("{}");

        String cachePutParam = "abc";
        LOG.info("prepare invoke Service's cacheEvict 1th");
        getExampleCacheService().cacheEvict(cachePutParam);// 删除缓存
        LOG.info("prepare invoke Service's cacheEvict 2th");
        getExampleCacheService().cacheEvict(cachePutParam);// 删除缓存

        map.put("success",true);
        map.put("msg","执行完毕！");
        modelAndView.addObject( PlatformSysConstant.JSONSTR, PlatformJsonUtils.toJsonString(map) );
        modelAndView.setViewName( this.VIEW_JSON );

        return  modelAndView;
    }

    @RequestMapping("/caching.do")
    public ModelAndView caching(){
        ModelAndView modelAndView = new ModelAndView();
        Map map = PlatformJsonUtils.stringToMap("{}");

        String cachingParam = "abc";
        LOG.info("prepare invoke Service's caching 1th");
        getExampleCacheService().caching(cachingParam);// 使用多个缓存注解的示例
        LOG.info("prepare invoke Service's caching 2th");
        getExampleCacheService().caching(cachingParam);//

        map.put("success",true);
        map.put("msg","执行完毕！");
        modelAndView.addObject( PlatformSysConstant.JSONSTR, PlatformJsonUtils.toJsonString(map) );
        modelAndView.setViewName( this.VIEW_JSON );

        return  modelAndView;
    }

    public ExampleBeanService getExampleBeanService() {
        return exampleBeanService;
    }

    public void setExampleBeanService(ExampleBeanService exampleBeanService) {
        this.exampleBeanService = exampleBeanService;
    }

    public ExampleCacheService getExampleCacheService() {
        return exampleCacheService;
    }

    public void setExampleCacheService(ExampleCacheService exampleCacheService) {
        this.exampleCacheService = exampleCacheService;
    }
}
