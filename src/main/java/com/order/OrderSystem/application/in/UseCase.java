package com.order.OrderSystem.application.in;

import com.order.OrderSystem.domain.RedisService;

import java.util.List;

public interface UseCase<Request,Response> {

    Response submit(Request req);

    List<String> getQueueNames();

    RedisService getRedisService();
}
