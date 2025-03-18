package com.example.paymentservice.dtos;

import lombok.Data;

@Data
public class PaymentDto {
    private String email;
    private String name;
    private String phoneNumber;
    private String orderId;
    private Long amount;
}
