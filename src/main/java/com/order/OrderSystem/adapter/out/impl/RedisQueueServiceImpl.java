package com.order.OrderSystem.adapter.out.impl;

import com.order.OrderSystem.application.out.RedisQueueService;
import com.order.OrderSystem.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisQueueServiceImpl implements RedisQueueService<Order> {

    @Autowired
    private RedisTemplate<String, Order> redisOrderTemplate;

    @Override
    public void addToZSet(String key, Order value, double score) {
        redisOrderTemplate.opsForZSet().add(key, value, score);
    }

    @Override
    public Set<Order> getAllOrdersFromZset(String queueName){
        return redisOrderTemplate.opsForZSet().range(queueName, 0, -1);
    }

    // 刪除 ZSet 中的元素
    @Override
    public void removeFromZSet(String key, Order value) {
        Long result = redisOrderTemplate.opsForZSet().remove(key, value);
        if (result != null && result > 0) {
            System.out.println("Successfully removed the value from ZSet.");
        } else {
            System.out.println("Failed to remove the value from ZSet or value not found.");
        }
    }

}
