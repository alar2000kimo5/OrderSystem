package com.order.OrderSystem.application.out;

import com.order.OrderSystem.domain.Order;

import java.util.Set;

public interface RedisQueueService<T> {
//    void enqueue(T item);
//
//    void enqueueAll(List<T> items);
//
//    List<T> dequeue();

    void addToZSet(String key, Order value, double score);
    Set<T> getAllOrdersFromZset(String queueName);
    void removeFromZSet(String key, Order value);
}
