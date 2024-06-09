package com.order.OrderSystem;

import com.order.OrderSystem.adapter.out.jpa.OrderJpa;
import com.order.OrderSystem.domain.OrderMatchEntity;
import com.order.OrderSystem.domain.Order;
import com.order.OrderSystem.domain.type.InComeType;
import com.order.OrderSystem.domain.type.PriceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.StreamSupport;

@SpringBootTest
class BaseOrderSystemApplicationTests {

	@Autowired
	OrderJpa orderRepository;
	Random random = new Random();
	@Test
	void contextLoads() {
		//arrange
		Order buy = createOrder();
		Order sell = createOrder();
		OrderMatchEntity testEntity = new OrderMatchEntity(buy,sell);
		testEntity.setBuyUserName("alan");
		orderRepository.save(testEntity);
		//action
		Iterable<OrderMatchEntity> dataList = orderRepository.findAll();
		//assert
		List<OrderMatchEntity> list = StreamSupport
				.stream(dataList.spliterator(), false).toList();
		Assertions.assertEquals(1,list.size());
		Assertions.assertEquals(1,list.get(0).getOrderId());
		Assertions.assertEquals("alan",list.get(0).getBuyUserName());

	}

	public Order createOrder() {
		Order order = new Order(InComeType.values()[random.nextInt(InComeType.values().length)], //
				random.nextInt(10) + 1, // 1-100
				PriceType.values()[random.nextInt(PriceType.values().length)], //
				BigDecimal.valueOf(random.nextInt(10) + 1), // 0-100
				new Timestamp(System.currentTimeMillis()) //
		);
		order.setUserName(UUID.randomUUID().toString().replace("-", "").substring(0, 8));
		return order;
	}
}
