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
    public List<Order> dequeue() {
        return redisOrderTemplate.opsForList().range(QUEUE_NAME, 0, -1);
    }
}
