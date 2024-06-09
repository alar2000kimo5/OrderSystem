package com.order.OrderSystem.application.out;

public interface RedisLockService<T, R> {
    R lockAndDo(String key, T function);
}
