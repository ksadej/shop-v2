package com.example.shopv2.service;

import com.example.shopv2.mapper.OrdersListMapper;
import com.example.shopv2.mapper.OrdersMapper;
import com.example.shopv2.model.Orders;
import com.example.shopv2.model.OrdersList;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.repository.OrdersListRepository;
import com.example.shopv2.repository.OrdersRepository;
import com.example.shopv2.repository.ShipmentRepository;
import com.example.shopv2.service.dto.OrdersDTO;
import com.example.shopv2.service.dto.OrdersListDTO;
import com.example.shopv2.service.user.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UserLogService userLogService;
    private final BasketService basketService;
    private final ShipmentRepository shipmentRepository;
    private final OrdersListRepository ordersListRepository;
    private OrdersMapper ordersMapper = new OrdersMapper();

    @Autowired
    public OrdersService(OrdersRepository orderRepository, UserLogService userLogService,
                         BasketService basketService, ShipmentRepository shipmentRepository,
                         OrdersListRepository ordersListRepository) {
        this.ordersRepository = orderRepository;
        this.userLogService = userLogService;
        this.basketService = basketService;
        this.shipmentRepository = shipmentRepository;

        this.ordersListRepository = ordersListRepository;
    }

//    private void clearOrderCart(OrdersDTO ordersDTO){
//        Orders orders = ordersMapper.requestToEntity(ordersDTO);
//        ordersRepository.deleteById(orders.getId());
//    }

    public List<OrdersDTO> getOrdersByUser(){
        UserEntity user = userLogService.loggedUser();
        List<Orders> allByUserEntity = ordersRepository.findAllByUserEntity(user);
        return allByUserEntity.stream()
                .map(x -> ordersMapper.entityToResponse(x))
                .collect(Collectors.toList());
    }

    public List<Orders> getAll(){
        UserEntity user = userLogService.loggedUser();
        return ordersRepository.findAllByUserEntity(user);
    }

    public Double sumTotalValue(){
        UserEntity user = userLogService.loggedUser();
        List<Long> collect = ordersRepository.findAllByUserEntity(user)
                .stream()
                .map(x -> x.getBasketId())
                .collect(Collectors.toList());

        Double basketTotal = basketService.sumPriceByBasketId(collect);
        return basketTotal;
    }

    public Double sumTotalValue2(){
        return null;
    }

    public void createSummary(OrdersDTO ordersDTO){
        UserEntity user = userLogService.loggedUser();
        System.out.println(ordersDTO);

        Orders orders = ordersMapper.requestToEntity(ordersDTO, user);
        List<OrdersList> orderRow = ordersDTO.getOrdersLists()
                .stream()
                .map(OrdersListMapper::mapToRow2)
                .collect(Collectors.toList());

        orders.setOrdersLists(orderRow);

        ordersRepository.save(orders);
    }

    public Double priceDiscount(){
        return null;
    }
}
