package com.es.reportverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@RestController
@EnableSwagger2
public class ReportverseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportverseApplication.class, args);
	}
}
