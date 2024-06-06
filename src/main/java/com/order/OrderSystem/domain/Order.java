package com.order.OrderSystem.domain;

import com.order.OrderSystem.domain.base.BaseOrder;
import com.order.OrderSystem.domain.type.InComeType;
import com.order.OrderSystem.domain.type.PriceType;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order extends BaseOrder {

    String userName;

    public Order(){

    }
    public Order(InComeType inComeType, int quantity, PriceType priceType, BigDecimal price, Timestamp orderTime) {
        super(inComeType, quantity, priceType, price, orderTime);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
