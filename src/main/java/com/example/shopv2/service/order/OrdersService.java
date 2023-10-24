package com.example.shopv2.service.order;

import com.example.shopv2.mapper.OrdersListMapper;
import com.example.shopv2.mapper.OrdersMapper;
import com.example.shopv2.model.Orders;
import com.example.shopv2.model.OrdersList;
import com.example.shopv2.model.Shipment;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.model.enums.OrderStatusType;
import com.example.shopv2.model.enums.PaymentType;
import com.example.shopv2.model.enums.ShipmentType;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.repository.OrdersListRepository;
import com.example.shopv2.repository.OrdersRepository;
import com.example.shopv2.repository.ShipmentRepository;
import com.example.shopv2.service.BasketService;
import com.example.shopv2.service.dto.OrdersDTO;
import com.example.shopv2.service.dto.OrdersSummaryDTO;
import com.example.shopv2.service.order.payment.PaypalService;
import com.example.shopv2.service.user.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    private final PaypalService paypalService;
    private OrdersMapper ordersMapper = new OrdersMapper();

    @Autowired
    public OrdersService(OrdersRepository orderRepository, UserLogService userLogService,
                         BasketService basketService, ShipmentRepository shipmentRepository,
                         OrdersListRepository ordersListRepository, IngredientRepository ingredientRepository,
                         PaypalService paypalService) {
        this.ordersRepository = orderRepository;
        this.userLogService = userLogService;
        this.basketService = basketService;
        this.shipmentRepository = shipmentRepository;
        this.ordersListRepository = ordersListRepository;
        this.ingredientRepository = ingredientRepository;
        this.paypalService = paypalService;
    }

//    private void clearOrderCart(OrdersDTO ordersDTO){
//        Orders orders = ordersMapper.requestToEntity(ordersDTO);
//        ordersRepository.deleteById(orders.getId());
//    }

    public List<OrdersList> ordersLists(){
        UserEntity user = userLogService.loggedUser();

        return ordersRepository
                .findAllByUserEntity(user)
                .stream()
                .flatMap(x ->x.getOrdersLists().stream())
                .toList();
    }

    public List<OrdersDTO> getOrders(){
        UserEntity user = userLogService.loggedUser();

        return ordersRepository.findAllByUserEntityAndOrdersStatus(user, OrderStatusType.NEW)
                .stream()
                .map(OrdersMapper -> ordersMapper.entityToRequest(OrdersMapper, user))
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

    public List<OrdersList> orderRow(OrdersDTO ordersDTO){
        return ordersDTO.getOrdersLists()
                .stream()
                .map(OrdersListMapper::mapToRow2)
                .collect(Collectors.toList());
    }

    public OrdersSummaryDTO createSummaryHelper(OrdersDTO ordersDTO, Orders orders){
        Shipment shipmentType = shipmentRepository.findByShipmentType(ShipmentType.valueOf(ordersDTO.getShipmentType()));
        OrdersSummaryDTO ordersSummary = ordersMapper.totalSummaryOrder(orders);
        ordersSummary.setTotalValue(sumTotalValue(shipmentType));
        return ordersSummary;
    }

    OrdersDTO saveOrder(Orders newOrders, UserEntity user){
        Orders newOrder = ordersRepository.save(newOrders);
        return ordersMapper.entityToRequest(newOrder, user);
    }

    //zapisuje do bazy danych produkt, typ wysyłki, typ płatności
    @Transactional
    public OrdersDTO addProductToOrderList(OrdersDTO ordersDTO){
        UserEntity user = userLogService.loggedUser();

        List<Orders> existingOrder = ordersRepository.findAllByUserEntityAndOrdersStatus(user, OrderStatusType.NEW);
        if(!existingOrder.isEmpty()){
            Orders orders = existingOrder.get(0);
            orders.getOrdersLists().addAll(orderRow(ordersDTO));
            return saveOrder(orders, user);
        }
        else{
            Orders orders = ordersMapper.requestToEntity(ordersDTO, user);
            orders.setOrdersLists(orderRow(ordersDTO));
            return saveOrder(orders, user);
        }
    }


    @Transactional
    public OrdersDTO setPaymentAndShipment(OrdersDTO ordersDTO){
        UserEntity user = userLogService.loggedUser();

        List<Orders> existingOrder = ordersRepository.findAllByUserEntityAndOrdersStatus(user, OrderStatusType.NEW);
        Orders orders = existingOrder.get(0);
        orders.setPaymentType(PaymentType.valueOf(ordersDTO.getPaymentType()));
        orders.setShipmentType(ShipmentType.valueOf(ordersDTO.getShipmentType()));
        return saveOrder(orders, user);
    }

    //aktualizacja jednego zamówienia w tabeli order a nie dodawania kolejnych
    @Transactional
    public OrdersSummaryDTO finalSummary(OrdersDTO ordersDTO) throws IOException {
        UserEntity user = userLogService.loggedUser();
        List<Orders> existingOrder = ordersRepository.findAllByUserEntityAndOrdersStatus(user, OrderStatusType.NEW);
        Orders orders = existingOrder.get(0);

        List<OrdersList> orderRow = ordersDTO.getOrdersLists()
                .stream()
                .map(OrdersListMapper::mapToRow2)
                .collect(Collectors.toList());
        orders.setOrdersLists(orderRow);
        OrdersSummaryDTO summaryHelper = createSummaryHelper(ordersDTO, orders);

        PaymentType paymentType = PaymentType.valueOf(ordersDTO.getPaymentType());
        initPayment(paymentType, summaryHelper, orders);
        initShipment();
        return summaryHelper;
    }

    public void initPayment(PaymentType paymentType,  OrdersSummaryDTO summaryHelper, Orders orders) throws IOException {
        if(paymentType.equals(PaymentType.PAYPAL)){
            paypalService.createOrder(summaryHelper.getTotalValue(), orders.getId());
        }
        if(paymentType.equals(PaymentType.BLIK)){

        }
        if(paymentType.equals(PaymentType.BANK_TRANSFER)){

        }
    }
    public void initShipment(){

    }

    public void sumAll(){

    }

    public Double priceDiscount(){
        return null;
    }
}
