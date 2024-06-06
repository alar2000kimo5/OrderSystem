package com.order.OrderSystem.application.out;

import com.order.OrderSystem.domain.MatchOrderEntity;

import java.util.List;

public interface OrderRepository<T> {


    List<T> findAll();

    void saveMatchOrder(T order);
}
