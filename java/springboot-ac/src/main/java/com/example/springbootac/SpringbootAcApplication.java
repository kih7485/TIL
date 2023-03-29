package com.example.springbootac;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootAcApplication {

	@Bean
	ApplicationRunner run(ConditionEvaluationReport report){
		return args -> {
			report.getConditionAndOutcomesBySource().entrySet().stream()
					.filter(co ->co.getValue().isFullMatch())
					.forEach(co -> {
						System.out.println(co.getKey() );
					});
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringbootAcApplication.class, args);
	}

}
