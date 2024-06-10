package com.order.OrderSystem.adapter.in.mapper;

import com.order.OrderSystem.adapter.in.OrderRequest;
import com.order.OrderSystem.application.engine.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OrderMapper {
    Order requestToOrder(OrderRequest orderRequest);
}
