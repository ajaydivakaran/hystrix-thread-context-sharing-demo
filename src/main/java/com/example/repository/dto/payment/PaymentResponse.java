package com.example.repository.dto.payment;

import lombok.Value;

import java.util.List;

@Value
public class PaymentResponse {
    private List<Payment> payments;
    private String correlationId;
}
