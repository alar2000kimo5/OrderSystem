package com.order.OrderSystem.adapter.in;

import com.order.OrderSystem.adapter.in.mapper.BaseRequest;
import com.order.OrderSystem.adapter.in.mapper.OrderMaper;
import com.order.OrderSystem.application.engine.Order;
import com.order.OrderSystem.domain.type.InComeType;
import com.order.OrderSystem.domain.type.PriceType;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class OrderRequest implements BaseRequest<Order> {
    private String userName;
    private InComeType inComeType;// buy or sell
    private int quantity;
    private PriceType priceType;  // market or limit
    private BigDecimal price;
    private Timestamp orderTime;

    final static OrderMaper mapper = Mappers.getMapper(OrderMaper.class);

    @Override
    public Order toObj() {
        return mapper.requestToOrder(this);
    }
}
