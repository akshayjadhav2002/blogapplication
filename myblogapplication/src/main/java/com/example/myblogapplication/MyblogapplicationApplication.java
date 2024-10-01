package com.example.myblogapplication;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class MyblogapplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyblogapplicationApplication.class, args);
	}

	@Bean
	public ModelMapper modalMapper(){
	return new ModelMapper();
	}
}
