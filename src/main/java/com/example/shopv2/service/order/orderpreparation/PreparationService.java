package com.example.shopv2.service.order.orderpreparation;

import com.example.shopv2.mapper.OrdersMapper;
import com.example.shopv2.model.Orders;
import com.example.shopv2.model.enums.OrderStatusType;
import com.example.shopv2.repository.OrdersRepository;
import com.example.shopv2.service.dto.OrdersDTO;
import com.example.shopv2.service.order.OrdersHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PreparationService extends OrdersHelper {

    @Autowired
    private RestTemplate restTemplate;
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    private final HttpHeaders headers;
    private final static Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Autowired
    public PreparationService(OrdersRepository ordersRepository, OrdersMapper ordersMapper, HttpHeaders headers) {
        super(headers, ordersRepository);
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
        this.headers = headers;
    }

    String URL = "https://crudcrud.com/api/7793bfe0e44d466aa53aedb7e3b7cf87/order";
    private final String ACCESS_TOKEN = null;

    @Override
    protected HttpHeaders header(String accessToken) {
        return super.header(accessToken);
    }

    @Override
    protected Orders updateStatus(OrderStatusType orderType, Long orderId) {
        return super.updateStatus(orderType, orderId);
    }

    public String sendTaskToWarehouse(Orders orders) throws JsonProcessingException {
        String auth = null;
        OrdersDTO ordersDTO = ordersMapper.orderForWarehouse(orders);
        String json = new ObjectMapper().writeValueAsString(ordersDTO);
        HttpEntity<String> entity = new HttpEntity<>(json, header(auth));

        ResponseEntity<String> response = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                entity,
                String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            LOGGER.log(Level.INFO, "ORDER SENT TO WAREHOUSE");
            updateStatus(OrderStatusType.IN_PREPARATION, orders.getId());
            return response.getBody();
        } else{
            LOGGER.log(Level.INFO, "FAILED TO SENT ORDER TO WAREHOUSE");
            return String.valueOf(response.getStatusCode());
        }
    }

    public void confirmStatus(Orders orders) {
        updateStatus(OrderStatusType.COMPLETED, orders.getId());
    }
}
