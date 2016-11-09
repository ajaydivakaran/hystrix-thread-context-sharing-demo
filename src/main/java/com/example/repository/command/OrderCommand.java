package com.example.repository.command;

import com.example.repository.dto.order.OrderResponse;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import org.springframework.web.client.RestTemplate;

public class OrderCommand extends HystrixCommand<OrderResponse> {
    private RestTemplate restTemplate;

    public OrderCommand(RestTemplate restTemplate) {
        super(HystrixCommandGroupKey.Factory.asKey("getOrders"), HystrixThreadPoolKey.Factory.asKey("orderService"));
        this.restTemplate = restTemplate;
    }

    @Override
    public OrderResponse run() {
        return restTemplate.getForObject("http://localhost:5000/orders", OrderResponse.class);
    }

}




