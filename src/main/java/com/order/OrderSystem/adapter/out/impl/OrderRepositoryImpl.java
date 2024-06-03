package com.order.OrderSystem.adapter.out.impl;

import com.order.OrderSystem.adapter.out.jpa.OrderJpa;
import com.order.OrderSystem.application.out.OrderRepository;
import com.order.OrderSystem.domain.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderRepositoryImpl implements OrderRepository {


    private OrderJpa orderJpa;
    @Autowired
    public OrderRepositoryImpl(OrderJpa orderJpa) {
        this.orderJpa = orderJpa;
    }
    @Override
    public List<TestEntity> findAll() {
        return (List<TestEntity>) orderJpa.findAll();
    }
}
