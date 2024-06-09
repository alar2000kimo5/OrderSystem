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
public class OrderMatchEngine implements MatchEngine<Order,Order> {

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
    public void scheduler() {
        executor.execute(runMather(OrderUseCase.QUEUE_NAME_30));
        executor.execute(runMather(OrderUseCase.QUEUE_NAME_60));
        executor.execute(runMather(OrderUseCase.QUEUE_NAME_end));
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


    private Runnable runMather(String key) {
        return () -> {
            Runnable matchRunner = () ->
                    matchAndSave(key, redisQueueService.getAllObjectsFromZset(key));
            redisLockService.lockAndDo(key, matchRunner);
        };
    }

    public void matchAndSave(String key, Set<Order> orders) {
        List<Order> matchedOrders = new ArrayList<>();
        orders.forEach(order -> {
            if (!matchedOrders.contains(order)) {
                List<Order> matched = orders.stream()
                        .filter(matchOrder -> !matchedOrders.contains(matchOrder)
                                && isMatch(order, matchOrder)).toList();

                if (!matched.isEmpty()) {
                    Order matchedOrder = matched.get(0);
                    orderRepository.saveMatchOrder(new OrderMatchEntity(order, matchedOrder)); //寫入db
                    redisQueueService.removeFromZSet(key, order);
                    redisQueueService.removeFromZSet(key, matchedOrder); // 刪除queue上的資料
                    matchedOrders.add(order);
                    matchedOrders.add(matchedOrder);
                }
            }
        });
    }
}
