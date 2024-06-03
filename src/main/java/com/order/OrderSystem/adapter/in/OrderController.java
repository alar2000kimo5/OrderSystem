package com.order.OrderSystem.adapter.in;

import com.order.OrderSystem.application.out.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/b")
    public String getOrder() {
        return orderRepository.findAll().toString();
    }
}
