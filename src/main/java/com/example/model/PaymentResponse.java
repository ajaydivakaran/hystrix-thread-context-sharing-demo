package com.example.model;

import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class PaymentResponse {
    private final List<Payment> payments;
    private final String correlationId;

    public PaymentResponse(com.example.repository.dto.payment.PaymentResponse paymentResponse) {
        this.payments = paymentResponse.getPayments().stream()
                .map(com.example.repository.dto.payment.Payment::toModel)
                .collect(Collectors.toList());
        this.correlationId = paymentResponse.getCorrelationId();
    }
}
