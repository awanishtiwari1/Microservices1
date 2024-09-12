package com.example.bhartitelconfig_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class bhartitelConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(bhartitelConfigServerApplication.class, args);
	}

}
