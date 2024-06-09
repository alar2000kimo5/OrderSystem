package com.order.OrderSystem.adapter.out.jpa;


import com.order.OrderSystem.domain.OrderMatchEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderJpa extends CrudRepository<OrderMatchEntity, Long> {

}
