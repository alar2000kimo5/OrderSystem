package com.order.OrderSystem.application.in;

import com.order.OrderSystem.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderUseCase implements UseCase<Order, String>{

    // redis queue

    @Override
    public String submit(Order order) {
        // send to redis queue

        return "user : " + order.getUserName() + " orderTime : " + order.getOrderTime();
    }
}
