package com.example.expensemanager;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class ExpensemanagerApplication {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		// Additional configuration if required
		return mapper;
	}
	public static void main(String[] args) {
		SpringApplication.run(ExpensemanagerApplication.class, args);
	}

}
