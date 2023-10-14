package com.example.shopv2.service.order;

import com.example.shopv2.mapper.OrdersMapper;
import com.example.shopv2.model.Orders;
import com.example.shopv2.model.OrdersList;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.model.enums.OrderStatusType;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.repository.OrdersListRepository;
import com.example.shopv2.repository.OrdersRepository;
import com.example.shopv2.repository.ShipmentRepository;
import com.example.shopv2.service.BasketService;
import com.example.shopv2.service.dto.OrdersDTO;
import com.example.shopv2.service.order.payment.PaypalService;
import com.example.shopv2.service.user.UserLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class OrdersServiceTest {

    private OrdersService ordersService;
    private OrdersRepository ordersRepository;
    private UserLogService userLogService;
    private BasketService basketService;
    private ShipmentRepository shipmentRepository;
    private OrdersListRepository ordersListRepository;
    private IngredientRepository ingredientRepository;
    private PaypalService paypalService;
    private OrdersMapper ordersMapper = new OrdersMapper();

    @BeforeEach
    public void setup(){
        ordersRepository = Mockito.mock(OrdersRepository.class);
        userLogService = Mockito.mock(UserLogService.class);
        basketService = Mockito.mock(BasketService.class);
        shipmentRepository = Mockito.mock(ShipmentRepository.class);
        ordersListRepository = Mockito.mock(OrdersListRepository.class);
        ingredientRepository = Mockito.mock(IngredientRepository.class);
        paypalService = Mockito.mock(PaypalService.class);
        ordersService = new OrdersService(ordersRepository, userLogService,
            basketService, shipmentRepository, ordersListRepository, ingredientRepository, paypalService);
    }

    @Test
    void ordersLists_checkReturnedData(){
        //given
        UserEntity user = initUser();

        OrdersList ordersList = OrdersList
                .builder()
                .ingredientId(22L)
                .build();
        List<OrdersList> ordersLists = new ArrayList<>();
        ordersLists.add(ordersList);
        Orders orders = Orders
                .builder()
                .userEntity(user)
                .basketId(44L)
                .ordersLists(ordersLists)
                .build();

        List<Orders> expectedOrders = new ArrayList<>();
        expectedOrders.add(orders);

        Mockito.when(userLogService.loggedUser()).thenReturn(user);
        Mockito.when(ordersRepository.findAllByUserEntity(user)).thenReturn(expectedOrders);

        //when
        List<OrdersList> actualOrders = ordersService.ordersLists();

        //then
        assertAll(
                ()->assertNotSame(expectedOrders,actualOrders),
                ()->assertInstanceOf(List.class, expectedOrders),
                ()->assertInstanceOf(List.class, actualOrders),
                ()->assertThat(actualOrders.get(0)).isExactlyInstanceOf(OrdersList.class)
        );
    }

    @Test
    void getOrders_checkIfReturnedDataIsMapped(){
        //given
        UserEntity user = initUser();
        Mockito.when(userLogService.loggedUser()).thenReturn(user);
        List<Orders> actualList = new ArrayList<>();
        actualList.add(initOrders());

        Mockito.when(ordersRepository.findAllByUserEntityAndOrdersStatus(user, OrderStatusType.NEW)).thenReturn(actualList);

        //when
        List<OrdersDTO> expectedLis = ordersService.getOrders();

        //then
        assertAll(
                ()->assertNotSame(actualList,expectedLis),
                ()->assertThat(expectedLis.get(0)).isExactlyInstanceOf(OrdersDTO.class)
        );

    }

    private UserEntity initUser(){
        return UserEntity
                .builder()
                .username("test_username")
                .password("test_password")
                .build();
    }

    private Orders initOrders(){
        OrdersList ordersList = OrdersList
                .builder()
                .ingredientId(22L)
                .build();
        List<OrdersList> ordersLists = new ArrayList<>();
        ordersLists.add(ordersList);

        return Orders
                .builder()
                .basketId(44L)
                .ordersLists(ordersLists)
                .build();

    }
}