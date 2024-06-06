package com.order.OrderSystem.application.out;

import java.util.List;

public interface RedisQueueService<T> {
    void enqueue(T item);

    List<T> dequeue();
}
