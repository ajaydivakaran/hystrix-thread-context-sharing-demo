package com.example.controller;

import com.example.model.OrderResponse;
import com.example.model.PaymentResponse;
import com.example.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;

import java.util.concurrent.CompletableFuture;

@RestController
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public CompletableFuture<OrderResponse> getOrders() {
        final Observable<com.example.model.OrderResponse> responseObservable = orderService.getOrders();
        return new RxJavaCompletableFuture<>(responseObservable);
    }

    @GetMapping("/payments")
    public CompletableFuture<PaymentResponse> getPayments() {
        final Observable<com.example.model.PaymentResponse> responseObservable = orderService.getPayments();
        return new RxJavaCompletableFuture<>(responseObservable);
    }

}


