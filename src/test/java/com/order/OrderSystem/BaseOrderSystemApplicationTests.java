package com.order.OrderSystem;

import com.order.OrderSystem.adapter.out.jpa.OrderJpa;
import com.order.OrderSystem.domain.MatchOrderEntity;
import com.order.OrderSystem.domain.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.StreamSupport;

@SpringBootTest
class BaseOrderSystemApplicationTests {

	@Autowired
	OrderJpa orderRepository;

	@Test
	void contextLoads() {
		//arrange
		Order buy = new Order();
		Order sell = new Order();
		MatchOrderEntity testEntity = new MatchOrderEntity(buy,sell);
		testEntity.setBuyUserName("alan");
		orderRepository.save(testEntity);
		//action
		Iterable<MatchOrderEntity> dataList = orderRepository.findAll();
		//assert
		List<MatchOrderEntity> list = StreamSupport
				.stream(dataList.spliterator(), false).toList();
		Assertions.assertEquals(1,list.size());
		Assertions.assertEquals(1,list.get(0).getOrderId());
		Assertions.assertEquals("alan",list.get(0).getBuyUserName());

	}

}
