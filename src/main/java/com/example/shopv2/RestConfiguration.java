package com.example.shopv2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class RestConfiguration {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean(name ="recipesHeaders")
    public HttpHeaders recipesHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("x-api-key", "a2a1c3482a2f45819ef4c1dbb4e1300f");
        return httpHeaders;
    }
}
