package com.example.hystrix;

import com.example.logging.RequestCorrelation;

import java.util.concurrent.Callable;

public class CorrelationIdCallable<K> implements Callable<K> {

    private final Callable<K> actual;
    private final String correlationId;

    public CorrelationIdCallable(Callable<K> actual) {
        this.actual = actual;
        // copy whatever state such as MDC
        this.correlationId = RequestCorrelation.getId();
    }

    @Override
    public K call() throws Exception {
        try {
            // set the state of this thread to that of its parent
            // this is where the MDC state and other ThreadLocal values can be set
            RequestCorrelation.setId(correlationId);
            // execute actual Callable with the state of the parent
            return actual.call();
        } finally {
            // restore this thread back to its original state
            RequestCorrelation.setId(correlationId);

        }
    }

}

