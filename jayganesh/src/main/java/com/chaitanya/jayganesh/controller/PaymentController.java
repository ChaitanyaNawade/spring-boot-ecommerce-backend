package com.chaitanya.jayganesh.controller;

import com.chaitanya.jayganesh.dto.CreateOrderRequest;
import com.chaitanya.jayganesh.dto.VerifyPaymentRequest;
import com.chaitanya.jayganesh.service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController
{
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService)
    {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-order")
    public String createOrder(@RequestBody CreateOrderRequest createOrderRequest) throws RazorpayException
    {
        return paymentService.createOrder(createOrderRequest.getAmount());
    }

    @PostMapping("/verify")
    public String verifyPayment(@RequestBody VerifyPaymentRequest verifyPaymentRequest) throws RazorpayException
    {
       boolean isValid = paymentService.verifyPayment(
                verifyPaymentRequest.getRazorpayOrderId(),
               verifyPaymentRequest.getRazorpayPaymentId(),
               verifyPaymentRequest.getRazorpaySignature()
       );

       if(isValid)
       {
           return "payment verified successfully";
       }
       else
       {
           return "payment verification failed";
       }
    }
}
