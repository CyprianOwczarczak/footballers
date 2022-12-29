package com.owczarczak.footballers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@EnableJpaRepositories
public class FootballersApplication {
	public static void main(String[] args) {
		SpringApplication.run(FootballersApplication.class, args);
	}
}
