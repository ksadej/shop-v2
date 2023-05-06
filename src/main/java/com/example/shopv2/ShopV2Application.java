package com.example.shopv2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication(exclude = {
		SecurityAutoConfiguration.class
})
public class ShopV2Application implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(ShopV2Application.class, args);
	}

	@Override
	public void run(String...args) throws Exception {

	}
}
