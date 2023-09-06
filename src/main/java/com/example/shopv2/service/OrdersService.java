package com.example.shopv2.service;

import com.example.shopv2.mapper.OrdersMapper;
import com.example.shopv2.model.Orders;
import com.example.shopv2.model.Shipment;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.model.enums.ShipmentType;
import com.example.shopv2.repository.OrdersRepository;
import com.example.shopv2.repository.ShipmentRepository;
import com.example.shopv2.service.dto.OrdersDTO;
import com.example.shopv2.service.dto.OrdersSummaryDTO;
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
    private final ShipmentRepository shipmentRepository;
    private OrdersMapper ordersMapper = new OrdersMapper();

    @Autowired
    public OrdersService(OrdersRepository orderRepository, UserLogService userLogService,
                         BasketService basketService, ShipmentRepository shipmentRepository) {
        this.ordersRepository = orderRepository;
        this.userLogService = userLogService;
        this.basketService = basketService;
        this.shipmentRepository = shipmentRepository;
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

    public Double sumTotalValue(Shipment shipment){
        UserEntity user = userLogService.loggedUser();
        List<Long> collect = ordersRepository.findAllByUserEntity(user)
                .stream()
                .map(x -> x.getBasketId())
                .collect(Collectors.toList());

        Double basketTotal = basketService.sumPriceByBasketId(collect);
        return basketTotal+ shipment.getPrice();
    }

    public void createSummary(OrdersDTO ordersDTO){
        UserEntity user = userLogService.loggedUser();
        Shipment shipmentType = shipmentRepository.findByShipmentType(ShipmentType.valueOf(ordersDTO.getShipmentType()));
        ordersDTO.setTotalValue(sumTotalValue(shipmentType));
        Orders orders = ordersMapper.requestToEntity(ordersDTO, user);
        ordersRepository.save(orders);

    }

    public Double priceDiscount(){
        return null;
    }
}
