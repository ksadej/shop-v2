package com.example.shopv2.service.order;

import com.example.shopv2.model.Orders;
import com.example.shopv2.model.OrdersStatus;
import com.example.shopv2.model.enums.OrderStatusType;
import com.example.shopv2.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Component
public class DeliveryService  {
    private final OrdersRepository ordersRepository;

    @Autowired
    public DeliveryService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public void sendStatus(Orders orders) {

        boolean contains = orders.getOrdersStatusList().contains(OrderStatusType.COMPLETED);
        if(contains){
            //send info about order preparation
        }

        OrdersStatus newStatus = OrdersStatus
                .builder()
                .addedBy("System")
                .statusAddedAt(OffsetDateTime.now())
                .ordersStatus(OrderStatusType.WAITING_FOR_DELIVERY)
                .build();

        Orders order = ordersRepository.findById(orders.getId()).get();
        order.getOrdersStatusList().add(newStatus);
        ordersRepository.save(order);

    }

    public void confirmStatus(Orders orders) {
        boolean contains = orders.getOrdersStatusList().contains(OrderStatusType.WAITING_FOR_DELIVERY);
        if(contains){
            //send info about order preparation
        }

        OrdersStatus newStatus = OrdersStatus
                .builder()
                .addedBy("System")
                .statusAddedAt(OffsetDateTime.now())
                .ordersStatus(OrderStatusType.COMPLETED)
                .build();

        Orders order = ordersRepository.findById(orders.getId()).get();
        order.getOrdersStatusList().add(newStatus);
        ordersRepository.save(order);

    }
}
