package com.order.OrderSystem.domain;

import com.order.OrderSystem.application.in.OrderUseCase;
import com.order.OrderSystem.application.out.OrderRepository;
import com.order.OrderSystem.application.out.RedisLockService;
import com.order.OrderSystem.application.out.RedisQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class OrderEngine implements MatchOrder {

    // redis
    @Autowired
    RedisQueueService<Order> redisQueueService;
    // jpa db
    @Autowired
    OrderRepository orderRepository;
    // redis lock
    @Autowired
    RedisLockService redisLockService;

    Executor executor = Executors.newFixedThreadPool(3);

    @Scheduled(fixedRate = 1000)
    @Override
    public void matchOrder() {
        executor.execute(runMather(OrderUseCase.QUEUE_NAME_30));
        executor.execute(runMather(OrderUseCase.QUEUE_NAME_60));
        executor.execute(runMather(OrderUseCase.QUEUE_NAME_end));
    }

    private Runnable runMather(String key) {
        return () -> {
            Runnable matchRunner = () ->
                    matchAndSave(key, redisQueueService.getAllOrdersFromZset(key));
            redisLockService.lockAndDo(key, matchRunner);
        };
    }

    public void matchAndSave(String key, Set<Order> orders) {
        List<Order> matchedOrders = new ArrayList<>();
        orders.forEach(order -> {
            if (!matchedOrders.contains(order)) {
                List<Order> matched = orders.stream()
                        .filter(matchOrder -> !matchedOrders.contains(matchOrder)
                                && isMatched(order, matchOrder)).toList();

                if (!matched.isEmpty()) {
                    Order matchedOrder = matched.get(0);
                    orderRepository.saveMatchOrder(new MatchOrderEntity(order, matchedOrder)); //寫入db
                    redisQueueService.removeFromZSet(key, matchedOrder); // 刪除queue上的資料
                    matchedOrders.add(order);
                    matchedOrders.add(matchedOrder);
                }
            }
        });
    }

    public boolean isMatched(Order order1, Order order2) {
        if (order1.getInComeType() == order2.getInComeType()) {
            return false;
        }

        return order1.getQuantity() == order2.getQuantity()
                && order1.getPriceType().equals(order2.getPriceType())
                && order1.getPrice().equals(order2.getPrice());
    }
}
