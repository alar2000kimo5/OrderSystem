package com.order.OrderSystem.adapter.out.impl;

import com.order.OrderSystem.adapter.out.jpa.OrderJpa;
import com.order.OrderSystem.application.out.OrderRepository;
import com.order.OrderSystem.domain.MatchOrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderRepositoryImpl implements OrderRepository<MatchOrderEntity> {
    @Autowired
    private OrderJpa orderJpa;

    @Override
    public List<MatchOrderEntity> findAll() {
        return (List<MatchOrderEntity>) orderJpa.findAll();
    }

    @Override
    public void saveMatchOrder(MatchOrderEntity order) {
        orderJpa.save(order);
    }
}
