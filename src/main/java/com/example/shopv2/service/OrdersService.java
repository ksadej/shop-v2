package com.example.shopv2.service;

import com.example.shopv2.mapper.OrdersListMapper;
import com.example.shopv2.mapper.OrdersMapper;
import com.example.shopv2.model.Orders;
import com.example.shopv2.model.OrdersList;
import com.example.shopv2.model.Shipment;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.model.enums.ShipmentType;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.repository.OrdersListRepository;
import com.example.shopv2.repository.OrdersRepository;
import com.example.shopv2.repository.ShipmentRepository;
import com.example.shopv2.service.dto.OrdersDTO;
import com.example.shopv2.service.dto.OrdersListDTO;
import com.example.shopv2.service.dto.OrdersSummaryDTO;
import com.example.shopv2.service.user.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UserLogService userLogService;
    private final BasketService basketService;
    private final ShipmentRepository shipmentRepository;
    private final OrdersListRepository ordersListRepository;
    private final IngredientRepository ingredientRepository;
    private OrdersMapper ordersMapper = new OrdersMapper();

    @Autowired
    public OrdersService(OrdersRepository orderRepository, UserLogService userLogService,
                         BasketService basketService, ShipmentRepository shipmentRepository,
                         OrdersListRepository ordersListRepository, IngredientRepository ingredientRepository) {
        this.ordersRepository = orderRepository;
        this.userLogService = userLogService;
        this.basketService = basketService;
        this.shipmentRepository = shipmentRepository;

        this.ordersListRepository = ordersListRepository;
        this.ingredientRepository = ingredientRepository;
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

    public List<OrdersList> ordersLists(){
        UserEntity user = userLogService.loggedUser();

        return ordersRepository
                .findAllByUserEntity(user)
                .stream()
                .flatMap(x ->x.getOrdersLists().stream())
                .toList();
    }

    public List<OrdersListDTO> getOrdersList(){
        return ordersLists()
                .stream()
                .map(OrdersListMapper::entityToRequest)
                .collect(Collectors.toList());
    }

    public Double sumTotalValue(Shipment shipment){
        List<Long> ordersLists = ordersLists().stream()
                .map(x -> x.getIngredientId())
                .toList();

        Double reduce = ordersLists
                .stream()
                .flatMap(IngredientRepository -> ingredientRepository.findById(IngredientRepository)
                        .stream()
                        .map(x -> x.getAmount()))
                .reduce(Double::sum).get();

        return reduce + shipment.getPrice();
    }

    public OrdersSummaryDTO createSummary(OrdersDTO ordersDTO){
        UserEntity user = userLogService.loggedUser();
        Orders orders = ordersMapper.requestToEntity(ordersDTO, user);

        List<OrdersList> orderRow = ordersDTO.getOrdersLists()
                .stream()
                .map(OrdersListMapper::mapToRow2)
                .collect(Collectors.toList());

        orders.setOrdersLists(orderRow);

        Orders newOrder = ordersRepository.save(orders);
        Shipment shipmentType = shipmentRepository.findByShipmentType(ShipmentType.valueOf(ordersDTO.getShipmentType()));

        OrdersSummaryDTO ordersSummaryDTO = OrdersSummaryDTO
                .builder()
                .shipmentType(String.valueOf(newOrder.getShipmentType()))
                .ordersStatus(String.valueOf(newOrder.getOrdersStatus()))
                .placeDate(OffsetDateTime.now())
                .totalValue(sumTotalValue(shipmentType))
                .build();

        return ordersSummaryDTO;
    }

    public Double priceDiscount(){
        return null;
    }
}
