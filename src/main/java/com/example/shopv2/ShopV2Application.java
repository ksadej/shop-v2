package com.example.shopv2;

import com.example.shopv2.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class ShopV2Application {



	public static void main(String[] args) {
		SpringApplication.run(ShopV2Application.class, args);


	}
}
