package kih.helloboot;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HellobootApplication {

	public static void main(String[] args) throws LifecycleException {
		ServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
		WebServer webServer = tomcatServletWebServerFactory.getWebServer(servletContext -> {
			servletContext.addServlet("frontController", new HttpServlet() {

				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					if(req.getRequestURI().equals("/hello")){
						String name = req.getParameter("name");

						resp.setStatus(HttpStatus.OK.value());
						resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
						resp.getWriter().println("Hello Servlet, "+ name);

					}else{
						resp.setStatus(HttpStatus.NOT_FOUND.value());
					}
				}
			}).addMapping("/*");
		});
		webServer.start();

	}

}
