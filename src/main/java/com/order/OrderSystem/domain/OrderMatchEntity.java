package com.order.OrderSystem.domain;

import com.order.OrderSystem.application.engine.Order;
import com.order.OrderSystem.domain.type.InComeType;
import com.order.OrderSystem.domain.type.PriceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "ORDERTABLE")
@Getter
@Setter
@ToString
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
        setBuyOrderTime(buy.getOrderTime());
        setBuyUserName(buy.getUserName());
        setSellOrderTime(sell.getOrderTime());
        setSellUserName(sell.getUserName());
        setQuantity(buy.getQuantity());
        setPriceType(buy.getPriceType());
        setPrice(buy.getPrice());
    }
}
