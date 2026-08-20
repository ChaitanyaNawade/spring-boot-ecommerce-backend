package com.chaitanya.jayganesh.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService
{
    @Value("${razorpay.key.id}")
    private  String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;


    public String createOrder(BigDecimal amount) throws RazorpayException
    {
        RazorpayClient client = new RazorpayClient(keyId,keySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount",amount.multiply(BigDecimal.valueOf(100)).intValue());
        orderRequest.put("currency","INR");
        orderRequest.put("receipt","receipt_"+System.currentTimeMillis());

        Order order = client.orders.create(orderRequest);

        return order.get("id");
    }


    public boolean verifyPayment(String orderId,String paymentId,String signature)throws RazorpayException
    {
        JSONObject options = new JSONObject();

        options.put("razorpay_order_id",orderId);
        options.put("razorpay_payment_id",paymentId);
        options.put("razorpay_signature",signature);

        return Utils.verifyPaymentSignature(options,keySecret);
    }
}