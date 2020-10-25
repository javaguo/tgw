package com.tgw.basic.example.dubbo.controller;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.common.utils.json.PlatformJsonUtils;
import com.tgw.basic.example.dubbo.model.ExampleBeanDubbo;
import com.tgw.basic.example.dubbo.service.ExampleBeanConsumerService;
import com.tgw.basic.framework.controller.BaseController;
import com.tgw.basic.framework.model.controller.SysEnController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by zjg on 2020/5/5.
 */
@Controller
@RequestMapping("/consumer")
public class ExampleBeanConsumerController extends BaseController<ExampleBeanDubbo>{
    private static final Logger LOG = LoggerFactory.getLogger(ExampleBeanConsumerController.class);

    @Resource
    private ExampleBeanConsumerService exampleBeanConsumerService;

    @Override
    public void initControllerBaseInfo(SysEnController controller) throws PlatformException {
        controller.setIdentifier( "ExampleDubboList" );// 每一个列表页面的唯一身份id
        controller.setLoadDataUrl( "consumer/searchData.do" );//加载列表页面数据的方法
        controller.setControllerBaseUrl( "consumer/" );//控制器的请求地址
    }

    @Override
    public void initSearch(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, ExampleBeanDubbo bean ) throws PlatformException {
    }

    @Override
    public void initField( SysEnController controller ) throws PlatformException {
        controller.addFieldId("id","ID",null);
        controller.addFieldDatetime("name","名称",false,false,false,false,false,null);

        /**--------------------- 添加时间、更新时间固定字段 示例开始 -----------------------------------------------------------------*/
        controller.addFieldDatetime("addTime","添加时间",true,false,false,false,false,null);
        controller.addFieldDatetime("updateTime","更新时间",true,false,false,false,false,null);
        /**--------------------- 添加时间、更新时间固定字段 示例结束 -----------------------------------------------------------------*/
    }

    @Override
    public void initFunction(SysEnController controller) throws PlatformException {
        controller.addFunctionBaseAjaxIndepe("consumerSingle","消费单个对象","consumer//single.do",true,"Application",1);
        controller.addFunctionBaseAjaxIndepe("consumerAll","消费多个对象","consumer//all.do",true,"Application",2);

        StringBuilder strIns = new StringBuilder();
        strIns.append("此页面是调用dubbo示例页面！");
        controller.addFunctionInstructions("instructions1","功能说明","Zoom",10,strIns.toString());
    }

    @RequestMapping("/all.do")
    @ResponseBody
    public String consumerAllBean(){
        List<ExampleBeanDubbo> allBean = exampleBeanConsumerService.consumerAllBean();

        Map map = PlatformJsonUtils.stringToMap("{}");
        map.put("success",true);
        map.put("allBean",allBean);
        map.put("msg","共消费"+allBean.size()+"个对象！");
        LOG.info(PlatformJsonUtils.toJsonString(map));

        return PlatformJsonUtils.toJsonString(map);
    }

    @RequestMapping("/all1.do")
    public ModelAndView consumerAllBean1(){
        ModelAndView modelAndView = new ModelAndView();
        Map map = PlatformJsonUtils.stringToMap("{}");

        List<ExampleBeanDubbo> allBean = exampleBeanConsumerService.consumerAllBean();
        map.put("success",true);
        map.put("allBean",allBean);
        map.put("msg","共消费"+allBean.size()+"个对象！");
        LOG.info("allBean",PlatformJsonUtils.toJsonString(map));

        modelAndView.addObject( PlatformSysConstant.JSONSTR, PlatformJsonUtils.toJsonString(map) );
        modelAndView.setViewName( this.VIEW_JSON );

        return  modelAndView;
    }

    @RequestMapping("/single.do")
    @ResponseBody
    public String consumerBeanById(HttpServletRequest request, HttpServletResponse response){
        String idStr = request.getParameter("id");
        idStr = StringUtils.isBlank(idStr)?"100":idStr;
        LOG.info("idStr: "+idStr);
        ExampleBeanDubbo exampleBeanDubbo = exampleBeanConsumerService.consumerBeanById(Integer.parseInt(idStr));

        Map map = PlatformJsonUtils.stringToMap("{}");
        map.put("success",true);
        map.put("exampleBeanDubbo",exampleBeanDubbo);
        map.put("msg","消费的对象id为"+exampleBeanDubbo.getId());
        LOG.info(PlatformJsonUtils.toJsonString(map));

        return PlatformJsonUtils.toJsonString(map);
    }

}
