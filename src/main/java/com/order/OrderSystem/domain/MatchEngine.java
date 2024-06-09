package com.order.OrderSystem.domain;

public interface MatchEngine<T,U> {

    boolean isMatch(T objT, U objU);
}
