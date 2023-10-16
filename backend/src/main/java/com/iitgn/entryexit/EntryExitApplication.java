package com.iitgn.entryexit;

import com.iitgn.entryexit.entities.Role;
import com.iitgn.entryexit.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class EntryExitApplication {

	private final RoleRepository roleRepository;

	public EntryExitApplication(RoleRepository roleRepository){
		this.roleRepository = roleRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(EntryExitApplication.class, args);
	}


	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			roleRepository.save(new Role(2, "ROLE_USER"));
			roleRepository.save(new Role(1, "ROLE_ADMIN"));
		};
	}

}
