package com.order.OrderSystem.domain;

import java.util.List;

public interface UseCase<Request,Response> {

    Response submit(Request req);

    List<String> getQueueNames();
}
