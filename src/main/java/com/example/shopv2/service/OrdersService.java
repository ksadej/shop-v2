package com.example.shopv2.service;

import com.example.shopv2.mapper.OrdersMapper;
import com.example.shopv2.model.Orders;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.repository.OrdersRepository;
import com.example.shopv2.service.dto.OrdersDTO;
import com.example.shopv2.service.user.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UserLogService userLogService;
    private final BasketService basketService;
    private OrdersMapper ordersMapper = new OrdersMapper();

    @Autowired
    public OrdersService(OrdersRepository orderRepository, UserLogService userLogService, BasketService basketService) {
        this.ordersRepository = orderRepository;
        this.userLogService = userLogService;
        this.basketService = basketService;
    }

    public void saveOrder(OrdersDTO ordersDTO){
        Orders orders = ordersMapper.requestToEntity(ordersDTO);
        UserEntity user = userLogService.loggedUser();
        orders.setUserEntity(user);
        ordersRepository.save(orders);
    }

    private void clearOrderCart(OrdersDTO ordersDTO){
        Orders orders = ordersMapper.requestToEntity(ordersDTO);
        ordersRepository.deleteById(orders.getId());
    }

    public List<OrdersDTO> getOrdersByUser(){
        UserEntity user = userLogService.loggedUser();
        List<Orders> allByUserEntity = ordersRepository.findAllByUserEntity(user);
        return allByUserEntity.stream()
                .map(x -> ordersMapper.entityToResponse(x))
                .collect(Collectors.toList());
    }

    public Double summaryOrder(){
        UserEntity user = userLogService.loggedUser();
        List<Long> collect = ordersRepository.findAllByUserEntity(user)
                .stream()
                .map(x -> x.getBasketId())
                .collect(Collectors.toList());

        Double aDouble = basketService.sumPriceByBasketId(collect);
        return aDouble;
    }

    public Double priceDiscount(){
        return null;
    }
}
