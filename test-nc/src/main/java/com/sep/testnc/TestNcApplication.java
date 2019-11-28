package com.sep.testnc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestNcApplication {

	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.trustStore", "src/main/resources/test.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "testpass");
		SpringApplication.run(TestNcApplication.class, args);
	}
	
}
