package com.order.OrderSystem.adapter.in;

import com.order.OrderSystem.application.in.OrderUseCase;
import com.order.OrderSystem.application.out.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderUseCase orderUseCase;


    @GetMapping("/get")
    public String getOrder() {
        return orderRepository.findAll().toString();
    }

    @PostMapping("/order")
    public String order(RequestOrder requestOrder) {
        return orderUseCase.submit(requestOrder.toObj());
    }


}
