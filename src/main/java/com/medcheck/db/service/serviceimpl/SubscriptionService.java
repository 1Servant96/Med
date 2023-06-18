package com.medcheck.db.service.serviceimpl;

import com.medcheck.db.entities.Subscription;
import com.medcheck.dto.request.SubscriptionRequest;
import com.medcheck.dto.response.SimpleResponse;

import java.util.List;

public interface SubscriptionService {
    SimpleResponse save(SubscriptionRequest subscriptionRequest);

    List<Subscription> findAll();

    void sendEmail(Subscription subscription);
}
