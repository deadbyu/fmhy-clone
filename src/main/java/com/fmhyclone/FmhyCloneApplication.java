package com.fmhyclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FmhyCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(FmhyCloneApplication.class, args);
	}

}
