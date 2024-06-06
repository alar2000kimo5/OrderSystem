package com.order.OrderSystem.application.in;

import com.order.OrderSystem.application.out.RedisQueueService;
import com.order.OrderSystem.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderUseCase implements UseCase<Order, String>{

    // redis queue
    @Autowired
    RedisQueueService redisQueueService;
    @Override
    public String submit(Order order) {
        // send to redis queue
        redisQueueService.enqueue(order);
        return "user : " + order.getUserName() + " orderTime : " + order.getOrderTime();
    }
}
