package com.example.repository.command;

import com.example.repository.dto.order.OrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderCommand {
    private RestTemplate restTemplate;

    public OrderCommand(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OrderResponse getOrders() {
        return restTemplate.getForObject("http://localhost:5000/orders", OrderResponse.class);
    }

}
