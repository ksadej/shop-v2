package com.example.shopv2.service.order;

import com.example.shopv2.mapper.OrdersMapper;
import com.example.shopv2.model.Orders;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.repository.OrdersRepository;
import com.example.shopv2.repository.OrdersStatusRepository;
import com.example.shopv2.service.dto.OrdersDTO;
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
    //validator do możliwości aktualizowania zamówienia według
    //    NEW,
    //    PROCESSING,
    //    PAID,
    //    IN_PREPARATION,
    //    COMPLETED,
    //    WAITING_FOR_DELIVERY,
    //    DELIVERED,
    //    RETURNED,
    //    REFUND,
    //    CANCELED;

    //funkcja do pobierania zamówienia na podstawie statusu NEW do realizacji
    public List<OrdersDTO> listOfOrdersToDo(){
        UserEntity user = userLogService.loggedUser();
        return ordersRepository.findAllByUserEntity(user)
                .stream()
                .map(OrdersMapper -> ordersMapper.entityToRequest(OrdersMapper, user))
                .collect(Collectors.toList());
    }

    public List<Orders> getAll(){
        return ordersRepository.findAll();
    }

    //function for adding new status for order
    //zmiana statusu na podstawie realizacji zadania osoby z jakąś konkretną rolą
    //wykorzystać łańcuch zoobowiązań lub iterator do tego
    //wzorzec kompozyt pasuje najleiej do zamówienia
}
