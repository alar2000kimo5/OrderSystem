package com.order.OrderSystem;

import com.order.OrderSystem.adapter.out.jpa.OrderJpa;
import com.order.OrderSystem.domain.TestEntity;
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
		TestEntity testEntity = new TestEntity();
		testEntity.setId(2);
		testEntity.setName("alan");
		orderRepository.save(testEntity);
		//action
		Iterable<TestEntity> dataList = orderRepository.findAll();
		//assert
		List<TestEntity> list = StreamSupport
				.stream(dataList.spliterator(), false).toList();
		Assertions.assertEquals(1,list.size());
		Assertions.assertEquals(2,list.get(0).getId());
		Assertions.assertEquals("alan",list.get(0).getName());

	}

}
