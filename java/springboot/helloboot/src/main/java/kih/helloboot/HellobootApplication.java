package kih.helloboot;


import config.MySpringBootApplication;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@MySpringBootApplication
public class HellobootApplication {

	@Bean
	ApplicationRunner applicationRunner(Environment env){
		return args -> {
			//환경변수는 application.properties보다 우선순위가 높음.
			String property = env.getProperty("my.name");
			System.out.println("myname: "+property);
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(HellobootApplication.class, args);
	}

}
