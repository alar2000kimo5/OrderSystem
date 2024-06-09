package com.order.OrderSystem.adapter.out.impl;

import com.order.OrderSystem.adapter.out.jpa.OrderJpa;
import com.order.OrderSystem.application.out.OrderRepository;
import com.order.OrderSystem.domain.OrderMatchEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderRepositoryImpl implements OrderRepository<OrderMatchEntity> {
    @Autowired
    private OrderJpa orderJpa;

    @Override
    public List<OrderMatchEntity> findAll() {
        return (List<OrderMatchEntity>) orderJpa.findAll();
    }

    @Override
    public void saveMatchOrder(OrderMatchEntity order) {
        orderJpa.save(order);
    }
}
