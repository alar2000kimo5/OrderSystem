package com.order.OrderSystem.application.out;

import com.order.OrderSystem.domain.Order;

import java.util.List;

public interface RedisQueueService<T> {
    void enqueue(T item);

    void enqueueAll(List<Order> items);

    List<T> dequeue();
}
