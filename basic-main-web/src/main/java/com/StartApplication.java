package com;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@Controller
@MapperScan(basePackages = "com.tgw.**.dao")
@ComponentScan(basePackages={"com.**"})
@ServletComponentScan(basePackages ={"com.**"} )
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@EnableWebMvc
@EnableDubbo
@EnableCaching
//@EnableScheduling
public class StartApplication extends SpringBootServletInitializer implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(StartApplication.class);

    public static void main(String[] args) {
//        SpringApplication app = new SpringApplication(StartApplication.class);
//        app.setBanner();
//        app.setLazyInitialization(false);
        SpringApplication.run(StartApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(StartApplication.class);
    }

    public void run(String... args) throws Exception {
        logger.info("服务启动完成!");
    }

    @RequestMapping("/")
    String home() {
        return "redirect:/login/toLogin.do";
    }
}