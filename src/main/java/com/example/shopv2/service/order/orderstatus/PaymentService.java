package com.example.shopv2.service.order.orderstatus;

import com.example.shopv2.model.Orders;
import com.example.shopv2.model.OrdersStatus;
import com.example.shopv2.model.enums.OrderStatusType;
import com.example.shopv2.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.OffsetDateTime;

@Service
public class PaymentService extends StatusValidator{

    private final OrdersRepository ordersRepository;
    private StatusValidator next;

    @Autowired
    public PaymentService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    //dostajemy polecenie z frontu wykonania polecenia zapłaty na podstawie przesłanego obiektu
    @Override
    public Orders sendStatus(Orders orders) {

        boolean contains = orders.getOrdersStatusList().contains(OrderStatusType.NEW);
        if(contains){
            //post order payment
        }

        OrdersStatus newStatus = OrdersStatus
                .builder()
                .addedBy("System")
                .statusAddedAt(OffsetDateTime.now())
                .ordersStatus(OrderStatusType.PROCESSING)
                .build();

        Orders order = ordersRepository.findById(orders.getId()).get();
        order.getOrdersStatusList().add(newStatus);
        ordersRepository.save(order);
        return orders;
    }

    @Override
    @Transactional
    public Orders confirmStatus(Orders orders) {
        //post verification payment


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
        next.confirmStatus(orders);
        return orders;
    }
}
