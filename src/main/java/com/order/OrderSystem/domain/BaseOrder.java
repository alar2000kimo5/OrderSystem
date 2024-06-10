package com.order.OrderSystem.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.order.OrderSystem.domain.type.InComeType;
import com.order.OrderSystem.domain.type.PriceType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


public abstract class BaseOrder implements Serializable {
    private InComeType inComeType;// buy or sell
    private int quantity;
    private PriceType priceType;  // market or limit
    private BigDecimal price;
    private Timestamp orderTime;

    @JsonCreator
    public BaseOrder(
            @JsonProperty("inComeType") InComeType inComeType,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("priceType") PriceType priceType,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("orderTime") Timestamp orderTime) {
        this.inComeType = inComeType;
        this.quantity = quantity;
        this.priceType = priceType;
        this.price = price;
        this.orderTime = orderTime;
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
        return "BaseOrder{" +
                "inComeType=" + inComeType +
                ", quantity=" + quantity +
                ", priceType=" + priceType +
                ", price=" + price +
                ", orderTime=" + orderTime +
                '}';
    }
}