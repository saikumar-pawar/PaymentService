package com.example.paymentservice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    @PostMapping("/stripeWebhook")
    public  String receiveEvents(@RequestBody String e) {
        System.out.println(e);
        return e;
    }
}
