package com.medcheck.db.service.serviceimpl;

import com.medcheck.db.service.PaymentService;
import com.medcheck.dto.request.PaymentRequest;
import com.medcheck.dto.response.SimpleResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

    @Service
    @Slf4j
    public class PaymentServiceImpl implements PaymentService {
        @Value("${stripe.apiKey}")
        private String apiKey;

        @Override
        public SimpleResponse chargeCreditCard(PaymentRequest paymentRequest) {
            try {
                Stripe.apiKey = apiKey;
                Map<String, Object> chargeParams = new HashMap<>();
                int amount = (int) Math.round(paymentRequest.getAmount() * 100);
                chargeParams.put("amount", amount);
                chargeParams.put("currency", paymentRequest.getCurrency());
                chargeParams.put("source", paymentRequest.getToken());
                Charge.create(chargeParams);
                log.info("successfully the charge credit card method works ");
                return new SimpleResponse("Payment successful!", "200");
            } catch (StripeException e) {
                log.error("Something wrong with token");
                return new SimpleResponse(e.getMessage(), "404");
            }
        }

    }

