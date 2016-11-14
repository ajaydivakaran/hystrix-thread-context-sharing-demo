package com.example.service;

import com.example.logging.CustomLogger;
import com.example.repository.command.OrderCommand;
import com.example.repository.command.PaymentCommand;
import com.example.repository.dto.order.OrderResponse;
import com.example.repository.dto.payment.PaymentResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.AsyncRestTemplate;
import rx.Observable;

@Service
public class OrderService {
    private AsyncRestTemplate restTemplate;

    public OrderService(AsyncRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Observable<com.example.model.OrderResponse> getOrders() {
        final Observable<OrderResponse> orderResponse = new OrderCommand(restTemplate).observe();
        orderResponse.flatMap(this::getPayments);
        return orderResponse.map(com.example.model.OrderResponse::new);
    }

    public Observable<com.example.model.PaymentResponse> getPayments() {
        final Observable<OrderResponse> orderResponse = new OrderCommand(restTemplate).observe();
        return orderResponse.flatMap(this::getPayments)
                .map(com.example.model.PaymentResponse::new);
    }

    private Observable<PaymentResponse> getPayments(OrderResponse o) {
        new CustomLogger().log("About to call payments");
        return new PaymentCommand(restTemplate).observe();
    }

}
