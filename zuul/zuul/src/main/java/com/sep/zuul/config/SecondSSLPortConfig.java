package com.sep.zuul.config;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class SecondSSLPortConfig{
    
   
    @Bean
    public ServletWebServerFactory servletContainer(@Value("${http.port}") int httpPort) {

	    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
	    tomcat.addAdditionalTomcatConnectors(createSslConnector(httpPort));
	    return tomcat;

    }
    
    private Connector createSslConnector(int httpPort) {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        try {
            File keystore = new ClassPathResource("zuulkeystore.jks").getFile();
            File truststore = new ClassPathResource("zuulkeystore.jks").getFile();
            connector.setScheme("https");
            connector.setSecure(true);
            connector.setPort(httpPort);
            protocol.setSSLEnabled(true);
            protocol.setClientAuth("require");
            protocol.setKeystoreFile(keystore.getAbsolutePath());
            protocol.setKeystorePass("zuulpass");
            protocol.setTruststoreFile(truststore.getAbsolutePath());
            protocol.setTruststorePass("zuulpass");
            protocol.setKeyAlias("zuul");
            return connector;
        }
        catch (IOException ex) {
            throw new IllegalStateException("can't access keystore: [" + "keystore"
                    + "] or truststore: [" + "keystore" + "]", ex);
        }
    }

}
