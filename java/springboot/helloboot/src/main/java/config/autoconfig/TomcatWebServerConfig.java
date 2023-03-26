package config.autoconfig;

import config.ConditionalMyOnClass;
import config.EnableMyConfigurationProperties;
import config.MyAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.*;

@MyAutoConfiguration
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
@EnableMyConfigurationProperties(ServerProperties.class)
public class TomcatWebServerConfig {

    @Bean("tomcatServletWebServerFactory")
    @ConditionalOnMissingBean
    public ServletWebServerFactory servletWebServerFactory(ServerProperties serverProperties) {
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        serverFactory.setContextPath(serverProperties.getContextPath());
        serverFactory.setPort(serverProperties.getPort());
        return serverFactory;
    }

}
