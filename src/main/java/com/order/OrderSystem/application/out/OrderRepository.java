package com.order.OrderSystem.application.out;

import java.util.List;

public interface OrderRepository<T> {


    List<T> findAll();

    void saveMatchOrder(List<T> order);
}
