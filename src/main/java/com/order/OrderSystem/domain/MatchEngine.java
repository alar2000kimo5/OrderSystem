package com.order.OrderSystem.domain;

import com.order.OrderSystem.application.in.UseCase;
import com.order.OrderSystem.application.out.RedisLockService;
import com.order.OrderSystem.application.out.RedisQueueZSetService;
import com.order.OrderSystem.domain.base.BaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class MatchEngine<T extends BaseOrder, U extends UseCase> {
    @Autowired
    private ApplicationContext applicationContext;
    private Class<U> useCaseClass;
    private U useCase;

    public MatchEngine(Class<U> useCaseClass) {
        this.useCaseClass = useCaseClass;
    }

    private U getUseCase() {
        if (this.useCase == null) {
            this.useCase = applicationContext.getBean(useCaseClass);
        }
        return this.useCase;
    }

    @Autowired
    RedisQueueZSetService<T> redisQueueZSetService;
    @Autowired
    RedisLockService redisLockService;

    protected abstract boolean isMatch(T objT, T objU);

    Executor executor = Executors.newFixedThreadPool(3);

    @Scheduled(fixedRate = 1000)
    public void scheduler() {
        UseCase useCase = getUseCase();
        List<String> queueNames = useCase.getQueueNames();
        queueNames.forEach(queueName -> executor.execute(runMather(queueName)));
    }

    private Runnable runMather(String queueName) {
        return () -> {
            Runnable matchRunner = () -> matchAndSave(queueName, redisQueueZSetService.getAllObjectsFromZset(queueName));
            redisLockService.lockAndDo(queueName, matchRunner);
        };
    }

    private void matchAndSave(String queueName, Set<T> orders) {
        List<T> matchedData = new ArrayList<>();
        orders.forEach(obj1 -> {
            if (!matchedData.contains(obj1)) {
                List<T> matched = orders.stream().filter(matchObj -> !matchedData.contains(matchObj) //
                        && isMatch(obj1, matchObj)).toList(); //

                if (!matched.isEmpty()) {
                    T matchedObj = matched.get(0);
                    this.postMatch(this.redisQueueZSetService, queueName, obj1, matchedObj);
                    matchedData.add(obj1);
                    matchedData.add(matchedObj);
                }
            }
        });
    }

    protected abstract void postMatch(RedisQueueZSetService<T> redisQueueZSetService, String queueName, T obj1, T matchedObj);
}
