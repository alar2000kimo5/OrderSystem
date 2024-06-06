package com.order.OrderSystem.domain;

import com.order.OrderSystem.application.out.OrderRepository;
import com.order.OrderSystem.application.out.RedisLockService;
import com.order.OrderSystem.application.out.RedisQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    @Scheduled(fixedRate = 1000)
    @Override
    public void matchOrder() {
        Runnable matchRunner = () -> {
            List<Order> orders = redisQueueService.dequeue();
            System.out.println("=============================" +orders);
            Collections.sort(orders, Comparator.comparing(Order::getOrderTime));
            matchAndSave(orders);
        };
        redisLockService.lockAndDo(matchRunner);
    }

    private void matchAndSave(List<Order> orders) {
        List<Order> matchedOrders = new ArrayList<>();
        orders.forEach(order -> {
            if (!matchedOrders.contains(order)) {
                List<Order> matched = orders.stream()
                        .filter(matchOrder -> !matchedOrders.contains(matchOrder) && isMatched(order, matchOrder)).toList();

                if (!matched.isEmpty()) {
                    Order matchedOrder = matched.get(0);
                    orderRepository.saveMatchOrder(new MatchOrderEntity(order, matchedOrder));
                    //System.out.println("Matched order: " + order + " with " + matchedOrder);
                    matchedOrders.add(order);
                    matchedOrders.add(matchedOrder);
                }
            }
        });

        orders.removeAll(matchedOrders);
    }

    private boolean isMatched(Order order1, Order order2) {
        if (order1.getInComeType() == order2.getInComeType()) {
            return false;
        }

        return order1.getQuantity() == order2.getQuantity()
                && order1.getPriceType().equals(order2.getPriceType())
                && order1.getPrice().equals(order2.getPrice());
    }
}
