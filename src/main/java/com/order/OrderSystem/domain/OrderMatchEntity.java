package com.order.OrderSystem.domain;

import com.order.OrderSystem.application.engine.Order;
import com.order.OrderSystem.domain.type.InComeType;
import com.order.OrderSystem.domain.type.PriceType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "ORDERTABLE")
public class OrderMatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private int quantity;
    private PriceType priceType;  // market or limit
    private BigDecimal price;
    private Timestamp buyOrderTime; // 買家下訂時間
    private Timestamp sellOrderTime; // 賣家下訂時間
    private String buyUserName; // 匹配買家名稱
    private String sellUserName; // 匹配賣家名稱

    protected OrderMatchEntity() {
    }

    public OrderMatchEntity(Order order1, Order order2) {
        Order buy = (order1.getInComeType() == InComeType.BUY) ? order1 : order2;
        Order sell = (order1.getInComeType() == InComeType.SELL) ? order1 : order2;
        this.buyOrderTime = buy.getOrderTime();
        this.buyUserName = buy.getUserName();
        this.sellOrderTime = sell.getOrderTime();
        this.sellUserName = sell.getUserName();
        this.quantity = buy.getQuantity();
        this.priceType =  buy.getPriceType();
        this.price =buy.getPrice();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public Timestamp getBuyOrderTime() {
        return buyOrderTime;
    }

    public void setBuyOrderTime(Timestamp buyOrderTime) {
        this.buyOrderTime = buyOrderTime;
    }

    public Timestamp getSellOrderTime() {
        return sellOrderTime;
    }

    public void setSellOrderTime(Timestamp sellOrderTime) {
        this.sellOrderTime = sellOrderTime;
    }

    public String getBuyUserName() {
        return buyUserName;
    }

    public void setBuyUserName(String buyUserName) {
        this.buyUserName = buyUserName;
    }

    public String getSellUserName() {
        return sellUserName;
    }

    public void setSellUserName(String sellUserName) {
        this.sellUserName = sellUserName;
    }

    @Override
    public String toString() {
        return "OrderMatchEntity{" +
                "orderId=" + orderId +
                ", quantity=" + quantity +
                ", priceType=" + priceType +
                ", price=" + price +
                ", buyOrderTime=" + buyOrderTime +
                ", sellOrderTime=" + sellOrderTime +
                ", buyUserName='" + buyUserName + '\'' +
                ", sellUserName='" + sellUserName + '\'' +
                '}';
    }
}
