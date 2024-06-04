package com.order.OrderSystem.domain.base;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public abstract class BaseOrderEngine<T extends BaseOrder> {

    private PriorityBlockingQueue<T> buyQueue;
    private PriorityBlockingQueue<T> sellQueue;

    public BaseOrderEngine() {
        this.buyQueue = new PriorityBlockingQueue<>(100, getComparator());
        this.sellQueue = new PriorityBlockingQueue<>(100, getComparator());
    }

    public void putBuy(T order) {
        this.buyQueue.put(order);
    }

    public void putSell(T order) {
        this.sellQueue.put(order);
    }

    public T pollBuy() throws InterruptedException {
        return this.buyQueue.poll(1, TimeUnit.SECONDS);
    }

    public T pollSell() throws InterruptedException {
        return this.sellQueue.poll(1, TimeUnit.SECONDS);
    }

    BiPredicate<T, T> match = (buy, sell) -> sell.getQuantity() == buy.getQuantity() //
                    && sell.getPriceType().equals(buy.getPriceType()) //
                    && sell.getPrice().equals(buy.getPrice());

    public List<T> matchOrder() {
        System.out.println("-----------------match order-------------------");
        return buyQueue.stream().filter((buy) -> sellQueue.stream().anyMatch(
                (sell) -> match.test(buy, sell))).toList();
    }


    protected abstract Comparator<T> getComparator();
}
