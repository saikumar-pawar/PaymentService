package com.example.paymentservice.services;

public interface IPaymentService {
    String initiatePayment(String email, String name, String phoneNumber, String orderId, Long amount);
}
