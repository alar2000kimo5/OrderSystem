package com.order.OrderSystem.application.engine;

import com.order.OrderSystem.application.in.OrderUseCase;
import com.order.OrderSystem.application.out.OrderRepository;
import com.order.OrderSystem.application.out.RedisQueueZSetService;
import com.order.OrderSystem.domain.MatchEngine;
import com.order.OrderSystem.domain.OrderMatchEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class OrderMatchEngine extends MatchEngine<Order, OrderUseCase> {
    // jpa db
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    RedisQueueZSetService redisQueueZSetService;

    OrderMatchEngine(Class<OrderUseCase> useCaseClass) {
        super(useCaseClass);
    }

    /**
     * 父層metchEngine會依照給出的queue，多緒上鎖後呼叫Runnable方法
     * 這裡給出上鎖後要做什麼事
     * */
    @Override
    protected Runnable lockAndRun(String lockQueueName) {
        return () -> matchAndSave(lockQueueName, getMatchData(lockQueueName));
    }
    /**
     * 這裡寫入2個物件如何匹配
     * obj1 主匹配
     * obj2 被匹配
     */
    @Override
    public boolean isMatch(Order obj1, Order obj2) {
        if (obj1.getInComeType() == obj2.getInComeType()) {
            return false;
        }

        return obj1.getQuantity() == obj2.getQuantity()
                && obj1.getPriceType().equals(obj2.getPriceType())
                && obj1.getPrice().equals(obj2.getPrice());
    }
    /**
     * 這裡處理匹配後要做事，此例為：1.寫入db 2.刪除queue上的資料
     * queueName 當下拿到資料的queueName
     * obj1 為主匹配
     * match 被匹配
     */
    private Set<Order> getMatchData(String queueName) {
        return redisQueueZSetService.getAllObjectsFromZset(queueName);
    }

    /*
    * 進行匹配
    * */
    private void matchAndSave(String queueName, Set<Order> orders) {
        List<Order> matchedData = new ArrayList<>();
        orders.forEach(obj1 -> {
            if (!matchedData.contains(obj1)) {
                List<Order> matched = orders.stream().filter(matchObj -> !matchedData.contains(matchObj) //
                        && this.isMatch(obj1, matchObj)).toList(); //

                if (!matched.isEmpty()) {
                    Order matchedObj = matched.get(0);
                    matchedData.add(obj1);
                    matchedData.add(matchedObj);
                    this.postMatch(queueName, obj1, matchedObj);
                }
            }
        });
    }

    /**
     * 這裡處理匹配後要做的事，此例為：1.寫入db 2.刪除queue上的資料
     * queueName 當下拿到資料的queueName
     * obj1 為主匹配
     * match 被匹配
     */
    public void postMatch(String queueName, Order obj1, Order matchedObj) {
        orderRepository.saveMatchOrder(new OrderMatchEntity(obj1, matchedObj)); //寫入db
        redisQueueZSetService.removeFromZSet(queueName, obj1);
        redisQueueZSetService.removeFromZSet(queueName, matchedObj); // 刪除queue上的資料
    }
}
