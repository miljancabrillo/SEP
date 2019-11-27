package com.sep.paypal;

import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;

@EnableEurekaClient
@SpringBootApplication
public class PaypalApplication {

	@Bean
	public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
		DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
		System.setProperty("javax.net.ssl.keyStore", "src/main/resources/paypalkeystore.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "paypalpass");
		System.setProperty("javax.net.ssl.trustStore", "src/main/resources/paypalkeystore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "paypalpass");
		EurekaJerseyClientBuilder builder = new EurekaJerseyClientBuilder();
		builder.withClientName("paypal");
		builder.withSystemSSLConfiguration();
		builder.withMaxTotalConnections(10);
		builder.withMaxConnectionsPerHost(10);
		args.setEurekaJerseyClient(builder.build());
		return args;
	}
	
	public static void main(String[] args) {
		System.setProperty("KEY_STORE_CLASSPATH", "src/main/resources/paypalkeystore.jks");
		System.setProperty("KEY_STORE_PASSWORD", "paypalpass");
		System.setProperty("KEY_ALIAS", "paypal");
		System.setProperty("EUREKA_INSTANCE_HOSTNAME", "localhost");
		System.setProperty("CLIENT_SERVICEURL_DEFAULTZONE", "https://localhost:8761/eureka/");
		SpringApplication.run(PaypalApplication.class, args);
	}

}
