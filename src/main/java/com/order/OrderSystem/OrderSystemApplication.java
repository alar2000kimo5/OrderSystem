package com.order.OrderSystem;

import com.order.OrderSystem.domain.OrderByTime;
import com.order.OrderSystem.domain.OrderEngineByTime;
import com.order.OrderSystem.domain.type.InComeType;
import com.order.OrderSystem.domain.type.PriceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootApplication
@EnableJpaRepositories
public class OrderSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderSystemApplication.class, args);
        createTable();
    }

    @Component
    public class UrlPrinter {

        @Autowired
        OrderEngineByTime orderEngineByTime;

        @EventListener(ApplicationReadyEvent.class)
        public void onApplicationReady(ApplicationReadyEvent event) {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            ExecutorService server = Executors.newFixedThreadPool(2);
            try {
                AtomicBoolean run = new AtomicBoolean(true);
                long durationInSeconds = 1; // 執行時間 1 秒
                scheduler.schedule(() -> run.set(false), durationInSeconds, TimeUnit.SECONDS);
                //while (run.get()) {
                for (int i = 0; i < 100; i++) {
                    server.execute(() -> {
                        OrderByTime order = createOrder();
                        if (order.getInComeType() == InComeType.BUY) {
                            System.out.println("buy:" + order);
                            orderEngineByTime.putBuy(order);
                        } else {
                            System.out.println("sell:" + order);
                            orderEngineByTime.putSell(order);
                        }
                    });
                }

                //}

                scheduler.shutdown();
                server.shutdown();
                boolean stop = server.awaitTermination(2, TimeUnit.SECONDS); // 等待最多 1秒
                if (stop) {
                    System.out.println("-------------main to match order------------------");
                    List<OrderByTime> orderList = orderEngineByTime.matchOrder();
                    orderList.forEach(System.out::println);
                    System.out.println("finish");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public OrderByTime createOrder() {
            Random random = new Random();
            return new OrderByTime(
                    InComeType.values()[random.nextInt(InComeType.values().length)], //
                    random.nextInt(10) + 1, // 1-100
                    PriceType.values()[random.nextInt(PriceType.values().length)], //
                    BigDecimal.valueOf(random.nextInt(10) + 1), // 0-100
                    new Timestamp(System.currentTimeMillis()) //
            );
        }
    }

    private static void createTable() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "password");
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS test (id INT PRIMARY KEY, name VARCHAR(255))");
            stmt.execute("INSERT INTO TEST (id, name) VALUES (1, 'New Order')");
            ResultSet rs = stmt.executeQuery("SELECT * FROM test");

            while (rs.next()) {
                System.out.println(rs.getInt("id") + ", " + rs.getString("name"));
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
