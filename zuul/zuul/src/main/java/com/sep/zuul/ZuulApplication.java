package com.sep.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ZuulApplication {
	
	@Bean
	 public RestTemplate template() throws Exception{
		 RestTemplate template = new RestTemplate();
		 return template;
	 }

	public static void main(String[] args) {
		System.setProperty("KEY_STORE_CLASSPATH", "src/main/resources/zuulkeystore.jks");
		System.setProperty("KEY_STORE_PASSWORD", "zuulpass");
		System.setProperty("KEY_ALIAS", "zuul");
		System.setProperty("EUREKA_INSTANCE_HOSTNAME", "localhost");
		System.setProperty("CLIENT_SERVICEURL_DEFAULTZONE", "https://localhost:8761/eureka/");
		System.setProperty("TRUST_STORE_CLASSPATH", "zuulkeystore.jks");
		System.setProperty("TRUST_STORE_PASSWORD", "classpath:zuulpass");
		SpringApplication.run(ZuulApplication.class, args);
	}

}
