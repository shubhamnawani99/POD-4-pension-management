package com.cts.disbursepension;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This microservice is responsible for verifying pension amount and bank
 * charges
 * 
 * @author Pod-4
 *
 */
@EnableSwagger2
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class PensionDisbursementService {

	public static void main(String[] args) {
		SpringApplication.run(PensionDisbursementService.class, args);
	}

}
