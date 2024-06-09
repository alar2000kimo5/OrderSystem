package com.order.OrderSystem.application.out;

import java.util.Set;

public interface RedisQueueService<T> {
    void addToZSet(String key, T value, double score);
    Set<T> getAllObjectsFromZset(String queueName);
    void removeFromZSet(String key, T value);
}
