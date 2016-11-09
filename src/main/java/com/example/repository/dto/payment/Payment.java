package com.example.repository.dto.payment;

import lombok.Value;

@Value
public class Payment {
    private int id;
    private int amount;

    public com.example.model.Payment toModel() {
        return new com.example.model.Payment(id, amount);
    }
}
