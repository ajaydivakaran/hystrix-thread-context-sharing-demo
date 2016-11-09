package com.example.service;

import com.example.repository.command.OrderCommand;
import com.example.repository.dto.order.OrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

@Service
public class OrderService {
    private RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Observable<com.example.model.OrderResponse> getOrders() {
        final Observable<OrderResponse> orderResponse = new OrderCommand(restTemplate).observe();
        return orderResponse.map(com.example.model.OrderResponse::new);
    }

}
