package com.order.OrderSystem.application.out;

public interface RedisLockService<T> {
    void lockAndDo(String key ,T function);
}
