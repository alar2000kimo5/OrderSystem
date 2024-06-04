package com.order.OrderSystem.domain;

import com.order.OrderSystem.domain.base.BaseOrder;
import com.order.OrderSystem.domain.type.InComeType;
import com.order.OrderSystem.domain.type.PriceType;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderByTime extends BaseOrder {

    private Timestamp orderTime;

    public OrderByTime(InComeType inComeType, int quantity, PriceType priceType, BigDecimal price, Timestamp orderTime) {
        super(inComeType, quantity, priceType, price);
        this.orderTime = orderTime;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    @Override
    public String toString() {
        return "OrderByTime{" +
                "orderTime=" + orderTime +
                "} " + super.toString();
    }
}
