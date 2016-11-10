package com.example.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;

import java.util.concurrent.Callable;

public class CorrelationHystrixConcurrencyStrategy extends HystrixConcurrencyStrategy {
    @Override
    public Callable wrapCallable(Callable callable) {
        return new CorrelationIdCallable(callable);
    }
}
