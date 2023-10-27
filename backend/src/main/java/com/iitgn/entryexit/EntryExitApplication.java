package com.iitgn.entryexit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class EntryExitApplication {
	public static void main(String[] args) {
		SpringApplication.run(EntryExitApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner() {
		return (args) -> {
			System.out.println("I am Running Successfully");
		};
	}
}
