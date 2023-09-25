package com.example.shopv2.service.order.orderpreparation;

import com.example.shopv2.model.Orders;
import com.example.shopv2.repository.OrdersRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PreparationService{

    private final OrdersRepository ordersRepository;

    @Autowired
    public PreparationService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public void sendDataToOrderPreparation(Orders orders) {
        final String uri = "https://crudcrud.com/api/f80436fe51b74cfba8350d3f76d0e58e/preparation";
        // create request body
        JSONObject request = new JSONObject();
        request.put("orderList", orders.getOrdersLists().get(0));

        // set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

        // send request and parse result
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> loginResponse = restTemplate
                .exchange(uri, HttpMethod.POST, entity, String.class);
        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            JSONObject userJson = new JSONObject(loginResponse.getBody());
        } else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {

        }
    }

    public void confirmStatus(Orders orders) {

    }
}
