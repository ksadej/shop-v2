package com.example.shopv2.service.order.orderstatus;

import com.example.shopv2.model.Orders;
import com.example.shopv2.model.OrdersStatus;
import com.example.shopv2.model.enums.OrderStatusType;
import com.example.shopv2.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class PreparationService extends StatusValidator{

    private final OrdersRepository ordersRepository;

    @Autowired
    public PreparationService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public Orders sendStatus(Orders orders) {

        boolean contains = orders.getOrdersStatusList().contains(OrderStatusType.PAID);
        if(contains){
            //send info about order preparation
        }

        OrdersStatus newStatus = OrdersStatus
                .builder()
                .addedBy("System")
                .statusAddedAt(OffsetDateTime.now())
                .ordersStatus(OrderStatusType.IN_PREPARATION)
                .build();

        Orders order = ordersRepository.findById(orders.getId()).get();
        order.getOrdersStatusList().add(newStatus);
        ordersRepository.save(order);
        return orders;
    }

    @Override
    public Orders confirmStatus(Orders orders) {
        boolean contains = orders.getOrdersStatusList().contains(OrderStatusType.IN_PREPARATION);
        if(contains){
            //send info about order preparation
        }

        OrdersStatus newStatus = OrdersStatus
                .builder()
                .addedBy("System")
                .statusAddedAt(OffsetDateTime.now())
                .ordersStatus(OrderStatusType.DELIVERED)
                .build();

        Orders order = ordersRepository.findById(orders.getId()).get();
        order.getOrdersStatusList().add(newStatus);
        ordersRepository.save(order);
        next.confirmStatus(orders);
        return orders;
    }
}
