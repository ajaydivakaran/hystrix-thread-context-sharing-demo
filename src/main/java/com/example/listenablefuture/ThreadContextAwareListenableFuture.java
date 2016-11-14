package com.example.listenablefuture;

import com.example.logging.RequestCorrelation;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ThreadContextAwareListenableFuture<T> implements ListenableFuture<T> {

    private final String id;
    private ListenableFuture<T> listenableFuture;

    public ThreadContextAwareListenableFuture(ListenableFuture<T> listenableFuture) {
        this.listenableFuture = listenableFuture;
        id = RequestCorrelation.getId();
    }

    @Override
    public void addCallback(ListenableFutureCallback<? super T> originalCallback) {

        listenableFuture.addCallback(new ListenableFutureCallback<T>() {
            @Override
            public void onFailure(Throwable ex) {
                setRequestCorrelation();
                originalCallback.onFailure(ex);
            }

            @Override
            public void onSuccess(T result) {
                setRequestCorrelation();
                originalCallback.onSuccess(result);

            }
        });
    }

    @Override
    public void addCallback(SuccessCallback<? super T> successCallback, FailureCallback failureCallback) {
        listenableFuture.addCallback(result -> {
            setRequestCorrelation();
            successCallback.onSuccess(result);
        }, ex -> {
            setRequestCorrelation();
            failureCallback.onFailure(ex);
        });
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return listenableFuture.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return listenableFuture.isCancelled();
    }

    @Override
    public boolean isDone() {
        return listenableFuture.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return listenableFuture.get();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return listenableFuture.get(timeout, unit);
    }

    private void setRequestCorrelation() {
        RequestCorrelation.setId(id);
    }
}
