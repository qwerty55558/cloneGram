package com.jsy.clonegram;

import com.jsy.clonegram.config.mybatis.MyBatisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(MyBatisConfig.class)
@SpringBootApplication
public class ClonegramApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClonegramApplication.class, args);
	}


}
