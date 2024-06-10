package com.order.OrderSystem.application.engine;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.order.OrderSystem.domain.base.BaseOrder;
import com.order.OrderSystem.domain.type.InComeType;
import com.order.OrderSystem.domain.type.PriceType;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order extends BaseOrder {

    String userName;

    @JsonCreator
    public Order(
            @JsonProperty("inComeType") InComeType inComeType,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("priceType") PriceType priceType,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("orderTime") Timestamp orderTime) {
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
                super.toString() +
                '}';
    }
}
