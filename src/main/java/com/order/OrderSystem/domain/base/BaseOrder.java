package com.order.OrderSystem.domain.base;

import com.order.OrderSystem.domain.type.InComeType;
import com.order.OrderSystem.domain.type.PriceType;

import java.math.BigDecimal;


public abstract class BaseOrder {
    private InComeType inComeType;// buy or sell
    private int quantity;
    private PriceType priceType;  // market or limit
    private BigDecimal price;

    public BaseOrder(InComeType inComeType, int quantity, PriceType priceType, BigDecimal price) {
        this.inComeType = inComeType;
        this.quantity = quantity;
        this.priceType = priceType;
        this.price = price;
    }

    public InComeType getInComeType() {
        return inComeType;
    }

    public int getQuantity() {
        return quantity;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public BigDecimal getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return "BaseOrder{" +
                "inComeType=" + inComeType +
                ", quantity=" + quantity +
                ", priceType=" + priceType +
                ", price=" + price +
                '}';
    }
}