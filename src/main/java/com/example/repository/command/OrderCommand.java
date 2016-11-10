package com.example.repository.command;

import com.example.logging.CustomLogger;
import com.example.repository.dto.order.OrderResponse;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import rx.Observable;

public class OrderCommand extends HystrixObservableCommand<OrderResponse> {
    private AsyncRestTemplate restTemplate;

    public OrderCommand(AsyncRestTemplate restTemplate) {
        super(HystrixCommandGroupKey.Factory.asKey("getOrders"));
        this.restTemplate = restTemplate;
    }

    @Override
    protected Observable<OrderResponse> construct() {
        return Observable.create(subscriber -> {
            final ListenableFuture<ResponseEntity<OrderResponse>> responseEntityListenableFuture =
                    restTemplate.getForEntity("http://localhost:5000/orders", OrderResponse.class);
            responseEntityListenableFuture.addCallback(new ListenableFutureCallback<ResponseEntity<OrderResponse>>() {
                @Override
                public void onFailure(Throwable ex) {
                    subscriber.onError(ex);
                }

                @Override
                public void onSuccess(ResponseEntity<OrderResponse> result) {
                    new CustomLogger().log("Finished processing.");
                    subscriber.onNext(result.getBody());
                    subscriber.onCompleted();
                }
            });
        });
    }
}




