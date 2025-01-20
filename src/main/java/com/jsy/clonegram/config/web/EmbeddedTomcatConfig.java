package com.jsy.clonegram.config.web;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 *  톰캣 설정 config
 *  특정 특수문자를 사용할 수 있도록 만듦
 */

//@Configuration
public class EmbeddedTomcatConfig implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers(connector -> {
            connector.setProperty("relaxedQueryChars", "\"<>[\\\\]^`{|}\"");
        });
    }
}
