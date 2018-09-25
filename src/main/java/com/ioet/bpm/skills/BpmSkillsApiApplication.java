package com.ioet.bpm.skills;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BpmSkillsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BpmSkillsApiApplication.class, args);
	}
}
