package com.virtuallab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class VirtualLabApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(VirtualLabApplication.class, args);
	}

}
