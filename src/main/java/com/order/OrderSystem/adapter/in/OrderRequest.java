package com.order.OrderSystem.adapter.in;

import com.order.OrderSystem.adapter.in.mapper.BaseRequest;
import com.order.OrderSystem.adapter.in.mapper.OrderMapper;
import com.order.OrderSystem.application.engine.Order;
import com.order.OrderSystem.domain.type.InComeType;
import com.order.OrderSystem.domain.type.PriceType;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderRequest implements BaseRequest<Order> {
    private String userName;
    private InComeType inComeType;// buy or sell
    private int quantity;
    private PriceType priceType;  // market or limit
    private BigDecimal price;
    private Timestamp orderTime;

    final static OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    @Override
    public Order toObj() {
        return mapper.requestToOrder(this);
    }

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
    public String toString() {
        return "OrderRequest{" +
                "userName='" + userName + '\'' +
                ", inComeType=" + inComeType +
                ", quantity=" + quantity +
                ", priceType=" + priceType +
                ", price=" + price +
                ", orderTime=" + orderTime +
                '}';
    }
}
