package com.order.OrderSystem.application.out;

public interface RedisLockService<T, R> extends RedisService {
    R lockAndDo(String key, T function);
}
