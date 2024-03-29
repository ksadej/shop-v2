package com.example.shopv2.service.order;

import com.example.shopv2.mapper.OrdersMapper;
import com.example.shopv2.model.*;
import com.example.shopv2.model.enums.OrderStatusType;
import com.example.shopv2.model.enums.PaymentType;
import com.example.shopv2.model.enums.ShipmentType;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.repository.OrdersListRepository;
import com.example.shopv2.repository.OrdersRepository;
import com.example.shopv2.repository.ShipmentRepository;
import com.example.shopv2.service.BasketService;
import com.example.shopv2.service.dto.OrdersDTO;
import com.example.shopv2.service.dto.OrdersListDTO;
import com.example.shopv2.service.dto.OrdersSummaryDTO;
import com.example.shopv2.service.order.payment.PaypalService;
import com.example.shopv2.service.user.UserLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        List<Orders> expectedOrders = new ArrayList<>();
        expectedOrders.add(initOrders());

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

    @Test
    void sumTotalValue_shouldSumValue(){
        //given
        UserEntity user = initUser();
        Shipment shipment = initShipment();

        Ingredient ingredient = Ingredient
                .builder()
                .amount(22.0)
                .build();
        Optional<Ingredient> opt = Optional.of(ingredient);

        Orders orders1 = initOrders();
        List<Orders> expectedOrders = new ArrayList<>();
        expectedOrders.add(orders1);

        Mockito.when(userLogService.loggedUser()).thenReturn(user);
        Mockito.when(ordersRepository.findAllByUserEntity(user)).thenReturn(expectedOrders);
        Mockito.when(ingredientRepository.findById(Mockito.anyLong())).thenReturn(opt);

        //when
        Double sum = ordersService.sumTotalValue(shipment);

        //then
        assertEquals(44.0, sum);
    }

    @Test
    void orderRow_shouldMapOrdersDTOToOrdersList(){
        //given//when
        List<OrdersList> ordersLists1 = ordersService.orderRow(initOrdersDTO());

        //then
        assertThat(ordersLists1.get(0)).isExactlyInstanceOf(OrdersList.class);
    }

    @Test
    void createSummaryHelper_checkIfReturnedObjectIsOrdersSummaryDTO(){
        //given
        Shipment shipment = initShipment();
        UserEntity user = initUser();

        Ingredient ingredient = Ingredient
                .builder()
                .amount(22.0)
                .build();
        Optional<Ingredient> opt = Optional.of(ingredient);

        Orders orders1 = initOrders();
        List<Orders> expectedOrders = new ArrayList<>();
        expectedOrders.add(orders1);

        Mockito.when(userLogService.loggedUser()).thenReturn(user);
        Mockito.when(shipmentRepository.findByShipmentType(Mockito.any())).thenReturn(shipment);
        Mockito.when(ordersRepository.findAllByUserEntity(user)).thenReturn(expectedOrders);
        Mockito.when(ingredientRepository.findById(Mockito.anyLong())).thenReturn(opt);

        //when
        OrdersSummaryDTO summaryHelper = ordersService.createSummaryHelper(initOrdersDTO(), initOrders());

        //then
        assertInstanceOf(OrdersSummaryDTO.class, summaryHelper);
    }

    @Test
    void saveOrder_checkIfIsSavedAndMapped(){
        //given
        UserEntity user = initUser();
        Orders orders = initOrders();
        orders.setUserEntity(user);
        Mockito.when(ordersRepository.save(orders)).thenReturn(orders);

        //when
        OrdersDTO ordersDTO = ordersService.saveOrder(orders, user);

        //then
        assertAll(
                ()->assertInstanceOf(OrdersDTO.class, ordersDTO),
                ()->Mockito.verify(ordersRepository, Mockito.times(1)).save(orders)
        );
    }

    @Test
    void addProductToOrderList_saveProductIfOrderExists(){
        //give
        UserEntity userEntity = initUser();
        Orders orders = initOrders();
        orders.setUserEntity(userEntity);
        Mockito.when(userLogService.loggedUser()).thenReturn(userEntity);
        Mockito.when(ordersRepository.findAllByUserEntityAndOrdersStatus(userEntity, OrderStatusType.NEW)).thenReturn(List.of(orders));
        Mockito.when(ordersRepository.save(orders)).thenReturn(orders);

        //when
        OrdersDTO ordersDTO = ordersService.addProductToOrderList(initOrdersDTO());

        //then
        assertThat(ordersDTO).isNotNull();
        assertThat(ordersDTO.getOrdersLists()).hasSize(2);
    }

    @Test
    void setPaymentAndShipment_checkIfPaymentAndShipmentIsSaved(){
        //given
        UserEntity userEntity = initUser();
        Orders orders = initOrders();
        orders.setUserEntity(userEntity);
        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(orders);
        Mockito.when(userLogService.loggedUser()).thenReturn(userEntity);
        Mockito.when(ordersRepository.findAllByUserEntityAndOrdersStatus(userEntity, OrderStatusType.NEW)).thenReturn(ordersList);
        Mockito.when(ordersRepository.save(orders)).thenReturn(orders);

        //when
        OrdersDTO ordersDTO = ordersService.setPaymentAndShipment(initOrdersDTO());


        //then
        assertThat(ordersDTO.getShipmentType()).isEqualTo(orders.getShipmentType().toString());
        assertThat(ordersDTO.getPaymentType()).isEqualTo(orders.getPaymentType().toString());
    }


    private UserEntity initUser(){
        return UserEntity
                .builder()
                .id(5L)
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
                .id(0L)
                .ordersStatus(OrderStatusType.NEW)
                .paymentType(PaymentType.BANK_TRANSFER)
                .shipmentType(ShipmentType.DELIVERYMAN)
                .ordersLists(ordersLists)
                .build();
    }

    private Shipment initShipment(){
        return Shipment
                .builder()
                .name("DELIVERYMAN")
                .price(22.0)
                .shipmentType(ShipmentType.DELIVERYMAN)
                .build();
    }

    private OrdersDTO initOrdersDTO(){
        List<OrdersListDTO> ordersListsDTO = new ArrayList<>();
        OrdersListDTO ordersListDTO = OrdersListDTO
                .builder()
                .ingredientId(2234L)
                .build();

        ordersListsDTO.add(ordersListDTO);
        return OrdersDTO
                .builder()
                .userId(5L)
                .shipmentType("DELIVERYMAN")
                .paymentType("BANK_TRANSFER")
                .ordersLists(ordersListsDTO)
                .build();
    }
}