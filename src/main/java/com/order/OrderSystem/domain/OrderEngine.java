package com.order.OrderSystem.domain;

import com.order.OrderSystem.application.out.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderEngine implements MatchOrder<Order>{

    // redis

    // jpa db
    @Autowired
    OrderRepository orderRepository;
    @Override
    public void matchOrder(Order order) {

    }
}
