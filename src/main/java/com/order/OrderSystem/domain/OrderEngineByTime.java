package com.order.OrderSystem.domain;

import com.order.OrderSystem.domain.base.BaseOrderEngine;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class OrderEngineByTime extends BaseOrderEngine<OrderByTime> {
    @Override
    protected Comparator<OrderByTime> getComparator() {
        return Comparator.comparing(OrderByTime::getOrderTime);
    }
}
