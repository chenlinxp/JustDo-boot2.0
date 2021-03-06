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
public class JustdoBootApplication {
	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(JustdoBootApplication.class, args);
		System.out.println("ヾ(◍°∇°◍)ﾉﾞ   justdo启动成功      ヾ(◍°∇°◍)ﾉﾞ");
	}
}
