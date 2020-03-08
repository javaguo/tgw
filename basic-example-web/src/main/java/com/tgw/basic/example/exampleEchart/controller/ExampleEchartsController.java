package com.tgw.basic.example.exampleEchart.controller;

import com.tgw.basic.example.exampleBean.model.ExampleBean;
import com.tgw.basic.framework.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhaojg on 2017/03/25
 */
@Controller
@RequestMapping("/exampleEcharts")
public class ExampleEchartsController extends BaseController<ExampleBean> {

    @RequestMapping("/echartBar.do")
    public ModelAndView echartBar(HttpServletRequest request, HttpServletResponse response, ExampleBean bean){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("page/manage/example/exampleEcharts/page/echartBar");
        return  modelAndView;
    }
}
