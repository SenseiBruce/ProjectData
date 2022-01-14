package com.example.springjpa.ProjectData;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan({"com.example.springjpa"})
@EnableJpaRepositories("com.example.springjpa.repository") 
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.example.springjpa.entity"})
@RestController
//@EnableOAuth2Client
public class ProjectDataApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectDataApplication.class, args);
		
	}

	
}
