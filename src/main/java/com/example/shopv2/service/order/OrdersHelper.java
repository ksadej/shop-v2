package com.example.shopv2.service.order;

import com.example.shopv2.model.Orders;
import com.example.shopv2.model.OrdersStatus;
import com.example.shopv2.model.enums.OrderStatusType;
import com.example.shopv2.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.OffsetDateTime;
import java.util.Optional;

public abstract class OrdersHelper {

    private final HttpHeaders headers;
    private final OrdersRepository ordersRepository;

    @Autowired
    protected OrdersHelper(HttpHeaders headers, OrdersRepository ordersRepository) {
        this.headers = headers;
        this.ordersRepository = ordersRepository;
    }

    protected HttpHeaders header(String accessToken){
        headers.set("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    protected Orders updateStatus(OrderStatusType orderType, Long orderId){
        OrdersStatus ordersStatus = OrdersStatus
                .builder()
                .ordersStatus(orderType)
                .statusAddedAt(OffsetDateTime.now())
                .addedBy("SYSTEM")
                .build();
        Optional<Orders> byId = ordersRepository.findById(orderId);
        Orders orders = byId.get();
        orders.getOrdersStatusList().add(ordersStatus);
        return ordersRepository.save(orders);
    }

    protected Orders updateStatus(OrderStatusType orderType, Long orderId, String transactionId){
        OrdersStatus ordersStatus = OrdersStatus
                .builder()
                .ordersStatus(orderType)
                .statusAddedAt(OffsetDateTime.now())
                .addedBy("SYSTEM")
                .build();
        Optional<Orders> byId = ordersRepository.findById(orderId);
        Orders orders = byId.get();
        orders.getOrdersStatusList().add(ordersStatus);
        return ordersRepository.save(orders);
    }
}
