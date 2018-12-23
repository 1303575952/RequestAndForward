package com.emep.changzhi.analyse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class RequestAndForwardApplication {

	public static void main(String[] args) {

		SpringApplication.run(RequestAndForwardApplication.class, args);
		log.error("***************** SPRING BOOT START UP SUCCESS ******************");
	}
}
