package com.sample.encryptApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@EnableFeignClients
@EnableConfigurationProperties
@SpringBootApplication
public class EncryptApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncryptApplication.class, args);
	}

}
