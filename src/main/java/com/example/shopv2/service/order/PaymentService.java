package com.example.shopv2.service.order;

import com.example.shopv2.model.Orders;
import com.example.shopv2.model.OrdersStatus;
import com.example.shopv2.model.enums.OrderStatusType;
import com.example.shopv2.repository.OrdersRepository;
import com.example.shopv2.service.order.orderstatus.StatusValidator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.time.OffsetDateTime;

@Service
public class PaymentService{
    private final OrdersRepository ordersRepository;

    @Autowired
    public PaymentService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public void sendPaymentData(Orders orders) {

        final String uri = "https://crudcrud.com/api/f80436fe51b74cfba8350d3f76d0e58e/payment";
        // create request body
        JSONObject request = new JSONObject();
        request.put("paymentType", orders.getPayment().getName());

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
        //post verification payment
        System.out.println("payment confirm");


        boolean contains = orders.getOrdersStatusList().contains(OrderStatusType.PROCESSING);
        if(contains){
            OrdersStatus newStatus = OrdersStatus
                    .builder()
                    .addedBy("System")
                    .statusAddedAt(OffsetDateTime.now())
                    .ordersStatus(OrderStatusType.PAID)
                    .build();

            Orders order = ordersRepository.findById(orders.getId()).get();
            order.getOrdersStatusList().add(newStatus);
            ordersRepository.save(order);
        }

    }
}
