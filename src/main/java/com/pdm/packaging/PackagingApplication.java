package com.pdm.packaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PackagingApplication {

	public static H2Calls h2 = new H2Calls();

	public static void main(String[] args) {
		SpringApplication.run(PackagingApplication.class, args);
		h2.createConnection("./localDB", "root", "default", 0);
		h2.initialize();
	}
}
