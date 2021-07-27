package com.cts.processPension;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This microservice is responsible to process the valid pension details
 * 
 * @author Shubham Nawani
 *
 */
@SpringBootApplication
@EnableFeignClients
@EnableSwagger2
public class ProcessPensionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessPensionServiceApplication.class, args);
	}

}
