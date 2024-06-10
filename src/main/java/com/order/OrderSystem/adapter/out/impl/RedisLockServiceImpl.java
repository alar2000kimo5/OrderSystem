package com.order.OrderSystem.adapter.out.impl;

import com.order.OrderSystem.application.out.RedisLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisLockServiceImpl implements RedisLockService<Runnable, Void> {

    @Autowired
    private RedisTemplate<String, String> redisLockTemplate;

    private static final String LOCK = "lock";

    @Override
    public Void lockAndDo(String queueName, Runnable runnable) {
        while (true) {
            // 在 Redis 中設置一個鎖，queueName 為 lockKey，value 為任意值，並設置過期時間
            Boolean lockAcquired = redisLockTemplate.opsForValue().setIfAbsent(queueName + LOCK, "value", Duration.ofSeconds(2));
            if (Boolean.TRUE.equals(lockAcquired)) {
                try {
                    // 在這裡執行需要同步訪問的代碼
                    runnable.run();
                } finally {
                    // 無論代碼執行成功與否，都需要釋放鎖
                    redisLockTemplate.delete(queueName + LOCK);
                }
            }
        }
    }
}
