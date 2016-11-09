package com.example.model;

import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class OrderResponse {

    private final List<Order> orders;
    private final String correlationId;

    public OrderResponse(com.example.repository.dto.order.OrderResponse orderResponse) {
        this.orders = orderResponse.getOrders().stream()
                .map(com.example.repository.dto.order.Order::toModel)
                .collect(Collectors.toList());
        this.correlationId = orderResponse.getCorrelationId();
    }

}
