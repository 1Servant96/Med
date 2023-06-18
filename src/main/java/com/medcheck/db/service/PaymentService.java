package com.medcheck.db.service;

import com.medcheck.dto.request.PaymentRequest;
import com.medcheck.dto.response.SimpleResponse;

public interface PaymentService {
    SimpleResponse chargeCreditCard(PaymentRequest paymentRequest);
}
