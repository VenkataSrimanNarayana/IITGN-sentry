package com.iitgn.entryexit;

import com.iitgn.entryexit.entities.Role;
import com.iitgn.entryexit.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.iitgn.entryexit.repositories.RecordRepository;


@SpringBootApplication
@RequiredArgsConstructor
public class EntryExitApplication {

	private final RoleRepository roleRepository;

	private final RecordRepository recordRepository;

	public static void main(String[] args) {
		SpringApplication.run(EntryExitApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return (args) -> {
			roleRepository.save(new Role(2, "ROLE_USER"));
			roleRepository.save(new Role(1, "ROLE_ADMIN"));
			roleRepository.save(new Role(3, "ROLE_MANAGER"));
		};
	}
}
