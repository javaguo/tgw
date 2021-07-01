package com.tgw.basic.example.basic.controller;

import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.common.utils.json.PlatformJsonUtils;
import com.tgw.basic.demo.quartz.DemoQuartzJob2;
import com.tgw.basic.demo.quartz.DemoQuartzJob3;
import com.tgw.basic.example.exampleBean.controller.ExampleBeanController;
import com.tgw.basic.example.exampleBean.model.ExampleBean;
import com.tgw.basic.framework.controller.BaseController;
import com.tgw.basic.quartz.utils.TgwQuartzUtils;
import org.quartz.SimpleScheduleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by zhaojg on 2017/03/25
 */
@Controller
@RequestMapping("/demoCollection")
public class DemoCollectionController extends BaseController<ExampleBean> {
    private static final Logger LOG = LoggerFactory.getLogger(DemoCollectionController.class);
    private static final String PATH_BASIC="basic";

    @RequestMapping("/demoCollection.do")
    public ModelAndView demoCollection(HttpServletRequest request, HttpServletResponse response, ExampleBean bean){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(ExampleBeanController.VIEW_EXAMPLE+"/"+PATH_BASIC+"/demoCollection");
        return  modelAndView;
    }

    @RequestMapping("/simpleQuartz.do")
    public ModelAndView simpleQuartz(){
        ModelAndView modelAndView = new ModelAndView();
        Map map = PlatformJsonUtils.stringToMap("{}");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY,1);

        try {
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(60).withRepeatCount(6);
            TgwQuartzUtils.createSimpleQuartzJob(DemoQuartzJob2.class,"demo_simple_quartz","demo_simple_quartz",simpleScheduleBuilder,new Date(),calendar.getTime());

            map.put("success",true);
            map.put("msg","创建定时任务成功！");
        } catch (Exception e) {
            map.put("success",false);
            map.put("msg","创建定时任务失败！"+e.getMessage());
        }

        modelAndView.addObject( PlatformSysConstant.JSONSTR, PlatformJsonUtils.toJsonString(map) );
        modelAndView.setViewName( this.VIEW_JSON );
        return  modelAndView;
    }

    @RequestMapping("/cronQuartz.do")
    public ModelAndView cronQuartz(){
        ModelAndView modelAndView = new ModelAndView();
        Map map = PlatformJsonUtils.stringToMap("{}");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,2);

        try {
            TgwQuartzUtils.createCronQuartzJob(DemoQuartzJob3.class,"demo_cron_quartz","demo_cron_quartz","0 0 * * * ?",new Date(),calendar.getTime());

            map.put("success",true);
            map.put("msg","创建定时任务成功！");
        } catch (Exception e) {
            map.put("success",false);
            map.put("msg","创建定时任务失败！"+e.getMessage());
        }

        modelAndView.addObject( PlatformSysConstant.JSONSTR, PlatformJsonUtils.toJsonString(map) );
        modelAndView.setViewName( this.VIEW_JSON );
        return  modelAndView;
    }
}
