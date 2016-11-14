package com.example.repository.command;

import com.example.hystrix.ThreadContextAwareListenableFuture;
import com.example.logging.CustomLogger;
import com.example.repository.dto.payment.PaymentResponse;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import rx.Observable;

public class PaymentCommand extends HystrixObservableCommand<PaymentResponse> {
    private AsyncRestTemplate restTemplate;

    public PaymentCommand(AsyncRestTemplate restTemplate) {
        super(HystrixCommandGroupKey.Factory.asKey("getPayments"));
        this.restTemplate = restTemplate;
    }

    @Override
    protected Observable<PaymentResponse> construct() {
        return Observable.create(subscriber -> {
            final ListenableFuture<ResponseEntity<PaymentResponse>> responseEntityListenableFuture =
                    new ThreadContextAwareListenableFuture<>(restTemplate.getForEntity
                            ("http://localhost:5001/payments", PaymentResponse.class));
            responseEntityListenableFuture.addCallback(new ListenableFutureCallback<ResponseEntity<PaymentResponse>>() {
                @Override
                public void onFailure(Throwable ex) {
                    subscriber.onError(ex);
                }

                @Override
                public void onSuccess(ResponseEntity<PaymentResponse> result) {
                    new CustomLogger().log("Finished processing.");
                    subscriber.onNext(result.getBody());
                    subscriber.onCompleted();
                }
            });

        });
    }
}
