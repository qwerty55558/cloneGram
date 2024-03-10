package com.jsy.clonegram;

import com.jsy.clonegram.config.UserConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(UserConfig.class)
@SpringBootApplication
public class ClonegramApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClonegramApplication.class, args);
	}


}
