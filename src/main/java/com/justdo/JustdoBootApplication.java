package com.justdo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@ServletComponentScan
@EnableAutoConfiguration
@SpringBootApplication
//@MapperScan({"com.justdo.modules.*.mapper" , "com.justdo.system.*.mapper"})
public class JustdoBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(JustdoBootApplication.class, args);
	}
}
