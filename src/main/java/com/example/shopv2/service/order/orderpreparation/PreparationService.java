package com.example.shopv2.service.order.orderpreparation;

import com.example.shopv2.mapper.OrdersMapper;
import com.example.shopv2.model.Orders;
import com.example.shopv2.repository.OrdersRepository;
import com.example.shopv2.service.dto.OrdersDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PreparationService{

    @Autowired
    private RestTemplate restTemplate;
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    private final static Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Autowired
    public PreparationService(OrdersRepository ordersRepository, OrdersMapper ordersMapper) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
    }

    String URL = "";
    private final String ACCESS_TOKEN = null;

    public HttpHeaders warehouseHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ACCESS_TOKEN);
        headers.add("Content-Type", "application/json");
        return headers;
    }
    public String sendTaskToWarehouse(Orders orders) throws JsonProcessingException {
        OrdersDTO ordersDTO = ordersMapper.orderForWarehouse(orders);
        String json = new ObjectMapper().writeValueAsString(ordersDTO);
        HttpEntity<String> entity = new HttpEntity<>(json, warehouseHeader());

        ResponseEntity<String> response = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                entity,
                String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            LOGGER.log(Level.INFO, "ORDER SENT TO WAREHOUSE");
            return response.getBody();
        } else{
            LOGGER.log(Level.INFO, "FAILED TO SENT ORDER TO WAREHOUSE");
            return String.valueOf(response.getStatusCode());
        }
    }

    public void confirmStatus(Orders orders) {

    }
}
