package com.example.repository.dto.order;

import lombok.Value;

import java.util.List;

@Value
public class OrderResponse {
    private List<Order> orders;
    private String correlationId;
}
