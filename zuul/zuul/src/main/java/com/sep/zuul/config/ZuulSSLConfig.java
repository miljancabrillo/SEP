package com.sep.zuul.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ZuulSSLConfig {
	
	 @Autowired
	 private Environment env;

	 @PostConstruct
	 private void configureSSL() {
	   //set to TLSv1.1 or TLSv1.2
	   System.setProperty("https.protocols", "TLSv1.1");

	   //load the 'javax.net.ssl.trustStore' and
	   //'javax.net.ssl.trustStorePassword' from application.properties
	   System.setProperty("javax.net.ssl.keyStore", "src/main/resources/zuul.jks"); 
	   System.setProperty("javax.net.ssl.keStorePassword",env.getProperty("server.ssl.key-store-password"));
	   
	   
	   System.setProperty("javax.net.ssl.trustStore", "src/main/resources/zuul.jks"); 
	   System.setProperty("javax.net.ssl.trustStorePassword",env.getProperty("server.ssl.key-store-password"));
	   
	  	}

}
