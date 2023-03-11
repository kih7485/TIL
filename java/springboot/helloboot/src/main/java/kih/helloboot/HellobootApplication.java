package kih.helloboot;

import org.apache.catalina.LifecycleException;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class HellobootApplication {
	@Bean
	public HelloController helloController(HelloService helloService){
		return new HelloController(helloService);
	}

	@Bean
	public HelloService helloService(){
		return new SimpleHelloService();
	}

	public static void main(String[] args) throws LifecycleException {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext(){
//		GenericWebApplicationContext applicationContext = new GenericWebApplicationContext(){
			@Override
			protected void onRefresh() {
				super.onRefresh();

				ServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
				WebServer webServer = tomcatServletWebServerFactory.getWebServer(servletContext -> {
					servletContext.addServlet("dispatcherServlet",
							new DispatcherServlet(this)
					).addMapping("/*");
				});
				webServer.start();
			}
		};
		applicationContext.register(HellobootApplication.class);
		applicationContext.refresh();



	}

}
