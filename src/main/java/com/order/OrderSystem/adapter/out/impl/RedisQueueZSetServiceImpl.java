package com.order.OrderSystem.adapter.out.impl;

import com.order.OrderSystem.application.out.RedisQueueZSetService;
import com.order.OrderSystem.application.engine.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisQueueZSetServiceImpl implements RedisQueueZSetService<Order> {

    @Autowired
    private RedisTemplate<String, Order> redisOrderTemplate;

    @Override
    public void addToZSet(String queueName, Order value, double score) {
        redisOrderTemplate.opsForZSet().add(queueName, value, score);
    }

    @Override
    public Set<Order> getAllObjectsFromZset(String queueName) {
        return redisOrderTemplate.opsForZSet().range(queueName, 0, -1);
    }

    // 刪除 ZSet 中的元素
    @Override
    public void removeFromZSet(String queueName, Order value) {
        redisOrderTemplate.opsForZSet().remove(queueName, value);
    }

}
