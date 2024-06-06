package com.order.OrderSystem.domain;

import com.order.OrderSystem.domain.base.BaseOrder;

public interface MatchOrder<T extends BaseOrder> {

    void matchOrder(T order);
}
