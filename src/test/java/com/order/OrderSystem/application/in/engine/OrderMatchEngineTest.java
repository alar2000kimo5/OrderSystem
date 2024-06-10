package com.order.OrderSystem.application.in.engine;

import com.order.OrderSystem.application.engine.Order;
import com.order.OrderSystem.application.engine.OrderMatchEngine;
import com.order.OrderSystem.application.out.OrderRepository;
import com.order.OrderSystem.application.out.RedisQueueZSetService;
import com.order.OrderSystem.domain.OrderMatchEntity;
import com.order.OrderSystem.domain.type.InComeType;
import com.order.OrderSystem.domain.type.PriceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderMatchEngineTest {
    //sut
    OrderMatchEngine orderMatchEngine;

    //mock
    @Mock
    OrderRepository orderRepository;
    @Mock
    RedisQueueZSetService redisQueueZSetService;


    @BeforeEach
    public void setUp() {
        orderMatchEngine = new OrderMatchEngine(orderRepository, redisQueueZSetService);
    }


    @Test //當資料有2筆匹配，應匹配2筆
    public void givenDataMatch2times_whenMatchAndSave_thenMatch2times() {
        // 創建固定的測試資料 arrange
        Set<Order> orders = new LinkedHashSet<>();
        orders.add(createOrder("26ae7702", InComeType.SELL, 9, PriceType.MARKET, new BigDecimal("1"), Timestamp.valueOf("2024-06-10 13:56:58.973")));
        orders.add(createOrder("ea2012de", InComeType.SELL, 7, PriceType.LIMIT, new BigDecimal("5"), Timestamp.valueOf("2024-06-10 13:56:58.973")));
        orders.add(createOrder("82433741", InComeType.BUY, 9, PriceType.MARKET, new BigDecimal("1"), Timestamp.valueOf("2024-06-10 13:56:59.576")));
        orders.add(createOrder("1dfc9b0b", InComeType.SELL, 8, PriceType.MARKET, new BigDecimal("3"), Timestamp.valueOf("2024-06-10 13:56:59.576")));
        orders.add(createOrder("7c9645d7", InComeType.SELL, 10, PriceType.LIMIT, new BigDecimal("6"), Timestamp.valueOf("2024-06-10 13:56:59.582")));
        orders.add(createOrder("3dbbf406", InComeType.BUY, 7, PriceType.LIMIT, new BigDecimal("7"), Timestamp.valueOf("2024-06-10 13:56:59.581")));
        orders.add(createOrder("971d59f3", InComeType.BUY, 10, PriceType.MARKET, new BigDecimal("9"), Timestamp.valueOf("2024-06-10 13:56:59.585")));
        orders.add(createOrder("469a61ce", InComeType.BUY, 2, PriceType.LIMIT, new BigDecimal("9"), Timestamp.valueOf("2024-06-10 13:56:59.586")));
        orders.add(createOrder("e26c22d5", InComeType.BUY, 6, PriceType.LIMIT, new BigDecimal("10"), Timestamp.valueOf("2024-06-10 13:56:59.590")));
        orders.add(createOrder("b327d8af", InComeType.BUY, 8, PriceType.MARKET, new BigDecimal("3"), Timestamp.valueOf("2024-06-10 13:56:59.590")));

        // 執行匹配方法 action
        orderMatchEngine.matchAndSave("testQueue", orders);

        // 驗證是否保存到資料庫 assert
        ArgumentCaptor<OrderMatchEntity> captor = ArgumentCaptor.forClass(OrderMatchEntity.class);
        verify(orderRepository, times(2)).saveMatchOrder(captor.capture());
        List<OrderMatchEntity> savedOrderMatch = captor.getAllValues();

        OrderMatchEntity data1 = savedOrderMatch.get(0);
        // 檢查匹配結果
        assertEquals("26ae7702", data1.getSellUserName());
        assertEquals("82433741", data1.getBuyUserName());
        assertEquals(9, data1.getQuantity());
        assertEquals(PriceType.MARKET, data1.getPriceType());
        assertEquals(new BigDecimal("1"), data1.getPrice());

        OrderMatchEntity data2 = savedOrderMatch.get(1);
        // 檢查匹配結果b327d8af
        assertEquals("b327d8af", data2.getBuyUserName());
        assertEquals("1dfc9b0b", data2.getSellUserName());
        assertEquals(8, data2.getQuantity());
        assertEquals(PriceType.MARKET, data2.getPriceType());
        assertEquals(new BigDecimal("3"), data2.getPrice());
    }

    private Order createOrder(String userName, InComeType inComeType, int quantity, PriceType priceType, BigDecimal price, Timestamp orderTime) {
        Order order = new Order(inComeType, quantity, priceType, price, orderTime);
        order.setUserName(userName);
        return order;
    }

    @Test // 當時間較早的資料應該先被匹配
    public void givenEarlierTimestamps_whenMatchAndSave_thenMatchFirst() {
        // 創建固定的測試資料 arrange
        Set<Order> orders = new LinkedHashSet<>();
        orders.add(createOrder("user1", InComeType.SELL, 9, PriceType.MARKET, new BigDecimal("1"), Timestamp.valueOf("2024-06-10 13:56:58.973")));
        orders.add(createOrder("user2", InComeType.SELL, 7, PriceType.LIMIT, new BigDecimal("5"), Timestamp.valueOf("2024-06-10 13:56:58.973")));
        orders.add(createOrder("user3", InComeType.BUY, 9, PriceType.MARKET, new BigDecimal("1"), Timestamp.valueOf("2024-06-10 13:56:59.576")));
        orders.add(createOrder("user4", InComeType.SELL, 8, PriceType.MARKET, new BigDecimal("3"), Timestamp.valueOf("2024-06-10 13:56:59.576")));
        orders.add(createOrder("user5", InComeType.SELL, 10, PriceType.LIMIT, new BigDecimal("6"), Timestamp.valueOf("2024-06-10 13:56:59.582")));
        orders.add(createOrder("user6", InComeType.BUY, 7, PriceType.LIMIT, new BigDecimal("7"), Timestamp.valueOf("2024-06-10 13:56:59.581")));
        orders.add(createOrder("user7", InComeType.BUY, 10, PriceType.MARKET, new BigDecimal("9"), Timestamp.valueOf("2024-06-10 13:56:59.585")));
        orders.add(createOrder("user8", InComeType.BUY, 2, PriceType.LIMIT, new BigDecimal("9"), Timestamp.valueOf("2024-06-10 13:56:59.586")));
        orders.add(createOrder("user9", InComeType.BUY, 6, PriceType.LIMIT, new BigDecimal("10"), Timestamp.valueOf("2024-06-10 13:56:59.590")));
        orders.add(createOrder("user10", InComeType.BUY, 8, PriceType.MARKET, new BigDecimal("3"), Timestamp.valueOf("2024-06-10 13:56:59.590")));

        // 執行匹配方法 act
        orderMatchEngine.matchAndSave("testQueue", orders);

        // 驗證是否保存到資料庫 assert
        ArgumentCaptor<OrderMatchEntity> captor = ArgumentCaptor.forClass(OrderMatchEntity.class);
        verify(orderRepository, times(2)).saveMatchOrder(captor.capture());
        List<OrderMatchEntity> savedOrderMatch = captor.getAllValues();

        OrderMatchEntity data1 = savedOrderMatch.get(0);
        // 檢查匹配結果 verify
        assertEquals("user1", data1.getSellUserName());
        assertEquals("user3", data1.getBuyUserName());
        assertEquals(9, data1.getQuantity());
        assertEquals(PriceType.MARKET, data1.getPriceType());
        assertEquals(new BigDecimal("1"), data1.getPrice());

        OrderMatchEntity data2 = savedOrderMatch.get(1);
        // 檢查匹配結果
        assertEquals("user4", data2.getSellUserName());
        assertEquals("user10", data2.getBuyUserName());
        assertEquals(8, data2.getQuantity());
        assertEquals(PriceType.MARKET, data2.getPriceType());
        assertEquals(new BigDecimal("3"), data2.getPrice());
    }
}
