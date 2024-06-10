package com.order.OrderSystem;

import com.order.OrderSystem.application.in.OrderUseCase;
import com.order.OrderSystem.application.out.OrderRepository;
import com.order.OrderSystem.application.engine.Order;
import com.order.OrderSystem.domain.OrderMatchEntity;
import com.order.OrderSystem.domain.type.InComeType;
import com.order.OrderSystem.domain.type.PriceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class OrderSystemApplication {

    private static final Logger logger = LoggerFactory.getLogger(OrderSystemApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OrderSystemApplication.class, args);
        createTable();
    }

    @Component
    public class ReadyAndRun {
        private final Logger logger = LoggerFactory.getLogger(ReadyAndRun.class);

        @Autowired
        OrderUseCase orderUseCase;
        @Autowired
        OrderRepository<OrderMatchEntity> orderRepository;

        ExecutorService server = Executors.newFixedThreadPool(2);

        //價格範圍
        int priceScope = 10;
        @EventListener(ApplicationReadyEvent.class)
        public void onApplicationReady(ApplicationReadyEvent event) {
            try {
                logger.info("Application is ready.");
                // run random order
                for (int i = 0; i < 20; i++) {
                    server.execute(() -> {
                        Order order = createOrder();
                        logger.info("Created Order: {}", order);
                        orderUseCase.submit(order);
                    });
                }
                server.shutdown();
                boolean stop = server.awaitTermination(5, TimeUnit.SECONDS); // 等待最多 5秒
                if (stop) {
                    logger.info("-------------main to match order------------------");
                    List<OrderMatchEntity> orderList = List.of();
                    while(orderList.isEmpty()){
                         orderList = orderRepository.findAll();
                    }

                    orderList.forEach(order -> logger.info(order.toString()));
                    logger.info("finish");
                }
            } catch (Exception e) {
                logger.error("Exception occurred: ", e);
            }
        }

        Random random = new Random();

        public Order createOrder() {
            Order order = new Order(InComeType.values()[random.nextInt(InComeType.values().length)], //
                    random.nextInt(10) + 1, // 1-10
                    PriceType.values()[random.nextInt(PriceType.values().length)], //
                    BigDecimal.valueOf(random.nextInt(priceScope) + 1), //價格範圍
                    new Timestamp(System.currentTimeMillis()) //
            );
            order.setUserName(UUID.randomUUID().toString().replace("-", "").substring(0, 8));
            return order;
        }
    }

    private static void createTable() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS ORDERTABLE ( orderId INT AUTO_INCREMENT PRIMARY KEY , quantity INT  , priceType VARCHAR(10)," +
                    " price DECIMAL(15, 2) , buyOrderTime TIMESTAMP ,sellOrderTime TIMESTAMP , buyUserName VARCHAR(15) ,  sellUserName VARCHAR(15)  )");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            logger.error("Exception occurred while creating table: ", e);
        }
    }
}
