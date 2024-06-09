package com.order.OrderSystem.application.engine;

import com.order.OrderSystem.application.in.OrderUseCase;
import com.order.OrderSystem.application.out.OrderRepository;
import com.order.OrderSystem.application.out.RedisQueueZSetService;
import com.order.OrderSystem.domain.MatchEngine;
import com.order.OrderSystem.domain.OrderMatchEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMatchEngine extends MatchEngine<Order, OrderUseCase> {
    // jpa db
    @Autowired
    OrderRepository orderRepository;

    OrderMatchEngine(Class<OrderUseCase> useCaseClass) {
        super(useCaseClass);
    }

    @Override
    public boolean isMatch(Order obj1, Order obj2) {
        if (obj1.getInComeType() == obj2.getInComeType()) {
            return false;
        }

        return obj1.getQuantity() == obj2.getQuantity()
                && obj1.getPriceType().equals(obj2.getPriceType())
                && obj1.getPrice().equals(obj2.getPrice());
    }

    @Override
    public void postMatch(RedisQueueZSetService<Order> redisQueueZSetService, String queueName, Order obj1, Order matchedObj) {
        orderRepository.saveMatchOrder(new OrderMatchEntity(obj1, matchedObj)); //寫入db
        redisQueueZSetService.removeFromZSet(queueName, obj1);
        redisQueueZSetService.removeFromZSet(queueName, matchedObj); // 刪除queue上的資料
    }


}
