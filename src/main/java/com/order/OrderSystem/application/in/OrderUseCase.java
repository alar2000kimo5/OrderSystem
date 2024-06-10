package com.order.OrderSystem.application.in;

import com.order.OrderSystem.application.engine.Order;
import com.order.OrderSystem.application.out.RedisQueueZSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static java.math.BigDecimal.valueOf;

@Component
public class OrderUseCase implements UseCase<Order, String>{

    // redis queue
    @Autowired
    RedisQueueZSetService redisQueueZSetService;

    public static final String QUEUE_NAME_30 = "orderQueue30";
    public static final String QUEUE_NAME_60 = "orderQueue60";
    public static final String QUEUE_NAME_end = "orderQueueEnd";

    @Override
    public String submit(Order order) {
        // send to redis queue
        BigDecimal price = order.getPrice();
        if(price.compareTo(valueOf(33)) < 0){
            redisQueueZSetService.addToZSet(QUEUE_NAME_30,order,order.getOrderTime().getTime());
        } else if(price.compareTo(valueOf(66)) < 0){
            redisQueueZSetService.addToZSet(QUEUE_NAME_60,order,order.getOrderTime().getTime());
        } else {
            redisQueueZSetService.addToZSet(QUEUE_NAME_end,order,order.getOrderTime().getTime());
        }

        //redisQueueService.enqueue(order);
        return "user : " + order.getUserName() + " orderTime : " + order.getOrderTime();
    }

    @Override
    public List<String> getQueueNames() {
        return Arrays.asList(QUEUE_NAME_30,QUEUE_NAME_60,QUEUE_NAME_end);
    }
}
