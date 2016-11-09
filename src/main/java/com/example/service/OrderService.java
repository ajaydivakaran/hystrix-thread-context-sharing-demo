package com.example.service;

import com.example.repository.command.OrderCommand;
import com.example.repository.dto.order.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private OrderCommand orderCommand;

    public OrderService(OrderCommand orderCommand) {
        this.orderCommand = orderCommand;
    }

    public com.example.model.OrderResponse getOrders() {
        final OrderResponse orderResponse = orderCommand.getOrders();
        return new com.example.model.OrderResponse(orderResponse);
    }

}
