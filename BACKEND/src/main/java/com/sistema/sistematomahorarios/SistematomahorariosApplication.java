package com.sistema.sistematomahorarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SistematomahorariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistematomahorariosApplication.class, args);
	}

}
