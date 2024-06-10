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

    private OrderMatchEntity() {
    }

    public OrderMatchEntity(Order order1, Order order2) {
        Order buy = (order1.getInComeType() == InComeType.BUY) ? order1 : order2;
        Order sell = (order1.getInComeType() == InComeType.SELL) ? order1 : order2;
        setBuyOrderTime(buy.getOrderTime());
        setBuyUserName(buy.getUserName());
        setSellOrderTime(sell.getOrderTime());
        setSellUserName(sell.getUserName());
        setQuantity(buy.getQuantity());
        setPriceType(buy.getPriceType());
        setPrice(buy.getPrice());
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
