package com.order.OrderSystem.application.out;

import com.order.OrderSystem.domain.TestEntity;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {


    List<TestEntity> findAll();
}
