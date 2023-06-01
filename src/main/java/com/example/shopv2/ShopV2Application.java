package com.example.shopv2;

import com.example.shopv2.repository.RecipesRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class ShopV2Application{


	public static void main(String[] args) {
		SpringApplication.run(ShopV2Application.class, args);
	}


}
