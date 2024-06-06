package com.order.OrderSystem.application.in;

public interface UseCase<Request,Response> {

    Response submit(Request req);
}
