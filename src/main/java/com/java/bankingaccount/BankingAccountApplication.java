package com.java.bankingaccount;

import com.java.bankingaccount.dto.UserDto;
import com.java.bankingaccount.services.auth.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static com.java.bankingaccount.enums.Roles.*;

@SpringBootApplication
@EnableJpaAuditing
public class BankingAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingAccountApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = UserDto.builder()
					.firstName("Admin")
					.lastName("Admin")
					.email("admin@mail.com")
					.password("password")
					.roles(ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());

			var manager = UserDto.builder()
					.firstName("Admin")
					.lastName("Admin")
					.email("manager@mail.com")
					.password("password")
					.roles(USER)
					.build();
			System.out.println("Manager token: " + service.register(manager).getAccessToken());

		};
	}

}
