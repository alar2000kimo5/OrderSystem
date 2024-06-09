package com.order.OrderSystem.adapter.in.mapper;

import com.order.OrderSystem.adapter.in.OrderRequest;
import com.order.OrderSystem.domain.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMaper {
    Order requestToOrder(OrderRequest orderRequest);
}
