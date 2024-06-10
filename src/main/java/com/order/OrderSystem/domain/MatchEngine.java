package com.order.OrderSystem.domain;

import com.order.OrderSystem.application.in.UseCase;
import com.order.OrderSystem.application.out.RedisLockService;
import com.order.OrderSystem.domain.base.BaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class MatchEngine<TBaseOrder extends BaseOrder, TUseCase extends UseCase> {

    private TUseCase useCase;
    private Executor executor = Executors.newFixedThreadPool(3);
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private RedisLockService redisLockService;


    private TUseCase getUseCase() {
        if (this.useCase == null) {
            this.useCase = applicationContext.getBean(getUseCaseClass());
        }
        return this.useCase;
    }

    protected abstract Class<TUseCase> getUseCaseClass();

    /**
     * 這裡寫入2個物件如何匹配
     * obj1 主匹配
     * obj2 被匹配
     */
    protected abstract boolean isMatch(TBaseOrder objT, TBaseOrder objU);

    /**
     *
     */
    protected abstract Runnable lockAndRun(String lockQueueName);

    @Scheduled(fixedRate = 1000)
    public void scheduler() {
        UseCase useCase = getUseCase();
        List<String> queueNames = useCase.getQueueNames();
        queueNames.forEach(queueName -> executor.execute(runMather(queueName)));
    }

    private Runnable runMather(String lockQueueName) {
        return () -> redisLockService.lockAndDo(lockQueueName, this.lockAndRun(lockQueueName));
    }
}
