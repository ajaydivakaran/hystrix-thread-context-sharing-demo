package com.example.repository.dto.order;

import lombok.Value;

@Value
public class Order {
    private int id;
    private String description;

    public com.example.model.Order toModel() {
        return new com.example.model.Order(id, description);
    }
}
