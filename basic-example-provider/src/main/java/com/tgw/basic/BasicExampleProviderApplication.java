package com.tgw.basic;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDubbo
@MapperScan(basePackages = "com.tgw.**.dao")
@ComponentScan(basePackages={"com.**"})
public class BasicExampleProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicExampleProviderApplication.class, args);
	}

}
