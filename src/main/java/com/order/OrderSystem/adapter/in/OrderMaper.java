package com.order.OrderSystem.adapter.in;

import com.order.OrderSystem.domain.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMaper {
    Order requestToOrder(RequestOrder requestOrder);
}
