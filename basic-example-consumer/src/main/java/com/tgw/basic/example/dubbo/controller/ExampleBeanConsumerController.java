package com.tgw.basic.example.dubbo.controller;

import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.example.dubbo.model.ExampleBeanDubbo;
import com.tgw.basic.example.dubbo.service.ExampleBeanConsumerService;
import com.tgw.basic.framework.controller.BaseController;
import net.sf.json.JSONObject;
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

/**
 * Created by zjg on 2020/5/5.
 */
@Controller
@RequestMapping("/consumer")
public class ExampleBeanConsumerController extends BaseController<ExampleBeanDubbo>{
    private static final Logger LOG = LoggerFactory.getLogger(ExampleBeanConsumerController.class);

    @Resource
    private ExampleBeanConsumerService exampleBeanConsumerService;

    @RequestMapping("/all.do")
    @ResponseBody
    public String consumerAllBean(){
        List<ExampleBeanDubbo> allBean = exampleBeanConsumerService.consumerAllBean();

        JSONObject jo = JSONObject.fromObject("{}");
        jo.put("allBean",allBean);
        LOG.info("allBean",jo.toString());

        return jo.toString();
    }

    @RequestMapping("/all1.do")
    public ModelAndView consumerAllBean1(){
        ModelAndView modelAndView = new ModelAndView();
        JSONObject jo = JSONObject.fromObject("{}");

        List<ExampleBeanDubbo> allBean = exampleBeanConsumerService.consumerAllBean();
        jo.put("success",true);
        jo.put("allBean",allBean);
        LOG.info("allBean",jo.toString());

        modelAndView.addObject( PlatformSysConstant.JSONSTR, jo.toString() );
        modelAndView.setViewName( this.VIEW_JSON );

        return  modelAndView;
    }

    @RequestMapping("/single.do")
    @ResponseBody
    public String consumerBeanById(HttpServletRequest request, HttpServletResponse response){
        String idStr = request.getParameter("id");
        LOG.info("idStr: "+idStr);
        ExampleBeanDubbo exampleBeanDubbo = exampleBeanConsumerService.consumerBeanById(Integer.parseInt(idStr));

        JSONObject jo = JSONObject.fromObject("{}");
        jo.put("bean",exampleBeanDubbo);
        LOG.info("bean",jo.toString());

        return jo.toString();
    }

}
