package com.order.OrderSystem.application.out;

import com.order.OrderSystem.domain.RedisService;

import java.util.Set;

public interface RedisQueueZSetService<T> extends RedisService {
    void addToZSet(String queueName, T value, double score);
    Set<T> getAllObjectsFromZset(String queueName);
    void removeFromZSet(String queueName, T value);
}
