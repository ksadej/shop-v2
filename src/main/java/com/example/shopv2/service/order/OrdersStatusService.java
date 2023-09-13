package com.example.shopv2.service.order;

import com.example.shopv2.mapper.OrdersMapper;
import com.example.shopv2.model.Orders;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.repository.OrdersRepository;
import com.example.shopv2.repository.OrdersStatusRepository;
import com.example.shopv2.service.dto.OrdersDTO;
import com.example.shopv2.service.order.orderstatus.DeliveryService;
import com.example.shopv2.service.order.orderstatus.PaymentService;
import com.example.shopv2.service.order.orderstatus.PreparationService;
import com.example.shopv2.service.order.orderstatus.StatusValidator;
import com.example.shopv2.service.user.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersStatusService {

    private final OrdersStatusRepository ordersStatusRepository;
    private final UserLogService userLogService;
    private final OrdersRepository ordersRepository;
    private OrdersMapper ordersMapper = new OrdersMapper();
    @Autowired
    public OrdersStatusService(OrdersStatusRepository ordersStatusRepository, UserLogService userLogService,
                               OrdersRepository ordersRepository) {
        this.ordersStatusRepository = ordersStatusRepository;
        this.userLogService = userLogService;
        this.ordersRepository = ordersRepository;
    }

    public List<OrdersDTO> orderStatus(){
        UserEntity user = userLogService.loggedUser();
        return ordersRepository.findAllByUserEntity(user)
                .stream()
                .map(OrdersMapper -> ordersMapper.entityToRequest(OrdersMapper, user))
                .collect(Collectors.toList());
    }

    public void orderingProcessStarter(Orders orders){
        StatusValidator payment = new PaymentService(ordersRepository);
        StatusValidator preparation = new PreparationService(ordersRepository);
        StatusValidator delivery = new DeliveryService(ordersRepository);
        payment.setNext(preparation);
        preparation.setNext(delivery);

        payment.confirmStatus(orders);
    }
}
