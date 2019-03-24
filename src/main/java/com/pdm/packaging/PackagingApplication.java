package com.pdm.packaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class PackagingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PackagingApplication.class, args);
		H2Calls h2 = new H2Calls();
		h2.createConnection("./localDB", "root", "default", 0);
	}
}
