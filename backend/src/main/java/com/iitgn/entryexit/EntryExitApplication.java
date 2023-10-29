package com.iitgn.entryexit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.iitgn.entryexit.spring.utils.ConversionUtility.convertJsonDateToLocalDate;
import static com.iitgn.entryexit.spring.utils.ConversionUtility.convertJsonTimeToLocalTime;

@SpringBootApplication
public class EntryExitApplication {
	public static void main(String[] args) {
		SpringApplication.run(EntryExitApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return (args) -> {
			System.out.println("I am Running Successfully");
			String jsonTime = "22:15:26";
			String jsonDate = "2023-10-29";

			LocalTime localTime = convertJsonTimeToLocalTime(jsonTime);
			LocalDate localDate = convertJsonDateToLocalDate(jsonDate);
		};
	}
}
