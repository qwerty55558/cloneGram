package com.jsy.clonegram;

import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.FollowService;
import com.jsy.clonegram.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class ClonegramApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClonegramApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(UserRepository userRepository, RedisService redisService) throws Exception {
		return (String[] args) -> {
			userRepository.findAll().forEach(c -> {
				Long id = c.getId();
				redisService.setFollower(id, (long) userRepository.findFollowersByUserId(id).size());
				redisService.setFollowing(id, (long) userRepository.findFollowingsByUserId(id).size());
			});
		};
	}

}
