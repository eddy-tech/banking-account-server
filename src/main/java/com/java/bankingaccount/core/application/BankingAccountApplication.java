package com.java.bankingaccount.core.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class BankingAccountApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankingAccountApplication.class, args);
	}
}
