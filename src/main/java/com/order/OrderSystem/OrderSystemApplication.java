package com.order.OrderSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
@EnableJpaRepositories
public class OrderSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderSystemApplication.class, args);
        createTable();
    }

    @Component
    public class UrlPrinter {

        private final RequestMappingHandlerMapping handlerMapping;

        public UrlPrinter(RequestMappingHandlerMapping handlerMapping) {
            this.handlerMapping = handlerMapping;
        }

        @EventListener(ApplicationReadyEvent.class)
        public void onApplicationReady(ApplicationReadyEvent event) {
            handlerMapping.getHandlerMethods().forEach((key, value) -> {
                System.out.println("Mapped URL: " + key);
            });
        }
    }

    private static void createTable(){
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
