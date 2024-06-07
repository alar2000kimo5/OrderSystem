package com.order.OrderSystem.adapter.out.impl;

import com.order.OrderSystem.application.out.RedisQueueService;
import com.order.OrderSystem.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisQueueServiceImpl implements RedisQueueService<Order> {

    @Autowired
    private RedisTemplate<String, Order> redisOrderTemplate;
    private static final String QUEUE_NAME = "orderQueue";

    @Override
    public void enqueue(Order item) {
        redisOrderTemplate.opsForList().leftPush(QUEUE_NAME, item);
    }

    @Override
    public void enqueueAll(List<Order> items) {
        redisOrderTemplate.opsForList().leftPushAll(QUEUE_NAME, items.toArray(new Order[0]));
    }

    @Override
    public List<Order> dequeue() {
        List<Order> orders = redisOrderTemplate.opsForList().range(QUEUE_NAME, 0, -1);
        redisOrderTemplate.opsForList().trim(QUEUE_NAME, 1, 0);  // 清空
        return orders;
    }
}
