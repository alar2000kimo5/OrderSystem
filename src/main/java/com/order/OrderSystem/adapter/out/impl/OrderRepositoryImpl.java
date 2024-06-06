package com.order.OrderSystem.adapter.out.impl;

import com.order.OrderSystem.adapter.out.jpa.OrderJpa;
import com.order.OrderSystem.application.out.OrderRepository;
import com.order.OrderSystem.domain.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderRepositoryImpl implements OrderRepository<OrderEntity> {
    @Autowired
    private OrderJpa orderJpa;

    @Override
    public List<OrderEntity> findAll() {
        return (List<OrderEntity>) orderJpa.findAll();
    }

    @Override
    public void saveMatchOrder(List order) {
        orderJpa.saveAll(order);
    }
}
