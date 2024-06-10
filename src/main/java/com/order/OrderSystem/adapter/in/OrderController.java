package com.order.OrderSystem.adapter.in;

import com.order.OrderSystem.OrderSystemApplication;
import com.order.OrderSystem.application.in.OrderUseCase;
import com.order.OrderSystem.application.out.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderUseCase orderUseCase;


    @GetMapping("/get")
    public String getOrder() {
        logger.info("Fetching all orders");
        return orderRepository.findAll().toString();
    }

    @PostMapping("/order")
    public String order(OrderRequest orderRequest) {
        logger.info("Received order request: {}", orderRequest);
        return orderUseCase.submit(orderRequest.toObj());
    }


}
