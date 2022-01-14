package com.langsin.oa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = {"com.langsin.oa.mapper"})

@SpringBootApplication
public class CommonManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonManageApplication.class, args);
	}

}
