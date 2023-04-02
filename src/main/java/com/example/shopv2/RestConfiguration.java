package com.example.shopv2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class RestConfiguration {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean(name="productHeaders")
    public HttpHeaders productHeaders(){
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-RapidAPI-Key", "9375d74b9dmsh8d296840872c4b8p1e3b6djsn62b60781207f");
        headers.set("X-RapidAPI-Host", "nutrition-by-api-ninjas.p.rapidapi.com");
        return headers;
    }

    @Bean(name ="recipesHeaders")
    public HttpHeaders recipesHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("x-api-key", "9016e19523d14535a6f734ee0c50c4ab");
        return httpHeaders;
    }
}
