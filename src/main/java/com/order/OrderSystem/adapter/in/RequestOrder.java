package com.order.OrderSystem.adapter.in;

import com.order.OrderSystem.adapter.in.mapper.BaseMapper;
import com.order.OrderSystem.adapter.in.mapper.OrderMaper;
import com.order.OrderSystem.domain.Order;
import com.order.OrderSystem.domain.type.InComeType;
import com.order.OrderSystem.domain.type.PriceType;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class RequestOrder implements BaseMapper<Order> {
    private String userName;
    private InComeType inComeType;// buy or sell
    private int quantity;
    private PriceType priceType;  // market or limit
    private BigDecimal price;
    private Timestamp orderTime;

    final static OrderMaper mapper = Mappers.getMapper(OrderMaper.class);

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public InComeType getInComeType() {
        return inComeType;
    }

    public void setInComeType(InComeType inComeType) {
        this.inComeType = inComeType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public Order toObj() {
        return mapper.requestToOrder(this);
    }
}
