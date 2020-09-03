package com.luu.ubas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableOAuth2Client
//@EnableCircuitBreaker
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UbasApplication {

	public static void main(String[] args) {
		SpringApplication.run(UbasApplication.class, args);
	}

}
